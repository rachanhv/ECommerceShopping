/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package vinformax.vinmart.recycleviewadapter;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import vinformax.vinmart.R;
import vinformax.vinmart.model.CatModel;

/**
 * Provides UI for the view with Tile.
 */
public class TileContentFragment extends Activity {
    private ContentAdapter adapter;
    private RecyclerView mRecyclerView;
    private List<CatModel> feedItemList = new ArrayList<CatModel>();
    private static final String iurl = "http://www.vinformax.com/vts/android-pro1/shopping/category_images/";
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_view);
        mRecyclerView = (RecyclerView)findViewById(R.id.my_recycler_view);
//        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
//        recyclerView.setAdapter(adapter);
        mRecyclerView.setHasFixedSize(true);
        // Set padding for Tiles
        int tilePadding = getResources().getDimensionPixelSize(R.dimen.tile_padding);
        mRecyclerView.setPadding(tilePadding, tilePadding, tilePadding, tilePadding);
        final String url = "http://www.vinformax.com/vts/android-pro1/shopping/fvnphpfiles/mastertype.php";

        new AsyncHttpTask().execute(url);
       // recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

    }


//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//    }


    public class AsyncHttpTask extends AsyncTask<String, Void, Integer> {

        @Override
        protected void onPreExecute() {
//            setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected Integer doInBackground(String... params) {
            InputStream inputStream = null;
            Integer result = 0;
            HttpURLConnection urlConnection = null;

            try {
                /* forming th java.net.URL object */
                URL url = new URL(params[0]);

                urlConnection = (HttpURLConnection) url.openConnection();

                /* for Get request */
                urlConnection.setRequestMethod("GET");

                int statusCode = urlConnection.getResponseCode();

                /* 200 represents HTTP OK */
                if (statusCode == 200) {

                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }

                    parseResult(response.toString());
                    result = 1; // Successful
                } else {
                    result = 0; //"Failed to fetch data!";
                }

            } catch (Exception e) {
                //Log.d(TAG, e.getLocalizedMessage());
            }

            return result; //"Failed to fetch data!";
        }

        @Override
        protected void onPostExecute(Integer result) {

//            setProgressBarIndeterminateVisibility(false);

            /* Download complete. Lets update UI */
            if (result == 1) {
                adapter = new ContentAdapter(TileContentFragment.this, feedItemList);
                mRecyclerView.setAdapter(adapter);
                mRecyclerView.setLayoutManager(new GridLayoutManager(TileContentFragment.this, 2));
            } else {
                Log.e("tag", "Failed to fetch data!");
            }
        }
    }
    private void parseResult(String result) {
        try {
            // setProgressBarIndeterminateVisibility(false);
            JSONObject response = new JSONObject(result);
            JSONArray posts = response.optJSONArray("Typeid");

            /*Initialize array if null*/
           if (null == feedItemList) {
                feedItemList = new ArrayList<CatModel>();
            }

            for (int i = 0; i < 2; i++) {
                JSONObject post = posts.optJSONObject(i);

                CatModel item = new CatModel();
                item.setTypeid(post.optString("type_id"));
                item.setImgname(post.optString("type_name"));

                item.setImgurl(iurl + post.optString("image_name"));

                feedItemList.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
    /**
     * Adapter to display recycler view.
     */
    public static class ContentAdapter extends RecyclerView.Adapter<FeedListRowHolder> {
        // Set numbers of Tiles in RecyclerView.
        private static final int LENGTH = 2;
        private List<CatModel> feedItemList;

        private Context mContext;

//        public ContentAdapter(Context context) {
//            Resources resources = context.getResources();
//            mPlaces = resources.getStringArray(R.array.places);
//            TypedArray a = resources.obtainTypedArray(R.array.places_picture);
//            mPlacePictures = new Drawable[a.length()];
//            for (int i = 0; i < mPlacePictures.length; i++) {
//                mPlacePictures[i] = a.getDrawable(i);
//            }
//            a.recycle();
//        }
        public ContentAdapter(Context context,List<CatModel> feedItemList) {
            this.feedItemList = feedItemList;
            this.mContext = context;

        }

        @Override
        public FeedListRowHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tile, null);
            FeedListRowHolder mh = new FeedListRowHolder(v);
            return mh;
        }

        @Override
        public void onBindViewHolder(final FeedListRowHolder feedListRowHolder, int i) {
            CatModel feedItem = feedItemList.get(i);
            for(i=0;i<2;i++) {
                String bsjabdk = String.valueOf(Html.fromHtml(feedItem.getimgname()));

                feedListRowHolder.title.setText(Html.fromHtml(feedItem.getimgname()));

                Picasso.with(mContext).load(feedItem.getimgurl())
                        .error(R.drawable.ic_launcher)
                        .placeholder(R.drawable.ic_launcher)
                        .into(feedListRowHolder.thumbnail);
            }
        }

        @Override
        public int getItemCount() {
            return LENGTH;
        }
    }
}