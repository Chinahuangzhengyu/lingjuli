package com.zhjl.qihao.localphotos;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.zhjl.qihao.R;
import com.zhjl.qihao.Session;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;
import com.zhjl.qihao.abutil.NewStatusBarUtils;
import com.zhjl.qihao.localphotos.PhotoFolderFragment.OnPageLodingClickListener;
import com.zhjl.qihao.localphotos.PhotoFragment.OnPhotoSelectClickListener;
import com.zhjl.qihao.localphotos.bean.PhotoInfo;
import com.zhjl.qihao.localphotos.bean.PhotoSerializable;
import com.zhjl.qihao.util.HeaderBar;
import com.zhjl.qihao.abutil.LogUtils;

/**
 * @author south
 * @title SelectPhotoActivity.java
 * @package com.centaline.mhc.activity.friendGroupActivity
 * @date 2014-8-6
 */
public class SelectPhotoActivity extends VolleyBaseActivity implements
        OnPageLodingClickListener, OnPhotoSelectClickListener {

    private PhotoFolderFragment photoFolderFragment;

    private TextView tv_right;

    private TextView tv_title;

    private List<PhotoInfo> hasList;

    private FragmentManager manager;
    private int backInt = 0;
    /**
     * 已选择图片数量
     */
    private int count;
    private int size = 0;
    private FragmentManager fManager;
    private PhotoFragment photoFragment;

    @Override

    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectphoto);

        manager = getSupportFragmentManager();
        HeaderBar headerBar = HeaderBar.createCommomBack(this, "请选择相册", "", "取消", this);
        setActivityHeaderStyle(headerBar);
        tv_right = headerBar.getTextViewRight();
        tv_title = headerBar.getTextViewTitle();
        //获取“内存重启”时保存的索引下标
        if (savedInstanceState != null) {
            photoFolderFragment = (PhotoFolderFragment) manager.getFragment(savedInstanceState, "photoFolder");
            photoFragment = (PhotoFragment) manager.getFragment(savedInstanceState, "photoFg");
            if (photoFolderFragment == null) {
                photoFolderFragment = new PhotoFolderFragment();
            }
            if (photoFragment == null){
                photoFragment = new PhotoFragment();
            }
            tv_title.setText("已选择0张");
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.hide(photoFolderFragment);
            transaction.show(photoFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            //恢复fragment页面
        } else {
            manager = getSupportFragmentManager();
            photoFolderFragment = new PhotoFolderFragment();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(R.id.body, photoFolderFragment);
            transaction.addToBackStack(null);
            // Commit the transaction
            transaction.commit();
        }
        getWindowManager().getDefaultDisplay().getMetrics(
                Session.get(this).getDisplayMetrics());
//		NewStatusBarUtils.setStatusBarColor(this, R.color.app_color);
        count = getIntent().getIntExtra("size", 0);
        hasList = new ArrayList<PhotoInfo>();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (photoFolderFragment != null && photoFolderFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "photoFolder", photoFolderFragment);
        }
        if (photoFragment != null && photoFragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "photoFg", photoFragment);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPageLodingClickListener(List<PhotoInfo> list) {
        // TODO Auto-generated method stub
        tv_title.setText("已选择0张");
        FragmentTransaction transaction = manager.beginTransaction();
        if (photoFragment == null) {
            photoFragment = new PhotoFragment();
        }
        Bundle args = new Bundle();
        PhotoSerializable photoSerializable = new PhotoSerializable();
        for (PhotoInfo photoInfoBean : list) {
            photoInfoBean.setChoose(false);
        }
        photoSerializable.setList(list);
        args.putInt("count", count);
        args.putSerializable("list", photoSerializable);
        photoFragment.setArguments(args);
        transaction = manager.beginTransaction();
        transaction.hide(photoFolderFragment).commit();
        transaction = manager.beginTransaction();
        transaction.add(R.id.body, photoFragment);
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

        transaction.addToBackStack(null);
        // Commit the transaction
        transaction.commit();
        backInt++;
    }

    @Override
    public void onPhotoSelectClickListener(List<PhotoInfo> list) {
        // TODO Auto-generated method stub
        hasList.clear();
        for (PhotoInfo photoInfoBean : list) {
            if (photoInfoBean.isChoose()) {
                hasList.add(photoInfoBean);
            }
        }
        if (hasList.size() > 0) {
            tv_right.setText("完成");
        } else {
            tv_right.setText("取消");
        }
        tv_title.setText("已选择" + hasList.size() + "张");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK && backInt == 0) {
            finish();
        } else if (keyCode == KeyEvent.KEYCODE_BACK && backInt == 1) {
            backInt--;
            hasList.clear();
            tv_title.setText("请选择相册");
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.show(photoFolderFragment).commit();
            manager.popBackStack(0, 0);
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.ll_head_left:
                if (backInt == 0) {
                    finish();
                } else if (backInt == 1) {
                    backInt--;
                    hasList.clear();
                    tv_title.setText("请选择相册");
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.show(photoFolderFragment).commit();
                    manager.popBackStack(0, 0);
                }
                break;

            case R.id.tv_right:
                Intent data = new Intent();
                LogUtils.e("tv_right hasList.size : " + hasList.size());
                if (hasList.size() > 0) {
                    List<String> list = new ArrayList<String>();
                    for (PhotoInfo info : hasList) {
                        list.add(info.getPath_absolute());
                    }
                    data.putExtra("result", (Serializable) list);
                    data.putExtra("size", count);
                    setResult(0x2, data);
                    finish();
                } else {
                    setResult(0x2, data);
                    finish();
                }
                break;

            default:
                break;
        }

    }
}
