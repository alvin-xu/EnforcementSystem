package com.narkii.security.data;

import com.narkii.security.data.EnforceSysContract.*;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;
import android.util.Log;

public  class InitDataBase {
	public static final String TAG="InitDataBase";
	
	static boolean isFirstIn = false;
	private static final String SHAREDPREFENCE_NAME="first_perf";
	
	
	public static void init(Context context){
		SharedPreferences preferences=context.getSharedPreferences(SHAREDPREFENCE_NAME, Activity.MODE_PRIVATE);
		// ȡ����Ӧ��ֵ�����û�и�ֵ��˵����δд�룬��true��ΪĬ��ֵ
        isFirstIn = preferences.getBoolean("isFirstIn", true);
        
        if(!isFirstIn) return;
		
        DbOperations operations=DbOperations.getInstance(context);
		
        //Ϊ�������ͱ�����������
        //ע�⣺�ð�׿��sqlite��׼�ӿڲ������ݣ����Բ��ù��Զ�������id�С������Լ�дsql��������Ϊnull��
		ContentValues[] values=new ContentValues[3];
		values[0]=new ContentValues(1);
		values[0].put(DocumentType.COLUMN_NAME, "�ֳ�����¼");
		values[1]=new ContentValues(1);
		values[1].put(DocumentType.COLUMN_NAME, "��������ָ����");
		values[2]=new ContentValues(1);
		values[2].put(DocumentType.COLUMN_NAME, "���ĸ��������");
		operations.insert(DocumentType.TABLE_NAME, values);

		//��ҵ���ͱ��������
		values=new ContentValues[8];
		String[] types={"ȫ��","����","�洢","����վ","ʹ��","ó��","����","����"};
		for(int i=0;i<8;i++){
			values[i]=new ContentValues(1);
			values[i].put(EnterpriseType.COLUMN_NAME, types[i]);
		}
		operations.insert(EnterpriseType.TABLE_NAME, values);
		
		//��ȫ���֤����
		
		String[] types2={"ȫ��","����","��Ʒ��","�綾Ʒ","����","����","ʹ��"};
		values=new ContentValues[types2.length];
		for(int i=0;i<types2.length;i++){
			values[i]=new ContentValues(1);
			values[i].put(SafetyPermitType.COLUMN_NAME, types2[i]);
		}
		operations.insert(SafetyPermitType.TABLE_NAME, values);
		
		//������������
		String[] types3={"ȫ��","�����ֵ�","÷��ֵ�","��԰�ֵ�","��ɽ�ֵ�","�����ֵ�","��Դ�ֵ�","������","������","��ܤ��","��ʯ��","���","����","�ص���","�ڿ���","������","������","Ӣ����","��ñ��","������"};
		values=new ContentValues[types3.length];
		for(int i=0;i<types3.length;i++){
			values[i]=new ContentValues(1);
			values[i].put(Area.COLUMN_NAME, types3[i]);
		}
		operations.insert(Area.TABLE_NAME, values);
		
		initEnterpriseData(context);
		
		//Ʒ�����ͱ�
		String[] types4={"��������","���ྭӪ","��������","���ྭӪ"};
		values=new ContentValues[types4.length];
		for(int i=0;i<types4.length;i++){
			values[i]=new ContentValues(1);
			values[i].put(VarityType.COLUMN_NAME, types4[i]);
		}
		operations.insert(VarityType.TABLE_NAME, values);
		
		initFilingData(context);
		initEnterprisePersion(context);
		initEnterpriseDocuments(context);
		initMember(context);
		
/*		String[] names={"֤��1","֤��2","֤��3","֤��4","֤��5","֤��6","֤��7","֤��8","֤��9"};
		String[] images={"/storage/sdcard0/Pictures/EnforcementSys/IMG_20131123_113604.jpg",
				Environment.getExternalStorageDirectory().toString()+"/Pictures/EnforcementSys/IMG_20131123_155059.jpg",
				Environment.getExternalStorageDirectory().toString()+"/Pictures/EnforcementSys/IMG_20131123_155247.jpg",
				Environment.getExternalStorageDirectory().toString()+"/Pictures/EnforcementSys/IMG_20131123_155506.jpg",
				Environment.getExternalStorageDirectory().toString()+"/Pictures/EnforcementSys/IMG_20131123_155744.jpg",
				Environment.getExternalStorageDirectory().toString()+"/Pictures/EnforcementSys/IMG_20131123_161544.jpg",
				Environment.getExternalStorageDirectory().toString()+"/Pictures/EnforcementSys/IMG_20131123_161715.jpg",
				Environment.getExternalStorageDirectory().toString()+"/Pictures/EnforcementSys/IMG_20131125_131726.jpg",
				Environment.getExternalStorageDirectory().toString()+"/Pictures/EnforcementSys/IMG_20131211_102144.jpg",
		};
		Log.d(TAG, Environment.getExternalStorageDirectory().toString());
		values=new ContentValues[names.length];
		for(int i=0;i<names.length;i++){
			values[i]=new ContentValues(4);
			values[i].put(Permission.COLUMN_FK_ENTERPRISE_ID,"1");
			values[i].put(Permission.COLUMN_TYPE, "1");
			values[i].put(Permission.COLUMN_CERTIFICATE_NAME, names[i]);
			values[i].put(Permission.COLUMN_URL, images[i]);
		}
		operations.insert(Permission.TABLE_NAME, values);*/
		
		Editor editor=preferences.edit();
		editor.putBoolean("isFirstIn", false);
		editor.commit();
	}
	
