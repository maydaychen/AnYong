package com.wshoto.user.anyong.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.wshoto.user.anyong.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ConfirmStep1Fragment extends Fragment {
    @BindView(R.id.et_login_mail)
    EditText mEtStep1Number;
    @BindView(R.id.et_login_pass)
    EditText mEtStep1Name;
    Unbinder unbinder;

    private FragmentManager mFragmentManager;

    public ConfirmStep1Fragment() {
        // Required empty public constructor
    }

    public static ConfirmStep1Fragment newInstance() {
        ConfirmStep1Fragment fragment = new ConfirmStep1Fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        View view = inflater.inflate(R.layout.fragment_confirm_step1, container, false);
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
        String num = mEtStep1Number.getText().toString();
        String name = mEtStep1Name.getText().toString();
        //如果填写的工号不足11位或者为空，就提示GPN不存在
        if (name.length() != 11) {
            Toast.makeText(getContext(), getText(R.string.step1_gpn), Toast.LENGTH_SHORT).show();
            return;
        }
        //检查填写的邮箱名称是否合法，如果不合法就有相关提示，如果合法就跳到ConfirmStep2Fragment
        if (!num.equals("") && !name.equals("") && num.contains("@cn.ey.com")) {
            mFragmentManager = getActivity().getSupportFragmentManager();
            //注意v4包的配套使用
            Fragment fragment = ConfirmStep2Fragment.newInstance(mEtStep1Number.getText().toString(), mEtStep1Name.getText().toString());
            mFragmentManager.beginTransaction().replace(R.id.id_content, fragment).addToBackStack("a").commit();
        } else {
            Toast.makeText(getContext(), getText(R.string.step1_error), Toast.LENGTH_SHORT).show();
        }
    }
}
