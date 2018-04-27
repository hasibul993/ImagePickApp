package com.imagepickapp.Database;

import android.content.Context;


import org.apache.commons.lang3.StringUtils;


public class DatabaseQuery extends DatabaseHelper {

    public DatabaseQuery(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }


    public static String GetQueryForImageInImageDB() {
        String query = "";
        /*Like %text% will search string in whole name but like text% will start search from first letter*/
        try {

            query = SELECT_ALL + TABLE_IMAGE;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return query;
    }

}
