package com.narkii.security.info;

import com.narkii.security.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class BaseDataFragment extends Fragment{
	public static final String TAG="BaseData";
	private View view;
	private Button addResponButton,addManagerButton;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.fragment_info_base, null);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		addResponButton=(Button) view.findViewById(R.id.button_info_add_responser);
		addManagerButton=(Button) view.findViewById(R.id.button_info_add_manager);
		addResponButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addPersonInfoView(addResponButton);
			}
		});
		addManagerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addPersonInfoView(addManagerButton);
			}
		});
	}
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		//�����Ա�����Activity��ص�view�ȣ�������ܵ�����Դй¶��Ӧ�ñ�������û���Ϣ���˴���Ҫ���渺���˺͹���Ա����Ϣ��
		super.onSaveInstanceState(outState);
		
	}

	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewStateRestored(savedInstanceState);
	}
	
	/**
	 * ���ڶ�̬���Ӳ��֣�����view�ĸ����ֵĸ�����LinearLayout�����module_responser_manager_info���֣�
	 * @param view
	 */
	private void addPersonInfoView(View view){
		final LinearLayout parent=(LinearLayout) view.getParent().getParent();
		LayoutInflater inflater=LayoutInflater.from(getActivity());
		View subView=inflater.inflate(R.layout.module_responser_manager_info, null);
		parent.addView(subView);
		final Button delButton=(Button) subView.findViewById(R.id.delete_person);
		delButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//ɾ����Ӧ����
				Log.d(TAG, "delete person");
				parent.removeView((View)v.getParent());
			}
		});
	}
}
