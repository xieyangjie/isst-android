/**
 * 
 */
package cn.edu.zju.isst.util;

import cn.edu.zju.isst.api.LoginSimulation;
import android.app.Activity;
import android.os.Message;

import static cn.edu.zju.isst.constant.Constants.*;

/**
 * @author theasir
 *
 */
public class MessageDisposition {

	public static final int INFO = 0;
	public static final int NOTLOGIN = 1;
	
	public static int dispose(Message msg, Activity activity){
		switch (msg.what) {
		case STATUS_LOGIN_USERNAME_NOT_EXIST:
		case STATUS_LOGIN_PASSWORD_ERROR:
			CM.showAlert(activity, (String) msg.obj);
			break;
		case STATUS_LOGIN_AUTH_EXPIRED:
		case STATUS_LOGIN_AUTH_FAILED:
			
			break;
		case STATUS_NOT_LOGIN:
			LoginSimulation.getStatusAfterExecute(activity);
		default:
			break;
		}
		return 0;
	}
}
