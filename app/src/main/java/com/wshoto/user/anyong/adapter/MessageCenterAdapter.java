package com.wshoto.user.anyong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.wshoto.user.anyong.Bean.MessageCenterBean;
import com.wshoto.user.anyong.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageCenterAdapter extends RecyclerView.Adapter<MessageCenterAdapter.ViewHolder> implements View.OnClickListener {

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<MessageCenterBean.DataBean> mData;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int data);
    }

    public MessageCenterAdapter(Context context, List<MessageCenterBean.DataBean> mData) {
        this.mData = mData;
        Context context1 = context;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_message_center, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
//        viewHolder.ivMessageLogo.setImageUrl( mData.get(position).get);
        viewHolder.tvMessageTitle.setText(mData.get(position).getTitle());
        viewHolder.tvMessageTime.setText(mData.get(position).getPublished());
        viewHolder.tvMessageSubTitle.setText(mData.get(position).getContent());
        if (mData.get(position).getIsread()==0) {
            viewHolder.dot.setVisibility(View.VISIBLE);
        }
        viewHolder.itemView.setTag(position);
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        if (mData==null) {
            return 0;
        }else {
            return mData.size();
        }
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
        @BindView(R.id.iv_message_logo)
        SmartImageView ivMessageLogo;
        @BindView(R.id.tv_message_title)
        TextView tvMessageTitle;
        @BindView(R.id.tv_message_time)
        TextView tvMessageTime;
        @BindView(R.id.tv_message_sub_title)
        TextView tvMessageSubTitle;
        @BindView(R.id.dot_read)
        View dot;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}