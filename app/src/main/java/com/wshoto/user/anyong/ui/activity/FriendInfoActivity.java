package com.wshoto.user.anyong.ui.activity;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.loopj.android.image.SmartImageView;
import com.wshoto.user.anyong.Bean.FriendInfoBean;
import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.Utils;
import com.wshoto.user.anyong.http.HttpJsonMethod;
import com.wshoto.user.anyong.http.ProgressSubscriber;
import com.wshoto.user.anyong.http.SubscriberOnNextListener;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FriendInfoActivity extends InitActivity {

    @BindView(R.id.iv_new_friend_logo)
    SmartImageView ivNewFriendLogo;
    @BindView(R.id.tv_new_friend_name)
    TextView tvNewFriendName;
    @BindView(R.id.tv_new_friend_num)
    TextView tvNewFriendNum;
    @BindView(R.id.tv_new_friend_tele)
    TextView tvNewFriendTele;
    @BindView(R.id.tv_person_bumen)
    TextView tvPersonBumen;
    @BindView(R.id.tv_person_zhiwei)
    TextView tvPersonZhiwei;
    @BindView(R.id.tv_person_tele)
    TextView tvPersonTele;
    @BindView(R.id.tv_person_friend_rank)
    TextView tvPersonFriendRank;
    @BindView(R.id.tv_person_anyong_rank)
    TextView tvPersonAnyongRank;
    private SubscriberOnNextListener<JSONObject> friendInfoOnNext;
    private SubscriberOnNextListener<JSONObject> giveOnNext;
    private FriendInfoBean friendInfoBean;
    private Gson mGson = new Gson();

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_friend_info);
        ButterKnife.bind(this);

    }

    @Override
    public void initData() {
        friendInfoOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                friendInfoBean = mGson.fromJson(jsonObject.toString(), FriendInfoBean.class);
                FriendInfoBean.DataBean dataBean = friendInfoBean.getData();
                ivNewFriendLogo.setImageUrl(dataBean.getAvatar());
                tvNewFriendName.setText(dataBean.getEnglish_name());
                tvNewFriendNum.setText(String.format(getResources().getString(R.string.user_number1), dataBean.getJob_no()));
                tvNewFriendTele.setText(dataBean.getMobile());
                tvPersonBumen.setText(dataBean.getDepartment_id());
                tvPersonZhiwei.setText(dataBean.getPosition());
                tvPersonTele.setText(dataBean.getIntegral() + "");
                tvPersonFriendRank.setText(dataBean.getFriendlevel() + "");
                tvPersonAnyongRank.setText(dataBean.getLevel() + "");
            }
        };
        giveOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                Toast.makeText(FriendInfoActivity.this, getText(R.string.give_success), Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(FriendInfoActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }
        };
        HttpJsonMethod.getInstance().friendInfo(
                new ProgressSubscriber(friendInfoOnNext, FriendInfoActivity.this),
                (String) SharedPreferencesUtils.getParam(this, "session", ""),
                getIntent().getStringExtra("friend_id"),(String) SharedPreferencesUtils.getParam(this, "language", "zh"));

    }


    @OnClick({R.id.iv_comfirm_back, R.id.tv_give_credit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_comfirm_back:
                finish();
                break;
            case R.id.tv_give_credit:
                showPopupWindow();
                break;
        }
    }

    private void showPopupWindow() {
        View contentView = LayoutInflater.from(this).inflate(
                R.layout.item_pop_give, null);
        final EditText pointNum = contentView.findViewById(R.id.et_point_num);
        final TextView comit = contentView.findViewById(R.id.tv_pop_commit);
        final TextView back = contentView.findViewById(R.id.tv_pop_back);


        final PopupWindow popupWindow = new PopupWindow(contentView,
                WindowManager.LayoutParams.MATCH_PARENT, Utils.Dp2Px(FriendInfoActivity.this, 200),
                true);
        back.setOnClickListener(v -> popupWindow.dismiss());
        comit.setOnClickListener(v -> {
            HttpJsonMethod.getInstance().givePoint(
                    new ProgressSubscriber(giveOnNext, FriendInfoActivity.this),
                    (String) SharedPreferencesUtils.getParam(this, "session", ""),
                    getIntent().getStringExtra("friend_id"),pointNum.getText().toString(),
                    (String) SharedPreferencesUtils.getParam(this, "language", "zh"));
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

        popupWindow.showAtLocation(FriendInfoActivity.this.findViewById(R.id.iv_new_friend_logo),
                Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 0);
    }
}
