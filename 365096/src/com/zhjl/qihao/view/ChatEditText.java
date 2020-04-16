package com.zhjl.qihao.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;

public class ChatEditText extends android.support.v7.widget.AppCompatEditText {
    public ChatEditText(Context context) {
        super(context);
    }

    public ChatEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ChatEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        InputConnection connection = super.onCreateInputConnection(outAttrs);
        int imeActions = outAttrs.imeOptions & EditorInfo.IME_MASK_ACTION;
        if ((imeActions & EditorInfo.IME_ACTION_SEND) != 0) {
            outAttrs.imeOptions ^= imeActions;
            outAttrs.imeOptions |= EditorInfo.IME_ACTION_SEND;
        }
        if ((outAttrs.imeOptions & EditorInfo.IME_FLAG_NO_ENTER_ACTION) != 0) {
            outAttrs.imeOptions &= ~EditorInfo.IME_FLAG_NO_ENTER_ACTION;
        }
        return connection;
    }
}
