package com.zhjl.qihao.localphotos;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import com.zhjl.qihao.R;
import com.zhjl.qihao.base.PhotoNumsBean;
import com.zhjl.qihao.localphotos.adapter.PhotoAdapter;
import com.zhjl.qihao.localphotos.bean.PhotoInfo;
import com.zhjl.qihao.localphotos.bean.PhotoSerializable;

/**
 *
 */
public class PhotoFragment extends Fragment {

    private PhotoSerializable photoSerializable;
    private View view;

    public interface OnPhotoSelectClickListener {
        public void onPhotoSelectClickListener(List<PhotoInfo> list);

    }

    private OnPhotoSelectClickListener onPhotoSelectClickListener;

    private GridView gridView;
    private PhotoAdapter photoAdapter;

    private List<PhotoInfo> list;

    private int hasSelect = 1;

    private int count;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        if (onPhotoSelectClickListener == null) {
            onPhotoSelectClickListener = (OnPhotoSelectClickListener) activity;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view==null){
            view = inflater.inflate(R.layout.fragment_photoselect, container, false);
        }
        gridView = (GridView) view.findViewById(R.id.gridview);
        Bundle args = getArguments();
        if (savedInstanceState!=null){
            photoSerializable = (PhotoSerializable) savedInstanceState.getSerializable("saveData");
        }else {
            photoSerializable = (PhotoSerializable) args
                    .getSerializable("list");
        }
        list = new ArrayList<PhotoInfo>();
        list.addAll(photoSerializable.getList());
        photoAdapter = new PhotoAdapter(getActivity(), list, gridView);
        gridView.setAdapter(photoAdapter);
        count = args.getInt("count");
        gridView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (list.get(position).isChoose() && hasSelect > 1) {
                    list.get(position).setChoose(false);
                    hasSelect--;
                } else if (hasSelect + count < PhotoNumsBean.getInstant().getNumber() + 1) {
                    list.get(position).setChoose(true);
                    hasSelect++;
                } else {
                    Toast.makeText(
                            getActivity(),
                            "最多可上传" + PhotoNumsBean.getInstant().getNumber() + "张图片", Toast.LENGTH_SHORT)
                            .show();
                }
                photoAdapter.refreshView(position);
                onPhotoSelectClickListener.onPhotoSelectClickListener(list);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (list != null && list.size() > 0) {
            outState.putSerializable("saveData",photoSerializable);
        }
        super.onSaveInstanceState(outState);
    }
}
