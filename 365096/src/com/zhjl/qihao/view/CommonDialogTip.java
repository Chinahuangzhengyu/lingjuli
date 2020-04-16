package com.zhjl.qihao.view;

import com.zhjl.qihao.R;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class CommonDialogTip extends Dialog{
	TextView tv_yes;
	TextView tv_no;
	private Context mContext;
	public CommonDialogTip(Context context, String title,Object ob ) {
		super(context);
		initView(context,title, ob);
	}

	public CommonDialogTip(Context context, String title,String text_yes,String text_no,Object ob ) {
		super(context);

		initView(context,title, ob);
		tv_yes.setText(text_yes);
		tv_no.setText(text_no);
		
	}
	
	private void initView(Context context,String title,Object ob ){
		mContext = context;
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_common_tip);
		tv_yes = (TextView) this.findViewById(R.id.yes);
		tv_no = (TextView) this.findViewById(R.id.no);
		TextView content = (TextView) this.findViewById(R.id.content);
		content.setText(title);
		setOnClickListener1(ob);
	}
	 
	private void setOnClickListener1(final Object ob ){
		if(ob != null){
			if(ob instanceof android.view.View.OnClickListener){
				tv_yes.setOnClickListener((android.view.View.OnClickListener) ob);
			}else if(ob instanceof Intent){
				tv_yes.setOnClickListener(new android.view.View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						mContext.startActivity((Intent) ob);
					}
				});
				
			}
			
		}else{
			tv_yes.setVisibility(View.GONE);
		}
		tv_no.setOnClickListener(new android.view.View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dismiss();
			}

		});
	}

}
