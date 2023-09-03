package com.tanejkbd.dvtanejk.activity;

import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.tanejkbd.dvtanejk.App;
import com.tanejkbd.dvtanejk.BuildConfig;
import com.tanejkbd.dvtanejk.R;
import com.tanejkbd.dvtanejk.adapter.PaymentHistoryAdapter;
import com.tanejkbd.dvtanejk.models.PaymentHistoryModel;
import com.tanejkbd.dvtanejk.utils.Constant;
import com.tanejkbd.dvtanejk.utils.CustomVolleyJsonRequest;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RedeemHistoryActivity extends AppCompatActivity {
    private TextView no_history_found_text;
    private RecyclerView paymentHistoryRecyclerView;
    private PaymentHistoryAdapter paymentHistoryAdapter;
    private ArrayList<PaymentHistoryModel> paymentHistoryModelArrayList = new ArrayList<>();
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_history);

        paymentHistoryRecyclerView = findViewById(R.id.redeem_history_rv);
        paymentHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        paymentHistoryAdapter = new PaymentHistoryAdapter(paymentHistoryModelArrayList, this);
        paymentHistoryRecyclerView.setAdapter(paymentHistoryAdapter);
        no_history_found_text = findViewById(R.id.no_payment_history_text);


        refreshLayout = findViewById(R.id.refreshLyt);
        refreshLayout.setRefreshing(true);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    loadData();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        loadData();
    }

    private void loadData() {
        String is_login = Constant.getString(this, Constant.IS_LOGIN);
        boolean LOGIN = false;
        if (is_login.equals("true")) {
            LOGIN = true;
        }
        if (Constant.isNetworkAvailable(this)) {
            if (LOGIN) {
                try {
                    String tag_json_obj = "json_login_req";
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("user_id", Constant.getString(this, Constant.USER_ID));
                    CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                            new String(Base64.decode(BuildConfig.ApiKey, Base64.DEFAULT)) + BuildConfig.RedeemHistoryKey, params, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.d("TAG", response.toString());

                            try {
                                boolean status = response.getBoolean("status");
                                if (status) {
                                    JSONArray object = response.getJSONArray("data");
                                    paymentHistoryModelArrayList.clear();
                                    for (int i = 0; i < object.length(); i++) {
                                        JSONObject jsonObject = object.getJSONObject(i);
                                        PaymentHistoryModel paymentInfo = new PaymentHistoryModel();
                                        paymentInfo.setId(jsonObject.getString("id"));
                                        paymentInfo.setRedeem_point(jsonObject.getString("redeem_point"));
                                        paymentInfo.setPayment_mode(jsonObject.getString("payment_mode"));
                                        paymentInfo.setPayment_info(jsonObject.getString("payment_info"));
                                        paymentInfo.setPayment_time(jsonObject.getString("payment_time"));
                                        paymentInfo.setStatus(jsonObject.getString("status"));
                                        paymentHistoryModelArrayList.add(paymentInfo);
                                    }
                                    if (!paymentHistoryModelArrayList.isEmpty()) {
                                        no_history_found_text.setVisibility(View.GONE);
                                        paymentHistoryRecyclerView.setVisibility(View.VISIBLE);
                                        paymentHistoryAdapter.notifyDataSetChanged();
                                    } else {
                                        no_history_found_text.setVisibility(View.VISIBLE);
                                        paymentHistoryRecyclerView.setVisibility(View.GONE);
                                        Constant.showToastMessage(RedeemHistoryActivity.this, "Nothing Found...");
                                    }
                                    if (refreshLayout.isRefreshing()) {
                                        refreshLayout.setRefreshing(false);
                                    }
                                } else {
                                    if (refreshLayout.isRefreshing()) {
                                        refreshLayout.setRefreshing(false);
                                    }
                                    Constant.showToastMessage(RedeemHistoryActivity.this, "Nothing Found...");
                                }
                            } catch (JSONException e) {
                                if (refreshLayout.isRefreshing()) {
                                    refreshLayout.setRefreshing(false);
                                }
                                Constant.showToastMessage(RedeemHistoryActivity.this, "Something Went Wrong...");
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                            VolleyLog.d("TAG", "Error: " + error.getMessage());
                            if (refreshLayout.isRefreshing()) {
                                refreshLayout.setRefreshing(false);
                            }
                            Constant.showToastMessage(RedeemHistoryActivity.this, "Something Went Wrong...");
                            if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                                Constant.showToastMessage(RedeemHistoryActivity.this, getResources().getString(R.string.slow_internet_connection));
                            }
                        }
                    });
                    jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                            1000 * 60,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    App.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                if (Constant.getString(this, Constant.USER_BLOCKED).equals("true")) {
                    Constant.showBlockedDialog(RedeemHistoryActivity.this, RedeemHistoryActivity.this, getResources().getString(R.string.you_are_blocked));
                    return;
                }
                Log.e("TAG", "onInit: else part of no login");
                Constant.GotoNextActivity(this, LoginActivity.class, "");
                if (refreshLayout.isRefreshing()) {
                    refreshLayout.setRefreshing(false);
                }
            }

        } else {
            if (refreshLayout.isRefreshing()) {
                refreshLayout.setRefreshing(false);
            }
            Constant.showInternetErrorDialog(this, getResources().getString(R.string.no_internet_connection));
        }
    }

}