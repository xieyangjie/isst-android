/**
 *
 */
package cn.edu.zju.isst.exception;

import android.os.Message;

import java.net.HttpURLConnection;

import cn.edu.zju.isst.net.CSTResponse;
import cn.edu.zju.isst.util.Lgr;

import static cn.edu.zju.isst.constant.Constants.HTTPERROR_CLIENTERROR;
import static cn.edu.zju.isst.constant.Constants.HTTPERROR_SERVERERROR;
import static cn.edu.zju.isst.constant.Constants.HTTPERROR_UNKNOWN;

/**
 * @author theasir
 * @deprecated HTTP错误处理机
 */
public class HttpErrorWeeder {

    /**
     * 处理错误，目的是传递消息
     *
     * @param response 响应
     * @param msg      消息
     */
    public static void fckHttpError(CSTResponse response, Message msg) {
        Lgr.i("HttpErrorWeeder Response Code: " + response.getStatus());

        switch (response.getStatus()) {
            case HttpURLConnection.HTTP_BAD_REQUEST:
            case HttpURLConnection.HTTP_NOT_FOUND:
                msg.what = HTTPERROR_CLIENTERROR;
                break;
            case HttpURLConnection.HTTP_BAD_GATEWAY:
            case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:
            case HttpURLConnection.HTTP_INTERNAL_ERROR:
                msg.what = HTTPERROR_SERVERERROR;
                break;
            default:
                msg.what = HTTPERROR_UNKNOWN;
                break;
        }
    }
}
