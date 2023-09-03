package com.tanejkbd.dvtanejk.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.tanejkbd.dvtanejk.R;
import com.tanejkbd.dvtanejk.fragments.LoginFragment;

public class LoginActivity extends AppCompatActivity {

    private static final int APP_PACKAGE_DOT_COUNT = 3; // number of dots present in package name
    private static final String DUAL_APP_ID_999 = "apps.clone.cloud.multiple.space.bit64";
    private static final char DOT = '.';

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        checkAppCloning();
    }


    private void checkAppCloning()
    {
        String path = getFilesDir().getPath();
        if (path.contains(DUAL_APP_ID_999))
        {
            killProcess();
        } else
        {
            int count = getDotCount(path);
            if (count > APP_PACKAGE_DOT_COUNT)
            {
                killProcess();     
            } else {
                getSupportFragmentManager().beginTransaction().add(R.id.frame_layout_Login, LoginFragment.newInstance()).commit();
            }
        }
    }

    private int getDotCount(String path)
    {
        int count = 0;
        for (int i = 0; i < path.length(); i++)
        {
            if (count > APP_PACKAGE_DOT_COUNT)
            {
                break;
            }
            if (path.charAt(i) == DOT)
            {
                count++;
            }
        }
        return count;
    }

    private void killProcess()
    {
        finish();
        android.os.Process.killProcess( android.os.Process.myPid());
    }

}