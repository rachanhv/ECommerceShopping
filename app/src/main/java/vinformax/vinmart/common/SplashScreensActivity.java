package vinformax.vinmart.common;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import vinformax.vinmart.R;
import vinformax.vinmart.database.VinMartSqlLite;
import vinformax.vinmart.model.UserData;
import vinformax.vinmart.service.Allurls;
import vinformax.vinmart.service.Checknetworkconnection;
import vinformax.vinmart.service.Singleton;

public class SplashScreensActivity extends AppCompatActivity {

   /* private Dialog mSplashScreenDialog;
    private boolean mShouldSetContentView = true;
    private Handler mHandler;
    private Runnable mRunnable;*/
    private Checknetworkconnection checknetworkconnection;
    private View view;
    ImageView image;
    Animation animTogether;
    private CoordinatorLayout coordinatorLayout;
    boolean checkInternet = false;
    boolean cnchk = false;
    VinMartSqlLite vinMartSqlLite;
    List<UserData> userdata = new ArrayList<>();

///////////////// for variable declare By Rachan 9.7.2016///////////////

    public static Map<String, Integer> hm = new HashMap<String, Integer>();
    public static ArrayList<String> favlist = new ArrayList<String>();
    public static String carthomecount = "0", loginst = "1", loginemailid = "",
            UserorExecutivesID = "", customerid_cartandfav = "", customer_nameglobal = "", customer_Executive = "",
            userorcustID = "", loginusertype = "", sqllitefav = "", sqllitecart, loginorlogoutstatus = "N", userinputtype = "";



    public static Context allcon;
    public static int downloadcsv = 0, emp = 0;
    public static String csvfilename = "", currsymbol = "";
    public static Context maincatcontext;


    public static String[] prdimgname, prditemid, group_ids, type_ids, category_ids, itemquantity, actualAmount, status;
    public static String[] imgname, price, imgurlname, discounts;

    public static int retdetails, errorcnt = 0;
    JSONArray dataJsonArr = new JSONArray();
    public static LinearLayout layout;
    RelativeLayout main;
    String name,number;
//////////////// for variable declare///////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash_screen);
        image = (ImageView) findViewById(R.id.comLogo);
       cnchk = Checknetworkconnection.isConnectingToInternet(SplashScreensActivity.this);
        main = (RelativeLayout) findViewById(R.id.mainLayout);
        layout = (LinearLayout) findViewById(R.id.linlayoutprogressads);

        vinMartSqlLite = new VinMartSqlLite(SplashScreensActivity.this);
        userdata = vinMartSqlLite.getUserDetails();



/*

        if(userdata==null){

        }
        else if(userdata!=null){

            userdata.size();
            Log.d("size", String.valueOf(userdata.size()));
        }
        else {

        }
*/



       // showSplashScreen(); // commented because i used in if condition

