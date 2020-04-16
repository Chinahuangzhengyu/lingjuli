package com.zhjl.qihao.freshshop.adapter;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.Session;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abrefactor.view.RoundImageView;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.freshshop.api.ShopInterface;
import com.zhjl.qihao.freshshop.bean.ShopCarListBean;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class ShopCarAdapter extends RecyclerView.Adapter<ShopCarAdapter.ViewHolder> {


    private VolleyBaseActivity context;
    public List<ShopCarListBean> shopList;
    public Map<Integer, Boolean> check = new HashMap<>();
    public Map<Integer, Boolean> deleteCheck = new HashMap<>();
    private final Session session;
    private PopupWindow shopDeletePopWindow;

    public ShopCarAdapter(VolleyBaseActivity mContext, List<ShopCarListBean> shopList) {
        context = mContext;
        this.shopList = shopList;
        session = Session.get(context);
    }

    public void addData(List<ShopCarListBean> shopList) {
        this.shopList = shopList;
        check.clear();
        for (int i = 0; i < shopList.size(); i++) {
            check.put(i, false);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.shop_car_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {
        try {
            holder.tvShopName.setText(shopList.get(i).getName());
            holder.tvPrice.setText("¥" + shopList.get(i).getPrice());
            holder.tvShopListSum.setText(shopList.get(i).getNumber() + "");
            PictureHelper.setImageView(context, shopList.get(i).getImage(), holder.imgShop, R.drawable.img_err);
            if (shopList.get(i).getUnit_name() != null && !shopList.get(i).getUnit_name().equals("")) {
                holder.tvShopType.setText(shopList.get(i).getUnit_name());
                holder.tvShopType.setVisibility(View.VISIBLE);
            } else {
                holder.tvShopType.setVisibility(View.GONE);
            }
            if (shopList.get(i).getStock() == 0) {
                holder.tvStock.setText("已售罄");
                holder.tvStock.setTextColor(ContextCompat.getColor(context, R.color.color_f80000));
            } else {
                holder.tvStock.setText("库存" + shopList.get(i).getStock());
                holder.tvStock.setTextColor(ContextCompat.getColor(context, R.color.text_color_f1));
            }
            holder.llShopCarItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Integer.parseInt(holder.tvShopListSum.getText().toString()) > shopList.get(i).getStock()) {
                        Toast.makeText(context, "商品库存不足！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    check.put(i, !check.get(i).booleanValue());
                    holder.ckStatus.setChecked(check.get(i));
                    boolean allFinish = isAllFinish();
                    setOnItemClickListener.OnItemClick(v, i, allFinish, calcPrice());
                }
            });
            holder.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initDeletePop(i);
                }
            });
            holder.imgShopListAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (shopList.get(i).getStock() > shopList.get(i).getNumber()) {
                        holder.imgShopListAdd.setEnabled(false);
                        addShopCar(i, true, holder, holder.imgShopListAdd);
                    } else {
                        Toast.makeText(context, "库存不足！", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            holder.imgShopListReduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int number = shopList.get(i).getNumber();
                    if (number > 1) {
                        holder.imgShopListReduce.setEnabled(false);
                        addShopCar(i, false, holder, holder.imgShopListReduce);
                    }
                }
            });
            holder.ckStatus.setChecked(check.get(i));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addShopCar(final int i, final boolean isAdd, final ViewHolder holder, final ImageView imgShopList) {
        ShopInterface shopInterface = ApiHelper.getInstance().buildRetrofit(context).createService(ShopInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", session.getmToken());
        map.put("product_id", shopList.get(i).getProduct_id());
        if (shopList.get(i).getUnit_name() != null && !shopList.get(i).getUnit_name().equals("")) {
            map.put("product_more_unit_id", shopList.get(i).getProduct_more_unit_id());
        }
        if (isAdd) {
            map.put("number", 1);
        } else {
            map.put("number", -1);
        }
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = shopInterface.addShopCar(body);
        context.activityRequestPhpData(call, new VolleyBaseActivity.RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                imgShopList.setEnabled(true);
                String string = (String) result;
                try {
                    JSONObject object = new JSONObject(string);
                    boolean status = object.optBoolean("status");
                    if (status) {
                        if (isAdd) {
                            shopList.get(i).setNumber(shopList.get(i).getNumber() + 1);
                            holder.tvShopListSum.setText(shopList.get(i).getNumber() + "");
                            getPriceChangeListener.getPrice(calcPrice());
                        } else {
                            shopList.get(i).setNumber(shopList.get(i).getNumber() - 1);
                            holder.tvShopListSum.setText(shopList.get(i).getNumber() + "");
                            getPriceChangeListener.getPrice(calcPrice());
                        }
                    } else {
                        int type = object.optInt("type");
                        if (type == 0) {
                            Toast.makeText(context, object.optString("message"), Toast.LENGTH_SHORT).show();
                        } else if (type == 1) {
                            Utils.loginPopWindow(context);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fail() {
                imgShopList.setEnabled(true);
            }
        });
    }

    private double calcPrice() {
        double sum = 0;
        for (int i = 0; i < check.size(); i++) {
            if (check.get(i)) {
                String price = shopList.get(i).getPrice();
                int number = shopList.get(i).getNumber();
                BigDecimal mPrice = new BigDecimal(price);
                BigDecimal mNumber = new BigDecimal(number);
                double value = mNumber.multiply(mPrice).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
                sum += value;
            }
        }
        BigDecimal price = new BigDecimal(sum).setScale(2, BigDecimal.ROUND_HALF_UP);
        return price.doubleValue();
    }


    private boolean isAllFinish() {
        for (int i = 0; i < check.size(); i++) {
            if (!check.get(i)) {
                return false;
            }
        }
        return true;
    }

    public boolean isOneClick() {
        for (int i = 0; i < check.size(); i++) {
            if (check.get(i)) {
                return true;
            }
        }
        return false;
    }

    public double allCheck(boolean isAll) {
        if (isAll) {
            for (int i = 0; i < check.size(); i++) {
                if (shopList.get(i).getStock() >= shopList.get(i).getNumber()) {
                    check.put(i, isAll);
                }
            }
        } else {
            for (int i = 0; i < check.size(); i++) {
                check.put(i, isAll);
            }
        }
        notifyDataSetChanged();
        return calcPrice();
    }


    private void initDeletePop(final int i) {
        View popView = LayoutInflater.from(context).inflate(R.layout.login_pop
                , null);
        TextView yes = (TextView) popView.findViewById(R.id.yes);
        TextView not = (TextView) popView.findViewById(R.id.no);
        TextView tvMessageTitle = (TextView) popView.findViewById(R.id.tv_message_title);
        tvMessageTitle.setText("确定要删除该商品吗？");
        shopDeletePopWindow = new PopupWindow(popView, Utils.dip2px(context, 256), ViewGroup.LayoutParams.WRAP_CONTENT);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestDeleteShop(i);
            }
        });
        not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shopDeletePopWindow.dismiss();
            }
        });
        shopDeletePopWindow.setFocusable(true);
        shopDeletePopWindow.setOutsideTouchable(true);
        Utils.darkenBackground(0.8f, context);
        shopDeletePopWindow.showAtLocation(context.findViewById(android.R.id.content).getRootView(), Gravity.CENTER, 0, 0);


        shopDeletePopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Utils.darkenBackground(1f, context);
                context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
        });
    }

    private void requestDeleteShop(final int i) {
        ShopInterface shopInterface = ApiHelper.getInstance().buildRetrofit(context).createService(ShopInterface.class);
        final Map<String, Object> map = new HashMap<>();
        map.put("user_token", session.getmToken());
        List<String> list = new ArrayList<>();
//        if (shopList.get(i).g()!=null&&!shopList.get(i).getUnit_name().equals("")){
//            list.add(shopList.get(i).getProduct_more_unit_id()+"");
//        }else {
//            list.add(shopList.get(i).getProduct_id());
//        }
        list.add(shopList.get(i).getId());
        RequestBody body = ParamForNet.putContainsArray(map, "pids", list);
//        map.put("id", shopList.get(i).getId());
        Call<ResponseBody> call = shopInterface.shopCarRemove(body);
        context.activityRequestPhpData(call, new VolleyBaseActivity.RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
                String string = (String) result;
                try {
                    JSONObject object = new JSONObject(string);
                    boolean status = object.optBoolean("status");
                    if (status) {
                        Iterator<Integer> iterator = check.keySet().iterator(); //删除条目
                        while (iterator.hasNext()) {
                            int key = iterator.next();
                            if (key == i) {
                                iterator.remove();
                                break;
                            }
                        }
                        Iterator<Integer> currentIterator = check.keySet().iterator(); //删除条目
                        int position = 0;
                        deleteCheck.clear();
                        while (currentIterator.hasNext()) {
                            deleteCheck.put(position, check.get(currentIterator.next()));
                            position++;
                        }
                        check.clear();
                        for (int j = 0; j < deleteCheck.size(); j++) {
                            check.put(j, deleteCheck.get(j));
                        }
                        shopList.remove(i);
                        shopDeletePopWindow.dismiss();
                        notifyItemRemoved(i);
                        notifyItemRangeChanged(i, getItemCount());
                        double calcPrice = calcPrice();
                        getPriceChangeListener.getPrice(calcPrice);
                    } else {
                        int type = object.optInt("type");
                        Toast.makeText(context, object.optString("message"), Toast.LENGTH_SHORT).show();
                        if (type == 1) {
                            Utils.loginPopWindow(context);
                        }
                    }
                    shopDeletePopWindow.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void fail() {

            }
        });
    }

    @Override
    public int getItemCount() {
        return shopList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.ck_status)
        CheckBox ckStatus;
        @BindView(R.id.img_shop)
        RoundImageView imgShop;
        @BindView(R.id.tv_shop_name)
        TextView tvShopName;
        @BindView(R.id.tv_shop_type)
        TextView tvShopType;
        @BindView(R.id.img_delete)
        ImageView imgDelete;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.img_shop_list_reduce)
        ImageView imgShopListReduce;
        @BindView(R.id.tv_shop_list_sum)
        TextView tvShopListSum;
        @BindView(R.id.img_shop_list_add)
        ImageView imgShopListAdd;
        @BindView(R.id.ll_shop_car_item)
        LinearLayout llShopCarItem;
        @BindView(R.id.tv_stock)
        TextView tvStock;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private SetOnItemClickListener setOnItemClickListener;

    public void setSetOnItemClickListener(SetOnItemClickListener setOnItemClickListener) {
        this.setOnItemClickListener = setOnItemClickListener;
    }

    public interface SetOnItemClickListener {
        void OnItemClick(View view, int position, boolean isAllFinish, double sumPrice);
    }


    private GetPriceChangeListener getPriceChangeListener;

    public void setGetPriceChangeListener(GetPriceChangeListener getPriceChangeListener) {
        this.getPriceChangeListener = getPriceChangeListener;
    }

    public interface GetPriceChangeListener {
        void getPrice(double sumPrice);
    }
}
