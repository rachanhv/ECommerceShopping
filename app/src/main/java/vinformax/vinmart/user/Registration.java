package vinformax.vinmart.user;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vinformax.vinmart.R;
import vinformax.vinmart.encryption.Encryptionalg;
import vinformax.vinmart.service.Allurls;
import vinformax.vinmart.service.Checknetworkconnection;
import vinformax.vinmart.service.Forgetpasswordmail;
import vinformax.vinmart.snackbar.SnackBar;


public class Registration extends AppCompatActivity {

    EditText Usr, Pwd, Eid, PhNo, cnpwd;
    Button Sub, Clr;
    SQLiteDatabase sb;
    String Username, UserType,Password, EmailID, PhoneNo, confrmpwd, uid;
    Spinner spinner;
    public static List<NameValuePair> param = new ArrayList<NameValuePair>();
    Encryptionalg encalo;
    public static boolean check = false;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.registration);
        cnpwd = (EditText) findViewById(R.id.txtconfirmpwd);
        Usr = (EditText) findViewById(R.id.txt_username);
        Pwd = (EditText) findViewById(R.id.txtpassword);
        Eid = (EditText) findViewById(R.id.txtemail);
        PhNo = (EditText) findViewById(R.id.txtphoneno);
        Sub = (Button) findViewById(R.id.btnsub);
        Clr = (Button) findViewById(R.id.btnclear);

        encalo = new Encryptionalg();

        Sub.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(final View arg0) {
                // TODO Auto-generated method stub
                InputMethodManager inputManager = (InputMethodManager) Registration.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(arg0.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                boolean cnchk = Checknetworkconnection.isConnectingToInternet(Registration.this);
                if (cnchk == true) {
                    RequestQueue queue = Volley.newRequestQueue(Registration.this);
                    StringRequest stringRequest = new StringRequest(Request.Method.GET, Allurls.url_addexeoruserID_todb,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    //  Toast.makeText(Registration.this, "Response is: "+ response.substring(0,1000), Toast.LENGTH_SHORT).show();
                                    //response =response.substring(0,1000);
                                    String numberOnly = response.replaceAll("[^0-9]", "");
                                    if (!numberOnly.equals("")) {
                                        long sumid = Integer.parseInt(numberOnly) + 1;
                                        response = response.replaceAll("[^A-Za-z]", "");
                                        response = response + String.valueOf(sumid);
                                        uid = response;
                                    } else {
                                        response = "1000";
                                    }

                                    uid =response;
                                    Username = Usr.getText().toString();

                                    Password = Pwd.getText().toString().trim();
                                    confrmpwd = cnpwd.getText().toString().trim();
                                    EmailID = Eid.getText().toString().trim();
                                    PhoneNo = PhNo.getText().toString().trim();
                                    if (Username == null || Username.trim().isEmpty() ||
                                            Password == null || Password.trim().isEmpty() ||
                                            confrmpwd == null || confrmpwd.trim().isEmpty() ||
                                            EmailID == null || EmailID.trim().isEmpty() ||
                                            PhoneNo == null || PhoneNo.trim().isEmpty()) {



                                        Usr.setError("Invalid User Name");
                                        Pwd.setError("Invalid Password ");
                                        cnpwd.setError("Invalid Conform Password");
                                        Eid.setError("Invalid Email Id");
                                        PhNo.setError("Invalid Phone Number");
                                        //Toast.makeText(getApplicationContext(), "Error..Field is empty..", 3000).show();
                                    } else

                                    {
                                        boolean invalidNote = true;
                                        final String email = Eid.getText().toString();
                                        final String pass = Pwd.getText().toString();
                                        final String mno = PhNo.getText().toString();
                                        if (!isusername(Username)) {
                                            Usr.setError("Invalid Username");
                                            invalidNote = false;
                                        }

                                        if (!isValidPassword(pass)) {
                                            Pwd.setError("Invalid Password (*Min 5 Char)");
                                            invalidNote = false;
                                        }
                                        if (!isValidEmail(email)) {
                                            Eid.setError("Invalid Email");
                                            invalidNote = false;
                                        }
                                        if (!isValidPhonenumber(mno)) {
                                            PhNo.setError("Invalid phone number");
                                            invalidNote = false;
                                        }
                                        if (invalidNote == true) {

                                            if (Password.equals(confrmpwd)) {

                                                try {


                                                    confrmpwd = encalo.encrypt(confrmpwd);
                                                } catch (Exception e) {
                                                    // TODO Auto-generated catch block
                                                    e.printStackTrace();
                                                }

                                                param.add(new BasicNameValuePair("userorexeid", uid));
                                                param.add(new BasicNameValuePair("username", Username));
                                                param.add(new BasicNameValuePair("password", confrmpwd));
                                                param.add(new BasicNameValuePair("mailid", EmailID));
                                                param.add(new BasicNameValuePair("phoneno", PhoneNo));
                                                param.add(new BasicNameValuePair("usertype", "users"));
                                                param.add(new BasicNameValuePair("status", "A"));

                                                new Registeraysntask(arg0).execute();

                                            } else {
                                                cnpwd.setError("Confirm Password do not match !");
                                            }

                                        }

                                    }
                                    // Display the first 500 characters of the response string.
                                    // mTextView.setText("Response is: "+ response.substring(0,500));
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Registration.this,"That didn't work!", Toast.LENGTH_SHORT).show();

                        }
                    });
                    queue.add(stringRequest);
                } else {

//                    Toast.makeText(Registration.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    new SnackBar(arg0, "Please check your internet connection");
                }
            }
        });



        Clr.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                InputMethodManager inputManager = (InputMethodManager) Registration.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(arg0.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                Usr.setText("");
                Pwd.setText("");
                Eid.setText("");
                PhNo.setText("");
                cnpwd.setText("");
            }
        });

    }




    private boolean isusername(String unme) {
        String uname_pattern = "^[A-Za-z0-9 ]{2,30}$";
        Pattern pattern = Pattern.compile(uname_pattern);
        Matcher matcher = pattern.matcher(unme);
        return matcher.matches();


    }


    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,3})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() >= 5) {
            return true;
        }
        return false;
    }

    private boolean isValidPhonenumber(String pno) {
        if (pno.length() == 10) {
            return true;
        }
        return false;
    }


    String regresult;
    final static String TAG = "JsonParser.java";
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";

    public class Registeraysntask extends AsyncTask<String, String, String> {

        View view;

        Registeraysntask(View v) {
            view = v;
        }

        final String TAG = "AsyncTaskParseJson.java";

        // set your json string url here
        String yourJsonStringUrl = Allurls.url_register;

        // contacts JSONArray
        JSONArray dataJsonArr = null;

        ProgressBar pb = (ProgressBar) findViewById(R.id.progressBar1);


        @Override
        protected void onPreExecute() {
            pb.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(String... arg0) {

            try {

                regresult = getJSONFromUrl(yourJsonStringUrl);

                if (regresult.trim().equals("Successfully")) {
                    String mailConformation = "Hello" + LoginScreen.loginst + "Welcome " + Username + " you have successfully registered..\n"
                            + "Your credentials:\n" + "Email id - " + EmailID + "\nPassword - " + cnpwd.getText().toString() + "\nPhone No - " + PhoneNo
                            + "\n\n\n Please click below to verify and activate your account \n\n" +
                            Allurls.url_verifiedmailtologin + "?mailid=" + EmailID + "\n\nIn case of any other problems with activation," +
                            " please contact us at androidt763@gmail.com\n\nHappy Shopping...\n\nBest regards,\nAndroid team\nVinformax Technology System.";
                    EmailID = Eid.getText().toString().trim();
                    Forgetpasswordmail mail = new Forgetpasswordmail();
                    mail.Forgetpass(EmailID, mailConformation);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String strFromDoInBg) {

            pb.setVisibility(View.GONE);
            regresult = regresult.trim();
            if (regresult.equals("Successfully registered,please confirm through Your Mail")) {
//                Toast.makeText(Registration.this, "successfully Registered", 3000).show();
                check = true;
//                new SnackBar(view, "successfully Registered");
//                Intent login = new Intent(Registration.this, LoginScreen.class);
                onBackPressed();
            } else if (regresult.equals("Email id already exists")) {
//                Toast.makeText(Registration.this, "Email id already exists", 3000).show();
                new SnackBar(view, "Email id already exists");
            } else {
//                Toast.makeText(Registration.this, "Registered Failed", 3000).show();
                check = true;
//                new SnackBar(view, "successfully Registered");
//                Intent login = new Intent(Registration.this, LoginScreen.class);
                onBackPressed();
            }
        }
    }


    public String getJSONFromUrl(String url) throws JSONException {
        String result = "";
        // make HTTP request
        try {
            DefaultHttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(param));
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            is = httpEntity.getContent();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {

            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "");
            }
            is.close();
            result = sb.toString();

        } catch (Exception e) {
            Log.e(TAG, "Error converting result " + e.toString());
        }

        // return JSON String
        param.clear();
        return result;
    }


    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
//    	Intent mm=new Intent(Registration.this, LoginScreen.class);
//    	startActivity(mm);
        super.onBackPressed();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
