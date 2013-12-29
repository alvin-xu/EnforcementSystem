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
		// 取得相应的值，如果没有该值，说明还未写入，用true作为默认值
        isFirstIn = preferences.getBoolean("isFirstIn", true);
        
        if(!isFirstIn) return;
		
        DbOperations operations=DbOperations.getInstance(context);
		
        //为文书类型表插入测试数据
        //注意：用安卓的sqlite标准接口插入数据，可以不用管自动递增的id列。若是自己写sql，则需置为null。
		ContentValues[] values=new ContentValues[3];
		values[0]=new ContentValues(1);
		values[0].put(DocumentType.COLUMN_NAME, "现场检查记录");
		values[1]=new ContentValues(1);
		values[1].put(DocumentType.COLUMN_NAME, "限期整改指令书");
		values[2]=new ContentValues(1);
		values[2].put(DocumentType.COLUMN_NAME, "整改复查意见书");
		operations.insert(DocumentType.TABLE_NAME, values);

		//企业类型表测试数据
		values=new ContentValues[8];
		String[] types={"全部","生产","存储","加油站","使用","贸易","零售","其他"};
		for(int i=0;i<8;i++){
			values[i]=new ContentValues(1);
			values[i].put(EnterpriseType.COLUMN_NAME, types[i]);
		}
		operations.insert(EnterpriseType.TABLE_NAME, values);
		
		//安全许可证类型
		
		String[] types2={"全部","生产","成品油","剧毒品","批发","零售","使用"};
		values=new ContentValues[types2.length];
		for(int i=0;i<types2.length;i++){
			values[i]=new ContentValues(1);
			values[i].put(SafetyPermitType.COLUMN_NAME, types2[i]);
		}
		operations.insert(SafetyPermitType.TABLE_NAME, values);
		
		//区域表测试数据
		String[] types3={"全部","青阳街道","梅岭街道","西园街道","罗山街道","新塘街道","灵源街道","安海镇","磁灶镇","陈埭镇","东石镇","深沪镇","金井镇","池店镇","内坑镇","龙湖镇","永和镇","英林镇","紫帽镇","西滨镇"};
		values=new ContentValues[types3.length];
		for(int i=0;i<types3.length;i++){
			values[i]=new ContentValues(1);
			values[i].put(Area.COLUMN_NAME, types3[i]);
		}
		operations.insert(Area.TABLE_NAME, values);
		
		initEnterpriseData(context);
		
		//品种类型表
		String[] types4={"二类生产","二类经营","三类生产","三类经营"};
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
		
