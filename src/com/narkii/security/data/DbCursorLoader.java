package com.narkii.security.data;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;
/**
 * 自定义的CursorLoader，用于从数据库加载大量数据，异步执行，效率高。
 * @author xubinbin
 */
public abstract class DbCursorLoader extends CursorLoader{
	public static final String TAG="DbCursorLoader";
	/**
	 * 此方法内必须填充数据库查询方法。
	 * @return 查询结果Cursor
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
