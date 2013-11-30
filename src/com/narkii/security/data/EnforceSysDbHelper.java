package com.narkii.security.data;

import com.narkii.security.data.EnforceSysContract.DocumentType;
import com.narkii.security.data.EnforceSysContract.Enterprise;
import com.narkii.security.data.EnforceSysContract.EnterpriseType;
import com.narkii.security.data.EnforceSysContract.SafetyPermitType;
import com.narkii.security.data.EnforceSysContract.VarityType;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class EnforceSysDbHelper extends SQLiteOpenHelper{
	public static final String TAG="EnforceSysDbHelper";
	
	private static final String TEXT_TYPE = " TEXT";
	private static final String INTEGER_TYPE=" INTEGER";
	private static final String COMMA_SEP = ",";
	private static final String PRIMARY_KEY_TYPE=" INTEGER PRIMARY KEY AUTOINCREMENT";
	
	private static String SQL_CREATE_ENTERPRISE=
			"CREATE TABLE "+Enterprise.TABLE_NAME+" ("+
			Enterprise._ID+						PRIMARY_KEY_TYPE+COMMA_SEP+
			Enterprise.COLUMN_NAME+				TEXT_TYPE+COMMA_SEP+
			Enterprise.COLUMN_ORGANIZATION_CODE+TEXT_TYPE+COMMA_SEP+
			Enterprise.COLUMN_FILE_NUMBER+		TEXT_TYPE+COMMA_SEP+
			Enterprise.COLUMN_BUSSINESS_LICENSE+TEXT_TYPE+COMMA_SEP+
			Enterprise.COLUMN_ADDRESS+			TEXT_TYPE+COMMA_SEP+
			Enterprise.COLUMN_AREA+				TEXT_TYPE+COMMA_SEP+
			Enterprise.COLUMN_ENTERPRISE_TYPE+	INTEGER_TYPE+COMMA_SEP+
			Enterprise.COLUMN_SPECIAL+			INTEGER_TYPE+COMMA_SEP+
			Enterprise.COLUMN_TELEPHONE+		TEXT_TYPE+COMMA_SEP+
			Enterprise.COLUMN_FAX+				TEXT_TYPE+COMMA_SEP+
			Enterprise.COLUMN_EMAIL+TEXT_TYPE+COMMA_SEP+
			Enterprise.COLUMN_REMARK+TEXT_TYPE+COMMA_SEP+
			Enterprise.COLUMN_FK_SAFETY_PERMIT_TYPE+INTEGER_TYPE+COMMA_SEP+
			Enterprise.COLUMN_SAFETY_PERMIT_NUM+TEXT_TYPE+COMMA_SEP+
			Enterprise.COLUMN_SCOPE+TEXT_TYPE+COMMA_SEP+
			Enterprise.COLUMN_VALID_DATE+TEXT_TYPE+COMMA_SEP+
			Enterprise.COLUMN_SITUATION+INTEGER_TYPE+COMMA_SEP+
			Enterprise.COLUMN_SITUATION_DATE+TEXT_TYPE+COMMA_SEP+
			Enterprise.COLUMN_DOC_NUM+TEXT_TYPE+COMMA_SEP+
			Enterprise.COLUMN_USER_TYPE+INTEGER_TYPE+COMMA_SEP+
			Enterprise.COLUMN_CHARGE+TEXT_TYPE+COMMA_SEP+
			Enterprise.COLUMN_SAFE_ADMIN+TEXT_TYPE+COMMA_SEP+
			Enterprise.COLUMN_CHARGE_PHONE+TEXT_TYPE+COMMA_SEP+
			Enterprise.COLUMN_SAFE_ADMIN_PHONE+TEXT_TYPE+COMMA_SEP+
			Enterprise.COLUMN_CREATE_DATE+TEXT_TYPE+COMMA_SEP+
			Enterprise.COLUMN_CREATE_BY+INTEGER_TYPE+COMMA_SEP+
			Enterprise.COLUMN_UPDATE_DATE+TEXT_TYPE+COMMA_SEP+
			Enterprise.COLUMN_UPDATE_BY+INTEGER_TYPE+
			" );";
	private static final String SQL_CREATE_DOCUMENT_TYPE=
			"CREATE TABLE "+DocumentType.TABLE_NAME+" ("+
					DocumentType._ID+	PRIMARY_KEY_TYPE+COMMA_SEP+
					DocumentType.COLUMN_NAME+	TEXT_TYPE+
			" )";
	private static final String SQL_CREATE_ENTERPRISE_TYPE=
			"CREATE TABLE "+EnterpriseType.TABLE_NAME+" ("+
					EnterpriseType._ID+	PRIMARY_KEY_TYPE+COMMA_SEP+
					EnterpriseType.COLUMN_NAME+TEXT_TYPE+
			")";
	private static final String SQL_CREATE_SAFETY_PERMIT_TYPE=
			"CREATE TABLE "+SafetyPermitType.TABLE_NAME+" ("+
					SafetyPermitType._ID+	PRIMARY_KEY_TYPE+COMMA_SEP+
					SafetyPermitType.COLUMN_NAME+TEXT_TYPE+
			")";
	private static final String SQL_CREATE_VARITY_TYPE=
			"CREATE TABLE "+VarityType.TABLE_NAME+" ("+
					VarityType._ID+	PRIMARY_KEY_TYPE+COMMA_SEP+
					VarityType.COLUMN_NAME+TEXT_TYPE+
			")";
	@SuppressWarnings("unused")
	private static final String SQL_DELETE_ENTERPRISE=
			"DROP TABLE IF EXISTS "+Enterprise.TABLE_NAME;
	
	public EnforceSysDbHelper(Context context){
		super(context, EnforceSysContract.DATABASE_NAME, null, EnforceSysContract.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		Log.d("APP", "create table");
		db.execSQL(SQL_CREATE_ENTERPRISE);
		db.execSQL(SQL_CREATE_DOCUMENT_TYPE);
		db.execSQL(SQL_CREATE_ENTERPRISE_TYPE);
		db.execSQL(SQL_CREATE_SAFETY_PERMIT_TYPE);
		db.execSQL(SQL_CREATE_VARITY_TYPE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
}
