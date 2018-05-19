package com.wshoto.user.anyong.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wshoto.user.anyong.R;

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
