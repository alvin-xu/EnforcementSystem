package com.narkii.security.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class EnforceSysDbHelper extends SQLiteOpenHelper{
	
	public EnforceSysDbHelper(Context context){
		super(context, EnforceSysContract.DATABASE_NAME, null, EnforceSysContract.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
}
