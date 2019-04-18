package com.wshoto.user.anyong.adapter;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.wshoto.user.anyong.Bean.MessageCenterBean;
import com.wshoto.user.anyong.R;

import java.util.List;

/**
 * 清单列表adapter
 * <p>
 * Created by DavidChen on 2018/5/30.
 */

public class InventoryAdapter extends BaseRecyclerViewAdapter<MessageCenterBean.DataBean> {

    private OnDeleteClickLister mDeleteClickListener;

    public InventoryAdapter(Context context, List<MessageCenterBean.DataBean> data) {
        super(context, data, R.layout.item_message_center);
    }

    @Override
    protected void onBindData(RecyclerViewHolder holder, MessageCenterBean.DataBean bean, int position) {
        View view = holder.getView(R.id.tv_delete);
        view.setTag(position);
        if (!view.hasOnClickListeners()) {
            view.setOnClickListener(v -> {
                if (mDeleteClickListener != null) {
                    mDeleteClickListener.onDeleteClick(v, (Integer) v.getTag());
                }
            });
        }
        ((TextView) holder.getView(R.id.tv_message_title)).setText(bean.getTitle());
        ((TextView) holder.getView(R.id.tv_message_time)).setText(bean.getPublished());
        ((TextView) holder.getView(R.id.tv_message_sub_title)).setText(bean.getContent());

        if (bean.getIsread() == 0) {
            ((View) holder.getView(R.id.dot_read)).setVisibility(View.VISIBLE);
        }
    }

    public void setOnDeleteClickListener(OnDeleteClickLister listener) {
        this.mDeleteClickListener = listener;
    }

    public interface OnDeleteClickLister {
        void onDeleteClick(View view, int position);
    }
}
