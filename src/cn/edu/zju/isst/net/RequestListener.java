package cn.edu.zju.isst.net;

/**
 * @author kbeta
 *         <p/>
 *         Updated by theasir
 * @deprecated 编写的Request请求的listener接口
 */
public interface RequestListener {

    public void onComplete(Object result);

    public void onHttpError(CSTResponse response);

    public void onException(Exception e);

}
