package com.wshoto.user.anyong.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.http.HttpJsonMethod;
import com.wshoto.user.anyong.http.ProgressSubscriber;
import com.wshoto.user.anyong.http.SubscriberOnNextListener;
import com.wshoto.user.anyong.ui.activity.ConfirmSuccessActivity;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.et_login_mobile)
    EditText mTvStep2Mobile;
    @BindView(R.id.tv_get_sms)
    TextView mTvGetEms;
    @BindView(R.id.et_login_code)
    EditText mTvGetCode;
    @BindView(R.id.sp_location)
    Spinner mSpLocation;
    Unbinder unbinder;
    @BindView(R.id.et_login_pass)
    EditText etLoginPass;
    @BindView(R.id.et_login_pass_again)
    EditText etLoginPassAgain;

    private String mParam1;
    private String mParam2;
    private int recLen = 60;
    private boolean flag = true;
    private SubscriberOnNextListener<JSONObject> sendOnNext;
    private SubscriberOnNextListener<JSONObject> confirmOnNext;
    List<String> list = new ArrayList();
    private String code;

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
        confirmOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                startActivity(new Intent(getActivity(), ConfirmSuccessActivity.class));
            } else {
                Toast.makeText(getContext(), jsonObject.getJSONObject("message").getString("status"), Toast.LENGTH_SHORT).show();
            }
        };

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm_step2, container, false);
        unbinder = ButterKnife.bind(this, view);


        list.add("+86");
        list.add("+886");
        list.add("+852");
        list.add("+853");
        code = list.get(0);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, list);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //绑定 Adapter到控件
        mSpLocation.setAdapter(adapter);
        mSpLocation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //showPrice(position);
                code = list.get(position);
                TextView tv = (TextView) view;
                tv.setTextColor(getResources().getColor(R.color.yellow));    //设置颜色
                tv.setTextSize(14.0f);    //设置大小
                tv.setGravity(Gravity.CENTER_HORIZONTAL);   //设置居中
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_get_sms, R.id.tv_step1_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_sms:
                String tele = mTvStep2Mobile.getText().toString();
                if (flag) {
                    flag = false;
                    mTvGetEms.setClickable(false);
                    handler.post(runnable);
                    HttpJsonMethod.getInstance().sendCode(
                            new ProgressSubscriber(sendOnNext, getActivity()), tele, "bind",
                            (String) SharedPreferencesUtils.getParam(getActivity(), "language", "zh"),
                            code.substring(1));
                } else {
                    Toast.makeText(getContext(), getText(R.string.mobile_hint), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.tv_step1_next:
                if (mTvStep2Mobile.getText().toString().equals("") || mTvGetCode.getText().toString().equals("") ||
                        etLoginPass.getText().toString().equals("") || etLoginPassAgain.getText().toString().equals("")) {
                    Toast.makeText(getContext(), getText(R.string.step1_error), Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!etLoginPassAgain.getText().toString().equals(etLoginPass.getText().toString())) {
                    Toast.makeText(getContext(), getText(R.string.step1_pass), Toast.LENGTH_SHORT).show();
                    return;
                }
                HttpJsonMethod.getInstance().userRisgist(
                        new ProgressSubscriber(confirmOnNext, getActivity()), mParam1, mParam2,
                        mTvStep2Mobile.getText().toString(), mTvGetCode.getText().toString(),
                        (String) SharedPreferencesUtils.getParam(getActivity(), "language", "zh"),
                        etLoginPassAgain.getText().toString());
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
