package com.huawei.huaweiservice.Repository;

/**
 * Created by saurabhsaumya on 18-12-2017.
 */

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.huawei.huaweiservice.DatabaseManager;
import com.huawei.huaweiservice.Models.CallDetail;


public class CallDetailRepo {
    private CallDetail callDetail;

    public CallDetailRepo() {
        callDetail = new CallDetail();
    }

    public static String createTable() {
        return "CREATE TABLE " + CallDetail.TABLE + "("
                + CallDetail.id + " PRIMARY KEY INTEGER,"
                + CallDetail.fromToNumber + " TEXT,"
                + CallDetail.callDayTime + " TEXT,"
                + CallDetail.callDuration + " TEXT,"
                + CallDetail.callType + " TEXT,"
                + CallDetail.geoLocation + " TEXT,"
                + CallDetail.callTypeCode + " TEXT,"
                + CallDetail.answeringMethod + " TEXT,"
                + CallDetail.lastModifiedDate + " TEXT,"
                + CallDetail.phoneAccountComponentName + " TEXT,"
                + CallDetail.numberInUse + " TEXT,"
                + CallDetail.viaNumber + " TEXT,"
                + CallDetail.isRead + " TEXT,"
                + CallDetail.recordingId + " TEXT)";
    }


    public int insert(CallDetail cd) {
        int cdId;
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        ContentValues values = new ContentValues();
        values.put("id", cd.id);
        values.put("fromToNumber", cd.fromToNumber);
        values.put("callDayTime", cd.callDayTime);
        values.put("callDuration", cd.callDuration);
        values.put("callType", cd.callType);
        values.put("geoLocation", cd.geoLocation);
        values.put("callTypeCode", cd.callTypeCode);
        values.put("answeringMethod", cd.answeringMethod);
        values.put("lastModifiedDate", cd.lastModifiedDate);
        values.put("phoneAccountComponentName", cd.phoneAccountComponentName);
        values.put("recordingId", cd.recordingId);
        values.put("numberInUse", cd.numberInUse);
        values.put("isRead", cd.isRead);
        cdId = (int) db.insert(CallDetail.TABLE, null, values);
        DatabaseManager.getInstance().closeDatabase();
        return cdId;
    }

    public void delete(String whereClause, String[] whereArgs) {
        SQLiteDatabase db = DatabaseManager.getInstance().openDatabase();
        db.delete(CallDetail.TABLE, whereClause, whereArgs);
        DatabaseManager.getInstance().closeDatabase();
    }
}
