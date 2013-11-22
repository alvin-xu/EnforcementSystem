package com.narkii.security.common;

import com.narkii.security.EnforcementFragment;
import com.narkii.security.InformationFragment;
import com.narkii.security.LawFragment;
import com.narkii.security.UserFragment;

import android.support.v4.app.Fragment;
 
public class FragmentsFactory {
	public static Fragment getFragment(int index){
		switch (index) {
		case 0:
			return new InformationFragment(); 
		case 1:
			return new EnforcementFragment(); 
		case 2:
			return new LawFragment();
		case 3:
			return new UserFragment();
		default:
			return null;
		}
	}
}
