package com.huawei.huaweiservice;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.huawei.huaweiservice.Models.CallDetail;
import com.huawei.huaweiservice.Repository.CallDetailRepo;


public class DBHandler  extends SQLiteOpenHelper {
    //version number to upgrade database version
    //each time if you Add, Edit table, you need to change the
    //version number.
    private static final int DATABASE_VERSION =1;
    // Database Name
    private static final String DATABASE_NAME = "huaweiservice.db";
    private static final String TAG = DBHandler.class.getSimpleName().toString();

    public DBHandler( ) {
        super(App.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //All necessary tables you like to create will create here
        db.execSQL(CallDetailRepo.createTable());
        //db.execSQL(StudentRepo.createTable());
        //db.execSQL(MajorRepo.createTable());
        //db.execSQL(StudentCourseRepo.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, String.format("SQLiteDatabase.onUpgrade(%d -> %d)", oldVersion, newVersion));

        // Drop table if existed, all data will be gone!!!
        db.execSQL("DROP TABLE IF EXISTS " + CallDetail.TABLE);
        //db.execSQL("DROP TABLE IF EXISTS " + Student.TABLE);
        //db.execSQL("DROP TABLE IF EXISTS " + Major.TABLE);
        //db.execSQL("DROP TABLE IF EXISTS " + StudentCourse.TABLE);
        onCreate(db);
    }

}