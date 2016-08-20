package vinformax.vinmart.common;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

import vinformax.vinmart.R;
import vinformax.vinmart.database.VinMartSqlLite;
import vinformax.vinmart.model.UserData;
import vinformax.vinmart.product.Favorite;
import vinformax.vinmart.product.ProductCart;
import vinformax.vinmart.product.ProductScreen;
import vinformax.vinmart.recycleviewadapter.TileContentFragment;
import vinformax.vinmart.user.LoginScreen;
import vinformax.vinmart.user.MyProfile;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TabLayout.OnTabSelectedListener {
    ///////////////////////
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    static MenuItem action_search;

    ////////////////
    private TabLayout tabLayout;

    String username,usermail;

    public static Context c;
    public static Context conApplication;

    //This is our viewPager
    private ViewPager viewPager;
    VinMartSqlLite vinMartSqlLite;
    List<UserData>userdata = new ArrayList<>();
    TextView nav_username,nav_mailid;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        c = MainActivity.this;
        conApplication = getApplicationContext();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tabLayout = (TabLayout) findViewById(R.id.tblHomeads);



        //Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Hot Deals"));
        tabLayout.addTab(tabLayout.newTab().setText("Discounts"));

        //tabLayout.addTab(tabLayout.newTab().setText("Categories"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //Initializing viewPager
        viewPager = (ViewPager) findViewById(R.id.homeViewpager);
        viewPager.setOffscreenPageLimit(2);
        //Creating our pager adapter
        Pager adapter = new Pager(getSupportFragmentManager(), tabLayout.getTabCount());
        //Adding adapter to pager
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(conApplication,ProductCart.class);
                startActivity(intent);
                Snackbar.make(view, "Happy shopping With VinMart", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);

        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        setupDrawerContent(navigationView);
        navigationView.setItemTextColor(ColorStateList.valueOf(Color.BLACK));

        View navView = LayoutInflater.from(this).inflate(R.layout.nav_header_main, navigationView);
        ImageView nav_userimage =(ImageView)navView.findViewById(R.id.nav_userimage);
        nav_username =(TextView)navView.findViewById(R.id.nav_username);
        nav_mailid =(TextView)navView.findViewById(R.id.nav_mailid);
        VinMartSqlLite vinMartSqlLite = new VinMartSqlLite(MainActivity.this);
        List<UserData> getUserDetails = new ArrayList<UserData>();
        getUserDetails = vinMartSqlLite.getUserDetails();

        if (getUserDetails.size() > 0) {

            username = getUserDetails.get(0).getUsername();
            usermail = getUserDetails.get(0).getUseremail();
            Toast.makeText(MainActivity.this, usermail +username+ "generating null values" , Toast.LENGTH_SHORT).show();
            nav_mailid.setText(usermail);
            nav_username.setText(username);

        }else {
            Log.d("","");
        }



        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {


                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    public void selectDrawerItem(MenuItem menuItem) {
        // Create a new fragment and specify the fragment to show based on nav item clicked
        Fragment fragment = null;
        Class fragmentClass = null;

        switch (menuItem.getItemId()) {
            /*case android.R.id.home:
                onBackPressed();
                return;*/

           case R.id.Address:
               Intent intent = new Intent(conApplication, Addresspage.class);
               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               startActivity(intent);
               return;
            case R.id.menuFavourite:
                Intent intent4 = new Intent(conApplication, Favorite.class);
                intent4.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent4);
                return;
           case R.id.login:
               vinMartSqlLite = new VinMartSqlLite(MainActivity.this);
               userdata = vinMartSqlLite.getUserDetails();
               if(userdata.size()==0){
                   Intent intent2 = new Intent(conApplication, LoginScreen.class);
                   intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   startActivity(intent2);
               }
                else {
                   Toast.makeText(conApplication,"User Already Loged in",Toast.LENGTH_LONG).show();
                   Intent i = new Intent(conApplication,MyProfile.class);
                   i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                   startActivity(i);
                   return;

               }
                return;

            case R.id.menuCategories:
                Intent i = new Intent(conApplication, TileContentFragment.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                return;

           /* case R.id.action_share:
               share();

                break;*/


            default:

        }

        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

////////////////////////
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, fragment).addToBackStack(null).commit();
        getSupportFragmentManager().getFragments().size();
        Log.d("numfrag", toString());// check counts of fragments
        // Highlight the selected item has been done by NavigationView
        menuItem.setChecked(true);
        // Set action bar title

        setTitle(menuItem.getTitle());

        // Close the navigation drawer
        drawer.closeDrawers();
    }

        @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.


        try {
            getMenuInflater().inflate(R.menu.main, menu);

            /*SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            search  = (MenuItem)menu.findItem(R.id.action_search);
            android.support.v7.widget.SearchView searchView = (android.support.v7.widget.SearchView) search.getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));*/

            SearchManager searchManager = (SearchManager) getSystemService(c.SEARCH_SERVICE);
            MenuItem menuItem = menu.findItem(R.id.action_search);
            SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

            //action_search = (MenuItem) menu.findItem(R.id.action_search);
            // SearchView searchView1 = (SearchView) MenuItemCompat.getActionView(action_search);
            //searchView1.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

            searchView.setBackgroundColor(Color.TRANSPARENT);
            searchView.setQueryHint(Html.fromHtml("<font color = #ffffff>" + getResources().getString(R.string.search_hint) + "</font>"));
            action_search.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    // TODO Auto-generated method stub
                    onSearchRequested();
                    return true;
                }
            });
        } catch (Exception e) {
            Log.d("sonn",e.getMessage());
          /*  Toast.makeText(MainActivity.this, "error - server not responding" + e.getMessage().toString(),
                    Toast.LENGTH_LONG).show();*/


            e.printStackTrace();
        }

        //return super.onCreateOptionsMenu(menu);
