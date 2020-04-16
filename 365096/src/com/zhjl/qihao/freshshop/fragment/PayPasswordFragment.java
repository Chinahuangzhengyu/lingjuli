package com.zhjl.qihao.freshshop.fragment;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abutil.NewStatusBarUtils;
import com.zhjl.qihao.freshshop.view.PayEditText;
import com.zhjl.qihao.util.Utils;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class PayPasswordFragment extends DialogFragment {
    @BindView(R.id.tv_status_color)
    TextView tvStatusColor;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.et_pay_password)
    PayEditText etPayPassword;
    Unbinder unbinder;
    private Context mContext;
    private VolleyBaseActivity activity;
    private View view;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
        this.activity = (VolleyBaseActivity) context;
    }

    public static PayPasswordFragment getInstance(double currentMoney) {
        PayPasswordFragment imgShowDialog = new PayPasswordFragment();
        Bundle bundle = new Bundle();
        bundle.putDouble("currentMoney", currentMoney);
        //传入值，跟Fragment传值方法一样
        imgShowDialog.setArguments(bundle);
        return imgShowDialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        Window window = getDialog().getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.TOP; //顶部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        window.setStatusBarColor(ContextCompat.getColor(mContext, R.color.text_color_f1));
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NORMAL, R.style.Dialog_PayDialog);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_pay_password, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        double currentMoney = getArguments().getDouble("currentMoney");
        DecimalFormat format = new DecimalFormat("0.00");
        tvPrice.setText(format.format(currentMoney) + "");
        tvStatusColor.setHeight(NewStatusBarUtils.getStatusBarHeight(mContext));
        etPayPassword.requestFocus();
        Utils.openKeyboard(etPayPassword);
        etPayPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (etPayPassword.getText().toString().trim().length() == 6) {
                    Utils.hideSoftInput(activity);
                    onIsFinishClickListener.OnClick(true, etPayPassword.getText().toString().trim());
                    dismiss();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        Utils.hideInput(activity);
    }

    private OnIsFinishClickListener onIsFinishClickListener;

    public void OnIsFinishClickListener(OnIsFinishClickListener onIsFinishClickListener) {
        this.onIsFinishClickListener = onIsFinishClickListener;
    }


    public interface OnIsFinishClickListener {
        void OnClick(boolean isFinish, String password);
    }

}
