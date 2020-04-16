package com.zhjl.qihao.propertyRepresentations.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhjl.qihao.R;
import com.zhjl.qihao.abcommon.VolleyBaseFragment;
import com.zhjl.qihao.propertyRepresentations.adapter.EventNotificationAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 物业申述
 */
public class PropertyRepresentations extends VolleyBaseFragment {

    @BindView(R.id.xrv_property_representations_list)
    XRecyclerView xrvPropertyRepresentationsList;
    Unbinder unbinder;
    private View view;
    private EventNotificationAdapter eventNotificationAdapter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_property_representations, container, false);
        }
        unbinder = ButterKnife.bind(this, view);
        xrvPropertyRepresentationsList.setLayoutManager(new LinearLayoutManager(mContext));
        eventNotificationAdapter = new EventNotificationAdapter(mContext, null);
        xrvPropertyRepresentationsList.setAdapter(eventNotificationAdapter);
        xrvPropertyRepresentationsList.setLoadingMoreEnabled(true);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
