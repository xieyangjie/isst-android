/**
 * 
 */
package cn.edu.zju.isst.net;

import java.io.FileNotFoundException;

import cn.edu.zju.isst.util.L;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;

/**
 * @author stevwayne
 * 
 */
public class UpdateManager {
//	public static final String UPDATE_APKFILE_URL = "http://www.zjucst.com/downloads/isst-1.0.0.apk";
	public static final String UPDATE_APKFILE_URL = "http://www.cst.zju.edu.cn/isst-releases/isst.apk";

	private static UpdateManager INSTANCE;
	private Context context;
	private DownloadManager dm;

	private UpdateManager(Context context) {
		this.context = context;
	}

	public static UpdateManager createInstance(Context context) {
		INSTANCE = new UpdateManager(context);
		return INSTANCE;
	}

	public static UpdateManager getInstance() {
		if (INSTANCE == null) {
			throw new IllegalStateException(
					"UpdateManager::createInstance() needs to be called "
							+ "before UpdateManager::getInstance()");
		}

		return INSTANCE;
	}

	public void downloadUpdate() {
		dm = (DownloadManager) context
				.getSystemService(Context.DOWNLOAD_SERVICE);

		Uri uri = Uri.parse(UPDATE_APKFILE_URL);
		DownloadManager.Request request = new DownloadManager.Request(uri);
		request.setDestinationInExternalPublicDir("Downloads", "ISST2.apk");
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
		
		final long ref = dm.enqueue(request);
		L.i("UpdateApkId", "" + ref);

		IntentFilter filter = new IntentFilter(
				DownloadManager.ACTION_DOWNLOAD_COMPLETE);

		BroadcastReceiver receiver = new BroadcastReceiver() {

			@Override
			public void onReceive(Context context, Intent intent) {
				long recceiveRef = intent.getLongExtra(
						DownloadManager.EXTRA_DOWNLOAD_ID, -1);
				L.i("UpdateApkId", "Downloaded Apk Id: " + recceiveRef);
				if (ref == recceiveRef) {
					try {
						L.i("UpdateApkId", "Before Open File");
						dm.openDownloadedFile(recceiveRef);
						L.i("UpdateApkId", "After Open File");
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		};

		context.registerReceiver(receiver, filter);
	}
}