	/**��ҵ��Ϣ���������*/
	public  static void initEnterpriseData(Context context){
		String [][] data={{"AAA��ҵ","00001","x00001","111111","����ʡȪ���н����������ֵ�1��","2","1","3","18030087654","fax8888","7686546@qq.com","","�ĺ�89688","1","������","����ǿ��","18765434567","19087654678","2011-3-4",null,null,null},
				{"BBB��ҵ","00002","x00002","222222","����ʡȪ���н����������ֵ�2��","2","2","3","15330087654","fax7777","216806546@qq.com","","�ĺ�89688","1","������","����ǿ��","18765434567","19087654678","2011-3-4",null,null,null},
				{"CCC��ҵ","00003","x00003","333333","����ʡȪ���н����а�����3��","7","3","3","18098087654","fax58888","00686546@qq.com","","�ĺ�89688","1","��������","��ǿ��","18765434567","19087654678","2011-3-4",null,null,null},
				{"DDD��ҵ","00004","x00004","444444","����ʡȪ���н����������ֵ�4��","3","4","3","18030080004","fax809888","7686546@qq.com","","�ĺ�89688","1","����","ǿ��","18765434567","19087654678","2011-3-4",null,null,null}
		};
		ContentValues[] values=new ContentValues[data.length];
		for(int i=0;i<data.length;i++){
			values[i]=new ContentValues(data[i].length);
			for(int j=0;j<data[i].length;j++)
				values[i].put(Enterprise.COLUMNS[j], data[i][j]);
			
		}
		DbOperations operations=DbOperations.getInstance(context);
		operations.insert(Enterprise.TABLE_NAME, values);
	}
	/**
	 * ������
	 * @param context
	 */
	public static void initFilingData(Context context){
		String [][] data={{"1","�ȼ�1","�����а����","no11111","2012-10-23","2013-12-20","1","Ʒ��","����","no1111","2012-10-23","2013-12-20","no11111","Ʒ��","100t","�ȼ�2","2012-10-23","2012-10-24","Ԥ������1","no1111","2012-11-24","v1","2012-9-2","2012-10-1","1","safe765567","��Ӫ��Χ","2011-5-3","2014-03-06","1",null},
				{"2","�ȼ�1","�����а����","no22222","2012-10-23","2013-12-20","2","Ʒ��","����","no1111","2012-10-23","2013-12-20","no11111","Ʒ��","100t","�ȼ�2","2012-10-23","2012-10-24","Ԥ������1","no1111","2012-11-24","v1","2012-9-2","2012-10-1","2","safe765567","��Ӫ��Χ","2011-5-3","2014-03-07","2",null},
				{"3","�ȼ�1","�����а����","no33333","2012-10-23","2013-12-20","3","Ʒ��","����","no1111","2012-10-23","2013-12-20","no11111","Ʒ��","100t","�ȼ�2","2012-10-23","2012-10-24","Ԥ������1","no1111","2012-11-24","v1","2012-9-2","2012-10-1","3","safe765567","��Ӫ��Χ","2011-5-3","2014-04-05","3",null},
				{"4","�ȼ�1","�����а����","no44444","2012-10-23","2013-12-20","3","Ʒ��","����","no1111","2012-10-23","2013-12-20","no11111","Ʒ��","100t","�ȼ�2","2012-10-23","2012-10-24","Ԥ������1","no1111","2012-11-24","v1","2012-9-2","2012-10-1","4","safe765567","��Ӫ��Χ","2011-5-3","2013-12-01","1",null}
		};
		ContentValues[] values=new ContentValues[data.length];
		for(int i=0;i<data.length;i++){
			values[i]=new ContentValues(data[i].length);
			for(int j=0;j<data[i].length;j++)
				values[i].put(Filing.COLUMNS[j], data[i][j]);
		}
		DbOperations operations=DbOperations.getInstance(context);
		operations.insert(Filing.TABLE_NAME, values);
	}
	/**
	 * ��ҵ������/����Ա��
	 * @param context
	 */
	public static void initEnterprisePersion(Context context){
		String[][] data={{"1","С��","1804004876","376384@qq.com","��ȫ�ʸ�֤","2012-2-5","2015-5-29","1"},
				{"1","С��","1804004876","376384@qq.com","��ȫ�ʸ�֤","2012-2-5","2015-5-29","1"},
				{"1","С��","1804004876","376384@qq.com","��ȫ�ʸ�֤","2012-2-5","2015-5-29","2"},
				{"1","Сǿ","1804004876","376384@qq.com","��ȫ�ʸ�֤","2012-2-5","2015-5-29","2"},
				{"1","С��","1804004876","376384@qq.com","��ȫ�ʸ�֤","2012-2-5","2015-5-29","2"},
				{"2","С��","1804004876","376384@qq.com","��ȫ�ʸ�֤","2012-2-5","2015-5-29","1"},
				{"2","С��","1804004876","376384@qq.com","��ȫ�ʸ�֤","2012-2-5","2015-5-29","2"},
				{"3","С��","1804004876","376384@qq.com","��ȫ�ʸ�֤","2012-2-5","2015-5-29","1"},
				{"3","Сΰ","1804004876","376384@qq.com","��ȫ�ʸ�֤","2012-2-5","2015-5-29","2"},
		};
		ContentValues[] values=new ContentValues[data.length];
		for(int i=0;i<data.length;i++){
			values[i]=new ContentValues(data[i].length);
			for(int j=0;j<data[i].length;j++)
				values[i].put(EnterprisePerson.COLUMNS[j], data[i][j]);
		}
		DbOperations operations=DbOperations.getInstance(context);
		operations.insert(EnterprisePerson.TABLE_NAME, values);
	}
	
