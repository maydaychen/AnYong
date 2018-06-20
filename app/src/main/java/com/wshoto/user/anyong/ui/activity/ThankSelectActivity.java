package com.wshoto.user.anyong.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jakewharton.rxbinding.widget.RxTextView;
import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.http.HttpJsonMethod;
import com.wshoto.user.anyong.http.ProgressSubscriber;
import com.wshoto.user.anyong.http.SubscriberOnNextListener;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThankSelectActivity extends InitActivity {

    @BindView(R.id.rv_thank_select)
    RecyclerView mRvThankSelect;
    @BindView(R.id.et_thank_select)
    EditText mEtThankSelect;
    private SubscriberOnNextListener<JSONObject> searchOnNext;


    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_thank_select);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {
        searchOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                //todo 好友搜索
            } else {
                Toast.makeText(ThankSelectActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }
        };
        RxTextView.textChanges(mEtThankSelect).subscribe(charSequence -> HttpJsonMethod.getInstance().thankObjectList(
                new ProgressSubscriber(searchOnNext, ThankSelectActivity.this),
                (String) SharedPreferencesUtils.getParam(ThankSelectActivity.this, "session", ""),
                charSequence.toString()));
    }

    @OnClick({R.id.tv_select_back, R.id.iv_select_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_select_back:
                finish();
                break;
            case R.id.iv_select_clear:
                mEtThankSelect.setText("");
                break;
            default:
                break;
        }
    }

}
