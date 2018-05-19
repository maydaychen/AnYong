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

public class MyRadiiFriendAdapter extends RecyclerView.Adapter<MyRadiiFriendAdapter.ViewHolder> implements View.OnClickListener {
    private MyRadiiFriendAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<MyRadiiBean> mData;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int data);
    }

    public MyRadiiFriendAdapter(Context context, List<MyRadiiBean> mData) {
        this.mData = mData;
        Context context1 = context;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public MyRadiiFriendAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_radii_friend, viewGroup, false);
        MyRadiiFriendAdapter.ViewHolder vh = new MyRadiiFriendAdapter.ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(MyRadiiFriendAdapter.ViewHolder viewHolder, int position) {
//        viewHolder.mOrderNum.setText("订单号：" + mData.get(position).getOrdersn());


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

    public void setOnItemClickListener(MyRadiiFriendAdapter.OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_friend)
        ImageView mIvRadiiBrand;
        @BindView(R.id.tv_friend_name)
        TextView mTvRadiiBrand;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}