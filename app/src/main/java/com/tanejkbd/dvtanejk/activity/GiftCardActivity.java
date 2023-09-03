package com.tanejkbd.dvtanejk.activity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.provider.Settings;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tanejkbd.dvtanejk.App;
import com.tanejkbd.dvtanejk.BuildConfig;
import com.tanejkbd.dvtanejk.R;
import com.tanejkbd.dvtanejk.adapter.PaymentAdapter;
import com.tanejkbd.dvtanejk.listener.PaymentListener;
import com.tanejkbd.dvtanejk.models.PaymentModel;
import com.tanejkbd.dvtanejk.utils.Constant;
import com.tanejkbd.dvtanejk.utils.CustomVolleyJsonRequest;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class GiftCardActivity extends AppCompatActivity implements PaymentListener {

    private GiftCardActivity activity;
    private TextView user_points_textView;
    private PaymentAdapter websiteAdapter;
    private ArrayList<PaymentModel> paymentModelArrayList = new ArrayList<>();
    private RecyclerView websiteRv;
    private TextView noPaymentFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gift_card);
        activity = this;
        user_points_textView = findViewById(R.id.user_points_textView);

        noPaymentFound = findViewById(R.id.noPaymentFound);
        websiteRv = findViewById(R.id.paymentRv);
        websiteRv.setLayoutManager(new GridLayoutManager(activity, 2));
        String json = Constant.getString(activity, Constant.PAYMENT_LIST);
        if (!json.equalsIgnoreCase("")) {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<PaymentModel>>() {
            }.getType();
            paymentModelArrayList = gson.fromJson(json, type);
            websiteAdapter = new PaymentAdapter(paymentModelArrayList, activity, activity);
            websiteRv.setAdapter(websiteAdapter);
        }
        setWebsiteData();
        String user_points = Constant.getString(activity, Constant.USER_POINTS);
        if (user_points.equals("")) {
            user_points_textView.setText("0");
        } else {
            user_points_textView.setText(Constant.getString(activity, Constant.USER_POINTS));
        }
    }

    private void setWebsiteData() {
        if (paymentModelArrayList.isEmpty()) {
            websiteRv.setVisibility(View.GONE);
            noPaymentFound.setVisibility(View.VISIBLE);
        } else {
            websiteRv.setVisibility(View.VISIBLE);
            noPaymentFound.setVisibility(View.GONE);
        }
    }

    private void paymentDialog(int index) {
        if (Integer.parseInt(Constant.getString(activity, Constant.USER_POINTS)) < Integer.parseInt(paymentModelArrayList.get(index).getPayment_btn_coins())) {
            Constant.showToastMessage(activity, "Insufficient Coins");
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(activity);
            builder.setTitle("Enter Payment Info");

            final EditText input = new EditText(activity);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            input.setHint("Enter" + " " + paymentModelArrayList.get(index).getPayment_btn_name() + " " + paymentModelArrayList.get(index).getPayment_btn_type());
            builder.setView(input);

            builder.setPositiveButton("Submit", (dialog, which) -> {
                if (Constant.isNetworkAvailable(activity) && Constant.isOnline(activity)) {
                    @SuppressLint("HardwareIds") String Device = Settings.Secure.getString(activity.getContentResolver(),
                            Settings.Secure.ANDROID_ID);
                    if (!Constant.getString(activity, Constant.USER_DEVICE).equals(Device)) {
                        killProcess();
                    } else {
                        String numberOrUpiId = input.getText().toString();
                        if (numberOrUpiId.length() == 0) {
                            Constant.showToastMessage(activity, getResources().getString(R.string.enterNumberOrUpi));
                        } else {
                            if (Integer.parseInt(Constant.getString(activity, Constant.USER_POINTS)) < Integer.parseInt(paymentModelArrayList.get(index).getPayment_btn_coins())) {
                                Constant.showToastMessage(activity, "You Have Not Enough Coins");
                                return;
                            }
                            RedeemPointsDialog(numberOrUpiId, paymentModelArrayList.get(index).getPayment_btn_coins(), paymentModelArrayList.get(index).getPayment_btn_name());
                        }
                    }
                } else {
                    Constant.showInternetErrorDialog(activity, getResources().getString(R.string.no_internet_connection));
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            builder.show();
        }
    }


    private void RedeemPointsDialog(final String numberOrUpiId, final String points, final String type) {
        SweetAlertDialog sweetAlertDialog;
        String points_text_string = getResources().getString(R.string.redeem_tag_line_2) + " " + numberOrUpiId + " " + getResources().getString(R.string.redeem_tag_line_3) + " " + points + " " + getResources().getString(R.string.redeem_tag_line_4) + " " + type;
        sweetAlertDialog = new SweetAlertDialog(activity, SweetAlertDialog.WARNING_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setCanceledOnTouchOutside(false);
        sweetAlertDialog.setTitleText(getResources().getString(R.string.redeem_tag_line_1));
        sweetAlertDialog.setContentText(points_text_string);
        sweetAlertDialog.setConfirmText("Yes");
        sweetAlertDialog.setCancelText("Cancel");

        sweetAlertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                sweetAlertDialog
                        .setTitleText("Redeem Successful!")
                        .setContentText("You will receive a reward within 48 hours!")
                        .setConfirmText("OK")
                        .setConfirmClickListener(null)
                        .showCancelButton(false)
                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                if (Constant.getString(activity, Constant.IS_LOGIN).equalsIgnoreCase("true")) {
                    makeRedeemRequest(numberOrUpiId, points, type, Constant.getString(activity, Constant.REFER_CODE));
                } else {
                    Constant.showToastMessage(activity, "Login First");
                }
            }
        }).setCancelClickListener(SweetAlertDialog::cancel).show();
    }

    private void killProcess() {
        finish();
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    private void makeRedeemRequest(String numberOrUpiId, final String points, String type, String refer_by) {
        final String user_previous_points = Constant.getString(activity, Constant.USER_POINTS);
        final int current_points = Integer.parseInt(user_previous_points) - Integer.parseInt(points);
        Constant.setString(activity, Constant.USER_POINTS, String.valueOf(current_points));
        user_points_textView.setText(String.valueOf(current_points));
        String tag_json_obj = "json_login_req";
        Map<String, String> params = new HashMap<String, String>();
        params.put("redeem_point", "redeem");
        if (!refer_by.equalsIgnoreCase("")) {
            params.put("referraled_with", refer_by);
        }
        params.put("user_id", Constant.getString(activity, Constant.USER_ID));
        params.put("new_point", String.valueOf(current_points));
        params.put("redeemed_point", points);
        params.put("payment_mode", type);
        params.put("payment_info", numberOrUpiId);
        Log.e("TAG", "signupNewUser: " + params);

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                new String(Base64.decode(BuildConfig.ApiKey, Base64.DEFAULT)) + BuildConfig.UpointsKey, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("TAG", response.toString());

                try {
                    boolean status = response.getBoolean("status");
                    if (status) {
                        Constant.addPoints(activity, Integer.parseInt(Constant.getString(activity, Constant.USER_POINTS)), 1, "redeem", "1");
                    } else {
                        Constant.showToastMessage(activity, response.getString("message"));
                        user_points_textView.setText(String.valueOf(user_previous_points));
                        Constant.setString(activity, Constant.USER_POINTS, String.valueOf(user_previous_points));
                        Constant.addPoints(activity, Integer.parseInt(Constant.getString(activity, Constant.USER_POINTS)), 1, "redeem", "1");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Constant.showToastMessage(activity, getResources().getString(R.string.slow_internet_connection));
                }
                user_points_textView.setText(String.valueOf(user_previous_points));
                Constant.setString(activity, Constant.USER_POINTS, String.valueOf(user_previous_points));
                Constant.addPoints(activity, Integer.parseInt(Constant.getString(activity, Constant.USER_POINTS)), 1, "redeem", "1");
            }
        });
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                Constant.GET_SETTINGS_FROM_ADMIN_PANNEL_TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        App.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    @Override
    public void paymentIndex(int index) {
        paymentDialog(index);
    }

}