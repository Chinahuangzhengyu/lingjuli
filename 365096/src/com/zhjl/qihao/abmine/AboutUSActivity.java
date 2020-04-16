package com.zhjl.qihao.abmine;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zhjl.qihao.util.UrlChangeUtils.API_HOST;
import static com.zhjl.qihao.util.UrlChangeUtils.JAVA_PORT_NUMBER;

/**
 * Created by Administrator on 2017/7/12.
 */

public class AboutUSActivity extends VolleyBaseActivity {

    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.rl_company)
    RelativeLayout rlCompany;
    @BindView(R.id.rl_use_help)
    RelativeLayout rlUseHelp;
    @BindView(R.id.rl_call_me)
    RelativeLayout rlCallMe;
    @BindView(R.id.tv_service_agreement)
    TextView tvServiceAgreement;
    @BindView(R.id.tv_private_agreement)
    TextView tvPrivateAgreement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ab_activity_aboutus);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, "关于我们", this);
        tvPrivateAgreement.setText(Html.fromHtml("<u>隐私政策</u>"));
        tvServiceAgreement.setText(Html.fromHtml("<u>服务协议</u>"));
        PackageManager packageManager = mContext.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(mContext.getPackageName(), 0);
            tvVersion.setText("Version" + packageInfo.versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ll_head_left:
                finish();
                break;
        }
    }

    @OnClick({R.id.rl_company, R.id.rl_use_help, R.id.rl_call_me,R.id.tv_private_agreement,R.id.tv_service_agreement})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.rl_company:
                intent.setClass(mContext, CompanyInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_use_help:
                intent.setClass(mContext, UserHelpActivity.class);
                startActivity(intent);
                break;
            case R.id.rl_call_me:
                Utils.callPerson(this, "是否拨打电话0855-8552588", "0855-8552588");
                break;
            case R.id.tv_private_agreement:
                intent.setClass(mContext,UserAgreementActivity.class);
                intent.putExtra("name","隐私政策");
                intent.putExtra("webContent",API_HOST+JAVA_PORT_NUMBER+"/static/protocol/private_prot.html");
                startActivity(intent);
                break;
            case R.id.tv_service_agreement:
                intent.setClass(mContext,UserAgreementActivity.class);
                intent.putExtra("name","服务协议");
                intent.putExtra("webContent",API_HOST+JAVA_PORT_NUMBER+"/static/protocol/service_prot.html");
                startActivity(intent);
                break;
        }
    }

}
