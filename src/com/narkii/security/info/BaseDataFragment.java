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
		//不可以保存与Activity相关的view等，否则可能导致资源泄露。应该保存的是用户信息，此处需要保存负责人和管理员的信息！
		super.onSaveInstanceState(outState);
		
	}

	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewStateRestored(savedInstanceState);
	}
	
	/**
	 * 用于动态增加布局，是在view的父布局的父布局LinearLayout中添加module_responser_manager_info布局！
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
				//删除对应数据
				Log.d(TAG, "delete person");
				parent.removeView((View)v.getParent());
			}
		});
	}
}
