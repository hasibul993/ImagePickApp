package com.imagepickapp.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by BookMEds on 05-02-2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database filePath
    private static final String DATABASE_NAME = "ImageDB";
    // Database Version
    private static final int DATABASE_VERSION = 1;
    private static String TAG = "DatabaseHelper";
    protected static Context con;


    ////query
    public static final String SELECT_ALL = "select * from ";
    public static final String WHERE = " WHERE ";
    public static final String ORDER_BY = " ORDER BY ";
    public static final String ALPHABETICAL_OREDER = " COLLATE NOCASE ";
    public static final String DESCENDING_OREDER = " DESC ";
    public static final String ASCENDING_OREDER = " ASC ";
    public static final String LIMIT_8 = " LIMIT 8 ";
    public static final String LIMIT_1 = " LIMIT 1 ";

    // Table name
    protected static final String TABLE_IMAGE = "Image";


    // Table Columns names - TABLE_IMAGE
    protected static final String COLUMN_FILEPATH = "filePath";
    protected static final String COLUMN_FILENAME = "fileName";


    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        con = context;
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {

            db.execSQL(QueryToCreateSongsTable());

        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " onCreate : " + ex.getMessage());
        }


    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {

        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " onUpgrade : " + ex.getMessage());
        }
    }

    ///this is to see the database table
    public ArrayList<Cursor> getData(String Query) {
        ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
        try {
            //get writable database
            SQLiteDatabase sqlDB = this.getWritableDatabase();
            String[] columns = new String[]{"message"};
            //an array list of cursor to save two cursors one has results from the query
            //other cursor stores error message if any errors are triggered

            MatrixCursor Cursor2 = new MatrixCursor(columns);
            alc.add(null);
            alc.add(null);


            try {
                String maxQuery = Query;
                //execute the query results will be save in Cursor c
                Cursor c = sqlDB.rawQuery(maxQuery, null);


                //add value to cursor2
                Cursor2.addRow(new Object[]{"Success"});

                alc.set(1, Cursor2);
                if (null != c && c.getCount() > 0) {


                    alc.set(0, c);
                    c.moveToFirst();

                    return alc;
                }
                return alc;
            } catch (SQLException sqlEx) {
                Log.d("printing exception", sqlEx.getMessage());
                //if any exceptions are triggered save the error message to cursor an return the arraylist
                Cursor2.addRow(new Object[]{"" + sqlEx.getMessage()});
                alc.set(1, Cursor2);
                return alc;
            } catch (Exception ex) {

                Log.d("printing exception", ex.getMessage());

                //if any exceptions are triggered save the error message to cursor an return the arraylist
                Cursor2.addRow(new Object[]{"" + ex.getMessage()});
                alc.set(1, Cursor2);
                return alc;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return alc;
    }


    public String QueryToCreateSongsTable() {
        return "CREATE TABLE "
                + TABLE_IMAGE + "(" + COLUMN_FILEPATH + " TEXT PRIMARY KEY NOT NULL ,"
                + COLUMN_FILENAME + " TEXT )";
    }

}
