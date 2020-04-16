package com.zhjl.qihao.abmine;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.util.NewHeaderBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zhjl.qihao.util.UrlChangeUtils.API_HOST;
import static com.zhjl.qihao.util.UrlChangeUtils.JAVA_PORT_NUMBER;

public class UserHelpActivity extends VolleyBaseActivity {
    @BindView(R.id.rl_user_agreement)
    RelativeLayout rlUserAgreement;
    @BindView(R.id.rl_return_shop_notice)
    RelativeLayout rlReturnShopNotice;
    @BindView(R.id.rl_send_shop_notice)
    RelativeLayout rlSendShopNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_help);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this,"使用帮助",this);
    }

    @OnClick({R.id.rl_user_agreement, R.id.rl_return_shop_notice, R.id.rl_send_shop_notice,R.id.iv_back})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.rl_user_agreement:
                intent.setClass(mContext,UserAgreementActivity.class);
                intent.putExtra("webContent", API_HOST+JAVA_PORT_NUMBER+"/static/protocol/user.html");
                intent.putExtra("name","用户协议");
                startActivity(intent);
                break;
            case R.id.rl_return_shop_notice:
                intent.setClass(mContext,UserAgreementActivity.class);
                intent.putExtra("webContent",API_HOST+JAVA_PORT_NUMBER+"/static/protocol/return_goods.html");
                intent.putExtra("name","退换货须知");
                startActivity(intent);
                break;
            case R.id.rl_send_shop_notice:
                intent.setClass(mContext,UserAgreementActivity.class);
                intent.putExtra("webContent",API_HOST+JAVA_PORT_NUMBER+"/static/protocol/delivery.html");
                intent.putExtra("name","送货须知");
                startActivity(intent);
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
