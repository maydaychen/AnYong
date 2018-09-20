package com.wshoto.user.anyong.ui.fragment;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.Utils;
import com.wshoto.user.anyong.http.HttpJsonMethod;
import com.wshoto.user.anyong.http.ProgressSubscriber;
import com.wshoto.user.anyong.http.SubscriberOnNextListener;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfirmStep2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfirmStep2Fragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.tv_step2_name)
    TextView mTvStep2Name;
    @BindView(R.id.et_step1_number)
    EditText mEtStep1Number;
    @BindView(R.id.tv_get_ems)
    TextView mTvGetEms;
    @BindView(R.id.et_step2_name)
    EditText mEtStep1Name;
    Unbinder unbinder;

    private String mParam1;
    private String mParam2;
    private FragmentManager mFragmentManager;
    private int recLen = 60;
    private boolean flag = true;
    private SubscriberOnNextListener<JSONObject> sendOnNext;

    public ConfirmStep2Fragment() {
        // Required empty public constructor
    }

    public static ConfirmStep2Fragment newInstance(String param1, String param2) {
        ConfirmStep2Fragment fragment = new ConfirmStep2Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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

        sendOnNext = resultBean -> {
            if (resultBean.getInt("code") == 1) {
                Toast.makeText(getContext(), getText(R.string.send_sussess), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), resultBean.getJSONObject("message").getString("status"), Toast.LENGTH_SHORT).show();
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm_step2, container, false);
        unbinder = ButterKnife.bind(this, view);
        mTvStep2Name.setText(mParam2);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_get_ems, R.id.tv_step1_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_ems:
                String tele = mEtStep1Number.getText().toString();
                if (flag && Utils.isChinaPhoneLegal(tele)) {
                    flag = false;
                    mTvGetEms.setClickable(false);
                    handler.post(runnable);
                    HttpJsonMethod.getInstance().sendCode(
                            new ProgressSubscriber(sendOnNext, getActivity()), tele ,"bind",
                            (String) SharedPreferencesUtils.getParam(getActivity(), "language", "zh"));
                } else {
                    Toast.makeText(getContext(), getText(R.string.mobile_hint), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_step1_next:
                if (mEtStep1Number.getText().toString().equals("")||mEtStep1Name.getText().toString().equals("")) {
                    Toast.makeText(getContext(), getText(R.string.step1_error), Toast.LENGTH_SHORT).show();
                    return;
                }
                mFragmentManager = getActivity().getSupportFragmentManager();
                Fragment fragment = ConfirmStep3Fragment.newInstance(mParam1, mParam2, mEtStep1Number.getText().toString(), mEtStep1Name.getText().toString());
                mFragmentManager.beginTransaction().replace(R.id.id_content, fragment).addToBackStack("a").commit();
                break;
            default:
                break;
        }
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (recLen >= 1) {
                recLen--;
                mTvGetEms.setText(recLen + "");
                handler.postDelayed(this, 1000);
            } else {
                flag = true;
                recLen = 60;
                mTvGetEms.setClickable(true);
                mTvGetEms.setText(getText(R.string.tv_change_pass_sms_send));
            }
        }
    };

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacksAndMessages(null);
    }
}
