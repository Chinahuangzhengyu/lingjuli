package com.zhjl.qihao.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * 作者： 黄郑宇
 * 时间： 2018/5/3
 * 类作用：
 */

public class LastInputEditText extends EditText {

    public LastInputEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public LastInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LastInputEditText(Context context) {
        super(context);
    }

    @Override
    protected void onSelectionChanged(int selStart, int selEnd) {
        super.onSelectionChanged(selStart, selEnd);
        //保证光标始终在最后面
        if(selStart==selEnd){//防止不能多选
            setSelection(getText().length());
        }

    }
}
