package com.wshoto.user.anyong.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.wshoto.user.anyong.R;
import com.wshoto.user.anyong.ui.activity.ConfirmSuccessActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConfirmStep2Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConfirmStep2Fragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    @BindView(R.id.tv_step2_name)
    TextView mTvStep2Name;
    @BindView(R.id.et_step1_number)
    EditText mEtStep1Number;
    @BindView(R.id.tv_get_ems)
    TextView mTvGetEms;
    @BindView(R.id.et_step1_name)
    EditText mEtStep1Name;
    Unbinder unbinder;

    private String mParam1;
    private String mParam2;
    private FragmentManager mFragmentManager;


    public ConfirmStep2Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConfirmStep2Fragment.
     */
    public static ConfirmStep2Fragment newInstance(String param1, String param2) {
        ConfirmStep2Fragment fragment = new ConfirmStep2Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm_step2, container, false);
        unbinder = ButterKnife.bind(this, view);
        mTvStep2Name.setText(mParam2);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_get_ems, R.id.tv_step1_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_get_ems:
                break;
            case R.id.tv_step1_next:
                startActivity(new Intent(getActivity(), ConfirmSuccessActivity.class));
                break;
            default:
                break;
        }
    }
}
