package com.narkii.security.enforce;

import java.util.Calendar;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.widget.EditText;
import android.widget.TimePicker;

@SuppressLint("ValidFragment")
public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
	private EditText text;
	public TimePickerFragment(EditText text){
		super();
		this.text=text;
	}
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
	// Use the current time as the default values for the picker
		final Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int minute = c.get(Calendar.MINUTE);
		
		// Create a new instance of TimePickerDialog and return it
		return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
	}
	
	public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// Do something with the time chosen by the user
		if(text!=null){
			text.setText((hourOfDay<10?"0"+hourOfDay:hourOfDay)+":"+(minute<10?"0"+minute:minute));
		}
	}
}