return true;
    }
    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        setIntent(intent);
        handleIntent(intent);
        super.onNewIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            /**
             * Use this query to display search results like
             * 1. Getting the data from SQLite and showing in listview
             * 2. Making webrequest and displaying the data
             * For now we just display the query only
             */
            //Toast.makeText(getApplicationContext(), query, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(GravityCompat.START);
                break;

            case R.id.action_settings:
                Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT)
                        .show();
                break;
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
                loginstatus();

                break;
            case R.id.action_add_favourite:
                Toast.makeText(this, "action cart selected", Toast.LENGTH_SHORT)
                        .show();
                Intent i = new Intent(conApplication,Favorite.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                //share();
                return true;
            case R.id.action_search:
                Toast.makeText(this, "action search selected", Toast.LENGTH_SHORT)
                        .show();
                onSearchRequested();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void loginstatus() {
    }
    @Override
    public boolean onSearchRequested() {
        Bundle appData = new Bundle();
        appData.putString("hello", "world");
        startSearch(null, false, appData, false);
        return true;
    }

    private void share() {

        final String shareBody = "Get Set Go in Just One day • Ready-made shopping application for all verticals • App can be flexible for B2B and B2C  vinMart - https://play.google.com/store/apps/details?id=vinmart.shopping&hl=en";

        //inviteCall();
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "APP NAME (Open it in Google Play Store to Download the Application)");

        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
/*
        switch (item.getItemId()) {
            case android.R.id.home:
//                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

            case R.id.menuHomeAds:
                displayView(0);
                return true;

            case R.id.menuCategories:

                displayView(1);
                return true;

            case R.id.menuFavourite:
                displayView(2);
                return true;

            case R.id.menuCart:
                displayView(5);
                return true;

            case R.id.Products:
                displayView(3);
                return true;

            case R.id.menuAboutUs:

                return false;

            case R.id.menuAcount:
                displayView(7);
                return true;

            case R.id.menuAddress:
                displayView(8);
                return true;
           *//* case R.id.share:
                displayView(9);
                return true;*//*

        }
        item.setChecked(true);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);*/
        return true;
    }

    private void displayView(int position) {
        // update the main content by replacing fragments
        Fragment fragment = null;
        Class fragmentClass;
        Intent intent;
        Activity a = null;

        switch (position) {
            case 0:
                a = new MainActivity();
                break;
            case 1:
                intent = new Intent(MainActivity.this, ProductScreen.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;
            case 2:
               // fragment = new MyProfile();
                break;
            case 3:
//               // fragment = new LoginScreen();
//                loginstatus();
                //fragment = new MyProfile();
                break;
            case 4:

                //fragment = new MyProfile();
                break;
            case 5:
                //fragment = new MyProfile();
                break;
            case 6:
               // fragment = new MyProfile();
                break;

            case 7:

              //  fragment = new MyProfile();
                break;

            case 8:
                //fragment = new MyProfile();
                break;
            case 9:
               // fragment = new MyProfile();
                break;

            default:

                break;
        }
    }

        @Override
        public void onTabSelected (TabLayout.Tab tab){
            viewPager.setCurrentItem(tab.getPosition());
        }
        @Override
        public void onTabUnselected (TabLayout.Tab tab){
        }

        @Override
        public void onTabReselected (TabLayout.Tab tab){
        }

}