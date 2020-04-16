package com.zhjl.qihao.freshshop.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abrefactor.view.RoundImageView;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.freshshop.api.ShopInterface;
import com.zhjl.qihao.freshshop.bean.ShopTypeMoreBean;
import com.zhjl.qihao.util.NewHeaderBar;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class ShopTypeMoreActivity extends VolleyBaseActivity {
    @BindView(R.id.gv_shop_type)
    GridView gvShopType;
    private int typeId;
    private List<ShopTypeMoreBean> shopTypeMoreBeans = new ArrayList<>();
    private ShopTypeAdapter shopTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_type_more);
        ButterKnife.bind(this);
        String name = getIntent().getStringExtra("name");
        NewHeaderBar.createCommomBack(this, name, this);
        typeId = getIntent().getIntExtra("typeId", 0);
        initData();
        shopTypeAdapter = new ShopTypeAdapter(shopTypeMoreBeans);
        gvShopType.setAdapter(shopTypeAdapter);
        gvShopType.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(mContext, ShopTypeListDetailActivity.class);
                intent.putExtra("typeId",shopTypeMoreBeans.get(position).getId());
                intent.putExtra("name",shopTypeMoreBeans.get(position).getName());
                startActivity(intent);
            }
        });
    }

    private void initData() {
        ShopInterface shopInterface = ApiHelper.getInstance().buildRetrofit(mContext).createService(ShopInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("id", typeId);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = shopInterface.shopMoreType(body);
        activityRequestPhpData(call, new RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
                String string = (String) result;
                try {
                    JSONArray array = new JSONArray(string);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.optJSONObject(i);
                        String toString = object.toString();
                        Gson gson = new Gson();
                        ShopTypeMoreBean typeBean = gson.fromJson(toString, ShopTypeMoreBean.class);
                        shopTypeMoreBeans.add(typeBean);
                    }
                    shopTypeAdapter.addData(shopTypeMoreBeans);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    class ShopTypeAdapter extends BaseAdapter {

        private List<ShopTypeMoreBean> shopTypeMoreBeans;

        public ShopTypeAdapter(List<ShopTypeMoreBean> shopTypeMoreBeans) {
            this.shopTypeMoreBeans = shopTypeMoreBeans;
        }

        public void addData(List<ShopTypeMoreBean> shopTypeMoreBeans) {
            this.shopTypeMoreBeans = shopTypeMoreBeans;
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return shopTypeMoreBeans.size();
        }

        @Override
        public Object getItem(int position) {
            return shopTypeMoreBeans.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            GvViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(mContext, R.layout.shop_type_item, null);
                holder = new GvViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (GvViewHolder) convertView.getTag();
            }
            PictureHelper.setOptionsGlide(mContext, 0, shopTypeMoreBeans.get(position).getImage(), holder.imgShopType, R.drawable.img_err);
            holder.tvShopTypeName.setText(shopTypeMoreBeans.get(position).getName());
            return convertView;
        }

        class GvViewHolder {
            @BindView(R.id.img_shop_type)
            RoundImageView imgShopType;
            @BindView(R.id.tv_shop_type_name)
            TextView tvShopTypeName;

            GvViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
}
