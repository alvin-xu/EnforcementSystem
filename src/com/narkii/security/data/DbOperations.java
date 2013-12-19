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
	 * 插入大量数据时用
	 * @param table		表名
	 * @param values	ContentValues的数组
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
	 * 插入数据
	 * @param table		表名
	 * @param values	ContentValues类型的键值对，键位数据库列名，值为要插入数据。
	 * @return	long类型的id，-1表示插入失败
	 */
	public long insert(String table,ContentValues values){
//		if(database==null||!database.isOpen())	database=dbHelper.getWritableDatabase();
		
		
		return database.insert(table, null, values);
	}
	
	/**
	 * 删除一条数据
	 * @param table			表名
	 * @param whereClause	查询条件
	 * @param whereArgs		查询条件中的参数值
	 * @return	int 删除的记录数/0 删除失败
	 */
	public int delete(String table,String whereClause,String[] whereArgs){
		return database.delete(table, whereClause, whereArgs);
	}
	
	/**
	 * 批量删除大量数据
	 * @param table
	 * @param where
	 * @param args	二维参数数组
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
	 * 更新数据
	 * @param table			表名
	 * @param values		需要更改的"列-值"对
	 * @param whereClause	查询条件
	 * @param whereArgs		查询条件中的参数值
	 * @return
	 */
	public int update(String table,ContentValues values,String whereClause, String[] whereArgs){
		return database.update(table, values, whereClause, whereArgs);
	}
	/**
	 * 查询数据
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
	 * 查询数据
	 * @param distinct	true if you want each row to be unique, false otherwise.
	 * @param table		The table name to compile the query against.
	 * @param columns	A list of which columns to return. Passing null will return all columns, which is discouraged to prevent reading data from storage that isn't going to be used.
	 * @param selection	A filter declaring which rows to return, formatted as an SQL WHERE clause (excluding the WHERE itself). Passing null will return all rows for the given table.
	 * @param selectionArgs		You may include ?s in selection, which will be replaced by the values from selectionArgs, in order that they appear in the selection. The values will be bound as Strings.
	 * @param groupBy	A filter declaring how to group rows, formatted as an SQL GROUP BY clause (excluding the GROUP BY itself). Passing null will cause the rows to not be grouped.
	 * @param having	A filter declare which row groups to include in the cursor, if row grouping is being used, formatted as an SQL HAVING clause (excluding the HAVING itself). Passing null will cause all row groups to be included, and is required when row grouping is not being used.
	 * @param orderBy	How to order the rows, formatted as an SQL ORDER BY clause (excluding the ORDER BY itself). Passing null will use the default sort order, which may be unordered.
	 * @param limit		Limits the number of rows returned by the query, formatted as LIMIT clause. Passing null denotes no LIMIT clause.
	 * @return Cursor查询结果
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
	 * 关闭数据库连接，不使用时必须显示调用。
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
