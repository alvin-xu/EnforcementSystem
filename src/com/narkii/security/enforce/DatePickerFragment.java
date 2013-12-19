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
	private EditText cView;

	public DatePickerFragment(EditText v){
		super();
		cView=v;
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
		if(cView!=null){
				cView.setText(year+"-"+((monthOfYear+1)<10?"0"+(monthOfYear+1):(monthOfYear+1))+"-"+(dayOfMonth<10?"0"+dayOfMonth:dayOfMonth));
		}
	}
	
}
