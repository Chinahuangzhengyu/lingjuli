package com.zhjl.qihao.activity.userlogin;

import android.content.Intent;
import android.os.Bundle;

import com.zhjl.qihao.R;
import com.zhjl.qihao.ZHJLApplication;
import com.zhjl.qihao.ablogin.activity.UserLoginActivity;
import com.zhjl.qihao.abrefactor.RefactorMainActivity;
import com.zhjl.qihao.abcommon.VolleyBaseActivity;

/**   
 * @ClassName:  UserLoadParent   
 * @Description:进入登陆界面   
 * @author: 黄南榆
 *      
 */ 
public class UserLoadParent extends VolleyBaseActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.userload_parent);
//		StatusBarutil.StatusBarLightMode(this, StatusBarutil.StatusBarLightMode(this));
//		NewStatusBarUtils.setStatusBarColor(this,R.color.app_color);
		try {
			
			if (!mSession.getUserType().equals(null)
					&& !mSession.getRegisterMobile().equals(null)
					&& !mSession.getRegisterMobile2().equals(null)
					&& !mSession.getUserId().equals(null)) {
				Intent intent2 = new Intent(UserLoadParent.this,RefactorMainActivity.class);
				intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//Intent.FLAG_ACTIVITY_CLEAR_TOP清空栈顶
				enterActivityWithFinish(intent2);
			}
		} catch (Exception ex) {
			Intent intent = new Intent(UserLoadParent.this,UserLoginActivity.class);
			ZHJLApplication.getInstance().finishAll();
			enterActivityWithFinish(intent);
		}
	}

}
