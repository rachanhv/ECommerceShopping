package vinformax.vinmart.user;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import vinformax.vinmart.R;
import vinformax.vinmart.database.VinMartSqlLite;
import vinformax.vinmart.encryption.Encryptionalg;
import vinformax.vinmart.service.Allurls;
import vinformax.vinmart.service.AppController;
import vinformax.vinmart.service.Checknetworkconnection;
import vinformax.vinmart.service.Forgetpasswordmail;
import vinformax.vinmart.service.Httprequestwithparam;
import vinformax.vinmart.service.Singleton;
import vinformax.vinmart.snackbar.SnackBar;

/**
 * Created by Vinformax Systems Pvt. Ltd on 06/28/2016.
 */
public class login extends Fragment implements GoogleApiClient.OnConnectionFailedListener {
    View rootView;
    ActionBar actionBar;
    String mailidforgetpwd = "";
    Button S_in,F_pwd;
    ImageView S_up;

    public static login con;
    EditText U_name, U_pwd;
    public static LinearLayout mainProgressGif;
    static String Login_Usr, Login_Pwd;
    JSONObject carthmap;
    JSONArray jArray = new JSONArray();
    String inputmulicart = "";
    Context context;
    Httprequestwithparam onjparam = new Httprequestwithparam();
    boolean emailerror = false, psserror = false;
    public static GoogleApiClient mGoogleApiClient;
    public static boolean googleLoginStatus;
    private static final int RC_SIGN_IN = 9001;
    ImageView btnFbLogin, btnGoogleLogin;
    CoordinatorLayout mainLayout;
    CallbackManager callbackManager;
    String mEmail, mId, id, gender, email, name;





    List<NameValuePair> paramuser = new ArrayList<NameValuePair>();

    List<NameValuePair> cartparam = new ArrayList<NameValuePair>();

    List<NameValuePair> param = new ArrayList<NameValuePair>();
    EditText txtMessage;
    TextView txtTitle;

    public login() {

    }

    public boolean isFBLoggedIn() {
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        rootView = inflater.inflate(R.layout.login, container, false);
        final Animation animAlpha = AnimationUtils.loadAnimation(getActivity(), R.anim.anim_alpha);
        con = login.this;
        FacebookSdk.sdkInitialize(getActivity());
        AppController.contentMainModel.setStatusBarActivity(getActivity(), true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        actionBar.setTitle("Login");
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(), "fonts/Roboto-Regular.ttf");
        S_in = (Button) rootView.findViewById(R.id.signin1);
        S_up = (ImageView) rootView.findViewById(R.id.signup1);
        F_pwd = (Button) rootView.findViewById(R.id.forgotpassword);
        U_name = (EditText) rootView.findViewById(R.id.username1);
        U_name.setTypeface(tf);
        U_pwd = (EditText) rootView.findViewById(R.id.password1);
        U_pwd.setTypeface(tf);
       // mainProgressGif = (LinearLayout) rootView.findViewById(R.id.mainProgressGif);
        btnFbLogin = (ImageView) rootView.findViewById(R.id.btnFbLogin);
        btnGoogleLogin = (ImageView) rootView.findViewById(R.id.bgoogleLogin);
        mainLayout = (CoordinatorLayout) rootView.findViewById(R.id.mainLayout);

        callbackManager = CallbackManager.Factory.create();

        btnFbLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFBLoggedIn()) {
                    LoginManager.getInstance().logOut();
                } else {
                    LoginManager.getInstance().logInWithReadPermissions(login.this, Arrays.asList("public_profile", "user_friends", "email", "user_birthday", "user_hometown"));
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
                            try {
                                id = object.getString("id");
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
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    View view = inflater.inflate(R.layout.dialogbox, null, false);
                    dialog.setCanceledOnTouchOutside(true);
                    dialog.setContentView(view);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

                    txtTitle = (TextView) dialog.findViewById(R.id.txt_dialog_title);

                    txtMessage = (EditText) dialog.findViewById(R.id.txt_dialog_message);

                    Button submit = (Button) dialog.findViewById(R.id.btnSubmit);
                    submit.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                            inputManager.hideSoftInputFromWindow(arg0.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                            mailidforgetpwd = txtMessage.getText().toString();

                            if (!isValidEmail(mailidforgetpwd)) {
//                                txtMessage.setError("Invalid Email");
                                new SnackBar(v, "Invalid Email");
                            } else {
                                param.add(new BasicNameValuePair("mailid", mailidforgetpwd));
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
                }
                else {
//                    Toast.makeText(con, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    new SnackBar(arg0, "Please check your internet connection");
                }

            }
        });

        btnGoogleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                rebuildGoogleApiClient();
                Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
                startActivityForResult(signInIntent, RC_SIGN_IN);


            }
        });

        S_up.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub

                // TODO Auto-generated method stub
                InputMethodManager inputManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(arg0.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                arg0.startAnimation(animAlpha);

                boolean cnchk = Checknetworkconnection.isConnectingToInternet(getActivity());
                if (cnchk == true) {
                    Intent PassToRegister = new Intent(getActivity(), Registration.class);
                    startActivity(PassToRegister);

                } else {
//                    Toast.makeText(con, "Please check your internet connection", Toast.LENGTH_SHORT).show();
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
                        //new AsyncTaskParseJson().execute();
                        new VolleyParseJson(v);
                    }
                } else {
//                    Toast.makeText(con, "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    new SnackBar(v, "Please check your internet connection");
                }
            }
        });
        return rootView;
    }

    public void rebuildGoogleApiClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .enableAutoManage(getActivity() /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleApiClient.connect();
    }
    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient == null) {

            rebuildGoogleApiClient();
        }

    }
    @Override
    public void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount acct = result.getSignInAccount();
                // Get account information
                String mFullName = acct.getDisplayName();
                mEmail = acct.getEmail();
                mId = acct.getId();

                param.clear();
                param.add(new BasicNameValuePair("mailid", mEmail));
                param.add(new BasicNameValuePair("username", mFullName));
                param.add(new BasicNameValuePair("password", ""));
                param.add(new BasicNameValuePair("userid", mId));
                param.add(new BasicNameValuePair("F_G_status", "G"));
                googleLoginStatus = mGoogleApiClient.isConnected();
                new VolleyParseJson(mainLayout);

