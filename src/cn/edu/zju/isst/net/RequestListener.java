package cn.edu.zju.isst.net;
/**
 * 编写的Request请求的listener接口
 * @author kbeta
 *
 */
public interface RequestListener {

	public void onComplete(Object result);

	public void onError(Exception e);

}