	/**
	 * ��ҵ�����
	 * @param context
	 */
	public static void initEnterpriseDocuments(Context context){
		String[][] data={{"1",null,"no11111","1","����","no12334","����","no21234","2013-05-04",null,
			"no11111|AAA��ҵ|������89��|������1|����|19087686680|����|���ã��ܺã��ǳ������ã��ܺã��ǳ������ã��ܺã��ǳ������ã��ܺã��ǳ������ã��ܺã��ǳ������ã��ܺã��ǳ������ã��ܺã��ǳ���",null},
				{"2",null,"no11112","1","����","no12334","����","no21234","2013-05-04",null,
				"no11112|BBB��ҵ|������89��|������1|����|19087686680|����|���ã��ܺã��ǳ������ã��ܺã��ǳ������ã��ܺã��ǳ������ã��ܺã��ǳ������ã��ܺã��ǳ������ã��ܺã��ǳ������ã��ܺã��ǳ���",null},
				{"2",null,"no11113","2","����","no12334","����","no21234","2013-05-04","2013-12-07",null,null},
				{"3",null,"no11114","1","����","no34567","����","no67898","2013-05-04",null,null,null},
				{"3",null,"no11115","2","����","no34567","����","no67898","2013-05-04",null,null,null},
				{"3",null,"no11116","3","����","no34567","����","no67898","2013-07-03",null,null,null}
		};
		ContentValues[] values=new ContentValues[data.length];
		for(int i=0;i<data.length;i++){
			values[i]=new ContentValues(data[i].length);
			for(int j=0;j<data[i].length;j++)
			{	
				values[i].put(Document.COLUMNS[j], data[i][j]);
			}
		}
		DbOperations operations=DbOperations.getInstance(context);
		operations.insert(Document.TABLE_NAME, values);
	}
	/**
	 * �û���Ϣ��
	 * @param context
	 */
	public static void initMember(Context context){
		String[][] data={{"����","111","no12334","0","1897654543","0","","1","1",null,null,null,null},
				{"����","111","no21234","0","1897654543","0","","1","1",null,null,null,null},
				{"����","111","no34567","0","1897654543","0","","1","1",null,null,null,null},
				{"����","111","no67898","0","1897654543","0","","1","1",null,null,null,null},
		};
		ContentValues[] values=new ContentValues[data.length];
		for(int i=0;i<data.length;i++){
			values[i]=new ContentValues(data[i].length);
			for(int j=0;j<data[i].length;j++)
			{	
				values[i].put(Member.COLUMNS[j], data[i][j]);
			}
		}
		DbOperations operations=DbOperations.getInstance(context);
		operations.insert(Member.TABLE_NAME, values);
	}
}
