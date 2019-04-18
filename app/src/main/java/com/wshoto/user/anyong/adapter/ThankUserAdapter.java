package com.wshoto.user.anyong.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;
import com.wshoto.user.anyong.Bean.ThankUserBean;
import com.wshoto.user.anyong.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ThankUserAdapter extends RecyclerView.Adapter<ThankUserAdapter.ViewHolder> {

    private CheckInterface checkInterface;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;
    private List<ThankUserBean.DataBean> mData;
    private List<ThankUserBean.DataBean> mlist;
    private Context mContext;

    //define interface
    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int data);
    }

    public void setCheckInterface(CheckInterface checkInterface) {
        this.checkInterface = checkInterface;
    }

    public ThankUserAdapter(Context context, List<ThankUserBean.DataBean> mData, List<ThankUserBean.DataBean> list) {
        this.mData = mData;
        this.mContext = context;
        this.mlist = list;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_thank_user, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
//        view.setOnClickListener(this);
        return vh;
    }


    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
//        viewHolder.ivMessageLogo.setImageUrl( mData.get(position).get);
        viewHolder.textView3.setText(mData.get(position).getEnglish_name());
        viewHolder.logo.setImageUrl(mData.get(position).getAvatar());
        viewHolder.itemView.setTag(position);
        viewHolder.mRelativeLayout.setOnClickListener(view -> {
            if (viewHolder.cbSelect.isChecked()) {
                checkInterface.deletePerson(position);
            } else {
                checkInterface.addPerson(position);
            }
            viewHolder.cbSelect.setChecked(!viewHolder.cbSelect.isChecked());
        });
        for (ThankUserBean.DataBean dataBean : mlist) {
            if (dataBean.getId().equals(mData.get(position).getId())) {
                viewHolder.cbSelect.setChecked(true);
                return;
            }
        }
        viewHolder.cbSelect.setChecked(false);
//        if (mlist.contains(mData.get(position))) {
//            viewHolder.cbSelect.setChecked(true);
//        }else {
//            viewHolder.cbSelect.setChecked(false);
//        }
    }

    //获取数据的数量
    @Override
    public int getItemCount() {
        return mData.size();
    }


//    @Override
//    public void onClick(View v) {
//        if (mOnItemClickListener != null) {
//            //注意这里使用getTag方法获取数据
//            mOnItemClickListener.onItemClick(v, (Integer) v.getTag());
//        }
//    }
//
//    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
//        this.mOnItemClickListener = listener;
//    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rv_body)
        RelativeLayout mRelativeLayout;
        @BindView(R.id.textView3)
        TextView textView3;
        @BindView(R.id.logo)
        SmartImageView logo;
        @BindView(R.id.cb_select)
        CheckBox cbSelect;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface CheckInterface {
        /**
         * 组选框状态改变触发的事件
         *
         * @param groupPosition 组元素位置
         */
        void addPerson(int groupPosition);

        /**
         * 子选框状态改变时触发的事件
         *
         * @param groupPosition 组元素位置
         */
        void deletePerson(int groupPosition);
    }

}