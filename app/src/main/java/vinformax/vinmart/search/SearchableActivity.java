package vinformax.vinmart.search;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import vinformax.vinmart.common.SplashScreensActivity;
import vinformax.vinmart.product.ProductScreen;

public class SearchableActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    private Uri mUri;

    private TextView mTvName;
    String prices, discount, imageurl, itemid, actual_price, searchAdsItemQuantity;
    public static Context main;
    String cartprdname;
    Intent intent;
    int qtyget;
    //AssignVariable assignVariable =new AssignVariable();
    SplashScreensActivity splashScreensActivity = new SplashScreensActivity();
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        intent = getIntent();
        mUri = intent.getData();

        main = SearchableActivity.this;
        getSupportLoaderManager().initLoader(0, null, this);
        Bundle appData = getIntent().getBundleExtra(SearchManager.APP_DATA);
        if (appData != null) {
            String hello = appData.getString("hello");
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int arg0, Bundle data) {

        if (mUri != null) {
            return new CursorLoader(getBaseContext(), mUri, null, null, null, null);
        } else {

            String query = intent.getStringExtra(SearchManager.QUERY);

            doSearch(query);

        }

        finish();
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> arg0, Cursor cursor) {
        if (cursor.moveToFirst()) {

            cartprdname = cursor.getString(cursor.getColumnIndex(cursor.getColumnName(1)));


            if (splashScreensActivity.prdimgname != null) {
                for (int i = 0; i < splashScreensActivity.prdimgname.length; i++) {
                    if (cartprdname.equals(splashScreensActivity.prdimgname[i])) {
                        prices = splashScreensActivity.price[i];
                        discount = splashScreensActivity.discounts[i];
                        imageurl = splashScreensActivity.imgurlname[i];
                        itemid = splashScreensActivity.prditemid[i];
                        actual_price = splashScreensActivity.actualAmount[i];
                        String ss = searchAdsItemQuantity = splashScreensActivity.itemquantity[i];
                        qtyget = splashScreensActivity.hm.get(itemid);
                        // emailid = Assignallvarible.emailid[i];
                    }
                }
            }

            Intent intent = new Intent(SearchableActivity.this, ProductScreen.class);
            Bundle bundle = new Bundle();

            bundle.putString("Context", "SearchableActivity");
            bundle.putString("vPName", cartprdname);
            bundle.putString("vPDiscountPrice", discount);
            bundle.putString("vpActualPrice", actual_price);
            bundle.putString("vProductImage", imageurl);
            Log.d("first", imageurl);


          /*  bundle.putString("Context", "SearchableActivity");
            bundle.putString("subiditem", itemid);
            bundle.putString("imggetid", imageurl);
            bundle.putString("qtyget", qtyget + "");
            bundle.putString("cartprdname", cartprdname);
            bundle.putString("price", prices);
            bundle.putString("discount", discount);
            bundle.putString("Actualprice", actual_price);
            bundle.putString("alertItemQuantity", "" + Integer.parseInt(searchAdsItemQuantity));*/
            intent.putExtras(bundle);
            startActivity(intent);
            finish();
        }
    }

    private void doSearch(String query) {
        Bundle data = new Bundle();
        data.putString("query", query);
        String ll = String.valueOf(query.charAt(0));
        String store = ll.toUpperCase();
        ll = query.replace(String.valueOf(query.charAt(0)), "");
        store = store + ll;
        int k = 0;

        if (splashScreensActivity.prdimgname != null) {

            for (int i = 0; i < splashScreensActivity.prdimgname.length; i++) {

                if (query.equals(splashScreensActivity.prdimgname[i]) || store.equals(splashScreensActivity.prdimgname[i])) {
                    prices = splashScreensActivity.price[i];
                    discount = splashScreensActivity.discounts[i];
                    imageurl = splashScreensActivity.imgurlname[i];
                    searchAdsItemQuantity = splashScreensActivity.itemquantity[i];
                    actual_price = splashScreensActivity.actualAmount[i];
                    itemid = splashScreensActivity.prditemid[i];
                    qtyget = splashScreensActivity.hm.get(itemid);
                    k++;


                    Intent intent = new Intent(SearchableActivity.this, ProductScreen.class);
                    Bundle bundle = new Bundle();

                    bundle.putString("Context", "SearchableActivity");
                    bundle.putString("vPName", cartprdname);
                    bundle.putString("vPDiscountPrice", discount);
                    bundle.putString("vpActualPrice", actual_price);
                    bundle.putString("vProductImage", imageurl);
                    Log.d("first", imageurl);

                   /* bundle.putString("Context", "SearchableActivity");
                    bundle.putString("subiditem", itemid);
                    bundle.putString("imggetid", imageurl);
                    bundle.putString("qtyget", qtyget + "");
                    bundle.putString("cartprdname", cartprdname);
                    bundle.putString("price", prices);
                    bundle.putString("discount", discount);
                    bundle.putString("Actualprice", actual_price);
                    bundle.putString("alertItemQuantity", "" + Integer.parseInt(searchAdsItemQuantity));*/
                    intent.putExtras(bundle);
                    startActivity(intent);
                    finish();

                }
            }
        }
        if (k == 0) {
            Toast.makeText(SearchableActivity.this, "No items", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public void onLoaderReset(Loader<Cursor> arg0) {
        // TODO Auto-generated method stub

    }
}