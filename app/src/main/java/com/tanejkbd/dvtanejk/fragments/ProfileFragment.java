package com.tanejkbd.dvtanejk.fragments;

import static com.tanejkbd.dvtanejk.utils.Constant.hideKeyboard;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.tanejkbd.dvtanejk.App;
import com.tanejkbd.dvtanejk.BuildConfig;
import com.tanejkbd.dvtanejk.R;
import com.tanejkbd.dvtanejk.activity.LoginActivity;
import com.tanejkbd.dvtanejk.activity.PrivacyActivity;
import com.tanejkbd.dvtanejk.activity.RedeemHistoryActivity;
import com.tanejkbd.dvtanejk.models.User;
import com.tanejkbd.dvtanejk.utils.Constant;
import com.tanejkbd.dvtanejk.utils.CustomVolleyJsonRequest;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Context mContext;

    private EditText name_editText, email_editText, number_editText;
    private AppCompatButton update_profile_btn, privacy_policy_btn, redeem_history, logout_button;
    private ProgressDialog alertDialog;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProfileFragment() {
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
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance() {
        ProfileFragment fragment = new ProfileFragment();
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
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        name_editText = view.findViewById(R.id.profile_name_edit_text);
        email_editText = view.findViewById(R.id.profile_email_edit_text);
        number_editText = view.findViewById(R.id.profile_number_edit_text);
        update_profile_btn = view.findViewById(R.id.update_profile_btn);
        privacy_policy_btn = view.findViewById(R.id.againBtn);
        redeem_history = view.findViewById(R.id.redeem_history);
        logout_button = view.findViewById(R.id.logout_button);
        onClick();
        return view;
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

    private void onClick() {
        name_editText.setText(Constant.getString(mContext, Constant.USER_NAME));
        email_editText.setText(Constant.getString(mContext, Constant.USER_EMAIL));
        number_editText.setText(Constant.getString(mContext, Constant.USER_NUMBER));

        update_profile_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constant.isNetworkAvailable(mContext) && Constant.isOnline(mContext)) {
                    if (name_editText.getText().toString().length() == 0) {
                        name_editText.setError(getResources().getString(R.string.enter_name));
                        name_editText.requestFocus();
                    } else if (number_editText.getText().toString().length() == 0) {
                        number_editText.setError(getResources().getString(R.string.enter_number));
                        number_editText.requestFocus();
                    } else if (number_editText.getText().toString().length() < 10) {
                        number_editText.setError(getResources().getString(R.string.enter_valid_number));
                        number_editText.requestFocus();
                    } else {
                        alertDialog = new ProgressDialog(mContext);
                        alertDialog.setTitle(getResources().getString(R.string.updateing_profile));
                        alertDialog.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_account, null));
                        alertDialog.setMessage(getResources().getString(R.string.please_wait));
                        alertDialog.setCancelable(false);
                        hideKeyboard(getActivity());
                        showProgressDialog();
                        updateProfile(name_editText.getText().toString(), email_editText.getText().toString(), number_editText.getText().toString());
                    }
                } else {
                    Constant.showInternetErrorDialog(getActivity(), getResources().getString(R.string.no_internet_connection));
                }
            }
        });

        privacy_policy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.GotoNextActivity(mContext, PrivacyActivity.class, "privacy");
                getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
            }
        });
        redeem_history.setOnClickListener(view -> {
            Constant.GotoNextActivity(mContext, RedeemHistoryActivity.class, "");
            getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
        });

        logout_button.setOnClickListener(v -> {
            Constant.setString(mContext, Constant.IS_LOGIN, "");
            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
            Constant.setString(requireActivity(), Constant.TODAY_DATE, currentDate);
            Intent i = new Intent(requireActivity(), LoginActivity.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            requireActivity().startActivity(i);
        });
    }

    private void updateProfile(String name, String email, String number) {
        String points = Constant.getString(mContext, Constant.USER_POINTS);
        String user_email = Constant.getString(mContext, Constant.USER_EMAIL);
        String userId = Constant.getString(mContext, Constant.USER_ID);

        String tag_json_obj = "json_login_req";
        Map<String, String> params = new HashMap<String, String>();
        params.put("update_profile", "any");
        params.put("email", email);
        params.put("name", name);
        params.put("password", "");
        params.put("img", "");
        params.put("user_id", userId);
        params.put("number", number);

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                new String(Base64.decode(BuildConfig.ApiKey, Base64.DEFAULT)) + BuildConfig.UprofileKey, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("TAG", response.toString());

                try {
                    hideProgressDialog();
                    boolean status = response.getBoolean("status");
                    if (status) {
                        JSONObject jsonObject = response.getJSONObject("0");
                        Constant.setString(mContext, Constant.USER_ID, jsonObject.getString("id"));
                        final User user = new User(jsonObject.getString("name"), jsonObject.getString("number"), jsonObject.getString("email"), jsonObject.getString("device"), jsonObject.getString("points"), jsonObject.getString("referraled_with"), jsonObject.getString("status"), jsonObject.getString("referral_code"));
                        hideProgressDialog();

                        if (user.getName() != null) {
                            Constant.setString(mContext, Constant.USER_NAME, user.getName());
                            Log.e("TAG", "onDataChange: " + user.getName());
                        }
                        if (user.getNumber() != null) {
                            Constant.setString(mContext, Constant.USER_NUMBER, user.getNumber());
                            Log.e("TAG", "onDataChange: " + user.getNumber());
                        }
                        if (user.getEmail() != null) {
                            Constant.setString(mContext, Constant.USER_EMAIL, user.getEmail());
                            Log.e("TAG", "onDataChange: " + user.getEmail());
                        }
                        if (user.getPoints() != null) {
                            Constant.setString(mContext, Constant.USER_POINTS, user.getPoints());
                            Log.e("TAG", "onDataChange: " + user.getPoints());
                        }
                        if (user.getReferCode() != null) {
                            Constant.setString(mContext, Constant.REFER_CODE, user.getReferCode());
                            Log.e("TAG", "onDataChange: " + user.getReferCode());
                        }
                        if (user.getIsBLocked() != null) {
                            Constant.setString(mContext, Constant.USER_BLOCKED, user.getIsBLocked());
                            Log.e("TAG", "onDataChange: " + user.getIsBLocked());
                        }
                        if (user.getUserReferCode() != null) {
                            Constant.setString(mContext, Constant.USER_REFFER_CODE, user.getUserReferCode());
                            Log.e("TAG", "onDataChange: " + user.getUserReferCode());
                        }

                        hideProgressDialog();
                        Constant.setString(mContext, Constant.IS_LOGIN, "true");
                        Constant.showToastMessage(mContext, getResources().getString(R.string.update_successfully));
                    } else {
                        Constant.showToastMessage(mContext, "Not Updated Try Again...");
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


    @Override
    public void onResume() {
        super.onResume();
        Constant.setString(mContext, "exit", "not_exit");
        String isProfileUpdateEnabled = Constant.getString(mContext, Constant.IS_P_UPDATE_ENABLED);

        if (isProfileUpdateEnabled.equalsIgnoreCase("true")) {
            update_profile_btn.setEnabled(true);
            name_editText.setEnabled(true);
            email_editText.setEnabled(true);
            number_editText.setEnabled(true);
        } else {
            update_profile_btn.setEnabled(false);
            name_editText.setEnabled(false);
            email_editText.setEnabled(false);
            number_editText.setEnabled(false);
        }
    }
}