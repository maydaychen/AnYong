package com.wshoto.user.anyong.ui.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.text.format.Time;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.step.UpdateUiCallBack;
import com.wshoto.user.anyong.step.service.StepService;
import com.wshoto.user.anyong.step.utils.SharedPreferencesUtils;
import com.wshoto.user.anyong.ui.widget.InitActivity;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HealthyLifeActivity extends InitActivity {

    @BindView(R.id.tv_healthy_number)
    TextView mTvHealthyNumber;
    @BindView(R.id.tv_healthy_gather)
    TextView mTvHealthyGather;
    @BindView(R.id.tv_option_yanbao)
    TextView mTvOptionYanbao;
    @BindView(R.id.tv_option_jingzhui)
    TextView mTvOptionJingzhui;

    @Override
    public void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_healthy_life);
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {
        setupService();

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        switch (hour) {
            case 10:
                mTvOptionYanbao.setClickable(true);
                mTvOptionYanbao.setOnClickListener(v -> {
                    Toast.makeText(HealthyLifeActivity.this, "积分取得成功！", Toast.LENGTH_SHORT).show();
                    mTvOptionYanbao.setBackground(getResources().getDrawable(R.drawable.boder_healthy_grey));
                    mTvOptionYanbao.setText("已领取积分！");
                    mTvOptionYanbao.setClickable(false);
                });
                mTvOptionYanbao.setBackground(getResources().getDrawable(R.drawable.boder_healthy_yellow));
                mTvOptionYanbao.setText("完成任务点我");
                break;
            case 16:
                mTvOptionJingzhui.setClickable(true);
                mTvOptionJingzhui.setOnClickListener(v -> {
                    Toast.makeText(HealthyLifeActivity.this, "积分取得成功！", Toast.LENGTH_SHORT).show();
                    mTvOptionJingzhui.setBackground(getResources().getDrawable(R.drawable.boder_healthy_grey));
                    mTvOptionJingzhui.setText("已领取积分！");
                    mTvOptionJingzhui.setClickable(false);
                });
                mTvOptionJingzhui.setBackground(getResources().getDrawable(R.drawable.boder_healthy_yellow));
                mTvOptionJingzhui.setText("完成任务点我");
                break;
        }
    }

    @OnClick(R.id.iv_comfirm_back)
    public void onViewClicked() {
        finish();
    }


    private boolean isBind = false;

    /**
     * 开启计步服务
     */
    private void setupService() {
        Intent intent = new Intent(this, StepService.class);
        isBind = bindService(intent, conn, Context.BIND_AUTO_CREATE);
        startService(intent);
    }

    /**
     * 用于查询应用服务（application Service）的状态的一种interface，
     * 更详细的信息可以参考Service 和 context.bindService()中的描述，
     * 和许多来自系统的回调方式一样，ServiceConnection的方法都是进程的主线程中调用的。
     */
    ServiceConnection conn = new ServiceConnection() {
        /**
         * 在建立起于Service的连接时会调用该方法，目前Android是通过IBind机制实现与服务的连接。
         * @param name 实际所连接到的Service组件名称
         * @param service 服务的通信信道的IBind，可以通过Service访问对应服务
         */
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            StepService stepService = ((StepService.StepBinder) service).getService();
            mTvHealthyNumber.setText(stepService.getStepCount() + "");

            //设置步数监听回调
            stepService.registerCallback(stepCount -> mTvHealthyNumber.setText(stepCount + ""));
        }

        /**
         * 当与Service之间的连接丢失的时候会调用该方法，
         * 这种情况经常发生在Service所在的进程崩溃或者被Kill的时候调用，
         * 此方法不会移除与Service的连接，当服务重新启动的时候仍然会调用 onServiceConnected()。
         * @param name 丢失连接的组件名称
         */
        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (isBind) {
            this.unbindService(conn);
        }
    }
}
