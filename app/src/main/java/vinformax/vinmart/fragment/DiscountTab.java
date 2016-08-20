package vinformax.vinmart.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import vinformax.vinmart.adapter.AutoScrollViewPager;
import vinformax.vinmart.indicator.CircleIndicator1;
import vinformax.vinmart.model.AssignVariable;
import vinformax.vinmart.recycleviewadapter.DiscountAdapterFour;
import vinformax.vinmart.recycleviewadapter.DiscountAdapterOne;
import vinformax.vinmart.recycleviewadapter.DiscountAdapterThree;
import vinformax.vinmart.recycleviewadapter.DiscountAdapterTwo;
import vinformax.vinmart.service.Allurls;


public class DiscountTab extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    /////////First Adapter//////////
    static DiscountAdapterOne adapter;
    static DiscountAdapterTwo adapter2;
    static DiscountAdapterThree adapter3;
    static DiscountAdapterFour adapter4;
    private final List<AssignVariable> mAssignVariableList = new ArrayList<AssignVariable>();
    private final List<AssignVariable> mAssignVariab1 = new ArrayList<>();
    private final List<AssignVariable> mAssignVariab2 = new ArrayList<>();
    private final List<AssignVariable> mAssignVariab3 = new ArrayList<>();

    public static android.support.v7.widget.RecyclerView recyclerView=null;
    SwipeRefreshLayout swipeRefreshLayout;
    /////////upto First Adapter //////////
    //rachan let this be..
    /////////Second Adapter//////////
    public static RecyclerView recyclerView1;
    RecyclerView recyclerView2;
    RecyclerView recyclerView3;
    /////////upto Second Adapter//////////

    private OnFragmentInteractionListener mListener;

    public static AutoScrollViewPager scrollviewPager;
    public static CircleIndicator1 circle;

    String[] header;
    public DiscountTab() {
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.discount_tab, container, false);
        //jsonPassing();
        /////////First Adapter//////////

      /*  scrollviewPager = (AutoScrollViewPager) view.findViewById(R.id.view_pager);
        circle = (CircleIndicator1) view.findViewById(R.id.indicator);*/

        jsonPassing();
        recyclerView=(RecyclerView)view.findViewById(R.id.myList);
        LinearLayoutManager llm = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(llm);

        adapter = new DiscountAdapterOne(mAssignVariableList);
        adapter.notifyDataSetChanged();
        recyclerView.setAdapter(adapter);


        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        /////////upto First Adapter //////////

        /////////Second Adapter//////////
        jsonPassing1();
        recyclerView1 = (RecyclerView) view.findViewById(R.id.recyclerview1);
        LinearLayoutManager llm2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        //LinearLayoutManager llm2 = new LinearLayoutManager(getActivity());
       // llm.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView1.setLayoutManager(llm2);

        adapter2 = new DiscountAdapterTwo(mAssignVariab1);
        adapter2.notifyDataSetChanged();
        recyclerView1.setAdapter(adapter2);



/////////Second Adapter//////////

        jsonPassing2();
        recyclerView2 = (RecyclerView) view.findViewById(R.id.recyclerview2);
        LinearLayoutManager llm3 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        //LinearLayoutManager llm2 = new LinearLayoutManager(getActivity());
        //llm3.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView2.setLayoutManager(llm3);
        adapter3 = new DiscountAdapterThree(mAssignVariab2);
        adapter3.notifyDataSetChanged();
        recyclerView2.setAdapter(adapter3);




        /////////////////////////
        jsonPassing3();
        recyclerView3 = (RecyclerView) view.findViewById(R.id.recyclerview3);
        LinearLayoutManager llm4 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        //LinearLayoutManager llm2 = new LinearLayoutManager(getActivity());
        //llm3.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView3.setLayoutManager(llm4);
        adapter4 = new DiscountAdapterFour(mAssignVariab3);
        adapter4.notifyDataSetChanged();
        recyclerView3.setAdapter(adapter4);




