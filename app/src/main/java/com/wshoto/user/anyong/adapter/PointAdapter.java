package com.wshoto.user.anyong.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
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
    private Context mContext;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int data);
    }

    public PointAdapter(Context context, List<PointBean.DataBean> mData) {
        this.mData = mData;
        mContext = context;
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
        viewHolder.tvPointTime.setText(mData.get(position).getCreated());
//        viewHolder.tvPointSource.setText(mData.get(position).getIntegral_log());
        String way = "";
        if (mData.get(position).getOpt().equals("+")) {
//            viewHolder.tvType.setText(mContext.getText(R.string.obtain));
            way = (mContext.getText(R.string.obtain)).toString();
        } else {
//            viewHolder.tvType.setText(mContext.getText(R.string.lose));
            way = (mContext.getText(R.string.lose)).toString();
        }
//        viewHolder.tvPointNum.setText(mData.get(position).getValue());
        viewHolder.tvPoint.setText(String.format((String) mContext.getResources().getText(R.string.point_detail),
                mData.get(position).getIntegral_log(), way, mData.get(position).getValue()));

        SpannableString spannableString = new SpannableString(viewHolder.tvPoint.getText().toString());
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FFE600")), way.length() + mData.get(position).getIntegral_log().length(),
                way.length() + mData.get(position).getIntegral_log().length() + mData.get(position).getValue().length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        viewHolder.tvPoint.setText(spannableString);


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
        @BindView(R.id.tv_point)
        TextView tvPoint;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}