package vinformax.vinmart.common;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import vinformax.vinmart.R;
import vinformax.vinmart.database.VinMartSqlLite;
import vinformax.vinmart.model.UserData;
import vinformax.vinmart.service.ApplocationService;

public class Addresspage extends AppCompatActivity {

    ApplocationService applocationService;
    EditText etcontactName, etcountry, etaddressOne, etaddressTwo, city, etstate, etpinCode, etmobileNumber, etemailId;
    String Name, Country, Addressone, Addresstwo, City, State, pincode, Mobilenumber, Email;
    Button Addresbtn;
    Context context;
    static int Result;
    TelephonyManager tMgr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresspage);
        context = this.getApplicationContext();
        applocationService = new ApplocationService(context);
        applocationService.getLocation();
        applocationService.getLatitude();
        applocationService.getLongitude();
        applocationService.isGpsTrackingEnabled();

        applocationService.getAddressLine(context);
        applocationService.getState(context);
        applocationService.getCountry(context);
        applocationService.getPostalcode(context);
        applocationService.getCity(context);
        applocationService.getSublocality(context);

        //etcontactName,etcountry,etaddressOne,etaddressTwo,city,etstate,etpinCode,etmobileNumber,etemailId
        Addresbtn = (Button) findViewById(R.id.Address_btn);
        etcontactName = (EditText) findViewById(R.id.etcontactName);
        etcountry = (EditText) findViewById(R.id.etcountry);
        etaddressOne = (EditText) findViewById(R.id.etaddressOne);
        etaddressTwo = (EditText) findViewById(R.id.etaddressTwo);
        city = (EditText) findViewById(R.id.city);
        etstate = (EditText) findViewById(R.id.etstate);
        etpinCode = (EditText) findViewById(R.id.etpinCode);
        etmobileNumber = (EditText) findViewById(R.id.etmobileNumber);
        etemailId = (EditText) findViewById(R.id.etemailId);

        Country = applocationService.getCountry(this);
        etcountry.setText(Country);

        Addressone = applocationService.getAddressLine(this);
        etaddressOne.setText(Addressone);

        Addresstwo = applocationService.getSublocality(this);
        etaddressTwo.setText(Addresstwo);

        City = applocationService.getCity(this);
        city.setText(City);


        State = applocationService.getState(this);
        etstate.setText(State);

        pincode = etpinCode.getText().toString();

        tMgr= (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        String mPhoneNumber = tMgr.getLine1Number();
        if (mPhoneNumber != null){
            etmobileNumber.setText(mPhoneNumber);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        UserDetails();
    }

    public void UserDetails() {

        try {
            VinMartSqlLite vinMartSqlLite = new VinMartSqlLite(Addresspage.this);
            List<UserData> getUserDetails = new ArrayList<UserData>();
            getUserDetails = vinMartSqlLite.getUserDetails();

            if (getUserDetails.size() > 0) {

                Name = getUserDetails.get(0).getUsername();
                etcontactName.setText(Name);

                Email = getUserDetails.get(0).getUseremail();
                etemailId.setText(Email);

            }

        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }

        Addresbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddAddress();

            }

            private void AddAddress() {
                VinMartSqlLite vinmartsqllite = new VinMartSqlLite(context);
                Result = vinmartsqllite.AddaddressDetails(Name,Country,Addressone,Addresstwo,City,State,pincode,Mobilenumber,Email);
                if (Result == 1) {
                    Toast.makeText(Addresspage.this, "added to Addresspage", Toast.LENGTH_SHORT).show();

                } else if (Result == -1) {
                    Toast.makeText(Addresspage.this, "failed", Toast.LENGTH_SHORT).show();
                } else if(Result==0) {
                    Toast.makeText(Addresspage.this, "Address Already Added", Toast.LENGTH_SHORT).show();
                }
                else  {
                    Toast.makeText(Addresspage.this, "try again", Toast.LENGTH_SHORT).show();
                }
            }
        });

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
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}