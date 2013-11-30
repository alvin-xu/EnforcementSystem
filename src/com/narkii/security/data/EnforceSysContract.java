package com.narkii.security.data;

import android.provider.BaseColumns;

public final class EnforceSysContract {
	public static final String TAG="EnforceSysContract";
	
	public static final String AUTHORITY="";
	
	public static final String DATABASE_NAME = "enforcesys";
    public static final int DATABASE_VERSION = 1;
    
	private EnforceSysContract(){}
	
	/**
	 * 企业信息表
	 */
	public static final class Enterprise implements BaseColumns{
		private Enterprise(){}
		public static final String TABLE_NAME="Tb_Enterprise";
		
		public static final String COLUMN_NAME="name";
		public static final String COLUMN_ORGANIZATION_CODE="organization_code";
		public static final String COLUMN_FILE_NUMBER="FileNumber";
		public static final String COLUMN_BUSSINESS_LICENSE="bussiness_license";
		public static final String COLUMN_ADDRESS="address";
		public static final String COLUMN_AREA="area";
		public static final String COLUMN_ENTERPRISE_TYPE="enterprise_type";
		public static final String COLUMN_SPECIAL="special";
		public static final String COLUMN_TELEPHONE="telephone";
		public static final String COLUMN_FAX="fax";
		public static final String COLUMN_EMAIL="email";
		public static final String COLUMN_REMARK="remark";
		/**
		 * 类型（外键，关联Tb_Safetypermits_Types）
		 */
		public static final String COLUMN_FK_SAFETY_PERMIT_TYPE="fk_safetypermit_type";
		public static final String COLUMN_SAFETY_PERMIT_NUM="safetypermit_num";
		public static final String COLUMN_SCOPE="scope";
		public static final String COLUMN_ISSUE_DATE="issue_date";
		public static final String COLUMN_VALID_DATE="valid_date";
		public static final String COLUMN_SITUATION="situation";
		public static final String COLUMN_SITUATION_DATE="situation_date";
		public static final String COLUMN_DOC_NUM="doc_num";
		public static final String COLUMN_USER_TYPE="user_type";
		public static final String COLUMN_CHARGE="charge";
		public static final String COLUMN_SAFE_ADMIN="safe_admin";
		public static final String COLUMN_CHARGE_PHONE="safe_phone";
		public static final String COLUMN_SAFE_ADMIN_PHONE="safe_admin_phone";
		public static final String COLUMN_CREATE_DATE="create_date";
		public static final String COLUMN_CREATE_BY="create_by";
		public static final String COLUMN_UPDATE_DATE="update_date";
		public static final String COLUMN_UPDATE_BY="update_by";
				
	}
	
	/**
	 *  备案表
	 */
	public static final class Filing implements BaseColumns{
		private Filing(){};
		public static final String TABLE_NAME="Tb_Filing";
		/**
		 * 企业Id（关联Tb_Enterprise）
		 */
		public static final String COLUMN_FK_ENTERPRISE_ID="enterprise_id";
		
		/**
		 * 安全生产标准化-等级
		 */
		public static final String COLUMN_F1_RANK="f1_rank";
		/**
		 * 安全生产标准化-发证机关
		 */
		public static final String COLUMN_F1_ISSUE_ORGAN="f1_issue_organ";
		/**
		 * 安全生产标准化-编号
		 */
		public static final String COLUMN_F1_NUM="f1_num";
		/**
		 * 安全生产标准化-发证日期
		 */
		public static final String COLUMN_F1_ISSUE_DATE="f1_issue_date";
		/**
		 * 安全生产标准化-有效日期
		 */
		public static final String COLUMN_F1_VALID_DATE="f1_valid_date";
		
