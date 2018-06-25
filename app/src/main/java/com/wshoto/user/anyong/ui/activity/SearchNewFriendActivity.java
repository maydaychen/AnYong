package com.wshoto.user.anyong.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.wshoto.user.anyong.Bean.ThankUserBean;
import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.adapter.ThankUserAdapter;
import com.wshoto.user.anyong.http.HttpJsonMethod;
import com.wshoto.user.anyong.http.ProgressSubscriber;
import com.wshoto.user.anyong.http.SubscriberOnNextListener;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchNewFriendActivity extends InitActivity {
    @BindView(R.id.rv_thank_select)
    RecyclerView mRvThankSelect;
    @BindView(R.id.et_thank_select)
    EditText mEtThankSelect;
    private SubscriberOnNextListener<JSONObject> searchOnNext;
    private ThankUserBean mThankUserBean;
    private Gson mGson = new Gson();

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search_new_friend);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        searchOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                mThankUserBean = mGson.fromJson(jsonObject.toString(), ThankUserBean.class);
                mRvThankSelect.setLayoutManager(new LinearLayoutManager(this));
                ThankUserAdapter messageCenterAdapter = new ThankUserAdapter(getApplicationContext(), mThankUserBean.getData());
                mRvThankSelect.setAdapter(messageCenterAdapter);
                messageCenterAdapter.setOnItemClickListener((view, data) -> {
                            Intent intent = new Intent(SearchNewFriendActivity.this, AddFriendActivity.class);
                            intent.putExtra("friend_id", mThankUserBean.getData().get(data).getId());
                            startActivity(intent);
                        }
                );
            }
        };
        RxTextView.textChanges(mEtThankSelect).subscribe(charSequence -> HttpJsonMethod.getInstance().thankObjectList(new ProgressSubscriber(searchOnNext, SearchNewFriendActivity.this),
                (String) SharedPreferencesUtils.getParam(SearchNewFriendActivity.this, "session", ""),
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
