package com.zhjl.qihao.homemanage.fragment;

import android.content.Context;
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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.propertyservicepay.bean.UserRoomListBean;
import com.zhjl.qihao.util.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class SelectRoomFragment extends DialogFragment {

    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.lv_home_list)
    ListView lvHomeList;
    Unbinder unbinder;
    private View view;
    private VolleyBaseActivity mContext;
    private ArrayList<UserRoomListBean.DataBean> data;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = (VolleyBaseActivity) context;
    }

    public static SelectRoomFragment getInstance(ArrayList<UserRoomListBean.DataBean> data) {
        SelectRoomFragment fragment = new SelectRoomFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList("data", data);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_select_room, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        data = getArguments().getParcelableArrayList("data");
        if (data != null) {
            lvHomeList.setAdapter(new SelectRoomListAdapter());
        } else {
            Toast.makeText(mContext, "暂无房间列表！请绑定房间", Toast.LENGTH_SHORT).show();
        }
        lvHomeList.setOnItemClickListener((parent, view, position, id) -> {
            String roomId = data.get(position).getRoomId();
            setOnItemClickLintener.onItemClick(roomId);
            dismiss();
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        Window window = getDialog().getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; //底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = Utils.dip2px(mContext,285);
        window.setAttributes(lp);
    }

    class SelectRoomListAdapter extends BaseAdapter {

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
                convertView = View.inflate(mContext, R.layout.room_list, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tvRoomName.setText(data.get(position).getSmallCommunityName() + data.get(position).getRoomName());
            return convertView;
        }
    }

    static
    class ViewHolder {
        @BindView(R.id.tv_room_name)
        TextView tvRoomName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                dismiss();
                break;
        }
    }

    public SetOnItemClickLintener setOnItemClickLintener;

    public void setSetOnItemClickLintener(SetOnItemClickLintener setOnItemClickLintener) {
        this.setOnItemClickLintener = setOnItemClickLintener;
    }

    public interface SetOnItemClickLintener {
        void onItemClick(String roomId);
    }
}
