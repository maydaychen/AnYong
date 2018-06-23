package com.wshoto.user.anyong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.wshoto.user.anyong.Bean.CalendarDayEventBean;
import com.wshoto.user.anyong.Bean.MessageCenterBean;
import com.wshoto.user.anyong.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CalendarEventListAdapter extends RecyclerView.Adapter<CalendarEventListAdapter.ViewHolder> implements View.OnClickListener {


    private CalendarEventListAdapter.OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<CalendarDayEventBean.DataBean> mData;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int data);
    }

    public CalendarEventListAdapter(Context context, List<CalendarDayEventBean.DataBean> mData) {
        this.mData = mData;
        Context context1 = context;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public CalendarEventListAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_calendar_list, viewGroup, false);
        CalendarEventListAdapter.ViewHolder vh = new CalendarEventListAdapter.ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(CalendarEventListAdapter.ViewHolder viewHolder, int position) {
//        viewHolder.ivMessageLogo.setImageUrl( mData.get(position).get);
        viewHolder.textView4.setText(mData.get(position).getStart_time());
        viewHolder.textView5.setText(mData.get(position).getContent());
        viewHolder.itemView.setTag(position);
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

    public void setOnItemClickListener(CalendarEventListAdapter.OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textView4)
        TextView textView4;
        @BindView(R.id.textView5)
        TextView textView5;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
