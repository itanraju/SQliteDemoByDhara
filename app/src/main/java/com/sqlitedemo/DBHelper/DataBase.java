package com.sqlitedemo.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.sqlitedemo.Model.ThemelModel;

import java.util.ArrayList;

public class DataBase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "database";
    public static final String TABLE_NAME = "master";
    public static final String ID = "id";
    public static final String THEME_NAME = "theme_name";
    public static final String SMALL_THUMBNAIL = "small_thumbnail";


    public DataBase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String Create_Table = "CREATE TABLE " + TABLE_NAME + "("
                + ID + " INTEGER,"
                + THEME_NAME + " TEXT,"
                + SMALL_THUMBNAIL + " BLOB)";

        db.execSQL(Create_Table);
    }

    public void InsertImage(ThemelModel themelModel) {
        Log.i("Db", "Insrt Data" + themelModel.getThemeName());
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ID, themelModel.getThemeid());
        values.put(THEME_NAME, themelModel.getThemeName());
        values.put(SMALL_THUMBNAIL, themelModel.getPic());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public boolean Isidexists(ThemelModel themelModel1) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = null;
        String checkQuery = "SELECT " + ID + " FROM " + TABLE_NAME + " WHERE " + ID + "= '" + themelModel1.getThemeid() + "'";
        cursor = db.rawQuery(checkQuery, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    public ArrayList<ThemelModel> getAllUser() {
        // array of columns to fetch
        String[] columns = {
                ID,
                THEME_NAME,
                SMALL_THUMBNAIL
        };
        // sorting orders
        String sortOrder =
                THEME_NAME + " ASC";
        ArrayList<ThemelModel> userList = new ArrayList<ThemelModel>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);

        if (cursor.moveToFirst()) {
            do {
                ThemelModel model = new ThemelModel();
                model.setThemeid(cursor.getString(cursor.getColumnIndex(ID)));
                model.setThemeName(cursor.getString(cursor.getColumnIndex(THEME_NAME)));
                model.setPic(cursor.getBlob(cursor.getColumnIndex(SMALL_THUMBNAIL)));

                // Adding user record to list
                userList.add(model);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // return user list
        return userList;
    }
}
