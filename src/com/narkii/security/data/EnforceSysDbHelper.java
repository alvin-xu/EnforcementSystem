package com.narkii.security.data;

import com.narkii.security.data.EnforceSysContract.Area;
import com.narkii.security.data.EnforceSysContract.Document;
import com.narkii.security.data.EnforceSysContract.DocumentType;
import com.narkii.security.data.EnforceSysContract.Enterprise;
import com.narkii.security.data.EnforceSysContract.EnterprisePersion;
import com.narkii.security.data.EnforceSysContract.EnterpriseType;
import com.narkii.security.data.EnforceSysContract.Filing;
import com.narkii.security.data.EnforceSysContract.Member;
import com.narkii.security.data.EnforceSysContract.Permission;
import com.narkii.security.data.EnforceSysContract.SafetyPermitType;
import com.narkii.security.data.EnforceSysContract.UserGroup;
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
	
	private static final String SQL_CREATE_ENTERPRISE=
			"CREATE TABLE "+Enterprise.TABLE_NAME+" ("+
			Enterprise._ID+						PRIMARY_KEY_TYPE+COMMA_SEP+
			Enterprise.COLUMN_NAME+				TEXT_TYPE+COMMA_SEP+
			Enterprise.COLUMN_ORGANIZATION_CODE+TEXT_TYPE+COMMA_SEP+
			Enterprise.COLUMN_FILE_NUMBER+		TEXT_TYPE+COMMA_SEP+
			Enterprise.COLUMN_BUSSINESS_LICENSE+TEXT_TYPE+COMMA_SEP+
			Enterprise.COLUMN_ADDRESS+			TEXT_TYPE+COMMA_SEP+
			Enterprise.COLUMN_AREA+				TEXT_TYPE+COMMA_SEP+
			Enterprise.COLUMN_FK_ENTERPRISE_TYPE+	INTEGER_TYPE+COMMA_SEP+
			Enterprise.COLUMN_SPECIAL+			INTEGER_TYPE+COMMA_SEP+
			Enterprise.COLUMN_TELEPHONE+		TEXT_TYPE+COMMA_SEP+
			Enterprise.COLUMN_FAX+				TEXT_TYPE+COMMA_SEP+
			Enterprise.COLUMN_EMAIL+TEXT_TYPE+COMMA_SEP+
			Enterprise.COLUMN_REMARK+TEXT_TYPE+COMMA_SEP+
			Enterprise.COLUMN_FK_SAFETY_PERMIT_TYPE+INTEGER_TYPE+COMMA_SEP+
			Enterprise.COLUMN_SAFETY_PERMIT_NUM+TEXT_TYPE+COMMA_SEP+
			Enterprise.COLUMN_SCOPE+TEXT_TYPE+COMMA_SEP+
			Enterprise.COLUMN_ISSUE_DATE+TEXT_TYPE+COMMA_SEP+
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
	private static final String SQL_CREATE_AREA=
			"CREATE TABLE "+Area.TABLE_NAME+" ("+
					Area._ID+PRIMARY_KEY_TYPE+COMMA_SEP+
					Area.COLUMN_NAME+TEXT_TYPE+COMMA_SEP+
					Area.COLUMN_PID+INTEGER_TYPE+
			")";
	private static final String SQL_CREATE_FILING=
			"CREATE TABLE "+Filing.TABLE_NAME+" ("+
					Filing._ID+PRIMARY_KEY_TYPE+COMMA_SEP+
					Filing.COLUMN_FK_ENTERPRISE_ID+INTEGER_TYPE+COMMA_SEP+
					Filing.COLUMN_F1_RANK+TEXT_TYPE+COMMA_SEP+
					Filing.COLUMN_F1_ISSUE_ORGAN+TEXT_TYPE+COMMA_SEP+
					Filing.COLUMN_F1_NUM+TEXT_TYPE+COMMA_SEP+
					Filing.COLUMN_F1_ISSUE_DATE+TEXT_TYPE+COMMA_SEP+
					Filing.COLUMN_F1_VALID_DATE+TEXT_TYPE+COMMA_SEP+
					Filing.COLUMN_FK_VARITY_TYPE_ID+INTEGER_TYPE+COMMA_SEP+
					Filing.COLUMN_F2_VARITY+TEXT_TYPE+COMMA_SEP+
					Filing.COLUMN_F2_MAINFLOW+TEXT_TYPE+COMMA_SEP+
					Filing.COLUMN_F2_NUM+TEXT_TYPE+COMMA_SEP+
					Filing.COLUMN_F2_ISSUE_DATE+TEXT_TYPE+COMMA_SEP+
					Filing.COLUMN_F2_VALID_DATE+TEXT_TYPE+COMMA_SEP+
					Filing.COLUMN_F3_NUM+TEXT_TYPE+COMMA_SEP+
					Filing.COLUMN_F3_VARITY+TEXT_TYPE+COMMA_SEP+
					Filing.COLUMN_F3_RESERVES+TEXT_TYPE+COMMA_SEP+
					Filing.COLUMN_F3_RANK+TEXT_TYPE+COMMA_SEP+
					Filing.COLUMN_F3_EVALUATE_DATE+TEXT_TYPE+COMMA_SEP+
					Filing.COLUMN_F3_NET_DATE+TEXT_TYPE+COMMA_SEP+
					Filing.COLUMN_F4_PLAN_NAME+TEXT_TYPE+COMMA_SEP+
					Filing.COLUMN_F4_RECORD_NUM+TEXT_TYPE+COMMA_SEP+
					Filing.COLUMN_F4_RELEASE_DATE+TEXT_TYPE+COMMA_SEP+
					Filing.COLUMN_F4_VERSION_NUM+TEXT_TYPE+COMMA_SEP+
					Filing.COLUMN_F4_FILING_DATE+TEXT_TYPE+COMMA_SEP+
					Filing.COLUMN_F4_REVIEW_DATE+TEXT_TYPE+
			")";
	private static final String SQL_CREATE_MEMBER=
			"CREATE TABLE "+Member.TABLE_NAME+" ("+
					Member._ID+PRIMARY_KEY_TYPE+COMMA_SEP+
					Member.COLUMN_NAME+TEXT_TYPE+COMMA_SEP+
					Member.COLUMN_PASSWORD+TEXT_TYPE+COMMA_SEP+
					Member.COLUMN_CERTIFICATE_NO+TEXT_TYPE+COMMA_SEP+
					Member.COLUMN_GENDER+INTEGER_TYPE+COMMA_SEP+
					Member.COLUMN_TELEPHONE+TEXT_TYPE+COMMA_SEP+
					Member.COLUMN_STATE+INTEGER_TYPE+COMMA_SEP+
					Member.COLUMN_HEAD+TEXT_TYPE+COMMA_SEP+
					Member.COLUMN_FK_GROUP_ID+INTEGER_TYPE+COMMA_SEP+
					Member.COLUMN_GROUPE_TYPE+INTEGER_TYPE+COMMA_SEP+
					Member.COLUMN_MEMBER_TYPE+INTEGER_TYPE+COMMA_SEP+
					Member.COLUMN_LAST_LOGIN_TIME+TEXT_TYPE+COMMA_SEP+
					Member.COLUMN_CREATE_DATE+TEXT_TYPE+COMMA_SEP+
					Member.COLUMN_UPDATE_DATE+TEXT_TYPE+COMMA_SEP+
					Member.COLUMN_UPDATE_BY+INTEGER_TYPE+
			")";
	private static final String SQL_CREATE_PERMISSION=
			"CREATE TABLE "+Permission.TABLE_NAME+" ("+
					Permission._ID+PRIMARY_KEY_TYPE+COMMA_SEP+
					Permission.COLUMN_FK_ENTERPRISE_ID+INTEGER_TYPE+COMMA_SEP+
					Permission.COLUMN_CERTIFICATE_NAME+TEXT_TYPE+COMMA_SEP+
					Permission.COLUMN_URL+TEXT_TYPE+COMMA_SEP+
					Permission.COLUMN_TYPE+INTEGER_TYPE+
			")";
	private static final String SQL_CREATE_ENTERPRISE_PERSION=
			"CREATE TABLE "+EnterprisePersion.TABLE_NAME+" ("+
					EnterprisePersion._ID+PRIMARY_KEY_TYPE+COMMA_SEP+
					EnterprisePersion.COLUMN_FK_ENTERPRISE_ID+INTEGER_TYPE+COMMA_SEP+
					EnterprisePersion.COLUMN_NAME+TEXT_TYPE+COMMA_SEP+
					EnterprisePersion.COLUMN_PHONE+TEXT_TYPE+COMMA_SEP+
					EnterprisePersion.COLUMN_EMAIL+TEXT_TYPE+COMMA_SEP+
					EnterprisePersion.COLUMN_SAFE_PAPER+TEXT_TYPE+COMMA_SEP+
					EnterprisePersion.COLUMN_ISSUE_DATE+TEXT_TYPE+COMMA_SEP+
					EnterprisePersion.COLUMN_VALID_DATE+TEXT_TYPE+COMMA_SEP+
					EnterprisePersion.COLUMN_TYPE+TEXT_TYPE+
			")";
