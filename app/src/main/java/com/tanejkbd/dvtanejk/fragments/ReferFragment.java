package com.tanejkbd.dvtanejk.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tanejkbd.dvtanejk.R;
import com.tanejkbd.dvtanejk.utils.Constant;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ReferFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ReferFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private Context mContext;
    private TextView refer_code_textView, refer_decs;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ReferFragment() {
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
     * @return A new instance of fragment ReferFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReferFragment newInstance() {
        ReferFragment fragment = new ReferFragment();
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
        View view = inflater.inflate(R.layout.fragment_refer, container, false);

        refer_code_textView = view.findViewById(R.id.refer_code);
        refer_decs = view.findViewById(R.id.refer_decs);

        // refer_decs.setText(R.string.refer_desc);
        refer_decs.setText(Constant.getString(mContext, Constant.REFER_TEXT));

        refer_code_textView.setText(Constant.getString(mContext, Constant.USER_REFFER_CODE));
        view.findViewById(R.id.refer_and_win_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Constant.referApp(mContext, refer_code_textView.getText().toString());
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Constant.setString(mContext, "exit", "not_exit");
    }
}