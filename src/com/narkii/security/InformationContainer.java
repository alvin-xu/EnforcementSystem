package com.narkii.security;

import com.narkii.security.info.InformationFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class InformationContainer extends Fragment{
	public static final String TAG="InformationContainer";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.container_information, null);
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Log.d(TAG, "on activity result");
		Fragment fragment=getChildFragmentManager().findFragmentByTag("info_search_tag");
		if(fragment!=null){
			Log.d(TAG, "find the child fragment");
			fragment.onActivityResult(requestCode, resultCode, data);
		}
	}
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		InformationFragment fragment=new InformationFragment();
		getChildFragmentManager().beginTransaction()
				.replace(R.id.information, fragment,"info_search_tag")
				.commit();
		Log.e(TAG, "entry count:"+getChildFragmentManager().getBackStackEntryCount());
	}
	
	public boolean popFragment() {
	    Log.e("test", "pop fragment: " + getChildFragmentManager().getBackStackEntryCount());
	    boolean isPop = false;
	    if (getChildFragmentManager().getBackStackEntryCount() > 0) {
	        isPop = true;
//	        getChildFragmentManager().popBackStack();
	        getChildFragmentManager().popBackStackImmediate();
	    }
	    return isPop;
	}
}
