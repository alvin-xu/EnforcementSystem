package com.narkii.security;

import com.narkii.security.enforce.EnforcementFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EnforcementContainer extends Fragment{
	public static final String TAG="EnforcementContainer";
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return inflater.inflate(R.layout.container_enforcement, null);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		EnforcementFragment fragment=new EnforcementFragment();
		getChildFragmentManager().beginTransaction()
				.replace(R.id.enforce_record, fragment,"enforce_search_tag")
				.commit();
		Log.e(TAG, "entry count:"+getChildFragmentManager().getBackStackEntryCount());
	}

	public boolean popFragment() {
	    Log.e("test", "pop fragment: " + getChildFragmentManager().getBackStackEntryCount());
	    boolean isPop = false;
	    if (getChildFragmentManager().getBackStackEntryCount() > 0) {
	        isPop = true;
	        getChildFragmentManager().popBackStack();
	    }
	    return isPop;
	}
}