/*	private static final String SQL_CREATE_DOCUMENT_CONTENT=
			"CREATE TABLE "+DocumentContent.TABLE_NAME+" ("+
					DocumentContent._ID+PRIMARY_KEY_TYPE+COMMA_SEP+
					DocumentContent.COLUMN_CONTENT+TEXT_TYPE+COMMA_SEP+
					DocumentContent.COLUMN_TYPE+INTEGER_TYPE+COMMA_SEP+
					DocumentContent.COLUMN_FK_ENTERPRISE_ID+INTEGER_TYPE+COMMA_SEP+
					DocumentContent.COLUMN_NO+TEXT_TYPE+COMMA_SEP+
					DocumentContent.COLUMN_SEAL+TEXT_TYPE+
			")";*/
	private static final String SQL_CREATE_DOCUMENT=
			"CREATE TABLE "+Document.TABLE_NAME+" ("+
					Document._ID+PRIMARY_KEY_TYPE+COMMA_SEP+
					Document.COLUMN_FK_ENTERPRISE_ID+INTEGER_TYPE+COMMA_SEP+
					Document.COLUMN_FK_CONTENT_ID+INTEGER_TYPE+COMMA_SEP+
					Document.COLUMN_NO+TEXT_TYPE+COMMA_SEP+
					Document.COLUMN_FK_DOCUMENT_TYPE+TEXT_TYPE+COMMA_SEP+
