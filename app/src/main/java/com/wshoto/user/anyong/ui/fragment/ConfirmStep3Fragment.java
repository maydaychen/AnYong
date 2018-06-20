package com.wshoto.user.anyong.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.wshoto.user.anyong.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ConfirmStep3Fragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";
    private static final String ARG_PARAM4 = "param4";
    @BindView(R.id.et_step3_number)
    EditText etStep3Pass;
    @BindView(R.id.et_step3_name)
    EditText etStep3Pass2;
    private FragmentManager mFragmentManager;
    private Unbinder unbinder;

    private String mParam1;
    private String mParam2;
    private String mParam3;
    private String mParam4;

    public ConfirmStep3Fragment() {
        // Required empty public constructor
    }

    public static ConfirmStep3Fragment newInstance(String param1, String param2, String param3, String param4) {
        ConfirmStep3Fragment fragment = new ConfirmStep3Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        args.putString(ARG_PARAM4, param4);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getString(ARG_PARAM3);
            mParam4 = getArguments().getString(ARG_PARAM4);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_confirm_step3, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tv_step1_next)
    public void onViewClicked() {
        mFragmentManager = getActivity().getSupportFragmentManager();
        //注意v4包的配套使用
        Fragment fragment = ConfirmStep4Fragment.newInstance(mParam1, mParam2, mParam3, mParam4, etStep3Pass.getText().toString());
        mFragmentManager.beginTransaction().replace(R.id.id_content, fragment).addToBackStack("a").commit();
    }
}
