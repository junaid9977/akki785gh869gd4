package com.tanejkbd.dvtanejk.activity;

import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.tanejkbd.dvtanejk.R;
import com.tanejkbd.dvtanejk.fragments.HomeFragment;
import com.tanejkbd.dvtanejk.fragments.ProfileFragment;
import com.tanejkbd.dvtanejk.fragments.ReferFragment;
import com.tanejkbd.dvtanejk.utils.Constant;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.ibrahimsn.lib.OnItemReselectedListener;
import me.ibrahimsn.lib.OnItemSelectedListener;
import me.ibrahimsn.lib.SmoothBottomBar;


public class MainActivity extends AppCompatActivity {
    MainActivity activity;
    SmoothBottomBar bottomBar;
    MediaPlayer popupSound;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activity = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow();
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        popupSound = MediaPlayer.create(activity, R.raw.popup);

        loadFragment(new HomeFragment());
        bottomBar = findViewById(R.id.bottomBar);
        bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                switch (i) {
                    case 0:
                        loadFragment(new HomeFragment());
                        break;
                    case 1:
                        loadFragment(new ReferFragment());
                        break;
                    case 2:
                        Constant.GotoNextActivity(activity, GiftCardActivity.class, "");
                        overridePendingTransition(R.anim.enter, R.anim.exit);
                        break;
                    case 3:
                        loadFragment(new ProfileFragment());
                        break;
                }
                return true;
            }
        });
        bottomBar.setOnItemReselectedListener(new OnItemReselectedListener() {
            @Override
            public void onItemReselect(int i) {
                switch (i) {
                    case 0:
                        loadFragment(new HomeFragment());
                        break;
                    case 1:
                        loadFragment(new ReferFragment());
                        break;
                    case 2:
                        Constant.GotoNextActivity(activity, GiftCardActivity.class, "");
                        overridePendingTransition(R.anim.enter, R.anim.exit);
                        break;
                    case 3:
                        loadFragment(new ProfileFragment());
                        break;
                }
            }
        });

    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout_main, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (Constant.getString(activity, "exit").equalsIgnoreCase("exit")) {
            showExitDialog();
        } else {
            if (getFragmentManager().getBackStackEntryCount() > 0) {
                getFragmentManager().popBackStack();
            } else {
                super.onBackPressed();
            }
        }
    }

    private void showExitDialog() {
        popupSound.start();
        new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("You want to exit?")
                .setConfirmText("Yes")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                        finish();
                    }
                })
                .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                    }
                })
                .show();
    }
}