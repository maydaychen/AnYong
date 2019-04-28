package com.wshoto.user.anyong.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.wshoto.user.anyong.Bean.ThankUserBean;
import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.adapter.PeopleSelectAdapter;
import com.wshoto.user.anyong.adapter.ThankUserAdapter;
import com.wshoto.user.anyong.http.HttpJsonMethod;
import com.wshoto.user.anyong.http.ProgressSubscriber;
import com.wshoto.user.anyong.http.SubscriberOnNextListener;
import com.wshoto.user.anyong.ui.widget.AutoLineFeedLayoutManager;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ThankSelectActivity extends InitActivity implements ThankUserAdapter.CheckInterface {

    @BindView(R.id.rv_thank_select)
    RecyclerView mRvThankSelect;
    @BindView(R.id.et_thank_select)
    EditText mEtThankSelect;
    @BindView(R.id.rv_person)
    RecyclerView rvPerson;
    @BindView(R.id.tv_select_send)
    TextView tvSelectSend;
    @BindView(R.id.tv_select_back)
    TextView tvSelectBack;
    private SubscriberOnNextListener<JSONObject> searchOnNext;
    private ThankUserBean mThankUserBean;
    private Gson mGson = new Gson();
    private List<ThankUserBean.DataBean> list = new ArrayList<>();
    private PeopleSelectAdapter peopleSelectAdapter;
    private ThankUserAdapter messageCenterAdapter;
    private boolean IS_FIRST = true;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_thank_select);
        ButterKnife.bind(this);


        tvSelectBack.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.putExtra("user", (Serializable) list);
            setResult(RESULT_OK, intent);
            finish();
        });
        tvSelectSend.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.putExtra("user", (Serializable) list);
            setResult(RESULT_OK, intent);
            finish();
        });
    }

    @Override
    public void initData() {
        searchOnNext = jsonObject -> {
            if (IS_FIRST) {
                list = (List<ThankUserBean.DataBean>) getIntent().getSerializableExtra("user");
                if (null == list) {
                    list = new ArrayList<>();
                }
                if (list.size() > 0) {
                    tvSelectBack.setVisibility(View.GONE);
                    tvSelectSend.setVisibility(View.VISIBLE);
                }
                IS_FIRST = false;
                rvPerson.setLayoutManager(new AutoLineFeedLayoutManager());
                peopleSelectAdapter = new PeopleSelectAdapter(getApplicationContext(), list);
                peopleSelectAdapter.setOnItemClickListener((view, data) -> {
                    list.remove(data);
                    peopleSelectAdapter.notifyDataSetChanged();
                    messageCenterAdapter.notifyDataSetChanged();
                    if (list.size() == 0) {
                        tvSelectBack.setVisibility(View.VISIBLE);
                        tvSelectSend.setVisibility(View.GONE);
                    }
                });
                rvPerson.setAdapter(peopleSelectAdapter);
            }
            if (jsonObject.getInt("code") == 1) {
                mThankUserBean = mGson.fromJson(jsonObject.toString(), ThankUserBean.class);
                mRvThankSelect.setLayoutManager(new LinearLayoutManager(this));
                messageCenterAdapter = new ThankUserAdapter(getApplicationContext(), mThankUserBean.getData(), list);
                messageCenterAdapter.setCheckInterface(ThankSelectActivity.this);
                mRvThankSelect.setAdapter(messageCenterAdapter);

            }
        };
        RxTextView.textChanges(mEtThankSelect).subscribe(charSequence -> HttpJsonMethod.getInstance().thankObjectList(new ProgressSubscriber(searchOnNext, ThankSelectActivity.this),
                (String) SharedPreferencesUtils.getParam(ThankSelectActivity.this, "session", ""),
                charSequence.toString(), (String) SharedPreferencesUtils.getParam(ThankSelectActivity.this, "language", "zh")));

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

    @Override
    public void addPerson(int groupPosition) {
        for (ThankUserBean.DataBean dataBean : list) {
            if (mThankUserBean.getData().get(groupPosition).getId().equals(dataBean.getId())) {
                return;
            }
        }
        list.add(mThankUserBean.getData().get(groupPosition));
        peopleSelectAdapter.notifyDataSetChanged();
        messageCenterAdapter.notifyDataSetChanged();
        if (list.size() > 0) {
            tvSelectBack.setVisibility(View.GONE);
            tvSelectSend.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void deletePerson(int groupPosition) {
        list.remove(mThankUserBean.getData().get(groupPosition));
        peopleSelectAdapter.notifyDataSetChanged();
        messageCenterAdapter.notifyDataSetChanged();
        if (list.size() == 0) {
            tvSelectBack.setVisibility(View.VISIBLE);
            tvSelectSend.setVisibility(View.GONE);
        }
    }
}
