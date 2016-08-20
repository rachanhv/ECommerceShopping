package vinformax.vinmart.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import vinformax.vinmart.recycleviewadapter.CategoryAdapter;
import vinformax.vinmart.R;
import vinformax.vinmart.model.AssignVariable;
import vinformax.vinmart.recycleviewadapter.DiscountAdapterOne;
import vinformax.vinmart.service.Allurls;

public class CategoriesTab extends Fragment {
    static CategoryAdapter categoryAdapter;
    private final List<AssignVariable> mAssignVariableList10 = new ArrayList<>();

    public static RecyclerView recyclerView5;
    private OnFragmentInteractionListener mListener;
    String[]header;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.categories_tab, container, false);

        recyclerView5 = (RecyclerView) view.findViewById(R.id.lst_items11);

        jsonimages();
        //GridLayoutManager llm3 = new GridLayoutManager(getActivity(), GridLayoutManager.DEFAULT_SPAN_COUNT);
        LinearLayoutManager llm6 = new LinearLayoutManager(getActivity());
        llm6.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView5.setLayoutManager(llm6);
        categoryAdapter = new CategoryAdapter(mAssignVariableList10);
        recyclerView5.setAdapter(categoryAdapter);
       /* AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(categoryAdapter);
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
        recyclerView5.setAdapter(scaleAdapter);*/
        categoryAdapter.notifyDataSetChanged();

        return view;
    }

    private void jsonimages() {

        String url = "http://10.0.0.247/fvnphpfiles/masterproduct.php";
        // String url ="http://www.vinformax.com/vts/android-pro1/shopping/fvnphpfiles/masterproducthomepage.php";
        final DiscountAdapterOne discountAdapterOne = new DiscountAdapterOne(mAssignVariableList10);
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray ja = response.getJSONArray("Productitems");
                            header = new String[ja.length()];
                            for (int i = 0; i < ja.length(); i++) {
                                AssignVariable assignVariable = new AssignVariable();
                                JSONObject jsonObject = ja.getJSONObject(i);
                                Log.d("VOLLEY111", toString());
                                // int id = Integer.parseInt(jsonObject.optString("id").toString());
                                assignVariable.productModelName = jsonObject.getString("item_name");
                                assignVariable.productModelActualPrice = jsonObject.getString("item_price");
                                //assignVariable.setProductImage(jsonObject.getString("item_image"));
                               assignVariable.setProductImage(Allurls.url_allimagedirectory+jsonObject.getString("item_image"));
                                mAssignVariableList10.add(assignVariable);
                            }
                            discountAdapterOne.notifyDataSetChanged();

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
        return;

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

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

}


///////////////////////////// Dont LOOK AT DOWN////////////////////////////
/*
public class HotDealsTab extends Fragment {
    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    AssignVariable person = new AssignVariable();
    private List<AssignVariable> albumList;

    public HotDealsTab() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.hotdeals_tab, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(getContext(), albumList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareAlbums();
        */
/*try {
            Glide.with(this).load(R.drawable.cover).into((ImageView) view.findViewById(R.id.backdrop));
        } catch (Exception e) {
            e.printStackTrace();
        }*//*

return view;
    }
    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.album1,
                R.drawable.album2,
                R.drawable.album3,
                R.drawable.album4,
                R.drawable.album5,
                R.drawable.album6,
                R.drawable.album7,
                R.drawable.album8,
                R.drawable.album9,
                R.drawable.album10,
                R.drawable.album11};

        AssignVariable a = new AssignVariable("True Romance", 13, covers[0]);
        albumList.add(a);

        a = new AssignVariable("Xscpae", 8, covers[1]);
        albumList.add(a);

        a = new AssignVariable("Maroon 5", 11, covers[2]);
        albumList.add(a);

        a = new AssignVariable("Born to Die", 12, covers[3]);
        albumList.add(a);

        a = new AssignVariable("Honeymoon", 14, covers[4]);
        albumList.add(a);

        a = new AssignVariable("I Need a Doctor", 1, covers[5]);
        albumList.add(a);

        a = new AssignVariable("Loud", 11, covers[6]);
        albumList.add(a);

        a = new AssignVariable("Legend", 14, covers[7]);
        albumList.add(a);

        a = new AssignVariable("Hello", 11, covers[8]);
        albumList.add(a);

        a = new AssignVariable("Greatest Hits", 17, covers[9]);
        albumList.add(a);

        adapter.notifyDataSetChanged();
    }

    */
/**
 * RecyclerView item decoration - give equal margin around grid item
 *//*

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    */
/**
 * Converting dp to pixel
 *//*

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}*/
