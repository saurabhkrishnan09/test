package com.huawei.huaweiservice;

import android.Manifest;
import android.animation.TypeConverter;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.CallLog;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ContentResolver;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import com.huawei.huaweiservice.Models.CallDetail;
import com.huawei.huaweiservice.Repository.CallDetailRepo;

import java.io.FileOutputStream;
import java.util.Date;

public class getCallDetailsActivity extends AppCompatActivity implements View.OnClickListener {
    String data;
    Button b1;
    private String file = "mydata";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_get_call_details);
        TextView callDetails = findViewById(R.id.call);
        callDetails.setMovementMethod(new ScrollingMovementMethod());
        b1 = (Button) findViewById(R.id.btnSave);
        // b1.setOnClickListener(new View.OnClickListener() {
        listener();
        //});
        getCallDetails();
    }

    private void listener() {
        b1.setOnClickListener(this);
    }

    public void onClick(View v) {
        saveCallDetails(v);
    }

    private void saveCallDetails(View view) {
        TextView callDetails = findViewById(R.id.call);
        data = callDetails.getText().toString();
        try {
            FileOutputStream fOut = openFileOutput(file, MODE_PRIVATE);
            fOut.write(data.getBytes());
            fOut.close();
            Toast.makeText(getBaseContext(), "file saved", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void getCallDetails() {
        TextView callDetails = findViewById(R.id.call);
        ContentResolver cr = getContentResolver();
        Cursor managedCursor = null;
        try {
            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CALL_LOG) == PackageManager.PERMISSION_GRANTED) {
                managedCursor = cr.query(CallLog.Calls.CONTENT_URI, null, null, null, null);
            }
            int number = managedCursor.getColumnIndex(CallLog.Calls.NUMBER);
            int type = managedCursor.getColumnIndex(CallLog.Calls.TYPE);
            int date = managedCursor.getColumnIndex(CallLog.Calls.DATE);
            int duration = managedCursor.getColumnIndex(CallLog.Calls.DURATION);
            int geocodedlocation = managedCursor.getColumnIndex(CallLog.Calls.GEOCODED_LOCATION);
            int lastmodified = managedCursor.getColumnIndex(CallLog.Calls.LAST_MODIFIED);
            int isread = managedCursor.getColumnIndex(CallLog.Calls.IS_READ);
            int phoneaccountcomponentname = managedCursor.getColumnIndex(CallLog.Calls.PHONE_ACCOUNT_COMPONENT_NAME);
            int phoneaccountid = managedCursor.getColumnIndex(CallLog.Calls.PHONE_ACCOUNT_ID);
            int vianumber = managedCursor.getColumnIndex(CallLog.Calls.VIA_NUMBER);


            List<CallDetail> lcd = null;
            CallDetail cd;
            while (managedCursor.moveToNext()) {
                cd = new CallDetail();
                cd.fromToNumber = managedCursor.getString(number);
                cd.callTypeCode = managedCursor.getString(type);
                cd.callDayTime = String.valueOf(new Date(Long.valueOf(managedCursor.getString(date))));
                cd.callDuration = managedCursor.getString(duration);
                cd.geoLocation = managedCursor.getString(geocodedlocation);
                cd.lastModifiedDate = String.valueOf(new Date(Long.valueOf(managedCursor.getString(lastmodified))));
                cd.isRead = managedCursor.getString(isread);
                cd.phoneAccountComponentName = managedCursor.getString(phoneaccountcomponentname);
                cd.viaNumber = managedCursor.getString(vianumber);
                cd.numberInUse = managedCursor.getString(phoneaccountid);
                cd.callType = null;
                switch (type) {
                    case CallLog.Calls.OUTGOING_TYPE:
                        cd.callType = "OUTGOING";
                        break;
                    case CallLog.Calls.INCOMING_TYPE:
                        cd.callType = "INCOMING";
                        break;
                    case CallLog.Calls.MISSED_TYPE:
                        cd.callType = "MISSED";
                        break;
                    case CallLog.Calls.ANSWERED_EXTERNALLY_TYPE:
                        cd.callType = "ANSWERED EXTERNALLY";
                        break;
                    case CallLog.Calls.BLOCKED_TYPE:
                        cd.callType = "BLOCKED";
                        break;
                    case CallLog.Calls.REJECTED_TYPE:
                        cd.callType = "REJECTED";
                        break;
                    case CallLog.Calls.VOICEMAIL_TYPE:
                        cd.callType = "VOICEMAIL";
                        break;
                }
                lcd.add(cd);
            }
            managedCursor.close();
            CallDetailRepo cdR = new CallDetailRepo();
            for (CallDetail cdd : lcd) {
                cdR.insert(cdd);
            }
            uploadToGDrive();
        } catch (Exception ex) {

        }
    }

    private void uploadToGDrive() {
        Context c = getApplicationContext();
        java.io.File dbFile = c.getDatabasePath("huaweiservice");
        //upload("temp.db", dbFile, "application/x-sqlite3");
        String x= "";
        int r = 4+3;
        int t= r;


    }
}