//                Toast.makeText(LoginScreen.this, mEmail + mFullName, Toast.LENGTH_LONG).show();
            }
            try {
                Auth.GoogleSignInApi.revokeAccess(LoginScreen.mGoogleApiClient);
            }catch (Exception e){}
        } else if (requestCode == 64206) {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
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

    @Override
    public void onResume() {
        super.onResume();
        if (Registration.check == true) {
            new SnackBar(S_in, "successfully Registered");
        }

    }


    String[] favlistet;
    final String TAG = "JsonParser.java";
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "", logname = "", userinputtype = "", userorexeID = "";

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }

    public class VolleyParseJson {
        JSONArray dataJsonArr = null;
        View vw;

        public VolleyParseJson(View v) {
            JSONObject requestJson = new JSONObject();
            vw = v;
            mainProgressGif.setVisibility(View.VISIBLE);
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
//                            mainProgressGif.setVisibility(View.GONE);

                            dataJsonArr = jsonObject.getJSONArray("Favouriteitems");
                            favlistet = new String[dataJsonArr.length()];
                        } catch (NullPointerException ec) {
                            Log.e("Error in Login", ec.toString());
                            mainProgressGif.setVisibility(View.GONE);
                        }
                        if (dataJsonArr != null) {
                            if (dataJsonArr.length() > 0) {
                                JSONObject c = dataJsonArr.getJSONObject(0);
                                logname = c.getString("LoginStatus");
                                LoginScreen.userinputtype = c.getString("UserType");
                                userorexeID = c.getString("UserorExeID");

                            }
                            for (int i = 1; i < dataJsonArr.length(); i++) {
                                //
                                JSONObject c = dataJsonArr.getJSONObject(i);
                                String ff = c.getString("favitem_id");
                                LoginScreen.favlist.add(ff);
                            }
                        }
                        logname = logname.trim();
                        userinputtype = LoginScreen.userinputtype = LoginScreen.userinputtype.trim();
                        if (logname.equals("1") && userinputtype.equals("N")) {
                            LoginScreen.loginst = "1";
                            new SnackBar(vw, "You must Activate your account by clicking the verification link sent on your Email");
                            mainProgressGif.setVisibility(View.GONE);
                        } else if (logname.equals("1")) {
                            LoginScreen.loginst = "1";
                            new SnackBar(vw, "Login error");
                            mainProgressGif.setVisibility(View.GONE);

                        }   else if (userinputtype.equals("users")) {
                            LoginScreen.loginusertype = userinputtype;
                            if (Login_Usr == null && email == null) {
                                LoginScreen.loginemailid = mEmail;
                            } else if(Login_Usr == null && mEmail == null){
                                LoginScreen.loginemailid = email;
                            } else {
                                LoginScreen.loginemailid = Login_Usr;
                            }
                            LoginScreen.userorcustID = userorexeID;
                            LoginScreen.loginst = logname;
                            LoginScreen.customer_nameglobal = logname;
                            LoginScreen.UserorExecutivesID = userorexeID;
                            LoginScreen.customerid_cartandfav = userorexeID;
                            LoginScreen.loginorlogoutstatus = "Y";
                            new AsyncTaskforgetusercart(vw).execute();

                        } else {

//                            Toast.makeText(con, "Login error..Try again..", Toast.LENGTH_SHORT).show();
                            new SnackBar(vw, "Login error..Try again..");
                            mainProgressGif.setVisibility(View.GONE);

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        mainProgressGif.setVisibility(View.GONE);
                    }
                    try {
                        Auth.GoogleSignInApi.revokeAccess(LoginScreen.mGoogleApiClient);
                    }catch (Exception e){}
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    mainProgressGif.setVisibility(View.GONE);
                    new SnackBar(vw, "Login error..Try again..");
                }
            });
            Singleton.getInstance(context).addToRequestQueue(jsonObjectRequest);

        }
    }

    public class AsyncTaskforgetusercart extends AsyncTask<String, String, String> {

        final String TAG = "AsyncTaskParseJson.java";

        // set your json string url here
        String yourJsonStringUrl = Allurls.url_executive_cartdetails;
        String hmitemid, prdnamefav2cart = "", pricefav2cart = "", discountfav2cart = "", prdidfav2cart = "", qnt;
        int insertsts = 0;
        String cartlist;
        int qnty;
        // contacts JSONArray
        View view;
        JSONArray dataJsonArrcart = null;

        AsyncTaskforgetusercart(View view) {
            this.view = view;
        }

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            try {
                // instantiate our json parser
                // JsonParser jParser = new JsonParser();
                // get json string from url

                for (int j = 0; j < LoginScreen.prditemid.length; j++) {
                }

                if (LoginScreen.hm.size() > 0) {

                    Iterator it = LoginScreen.hm.entrySet().iterator();
                    while (it.hasNext()) {


                        Map.Entry pairs = (Map.Entry) it.next();
                        int io = Integer.parseInt(pairs.getValue().toString());
                        if (io >= 1) {
                            hmitemid = pairs.getKey().toString();
                            for (int cnt = 0; cnt < LoginScreen.prditemid.length; cnt++) {
                                if (hmitemid.equals(LoginScreen.prditemid[cnt])) {
                                    prdidfav2cart = LoginScreen.prditemid[cnt];
                                    prdnamefav2cart = LoginScreen.prdimgname[cnt];
                                    pricefav2cart = LoginScreen.price[cnt];
                                    discountfav2cart = LoginScreen.discounts[cnt];
                                    qnty = LoginScreen.hm.get(hmitemid);

                                    try {
                                        carthmap = new JSONObject();
                                        Object k = carthmap;
                                        carthmap.put("customerid", LoginScreen.customerid_cartandfav);
                                        carthmap.put("executiveid", LoginScreen.UserorExecutivesID);
                                        String con = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

                                        //String mon=con;

                                        carthmap.put("createdon", con);
                                        carthmap.put("createdby", LoginScreen.loginst);
                                        carthmap.put("modifiedon", con);
                                        carthmap.put("modifiedby", LoginScreen.loginst);
                                        Random r = new Random();
                                        String qid = String.valueOf(r.nextInt(99999999 - 99999 + 1) + 999);

                                        carthmap.put("queueid", qid);
                                        carthmap.put("productid", prdidfav2cart);
                                        carthmap.put("productname", prdnamefav2cart);

                                        carthmap.put("quantity", qnty);
                                        carthmap.put("price", pricefav2cart);
                                        carthmap.put("discount", discountfav2cart);
                                        qnt = String.valueOf(qnty);

                                        float price = Float.parseFloat(pricefav2cart);
                                        float dis = Float.parseFloat(discountfav2cart);
                                        float qt = Float.parseFloat(qnt);
                                        float totforfinal;
                                        if (dis == 0) {
                                            totforfinal = price * qt;
                                        } else {
                                            totforfinal = ((price * dis) / 100);
                                            totforfinal = (price - totforfinal) * qt;
                                        }

                                        carthmap.put("total", String.valueOf(totforfinal));
                                        Object k1 = carthmap;
                                        jArray.put(k1);
                                    } catch (JSONException jse) {
                                        jse.printStackTrace();
                                    }
                                }
                            }

                            System.out.print(inputmulicart);
                            Log.e("favtocart", inputmulicart);

                            inputmulicart = jArray.toString();
                            Httprequestwithparam sendfav;
                            try {
                                sendfav = new Httprequestwithparam();

                                cartparam.add(new BasicNameValuePair("addcartsdetails", inputmulicart));

                                String resmfav = sendfav.getStringFromUrlres(Allurls.url_multiplefavtocart, cartparam);
                                cartparam.clear();
                                //mListView.setAdapter(new Adapterdd(item));

                            } catch (Exception e) {
                                // TODO: handle exception
                                e.printStackTrace();
                            }
                        }
                    }

                }
//
                paramuser.clear();
                paramuser.add(new BasicNameValuePair("customerid", LoginScreen.UserorExecutivesID));
                paramuser.add(new BasicNameValuePair("executiveid", LoginScreen.UserorExecutivesID));
                JSONObject json = onjparam.getJSONFromUrlres(yourJsonStringUrl, paramuser);
                param.clear();
                // get the array of users
                dataJsonArrcart = json.getJSONArray("cartitems");
                if (dataJsonArrcart.length() > 0) {

                    for (int len = 0; len < dataJsonArrcart.length(); len++) {
                        JSONObject c = dataJsonArrcart.getJSONObject(len);

                        LoginScreen.hm.put(c.getString("product_id"), Integer.parseInt(c.getString("quantity")));
                    }

                    LoginScreen.carthomecount = String.valueOf(dataJsonArrcart.length());

                }

            } catch (Exception ec) {
                ec.printStackTrace();
            }


            return null;
        }

        @Override
        protected void onPostExecute(String strFromDoInBg) {

            String joinedfavid = TextUtils.join(",", LoginScreen.favlist);
            Map<String, Integer> carttab = new HashMap<String, Integer>();
            Iterator it = LoginScreen.hm.entrySet().iterator();
            while (it.hasNext()) {

                Map.Entry pairs = (Map.Entry) it.next();
                int io = Integer.parseInt(pairs.getValue().toString());
                if (io >= 1) {
                    String strArray = pairs.getKey().toString();
                    int strval = Integer.parseInt(pairs.getValue().toString());
                    carttab.put(strArray, strval);
                }
            }

            cartlist = new JSONObject(carttab).toString();

            VinMartSqlLite db = new VinMartSqlLite(context);
            int delsts = db.deletelogindetails();

             if (insertsts != 0) {

                Intent makehome = new Intent(context, Registration.class);
                startActivity(makehome);
                //finish();
            } else {
                new SnackBar(view, "Insert failed");
                mainProgressGif.setVisibility(View.GONE);
            }
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
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