//  Rachan i think this works..
        //up to here rachan
        return view;
    }

    private void jsonPassing3() {

       // String url = "http://10.0.0.247/fvnphpfiles/lowprice1.php";
        String url = "http://www.vinformax.com/vts/android-pro1/shopping/fvnphpfiles/allsubproducts.php";
        // String url ="http://www.vinformax.com/vts/android-pro1/shopping/fvnphpfiles/masterproducthomepage.php";
       // final DiscountAdapterOne discountAdapterOne = new DiscountAdapterOne(mAssignVariab1);
        RequestQueue requestQueue4 = Volley.newRequestQueue(getActivity());
        requestQueue4.getCache().clear();
        JsonObjectRequest jsonObjectRequest4 = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray ja = response.getJSONArray("Productitems");
                            header = new String[ja.length()];
                            for (int i = 10; i < ja.length(); i++) {
                                AssignVariable assignVariable = new AssignVariable();
                                JSONObject jsonObject = ja.getJSONObject(i);
                                Log.d("VOLLEY111", toString());
                                assignVariable.setProductModelName(jsonObject.getString("item_name"));
                                assignVariable.setProductModelActualPrice(jsonObject.getString("item_price"));
                                assignVariable.setProductModelDiscountPrice(jsonObject.getString("item_discounts"));
                                assignVariable.setProductImage(Allurls.url_allimagedirectory+jsonObject.getString("item_image"));
                                //assignVariable.age = jsonObject.getInt("item_image");
                                mAssignVariab3.add(assignVariable);

                               /* VinMartSqlLite vinMartSqlLite = new VinMartSqlLite(getActivity());
                                vinMartSqlLite.storedata(assignVariable.productModelName,assignVariable.productModelActualPrice,assignVariable.getProductModelDiscountPrice());
*/
                            }
                            adapter4.notifyDataSetChanged();

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

        requestQueue4.add(jsonObjectRequest4);
    }

    private void jsonPassing2() {
        //String url = "http://10.0.0.247/fvnphpfiles/topdiscount.php";
        String url = "http://www.vinformax.com/vts/android-pro1/shopping/fvnphpfiles/allsubproducts.php";
        // String url ="http://www.vinformax.com/vts/android-pro1/shopping/fvnphpfiles/masterproducthomepage.php";
        //final DiscountAdapterOne discountAdapterOne = new DiscountAdapterOne(mAssignVariableList);
        RequestQueue requestQueue3 = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest3 = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray ja = response.getJSONArray("Productitems");
                            header = new String[ja.length()];
                            for (int i = 20; i < 50; i++) {
                                AssignVariable assignVariable = new AssignVariable();
                                JSONObject jsonObject = ja.getJSONObject(i);
                                Log.d("VOLLEY111", toString());
                                assignVariable.setProductModelName(jsonObject.getString("item_name"));
                                assignVariable.setProductModelActualPrice(jsonObject.getString("item_price"));
                                assignVariable.setProductModelDiscountPrice(jsonObject.getString("item_discounts"));
                                assignVariable.setProductImage(Allurls.url_allimagedirectory+jsonObject.getString("item_image"));
                                //assignVariable.age = jsonObject.getInt("item_image");
                                mAssignVariab2.add(assignVariable);
                            }
                            adapter3.notifyDataSetChanged();

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

        requestQueue3.add(jsonObjectRequest3);
    }

    private void jsonPassing1() {
      //  String url = "http://10.0.0.247/fvnphpfiles/lowprice2.php";
        String url = "http://www.vinformax.com/vts/android-pro1/shopping/fvnphpfiles/allsubproducts.php";
        // String url ="http://www.vinformax.com/vts/android-pro1/shopping/fvnphpfiles/masterproducthomepage.php";
        //final DiscountAdapterTwo recyclerViewAdapter = new DiscountAdapterTwo(mAssignVariab1);
        RequestQueue requestQueue2 = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray ja = response.getJSONArray("Productitems");
                            header = new String[ja.length()];
                            for (int i = 30; i < 62; i++) {
                                AssignVariable assignVariable = new AssignVariable();
                                JSONObject jsonObject = ja.getJSONObject(i);
                                Log.d("VOLLEY111", toString());
                                assignVariable.setProductModelName(jsonObject.getString("item_name"));
                                assignVariable.setProductModelActualPrice(jsonObject.getString("item_price"));
                                assignVariable.setProductModelDiscountPrice(jsonObject.getString("item_discounts"));
                                assignVariable.setProductImage(Allurls.url_allimagedirectory+jsonObject.getString("item_image"));
                                mAssignVariab1.add(assignVariable);
                            }
                            adapter2.notifyDataSetChanged();

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

        requestQueue2.add(jsonObjectRequest2);
    }

    private void jsonPassing() {
       // String url = "http://10.0.0.247/fvnphpfiles/lowprice.php";
        String url = "http://www.vinformax.com/vts/android-pro1/shopping/fvnphpfiles/allsubproducts.php";
        // String url ="http://www.vinformax.com/vts/android-pro1/shopping/fvnphpfiles/masterproducthomepage.php";
       // final DiscountAdapterOne discountAdapterOne = new DiscountAdapterOne(mAssignVariableList);
        RequestQueue requestQueue1 = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest1 = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray ja = response.getJSONArray("Productitems");
                            header = new String[ja.length()];
                            for (int i = 40; i < 85; i++) {
                                AssignVariable assignVariable = new AssignVariable();

                                JSONObject jsonObject = ja.getJSONObject(i);
                                Log.d("VOLLEY111", toString());
                                assignVariable.setProductModelName(jsonObject.getString("item_name"));
                                assignVariable.setProductModelActualPrice(jsonObject.getString("item_price"));
                                assignVariable.setProductModelDiscountPrice(jsonObject.getString("item_discounts"));
                                assignVariable.setProductImage(Allurls.url_allimagedirectory+jsonObject.getString("item_image"));
                            mAssignVariableList.add(assignVariable);


                            }
                            adapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        // Toast.makeText(getActivity(), "Error in Connection", Toast.LENGTH_LONG).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return super.getParams();
            }
        };

        requestQueue1.add(jsonObjectRequest1);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onStart() {
        super.onStart();


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
       /* jsonPassing();
        jsonPassing1();
        jsonPassing2();
        jsonPassing3();*/

    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(true);
            }
        });

       onResume();


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
