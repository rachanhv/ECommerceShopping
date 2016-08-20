package vinformax.vinmart.user;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vinformax.vinmart.R;
import vinformax.vinmart.database.VinMartSqlLite;
import vinformax.vinmart.model.UserData;


/**
 * Created by basker.t on 2/15/2016.
 */
public class MyProfile extends Activity {

    TextView profileName,profileEmail;
    View rootView;
    ActionBar actionBar;
    /*SharedPreferences sharedPreferences = getActivity().getPreferences(0);*/

    Context c;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.myprofile);
      c = MyProfile.this;
      c = getApplicationContext();
      profileName = (TextView)findViewById(R.id.profileName);
      profileEmail=(TextView)findViewById(R.id.profileEmail);
   /*   int phoneBarsHeight = ((WindowManager) this.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getHeight()
              - container.getHeight();*/

//      Toolbar toolbar = (Toolbar)findViewById(R.id.toolbarNavMain);


      VinMartSqlLite vinMartSqlLite = new VinMartSqlLite(MyProfile.this);
      List<UserData> getUserDetails = new ArrayList<UserData>();
      getUserDetails = vinMartSqlLite.getUserDetails();
      if (getUserDetails.size() > 0) {
       String   username = getUserDetails.get(0).getUsername();
          String usermail = getUserDetails.get(0).getUseremail();
          Toast.makeText(MyProfile.this, usermail +username+ "generating null values" , Toast.LENGTH_SHORT).show();
          profileEmail.setText(usermail);
          profileName.setText(username);

      }else {
          Log.d("","");
      }
  }
    /*  @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }*/
}
