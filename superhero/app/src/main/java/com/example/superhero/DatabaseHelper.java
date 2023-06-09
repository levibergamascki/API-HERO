package com.example.superhero;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private final Context context;
    public static final String DATABASE_NAME = "superhero.db";
    public static final int DATABASE_VERSION = 2;
    public static final String USER_TABLE_NAME = "usuario";
    public static final String USER_COLUMN_ID = "_id";
    public static final String USER_COLUMN_NAME = "nome";
    public static final String USER_COLUMN_EMAIL = "email";
    public static final String USER_COLUMN_SENHA = "senha";



    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table " + USER_TABLE_NAME +
                        "( " + USER_COLUMN_ID + " integer primary key autoincrement," +
                        USER_COLUMN_NAME + " text , " +
                        USER_COLUMN_EMAIL + " text, " +
                        USER_COLUMN_SENHA + " text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+ USER_TABLE_NAME);
        onCreate(db);
    }
    public boolean Cadastrar(String user_name, String user_email, String user_password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(USER_COLUMN_NAME, user_name);
        cv.put(USER_COLUMN_EMAIL, user_email);
        cv.put(USER_COLUMN_SENHA, user_password);

        long resultInsert = db.insert(USER_TABLE_NAME, null, cv);

        if (resultInsert == -1){
            return false;
        } else {
            return true;
        }

    }
    public boolean checkemail(String user_email){
        SQLiteDatabase myDb = this.getWritableDatabase();
        Cursor cursor = myDb.rawQuery("SELECT * FROM " + USER_TABLE_NAME + " WHERE email = ?", new String[] {user_email});
        if(cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkpassword(String user_email, String user_senha) {
        SQLiteDatabase myDb = this.getWritableDatabase();
        Cursor cursor = myDb.rawQuery("SELECT * FROM " + USER_TABLE_NAME + " WHERE email = ? and senha = ?", new String[] {user_email, user_senha});
        if(cursor.getCount() > 0) {
            return true;
        } else {
            return false;
        }
    }
    public Cursor getdata(String user_email) {
        SQLiteDatabase myDb = this.getReadableDatabase();
        Cursor cursor = myDb.rawQuery("SELECT * FROM " + USER_TABLE_NAME + " WHERE email = ?", new String[]{user_email});
        return cursor;
    }

    public void updateData(String user_email, String user_image) {

        // Cursor cursor = myDb.rawQuery("update " + USER_TABLE_NAME + " SET " + USER_COLUMN_IMAGE + " = ?" + " where email = ?", new String[]{user_image, user_email});

        SQLiteDatabase myDb = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        myDb.update(USER_TABLE_NAME, cv, "email = ?", new String[]{user_email});
        myDb.close();
    }
}
