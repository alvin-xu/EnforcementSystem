package com.narkii.security.common;

import com.narkii.security.EnforcementFragment;
import com.narkii.security.InformationFragment;
import com.narkii.security.LawFragment;
import com.narkii.security.UserFragment;
import com.narkii.security.info.BaseDataFragment;
import com.narkii.security.info.LicenseInfoFragment;
import com.narkii.security.info.RecordFragment;
import com.narkii.security.info.RemarkFragment;
import com.narkii.security.info.SecureDataFragment;

import android.support.v4.app.Fragment;
 
public class FragmentsFactory {
	public static Fragment getMainFragment(int index){
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
	public static Fragment getInfoFragment(int index){
		switch (index) {
		case 0:
			return new BaseDataFragment(); 
		case 1:
			return new SecureDataFragment(); 
		case 2:
			return new LicenseInfoFragment();
		case 3:
			return new RecordFragment();
		case 4:
			return new RemarkFragment();
		default:
			return null;
		}
	}
}
