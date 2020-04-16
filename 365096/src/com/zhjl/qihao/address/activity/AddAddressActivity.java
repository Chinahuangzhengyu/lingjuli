package com.zhjl.qihao.address.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.kyleduo.switchbutton.SwitchButton;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.address.fragment.AddressChooseFragment;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.util.Utils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddAddressActivity extends VolleyBaseActivity {
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.et_detail_address)
    EditText etDetailAddress;
    @BindView(R.id.sb_address)
    SwitchButton sbAddress;
    @BindView(R.id.rl_address)
    RelativeLayout rlAddress;
    private PopupWindow exitPopWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, "新增地址", this);
        etName.setSelection(0, etName.getText().toString().trim().length());
    }

    @OnClick({R.id.tv_address, R.id.sb_address, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_address:
                initCityAddressPop();
                break;
            case R.id.sb_address:
//                sbAddress.toggle();
                break;
            case R.id.iv_back:
                initExitPop();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            initExitPop();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initExitPop() {
        View popView = LayoutInflater.from(this).inflate(R.layout.message_pop, null);
        TextView yes = (TextView) popView.findViewById(R.id.yes);
        TextView not = (TextView) popView.findViewById(R.id.no);
        TextView tvMessageTitle = (TextView) popView.findViewById(R.id.tv_message_title);
        tvMessageTitle.setText("是否需要保存本次编辑结果？");
        yes.setText("保存");
        not.setText("不保存");
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitPopWindow.dismiss();

            }
        });
        not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exitPopWindow.dismiss();
                finish();
            }
        });
        exitPopWindow = new PopupWindow(popView, Utils.dip2px(mContext, 256), ViewGroup.LayoutParams.WRAP_CONTENT);
        //customerPopWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent, null));
        exitPopWindow.setFocusable(true);
        exitPopWindow.setOutsideTouchable(true);
        exitPopWindow.setAnimationStyle(R.style.AnimationPopupCenter);
        Utils.darkenBackground(0.3f, this);
        exitPopWindow.showAtLocation(rlAddress, Gravity.CENTER, 0, 0);


        exitPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Utils.darkenBackground(1f, AddAddressActivity.this);
                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
        });
    }

    private void initCityAddressPop() {
        AddressChooseFragment fragment = new AddressChooseFragment(this);
        fragment.show(getSupportFragmentManager(), "2");
    }
}
