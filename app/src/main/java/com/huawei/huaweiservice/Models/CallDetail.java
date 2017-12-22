package com.huawei.huaweiservice.Models;

public class CallDetail {
    public static final String TAG = CallDetail.class.getSimpleName();
    public static final String TABLE = "CallDetail";
    public static long id;
    public static String fromToNumber;
    public static String callDayTime;
    public static String callDuration;
    public static String geoLocation;
    public static String callTypeCode;
    public static String callType; // INCOMING(1), OUTGOING(2), CONFERENCING (3);
    public static String lastModifiedDate;
    public static String answeringMethod; //RECEIVED(1), DECLINED(2), MISSED(3);
    public static String recordingId;
    public static String phoneAccountComponentName;
    public static String viaNumber;
    public static String numberInUse;
    public static String isRead;
}