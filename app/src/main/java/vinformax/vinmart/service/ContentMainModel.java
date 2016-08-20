package vinformax.vinmart.service;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by basker.t on 12/23/2015.
 */
public class ContentMainModel {
    private int colorPrimary;
    private int colorPrimaryDark;
    private int colorAccent,colorAddtoCartBtn;
    private Activity activity;
    private static int tabColorPrimary;
    private static boolean transparent;
    //private ColorStateList tabColorList;

    public ContentMainModel() {
        colorPrimary= Color.parseColor("#39BCFC");
        colorPrimaryDark= Color.parseColor("#33ace7");
        colorAccent= Color.parseColor("#eeeeee");
        colorAddtoCartBtn= Color.parseColor("#c2ebff");
    }

    public ContentMainModel(JSONObject object) {
        try {
            JSONArray array = object.getJSONArray("theme");
            JSONObject jsonObject = array.getJSONObject(0);
            this.colorPrimary = Color.parseColor(jsonObject.getString("colorPrimary"));
            this.colorPrimaryDark = Color.parseColor(jsonObject.getString("colorPrimaryDark"));
            this.colorAccent = Color.parseColor(jsonObject.getString("colorAccent"));
            this.colorAddtoCartBtn = Color.parseColor(jsonObject.getString("colorAddtoCartBtn"));
            //ColorStateList colorStateList=new ColorStateList()
            tabColorPrimary=colorPrimary;
            //notifyChange();
            setStatusBarColor();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }




    public int getColorPrimaryDark() {
        return this.colorPrimaryDark;

    }


    public void setStatusBarActivity(Activity activity, boolean transparent) {
        this.transparent=transparent;
        this.activity = activity;
        setStatusBarColor();

    }

    private void setStatusBarColor() {
        if (activity != null )
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if(transparent){
                    activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
                }else {
                    activity.getWindow().setStatusBarColor(getColorPrimaryDark());
                }
            }
    }
}
