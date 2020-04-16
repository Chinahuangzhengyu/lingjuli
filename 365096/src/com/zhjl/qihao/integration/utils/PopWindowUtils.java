package com.zhjl.qihao.integration.utils;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.util.Utils;

public class PopWindowUtils {
    private static PopupWindow popupWindow = new PopupWindow();
    private int width = 256;

    private static PopWindowUtils instance = new PopWindowUtils();
    private TextView tvMessageTitle;
    private TextView not;
    private TextView yes;

    public static PopWindowUtils getInstance() {
        return instance;
    }

    public static PopupWindow getPopupWindow() {
        return popupWindow;
    }

    public void setWidth(int width) {
        this.width = width;
    }


    //必须先show再调用
    public TextView getYesView() {
        View view = popupWindow.getContentView();
        if (view != null) {
            yes = view.findViewById(R.id.yes);
        }
        return yes;
    }
    public TextView getNoView() {
        View view = popupWindow.getContentView();
        if (view != null) {
            not = view.findViewById(R.id.no);
        }
        return not;
    }



    public void show(String content, VolleyBaseActivity mContext) {
        View popView = LayoutInflater.from(mContext).inflate(R.layout.login_pop
                , null);
        popupWindow.setWidth(Utils.dip2px(mContext, width));
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setContentView(popView);
        yes = popView.findViewById(R.id.yes);
        not = popView.findViewById(R.id.no);
        tvMessageTitle = popView.findViewById(R.id.tv_message_title);
        tvMessageTitle.setText(content);
        not.setOnClickListener(v -> {
            popupWindow.dismiss();
            if (setNoOnClickListener!=null){
                setNoOnClickListener.onClick();
            }
        });
        yes.setOnClickListener(v -> {
            popupWindow.dismiss();
            if (setYesOnClickListener!=null){
                setYesOnClickListener.onClick();
            }
        });
//        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(false);
        Utils.darkenBackground(0.8f, mContext);
        if (!popupWindow.isShowing()) {
            popupWindow.showAtLocation(mContext.findViewById(android.R.id.content).getRootView(), Gravity.CENTER, 0, 0);
        }

        popupWindow.setOnDismissListener(() -> {
            Utils.darkenBackground(1f, mContext);
            mContext.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        });


    }


    public void dismiss() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
        }
    }

    public interface setYesOnClickListener {
        void onClick();
    }

    private setYesOnClickListener setYesOnClickListener;

    public void setSetYesOnClickListener(PopWindowUtils.setYesOnClickListener setYesOnClickListener) {
        this.setYesOnClickListener = setYesOnClickListener;
    }


    public interface SetNoOnClickListener {
        void onClick();
    }

    private SetNoOnClickListener setNoOnClickListener;

    public void setNoOnClickListener(PopWindowUtils.SetNoOnClickListener setNoOnClickListener) {
        this.setNoOnClickListener = setNoOnClickListener;
    }
}
