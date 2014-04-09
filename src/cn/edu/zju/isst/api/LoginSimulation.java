/**
 * 
 */
package cn.edu.zju.isst.api;

import org.json.JSONObject;

import android.content.Context;
import cn.edu.zju.isst.db.DataManager;
import cn.edu.zju.isst.db.User;
import cn.edu.zju.isst.net.CSTResponse;
import cn.edu.zju.isst.net.RequestListener;
import cn.edu.zju.isst.util.L;

/**
 * 模拟（更新）登录类
 * 
 * @author theasir
 * 
 *         TODO WIP
 */
public class LoginSimulation {

	/**
	 * 自定义状态，在空闲状态下的status，区别于服务器返回的status code
	 */
	private static final int STATUS_IDLE = -1;

	private static int status = STATUS_IDLE;

	/**
	 * 模拟（更新）登录后获取服务器返回状态码status code
	 * 
	 * @param context
	 *            用于加载DBHelper获取当前数据库
	 * @return 状态码
	 */
	public synchronized static int getStatusAfterExecute(Context context) {
		execute(context);
		final int statusForResult = status;
		reset();
		return statusForResult;
	}

	/**
	 * 模拟（更新）登录后获取服务器返回状态码status code
	 * 
	 * @param currentUser
	 *            当前用户
	 * @return 状态码
	 */
	public synchronized static int getStatusAfterExecute(User currentUser) {
		execute(currentUser);
		final int statusForResult = status;
		reset();
		return statusForResult;
	}

	public synchronized static int getStatus() {
		return status;
	}

	private static void reset() {
		status = STATUS_IDLE;
	}

	/**
	 * 执行模拟（更新）登录，默认加载缓存数据库内用户信息
	 * 
	 * @param context
	 *            用于加载DBHelper获取当前数据库
	 */
	private static void execute(Context context) {
		final User currentUser = DataManager.getCurrentUser(context);
		execute(currentUser);
	}

	/**
	 * 执行模拟（更新）登录
	 * 
	 * @param currentUser
	 *            当前用户
	 */
	private static void execute(User currentUser) {
		LoginApi.update(currentUser, null, null, new RequestListener() {

			@Override
			public void onComplete(Object result) {
				L.i(this.getClass().getName() + " onComplete!");
				try {
					JSONObject jsonObject = (JSONObject) result;
					status = jsonObject.getInt("status");
				} catch (Exception e) {
					L.i(this.getClass().getName() + " onComplete Exception!");
					onException(e);
				}

			}

			@Override
			public void onHttpError(CSTResponse response) {
				L.i(this.getClass().getName() + " onHttpError!");

			}

			@Override
			public void onException(Exception e) {
				L.i(this.getClass().getName() + " onException!");
				e.printStackTrace();
			}

		});
	}

}