      //  Contacts contacts= new Contacts(name, number, false);

    }
    @Override
    protected void onResume() {
        super.onResume();

        if (cnchk == true) {
            //Database
            startUpVolley();

            //new	AsyncTaskParseJson().execute();
        } else {
//            Toast.makeText(Assignallvarible.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
            Snackbar snack = Snackbar.make(main, "Please check your internet connection", Snackbar.LENGTH_INDEFINITE);
            // Toast.makeText(SplashScreensActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
            Toast.makeText(SplashScreensActivity.this,"networklost", Toast.LENGTH_LONG).show();
            snack.setAction("Try Again", new View.OnClickListener() {
                @Override
                public void onClick(View v){
                    startUpVolley();
                }
            });
            ViewGroup group = (ViewGroup) snack.getView();
            group.setBackgroundColor(ContextCompat.getColor(SplashScreensActivity.this, R.color.red));
            snack.show();
        }
    }



    public void startUpVolley() {
       // DatabaseHandler db = new DatabaseHandler(this);

        //int insertstatus = db.addlLoginDetails(Assignallvarible.UserorExecutivesID, Assignallvarible.customerid_cartandfav, Assignallvarible.loginst, Assignallvarible.loginemailid, Assignallvarible.loginusertype, "joinedfavid", "cartlist");

        //retdetails = db.getlogindetails();

      //  String url = "http://10.0.0.247/fvnphpfiles/allsubproducts.php";
        String url = "http://www.vinformax.com/vts/android-pro1/shopping/fvnphpfiles/allsubproducts.php";
//		isInternetOn();
        animTogether = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.firstcycle);

    //    animTogether.setAnimationListener(this);
        Singleton svolley = new Singleton(SplashScreensActivity.this);
        //JSONObject jsonres = svolley.getresfromserver(Allurls.url_allsubcategories);
        try {
            layout.setVisibility(View.VISIBLE);
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Do something with the response

                            try {

                                try {
                                    dataJsonArr = response.getJSONArray("Productitems");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                prditemid = new String[dataJsonArr.length()];
                                itemquantity = new String[dataJsonArr.length()];
                                status = new String[dataJsonArr.length()];
                                prdimgname = new String[dataJsonArr.length()];
                                imgname = new String[dataJsonArr.length()];
                                imgurlname = new String[dataJsonArr.length()];
                                price = new String[dataJsonArr.length()];
                                discounts = new String[dataJsonArr.length()];
                                group_ids = new String[dataJsonArr.length()];
                                type_ids = new String[dataJsonArr.length()];
                                category_ids = new String[dataJsonArr.length()];
                                actualAmount = new String[dataJsonArr.length()];
                                if (dataJsonArr != null) {
                                    for (int i = 0; i < dataJsonArr.length(); i++) {
                                        JSONObject c = null;
                                        try {
                                            c = dataJsonArr.getJSONObject(i);
                                            prditemid[i] = c.getString("item_id");
                                            itemquantity[i] = c.getString("item_quantity");
                                            status[i] = c.getString("status");
                                            prdimgname[i] = c.getString("item_name");
                                            price[i] = c.getString("item_price");
                                            discounts[i] = c.getString("item_discounts");
                                            imgurlname[i] = Allurls.url_allimagedirectory + c.getString("item_image");
                                            group_ids[i] = c.getString("group_id");
                                            type_ids[i] = c.getString("type_id");

                                            actualAmount[i] = c.getString("item_price");
                                            category_ids[i] = c.getString("category_id");

                                            hm.put(prditemid[i], 0);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        // Storing each json item in variable

                                    }
                                }
                                if (retdetails >= 1) {
                                    if (SplashScreensActivity.sqllitefav.trim().length() > 0) {
                                        String splitfav[] = SplashScreensActivity.sqllitefav.split(",");
                                        for (String favlistfromtbl : splitfav) {
                                            SplashScreensActivity.favlist.add(favlistfromtbl);
                                        }
                                    } else {
                                        emp = 1;
                                    }
                                    try {
                                        JSONObject cartlistfrmtbl = new JSONObject(SplashScreensActivity.
                                                sqllitecart);
                                        Iterator keys = cartlistfrmtbl.keys();

                                        while (keys.hasNext()) {
                                            // loop to get the dynamic key
                                            String currentDynamicKey = (String) keys.next();
                                            // get the value of the dynamic key
                                            int userPhotoId = cartlistfrmtbl.getInt(currentDynamicKey);
                                            // int currentDynamicValue = Integer.parseInt(cartlistfrmtbl.getJSONObject(currentDynamicKey).toString());
                                            SplashScreensActivity.hm.put(currentDynamicKey, userPhotoId);
                                            // do something here with the value...
                                        }

                                    } catch (JSONException e) {
                                        // TODO Auto-generated catch block
                                        e.printStackTrace();
                                    }

                                }

                            /*    Sukumar 6/22/16 hidden for without using login */

                               /* if (Assignallvarible.loginst.equals("Admin")) {
                                    Intent vv = new Intent(Assignallvarible.this, Adminhome.class);
                                    startActivity(vv);
                                } else {
                                    if (loginorlogoutstatus.equals("Y")) {
                                        Intent vv = new Intent(Assignallvarible.this, NavDrawerMainActivity.class);
                                        startActivity(vv);
                                        layout.setVisibility(View.GONE);
                                        finish();
                                    } else {
                                        Intent vv = new Intent(Assignallvarible.this, LoginScreen.class);
                                        startActivity(vv);
                                        layout.setVisibility(View.GONE);
                                        finish();
                                    }
                                }
*/
                                Intent vv = new Intent(SplashScreensActivity.this, MainActivity.class);
                                startActivity(vv);
                                layout.setVisibility(View.GONE);
                                finish();

                            } catch (NullPointerException nullexe) {
                                nullexe.printStackTrace();
                            }
                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle error
                            Snackbar snack = Snackbar.make(main, "Please check your internet connection", Snackbar.LENGTH_INDEFINITE);
                            layout.setVisibility(View.GONE);
                            snack.setAction("Try Again", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                           startUpVolley();
                                }
                            });
                            ViewGroup group = (ViewGroup) snack.getView();
                            group.setBackgroundColor(ContextCompat.getColor(SplashScreensActivity.this, R.color.red));
                            snack.show();

                        }

                    });
            svolley.addToRequestQueue(stringRequest);
            JsonObjectRequest jsonreqmaincat = new JsonObjectRequest(Request.Method.GET, Allurls.url_maincategories, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            try {
                                JSONArray jarrayfortypemaster = jsonObject.getJSONArray("Symbol");
                                if (jarrayfortypemaster != null) {
                                    if (jarrayfortypemaster.length() > 0) {
                                        JSONObject b = jarrayfortypemaster.getJSONObject(0);
                                        SplashScreensActivity.currsymbol = (b.getString("CCSymbol").equals("GBP") ? "£" : b.getString("CCSymbol"));
                                    }
                                }
                            } catch (JSONException ex) {
                                Log.e("Error in maincat", ex.toString());
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });
            svolley.addToRequestQueue(jsonreqmaincat);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
/*
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null) {
            mHandler.removeCallbacks(mRunnable);
        }
    }

    private void setTimerToHideSplashScreen() {
        mHandler = new Handler();
        mRunnable = new Runnable() {
            @Override
            public void run() {
                hideSplashScreen();
            }
        };
        mHandler.postDelayed(mRunnable, 4000);	//2 seconds
    }

    private void hideSplashScreen() {
        if (mSplashScreenDialog != null) {
            mSplashScreenDialog.dismiss();
            mSplashScreenDialog = null;
        }
    }*/






    ///////////////////////////////////////// Dont Look Down//////////////////
  /*  private void showSplashScreen() {
        mSplashScreenDialog = new Dialog(this, R.style.SplashScreenStyle);
        mSplashScreenDialog.setContentView(R.layout.activity_splash_screen);
        mSplashScreenDialog.show();
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.splashCoordinator);
        mSplashScreenDialog.setOnCancelListener(new OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                mShouldSetContentView = false;
                finish();
            }
        });
        mSplashScreenDialog.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                if (mShouldSetContentView) {
                    checkInternet   = Checknetworkconnection.isConnectingToInternet(SplashScreensActivity.this);

                    if(checkInternet)
                    {
                        Intent i = new Intent(SplashScreensActivity.this,MainActivity.class);
                        startActivity(i);
                        SplashScreensActivity.this.finish();
                    }
                    else {
                        Toast.makeText(SplashScreensActivity.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });
        setTimerToHideSplashScreen();
    }*/


}
