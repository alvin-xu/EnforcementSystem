package com.narkii.security.enforce;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;
import android.widget.EditText;

@SuppressLint("ValidFragment")
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
	public static final int NUMBER_FORMAT=1;
	public static final int CHINA_FORMAT=2;
	private EditText cView;
	private int dateType;

	/**
	 * 
	 * @param v 用于显示date 的EditText
	 * @param dateType 显示形式， 1:2012-12-04；2:2012年12月04日
	 */
	public DatePickerFragment(EditText v,int dateType){
		super();
		cView=v;
		this.dateType=dateType;
	}
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		final Calendar c=Calendar.getInstance();
		int year=c.get(Calendar.YEAR);
		int month=c.get(Calendar.MONTH);
		int day =c.get(Calendar.DAY_OF_MONTH);
		
		return new DatePickerDialog(getActivity(), this, year, month, day);
	}

	@Override
	public void onDateSet(DatePicker view, int year, int monthOfYear,
			int dayOfMonth) {
		// TODO Auto-generated method stub
		if(cView!=null && dateType==NUMBER_FORMAT){
				cView.setText(year+"-"+((monthOfYear+1)<10?"0"+(monthOfYear+1):(monthOfYear+1))+"-"+(dayOfMonth<10?"0"+dayOfMonth:dayOfMonth));
		}else if(cView!=null && dateType==CHINA_FORMAT){
			cView.setText(year+" 年  "+((monthOfYear+1)<10?"0"+(monthOfYear+1):(monthOfYear+1))+" 月  "+(dayOfMonth<10?"0"+dayOfMonth:dayOfMonth)+" 日");
			cView.setTag(year+"-"+((monthOfYear+1)<10?"0"+(monthOfYear+1):(monthOfYear+1))+"-"+(dayOfMonth<10?"0"+dayOfMonth:dayOfMonth));
		}
	}
	
}
