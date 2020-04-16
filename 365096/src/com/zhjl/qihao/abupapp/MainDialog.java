package com.zhjl.qihao.abupapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abutil.PictureHelper;
import com.zhjl.qihao.mymessage.activity.MessageDetailActivity;
import com.zhjl.qihao.util.Utils;

import java.io.File;

public class MainDialog {

    public void show(VolleyBaseActivity context, String pic, String url) {
        if (isContextValid(context)) {

            View view = View.inflate(context, R.layout.main_message_view, null);
            ImageView imgMessage = view.findViewById(R.id.img_message);
            ImageView imgCancel = view.findViewById(R.id.img_cancel);
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            AlertDialog dialog = builder.create();
            dialog.setView(view);
            //设置背景透明
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            dialog.show();
            //点击对话框外面,对话框不消失
            dialog.setCanceledOnTouchOutside(false);
            dialog.getWindow().setLayout(Utils.dip2px(context, 300), ViewGroup.LayoutParams.WRAP_CONTENT);
            //加载图片
            PictureHelper.setImageView(context, pic, imgMessage, R.drawable.img_loading);
            imgMessage.setOnClickListener(v -> {
                dialog.dismiss();
                Intent intent = new Intent(context, MessageDetailActivity.class);
                intent.putExtra("url",url);
                context.startActivity(intent);
            });
            imgCancel.setOnClickListener(v -> {
                dialog.dismiss();
            });
        }
    }

    private static boolean isContextValid(Context context) {
        return context instanceof Activity && !((Activity) context).isFinishing();
    }
}
