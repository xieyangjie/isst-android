/**
 * 
 */
package cn.edu.zju.isst.ui.usercenter;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import cn.edu.zju.isst.R;
import cn.edu.zju.isst.db.DataManager;
import cn.edu.zju.isst.db.User;
import cn.edu.zju.isst.dummy.TestFloatingActivity;
import cn.edu.zju.isst.ui.main.MainActivity;
import cn.edu.zju.isst.ui.main.NewMainActivity;
import cn.edu.zju.isst.util.J;

/**
 * @author theasir
 * 
 */
public class UserCenterFragment extends Fragment {

	private User m_userCurrent;

	private View m_viewUser;
	private ImageView m_imgvUserAvatar;
	private TextView m_txvName;
	private TextView m_txvSignature;
	private Button m_btnLogout;

	private static UserCenterFragment INSTANCE = new UserCenterFragment();

	public UserCenterFragment() {
	}

	public static UserCenterFragment getInstance() {
		return INSTANCE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 * android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.user_center_fragment, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.Fragment#onViewCreated(android.view.View,
	 * android.os.Bundle)
	 */
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		initComponent(view);

		setUpListener();

		initUser();

		show();
	}

	private void initComponent(View view) {
		m_viewUser = view.findViewById(R.id.user_center_fragment_user);
		m_imgvUserAvatar = (ImageView) view
				.findViewById(R.id.user_center_fragment_user_avatar_imgv);
		m_txvName = (TextView) view
				.findViewById(R.id.user_center_fragment_name_txv);
		m_txvSignature = (TextView) view
				.findViewById(R.id.user_center_fragment_signature_txv);
		m_btnLogout = (Button) view
				.findViewById(R.id.user_center_fragment_logout_btn);
	}

	private void setUpListener() {
		m_viewUser.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().startActivity(
						new Intent(getActivity(), UserInfoActivity.class));

				// getActivity().startActivity(
				// new Intent(getActivity(), TestFloatingActivity.class));
			}
		});

		m_btnLogout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
					((NewMainActivity) getActivity()).logout();
			}
		});
	}

	private void initUser() {
		m_userCurrent = DataManager.getCurrentUser();
	}

	private void show() {
		m_txvName.setText(m_userCurrent.getName());
		m_txvSignature.setText(m_userCurrent.getSignature());
	}

}
