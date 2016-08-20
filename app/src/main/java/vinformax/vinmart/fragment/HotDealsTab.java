package vinformax.vinmart.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import vinformax.vinmart.R;
import vinformax.vinmart.model.AssignVariable;
import vinformax.vinmart.recycleviewadapter.HotDealsAdapter;
import vinformax.vinmart.service.Allurls;

public class HotDealsTab extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public static HotDealsAdapter hotDealsAdapter;
    private final   List<AssignVariable> mAssignVariableList4 = new ArrayList<>();
    public static android.support.v7.widget.RecyclerView recyclerView=null;


    private StaggeredGridLayoutManager staggeredGridLayoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    ProgressBar progressBar;

    Animation animTogether;
    public static RelativeLayout layout;

    private OnFragmentInteractionListener mListener;
    String[]header;
    public HotDealsTab() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //jsonimages();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.hotdeals_tab, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.lst_items);
       // progressBar =(ProgressBar) view.findViewById(R.id.progressBar);

        //progressBar.setVisibility(View.VISIBLE);
        layout=(RelativeLayout)view.findViewById(R.id.layout);


        jsonimages();
        staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, 1);
        staggeredGridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        hotDealsAdapter = new HotDealsAdapter(mAssignVariableList4);
        //hotDealsAdapter = new HotDealsAdapter(mAssignVariableList4);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        recyclerView.getRecycledViewPool().clear();
       // progressBar.setVisibility(View.GONE);
        recyclerView.setAdapter(hotDealsAdapter);
        hotDealsAdapter.notifyDataSetChanged();
        //recyclerView.swapAdapter(hotDealsAdapter,true);


/*
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(hotDealsAdapter);
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
        recyclerView.setAdapter(scaleAdapter);*/
        /*animTogether = AnimationUtils.loadAnimation(getActivity(),
                R.anim.firstcycle);
*/

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container_hotdeals);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        return view;
    }



    private void jsonimages() {




        //String url = "http://10.0.0.247/fvnphpfiles/allsubproducts.php";
         String url = "http://www.vinformax.com/vts/android-pro1/shopping/fvnphpfiles/allsubproducts.php";
         //String url = "http://10.0.0.110/fvnphpfiles/demomasterproduct.php";
         //http://10.0.0.228/fvnphpfiles/demomasterproduct.php
         // String url ="http://www.vinformax.com/vts/android-pro1/shopping/fvnphpfiles/masterproducthomepage.php";
        // final DiscountAdapterOne discountAdapterOne = new DiscountAdapterOne(mAssignVariableList3);
         RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
         //requestQueue.getCache().clear();
        requestQueue.getCache().remove(url);
         JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                 new Response.Listener<JSONObject>() {
                     @Override
                     public void onResponse(JSONObject response) {
                         try {
                             JSONArray ja = response.getJSONArray("Productitems");
                             header = new String[ja.length()];
                             for (int i = 1; i < 100; i++) {
                                 AssignVariable assignVariable = new AssignVariable();
                                 JSONObject jsonObject = ja.getJSONObject(i);
                                 Log.d("nameitem1", jsonObject.getString("item_name"));
                                 Log.d("nameitem2",jsonObject.getString("item_price"));
                                 assignVariable.setProductModelName(jsonObject.getString("item_name"));
                                 assignVariable.setProductModelActualPrice(jsonObject.getString("item_price"));
                                 assignVariable.setProductModelDiscountPrice(jsonObject.getString("item_discounts"));
                                 assignVariable.setProductImage(Allurls.url_allimagedirectory+jsonObject.getString("item_image"));

                                 mAssignVariableList4.add(assignVariable);
                             }

                             hotDealsAdapter.notifyDataSetChanged();

                         } catch (JSONException e) {
                             e.printStackTrace();
                         }
                     }
                 },
                 new Response.ErrorListener() {
                     @Override
                     public void onErrorResponse(VolleyError error) {
                     }
                 }) {

             @Override
             protected Map<String, String> getParams() throws AuthFailureError {
                 return super.getParams();
             }
         };

         requestQueue.add(jsonObjectRequest);
             }

    // TODO: Rename method, update argument and hook method into UI event
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);

            }
        });
       // final String url = "http://www.vinformax.com/vts/android-pro1/shopping/fvnphpfiles/allsubproducts.php";
       // new AsyncHttpTask().execute(url);


        swipeRefreshLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}


