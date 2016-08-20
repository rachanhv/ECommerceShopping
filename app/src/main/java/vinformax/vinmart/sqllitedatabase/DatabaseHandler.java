package vinformax.vinmart.sqllitedatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import vinformax.vinmart.common.SplashScreensActivity;

public class DatabaseHandler extends SQLiteOpenHelper {
    public static Context dbcontxt;
    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "vinmartdb";

    // Contacts table name
    private static final String TABLE_LOGINDETAILS = "vinmarttb";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        dbcontxt = context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "create table if not exists " + TABLE_LOGINDETAILS + "(userorexeloginid varchar,userorcustid varchar,name varchar,mailid varchar," +
                "inputtype varchar,favids varchar,cartlist varchar,customernameglobal varchar, loginorlogoutstatus varchar)";
//		addContact(Assignallvarible.UserorExecutivesID, Assignallvarible.customerid_cartandfav, Assignallvarible.loginst, Assignallvarible.loginemailid, Assignallvarible.loginusertype, "", "");

//		String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
//				+ KEY_ID + " INTEGER PRIMARY KEY," + KEY_NAME + " TEXT,"
//				+ KEY_PH_NO + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGINDETAILS);

        // Create tables again
        onCreate(db);
    }





    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new contact
    public int addlLoginDetails(String userorexeloginid, String userorcustid, String name, String mailid,
                                String inputtype, String favids, String cartlist, String customernameglobal, String loginorlogoutstatus) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL("insert into " + TABLE_LOGINDETAILS + " values('" + userorexeloginid + "','" + userorcustid + "','" + name + "','" + mailid + "','" + inputtype + "','" + favids + "','" + cartlist + "','" + customernameglobal + "','" + loginorlogoutstatus + "');");
            db.close(); // Closing database connection
            return 1;
        } catch (Exception t) {
            return 0;
        }

    }


    // Updating single contact
    public int updatefavourite(String favlist) {
        try {
            //SQLiteDatabase db = this.getWritableDatabase();
            SQLiteDatabase db = (SQLiteDatabase) (this).getWritableDatabase();
            db.execSQL("update " + TABLE_LOGINDETAILS + " set favids=" + favlist);
            db.close(); // Closing database connection
            return 1;
        } catch (Exception t) {
            return 0;
        }
    }
    public int updateCart(String cartlist) {
        try {
            //SQLiteDatabase db = this.getWritableDatabase();
            SQLiteDatabase db = (SQLiteDatabase) (this).getWritableDatabase();
            db.execSQL("update " + TABLE_LOGINDETAILS + " set cartlist='" + cartlist+"'");
            db.close(); // Closing database connection
            return 1;
        } catch (Exception t) {
            return 0;
        }
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

    // Getting contacts Count
//	String userorexeloginid,String userorcustid,String name,String mailid,
//	String inputtype,String favids, String cartlist
    public int getlogindetails() {
        try {

            SQLiteDatabase db = this.getReadableDatabase();
            String countQuery = "SELECT  * FROM " + TABLE_LOGINDETAILS;
//            Cursor res =  db.rawQuery("select * from " + TABLE_LOGINDETAILS + " where id=" + id + "", null);
            Cursor cursor = db.rawQuery(countQuery, null);
            int cnt = cursor.getCount();
            if (cnt > 0) {
                if (cursor.moveToFirst()) {

                    SplashScreensActivity.UserorExecutivesID = cursor.getString(0);
                    SplashScreensActivity.userorcustID = cursor.getString(1);
                    SplashScreensActivity.loginst = cursor.getString(2);
                    SplashScreensActivity.loginemailid = cursor.getString(3);
                    SplashScreensActivity.loginusertype = cursor.getString(4);
                    SplashScreensActivity.sqllitefav = cursor.getString(5);
                    SplashScreensActivity.sqllitecart = cursor.getString(6);
                    SplashScreensActivity.customer_nameglobal = cursor.getString(7);
                    SplashScreensActivity.loginorlogoutstatus = cursor.getString(8);
                    SplashScreensActivity.customerid_cartandfav = cursor.getString(0);
                    if (SplashScreensActivity.loginusertype.trim().equals("Executives")) {
                        SplashScreensActivity.customer_Executive = SplashScreensActivity.loginst;
                    } else {
                        SplashScreensActivity.customer_Executive = "";
                    }
                }
            } else {
                SplashScreensActivity.customer_Executive = "";
                SplashScreensActivity.loginst = "1";
            }
            cursor.close();
            return cnt;
        } catch (Exception ee) {
            return 0;
        }
        // return count
    }

}
