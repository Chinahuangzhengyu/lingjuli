package com.zhjl.qihao.address.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

@SuppressLint("ValidFragment")
public class AddressChooseFragment extends DialogFragment implements View.OnClickListener {

    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.ll_fragment)
    LinearLayout llFragment;
    Unbinder unbinder;
    @BindView(R.id.tab_city)
    TabLayout tabCity;
    private Activity context;
    private View view;



    public AddressChooseFragment(Activity context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_city_choose, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        tvCancel.setOnClickListener(this);

        AddressListFragment addressListFragment = new AddressListFragment();
        if (!addressListFragment.isAdded()) {
            getChildFragmentManager().beginTransaction().add(R.id.ll_fragment, addressListFragment).commit();
        }
        getChildFragmentManager().beginTransaction().show(addressListFragment).commit();

        tabCity.addTab(tabCity.newTab().setCustomView(R.layout.tab_city_item));
        TextView tabItemText = tabCity.getTabAt(0).getCustomView().findViewById(R.id.tab_item_text);
        tabItemText.setTextColor(Color.parseColor("#23ac38"));
        tabItemText.setText("选择省份/地区");
        tabCity.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView textView = tab.getCustomView().findViewById(R.id.tab_item_text);
                textView.setTextColor(Color.parseColor("#23ac38"));
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                TextView textView = tab.getCustomView().findViewById(R.id.tab_item_text);
                textView.setTextColor(Color.parseColor("#1f1f1f"));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        Window window = getDialog().getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; //底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }
}
