package vinformax.vinmart.service;

import android.app.Application;

/**
 * Created by basker.t on 12/23/2015.
 */
public class AppController extends Application {
    public static ContentMainModel contentMainModel=new ContentMainModel();
    @Override
    public void onCreate() {
        super.onCreate();
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Allurls.url_theme_config, null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject jsonObject) {
//                     contentMainModel=new ContentMainModel(jsonObject);
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//
//            }
//        });
//        Singleton.getInstance(getApplicationContext()).addToRequestQueue(jsonObjectRequest);
        Singleton.getInstance(getApplicationContext()).setTheme("S0001");
    }
}
