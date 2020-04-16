package com.zhjl.qihao.common.activity;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.util.AbStrUtil;
import com.zhjl.qihao.abutil.ToastUtils;

public class InvoiceActivity extends VolleyBaseActivity implements OnClickListener {
    public static final int INVOICE_REQUEST_CODE = 9045;

    EditText etInvoiceTitle;
    RadioGroup rgInvoiceContent;
    JSONObject invoiceInfo;
    String shopId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        initView();
        initData();
    }

    public void initView() {
        findViewById(R.id.img_back).setOnClickListener(this);
        findViewById(R.id.btn_confirm).setOnClickListener(this);
        etInvoiceTitle = (EditText) findViewById(R.id.et_invoice_title);
        rgInvoiceContent = (RadioGroup) findViewById(R.id.rg_invoice_content);
    }

    public void initData() {
        shopId = getIntent().getStringExtra("shopId");
        String invoiceInfoStr = getIntent().getStringExtra("invoiceInfo");
        try {
            if (!AbStrUtil.isEmpty(invoiceInfoStr)) {
                invoiceInfo = new JSONObject(invoiceInfoStr);
                etInvoiceTitle.setText(invoiceInfo.optString("invoiceTitle"));
                for (int i = 0; i < rgInvoiceContent.getChildCount(); i++) {
                    View view = rgInvoiceContent.getChildAt(i);
                    if (view instanceof RadioButton) {
                        RadioButton button = (RadioButton) view;
                        if (button.getText().equals(invoiceInfo.optString("invoiceContent"))) {
                            button.setChecked(true);
                        }
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (invoiceInfo == null) invoiceInfo = new JSONObject();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                InvoiceActivity.this.finish();
                break;
            case R.id.btn_confirm:
                if (AbStrUtil.isEmpty(etInvoiceTitle.getText().toString())) {
                    ToastUtils.showToast(mContext, "请输入发票抬头信息");
                    return;
                }
                try {
                    invoiceInfo.put("invoiceTitle", etInvoiceTitle.getText().toString());
                    RadioButton radioButton = (RadioButton) rgInvoiceContent.findViewById(rgInvoiceContent.getCheckedRadioButtonId());
                    invoiceInfo.put("invoiceContent", radioButton.getText());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent intent = getIntent();
                intent.putExtra("shopId", shopId);
                intent.putExtra("invoiceInfo", invoiceInfo.toString());
                InvoiceActivity.this.setResult(RESULT_OK, intent);
                InvoiceActivity.this.finish();
                break;
            default:
                break;
        }

    }

}
