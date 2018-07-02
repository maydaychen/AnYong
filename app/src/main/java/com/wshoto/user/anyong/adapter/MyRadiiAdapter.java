package com.wshoto.user.anyong.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.wshoto.user.anyong.Bean.MyRadiuBean;
import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.ui.activity.FriendInfoActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2018/5/8.
 * 2091320109@qq.com
 */

public class MyRadiiAdapter extends RecyclerView.Adapter<MyRadiiAdapter.ViewHolder> implements View.OnClickListener {
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<MyRadiuBean.DataBean> mData;
    private Context context1;
    private ModifyCountInterface modifyCountInterface;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int data);
    }

    public void setModifyCountInterface(ModifyCountInterface modifyCountInterface) {
        this.modifyCountInterface = modifyCountInterface;
    }

    public MyRadiiAdapter(Context context, List<MyRadiuBean.DataBean> mData) {
        this.mData = mData;
        this.context1 = context;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_radii, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
//        viewHolder.mOrderNum.setText("订单号：" + mData.get(position).getOrdersn());
//        viewHolder.mOrderName.setText("客户姓名：" + mData.get(position).getMembername());
//        viewHolder.mOrderTel.setText("客户电话：" + mData.get(position).getMembermobile());
//        viewHolder.mOrderDate.setText("预约日期：" + mData.get(position).getAppointime() + "（星期" + mData.get(position).getWeekday()+")");
//        viewHolder.mOrderAdd.setText("地址：" + mData.get(position).getAddress());
//        viewHolder.mOrderOperate.setOnClickListener(view -> EventBus.getDefault().post(mData.get(position)));
//        viewHolder.mOrderOperate.setVisibility("0".equals(mData.get(position).getIs_stamp()) ? View.VISIBLE : View.GONE);
        switch (position) {
            case 0:
                viewHolder.mIvRadiiBrand.setImageResource(R.drawable.brand_1);
                break;
            case 1:
                viewHolder.mIvRadiiBrand.setImageResource(R.drawable.brand_2);
                break;
            case 2:
                viewHolder.mIvRadiiBrand.setImageResource(R.drawable.brand_3);
                break;
            default:
                viewHolder.mIvRadiiBrand.setVisibility(View.GONE);
                viewHolder.mTvRadiiBrand.setVisibility(View.VISIBLE);
                viewHolder.mTvRadiiBrand.setText(position + 1 + "");
                break;
        }
        viewHolder.mIvRadiiLogo.setImageUrl(mData.get(position).getAvatar());
        viewHolder.mTvRadiiName.setText(mData.get(position).getEnglish_name());
        viewHolder.mTvRadiiPoint.setText(mData.get(position).getIntegral() + "");
        viewHolder.mTvRadiiDetail.setText(mData.get(position).getLevel() + "");
        viewHolder.itemView.setTag(position);
        viewHolder.mIvRadiiGive.setOnClickListener(view -> {
            modifyCountInterface.doDecrease(mData.get(position).getId());// 暴露删减接口
        });
        viewHolder.mFriend.setOnClickListener(view -> {
            Intent intent = new Intent(context1, FriendInfoActivity.class);
            intent.putExtra("friend_id", mData.get(position).getId());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context1.startActivity(intent);
        });
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return mData.size();
    }


    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取数据
            mOnItemClickListener.onItemClick(v, (Integer) v.getTag());
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_radii_brand)
        ImageView mIvRadiiBrand;
        @BindView(R.id.ll_item_friend)
        LinearLayout mFriend;
        @BindView(R.id.tv_radii_brand)
        TextView mTvRadiiBrand;
        @BindView(R.id.iv_radii_logo)
        SmartImageView mIvRadiiLogo;
        @BindView(R.id.tv_radii_name)
        TextView mTvRadiiName;
        @BindView(R.id.tv_radii_point)
        TextView mTvRadiiPoint;
        @BindView(R.id.tv_radii_detail)
        TextView mTvRadiiDetail;
        @BindView(R.id.iv_radii_give)
        ImageView mIvRadiiGive;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface ModifyCountInterface {

        /**
         * 删减操作
         *
         * @param id 组元素位置
         */
        void doDecrease(String id);

    }
}
