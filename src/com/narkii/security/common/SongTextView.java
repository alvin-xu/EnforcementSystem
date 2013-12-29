package com.narkii.security.common;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class SongTextView extends TextView{

	private static Typeface tf=null;

	public SongTextView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initFont();
	}

	public SongTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initFont();
	}

	public SongTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initFont();
	}
	
	private void initFont(){
		if(tf==null)
			tf=Typeface.createFromAsset(getContext().getAssets(), "fsong_gb2312.ttf");
		setTypeface(tf);
		getPaint().setFakeBoldText(true);
	}
}
