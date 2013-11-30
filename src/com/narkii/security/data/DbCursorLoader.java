package com.narkii.security.data;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;
/**
 * �Զ����CursorLoader�����ڴ����ݿ���ش������ݣ��첽ִ�У�Ч�ʸߡ�
 * @author xubinbin
 */
public abstract class DbCursorLoader extends CursorLoader{
	public static final String TAG="DbCursorLoader";
	/**
	 * �˷����ڱ���������ݿ��ѯ������
	 * @return ��ѯ���Cursor
	 */
	public abstract Cursor getDbCursor();
	
	public DbCursorLoader(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Cursor loadInBackground() {
		// TODO Auto-generated method stub
		return getDbCursor();
	}
	
}	
