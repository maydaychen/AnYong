package com.wshoto.user.anyong.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.wshoto.user.anyong.Bean.HealthyTaskBean;
import com.wshoto.user.anyong.Bean.ThankBean;
import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.SharedPreferencesUtils;
import com.wshoto.user.anyong.adapter.MessageCenterAdapter;
import com.wshoto.user.anyong.adapter.ThankUListAdapter;
import com.wshoto.user.anyong.http.HttpJsonMethod;
import com.wshoto.user.anyong.http.ProgressSubscriber;
import com.wshoto.user.anyong.http.SubscriberOnNextListener;
import com.wshoto.user.anyong.ui.activity.MessageCenterActivity;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThankListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThankListFragment extends Fragment {

    @BindView(R.id.rv_thankyou)
    RecyclerView mRvThankyou;
    Unbinder unbinder;
    private SubscriberOnNextListener<JSONObject> thankOnNext;
    private ThankBean mThankBean;
    private Gson mGson = new Gson();

    public ThankListFragment() {
        // Required empty public constructor
    }


    public static ThankListFragment newInstance() {
        ThankListFragment fragment = new ThankListFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thankOnNext = jsonObject -> {
            if (jsonObject.getInt("code") == 1) {
                mThankBean = mGson.fromJson(jsonObject.toString(), ThankBean.class);
                mRvThankyou.setLayoutManager(new LinearLayoutManager(getActivity()));
                ThankUListAdapter messageCenterAdapter = new ThankUListAdapter(getActivity(), mThankBean.getMessage().getData());
                mRvThankyou.setAdapter(messageCenterAdapter);
            } else {
                Toast.makeText(getContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
            }
        };
        HttpJsonMethod.getInstance().thankList(
                new ProgressSubscriber(thankOnNext, getActivity()),(String) SharedPreferencesUtils.getParam(getActivity(), "session", ""));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thank_list, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
