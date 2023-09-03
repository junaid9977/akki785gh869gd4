package com.tanejkbd.dvtanejk.fragments;

import static com.tanejkbd.dvtanejk.utils.Constant.hideKeyboard;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.tanejkbd.dvtanejk.App;
import com.tanejkbd.dvtanejk.BuildConfig;
import com.tanejkbd.dvtanejk.R;
import com.tanejkbd.dvtanejk.utils.Constant;
import com.tanejkbd.dvtanejk.utils.CustomVolleyJsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ForgotFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ForgotFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText email_EditText, otp_editText, new_password_editText;
    private TextView resend_textView;
    TextView back_to_login_text;
    private AppCompatButton reset_btn, otp_btn, change_password_btn;
    private Context mContext;
    private ProgressDialog alertDialog;
    private String OTP;
    private LinearLayout changeLyt, otpLyt;

    public ForgotFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mContext = context;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ForgotFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ForgotFragment newInstance() {
        ForgotFragment fragment = new ForgotFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgot, container, false);
        email_EditText = view.findViewById(R.id.reset_email_edit_text);
        reset_btn = view.findViewById(R.id.reset_password_btn);
        otp_btn = view.findViewById(R.id.submit_otp_btn);
        resend_textView = view.findViewById(R.id.resend_otp);
        otpLyt = view.findViewById(R.id.reset_lyt_otp);
        changeLyt = view.findViewById(R.id.change_lyt);
        otp_editText = view.findViewById(R.id.reset_otp_edit_text);
        new_password_editText = view.findViewById(R.id.new_password_edit_text);
        change_password_btn = view.findViewById(R.id.password_btn);
        back_to_login_text = view.findViewById(R.id.back_to_login_text);

        alertDialog = new ProgressDialog(mContext);
        alertDialog.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_account, null));
        alertDialog.setTitle(getResources().getString(R.string.reset_password));
        alertDialog.setMessage(getResources().getString(R.string.please_wait));
        alertDialog.setCancelable(false);
        onClick();

        return view;
    }

    private void onClick() {


        change_password_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new_password_editText.getText().toString().length() == 0) {
                    new_password_editText.setError(getResources().getString(R.string.enter_password));
                    new_password_editText.requestFocus();
                } else if (new_password_editText.getText().toString().length() < 6) {
                    new_password_editText.setError(getResources().getString(R.string.enter_6_digit_password));
                    new_password_editText.requestFocus();
                } else {
                    changePasswordMethod(new_password_editText.getText().toString());
                }
            }
        });

        resend_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideKeyboard(getActivity());
                showProgressDialog();
                Random rnd = new Random();
                int number = rnd.nextInt(999999);
                resetPasswordEmail(email_EditText.getText().toString(), String.format("%06d", number));
            }
        });
        back_to_login_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() == null) {
                    return;
                }

                getActivity().onBackPressed();
            }
        });
        otp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(otp_editText.getText().toString().trim())) {
                    otp_editText.setError("Enter Otp");
                    otp_editText.requestFocus();
                    return;
                }
                if (!otp_editText.getText().toString().trim().equalsIgnoreCase(OTP)) {
                    otp_editText.setError("Enter Correct Otp");
                    otp_editText.requestFocus();
                    return;
                }
                otpLyt.setVisibility(View.GONE);
                changeLyt.setVisibility(View.VISIBLE);
            }
        });

        reset_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constant.isNetworkAvailable(mContext)) {
                    String Email = email_EditText.getText().toString();
                    if (Email.length() == 0) {
                        email_EditText.setError(getResources().getString(R.string.enter_email));
                        email_EditText.requestFocus();
                    } else if (!Constant.isValidEmailAddress(Email)) {
                        email_EditText.setError(getResources().getString(R.string.enter_valid_email));
                        email_EditText.requestFocus();
                    } else {
                        hideKeyboard(getActivity());
                        showProgressDialog();
                        Random rnd = new Random();
                        int number = rnd.nextInt(999999);
                        resetPasswordEmail(Email, String.format("%06d", number));
                    }
                } else {
                    Constant.showInternetErrorDialog(getActivity(), getResources().getString(R.string.no_internet_connection));
                }
            }
        });

    }

    private void changePasswordMethod(String password) {
        showProgressDialog();

        String tag_json_obj = "json_login_req";
        Map<String, String> params = new HashMap<String, String>();
        params.put("rest_pass", "any");
        params.put("email", email_EditText.getText().toString());
        params.put("pass", password);

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                new String(Base64.decode(BuildConfig.ApiKey, Base64.DEFAULT))+BuildConfig.RpKey, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("TAG", response.toString());

                try {
                    hideProgressDialog();
                    boolean status = response.getBoolean("status");
                    if (status) {
                        hideProgressDialog();
                        Constant.showToastMessage(mContext, "Password Reset");
                        if (getActivity() == null) {
                            return;
                        }
                        getActivity().onBackPressed();
                    } else {
                        Constant.showToastMessage(mContext, "Password Not Updated Please Check your email");
                    }
                } catch (JSONException e) {
                    hideProgressDialog();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                hideProgressDialog();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Constant.showToastMessage(mContext, getResources().getString(R.string.slow_internet_connection));
                }
            }
        });
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                1000 * 20,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        App.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    private void resetPasswordEmail(String email, final String otp) {
        showProgressDialog();

        String tag_json_obj = "json_login_req";
        Map<String, String> params = new HashMap<String, String>();
        params.put("recover", "anything");
        params.put("email", email);
        params.put("otp", otp);
        Log.e("TAG", "resetPasswordEmail: " + params);

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                new String(Base64.decode(BuildConfig.ApiKey, Base64.DEFAULT))+BuildConfig.FpKey, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("TAG", response.toString());

                try {
                    hideProgressDialog();
                    boolean status = response.getBoolean("status");
                    if (status) {
                        hideProgressDialog();
                        Constant.showToastMessage(mContext, "Otp Sent to your email check your email");
                        email_EditText.setVisibility(View.GONE);
                        otp_editText.setVisibility(View.VISIBLE);
                        reset_btn.setVisibility(View.GONE);
                        otp_btn.setVisibility(View.VISIBLE);
                        resend_textView.setVisibility(View.VISIBLE);
                        email_EditText.setVisibility(View.GONE);
                        OTP = otp;
                    } else {
                        Constant.showToastMessage(mContext, "This Email is Not Registered");
                    }
                } catch (JSONException e) {
                    hideProgressDialog();
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                VolleyLog.d("TAG", "Error: " + error.getMessage());
                hideProgressDialog();
                if (error instanceof TimeoutError || error instanceof NoConnectionError) {
                    Constant.showToastMessage(mContext, getResources().getString(R.string.slow_internet_connection));
                }
            }
        });
        jsonObjReq.setRetryPolicy(new DefaultRetryPolicy(
                1000 * 20,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        App.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }

    public void showProgressDialog() {
        if (alertDialog != null && !alertDialog.isShowing()) {
            alertDialog.show();
        }
    }

    public void hideProgressDialog() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }
}