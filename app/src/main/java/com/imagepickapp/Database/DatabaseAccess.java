package com.imagepickapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.imagepickapp.Model.ImageModel;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Hasib on 05-02-2018.
 */

public class DatabaseAccess extends DatabaseHelper {

    private static String TAG = "DatabaseAccess";

    private static DatabaseAccess databaseAccess = null;


    private DatabaseAccess(Context context) {
        super(context);
    }

    public static DatabaseAccess getInstance(Context context) {
        try {
            if (databaseAccess == null)
                databaseAccess = new DatabaseAccess(context);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return databaseAccess;
    }



    /*below drug insertion*/


    public void InsertImagesInImageDB(ImageModel imageModel) {
        SQLiteDatabase db = super.getWritableDatabase();
        db.beginTransaction();
        ContentValues values = new ContentValues();
        try {

            values.put(COLUMN_FILEPATH, imageModel.filePath);
            values.put(COLUMN_FILENAME, imageModel.fileName);

            long _id = db.insertWithOnConflict(TABLE_IMAGE, null,
                    values, SQLiteDatabase.CONFLICT_IGNORE);

            if (_id == -1) {

                db.update(TABLE_IMAGE, values, COLUMN_FILEPATH + "= '"
                        + imageModel.filePath + "'", null);
            }

            db.setTransactionSuccessful();
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " InsertImagesInImageDB : " + ex.getMessage());
        } finally {
            db.endTransaction();
            values.clear();
            db.close();
        }
    }

    public ArrayList<ImageModel> GetImageList(Cursor cursor) {

        ArrayList<ImageModel> orderItemsArrayList = new ArrayList<>();

        try {

            if (cursor.moveToFirst()) {

                while (!cursor.isAfterLast()) {
                    try {
                        ImageModel drugModel = new ImageModel();

                        drugModel = GetImageModel(cursor);

                        if (drugModel != null)
                            orderItemsArrayList.add(drugModel);

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                    cursor.moveToNext();
                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " GetImageList() : " + ex.getMessage());
        }

        return orderItemsArrayList;
    }

    public ImageModel GetImageModel(Cursor cursor) {

        ImageModel drugModel = new ImageModel();
        try {
            drugModel.filePath = cursor.getString(cursor.getColumnIndex(COLUMN_FILEPATH));
            drugModel.fileName = cursor.getString(cursor.getColumnIndex(COLUMN_FILENAME));
        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " GetImageModel() : " + ex.getMessage());
            drugModel = null;
        }

        return drugModel;
    }

    /*Fetch customer details from order db*/

    public ArrayList<ImageModel> GetImageListFromImageDB() {
        SQLiteDatabase db = super.getWritableDatabase();
        Cursor cursor = null;
        ArrayList<ImageModel> imageModels = new ArrayList<>();

        try {

            cursor = db.rawQuery(DatabaseQuery.GetQueryForImageInImageDB(), null);

            if (cursor.getCount() > 0)
                imageModels = GetImageList(cursor);

        } catch (Exception ex) {
            ex.printStackTrace();
            Log.i(TAG, " GetImageListFromImageDB() : " + ex.getMessage());
        }

        if (cursor != null)
            cursor.close();
        db.close();
        return imageModels;
    }

}
