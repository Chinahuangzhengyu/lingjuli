package com.zhjl.qihao.order.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.Session;
import com.zhjl.qihao.abrefactor.view.RoundImageView;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.freshshop.activity.ShopPayActivity;
import com.zhjl.qihao.order.activity.OrderDetailActivity;
import com.zhjl.qihao.order.activity.ReplacementDetailActivity;
import com.zhjl.qihao.order.activity.RequestReplacementActivity;
import com.zhjl.qihao.order.activity.ShopFinishEvaluateActivity;
import com.zhjl.qihao.order.api.OrderApiInterface;
import com.zhjl.qihao.order.bean.AllOrderListBean;
import com.zhjl.qihao.util.Utils;
import com.zhjl.qihao.view.ListViewForScrollView;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllOrderAdapter extends RecyclerView.Adapter<AllOrderAdapter.ViewHolder> {

    private Activity context;
    public List<AllOrderListBean.OrderListBean> orderList;
    private final Session session;
    private PopupWindow surePopWindow;
    public static final int REQUEST_REFRESH = 0x116;

    public AllOrderAdapter(Activity context, List<AllOrderListBean.OrderListBean> orderList) {
        this.context = context;
        this.orderList = orderList;
        session = Session.get(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.order_list_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {
        try {
            holder.tvStatus.setText(orderList.get(i).getStatus_format());
            holder.tvType.setText(orderList.get(i).getDelivery_type());
            if (orderList.get(i).getPick_code()!=null&&!orderList.get(i).getPick_code().equals("")){
                holder.tvCode.setVisibility(View.VISIBLE);
                holder.tvCode.setText("提货码:"+orderList.get(i).getPick_code());
            }else {
                holder.tvCode.setVisibility(View.GONE);
            }
            holder.tvShopSumCount.setText("共" + orderList.get(i).getItems().size() + "件商品\u3000合计：");
            holder.tvShopSumPrice.setText("¥" + orderList.get(i).getPrice());
            switch (orderList.get(i).getStatus()) {
                case 0:
                    holder.tvCancelOrder.setVisibility(View.VISIBLE);
                    holder.tvCancelOrder.setText("取消订单");
                    holder.tvSure.setVisibility(View.VISIBLE);
                    holder.tvSure.setText("付款");
                    break;
                case 1:
                    holder.tvCancelOrder.setVisibility(View.GONE);
                    holder.tvSure.setVisibility(View.VISIBLE);
                    holder.tvSure.setText("提醒发货");
                    break;
                case 2:
                    holder.tvCancelOrder.setVisibility(View.GONE);
                    holder.tvCancelOrder.setText("取消订单");
                    holder.tvSure.setVisibility(View.VISIBLE);
                    holder.tvSure.setText("确定收货");
                    break;
                case 3:
                    holder.tvCancelOrder.setVisibility(View.VISIBLE);
                    holder.tvCancelOrder.setText("删除订单");
                    holder.tvSure.setVisibility(View.GONE);
                    break;
                default:
                    holder.tvCancelOrder.setVisibility(View.VISIBLE);
                    holder.tvCancelOrder.setText("删除订单");
                    holder.tvSure.setVisibility(View.GONE);
                    break;
            }
            if (holder.tvSure.getVisibility() == View.GONE) {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) holder.tvCancelOrder.getLayoutParams();
                lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                holder.tvCancelOrder.setLayoutParams(lp);
            } else {
                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) holder.tvCancelOrder.getLayoutParams();
                lp.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                holder.tvCancelOrder.setLayoutParams(lp);
            }
            holder.tvSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    if (holder.tvSure.getText().toString().equals("确定收货")) {
                        initSurePop(orderList.get(i).getDelivery_type_status(), orderList.get(i).getOrder_id(), i);
                    } else if (holder.tvSure.getText().toString().equals("付款")) {
                        intent.setClass(context, ShopPayActivity.class);
                        intent.putExtra("order_id", orderList.get(i).getOrder_id());
                        if (orderList.get(i).getItems().size() > 0) {
                            intent.putExtra("shopName", orderList.get(i).getItems().get(0).getName());
                            intent.putExtra("shopImg", orderList.get(i).getItems().get(0).getImage());
                        }
                        BigDecimal bigDecimal = new BigDecimal(orderList.get(i).getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP);
                        intent.putExtra("currentMoney", bigDecimal.doubleValue());
                        context.startActivityForResult(intent, REQUEST_REFRESH);
                    } else {
                        Toast.makeText(context, "提醒成功！", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
            });
            holder.tvCancelOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (holder.tvCancelOrder.getText().toString().equals("取消订单")) {
                        initSurePop(5, orderList.get(i).getOrder_id(), i);
                    } else {
                        initSurePop(6, orderList.get(i).getOrder_id(), i);
                    }
                }
            });
            holder.lvShopList.setAdapter(new ShopListAdapter(orderList.get(i).getItems(), orderList.get(i).getStatus(), orderList.get(i).getOrder_id()));
            holder.lvShopList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(context, OrderDetailActivity.class);
                    intent.putExtra("orderStatus", orderList.get(i).getStatus());
                    intent.putExtra("id", orderList.get(i).getOrder_id());
                    context.startActivityForResult(intent, REQUEST_REFRESH);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initSurePop(final int type, final long order_id, final int i) {
        View popView = LayoutInflater.from(context).inflate(R.layout.login_pop
                , null);
        TextView yes = (TextView) popView.findViewById(R.id.yes);
        TextView tvMessageTitle = (TextView) popView.findViewById(R.id.tv_message_title);
        TextView not = (TextView) popView.findViewById(R.id.no);
        if (type == 1) {
            tvMessageTitle.setText("是否确认已自提？");
        } else if (type == 5) {
            tvMessageTitle.setText("是否取消订单？");
        } else if (type == 6) {
            tvMessageTitle.setText("确认删除订单？");
        } else {
            tvMessageTitle.setText("是否确认收货？");
        }
        surePopWindow = new PopupWindow(popView, Utils.dip2px(context, 256), ViewGroup.LayoutParams.WRAP_CONTENT);
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestOrderChange(type, order_id, i);
            }
        });
        not.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                surePopWindow.dismiss();
            }
        });
        surePopWindow.setFocusable(true);
        surePopWindow.setOutsideTouchable(true);
        Utils.darkenBackground(0.8f, context);
        if (!surePopWindow.isShowing()) {
            surePopWindow.showAtLocation(context.findViewById(android.R.id.content).getRootView(), Gravity.CENTER, 0, 0);
        }

        surePopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                Utils.darkenBackground(1f, context);
                context.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            }
        });
    }

    private void requestOrderChange(final int type, long order_id, final int i) {
        OrderApiInterface orderInterface = ApiHelper.getInstance().buildRetrofit(context).createService(OrderApiInterface.class);
        Map<String, Object> map = new HashMap<>();
        map.put("user_token", session.getmToken());
        map.put("order_id", order_id);
        if (type == 5) {
            map.put("deal_type", "cancel");
        } else if (type == 6) {
            map.put("deal_type", "delete");
        } else {
            map.put("deal_type", "confirm");
        }
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = orderInterface.orderHandle(body);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        surePopWindow.dismiss();
                        String string = response.body().string();
                        string = string.replaceAll("\r|\n", "");
                        if (!string.equals("")) {
                            JSONObject object = new JSONObject(string);
                            boolean status = object.optBoolean("status");
                            if (status) {
                                Toast.makeText(context, object.optString("message"), Toast.LENGTH_SHORT).show();
                                if (type == 6) {
                                    orderList.remove(i);
                                    notifyDataSetChanged();
                                }
                                getOrderStatus.getStatus(type);
                            } else {
                                int type = object.optInt("type");
                                if (type == 0) {
                                    Toast.makeText(context, object.optString("message"), Toast.LENGTH_SHORT).show();
                                } else if (type == 1) {
                                    Utils.loginPopWindow(context);
                                    Toast.makeText(context, object.optString("message"), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, object.optString("message"), Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            Toast.makeText(context, "网络不给力，请稍后再试！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        surePopWindow.dismiss();
                        Toast.makeText(context, "加载失败，请稍后再试！", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(context, "加载失败，请稍后再试！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                surePopWindow.dismiss();
                Toast.makeText(context, "加载失败，请稍后再试！", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private getOrderStatus getOrderStatus;

    public void setGetOrderStatus(AllOrderAdapter.getOrderStatus getOrderStatus) {
        this.getOrderStatus = getOrderStatus;
    }

    public interface getOrderStatus {
        void getStatus(int type);
    }


    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public void addData(List<AllOrderListBean.OrderListBean> orderList) {
        this.orderList = orderList;
        notifyDataSetChanged();
    }

    public void addOne(List<AllOrderListBean.OrderListBean> orderList, int position) {
        this.orderList = orderList;
        notifyItemChanged(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_type)
        TextView tvType;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.lv_shop_list)
        ListViewForScrollView lvShopList;
        @BindView(R.id.tv_order_status)
        TextView tvOrderStatus;
        @BindView(R.id.tv_shop_sum_count)
        TextView tvShopSumCount;
        @BindView(R.id.tv_shop_sum_price)
        TextView tvShopSumPrice;
        @BindView(R.id.tv_sure)
        TextView tvSure;
        @BindView(R.id.tv_code)
        TextView tvCode;
        @BindView(R.id.tv_cancel_order)
        TextView tvCancelOrder;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class ShopListAdapter extends BaseAdapter {

        private List<AllOrderListBean.OrderListBean.ItemsBean> items;
        private int status;
        private long order_id;

        public ShopListAdapter(List<AllOrderListBean.OrderListBean.ItemsBean> items, int status, long order_id) {
            this.items = items;
            this.status = status;
            this.order_id = order_id;
        }

        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            try {
                ShopListHolder holder = null;
                if (convertView == null) {
                    convertView = View.inflate(context, R.layout.shop_list, null);
                    holder = new ShopListHolder(convertView);
                    convertView.setTag(holder);
                } else {
                    holder = (ShopListHolder) convertView.getTag();
                }
                PictureHelper.setImageView(context, items.get(position).getImage(), holder.imgShop, R.drawable.img_loading);
                holder.tvShopPrice.setText("¥" + items.get(position).getPrice());
                holder.tvShopCount.setText("x" + items.get(position).getNumber());
                holder.tvShopName.setText(items.get(position).getName());
                holder.tvReturnMoneyName.setText(items.get(position).getAfter_sale_format());
                if (status == 3 && items.get(position).getComment() == 0) {
                    holder.tvShopEvaluate.setVisibility(View.VISIBLE);
                } else {
                    holder.tvShopEvaluate.setVisibility(View.GONE);
                }
                if (items.get(position).getUnit_name() != null && !items.get(position).getUnit_name().equals("")) {
                    holder.tvShopType.setVisibility(View.VISIBLE);
                    holder.tvShopType.setText(items.get(position).getUnit_name());
                } else {
                    holder.tvShopType.setVisibility(View.GONE);
                }
                if (status == 1 && items.get(position).getCan_after_sale() == 1 || status == 2 && items.get(position).getCan_after_sale() == 1 || status == 3 && items.get(position).getCan_after_sale() == 1) {
//                    RelativeLayout.LayoutParams rl = (RelativeLayout.LayoutParams) holder.tvReturnType.getLayoutParams();
//                    if (holder.tvShopEvaluate.getVisibility() == View.GONE) {
//                        rl.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//                        holder.tvReturnType.setLayoutParams(rl);
//                    } else {
//                        rl.removeRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//                        holder.tvReturnType.setLayoutParams(rl);
//                    }
                    holder.tvReturnType.setVisibility(View.VISIBLE);
                } else {
                    holder.tvReturnType.setVisibility(View.GONE);
                }
                if (holder.tvShopEvaluate.getVisibility() == View.GONE && holder.tvReturnType.getVisibility() == View.GONE && items.get(position).getAfter_sale_format().equals("")) {
                    holder.llOrderType.setVisibility(View.GONE);
                } else {
                    holder.llOrderType.setVisibility(View.VISIBLE);
                }
                holder.tvShopEvaluate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ShopFinishEvaluateActivity.class);
                        intent.putExtra("order_item_id", items.get(position).getOrder_item_id());
                        context.startActivityForResult(intent, REQUEST_REFRESH);
                    }
                });
                holder.tvReturnType.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (status == 1) {
                            Intent intent = new Intent(context, ReplacementDetailActivity.class);
                            intent.putExtra("name", "申请退款");
                            intent.putExtra("isNoPay", true);
                            intent.putExtra("order_id", order_id);
                            intent.putExtra("order_item_id", items.get(position).getOrder_item_id());
                            context.startActivityForResult(intent, REQUEST_REFRESH);
                        } else {
                            Intent intent = new Intent(context, RequestReplacementActivity.class);
                            intent.putExtra("order_id", order_id);
                            intent.putExtra("order_item_id", items.get(position).getOrder_item_id());
                            context.startActivityForResult(intent, REQUEST_REFRESH);
                        }

                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }

            return convertView;
        }
    }


    class ShopListHolder {
        @BindView(R.id.img_shop)
        RoundImageView imgShop;
        @BindView(R.id.tv_shop_price)
        TextView tvShopPrice;
        @BindView(R.id.tv_shop_count)
        TextView tvShopCount;
        @BindView(R.id.tv_shop_evaluate)
        TextView tvShopEvaluate;
        @BindView(R.id.tv_shop_name)
        TextView tvShopName;
        @BindView(R.id.tv_return_money_name)
        TextView tvReturnMoneyName;
        @BindView(R.id.tv_return_type)
        TextView tvReturnType;
        @BindView(R.id.tv_shop_type)
        TextView tvShopType;
        @BindView(R.id.ll_order_type)
        LinearLayout llOrderType;

        ShopListHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
