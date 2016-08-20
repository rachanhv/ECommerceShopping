package vinformax.vinmart.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import vinformax.vinmart.model.ProductCart;
import vinformax.vinmart.model.UserData;

/**
 * Created by Mobtech-04 on 7/4/2016.
 */
public class VinMartSqlLite extends SQLiteOpenHelper {

    public static Context dbcontext;
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "vinmart";
    private static final String ADDTOCART = "addtocart";
    private static final String ADDTOFAV = "addtofav";
    private static final String STOREDATA = "storedata";
    private static final String ADDUSER = "userDetail";

    private static final String STOREADDRESS = "useraddress";
    // Contacts table name
    private static final String TABLE_LOGINDETAILS = "vinmarttb";
    public static int grandTotal = 0;
    public static int grandSubTotal = 0;

    public VinMartSqlLite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        dbcontext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {

            String ADDTOCARTDATA = "CREATE TABLE IF NOT EXISTS addtocart (productId varchar, productName varchar, quantity varchar, productPrice varchar," +
                    "productDiscountPrice varchar, productDiscountPercentage varchar, productTotal varchar, productImageName varchar, status varchar)";
            db.execSQL(ADDTOCARTDATA);

            String ADDTOFAVDATA = "CREATE TABLE IF NOT EXISTS addtofav (productId varchar, productName varchar, productPrice varchar," +
                    "productDiscountPrice varchar, productDiscountPercentage varchar,  productImageName varchar, status varchar)";
            db.execSQL(ADDTOFAVDATA);


            String ADDUSERADDRESS = "CREATE TABLE IF NOT EXISTS useraddress(etcontactName varchar, etcountry varchar, etaddressOne varchar, etaddressTwo varchar, city varchar, etstate varchar, etpinCode varchar, etmobileNumber varchar, etemailId varchar)";

            db.execSQL(ADDUSERADDRESS);


            String CREATE_CONTACTS_TABLE = "create table if not exists " + TABLE_LOGINDETAILS + "(userorexeloginid varchar,userorcustid varchar,name varchar,mailid varchar," +
                    "inputtype varchar, customernameglobal varchar, loginorlogoutstatus varchar)";

            db.execSQL(CREATE_CONTACTS_TABLE);


            String STOREDATA = "CREATE TABLE IF NOT EXISTS storedata (productName varchar, productPrice varchar," +
                    "productDiscountPrice varchar, productImageName varchar)";
            db.execSQL(STOREDATA);


            String ADDUSERDATA = "CREATE TABLE IF NOT EXISTS userDetail (userId varchar, userName varchar, userEmail varchar)";
            db.execSQL(ADDUSERDATA);





        } catch (SQLException sql) {
            sql.printStackTrace();
            sql.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + ADDTOCART);
        db.execSQL("DROP TABLE IF EXIST" + STOREDATA);
        db.execSQL("DROP TABLE IF EXIST" + ADDTOFAV);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGINDETAILS);
        db.execSQL("DROP TABLE IF EXISTS " + ADDUSER);
        db.execSQL("DROP TABLE IF EXISTS" + STOREADDRESS);
        onCreate(db);

    }


    public int AddtoCartDataBase(String pId, String pName, String pQuantity, String pPrice, String pDiscountPrice, String pDiscountPercentage,
                                 String pTotal, String pImageName, String pStatus) {
        int result = 0;

        try {
            String productId = "productName";

            SQLiteDatabase db = this.getWritableDatabase();
            SQLiteDatabase dbReadable = this.getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("productId", pId);
            contentValues.put("productName", pName);
            contentValues.put("quantity", pQuantity);
            contentValues.put("productPrice", pPrice);
            contentValues.put("productDiscountPrice", pDiscountPrice);
            contentValues.put("productDiscountPercentage", pDiscountPercentage);
            contentValues.put("productTotal", pTotal);
            contentValues.put("productImageName", pImageName);
            contentValues.put("status", pStatus);

            Cursor cursor = dbReadable.query(ADDTOCART, null,
                    productId + "=?", new String[]{pName}, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                result = 0;
            } else {
                db.insert(ADDTOCART, null, contentValues);
                result = 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            result = -1;
        }
        return result;
    }

    public  int AddaddressDetails(String Name, String Country, String Addressone, String Addresstwo, String City,
                                  String State, String Pincode, String Mobilenumber, String mailid)
    {
        int result = 0;
        try
        {
            SQLiteDatabase db = this.getWritableDatabase();
            //SQLiteDatabase dbReadable = this.getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("etcontactName",Name);
            contentValues.put("etcountry",Country);
            contentValues.put("etaddressOne",Addressone);
            contentValues.put("etaddressTwo",Addresstwo);
            contentValues.put("city",City);
            contentValues.put("etstate",State);
            contentValues.put("etpinCode",Pincode);
            contentValues.put("etmobileNumber",Mobilenumber);
            contentValues.put("etemailId",mailid);
            db.insert(STOREADDRESS, null, contentValues);
            result = 1;

        }
        catch (Exception e)
        {
            e.getMessage();
            e.printStackTrace();
            result = -1;
        }
        return result;
    }


    public int AddtoFavDataBase(String pId, String pName, String pPrice, String pDiscountPrice, String pDiscountPercentage,
                                String pImageName, String pStatus) {
        int result = 0;

        try {
            String productId = "productName";
            SQLiteDatabase db = this.getWritableDatabase();
            SQLiteDatabase dbReadable = this.getReadableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("productId", pId);
            contentValues.put("productName", pName);
            contentValues.put("productPrice", pPrice);
            contentValues.put("productDiscountPrice", pDiscountPrice);
            contentValues.put("productDiscountPercentage", pDiscountPercentage);
            contentValues.put("productImageName", pImageName);
            contentValues.put("status", pStatus);
            Cursor cursor = dbReadable.query(ADDTOFAV, null,
                    productId + "=?", new String[]{pName}, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {
                result = 0;

            } else {
                db.insert(ADDTOFAV, null, contentValues);
                result = 1;
            }

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            result = -1;
        }
        return result;
    }

    //---deletes a particular title---
   /* public boolean deleteTitle(long rowId)
    {
        String productId = "productName";
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(ADDTOFAV, productId +
                "=" + rowId, null) > 0;
    }*/

    public int deleteItem(String product_id){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DELETE FROM addtofav WHERE productName ='"+product_id+"'");
            db.close();
            return 1;
        }
        catch (Exception e){
            e.printStackTrace();
            return 0;
        }




    }
    public int storedata(String pName, String pPrice, String pDiscountPrice) {
        int result = 0;
        try {
            String productId = "productName";

            SQLiteDatabase db = this.getWritableDatabase();
            SQLiteDatabase dbReadable = this.getReadableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put("productName", pName);

            contentValues.put("productPrice", pPrice);
            contentValues.put("productDiscountPrice", pDiscountPrice);

            db.insert(STOREDATA, null, contentValues);

        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            result = -1;
        }
        return result;
    }


    public int addUserDetail(String userId, String userName, String userEmail) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("userId", userId);
            contentValues.put("userName", userName);
            contentValues.put("userEmail",userEmail);
            db.insert(ADDUSER, null, contentValues);

            return 1;
        } catch (Exception t) {
            return 0;
        }

    }


    public List<ProductCart> getFavDetails() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<ProductCart> favProduct = new ArrayList<ProductCart>();
        String ScuroserQuery = "SELECT * FROM " + ADDTOFAV;
        Cursor cursor = db.rawQuery(ScuroserQuery, null);
        try {

            if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {

                do {

                    ProductCart pc = new ProductCart();

                    pc.setCartProductId(cursor.getString(cursor.getColumnIndex("productId")));
                    pc.setCartProductName(cursor.getString(cursor.getColumnIndex("productName")));
                    pc.setCartActualPrice(cursor.getString(cursor.getColumnIndex("productPrice")));
                    pc.setCartDiscountPrice(cursor.getString(cursor.getColumnIndex("productDiscountPrice")));
                    pc.setCartDiscountPercentage(cursor.getString(cursor.getColumnIndex("productDiscountPercentage")));
                    pc.setCartImage(cursor.getString(cursor.getColumnIndex("productImageName")));
                    pc.setCartOrderStatus(cursor.getString(cursor.getColumnIndex("status")));
                    favProduct.add(pc);

                }
                while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
        return favProduct;

    }

    public List<ProductCart> getCartDetails() {

        SQLiteDatabase db = this.getWritableDatabase();
        List<ProductCart> cartProduct = new ArrayList<ProductCart>();
        String ScuroserQuery = "SELECT * FROM " + ADDTOCART;
        Cursor cursor = db.rawQuery(ScuroserQuery, null);
        try {

            if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {

                grandSubTotal = 0;
                grandTotal = 0;
                do {

                    ProductCart pc = new ProductCart();

                    pc.setCartProductId(cursor.getString(cursor.getColumnIndex("productId")));
                    pc.setCartProductName(cursor.getString(cursor.getColumnIndex("productName")));
                    pc.setCartQuantity(cursor.getString(cursor.getColumnIndex("quantity")));
                    pc.setCartActualPrice(cursor.getString(cursor.getColumnIndex("productPrice")));
                    pc.setCartDiscountPrice(cursor.getString(cursor.getColumnIndex("productDiscountPrice")));
                    pc.setCartDiscountPercentage(cursor.getString(cursor.getColumnIndex("productDiscountPercentage")));
                    pc.setCartImage(cursor.getString(cursor.getColumnIndex("productImageName")));
                    pc.setCartTotal(cursor.getString(cursor.getColumnIndex("productTotal")));
                    pc.setCartOrderStatus(cursor.getString(cursor.getColumnIndex("status")));
                    cartProduct.add(pc);

                    grandSubTotal = Integer.parseInt(cursor.getString(cursor.getColumnIndex("productTotal")));
                    grandTotal = grandTotal + grandSubTotal;

                }
                while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
        return cartProduct;
    }
    public int addlLoginDetails(String userorexeloginid, String userorcustid, String name, String mailid,
                                String inputtype, String customernameglobal, String loginorlogoutstatus) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("insert into " + TABLE_LOGINDETAILS + " values('" + userorexeloginid + "','" + userorcustid + "','" + name + "','" + mailid + "','" + inputtype + "','" + customernameglobal + "','" + loginorlogoutstatus + "');");
            db.close(); // Closing database connection
            return 1;
        } catch (Exception t) {
            return 0;
        }
    }
    public List<UserData> getUserDetails() {

        SQLiteDatabase db = this.getWritableDatabase();
        List<UserData> userdata = new ArrayList<>();
        String ScuroserQuery = "SELECT * FROM " + TABLE_LOGINDETAILS;
        Cursor cursor = db.rawQuery(ScuroserQuery, null);
        try {

            if (cursor != null && cursor.getCount() > 0 && cursor.moveToFirst()) {

                do {

                    UserData uc = new UserData();

                    uc.setUsername(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                    uc.setUseremail(cursor.getString(cursor.getColumnIndexOrThrow("mailid")));
                    uc.setUserloginid(cursor.getString(cursor.getColumnIndexOrThrow("userorcustid")));
                    uc.setUsercustid(cursor.getString(cursor.getColumnIndexOrThrow("customernameglobal")));

                    userdata.add(uc);
                    String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                    String email = cursor.getString(cursor.getColumnIndexOrThrow("mailid"));
                    String userid = cursor.getString(cursor.getColumnIndexOrThrow("userorcustid"));
                    String custid = cursor.getString(cursor.getColumnIndexOrThrow("customernameglobal"));

                    Log.d("email", email);
                    Log.d("name", name);
                    Log.d("userid",userid);
                    Log.d("custid",custid);
                }
                while (cursor.moveToNext());
            }

        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
            userdata = null;
        }
        return userdata;

    }

    public int deletelogindetails() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGINDETAILS);
            onCreate(db);
            db.close(); // Closing database connection
            return 1;
        } catch (Exception t) {
            return 0;
        }
    }
}
