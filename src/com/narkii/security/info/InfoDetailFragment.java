package com.narkii.security.info;

import com.narkii.security.R;

import android.R.integer;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class InfoDetailFragment extends Fragment{
	public static final String TAG="InfoDetailFragment";
	private View view;
	private TextView[] navViews=new TextView[4];
	private String[] tags={"info_base_tag","info_secure_tag","info_license_tag","info_record_tag"};
	private int currentViewId=0;
	private Bundle param;
	@Override
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
		Log.d(TAG, "on attach");
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		param=getArguments();
		Log.d(TAG, "on activity created");
		//添加企业信息默认显示模块：基本信息
		Fragment baseFragment = new BaseDataFragment();
		baseFragment.setArguments(param);
		getFragmentManager()
				.beginTransaction()
				.replace(R.id.info_detail_container, baseFragment,
						tags[0]).commit();
		currentViewId=0;
		
		navViews[0]=(TextView) view.findViewById(R.id.nav_base_data);
		navViews[0].setBackgroundResource(R.drawable.info_nav_click);
		navViews[1]=(TextView) view.findViewById(R.id.nav_secure_data);
		navViews[2]=(TextView) view.findViewById(R.id.nav_license_info);
		navViews[3]=(TextView) view.findViewById(R.id.nav_record);
		initListeners();
		
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view=inflater.inflate(R.layout.activity_info_detail, null);
		
		return view;
	}
	private void initListeners(){
		for(int i=0;i<navViews.length;i++){
			navViews[i].setTag(i);
			navViews[i].setOnClickListener(new OnClickListener() {
				@SuppressLint("NewApi")
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.d(TAG, "on click "+v.getTag());
					for(int j=0;j<navViews.length;j++){
						if(navViews[j].equals((TextView)v)){
							navViews[j].setBackgroundResource(R.drawable.info_nav_click);
						}
						else 
							navViews[j].setBackground(null);
					}
				
					Fragment fragment=getFragmentManager().findFragmentByTag(tags[(Integer)v.getTag()]);
					if(fragment!=null){
						Log.d(TAG, "show the fragment tag:"+tags[(Integer)v.getTag()]+" hide tag: "+tags[currentViewId]);
						getFragmentManager().beginTransaction()
							.hide(getFragmentManager().findFragmentByTag(tags[currentViewId]))
							.show(fragment)
							.commit();
					}else{
						Log.d(TAG, "add the fragment tag: "+tags[(Integer)v.getTag()]+" hide tag: "+tags[currentViewId]);
						switch ((Integer)v.getTag()) {
							case 0:
								fragment=new BaseDataFragment();
								break;
							case 1:
								fragment=new SecureDataFragment();
								break;
							case 2:
								fragment=new LicenseInfoFragment();
								break;
							case 3:
								fragment=new RecordFragment();
								break;
							default:
								break;
						}
						fragment.setArguments(param);
						getFragmentManager().beginTransaction()
							.hide(getFragmentManager().findFragmentByTag(tags[currentViewId]))
							.add(R.id.info_detail_container, fragment,tags[(Integer)v.getTag()])
							.commit();
						
					}
					currentViewId=(Integer)v.getTag();
				}
			});
		}
		
		
	}
}