		/**
		 * 品种类型（关联Tb_Variety_type）
		 */
		public static final String COLUMN_FK_VARITY_TYPE_ID="varity_type_id";
		/**
		 * 易制毒化学品生产经营-品种
		 */
		public static final String COLUMN_F2_VARITY="f2_varity";
		/**
		 * 易制毒化学品生产经营-主要流向
		 */
		public static final String COLUMN_F2_MAINFLOW="f2_mainflow";
		/**
		 * 易制毒化学品生产经营-编号
		 */
		public static final String COLUMN_F2_NUM="f2_num";
		/**
		 * 易制毒化学品生产经营-发证日期
		 */
		public static final String COLUMN_F2_ISSUE_DATE="f2_issue_date";
		/**
		 * 易制毒化学品生产经营-有效日期
		 */
		public static final String COLUMN_F2_VALID_DATE="f2_valid_date";
		
		
		/**
		 * 重大危险源-编号
		 */
		public static final String COLUMN_F3_NUM="f3_num";
		/**
		 * 重大危险源-品种
		 */
		public static final String COLUMN_F3_VARITY="f3_varity";
		/**
		 * 重大危险源-储量
		 */
		public static final String COLUMN_F3_RESERVES="f3_reserves";
		/**
		 * 重大危险源-等级
		 */
		public static final String COLUMN_F3_RANK="f3_rank";
		/**
		 * 重大危险源-评估日期
		 */
		public static final String COLUMN_F3_EVALUATE_DATE="f3_evaluate_date";
		/**
		 * 重大危险源-联网日期
		 */
		public static final String COLUMN_F3_NET_DATE="f3_net_date";
		
		
		/**
		 * 应急预案备案-预案名称
		 */
		public static final String COLUMN_F4_PLAN_NAME="f4_plan_name";
		/**
		 * 应急预案备案-备案编号
		 */
		public static final String COLUMN_F4_RECORD_NUM="f4_record_num";
		/**
		 * 应急预案备案-发布日期
		 */
		public static final String COLUMN_F4_RELEASE_DATE="f4_release_date";
		/**
		 * 应急预案备案-版本号
		 */
		public static final String COLUMN_F4_VERSION_NUM="f4_version_num";
		/**
		 * 应急预案备案-备案日期
		 */
		public static final String COLUMN_F4_FILING_DATE="f4_filing_date";
		/**
		 * 应急预案备案-评审日期
		 */
		public static final String COLUMN_F4_REVIEW_DATE="f4_review_date";
		
		
		
	}
	
	/**
	 * 会员表
	 */
	public static final class Member implements BaseColumns{
		private Member(){}
		
		public static final String TABLE_NAME="Tb_Member";
		
		public static final String COLUMN_NAME="name";
		public static final String COLUMN_PASSWORD="password";
		public static final String COLUMN_CERTIFICATE_NO="certificate_no";
		public static final String COLUMN_GENDER="gender";
		public static final String COLUMN_TELEPHONE="telephone";
		public static final String COLUMN_STATE="state";
		public static final String COLUMN_HEAD="head";
		public static final String COLUMN_FK_GROUP_ID="group_id";
		public static final String COLUMN_GROUPE_TYPE="group_type";
		public static final String COLUMN_MEMBER_TYPE="member_type";
		public static final String COLUMN_LAST_LOGIN_TIME="last_login_time";
		public static final String COLUMN_CREATE_DATE="create_date";
		public static final String COLUMN_UPDATE_DATE="update_date";
		public static final String COLUMN_UPDATE_BY="update_by";
	}
	
	/**
	 * 文书类型 表
	 */
	public static final class DocumentType implements BaseColumns{
		private DocumentType(){};
		
		public static final String TABLE_NAME="Tb_Document_Type";
		/**
		 * 文书类型名称
		 * <P>Type:TEXT</P>
		 */
		public static final String COLUMN_NAME="name";
		
	}
	
	/**
	 * 企业类型表
	 */
	public static final class EnterpriseType implements BaseColumns{
		private EnterpriseType(){};
		
		public static final String TABLE_NAME="Tb_Enterprise_Type";
		
		/**
		 * 企业类型名称
		 * <P>Type:TEXT</P>
		 */
		public static final String COLUMN_NAME="name";
	}
	
	/**
	 * 安全许可证类型表
	 */
	public static final class SafetyPermitType implements BaseColumns{
		private SafetyPermitType(){};
		
		public static final String TABLE_NAME="Tb_Safety_Permit_Type";
		
		/**
		 * 安全许可证名称
		 * <P>Type:TEXT</P>
		 */
		public static final String COLUMN_NAME="name";
	}
	
	/**
	 * 品种类型表
	 */
	public static final class VarityType implements BaseColumns{
		private VarityType(){};
		
		public static final String TABLE_NAME="Tb_Varity_Type";
		
		/**
		 * 品种类型名称
		 * <P>Type:TEXT</P>
		 */
		public static final String COLUMN_NAME="name";
	}
	
	/**
	 * 区域表
	 */
	public static final class Area implements BaseColumns{
		private Area(){};
		
		public static final String TABLE_NAME="Tb_Area";
		
		/**
		 * 区域名称
		 * <P>Type:TEXT</P>
		 */
		public static final String COLUMN_NAME="name";
		public static final String COLUMN_PID="pid";
	}
}
