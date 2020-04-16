package com.zhjl.qihao.base;

import com.zhjl.qihao.abcommon.VolleyBaseFragment;

/**
 * Created by Administrator on 2017-06-28.
 */

public abstract  class CircleVolleyBaseFragment extends VolleyBaseFragment {

    public abstract  void initZan(String forumNoteId, String noteSource, int Position,int type);

    public abstract void showDeletDialog(final String id, int type);

    public abstract void showReportDialog(final String id, int type);

    public abstract void showCopyDialog(final String text);

    public abstract void showReplyPopWindow(final String forumNoteId,
                                   final String replyUserType, final String beReplyer,
                                   final String noteSource, final int position, final int action,final String replyUser);

}
