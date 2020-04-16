package com.zhjl.qihao.freshshop.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.Session;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.freshshop.api.ShopInterface;
import com.zhjl.qihao.freshshop.bean.ReportingTypeBean;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

@SuppressLint("ValidFragment")
public class ComplaintDetailPopFragment extends DialogFragment {

    @BindView(R.id.ll_shop_evaluate_detail)
    LinearLayout llShopEvaluateDetail;
    @BindView(R.id.tv_shop_evaluate_detail_cancel)
    TextView tvShopEvaluateDetailCancel;
    Unbinder unbinder;
    private VolleyBaseActivity context;
    private View view;
    private List<ReportingTypeBean> list;
    private final Session session;
    private int id;

    public ComplaintDetailPopFragment(VolleyBaseActivity context, List<ReportingTypeBean> list, int id) {
        this.context = context;
        this.id = id;
        this.list = list;
        session = Session.get(context);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_shop_evaluate_complaint_detail, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        for (int i = 0; i < list.size(); i++) {
            TextView textView = new TextView(context);
            textView.setWidth(WindowManager.LayoutParams.MATCH_PARENT);
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.parseColor("#FF1F1F1F"));
            textView.setTextSize(16);
            if (i == 0) {
                textView.setPadding(0, Utils.dip2px(context, 16), 0, 0);
            } else if (i == 1) {
                textView.setPadding(0, Utils.dip2px(context, 20), 0, 0);
            } else if (i == list.size() - 1) {
                textView.setPadding(0, Utils.dip2px(context, 20), 0, Utils.dip2px(context, 16));
            }
            textView.setText(list.get(i).getName());
            llShopEvaluateDetail.addView(textView);
            final int position = i;
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requestData(list.get(position).getType());
                }
            });
        }
        return view;
    }

    private void requestData(String type) {
        ShopInterface shopInterface = ApiHelper.getInstance().buildRetrofit(context).createService(ShopInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", session.getmToken());
        map.put("comment_id", id);
        map.put("report_type", type);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = shopInterface.commentReporting(body);
        context.activityRequestPhpData(call, new VolleyBaseActivity.RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                JSONObject object = new JSONObject((String) result);
                boolean status = object.optBoolean("status");
                if (status) {
                    Toast.makeText(context, "举报成功！", Toast.LENGTH_SHORT).show();
                    dismiss();
                } else {
                    Utils.phpIsLogin(context, object.optInt("type"), object.optString("message"));
                }
            }

            @Override
            public void fail() {

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM;
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.tv_shop_evaluate_detail_cancel)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_shop_evaluate_detail_cancel:
                dismiss();
                break;
        }
    }
}
