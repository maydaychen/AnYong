package com.wshoto.user.anyong.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.wshoto.user.anyong.Bean.MyRadiiBean;
import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.ui.activity.NewFriendActivity;
import com.wshoto.user.anyong.ui.activity.NewFriendInfoActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewFriendApplyAdapter extends RecyclerView.Adapter<NewFriendApplyAdapter.ViewHolder> implements View.OnClickListener {

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<MyRadiiBean> mData;
    private Context mContext;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int data);
    }

    public NewFriendApplyAdapter(Context context, List<MyRadiiBean> mData) {
        this.mData = mData;
        this.mContext = context;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_new_friend_request, viewGroup, false);
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
//        viewHolder.itemView.setTag(position);
        if (true) {
            viewHolder.mTvNewFriend.setTextColor(mContext.getColor(R.color.white));
            viewHolder.mTvNewFriend.setBackground(mContext.getDrawable(R.drawable.boder_new_friend_apply));
            viewHolder.mTvNewFriend.setOnClickListener(view -> {
                Intent intent = new Intent(mContext, NewFriendInfoActivity.class);
                mContext.startActivity(intent);
            });
        }

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
        @BindView(R.id.iv_new_friend_logo)
        ImageView ivNewFriendLogo;
        @BindView(R.id.new_firend_name)
        TextView newFirendName;
        @BindView(R.id.tv_new_friend_status)
        TextView mTvNewFriend;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}