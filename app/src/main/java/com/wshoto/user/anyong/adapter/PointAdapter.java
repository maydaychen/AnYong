package com.wshoto.user.anyong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wshoto.user.anyong.Bean.PointBean;
import com.wshoto.user.anyong.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PointAdapter extends RecyclerView.Adapter<PointAdapter.ViewHolder> implements View.OnClickListener {


    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<PointBean.DataBean> mData;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int data);
    }

    public PointAdapter(Context context, List<PointBean.DataBean> mData) {
        this.mData = mData;
        Context context1 = context;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_point, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        view.setOnClickListener(this);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
//        viewHolder.ivMessageLogo.setImageUrl( mData.get(position).get);
        viewHolder.tvPointTime.setText(mData.get(position).getCreated());
        viewHolder.tvPointSource.setText(mData.get(position).getIntegral_log());
        if (mData.get(position).getOpt().equals("+")) {
            viewHolder.tvType.setText("获得");
        }else {
            viewHolder.tvType.setText("失去");
        }
        viewHolder.tvPointNum.setText(mData.get(position).getValue());
        viewHolder.itemView.setTag(position);
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        if (mData == null) {
            return 0;
        } else {
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
        @BindView(R.id.tv_point_time)
        TextView tvPointTime;
        @BindView(R.id.tv_point_source)
        TextView tvPointSource;
        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_point_num)
        TextView tvPointNum;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}