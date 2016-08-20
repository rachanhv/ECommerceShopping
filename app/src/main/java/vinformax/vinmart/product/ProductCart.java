package vinformax.vinmart.product;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.apache.http.NameValuePair;
import org.json.JSONException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import vinformax.vinmart.R;
import vinformax.vinmart.database.VinMartSqlLite;
import vinformax.vinmart.recycleviewadapter.CartProductAdapter;

public class ProductCart extends AppCompatActivity {
    RecyclerView recyclerView;
    TextView tvsummaryTotal;
    TextView buyTextView;
    private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;    // latha AdDBQ2i6szN4e5hFJTwsFNgQwJfvAE-qllMRUTUZFV163LMTqeM2o6ZEoJ1bBDu1eptMY_Ai_Gdxbz_v
    private static final String CONFIG_CLIENT_ID = "AdDBQ2i6szN4e5hFJTwsFNgQwJfvAE-qllMRUTUZFV163LMTqeM2o6ZEoJ1bBDu1eptMY_Ai_Gdxbz_v";          //AagBUCEqPH_ELSbLiWsgtSwuvIxR-VXQLgDIMwPUiTEdnjsB8Hgt-0NbSMNOsiJB21XBX0OOugu4YVOs

    private static final int REQUEST_CODE_PAYMENT = 1;
    private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;

    private static PayPalConfiguration config = new PayPalConfiguration()
            .environment(CONFIG_ENVIRONMENT)
            .clientId(CONFIG_CLIENT_ID)
            .merchantName("VinKart")
            .merchantPrivacyPolicyUri(
                    Uri.parse("https://www.example.com/privacy"))
            .merchantUserAgreementUri(
                    Uri.parse("https://www.example.com/legal"));

    PayPalPayment thingToBuy;
    String conformation = "";
    private static final String TAG = "paymentExample";
    List<NameValuePair> param = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_cart);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        tvsummaryTotal = (TextView) findViewById(R.id.tvsummaryTotal);
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CartProductAdapter adapter = new CartProductAdapter(this);
        buyTextView = (TextView) findViewById(R.id.buyTextView);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);

    }

    @Override
    protected void onResume() {
        super.onResume();

        tvsummaryTotal.setText(String.valueOf(VinMartSqlLite.grandTotal));
        buyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent intent = new Intent(ProductCart.this, PayPalService.class);
                    intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
                    startService(intent);



                    AlertDialog.Builder confirmToPay = new AlertDialog.Builder(ProductCart.this);
                    confirmToPay.setTitle("CheckOut");
                    confirmToPay.setMessage("Please confirm Total Payment of"
                            + CurrencyFormat.putComma(tvsummaryTotal.getText().toString())
                            + " With Paypal");

                    confirmToPay.setPositiveButton("Continue",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {

                                    thingToBuy = new PayPalPayment(
                                            new BigDecimal(tvsummaryTotal
                                                    .getText().toString()),
                                            "USD",
                                            "Total",
                                            PayPalPayment.PAYMENT_INTENT_SALE);
                                    Intent intent = new Intent(
                                            ProductCart.this,
                                            PaymentActivity.class);

                                    intent.putExtra(
                                            PaymentActivity.EXTRA_PAYMENT,
                                            thingToBuy);

                                    startActivityForResult(intent,
                                            REQUEST_CODE_PAYMENT);

                                }
                            });
                    confirmToPay.setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,
                                                    int which) {
                                    dialog.cancel();
                                }
                            });
                    confirmToPay.show();


                }




        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = data
                        .getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        Log.i(TAG, confirm.toJSONObject().toString(4));
                        Log.i(TAG, confirm.getPayment().toJSONObject()
                                .toString(4));
                        conformation = "conformed";

                        Toast.makeText(ProductCart.this,
                                "PaymentConfirmation info received from PayPal",
                                Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        Log.e(TAG, "an extremely unlikely failure occurred: ",
                                e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                conformation = "conformed failed";
                Log.i(TAG, "The user canceled.");

            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                conformation = "conformed invalid";
                Log.i(TAG,
                        "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        } else if (requestCode == REQUEST_CODE_FUTURE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PayPalAuthorization auth = data
                        .getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        Log.i("FuturePaymentExample", auth.toJSONObject()
                                .toString(4));

                        String authorization_code = auth.getAuthorizationCode();
                        Log.i("FuturePaymentExample", authorization_code);

                        sendAuthorizationToServer(auth);
                        Toast.makeText(ProductCart.this,
                                "Future Payment code received from PayPal",
                                Toast.LENGTH_LONG).show();

                    } catch (JSONException e) {
                        Log.e("FuturePaymentExample",
                                "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("FuturePaymentExample", "The user canceled.");
            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i("FuturePaymentExample",
                        "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
            }
        }
    }

    private void sendAuthorizationToServer(PayPalAuthorization authorization) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.cartmain, menu);
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
                Intent i = new Intent(ProductCart.this,Favorite.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                //share();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
