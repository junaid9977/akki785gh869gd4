package com.tanejkbd.dvtanejk.fragments;

import static com.tanejkbd.dvtanejk.utils.Constant.hideKeyboard;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.tanejkbd.dvtanejk.activity.MainActivity;
import com.tanejkbd.dvtanejk.models.User;
import com.tanejkbd.dvtanejk.utils.Constant;
import com.tanejkbd.dvtanejk.utils.CustomVolleyJsonRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText email_edit_text, password_edit_text;
    private AppCompatButton login_button;
    private TextView sign_up_text_view, forgot_textView;
    private Context mContext;
    private ProgressDialog alertDialog;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LoginFragment() {
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
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
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
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        email_edit_text = view.findViewById(R.id.login_email_edit_text);
        password_edit_text = view.findViewById(R.id.login_password_edit_text);
        login_button = view.findViewById(R.id.login_btn);
        sign_up_text_view = view.findViewById(R.id.sign_up_text);
        forgot_textView = view.findViewById(R.id.forgot_text);
        alertDialog = new ProgressDialog(mContext);
        alertDialog.setIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_account, null));
        alertDialog.setTitle(getResources().getString(R.string.login_in_progress));
        alertDialog.setMessage(getResources().getString(R.string.please_wait));
        alertDialog.setCancelable(false);
        onInitView();


        return view;
    }

    private void onInitView() {

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Constant.isNetworkAvailable(mContext)) {
                    String Email = email_edit_text.getText().toString();
                    String Password = password_edit_text.getText().toString();
                    if (Email.length() == 0) {
                        email_edit_text.setError(getResources().getString(R.string.enter_email));
                        email_edit_text.requestFocus();
                    } else if (!Constant.isValidEmailAddress(Email)) {
                        email_edit_text.setError(getResources().getString(R.string.enter_valid_email));
                        email_edit_text.requestFocus();
                    } else if (Password.length() == 0) {
                        password_edit_text.setError(getResources().getString(R.string.enter_password));
                        password_edit_text.requestFocus();
                    } else if (Password.length() < 6) {
                        password_edit_text.setError(getResources().getString(R.string.enter_valid_number));
                        password_edit_text.requestFocus();
                    } else {
                        hideKeyboard(getActivity());
                        showProgressDialog();
                        signInWithEmailandPassword(Email, Password);
                    }
                } else {
                    Constant.showInternetErrorDialog(getActivity(), getResources().getString(R.string.no_internet_connection));
                }
            }
        });

        sign_up_text_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() == null) {
                    return;
                }
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_Login, SignUpFragment.newInstance()).addToBackStack(null).commit();

            }
        });

        forgot_textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() == null) {
                    return;
                }
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_Login, ForgotFragment.newInstance()).addToBackStack(null).commit();

            }
        });

    }


    private void signInWithEmailandPassword(String email, final String password) {
        if (getActivity() == null) {
            hideProgressDialog();
            return;
        }
        String tag_json_obj = "json_login_req";
        Map<String, String> params = new HashMap<String, String>();
        params.put("get_login", "any");
        params.put("email", email);
        params.put("password", password);
        Log.e("TAG", "signupNewUser: " + params);

        CustomVolleyJsonRequest jsonObjReq = new CustomVolleyJsonRequest(Request.Method.POST,
                new String(Base64.decode(BuildConfig.ApiKey, Base64.DEFAULT))+BuildConfig.LoginKey, params, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d("TAG", response.toString());

                try {
                    hideProgressDialog();
                    boolean status = response.getBoolean("status");
                    if (status) {
                        JSONObject jsonObject = response.getJSONObject("0");
                        Constant.setString(mContext, Constant.USER_ID, jsonObject.getString("id"));
                        Constant.setString(mContext, Constant.USER_PASSWORD, password);
                        final User user = new User(jsonObject.getString("name"), jsonObject.getString("number"), jsonObject.getString("email"), jsonObject.getString("device"), jsonObject.getString("points"), jsonObject.getString("referraled_with"), jsonObject.getString("status"), jsonObject.getString("referral_code"));
                        if (getActivity() == null) {
                            hideProgressDialog();
                            return;
                        }

                        if (response.has("date")) {
                            Constant.setString(mContext, Constant.TODAY_DATE, response.getString("date"));
                        } else {
                            String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                            Constant.setString(mContext, Constant.TODAY_DATE, currentDate);
                        }

                        if (jsonObject.has("daily_check_in")) {
                            Constant.setString(mContext, Constant.LAST_DATE, jsonObject.getString("daily_check_in"));
                        }
                        if (jsonObject.has("last_date_watch")) {
                            Constant.setString(mContext, Constant.LAST_DATE_WATCH, jsonObject.getString("last_date_watch"));
                        }
                        if (jsonObject.has("watched1_date")) {
                            Constant.setString(mContext, Constant.WATCHED1_DATE, jsonObject.getString("watched1_date"));
                        }
                        if (jsonObject.has("watched2_date")) {
                            Constant.setString(mContext, Constant.WATCHED2_DATE, jsonObject.getString("watched2_date"));
                        }
                        if (jsonObject.has("watched3_date")) {
                            Constant.setString(mContext, Constant.WATCHED3_DATE, jsonObject.getString("watched3_date"));
                        }
                        if (jsonObject.has("watched4_date")) {
                            Constant.setString(mContext, Constant.WATCHED4_DATE, jsonObject.getString("watched4_date"));
                        }
                        if (jsonObject.has("watched5_date")) {
                            Constant.setString(mContext, Constant.WATCHED5_DATE, jsonObject.getString("watched5_date"));
                        }
                        if (jsonObject.has("watched6_date")) {
                            Constant.setString(mContext, Constant.WATCHED6_DATE, jsonObject.getString("watched6_date"));
                        }
                        if (jsonObject.has("watched7_date")) {
                            Constant.setString(mContext, Constant.WATCHED7_DATE, jsonObject.getString("watched7_date"));
                        }
                        if (jsonObject.has("watched8_date")) {
                            Constant.setString(mContext, Constant.WATCHED8_DATE, jsonObject.getString("watched8_date"));
                        }
                        if (jsonObject.has("watched9_date")) {
                            Constant.setString(mContext, Constant.WATCHED9_DATE, jsonObject.getString("watched9_date"));
                        }
                        if (jsonObject.has("watched10_date")) {
                            Constant.setString(mContext, Constant.WATCHED10_DATE, jsonObject.getString("watched10_date"));
                        }
                        if (jsonObject.has("visited1_date")) {
                            Constant.setString(mContext, Constant.VISITED1_DATE, jsonObject.getString("visited1_date"));
                        }
                        if (jsonObject.has("visited2_date")) {
                            Constant.setString(mContext, Constant.VISITED2_DATE, jsonObject.getString("visited2_date"));
                        }
                        if (jsonObject.has("visited3_date")) {
                            Constant.setString(mContext, Constant.VISITED3_DATE, jsonObject.getString("visited3_date"));
                        }
                        if (jsonObject.has("visited4_date")) {
                            Constant.setString(mContext, Constant.VISITED4_DATE, jsonObject.getString("visited4_date"));
                        }
                        if (jsonObject.has("visited5_date")) {
                            Constant.setString(mContext, Constant.VISITED5_DATE, jsonObject.getString("visited5_date"));
                        }
                        if (jsonObject.has("visited6_date")) {
                            Constant.setString(mContext, Constant.VISITED6_DATE, jsonObject.getString("visited6_date"));
                        }
                        if (jsonObject.has("visited7_date")) {
                            Constant.setString(mContext, Constant.VISITED7_DATE, jsonObject.getString("visited7_date"));
                        }
                        if (jsonObject.has("visited8_date")) {
                            Constant.setString(mContext, Constant.VISITED8_DATE, jsonObject.getString("visited8_date"));
                        }
                        if (jsonObject.has("visited9_date")) {
                            Constant.setString(mContext, Constant.VISITED9_DATE, jsonObject.getString("visited9_date"));
                        }
                        if (jsonObject.has("visited10_date")) {
                            Constant.setString(mContext, Constant.VISITED10_DATE, jsonObject.getString("visited10_date"));
                        }
                        if (jsonObject.has("last_date_invalid")) {
                            Constant.setString(mContext, Constant.LAST_DATE_INVALID, jsonObject.getString("last_date_invalid"));
                        }
                        if (jsonObject.has("scratch_date")) {
                            Constant.setString(mContext, Constant.LAST_DATE_SCRATCH, jsonObject.getString("scratch_date"));
                        }
                        if (jsonObject.has("scratch_count")) {
                            Constant.setString(mContext, Constant.SCRATCH_COUNT, jsonObject.getString("scratch_count"));
                        }
                        if (jsonObject.has("spin_date")) {
                            Constant.setString(mContext, Constant.LAST_DATE_SPIN, jsonObject.getString("spin_date"));
                        }
                        if (jsonObject.has("spin_count")) {
                            Constant.setString(mContext, Constant.SPIN_COUNT, jsonObject.getString("spin_count"));
                        }
                        if (jsonObject.has("captcha_count")) {
                            Constant.setString(mContext, Constant.CAPTCHA_COUNT, jsonObject.getString("captcha_count"));
                        }
                        if (jsonObject.has("captcha_date")) {
                            Constant.setString(mContext, Constant.LAST_DATE_CAPTCHA, jsonObject.getString("captcha_date"));
                        }
                        if (jsonObject.has("singup_bounus_date")) {
                            Constant.setString(mContext, Constant.SIGNUP_BOUNUS_DATE, jsonObject.getString("singup_bounus_date"));
                        }
                        if (jsonObject.has("quiz_count")) {
                            Constant.setString(mContext, Constant.QUIZ_COUNT, jsonObject.getString("quiz_count"));
                        }
                        if (jsonObject.has("quiz_date")) {
                            Constant.setString(mContext, Constant.LAST_DATE_QUIZ, jsonObject.getString("quiz_date"));
                        }
                        if (jsonObject.has("game_count")) {
                            Constant.setString(mContext, Constant.GAME_COUNT, jsonObject.getString("game_count"));
                        }
                        if (jsonObject.has("game_date")) {
                            Constant.setString(mContext, Constant.LAST_DATE_GAME, jsonObject.getString("game_date"));
                        }
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
                        if (user.getDevice() != null) {
                            Constant.setString(mContext, Constant.USER_DEVICE, user.getDevice());
                            Log.e("TAG", "onDataChange: " + user.getDevice());
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
                        if (Constant.getString(mContext, Constant.USER_BLOCKED).equals("0")) {
                            Constant.showBlockedDialog(mContext, getActivity(), getResources().getString(R.string.you_are_blocked));
                        } else {
                            Constant.setString(mContext, Constant.IS_LOGIN, "true");
                            Constant.showToastMessage(mContext, getResources().getString(R.string.login_successfully));
                            updateUI();
                        }
                    } else {
                        Constant.showToastMessage(mContext, "Invalid Email Or Password");
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

    @Override
    public void onStart() {
        super.onStart();
    }

    private void updateUI() {
        if (getActivity() == null) {
            return;
        }
        Constant.GotoNextActivity(mContext, MainActivity.class, "");
        getActivity().overridePendingTransition(R.anim.enter, R.anim.exit);
        getActivity().finish();
    }
}