/*		String[] names={"证件1","证件2","证件3","证件4","证件5","证件6","证件7","证件8","证件9"};
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
	
	/**企业信息表测试数据*/
	public  static void initEnterpriseData(Context context){
		String [][] data={{"AAA企业","00001","x00001","111111","福建省泉州市晋江市青阳街道1号","2","1","3","18030087654","fax8888","7686546@qq.com","","文号89688","1","张三分","李四强二","18765434567","19087654678","2011-3-4",null,null,null},
				{"BBB企业","00002","x00002","222222","福建省泉州市晋江市青阳街道2号","2","2","3","15330087654","fax7777","216806546@qq.com","","文号89688","1","刘三分","王四强二","18765434567","19087654678","2011-3-4",null,null,null},
				{"CCC企业","00003","x00003","333333","福建省泉州市晋江市安海镇3号","7","3","3","18098087654","fax58888","00686546@qq.com","","文号89688","1","陈三分田","李强二","18765434567","19087654678","2011-3-4",null,null,null},
				{"DDD企业","00004","x00004","444444","福建省泉州市晋江市青阳街道4号","3","4","3","18030080004","fax809888","7686546@qq.com","","文号89688","1","三分","强二","18765434567","19087654678","2011-3-4",null,null,null}
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
	 * 备案表
	 * @param context
	 */
	public static void initFilingData(Context context){
		String [][] data={{"1","等级1","晋江市安监局","no11111","2012-10-23","2013-12-20","1","品种","流向","no1111","2012-10-23","2013-12-20","no11111","品种","100t","等级2","2012-10-23","2012-10-24","预案名称1","no1111","2012-11-24","v1","2012-9-2","2012-10-1","1","safe765567","经营范围","2011-5-3","2014-03-06","1",null},
				{"2","等级1","晋江市安监局","no22222","2012-10-23","2013-12-20","2","品种","流向","no1111","2012-10-23","2013-12-20","no11111","品种","100t","等级2","2012-10-23","2012-10-24","预案名称1","no1111","2012-11-24","v1","2012-9-2","2012-10-1","2","safe765567","经营范围","2011-5-3","2014-03-07","2",null},
				{"3","等级1","晋江市安监局","no33333","2012-10-23","2013-12-20","3","品种","流向","no1111","2012-10-23","2013-12-20","no11111","品种","100t","等级2","2012-10-23","2012-10-24","预案名称1","no1111","2012-11-24","v1","2012-9-2","2012-10-1","3","safe765567","经营范围","2011-5-3","2014-04-05","3",null},
				{"4","等级1","晋江市安监局","no44444","2012-10-23","2013-12-20","3","品种","流向","no1111","2012-10-23","2013-12-20","no11111","品种","100t","等级2","2012-10-23","2012-10-24","预案名称1","no1111","2012-11-24","v1","2012-9-2","2012-10-1","4","safe765567","经营范围","2011-5-3","2013-12-01","1",null}
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
	 * 企业负责人/管理员表
	 * @param context
	 */
	public static void initEnterprisePersion(Context context){
		String[][] data={{"1","小明","1804004876","376384@qq.com","安全资格证","2012-2-5","2015-5-29","1"},
				{"1","小华","1804004876","376384@qq.com","安全资格证","2012-2-5","2015-5-29","1"},
				{"1","小张","1804004876","376384@qq.com","安全资格证","2012-2-5","2015-5-29","2"},
				{"1","小强","1804004876","376384@qq.com","安全资格证","2012-2-5","2015-5-29","2"},
				{"1","小林","1804004876","376384@qq.com","安全资格证","2012-2-5","2015-5-29","2"},
				{"2","小天","1804004876","376384@qq.com","安全资格证","2012-2-5","2015-5-29","1"},
				{"2","小李","1804004876","376384@qq.com","安全资格证","2012-2-5","2015-5-29","2"},
				{"3","小乔","1804004876","376384@qq.com","安全资格证","2012-2-5","2015-5-29","1"},
				{"3","小伟","1804004876","376384@qq.com","安全资格证","2012-2-5","2015-5-29","2"},
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
	 * 企业文书表
	 * @param context
	 */
	public static void initEnterpriseDocuments(Context context){
		String[][] data={{"1",null,"no11111","1","张三","no12334","李四","no21234","2013-05-04",null,
			"no11111|AAA企业|龙湖街89号|代表人1|经理|19087686680|场所|良好，很好，非常好良好，很好，非常好良好，很好，非常好良好，很好，非常好良好，很好，非常好良好，很好，非常好良好，很好，非常好",null},
				{"2",null,"no11112","1","张三","no12334","李四","no21234","2013-05-04",null,
				"no11112|BBB企业|龙湖街89号|代表人1|经理|19087686680|场所|良好，很好，非常好良好，很好，非常好良好，很好，非常好良好，很好，非常好良好，很好，非常好良好，很好，非常好良好，很好，非常好",null},
				{"2",null,"no11113","2","张三","no12334","李四","no21234","2013-05-04","2013-12-07",null,null},
				{"3",null,"no11114","1","王五","no34567","刘二","no67898","2013-05-04",null,null,null},
				{"3",null,"no11115","2","王五","no34567","刘二","no67898","2013-05-04",null,null,null},
				{"3",null,"no11116","3","王五","no34567","刘二","no67898","2013-07-03",null,null,null}
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
	 * 用户信息表
	 * @param context
	 */
	public static void initMember(Context context){
		String[][] data={{"张三","111","no12334","0","1897654543","0","","1","1",null,null,null,null},
				{"李四","111","no21234","0","1897654543","0","","1","1",null,null,null,null},
				{"王五","111","no34567","0","1897654543","0","","1","1",null,null,null,null},
				{"刘二","111","no67898","0","1897654543","0","","1","1",null,null,null,null},
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
