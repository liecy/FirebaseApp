package com.rahmi.loginapps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String databaseName = "new_apps.db";

    public DatabaseHelper(Context context) {
        super(context, databaseName, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE session(id integer PRIMARY KEY, login text)");
        db.execSQL("CREATE TABLE user(id integer PRIMARY KEY AUTOINCREMENT, username text, password text)");
        //db.execSQL("INSERT INTO session(id, login) VALUES(1,'kosong')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        //db.execSQL("DROP TABLE IF EXISTS session");
        db.execSQL("DROP TABLE IF EXISTS user");
        onCreate(db);
    }

    //Check Session
    public Boolean checkSession(String value){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cs = db.rawQuery("SELECT * FROM session WHERE login = ?",new String[]{value});
        return cs.getCount() > 0;
    }

    //Upgrade Session
    public Boolean upgradeSession(String value, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("login", value);
        long update = db.update("session",cv, "id="+id,null);
        return update != -1;
    }

    //Save User Creadential
    public Boolean saveUser(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("password", password);
        long insert = db.insert("user",null,cv);
        return insert != -1;
    }

    //Check Login User
    public Boolean checkLogin(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cs = db.rawQuery("SELECT * FROM user WHERE username = ? AND password = ?",new String[]{username, password});
        return cs.getCount() > 0;
    }

    //Retrieve User Password SQLite
    public String retrievePassword(String username){
        String password = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT password FROM user WHERE username = ?", new String[]{username});
        if (cursor.moveToFirst()) {
            int columnIndex = cursor.getColumnIndex("password");
            if (columnIndex != -1) {
                password = cursor.getString(columnIndex);
            }
        }
        cursor.close();
        return password;
    }
}

