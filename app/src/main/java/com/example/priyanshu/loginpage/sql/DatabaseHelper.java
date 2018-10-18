package com.example.priyanshu.loginpage.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.priyanshu.loginpage.Model.User;

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NAME = "UserManager.db";
    private static final String TABLE_USER = "user";

    // User Table Columns names
    private static final String COLUMN_USER_ID = "id";
    private static final String COLUMN_USER_NAME = "username";
    private static final String COLUMN_USER_PASSWORD = "password";

    //create table sql query
    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";

    // drop table sql query
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop User Table if exist
        db.execSQL(DROP_USER_TABLE);

        // Create tables again
        onCreate(db);
    }

    public void addUser(User user)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());

        //Inserting row
        db.insert(TABLE_USER, null, values);
        db.close();
    }

    //method checks whether there is a user with this username or not
    public boolean isUserNameValid(String username)
    {
        //array of coulmns to fetch
        String[] coulmns = {
                COLUMN_USER_ID
        };

        SQLiteDatabase db = this.getReadableDatabase();

        //selection criteria
        String selection = COLUMN_USER_NAME + " = ?";

        //selection argument
        String[] selectionArgs = {username};

        //query user table with condition
        Cursor cursor = db.query(TABLE_USER,
                coulmns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if(cursorCount > 0){
            return true;
        }

        return false;
    }
    
    public boolean areCredentialsValid(String username, String password){
        //array of coulmns to fetch
        String[] coulmns = {
                COLUMN_USER_ID
        };

        SQLiteDatabase db = this.getReadableDatabase();

        //selection criteria
        String selection = COLUMN_USER_NAME + " = ?";

        //selection argument
        String[] selectionArgs = {username, password};

        //query user table with condition
        Cursor cursor = db.query(TABLE_USER,
                coulmns,
                selection,
                selectionArgs,
                null,
                null,
                null
        );

        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if(cursorCount > 0){
            return true;
        }

        return false;
    }
}
