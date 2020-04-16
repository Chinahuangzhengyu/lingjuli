package com.zhjl.qihao.nearbyinteraction.fragment;

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
import android.widget.TextView;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.complaint.api.ComplaintApiInterfaces;
import com.zhjl.qihao.nearbyinteraction.api.NearByInterfaces;
import com.zhjl.qihao.zq.ApiHelper;
import com.zhjl.qihao.zq.ParamForNet;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

public class ReportFragment extends DialogFragment {

    @BindView(R.id.tv_note_complaint)
    TextView tvNoteComplaint;
    @BindView(R.id.tv_note_cancel)
    TextView tvNoteCancel;
    Unbinder unbinder;
    @BindView(R.id.tv_delete_note)
    TextView tvDeleteNote;
    private VolleyBaseActivity mContext;
    private View view;
    private String forumNoteId = "";
    private String discussId;
    private boolean isReply;
    private boolean isMySelf;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = (VolleyBaseActivity) context;
    }

    public static ReportFragment getInstance(String forumNoteId, boolean isReply, boolean isMySelf, String discussId) {
        ReportFragment replyPopFragment = new ReportFragment();
        Bundle bundle = new Bundle();
        bundle.putString("forumNoteId", forumNoteId);
        bundle.putString("discussId", discussId);
        bundle.putBoolean("isReply", isReply);
        bundle.putBoolean("isMySelf", isMySelf);
        replyPopFragment.setArguments(bundle);
        return replyPopFragment;
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
        getDialog().getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);

        Window window = getDialog().getWindow();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(lp);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_reply_pop, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        forumNoteId = getArguments().getString("forumNoteId");
        discussId = getArguments().getString("discussId");
        isReply = getArguments().getBoolean("isReply");
        isMySelf = getArguments().getBoolean("isMySelf");
        if (isMySelf) {
            tvDeleteNote.setVisibility(View.VISIBLE);
            tvNoteComplaint.setVisibility(View.GONE);
        } else {
            tvDeleteNote.setVisibility(View.GONE);
            tvNoteComplaint.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_note_complaint, R.id.tv_note_cancel, R.id.tv_delete_note})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_note_complaint:
                tvNoteComplaint.setEnabled(false);
                if (isReply) {
                    if (discussId != null && !discussId.equals("")) {
                        requestReportNote();
                    }
                } else {
                    requestReport();
                }
                break;
            case R.id.tv_note_cancel:
                dismiss();
                break;
            case R.id.tv_delete_note:
                tvDeleteNote.setEnabled(false);
                if (isReply){
                    if (discussId != null && !discussId.equals("")) {
                        deleteReply();
                    } else {
                        tvDeleteNote.setEnabled(true);
                        Toast.makeText(mContext, "帖子删除错误！", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    deleteNote();
                }
                break;
        }
    }

    private void deleteNote(){
        NearByInterfaces nearByInterfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(NearByInterfaces.class);
        Map<String, Object> map = new HashMap<>();
        map.put("forumNoteId", forumNoteId);
        Call<ResponseBody> call = nearByInterfaces.noteDelete(ParamForNet.put(map));
        mContext.activityRequestData(call, null, new VolleyBaseActivity.RequestResult<Object>() {
            @Override
            public void success(Object result, String message) throws Exception {
                Toast.makeText(mContext, "删除成功！", Toast.LENGTH_SHORT).show();
                setRefershData.refresh();
                dismiss();
                tvDeleteNote.setEnabled(true);
            }

            @Override
            public void fail() {

            }
        });
    }

    private void requestReportNote() {
        NearByInterfaces interfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(NearByInterfaces.class);
        Map<String, Object> map = new HashMap<>();
        map.put("discussId", discussId);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = interfaces.noteReplyReport(body);
        mContext.activityRequestData(call, null, new VolleyBaseActivity.RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
                Toast.makeText(mContext, "举报成功！", Toast.LENGTH_SHORT).show();
                dismiss();
                tvNoteComplaint.setEnabled(true);
            }

            @Override
            public void fail() {
                tvNoteComplaint.setEnabled(true);
            }
        });
    }

    private void deleteReply() {
        NearByInterfaces interfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(NearByInterfaces.class);
        Map<String, Object> map = new HashMap<>();
        map.put("discussId", discussId);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = interfaces.noteReplyDelete(body);
        mContext.activityRequestData(call, null, new VolleyBaseActivity.RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
                Toast.makeText(mContext, "删除成功！", Toast.LENGTH_SHORT).show();
                setRefershData.refresh();
                tvDeleteNote.setEnabled(true);
                dismiss();
            }

            @Override
            public void fail() {
                tvDeleteNote.setEnabled(true);
            }
        });
    }

    private void requestReport() {
        ComplaintApiInterfaces interfaces = ApiHelper.getInstance().buildRetrofit(mContext).createService(ComplaintApiInterfaces.class);
        Map<String, Object> map = new HashMap<>();
        map.put("forumNoteId", forumNoteId);
        RequestBody body = ParamForNet.put(map);
        Call<ResponseBody> call = interfaces.reportContent(body);
        mContext.activityRequestData(call, null, new VolleyBaseActivity.RequestResult<Object>() {
            @Override
            public void success(Object result, String message) {
                Toast.makeText(mContext, "举报成功！", Toast.LENGTH_SHORT).show();
                dismiss();
                tvNoteComplaint.setEnabled(true);
            }

            @Override
            public void fail() {
                tvNoteComplaint.setEnabled(true);
            }
        });
    }
    public interface SetRefershData{
        void refresh();
    }
    private SetRefershData setRefershData;

    public void setSetRefershData(SetRefershData setRefershData) {
        this.setRefershData = setRefershData;
    }
}
