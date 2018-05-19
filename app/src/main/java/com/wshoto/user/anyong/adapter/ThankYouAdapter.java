package com.wshoto.user.anyong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wshoto.user.anyong.Bean.MyRadiiBean;
import com.wshoto.user.anyong.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by user on 2018/5/9.
 * 2091320109@qq.com
 */

public class ThankYouAdapter extends RecyclerView.Adapter<ThankYouAdapter.ViewHolder> implements View.OnClickListener {
    private ThankYouAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<MyRadiiBean> mData;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int data);
    }

    public ThankYouAdapter(Context context, List<MyRadiiBean> mData) {
        this.mData = mData;
        Context context1 = context;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ThankYouAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_radii, viewGroup, false);
        ThankYouAdapter.ViewHolder vh = new ThankYouAdapter.ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ThankYouAdapter.ViewHolder viewHolder, int position) {
//        viewHolder.mOrderNum.setText("订单号：" + mData.get(position).getOrdersn());
//        viewHolder.mOrderName.setText("客户姓名：" + mData.get(position).getMembername());
//        viewHolder.mOrderTel.setText("客户电话：" + mData.get(position).getMembermobile());
//        viewHolder.mOrderDate.setText("预约日期：" + mData.get(position).getAppointime() + "（星期" + mData.get(position).getWeekday()+")");
//        viewHolder.mOrderAdd.setText("地址：" + mData.get(position).getAddress());
//        viewHolder.mOrderOperate.setOnClickListener(view -> EventBus.getDefault().post(mData.get(position)));
//        viewHolder.mOrderOperate.setVisibility("0".equals(mData.get(position).getIs_stamp()) ? View.VISIBLE : View.GONE);
//        viewHolder.itemView.setTag(position);
        viewHolder.mIvRadiiGive.setOnClickListener(view -> {

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

    public void setOnItemClickListener(ThankYouAdapter.OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_radii_brand)
        ImageView mIvRadiiBrand;
        @BindView(R.id.tv_radii_brand)
        TextView mTvRadiiBrand;
        @BindView(R.id.iv_radii_logo)
        ImageView mIvRadiiLogo;
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
}