package com.zhjl.qihao.opendoor.adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.michoi.unlock.UnlockManager;
import com.michoi.unlock.mode.RightsRecord;
import com.zhjl.qihao.R;
import com.zhjl.qihao.Session;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OpenDoorListAdapter extends RecyclerView.Adapter<OpenDoorListAdapter.ViewHolder> {

    private Activity mContext;
    private ArrayList<RightsRecord> data;
    private final Session session;
    private ProgressDialog dialog;

    public OpenDoorListAdapter(Activity mContext, ArrayList<RightsRecord> data) {
        this.mContext = mContext;
        this.data = data;
        session = Session.get(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.open_door_list, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int i) {
        holder.tvTitle.setText(data.get(i).DoorName);
        holder.tvOpenDoor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tvOpenDoor.setEnabled(false);
                dialog = new ProgressDialog(mContext);
                dialog.setMessage("开锁中。。。");
                dialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final int unlock = UnlockManager.getInstance().unlock(data.get(i).unlockId, session.getRegisterMobile(), data.get(i).DoorID);
                        mContext.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (unlock == 2) {
                                    dialog.dismiss();
                                    holder.tvOpenDoor.setEnabled(true);
                                    Toast.makeText(mContext, "开锁成功！", Toast.LENGTH_SHORT).show();
                                } else {
                                    dialog.dismiss();
                                    holder.tvOpenDoor.setEnabled(true);
                                    Toast.makeText(mContext, "开锁失败！", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).start();

            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void addData(ArrayList<RightsRecord> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_open_door)
        TextView tvOpenDoor;
        @BindView(R.id.img_title)
        ImageView imgTitle;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_content)
        TextView tvContent;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
