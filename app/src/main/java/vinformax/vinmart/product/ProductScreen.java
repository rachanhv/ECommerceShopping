package vinformax.vinmart.product;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.math.BigDecimal;

import vinformax.vinmart.R;
import vinformax.vinmart.animation.BadgeView;
import vinformax.vinmart.database.VinMartSqlLite;
import vinformax.vinmart.service.CustomVolleyRequest;

public class ProductScreen extends AppCompatActivity {
    String vProductImage;
    String vProductDiscountPrice;
    String vProductActualPrice;
    String vProductName;
    String vTitle;
    String VProductDiscountPercentage;
    NetworkImageView productImage;
    private ImageLoader imageLoader;
    TextView productName, productDisPrice, productActPrice, quantityMinus, quantityPlus, productTotal;
    EditText quantityValue;
    Button addToCart,cartDetails;
    BigDecimal cartTotal;
    static int multiplyer = 0;
    static String quantityStringValue = "";
    static int counter = 0;
    static int favResult, cartResult = 0;
    String pCounter, pPrice;
    ImageButton favButton;

    int playpausestate=-1,initialFlag=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_screen);
        productName = (TextView) findViewById(R.id.productName);
        productActPrice = (TextView) findViewById(R.id.productActPrice);
        productDisPrice = (TextView) findViewById(R.id.productDisPrice);


        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.productToolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }catch (Exception e){
            e.printStackTrace();
            e.getMessage();
        }


        addToCart = (Button) findViewById(R.id.addToCart);
        //cartDetails = (Button)findViewById(R.id.cartDetails);
        favButton= (ImageButton) findViewById(R.id.imageButton1);
        quantityMinus = (TextView) findViewById(R.id.quantityMinus);
        quantityPlus = (TextView) findViewById(R.id.quantityPlus);
        quantityValue = (EditText) findViewById(R.id.quantityValue);
        productTotal = (TextView) findViewById(R.id.productTotal);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        Bundle intent = getIntent().getExtras();
        productImage = (NetworkImageView) findViewById(R.id.productImage);
        vProductName = intent.getString("vPName");
        vProductActualPrice = intent.getString("vPDiscountPrice");
        vProductDiscountPrice = intent.getString("vpActualPrice");
        vProductImage = intent.getString("vProductImage");
        VProductDiscountPercentage = intent.getString("vPDiscountPercentage");
        productTotal.setText(vProductActualPrice);
        productName.setText(vProductName);
        productDisPrice.setPaintFlags(productDisPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        productActPrice.setText(vProductActualPrice);
        productDisPrice.setText(vProductDiscountPrice);
        quantityValue.setText(String.valueOf(1));

        BadgeView badge = new BadgeView(this, productImage);
        badge.setText(vProductActualPrice + "% Discount");
        badge.setBadgeBackgroundColor(Color.parseColor("#A4C639"));
        TranslateAnimation anim = new TranslateAnimation(-1000, 5, 3, 3);
        anim.setInterpolator(new BounceInterpolator());
        anim.setDuration(2000);
        badge.show(anim);
        badge.show();

        BadgeView badge1 = new BadgeView(this, addToCart);
        badge1.setText("12");
        badge1.setTextColor(Color.RED);
        badge1.setBadgeBackgroundColor(Color.parseColor("#A4C639"));
        TranslateAnimation anim1 = new TranslateAnimation(-1000, 5, 3, 3);
        anim1.setInterpolator(new BounceInterpolator());
        anim1.setDuration(2000);
        badge1.show(anim);
        badge1.setBadgePosition(BadgeView.POSITION_CENTER);
        badge1.show();
        ProductQuantityManipulation();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.cartDetails);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(ProductScreen.this,ProductCart.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

        }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("second", vProductImage);
        loadImage();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
      /*  vProductName = "";
        vProductActualPrice = "";
        vProductDiscountPrice = "";
        vProductImage = "";
        vTitle = "";
        VProductDiscountPercentage = "";
        finish();*/
    }

    private void loadImage() {


        if (vProductImage.equals("")) {
            //       Toast.makeText(this, "Please enter a URL", Toast.LENGTH_LONG).show();
            productImage.setImageResource(R.mipmap.ic_launcher);
            return;
        }
        imageLoader = CustomVolleyRequest.getInstance(this.getApplicationContext())
                .getImageLoader();
        imageLoader.get(vProductImage, ImageLoader.getImageListener(productImage,
                R.mipmap.ic_launcher, android.R.drawable
                        .ic_dialog_alert));
        productImage.setImageUrl(vProductImage, imageLoader);
    }

    public void ProductQuantityManipulation() {

        quantityPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    quantityStringValue = quantityValue.getText().toString();
                    counter = Integer.parseInt(quantityStringValue) + 1;

                multiplyer = Integer.parseInt(vProductActualPrice);
                cartTotal = BigDecimal.valueOf(counter * multiplyer);
                quantityValue.setText(Integer.toString(counter));
                quantityValue.setSelection(quantityValue.getText().length());
                productTotal.setText(String.valueOf(cartTotal));
                } catch (Exception e) {
                    counter = 1;
                }
            }
        });
        favButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {

                    AddTOFav();

                }

        });


        quantityMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    quantityStringValue = quantityValue.getText().toString();
                    if (quantityStringValue.equals("1")) {

                        counter = 1;

                    } else {
                        counter = Integer.parseInt(quantityStringValue) - 1;
                    }

                } catch (Exception e) {
                    counter = 1;
                }
                multiplyer = Integer.parseInt(vProductActualPrice);
                cartTotal = BigDecimal.valueOf(counter * multiplyer);
                quantityValue.setText(Integer.toString(counter));
                quantityValue.setSelection(quantityValue.getText().length());
                productTotal.setText(String.valueOf(cartTotal));
            }
        });

        quantityValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    quantityStringValue = quantityValue.getText().toString();
                    if (quantityStringValue.equals("1")) {

                        counter = 1;
                    } else {

                        counter = Integer.parseInt(quantityStringValue);
                    }
                }
                catch (Exception e) {
                    counter = 1;
                }
                multiplyer = Integer.parseInt(vProductActualPrice);
                cartTotal = BigDecimal.valueOf(counter * multiplyer);
                quantityValue.setText(Integer.toString(counter));
                quantityValue.setSelection(quantityValue.getText().length());
                productTotal.setText(String.valueOf(cartTotal));
            }
        });
        quantityValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    quantityStringValue = quantityValue.getText().toString();
                    if (quantityStringValue.equals("1")) {

                        counter = 1;
                    } else {
                        quantityStringValue = quantityValue.getText().toString();
                        counter = Integer.parseInt(quantityStringValue);
                        if(counter<1){
                            counter=1;
                        }
                    }
                }catch (Exception e) {
                    counter = 1;
                }
                multiplyer = Integer.parseInt(vProductActualPrice);
                cartTotal = BigDecimal.valueOf(counter * multiplyer);
                quantityValue.setText(Integer.toString(counter));
                quantityValue.setSelection(quantityValue.getText().length());
                productTotal.setText(String.valueOf(cartTotal));
            }
        });


        quantityValue.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                quantityStringValue = quantityValue.getText().toString();
                try {
                    quantityStringValue = quantityValue.getText().toString();
                    if (quantityStringValue.equals("1")) {

                        counter = 1;
                    } else {

                        counter = Integer.parseInt(quantityStringValue);
                        if(counter<1){
                            counter=1;
                        }
                        multiplyer = Integer.parseInt(vProductActualPrice);
                        cartTotal = BigDecimal.valueOf(counter * multiplyer);
                    }
                }catch (Exception e){
                    e.getMessage();
                    e.printStackTrace();
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

                productTotal.setText(String.valueOf(cartTotal));
            }
        });



        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddToCart();
            }
        });

   /*     cartDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),ProductCart.class);
                startActivity(i);
            }
        });*/
    }


    private void AddTOFav() {

       favButton.setImageResource(R.drawable.heart1);
        VinMartSqlLite vinMartSqlLite = new VinMartSqlLite(this);
        favResult = vinMartSqlLite.AddtoFavDataBase("1",vProductName,vProductActualPrice,vProductDiscountPrice,VProductDiscountPercentage,vProductImage,"no");
        if (favResult == 1) {
            Toast.makeText(ProductScreen.this, "added to favorite", Toast.LENGTH_SHORT).show();

        } else if (favResult == -1) {
            Toast.makeText(ProductScreen.this, "failed", Toast.LENGTH_SHORT).show();

        } else if(favResult==0) {favButton.setImageResource(R.drawable.heart);

            Toast.makeText(ProductScreen.this, "Favorite Item Removed", Toast.LENGTH_SHORT).show();
            vinMartSqlLite.deleteItem(vProductName);
        }
        else  {
            Toast.makeText(ProductScreen.this, "try again", Toast.LENGTH_SHORT).show();
        }
    }


    private void AddToCart() {

        pCounter = quantityValue.getText().toString();
        pPrice = productTotal.getText().toString();



        
       // Log.d("first", String.valueOf(productCartId));




        VinMartSqlLite vinMartSqlLite = new VinMartSqlLite(this);


        cartResult = vinMartSqlLite.AddtoCartDataBase("1", vProductName, pCounter, vProductActualPrice, vProductDiscountPrice, VProductDiscountPercentage, pPrice, vProductImage, "no");

        if (cartResult == 1) {
            Toast.makeText(ProductScreen.this, "added to cart", Toast.LENGTH_SHORT).show();

        } else if (cartResult == -1) {
            Toast.makeText(ProductScreen.this, "failed", Toast.LENGTH_SHORT).show();
        } else if(cartResult==0) {
            Toast.makeText(ProductScreen.this, "Product Already Added", Toast.LENGTH_SHORT).show();
        }
        else  {
            Toast.makeText(ProductScreen.this, "try again", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.main, menu);
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
            case R.id.action_add_favourite:
                Toast.makeText(this, "action cart selected", Toast.LENGTH_SHORT)
                        .show();
                Intent i = new Intent(ProductScreen.this,ProductCart.class);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                //share();
                return true;

        }

        return super.onOptionsItemSelected(item);
    }

}
