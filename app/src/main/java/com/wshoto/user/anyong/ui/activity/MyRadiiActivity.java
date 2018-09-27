package com.wshoto.user.anyong.ui.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wshoto.user.anyong.Bean.MyRadiuBean;
import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.Utils;
import com.wshoto.user.anyong.adapter.MyRadiiAdapter;
import com.wshoto.user.anyong.http.HttpJsonMethod;
import com.wshoto.user.anyong.http.ProgressSubscriber;
import com.wshoto.user.anyong.http.SubscriberOnNextListener;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyRadiiActivity extends InitActivity implements MyRadiiAdapter.ModifyCountInterface {
    @BindView(R.id.rv_my_radii)
    RecyclerView rvMyRadii;
    @BindView(R.id.ll_new_friend_apply)
    LinearLayout llNewFriendApply;
    private SubscriberOnNextListener<JSONObject> listOnNext;
    private SubscriberOnNextListener<JSONObject> messageOnNext;
    private SubscriberOnNextListener<JSONObject> giveOnNext;
    private MyRadiuBean myRadiuBean;
    private Gson mGson = new Gson();

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_my_radii);
        ButterKnife.bind(this);
        llNewFriendApply.setOnClickListener(view -> startActivity(new Intent(MyRadiiActivity.this, NewFriendActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        HttpJsonMethod.getInstance().myRadiusList(
                new ProgressSubscriber(listOnNext, MyRadiiActivity.this),
                (String) SharedPreferencesUtils.getParam(this, "session", ""),
                (String) SharedPreferencesUtils.getParam(this, "language", "zh"));

    }

    @Override
    public void initData() {
        listOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                myRadiuBean = mGson.fromJson(jsonObject.toString(), MyRadiuBean.class);
                rvMyRadii.setLayoutManager(new LinearLayoutManager(this));
                MyRadiiAdapter messageCenterAdapter = new MyRadiiAdapter(getApplicationContext(), myRadiuBean.getData());
                rvMyRadii.setAdapter(messageCenterAdapter);
                messageCenterAdapter.setModifyCountInterface(MyRadiiActivity.this);// 关键步骤2,设置数量增减接口
            } else {
                Toast.makeText(MyRadiiActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }
        };
        giveOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                Toast.makeText(MyRadiiActivity.this, getText(R.string.give_success), Toast.LENGTH_SHORT).show();
                onResume();
            } else {
                Toast.makeText(MyRadiiActivity.this, jsonObject.getJSONObject("message").getString("status"), Toast.LENGTH_SHORT).show();
            }
        };

    }

    @OnClick(R.id.iv_comfirm_back)
    public void onViewClicked() {
        finish();
    }

    @Override
    public void doDecrease(String id) {
        showPopupWindow(id);
    }

    private void showPopupWindow(String id) {
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.item_pop_give, null);
        final EditText pointNum = contentView.findViewById(R.id.et_point_num);
        final TextView comit = contentView.findViewById(R.id.tv_pop_commit);
        final TextView back = contentView.findViewById(R.id.tv_pop_back);


        final PopupWindow popupWindow = new PopupWindow(contentView,
                WindowManager.LayoutParams.MATCH_PARENT, Utils.Dp2Px(MyRadiiActivity.this, 200),
                true);
        back.setOnClickListener(v -> popupWindow.dismiss());
        comit.setOnClickListener(v -> {
            HttpJsonMethod.getInstance().givePoint(
                    new ProgressSubscriber(giveOnNext, MyRadiiActivity.this),
                    (String) SharedPreferencesUtils.getParam(this, "session", ""),
                    id, pointNum.getText().toString(),(String) SharedPreferencesUtils.getParam(this, "language", "zh"));
            popupWindow.dismiss();
        });

        ColorDrawable dw = new ColorDrawable(0x00000000);
        popupWindow.setBackgroundDrawable(dw);
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = 0.4f;
        getWindow().setAttributes(lp);
        popupWindow.setOnDismissListener(() -> {
            WindowManager.LayoutParams lp1 = getWindow().getAttributes();
            lp1.alpha = 1f;
            getWindow().setAttributes(lp1);
        });

        popupWindow.showAtLocation(MyRadiiActivity.this.findViewById(R.id.rv_my_radii),
                Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
    }
}
