package com.zhjl.qihao.freshshop.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
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
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.freshshop.api.ShopInterface;
import com.zhjl.qihao.freshshop.bean.ReportingTypeBean;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.ResponseBody;
import retrofit2.Call;

@SuppressLint("ValidFragment")
public class ComplaintPopFragment extends DialogFragment {

    @BindView(R.id.tv_shop_evaluate_complaint)
    TextView tvShopEvaluateComplaint;
    @BindView(R.id.tv_shop_evaluate_cancel)
    TextView tvShopEvaluateCancel;
    Unbinder unbinder;
    private View view;
    private VolleyBaseActivity context;
    private ComplaintDetailPopFragment detailPopFragment;
    private List<ReportingTypeBean> list = new ArrayList<>();
    private int id;

    public ComplaintPopFragment(VolleyBaseActivity context, int id) {
        this.context = context;
        this.id =id;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_shop_evaluate_complaint, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }

    private void initData() {
        ShopInterface shopInterface = ApiHelper.getInstance().buildRetrofit(context).createService(ShopInterface.class);
        Call<ResponseBody> call = shopInterface.reportingType(ParamForNet.put(new HashMap<String, Object>()));
        context.activityRequestPhpData(call, new VolleyBaseActivity.RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                JSONArray array = new JSONArray((String) result);
                Gson gson = new Gson();
                for (int i = 0; i < array.length(); i++) {
                    String string = array.getJSONObject(i).toString();
                    ReportingTypeBean reportingTypeBean = gson.fromJson(string, ReportingTypeBean.class);
                    list.add(reportingTypeBean);
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

    @OnClick({R.id.tv_shop_evaluate_complaint, R.id.tv_shop_evaluate_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_shop_evaluate_complaint:
                getDialog().dismiss();
                detailPopFragment = new ComplaintDetailPopFragment(context,list,id);
                detailPopFragment.show(getChildFragmentManager(),"4");
                break;
            case R.id.tv_shop_evaluate_cancel:
                dismiss();
                break;
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
//        super.onDismiss(dialog);

    }
}
