package vinformax.vinmart.user;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vinformax.vinmart.R;
import vinformax.vinmart.common.MainActivity;
import vinformax.vinmart.database.VinMartSqlLite;
import vinformax.vinmart.encryption.Encryptionalg;
import vinformax.vinmart.service.Allurls;
import vinformax.vinmart.service.Checknetworkconnection;
import vinformax.vinmart.service.Forgetpasswordmail;
import vinformax.vinmart.service.Httprequestwithparam;
import vinformax.vinmart.service.Singleton;
import vinformax.vinmart.snackbar.SnackBar;

//LoginScreen
public class LoginScreen extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {


    String mailidforgetpwd = "";
    Button S_in, F_pwd;
    ImageView S_up;

    EditText U_name, U_pwd;
    public static LinearLayout mainProgressGif;
    SQLiteDatabase sb;
    static String Login_Usr, Login_Pwd;
    Context context;
    Httprequestwithparam onjparam = new Httprequestwithparam();
    boolean emailerror = false, psserror = false;
    public static GoogleApiClient mGoogleApiClient;
    public static boolean googleLoginStatus;
    private static final int RC_SIGN_IN = 0;
    ImageView btnFbLogin, btnGoogleLogin;
    CoordinatorLayout mainLayout;
    CallbackManager callbackManager;
    String mEmail, mId, id, gender, email, name;
    public static Map<String, Integer> hm = new HashMap<String, Integer>();
    public static ArrayList<String> favlist = new ArrayList<String>();
    public static String carthomecount = "0", loginst = "1", loginemailid = "",
            UserorExecutivesID = "", customerid_cartandfav = "", customer_nameglobal = "", customer_Executive = "",
            userorcustID = "", loginusertype = "", sqllitefav = "", sqllitecart, loginorlogoutstatus = "N";
    public static Context allcon;
    public static int downloadcsv = 0, emp = 0;
    public static String csvfilename = "", currsymbol = "";
    public static Context maincatcontext;
    ImageView image;
    Animation animTogether;
    public static String[] prdimgname, prditemid, group_ids, type_ids, category_ids, itemquantity, actualAmount, status;
    public static String[] imgname, price, imgurlname, discounts;
    public static int retdetails, errorcnt = 0;
    JSONArray dataJsonArr = new JSONArray();
    public static LinearLayout layout;
    private static final int SIGN_IN_REQUEST_CODE = 0x0;
    String UID;
    RelativeLayout main;
    private static final int FB_REQUEST_CODE = 64206;
    String uid ;
    private Toolbar toolbar;

    public LoginScreen() {
        allcon = LoginScreen.this;

    }

    List<NameValuePair> param = new ArrayList<NameValuePair>();
    EditText txtMessage;
    TextView txtTitle;


