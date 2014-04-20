/**
 * 
 */
package cn.edu.zju.isst.exception;

import static cn.edu.zju.isst.constant.Constants.EXCEPTION_CLASSCAST;
import static cn.edu.zju.isst.constant.Constants.EXCEPTION_IO;
import static cn.edu.zju.isst.constant.Constants.EXCEPTION_JSON;
import static cn.edu.zju.isst.constant.Constants.EXCEPTION_SOCKETTIMEOUT;
import static cn.edu.zju.isst.constant.Constants.EXCEPTION_UNKNOWN;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.json.JSONException;

import android.os.Message;
import cn.edu.zju.isst.util.L;

/**
 * 异常清除机
 * 
 * @author theasir
 * 
 */
public class ExceptionWeeder {

	/**
	 * 处理异常，目的是传递消息
	 * 
	 * @param e
	 *            异常
	 * @param msg
	 *            消息
	 */
	public static void fckException(Exception e, Message msg) {
		L.i("ExceptionWeeder FUCK U Exception type of " + e.getClass());

		if (L.isDebuggable()) {
			e.printStackTrace();
		}

		if (e instanceof SocketTimeoutException) {
			msg.what = EXCEPTION_SOCKETTIMEOUT;
		} else if (e instanceof IOException) {
			msg.what = EXCEPTION_IO;
		} else if (e instanceof JSONException) {
			msg.what = EXCEPTION_JSON;
		} else if (e instanceof ClassCastException) {
			msg.what = EXCEPTION_CLASSCAST;
		} else {
			msg.what = EXCEPTION_UNKNOWN;
		}
	}
}