//					Document.COLUMN_FK_MEMBER_ID1+INTEGER_TYPE+COMMA_SEP+
//					Document.COLUMN_FK_MEMBER_ID2+INTEGER_TYPE+COMMA_SEP+
					Document.COLUMN_OFFICER1+TEXT_TYPE+COMMA_SEP+
					Document.COLUMN_CERTIFICATE_NO1+TEXT_TYPE+COMMA_SEP+
					Document.COLUMN_OFFICER2+TEXT_TYPE+COMMA_SEP+
					Document.COLUMN_CERTIFICATE_NO2+TEXT_TYPE+COMMA_SEP+
					Document.COLUMN_CREATE_DATE+TEXT_TYPE+COMMA_SEP+
					Document.COLUMN_MATURITY_DATE+TEXT_TYPE+COMMA_SEP+
					Document.COLUMN_CONTENT+TEXT_TYPE+COMMA_SEP+
					Document.COLUMN_SEAL+TEXT_TYPE+
			")";
	private static final String SQL_CREATE_USER_GROUPE=
			"CREATE TABLE "+UserGroup.TABLE_NAME+" ("+
					UserGroup._ID+PRIMARY_KEY_TYPE+COMMA_SEP+
					UserGroup.COLUMN_NAME+TEXT_TYPE+
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
		
		db.execSQL(SQL_CREATE_AREA);
		db.execSQL(SQL_CREATE_ENTERPRISE_TYPE);
		db.execSQL(SQL_CREATE_SAFETY_PERMIT_TYPE);
		
		db.execSQL(SQL_CREATE_ENTERPRISE);
		
		db.execSQL(SQL_CREATE_DOCUMENT_TYPE);
//		db.execSQL(SQL_CREATE_DOCUMENT_CONTENT);
		db.execSQL(SQL_CREATE_DOCUMENT);
		
		db.execSQL(SQL_CREATE_VARITY_TYPE);
		db.execSQL(SQL_CREATE_FILING);
		
		db.execSQL(SQL_CREATE_PERMISSION);
		
		db.execSQL(SQL_CREATE_ENTERPRISE_PERSION);
		
		db.execSQL(SQL_CREATE_USER_GROUPE);
		db.execSQL(SQL_CREATE_MEMBER);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
}
