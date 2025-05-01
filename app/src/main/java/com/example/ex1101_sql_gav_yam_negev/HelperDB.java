package com.example.ex1101_sql_gav_yam_negev;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;


public class HelperDB extends android.database.sqlite.SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dbexam.db";
    private static final int DATABASE_VERSION = 1;
    String strCreate, strDelete;

    public HelperDB(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        strCreate="CREATE TABLE "+Workers.TABLE_WORKERS;
        strCreate+=" ("+Workers.KEY_ID+" INTEGER PRIMARY KEY,";
        strCreate+=" "+Workers.CARD_ID+" TEXT,";
        strCreate+=" "+Workers.LAST_NAME+" TEXT,";
        strCreate+=" "+Workers.NAME+" TEXT,";
        strCreate+=" "+Workers.WORKER_COMPANY+" TEXT,";
        strCreate+=" "+Workers.PERSONAL_ID+" TEXT,";
        strCreate+=" "+Workers.PHONE_NUMBER+" TEXT,";
        strCreate+=" "+Workers.IS_WORKING+" INTEGER";
        strCreate+=");";
        db.execSQL(strCreate);

        strCreate="CREATE TABLE "+FoodCompany.TABLE_FOOD_COMPANY;
        strCreate+=" ("+FoodCompany.KEY_ID_FoodC+" INTEGER PRIMARY KEY,";
        strCreate+=" "+FoodCompany.COMPANY_NUMBER+" TEXT,";
        strCreate+=" "+FoodCompany.COMPANY_NAME+" TEXT,";
        strCreate+=" "+FoodCompany.C_FIRST_PHONE_NUMBER+" TEXT,";
        strCreate+=" "+FoodCompany.C_SECOND_PHONE_NUMBER+" TEXT,";
        strCreate+=" "+FoodCompany.IS_WORKING_COMPANY+" INTEGER";
        strCreate+=");";
        db.execSQL(strCreate);

        strCreate="CREATE TABLE "+Meals.TABLE_MEALS;
        strCreate+=" ("+Meals.KEY_ID_MEALS+" INTEGER PRIMARY KEY,";
        strCreate+=" "+Meals.APPETIZER+" TEXT,";
        strCreate+=" "+Meals.MAIN_COURSE+" TEXT,";
        strCreate+=" "+Meals.EXTRA+" TEXT,";
        strCreate+=" "+Meals.DESSERT+" TEXT,";
        strCreate+=" "+Meals.DRINK+" TEXT";
        strCreate+=");";
        db.execSQL(strCreate);

        strCreate="CREATE TABLE "+Order_Details.TABLE_ORDER_DETAILS;
        strCreate+=" ("+Order_Details.KEY_ID_OD+" INTEGER PRIMARY KEY,";
        strCreate+=" "+Order_Details.DATE+" TEXT,";
        strCreate+=" "+Order_Details.TIME+" TEXT,";
        strCreate+=" "+Order_Details.WORKER_ID+" TEXT,";
        strCreate+=" "+ Order_Details.FOOD_COMPANY+" TEXT";
        strCreate+=");";
        db.execSQL(strCreate);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        strDelete="DROP TABLE IF EXISTS "+Workers.TABLE_WORKERS;
        db.execSQL(strDelete);
        strDelete="DROP TABLE IF EXISTS "+FoodCompany.TABLE_FOOD_COMPANY;
        db.execSQL(strDelete);
        strDelete="DROP TABLE IF EXISTS "+Meals.TABLE_MEALS;
        db.execSQL(strDelete);
        strDelete="DROP TABLE IF EXISTS "+Order_Details.TABLE_ORDER_DETAILS;
        db.execSQL(strDelete);


        onCreate(db);

    }
}