    public boolean isFBLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this);
        setContentView(R.layout.login);
        context = LoginScreen.this;

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        final Animation animAlpha = AnimationUtils.loadAnimation(context, R.anim.alpha);
        // Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Regular.ttf");
        S_in = (Button) findViewById(R.id.signin1);
        S_up = (ImageView) findViewById(R.id.signup1);
        F_pwd = (Button) findViewById(R.id.forgotpassword);
        U_name = (EditText) findViewById(R.id.username1);
        //  U_name.setTypeface(tf);
        U_pwd = (EditText) findViewById(R.id.password1);
        //  U_pwd.setTypeface(tf);
        // mainProgressGif = (LinearLayout) findViewById(R.id.mainProgressGif);
        // loginscreen = (LinearLayout) findViewById(R.id.llloginscreen);
        btnFbLogin = (ImageView) findViewById(R.id.btnFbLogin);
        btnGoogleLogin = (ImageView) findViewById(R.id.bgoogleLogin);
        mainLayout = (CoordinatorLayout) findViewById(R.id.mainLayout);
        callbackManager = CallbackManager.Factory.create();

        btnFbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFBLoggedIn()) {
                    LoginManager.getInstance().logOut();
                } else {
                    LoginManager.getInstance().logInWithReadPermissions(LoginScreen.this, Arrays.asList("public_profile", "user_friends", "email", "user_birthday", "user_hometown"));

                }
            }
        });
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                try {
                    GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                        @Override
                        public void onCompleted(JSONObject object, GraphResponse response) {
                            //System.out.println(object.toString());

                            try {
                                generateuid();
                                uid = UID;
                                id = object.getString("id");
                                //birthday = object.getString("birthday");
                                gender = object.getString("gender");
                                email = object.getString("email");
                                name = object.getString("name");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            param.clear();
                            param.add(new BasicNameValuePair("mailid", email));
                            param.add(new BasicNameValuePair("username", name));
                            param.add(new BasicNameValuePair("password", ""));
                            param.add(new BasicNameValuePair("userid", id));
                            param.add(new BasicNameValuePair("F_G_status", "F"));
                            new VolleyParseJson(mainLayout);
//


                        }
                    });
                    Bundle bundle = new Bundle();
                    bundle.putString("fields", "id,name,email,gender,birthday,hometown");
                    graphRequest.setParameters(bundle);
                    graphRequest.executeAsync();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();

        U_pwd.setTransformationMethod(new PasswordTransformationMethod());
        F_pwd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(final View arg0) {
                InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(arg0.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                arg0.startAnimation(animAlpha);

                boolean cnchk = Checknetworkconnection.isConnectingToInternet(context);
                if (cnchk == true) {

                    final Dialog dialog = new Dialog(context);
                    // hide to default title for Dialog
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    // inflate the layout dialog_layout.xml and set it as contentView
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = inflater.inflate(R.layout.dialogbox, null, false);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.setContentView(view);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

                    // Retrieve views from the inflated dialog layout and update their values
                    txtTitle = (TextView) dialog.findViewById(R.id.txt_dialog_title);

                    txtMessage = (EditText) dialog.findViewById(R.id.txt_dialog_message);

                    Button submit = (Button) dialog.findViewById(R.id.btnSubmit);
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputManager.hideSoftInputFromWindow(arg0.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                            mailidforgetpwd = txtMessage.getText().toString();
                            String mailidforgetpwd11;
                            mailidforgetpwd11 = txtMessage.getText().toString();
                            // Open the browser

                            if (!isValidEmail(mailidforgetpwd)) {
//                                txtMessage.setError("Invalid Email");
                                new SnackBar(v, "Invalid Email");
                            } else {
                                param.add(new BasicNameValuePair("mailid", mailidforgetpwd));
                                //	new  Getforgetpassword().execute();
                                new VolleyGetforgetpassword(arg0);
                                dialog.dismiss();
                                //Toast.makeText(con, "get password to ur mail", 4000).show();
                            }

                            // Dismiss the dialog

                        }
                    });

                    Button btnCancel = (Button) dialog.findViewById(R.id.btnCancel);
                    btnCancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            // Close the dialog


                            dialog.dismiss();
                        }
                    });

                    // Display the dialog
                    dialog.show();
                } else {
//                    Toast.makeText(con, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    new SnackBar(arg0, "Please check your internet connection");
                }

            }
        });

        btnGoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(i, SIGN_IN_REQUEST_CODE);


            }
        });

        S_up.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(arg0.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                arg0.startAnimation(animAlpha);

                boolean cnchk = Checknetworkconnection.isConnectingToInternet(context);
                if (cnchk == true) {

                    Intent PassToRegister = new Intent(LoginScreen.this, Registration.class);
                    startActivity(PassToRegister);
                } else {
                    new SnackBar(arg0, "Please check your internet connection");
                }
            }
        });


        S_in.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(v.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                v.startAnimation(animAlpha);

                boolean cnchk = Checknetworkconnection.isConnectingToInternet(context);
                if (cnchk == true) {
                    boolean stsbln = true;
                    //loginscreen.setEnabled(false);
                    Login_Usr = U_name.getText().toString().trim();
                    Login_Pwd = U_pwd.getText().toString().trim();

                    final String email = U_name.getText().toString();
                    final String pass = U_pwd.getText().toString();
                    if (!isValidEmail(email)) {
                        //loginscreen.setEnabled(true);
//                        U_name.setError("Invalid Email");
                        emailerror = true;
                        stsbln = false;
                    }
                    if (!isValidPassword(pass)) {
                        psserror = true;
                        stsbln = false;
                    }

                    if (emailerror == true && psserror == true) {
                        new SnackBar(v, "Invalid Email Address and Password");
                        emailerror = false;
                        psserror = false;
                    } else if (emailerror == true && psserror == false) {
                        new SnackBar(v, "Invalid Email Address");
                        emailerror = false;
                    } else if (emailerror == false && psserror == true) {
                        new SnackBar(v, "Invalid Password");
                        psserror = false;
                    }

                    if (stsbln == true) {

                        param.add(new BasicNameValuePair("mailid", Login_Usr));
                        try {
                            Login_Pwd = Encryptionalg.encrypt(Login_Pwd);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        param.add(new BasicNameValuePair("password", Login_Pwd));
                        param.add(new BasicNameValuePair("F_G_status", "E"));
                        new VolleyParseJson(v);
                    }
                } else {
//                    Toast.makeText(con, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    new SnackBar(v, "Please check your internet connection");
                }
            }
        });


    }
    public void generateuid(){
        boolean cnchk = Checknetworkconnection.isConnectingToInternet(LoginScreen.this);

        if (cnchk == true) {
            RequestQueue queue = Volley.newRequestQueue(LoginScreen.this);
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

                            // Display the first 500 characters of the response string.
                            // mTextView.setText("Response is: "+ response.substring(0,500));
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginScreen.this,"That didn't work!", Toast.LENGTH_SHORT).show();

                }
            });
            queue.add(stringRequest);
        } else {

            Toast.makeText(LoginScreen.this, "Please check your internet connection", Toast.LENGTH_SHORT).show();
        }


    }
    /*  @Override
      protected void onActivityResult(int requestCode, int resultCode, Intent data) {
          switch (requestCode) {
              case SIGN_IN_REQUEST_CODE:
                  GoogleSignInResult googleSignInResult =
                          Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                  handleSignInResult(googleSignInResult);
                  break;
          }
      }*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from
        //   GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            try {
                GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                if (result.isSuccess()) {
                    generateuid();
                    GoogleSignInAccount acct = result.getSignInAccount();
                    // Get account information
                    String mFullName = acct.getDisplayName();
                    mEmail = acct.getEmail();
                    mId = acct.getId();
                    String mTokenId = acct.getIdToken();
                    Set mGrantedScope = acct.getGrantedScopes();
                    Uri mPhotoUrl = acct.getPhotoUrl();
                    param.clear();
                    param.add(new BasicNameValuePair("mailid", mEmail));
                    param.add(new BasicNameValuePair("username", mFullName));
                    param.add(new BasicNameValuePair("password", ""));
                    param.add(new BasicNameValuePair("userid", UID));
                    param.add(new BasicNameValuePair("F_G_status", "G"));
                    googleLoginStatus = mGoogleApiClient.isConnected();
                    new VolleyParseJson(mainLayout);
//                Toast.makeText(LoginScreen.this, mEmail + mFullName, Toast.LENGTH_LONG).show();
                }
                Auth.GoogleSignInApi.revokeAccess(LoginScreen.mGoogleApiClient);
            }catch (Exception e){
                e.getMessage();
                e.printStackTrace();
            }
        } else if (requestCode == 64206) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }
    private void handleSignInResult(GoogleSignInResult googleSignInResult) {
        if (googleSignInResult.isSuccess()) {
            GoogleSignInAccount account = googleSignInResult.getSignInAccount();
            if (account != null) {
               /* state.setText(getString(R.string.state, account.getEmail(), account.getDisplayName()));
                Picasso.with(this).load(account.getPhotoUrl()).into(profilePic);*/
                param.clear();
                param.add(new BasicNameValuePair("mailid", account.getEmail()));
                param.add(new BasicNameValuePair("username", account.getDisplayName()));
                param.add(new BasicNameValuePair("password", ""));
                Registration UID =new Registration();
                param.add(new BasicNameValuePair("userid",UID.uid));
                param.add(new BasicNameValuePair("F_G_status", "G"));
                googleLoginStatus = mGoogleApiClient.isConnected();
                new VolleyParseJson(mainLayout);

            }
        } else {
            Log.d("","");
        }
    }
  /*  @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == RC_SIGN_IN) {

            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            //if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();
                // Get account information
                String mFullName = acct.getDisplayName();
                mEmail = acct.getEmail();
                mId = acct.getId();
                String mTokenId = acct.getIdToken();
                Set mGrantedScope = acct.getGrantedScopes();
                Uri mPhotoUrl = acct.getPhotoUrl();
                param.clear();
                param.add(new BasicNameValuePair("mailid", mEmail));
                param.add(new BasicNameValuePair("username", mFullName));
                param.add(new BasicNameValuePair("password", ""));
                param.add(new BasicNameValuePair("userid", mId));
                param.add(new BasicNameValuePair("F_G_status", "G"));
                googleLoginStatus = mGoogleApiClient.isConnected();
                new VolleyParseJson(mainLayout);

//                Toast.makeText(LoginScreen.this, mEmail + mFullName, Toast.LENGTH_LONG).show();
           // }
            try {
                Auth.GoogleSignInApi.revokeAccess(LoginScreen.mGoogleApiClient);
            } catch (Exception e) {
            }
        } else if (requestCode == 64206) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }*/


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

    @Override
    public void onResume() {
        super.onResume();
        if (Registration.check == true) {
            new SnackBar(S_in, "successfully Registered");
        }

    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
//        21245
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }


    String[] favlistet;
    final String TAG = "JsonParser.java";
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "", logname = "", userinputtype = "", userorexeID = "";

    public class VolleyParseJson {
        JSONArray dataJsonArr = null;
        View vw;

        public VolleyParseJson(View v) {
            JSONObject requestJson = new JSONObject();
            vw = v;
            if (param != null) {
                for (NameValuePair element : param) {
                    try {
                        requestJson.put(element.getName(), element.getValue());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Allurls.url_login, requestJson, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject jsonObject) {
                    try {
                        try {

                            dataJsonArr = jsonObject.getJSONArray("Favouriteitems");
                            favlistet = new String[dataJsonArr.length()];
                        } catch (NullPointerException ec) {
                            Log.e("Error in Login", ec.toString());
                        }
                        if (dataJsonArr != null) {
                            if (dataJsonArr.length() > 0) {
                                JSONObject c = dataJsonArr.getJSONObject(0);
                                logname = c.getString("LoginStatus");
                                userinputtype = c.getString("UserType");
                                userorexeID = c.getString("UserorExeID");

                            }
                            for (int i = 1; i < dataJsonArr.length(); i++) {
                                //
                                JSONObject c = dataJsonArr.getJSONObject(i);
                                String ff = c.getString("favitem_id");
                                favlist.add(ff);
                            }
                        }
                        logname = logname.trim();
                        userinputtype = userinputtype = userinputtype.trim();
                        if (logname.equals("1") && userinputtype.equals("N")) {
                            loginst = "1";
//                            Toast.makeText(con, "You must Activate your account by clicking the verification link sent on your Email", Toast.LENGTH_LONG).show();
                            new SnackBar(vw, "You must Activate your account by clicking the verification link sent on your Email");
                        } else if (logname.equals("1")) {
                            loginst = "1";
//                            Toast.makeText(con, "Login error", Toast.LENGTH_SHORT).show();
                            new SnackBar(vw, "Login error");

                        } else if (userinputtype.equals("users")) {
                            loginusertype = userinputtype;
                            if (Login_Usr == null && email == null) {
                                loginemailid = mEmail;
                            } else if (Login_Usr == null && mEmail == null) {
                                loginemailid = email;
                            } else {
                                loginemailid = Login_Usr;
                            }
                            userorcustID = userorexeID;
                            loginst = logname;
                            customer_nameglobal = logname;
                            UserorExecutivesID = userorexeID;
                            customerid_cartandfav = userorexeID;
                            loginorlogoutstatus = "Y";

                            VinMartSqlLite db = new VinMartSqlLite(context);
                            //        int delsts = db.deletelogindetails();
                           int insertsts = db.addlLoginDetails(LoginScreen.UserorExecutivesID, LoginScreen.customerid_cartandfav,
                                    LoginScreen.loginst, LoginScreen.loginemailid, LoginScreen.loginusertype, LoginScreen.customer_nameglobal, LoginScreen.loginorlogoutstatus);

                            Intent userLog = new Intent(LoginScreen.this, MainActivity.class);
                            LoginScreen.this.finish();
                            startActivity(userLog);


                        } else {

//                            Toast.makeText(con, "Login error..Try again..", Toast.LENGTH_SHORT).show();
                            new SnackBar(vw, "Login error..Try again..");

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    try {
                        Auth.GoogleSignInApi.revokeAccess(LoginScreen.mGoogleApiClient);
                    } catch (Exception e) {
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    new SnackBar(vw, "Login error..Try again..");
                }
            });
            Singleton.getInstance(context).addToRequestQueue(jsonObjectRequest);

        }
    }


    String strpass;

    public class VolleyGetforgetpassword {
        private ProgressDialog progress;
        String mailres = "";
        View vw;

        public VolleyGetforgetpassword(View v) {
            vw = v;
            progress = new ProgressDialog(context);
            progress.setMessage("Processing..");
            progress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progress.setIndeterminate(true);
            progress.show();
            JSONObject requestJson = new JSONObject();
            try {
                if (param != null) {
                    for (NameValuePair element : param) {
                        requestJson.put(element.getName(), element.getValue());
                    }
                }
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Allurls.url_getfogetpassword, requestJson, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            strpass = jsonObject.getString("password");


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            if (strpass.equals("null")) {
                                mailres = "Your mailid does't matched";
                            } else {
                                strpass = Encryptionalg.decrypt(strpass);
                                strpass = "Your Login forgot Password is : " + strpass;
                                Forgetpasswordmail obj = new Forgetpasswordmail();
                                mailres = obj.Forgetpass(mailidforgetpwd, strpass);
                                //obj.main("test");
                            }
                        } catch (NullPointerException e) {

                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        progress.cancel();
//                        Toast.makeText(con, mailres, Toast.LENGTH_LONG).show();
                        new SnackBar(vw, mailres);

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progress.cancel();
                    }
                });
                Singleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

}
