package com.zhjl.qihao.integration.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.integration.adapter.MembershipCardAdapter;
import com.zhjl.qihao.integration.api.IntegralInterface;
import com.zhjl.qihao.integration.bean.CardListBean;
import com.zhjl.qihao.integration.utils.RequestUtils;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.zq.ApiHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Call;

import static com.zhjl.qihao.integration.activity.AddMembershipCardActivity.ADD_CARD_RESULT_CODE;

/**
 * 会员卡页面
 */
public class MembershipCardActivity extends VolleyBaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_right)
    TextView tvRight;
    @BindView(R.id.xrv_membership_card)
    XRecyclerView xrvMembershipCard;
    private List<CardListBean> list = new ArrayList<>();
    private MembershipCardAdapter membershipCardAdapter;
    public static final int REQUEST_ADD_CARD_CODE = 0x123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_membership_card);
        ButterKnife.bind(this);
        NewHeaderBar.createCommomBack(this, "我的会员卡", "新增", this);
        tvRight.setTextColor(ContextCompat.getColor(mContext, R.color.new_theme_color));
        tvRight.setTextSize(14);
        Drawable drawable = ContextCompat.getDrawable(mContext, R.drawable.img_card_add);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        tvRight.setCompoundDrawables(null, null, drawable, null);
        xrvMembershipCard.setLayoutManager(new LinearLayoutManager(mContext));
        membershipCardAdapter = new MembershipCardAdapter(mContext, list);
        xrvMembershipCard.setAdapter(membershipCardAdapter);

        xrvMembershipCard.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                initData();
            }

            @Override
            public void onLoadMore() {

            }
        });
    }

    private void initData() {
        IntegralInterface integralInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(IntegralInterface.class);
        Call<ResponseBody> call = RequestUtils.membershipCardList(mSession.getmToken(), integralInterface);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                JSONObject object = new JSONObject((String) result);
                boolean status = object.optBoolean("status");
                list.clear();
                if (status) {
                    JSONArray cards = object.optJSONArray("cards");
                    for (int i = 0; i < cards.length(); i++) {
                        JSONObject cardObj = cards.optJSONObject(i);
                        CardListBean cardListBean = new CardListBean();
                        cardListBean.setCard_id(cardObj.optString("card_id"));
                        cardListBean.setMoney(cardObj.optString("money"));
                        cardListBean.setIs_default(cardObj.optInt("is_default"));
                        list.add(cardListBean);
                    }
                    membershipCardAdapter.addData(list);
                    xrvMembershipCard.refreshComplete();
                } else {
                    Utils.phpIsLogin(MembershipCardActivity.this, object.optInt("type"), object.optString("message"));
                }
            }

            @Override
            public void fail() {
                xrvMembershipCard.refreshComplete();
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.tv_right})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right:
                Intent intent = new Intent(mContext, AddMembershipCardActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }
}
