package vinformax.vinmart.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import vinformax.vinmart.R;
import vinformax.vinmart.recycleviewadapter.FavoriteAdapter;

/**
 * Created by Mobtech-01 on 09-Aug-16.
 */
public class Favorite extends AppCompatActivity {
    RecyclerView recyclerView;
    Context con;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fav_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        con=this.getApplicationContext();
        recyclerView= (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        FavoriteAdapter adapter=new FavoriteAdapter(this);

        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        recyclerView.setHasFixedSize(true);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.myprofile:
                Toast.makeText(this, "myprofile selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.status:
                Toast.makeText(this, "status selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.Logout:
                Toast.makeText(this, "Logout selected", Toast.LENGTH_SHORT)
                        .show();
                break;
            case R.id.action_add_favourite:
                Toast.makeText(this, "action cart selected", Toast.LENGTH_SHORT)
                        .show();
                Intent i = new Intent(Favorite.this,Favorite.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                //share();
                return true;
               }

        return super.onOptionsItemSelected(item);
    }
}
