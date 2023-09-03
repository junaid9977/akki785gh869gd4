package com.tanejkbd.dvtanejk.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.Nullable;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.tanejkbd.dvtanejk.App;
import com.tanejkbd.dvtanejk.BuildConfig;
import com.tanejkbd.dvtanejk.utils.Constant;
import com.tanejkbd.dvtanejk.utils.CustomVolleyJsonRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateDateService extends Service {
    Thread thread;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String type = intent.getStringExtra("type");
        String date = intent.getStringExtra("date");
        String number = intent.getStringExtra("number");

        addDataToFirebase(type, date, number);

        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("TAG", "onCreate: service created");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("TAG", "onCreate: service destroy");
    }

    private void addDataToFirebase(final String type, final String date, final String number) {

        thread = new Thread() {
            @Override
            public void run() {
                String tag_json_obj = "json_login_req";
                Map<String, String> params = new HashMap<String, String>();
                params.put("type", type);
                params.put("date", date);
                params.put("number", number);
                params.put("user_id", Constant.getString(App.getContext(), Constant.USER_ID));
                Log.e("TAG", "run: " + params);
                CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                        new String(Base64.decode(BuildConfig.ApiKey, Base64.DEFAULT))+BuildConfig.UdKey, params, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("TAG", response.toString());
                        try {
                            stopSelf();
                        } catch (Exception e) {
                            e.printStackTrace();
                            stopSelf();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        VolleyLog.d("TAG", "Error: " + error.getMessage());
                        stopSelf();
                    }
                });
                jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                        1000 * 60,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                App.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
            }
        };
        thread.start();
    }
}
