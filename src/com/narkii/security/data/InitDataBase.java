package com.narkii.security.data;

import com.narkii.security.data.EnforceSysContract.*;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public  class InitDataBase {
	public static final String TAG="InitDataBase";
	
	static boolean isFirstIn = false;
	private static final String SHAREDPREFENCE_NAME="first_perf";
	
	private static final String SQL_INSERT_ENTERPRISE=
			"INSERT INTO "+Enterprise.TABLE_NAME+" values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
	
	
	public static void init(Context context){
		SharedPreferences preferences=context.getSharedPreferences(SHAREDPREFENCE_NAME, Activity.MODE_PRIVATE);
		// 取得相应的值，如果没有该值，说明还未写入，用true作为默认值
        isFirstIn = preferences.getBoolean("isFirstIn", true);
        
        if(!isFirstIn) return;
        
		//企业信息表测试数据
		EnforceSysDbHelper dbHelper=new EnforceSysDbHelper(context);
		
		SQLiteDatabase database=dbHelper.getWritableDatabase();
		for(int i=0;i<10;i++)
		{	String[] args={null,"Q","a","b","c","1","a","b","c","1","a","b","c","1","a","b","c","1","a","b","c","1","a","b","c","1","a","b","c"};
			database.execSQL(SQL_INSERT_ENTERPRISE, args);
		}
		
        DbOperations operations=DbOperations.getInstance(context);
		//为文书表插入测试数据
        //注意：用安卓的sqlite标准接口插入数据，可以不用管自动递增的id列。若是自己写sql，则需置为null。
		ContentValues[] values=new ContentValues[3];
		values[0]=new ContentValues(1);
		values[0].put(DocumentType.COLUMN_NAME, "现场检查记录");
		values[1]=new ContentValues(1);
		values[1].put(DocumentType.COLUMN_NAME, "限期整改指令书");
		values[2]=new ContentValues(1);
		values[2].put(DocumentType.COLUMN_NAME, "整改复查意见书");
		operations.insert(DocumentType.TABLE_NAME, values);
		Log.d("APP", "init database data");

		//企业类型表测试数据
		values=new ContentValues[8];
		String[] types={"全部","生产","存储","加油站","使用","贸易","零售","其他"};
		for(int i=0;i<8;i++){
			values[i]=new ContentValues(1);
			values[i].put(EnterpriseType.COLUMN_NAME, types[i]);
		}
		operations.insert(EnterpriseType.TABLE_NAME, values);
		
		//安全许可证类型
		values=new ContentValues[6];
		String[] types2={"生产","成品油","剧毒品","批发","零售","使用"};
		for(int i=0;i<6;i++){
			values[i]=new ContentValues(1);
			values[i].put(SafetyPermitType.COLUMN_NAME, types2[i]);
		}
		operations.insert(SafetyPermitType.TABLE_NAME, values);
		
		Editor editor=preferences.edit();
		editor.putBoolean("isFirstIn", false);
		editor.commit();
	}
}
