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
		// ȡ����Ӧ��ֵ�����û�и�ֵ��˵����δд�룬��true��ΪĬ��ֵ
        isFirstIn = preferences.getBoolean("isFirstIn", true);
        
        if(!isFirstIn) return;
        
		//��ҵ��Ϣ���������
		EnforceSysDbHelper dbHelper=new EnforceSysDbHelper(context);
		
		SQLiteDatabase database=dbHelper.getWritableDatabase();
		for(int i=0;i<10;i++)
		{	String[] args={null,"Q","a","b","c","1","a","b","c","1","a","b","c","1","a","b","c","1","a","b","c","1","a","b","c","1","a","b","c"};
			database.execSQL(SQL_INSERT_ENTERPRISE, args);
		}
		
        DbOperations operations=DbOperations.getInstance(context);
		//Ϊ���������������
        //ע�⣺�ð�׿��sqlite��׼�ӿڲ������ݣ����Բ��ù��Զ�������id�С������Լ�дsql��������Ϊnull��
		ContentValues[] values=new ContentValues[3];
		values[0]=new ContentValues(1);
		values[0].put(DocumentType.COLUMN_NAME, "�ֳ�����¼");
		values[1]=new ContentValues(1);
		values[1].put(DocumentType.COLUMN_NAME, "��������ָ����");
		values[2]=new ContentValues(1);
		values[2].put(DocumentType.COLUMN_NAME, "���ĸ��������");
		operations.insert(DocumentType.TABLE_NAME, values);
		Log.d("APP", "init database data");

		//��ҵ���ͱ��������
		values=new ContentValues[8];
		String[] types={"ȫ��","����","�洢","����վ","ʹ��","ó��","����","����"};
		for(int i=0;i<8;i++){
			values[i]=new ContentValues(1);
			values[i].put(EnterpriseType.COLUMN_NAME, types[i]);
		}
		operations.insert(EnterpriseType.TABLE_NAME, values);
		
		//��ȫ���֤����
		values=new ContentValues[6];
		String[] types2={"����","��Ʒ��","�綾Ʒ","����","����","ʹ��"};
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
