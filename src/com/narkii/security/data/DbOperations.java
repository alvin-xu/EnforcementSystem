package com.narkii.security.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

public class DbOperations {
	public static final String TAG="DbOperations";
	private static DbOperations operations;
	
	private EnforceSysDbHelper dbHelper;
	private SQLiteDatabase	database;
	
	private DbOperations(Context context){
		if(dbHelper==null){
			dbHelper=new EnforceSysDbHelper(context);
			database=dbHelper.getWritableDatabase();
		}
	}
	
	public static DbOperations getInstance(Context context){
		if(operations==null) operations=new DbOperations(context);
		
		return operations;
	}
	
	/**
	 * �����������ʱ��
	 * @param table		����
	 * @param values	ContentValues������
	 * @return boolean
	 */
	public boolean insert(String table,ContentValues[] values){
//		if(database==null||!database.isOpen())	database=dbHelper.getWritableDatabase();
		
		database.beginTransaction();
		for(int i=0;i<values.length;i++){
			database.insert(table, null, values[i]);
		}
		database.setTransactionSuccessful();
		database.endTransaction();

		return true;
	}

	/**
	 * ��������
	 * @param table		����
	 * @param values	ContentValues���͵ļ�ֵ�ԣ���λ���ݿ�������ֵΪҪ�������ݡ�
	 * @return	long���͵�id��-1��ʾ����ʧ��
	 */
	public long insert(String table,ContentValues values){
//		if(database==null||!database.isOpen())	database=dbHelper.getWritableDatabase();
		
		
		return database.insert(table, null, values);
	}
	
	/**
	 * ɾ��һ������
	 * @param table			����
	 * @param whereClause	��ѯ����
	 * @param whereArgs		��ѯ�����еĲ���ֵ
	 * @return	int ɾ���ļ�¼��/0 ɾ��ʧ��
	 */
	public int delete(String table,String whereClause,String[] whereArgs){
		return database.delete(table, whereClause, whereArgs);
	}
	
	/**
	 * ����ɾ����������
	 * @param table
	 * @param where
	 * @param args	��ά��������
	 * @return
	 */
	public int delete(String table,String where,String[][] args){
		int count=0;
		database.beginTransaction();
		for(int i=0;i<args.length;i++){
			count+=database.delete(table, where, args[i]);
			Log.d(TAG, "count:"+count);
		}
		database.setTransactionSuccessful();
		database.endTransaction();
		return	count;
	}
	/**
	 * ��������
	 * @param table			����
	 * @param values		��Ҫ���ĵ�"��-ֵ"��
	 * @param whereClause	��ѯ����
	 * @param whereArgs		��ѯ�����еĲ���ֵ
	 * @return
	 */
	public int update(String table,ContentValues values,String whereClause, String[] whereArgs){
		return database.update(table, values, whereClause, whereArgs);
	}
	/**
	 * ��ѯ����
	 * @param table		The table name to compile the query against.
	 * @param columns	A list of which columns to return. Passing null will return all columns, which is discouraged to prevent reading data from storage that isn't going to be used.
	 * @param selection	A filter declaring which rows to return, formatted as an SQL WHERE clause (excluding the WHERE itself). Passing null will return all rows for the given table.
	 * @param selectionArgs	You may include ?s in selection, which will be replaced by the values from selectionArgs, in order that they appear in the selection. The values will be bound as Strings.
	 * @return
	 */
	public Cursor query(String table,String[] columns,String selection,String[] selectionArgs){
//		if(database==null||!database.isOpen())	database=dbHelper.getReadableDatabase();
		
		Cursor cursor=database.query(table, columns, selection, selectionArgs, null, null, null);
		
		return cursor;
	}
	
	/**
	 * ��ѯ����
	 * @param distinct	true if you want each row to be unique, false otherwise.
	 * @param table		The table name to compile the query against.
	 * @param columns	A list of which columns to return. Passing null will return all columns, which is discouraged to prevent reading data from storage that isn't going to be used.
	 * @param selection	A filter declaring which rows to return, formatted as an SQL WHERE clause (excluding the WHERE itself). Passing null will return all rows for the given table.
	 * @param selectionArgs		You may include ?s in selection, which will be replaced by the values from selectionArgs, in order that they appear in the selection. The values will be bound as Strings.
	 * @param groupBy	A filter declaring how to group rows, formatted as an SQL GROUP BY clause (excluding the GROUP BY itself). Passing null will cause the rows to not be grouped.
	 * @param having	A filter declare which row groups to include in the cursor, if row grouping is being used, formatted as an SQL HAVING clause (excluding the HAVING itself). Passing null will cause all row groups to be included, and is required when row grouping is not being used.
	 * @param orderBy	How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * @param limit		Limits the number of rows returned by the query, formatted as LIMIT clause. Passing null denotes no LIMIT clause.
	 * @return Cursor��ѯ���
	 */
	public Cursor query(boolean distinct,String table, String[] columns, String selection, String[] selectionArgs,
			String groupBy,String having,String orderBy,String limit){
		
		return null;
	}
	public Cursor rawQuery(String sql,String[] selectionArgs){
		Cursor cursor=database.rawQuery(sql, selectionArgs);
		return cursor;
	}
	public Cursor joinQuery(String tables,String[] columns,String selection,String[] args){
		SQLiteQueryBuilder builder=new SQLiteQueryBuilder();
		builder.setTables(tables);
		Cursor cursor=builder.query(database, columns, selection, args, null, null, null);
		return cursor;
	}

	/**
	 * �ر����ݿ����ӣ���ʹ��ʱ������ʾ���á�
	 */
	public void close(){
		if(database!=null){
			database.close();
			operations=null;
		}
	}
	public SQLiteDatabase getDatabase(){
		if(database!=null)
			return database;
		return null;
	}
}
