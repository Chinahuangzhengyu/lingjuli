package com.zhjl.qihao.abrefactor.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abrefactor.bean.NearByTypeBean;
import com.zhjl.qihao.nearbyinteraction.activity.NearBySendNoteActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SendNoteTypeFragment extends DialogFragment {


    @BindView(R.id.gv_send_note_type)
    GridView gvSendNoteType;
    @BindView(R.id.img_send_note)
    ImageView imgSendNote;
    @BindView(R.id.tv_send_note)
    TextView tvSendNote;
    @BindView(R.id.rl_send_note)
    RelativeLayout rlSendNote;
    @BindView(R.id.img_cancel)
    ImageView imgCancel;
    Unbinder unbinder;
    private View view;
    private Context context;
    private ArrayList<NearByTypeBean.DataBean> data = new ArrayList<>();
    private int position;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }


    public static SendNoteTypeFragment getInstance(ArrayList<NearByTypeBean.DataBean> data) {
        SendNoteTypeFragment fragment = new SendNoteTypeFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("data", data);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_send_note_type, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        data = getArguments().getParcelableArrayList("data");
        rlSendNote.setEnabled(false);
        if (data != null && data.size() > 0) {
            gvSendNoteType.setAdapter(new SendNoteTypeAdapter());
        }
        gvSendNoteType.setOnItemClickListener((parent, view, position, id) -> {
            for (int i = 0; i < gvSendNoteType.getChildCount(); i++) {
                if (i == position) {
                    RelativeLayout rl = (RelativeLayout) gvSendNoteType.getChildAt(i);
                    TextView tvContent = (TextView) rl.getChildAt(0);
                    tvContent.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_green_6));
                    tvContent.setTextColor(ContextCompat.getColor(context, R.color.white));
                } else {
                    RelativeLayout rl = (RelativeLayout) gvSendNoteType.getChildAt(i);
                    TextView tvContent = (TextView) rl.getChildAt(0);
                    tvContent.setBackground(ContextCompat.getDrawable(context, R.drawable.circle_f2f2f2_6));
                    tvContent.setTextColor(ContextCompat.getColor(context, R.color.text_color_f1));
                }
            }
            this.position = position;
            settingSend();
        });
        return view;
    }

    /**
     * 设置可点击
     */
    private void settingSend() {
        rlSendNote.setEnabled(true);
        rlSendNote.setAlpha(1f);
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

    class SendNoteTypeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = View.inflate(context, R.layout.send_note_type, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvSendType.setText(data.get(position).getLabelName());
            return convertView;
        }

        class ViewHolder {
            @BindView(R.id.tv_send_type)
            TextView tvSendType;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }


    @OnClick({R.id.rl_send_note, R.id.img_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_send_note:
                Intent intent = new Intent(context, NearBySendNoteActivity.class);
                intent.putExtra("labelTypeId",data.get(position).getId());
                startActivity(intent);
                break;
            case R.id.img_cancel:
                dismiss();
                break;
        }
    }
}
