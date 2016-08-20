package vinformax.vinmart.search;


import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.provider.BaseColumns;

import java.util.HashMap;

public class CountryDB {

    private static final String DBNAME = "country";

    public static final String KEY_WORD = SearchManager.SUGGEST_COLUMN_TEXT_1;
    public static final String KEY_DEFINITION = SearchManager.SUGGEST_COLUMN_TEXT_2;
    private static final int VERSION = 2;

    private CountryDBOpenHelper mCountryDBOpenHelper;

    private static final String FIELD_ID = "_id";
    private static final String FIELD_NAME = "name";
    //private static final String FIELD_FLAG = "flag";
    //private static final String FIELD_CURRENCY = "currency";
    private static final String TABLE_NAME = "countries";
    private HashMap<String, String> mAliasMap;


    public CountryDB(Context context) {


        mCountryDBOpenHelper = new CountryDBOpenHelper(context, DBNAME, null, VERSION);

        // This HashMap is used to map table fields to Custom Suggestion fields
        mAliasMap = new HashMap<String, String>();

        // Unique id for the each Suggestions ( Mandatory )
        mAliasMap.put("_ID", FIELD_ID + " as " + "_id");

        // Text for Suggestions ( Mandatory )
        mAliasMap.put(SearchManager.SUGGEST_COLUMN_TEXT_1, FIELD_NAME + " as " + SearchManager.SUGGEST_COLUMN_TEXT_1);

        // Icon for Suggestions ( Optional )
        //	mAliasMap.put( SearchManager.SUGGEST_COLUMN_ICON_1, FIELD_FLAG + " as " + SearchManager.SUGGEST_COLUMN_ICON_1);

        // This value will be appended to the Intent data on selecting an item from Search result or Suggestions ( Optional )
        mAliasMap.put(SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID, FIELD_ID + " as " + SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID);
    }


    /**
     * Returns Countries
     */

    public Cursor getSuggestions(String query) {
        query = query.toLowerCase();
        String[] columns = new String[]{
                BaseColumns._ID,
                CountryDB.KEY_WORD,
                CountryDB.KEY_DEFINITION,
       /* SearchManager.SUGGEST_COLUMN_SHORTCUT_ID,
                        (only if you want to refresh shortcuts) */
                SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID};

        return getWordMatches(query, columns);
    }

    public Cursor getWordMatches(String query, String[] columns) {
        String selection = KEY_WORD + " MATCH ?";
        String[] selectionArgs = new String[]{query + "*"};

        return query(selection, selectionArgs, columns);

        /* This builds a query that looks like:
         *     SELECT <columns> FROM <table> WHERE <KEY_WORD> MATCH 'query*'
         * which is an FTS3 search for the query text (plus a wildcard) inside the word column.
         *
         * - "rowid" is the unique id for all rows but we need this value for the "_id" column in
         *    order for the Adapters to work, so the columns need to make "_id" an alias for "rowid"
         * - "rowid" also needs to be used by the SUGGEST_COLUMN_INTENT_DATA alias in order
         *   for suggestions to carry the proper intent data.
         *   These aliases are defined in the DictionaryProvider when queries are made.
         * - This can be revised to also search the definition text with FTS3 by changing
         *   the selection clause to use FTS_VIRTUAL_TABLE instead of KEY_WORD (to search across
         *   the entire table, but sorting the relevance could be difficult.
         */
    }

    public Cursor query(String selection, String[] selectionArgs, String[] columns) {
        /* The SQLiteBuilder provides a map for all possible columns requested to
         * actual columns in the database, creating a simple column alias mechanism
         * by which the ContentProvider does not need to know the real column names
         */
        SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
        builder.setTables(TABLE_NAME);
        builder.setProjectionMap(mAliasMap);
        Cursor cursor = null;
        try {
            cursor = builder.query(mCountryDBOpenHelper.getReadableDatabase(),
                    columns, selection, selectionArgs, null, null, null);
        } catch (Exception e) {
            System.out.println(e);
        }

        if (cursor == null) {

        } else if (!cursor.moveToFirst()) {
            cursor.close();

        }
        return cursor;
    }


    public Cursor getCountries(String[] selectionArgs) {

        String selection = FIELD_NAME + " like ? ";

        if (selectionArgs != null) {
            selectionArgs[0] = "%" + selectionArgs[0] + "%";
        }

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        queryBuilder.setProjectionMap(mAliasMap);

        queryBuilder.setTables(TABLE_NAME);

        Cursor c = queryBuilder.query(mCountryDBOpenHelper.getReadableDatabase(),
                new String[]{"_ID",
                        SearchManager.SUGGEST_COLUMN_TEXT_1,
                        //SearchManager.SUGGEST_COLUMN_ICON_1 ,
                        SearchManager.SUGGEST_COLUMN_INTENT_DATA_ID},
                selection,
                selectionArgs,
                null,
                null, null
//                FIELD_NAME + " asc ", "10"
        );
        if (c == null) {
            return null;
        } else if (!c.moveToFirst()) {
            c.close();
            return null;
        }
        return c;

    }

    /**
     * Return Country corresponding to the id
     */
    public Cursor getCountry(String id) {

        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

        queryBuilder.setTables(TABLE_NAME);

        Cursor c = queryBuilder.query(mCountryDBOpenHelper.getReadableDatabase(),
                new String[]{"_id", "name"},
                "_id = ?", new String[]{id}, null, null, null, "1"
        );

        return c;
    }


    class CountryDBOpenHelper extends SQLiteOpenHelper {

        public CountryDBOpenHelper(Context context,
                                   String name,
                                   CursorFactory factory,
                                   int version) {
            super(context, DBNAME, factory, VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String sql = "";

            // Defining table structure
            sql = " create table " + TABLE_NAME + "" + " ( " + FIELD_ID + " integer primary key autoincrement, "
                    + FIELD_NAME + " varchar(100) " + " ) ";

            // Creating table
            db.execSQL(sql);

            for (int i = 0; i < Country.countries.length; i++) {

                // Defining insert statement
                sql = "insert into " + TABLE_NAME + " ( " + FIELD_NAME + " ) " + " values ( " + " '" + Country.countries[i].replace("'","''") + "' ) ";

                // Inserting values into table
                db.execSQL(sql);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
        }
    }
}