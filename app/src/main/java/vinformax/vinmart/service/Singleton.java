package vinformax.vinmart.service;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


/**
 * Created by subbian.goutham on 12/11/2015.
 */
public class Singleton {

    private static Context con;
    private RequestQueue requestQueue;
    private static Singleton singleton;
    private JSONObject jsonresult;
    private ImageLoader imageLoader;
    private LruBitmapCache lruBitmapCache;


    public Singleton(Context con) {
        this.con = con;
        requestQueue = getRequstQueue();
        lruBitmapCache = new LruBitmapCache(LruBitmapCache.getCacheSize(con));
        imageLoader = new ImageLoader(getRequstQueue(), lruBitmapCache);
        jsonresult = new JSONObject();
    }

    public static synchronized Singleton getInstance(Context context) {
        if (singleton == null) {
            singleton = new Singleton(context);
        }
         return singleton;
    }

    public RequestQueue getRequstQueue() {

        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(con.getApplicationContext());
        }
        return requestQueue;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequstQueue().add(req);
    }


    public JSONObject getresfromserver(String url) {
        try {

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Do something with the response
                            jsonresult = response;
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle error
                        }
                    });
            singleton.addToRequestQueue(stringRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonresult;
    }

    public JSONObject getreqandresfromserver(String url, JSONObject json) {
        try {

            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, json,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            // Do something with the response
                            jsonresult = response;
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle error
                        }
                    });
            singleton.addToRequestQueue(stringRequest);
        } catch (Exception e) {

        }
        return jsonresult;
    }

    public void setOnBitmapCompleteListener(LruBitmapCache.BitmapCompleteListener bitmapCompleteListener) {
        lruBitmapCache.setBitmapCompleteListener(bitmapCompleteListener);
    }

    public void setTheme(String id){
        try {
            JSONObject requestParam = new JSONObject();
            requestParam.put("id",id);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest( Allurls.url_theme_config, requestParam, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    AppController.contentMainModel = new ContentMainModel(jsonObject);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {

                }
            });
            singleton.addToRequestQueue(jsonObjectRequest);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
