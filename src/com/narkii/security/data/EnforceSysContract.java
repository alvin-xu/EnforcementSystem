package com.narkii.security.data;

import android.provider.BaseColumns;

public final class EnforceSysContract {
	public static final String TAG = "EnforceSysContract";

	public static final String AUTHORITY = "";

	public static final String DATABASE_NAME = "enforcesys";
	public static final int DATABASE_VERSION = 1;

	private EnforceSysContract() {
	}

	/**
	 * ��ҵ��Ϣ��
	 */
	public static final class Enterprise implements BaseColumns {
		private Enterprise() {
		}

		public static final String TABLE_NAME = "Tb_Enterprise";

		public static final String COLUMN_NAME = "company_name";
		public static final String COLUMN_ORGANIZATION_CODE = "organization_code";
		public static final String COLUMN_FILE_NUMBER = "FileNumber";
		public static final String COLUMN_BUSSINESS_LICENSE = "bussiness_license";
		public static final String COLUMN_ADDRESS = "address";
		public static final String COLUMN_AREA = "area";
		public static final String COLUMN_FK_ENTERPRISE_TYPE = "enterprise_type";
		public static final String COLUMN_SPECIAL = "special";
		public static final String COLUMN_TELEPHONE = "telephone";
		public static final String COLUMN_FAX = "fax";
		public static final String COLUMN_EMAIL = "email";
		public static final String COLUMN_REMARK = "remark";
		/**
		 * ���ͣ����������Tb_Safetypermits_Types��
		 */
		public static final String COLUMN_FK_SAFETY_PERMIT_TYPE = "fk_safetypermit_type";
		public static final String COLUMN_SAFETY_PERMIT_NUM = "safetypermit_num";
		public static final String COLUMN_SCOPE = "scope";
		public static final String COLUMN_ISSUE_DATE = "issue_date";
		public static final String COLUMN_VALID_DATE = "valid_date";
		public static final String COLUMN_SITUATION = "situation";
		public static final String COLUMN_SITUATION_DATE = "situation_date";
		public static final String COLUMN_DOC_NUM = "doc_num";
		public static final String COLUMN_USER_TYPE = "user_type";
		public static final String COLUMN_CHARGE = "charge";
		public static final String COLUMN_SAFE_ADMIN = "safe_admin";
		public static final String COLUMN_CHARGE_PHONE = "charge_phone";
		public static final String COLUMN_SAFE_ADMIN_PHONE = "safe_admin_phone";
		public static final String COLUMN_CREATE_DATE = "create_date";
		public static final String COLUMN_CREATE_BY = "create_by";
		public static final String COLUMN_UPDATE_DATE = "update_date";
		public static final String COLUMN_UPDATE_BY = "update_by";

		public static final String[] COLUMNS = { COLUMN_NAME,
				COLUMN_ORGANIZATION_CODE, COLUMN_FILE_NUMBER,
				COLUMN_BUSSINESS_LICENSE, COLUMN_ADDRESS, COLUMN_AREA,
				COLUMN_FK_ENTERPRISE_TYPE, COLUMN_SPECIAL, COLUMN_TELEPHONE,
				COLUMN_FAX, COLUMN_EMAIL, COLUMN_REMARK,
				COLUMN_FK_SAFETY_PERMIT_TYPE, COLUMN_SAFETY_PERMIT_NUM,
				COLUMN_SCOPE, COLUMN_ISSUE_DATE, COLUMN_VALID_DATE,
				COLUMN_SITUATION, COLUMN_SITUATION_DATE, COLUMN_DOC_NUM,
				COLUMN_USER_TYPE, COLUMN_CHARGE, COLUMN_SAFE_ADMIN,
				COLUMN_CHARGE_PHONE, COLUMN_SAFE_ADMIN_PHONE,
				COLUMN_CREATE_DATE, COLUMN_CREATE_BY, COLUMN_UPDATE_DATE,
				COLUMN_UPDATE_BY };

	}

	/**
	 * ������
	 */
	public static final class Filing implements BaseColumns {
		private Filing() {
		};

		public static final String TABLE_NAME = "Tb_Filing";
		/**
		 * ��ҵId������Tb_Enterprise��
		 */
		public static final String COLUMN_FK_ENTERPRISE_ID = "enterprise_id";

		/**
		 * ��ȫ������׼��-�ȼ�
		 */
		public static final String COLUMN_F1_RANK = "f1_rank";
		/**
		 * ��ȫ������׼��-��֤����
		 */
		public static final String COLUMN_F1_ISSUE_ORGAN = "f1_issue_organ";
		/**
		 * ��ȫ������׼��-���
		 */
		public static final String COLUMN_F1_NUM = "f1_num";
		/**
		 * ��ȫ������׼��-��֤����
		 */
		public static final String COLUMN_F1_ISSUE_DATE = "f1_issue_date";
		/**
		 * ��ȫ������׼��-��Ч����
		 */
		public static final String COLUMN_F1_VALID_DATE = "f1_valid_date";

		/**
		 * Ʒ�����ͣ�����Tb_Variety_type��
		 */
		public static final String COLUMN_FK_VARITY_TYPE_ID = "varity_type_id";
		/**
		 * ���ƶ���ѧƷ������Ӫ-Ʒ��
		 */
		public static final String COLUMN_F2_VARITY = "f2_varity";
		/**
		 * ���ƶ���ѧƷ������Ӫ-��Ҫ����
		 */
		public static final String COLUMN_F2_MAINFLOW = "f2_mainflow";
		/**
		 * ���ƶ���ѧƷ������Ӫ-���
		 */
		public static final String COLUMN_F2_NUM = "f2_num";
		/**
		 * ���ƶ���ѧƷ������Ӫ-��֤����
		 */
		public static final String COLUMN_F2_ISSUE_DATE = "f2_issue_date";
		/**
		 * ���ƶ���ѧƷ������Ӫ-��Ч����
		 */
		public static final String COLUMN_F2_VALID_DATE = "f2_valid_date";

		/**
		 * �ش�Σ��Դ-���
		 */
		public static final String COLUMN_F3_NUM = "f3_num";
		/**
		 * �ش�Σ��Դ-Ʒ��
		 */
		public static final String COLUMN_F3_VARITY = "f3_varity";
		/**
		 * �ش�Σ��Դ-����
		 */
		public static final String COLUMN_F3_RESERVES = "f3_reserves";
		/**
		 * �ش�Σ��Դ-�ȼ�
		 */
		public static final String COLUMN_F3_RANK = "f3_rank";
		/**
		 * �ش�Σ��Դ-��������
		 */
		public static final String COLUMN_F3_EVALUATE_DATE = "f3_evaluate_date";
		/**
		 * �ش�Σ��Դ-��������
		 */
		public static final String COLUMN_F3_NET_DATE = "f3_net_date";

		/**
		 * Ӧ��Ԥ������-Ԥ������
		 */
		public static final String COLUMN_F4_PLAN_NAME = "f4_plan_name";
		/**
		 * Ӧ��Ԥ������-�������
		 */
		public static final String COLUMN_F4_RECORD_NUM = "f4_record_num";
		/**
		 * Ӧ��Ԥ������-��������
		 */
		public static final String COLUMN_F4_RELEASE_DATE = "f4_release_date";
		/**
		 * Ӧ��Ԥ������-�汾��
		 */
		public static final String COLUMN_F4_VERSION_NUM = "f4_version_num";
		/**
		 * Ӧ��Ԥ������-��������
		 */
		public static final String COLUMN_F4_FILING_DATE = "f4_filing_date";
		/**
		 * Ӧ��Ԥ������-��������
		 */
		public static final String COLUMN_F4_REVIEW_DATE = "f4_review_date";

		public static final String COLUMNS[] = { COLUMN_FK_ENTERPRISE_ID,
				COLUMN_F1_RANK, COLUMN_F1_ISSUE_ORGAN, COLUMN_F1_NUM,
				COLUMN_F1_ISSUE_DATE, COLUMN_F1_VALID_DATE,
				COLUMN_FK_VARITY_TYPE_ID, COLUMN_F2_VARITY, COLUMN_F2_MAINFLOW,
				COLUMN_F2_NUM, COLUMN_F2_ISSUE_DATE, COLUMN_F2_VALID_DATE,
				COLUMN_F3_NUM, COLUMN_F3_VARITY, COLUMN_F3_RESERVES,
				COLUMN_F3_RANK, COLUMN_F3_EVALUATE_DATE, COLUMN_F3_NET_DATE,
				COLUMN_F4_PLAN_NAME, COLUMN_F4_RECORD_NUM,
				COLUMN_F4_RELEASE_DATE, COLUMN_F4_VERSION_NUM,
				COLUMN_F4_FILING_DATE, COLUMN_F4_REVIEW_DATE };
	}

	/**
	 * ��Ա��
	 */
	public static final class Member implements BaseColumns {
		private Member() {
		}

		public static final String TABLE_NAME = "Tb_Member";

		public static final String COLUMN_NAME = "member_name";
		public static final String COLUMN_PASSWORD = "password";
		public static final String COLUMN_CERTIFICATE_NO = "certificate_no";
		public static final String COLUMN_GENDER = "gender";
		public static final String COLUMN_TELEPHONE = "telephone";
		public static final String COLUMN_STATE = "state";
		public static final String COLUMN_HEAD = "head";
		public static final String COLUMN_FK_GROUP_ID = "group_id";
		public static final String COLUMN_GROUPE_TYPE = "group_type";
		public static final String COLUMN_MEMBER_TYPE = "member_type";
		public static final String COLUMN_LAST_LOGIN_TIME = "last_login_time";
		public static final String COLUMN_CREATE_DATE = "create_date";
		public static final String COLUMN_UPDATE_DATE = "update_date";
		public static final String COLUMN_UPDATE_BY = "update_by";
		
		public static final String[] COLUMNS = { COLUMN_NAME, COLUMN_PASSWORD,
				COLUMN_CERTIFICATE_NO, COLUMN_GENDER, COLUMN_TELEPHONE,
				COLUMN_STATE, COLUMN_HEAD, COLUMN_FK_GROUP_ID,
				COLUMN_GROUPE_TYPE, COLUMN_MEMBER_TYPE, COLUMN_LAST_LOGIN_TIME,
				COLUMN_CREATE_DATE, COLUMN_UPDATE_DATE, COLUMN_UPDATE_BY };
	}

	/**
	 * �û������
	 */
	public static final class UserGroup implements BaseColumns {
		private UserGroup() {
		};

		public static final String TABLE_NAME = "Tb_User_Group";
		/**
		 * ��������
		 * <P>
		 * Type:TEXT
		 * </P>
		 */
		public static final String COLUMN_NAME = "group_name";

	}

	/**
	 * �������� ��
	 */
	public static final class DocumentType implements BaseColumns {
		private DocumentType() {
		};

		public static final String TABLE_NAME = "Tb_Document_Type";
		/**
		 * ������������
		 * <P>
		 * Type:TEXT
		 * </P>
		 */
		public static final String COLUMN_NAME = "dtype_name";

	}

	/**
	 * ��ҵ���ͱ�
	 */
	public static final class EnterpriseType implements BaseColumns {
		private EnterpriseType() {
		};

		public static final String TABLE_NAME = "Tb_Enterprise_Type";

		/**
		 * ��ҵ��������
		 * <P>
		 * Type:TEXT
		 * </P>
		 */
		public static final String COLUMN_NAME = "etype_name";
	}

	/**
	 * ��ȫ���֤���ͱ�
	 */
	public static final class SafetyPermitType implements BaseColumns {
		private SafetyPermitType() {
		};

		public static final String TABLE_NAME = "Tb_Safety_Permit_Type";

		/**
		 * ��ȫ���֤����
		 * <P>
		 * Type:TEXT
		 * </P>
		 */
		public static final String COLUMN_NAME = "permit_name";
	}

	/**
	 * Ʒ�����ͱ�
	 */
	public static final class VarityType implements BaseColumns {
		private VarityType() {
		};

		public static final String TABLE_NAME = "Tb_Varity_Type";

		/**
		 * Ʒ����������
		 * <P>
		 * Type:TEXT
		 * </P>
		 */
		public static final String COLUMN_NAME = "vtype_name";
	}

	/**
	 * �����
	 */
	public static final class Area implements BaseColumns {
		private Area() {
		};

		public static final String TABLE_NAME = "Tb_Area";

		/**
		 * ��������
		 * <P>
		 * Type:TEXT
		 * </P>
		 */
		public static final String COLUMN_NAME = "area_name";
		/**
		 * <P>
		 * Type:INTEGER
		 * </P>
		 */
		public static final String COLUMN_PID = "pid";
	}

	/**
	 * ���������Ϣ��
	 */
	public static final class Permission implements BaseColumns {
		private Permission() {
		};

		public static final String TABLE_NAME = "Tb_Permission";
		/**
		 * ��ҵid�����
		 * <P>
		 * Type:INTEGER
		 * </P>
		 */
		public static final String COLUMN_FK_ENTERPRISE_ID = "enterprise_id";
		/**
		 * ֤������
		 */
		public static final String COLUMN_CERTIFICATE_NAME = "certificate_name";
		/**
		 * �洢��ַ
		 */
		public static final String COLUMN_URL = "url";
		/**
		 * ���ͣ�1ͼƬ��2�ĵ�
		 * <P>
		 * Type:INTEGER
		 * </P>
		 */
		public static final String COLUMN_TYPE = "type";

	}

	/**
	 * ��ҵ������/����Ա��
	 */
	public static final class EnterprisePersion implements BaseColumns {
		private EnterprisePersion() {
		};

		public static final String TABLE_NAME = "Tb_EnterprisePersion";
		/**
		 * ��ҵid�����
		 * <P>
		 * Type:INTEGER
		 * </P>
		 */
		public static final String COLUMN_FK_ENTERPRISE_ID = "enterprise_id";
		/**
		 * ����
		 */
		public static final String COLUMN_NAME = "eperson_name";
		/**
		 * �绰
		 */
		public static final String COLUMN_PHONE = "phone";
		/**
		 * �����ʼ�
		 */
		public static final String COLUMN_EMAIL = "email";
		/**
		 * ��ȫ�ʸ�֤
		 */
		public static final String COLUMN_SAFE_PAPER = "safe_paper";
		/**
		 * ��֤����
		 */
		public static final String COLUMN_ISSUE_DATE = "issue_date";
		/**
		 * ��Ч����
		 */
		public static final String COLUMN_VALID_DATE = "valid_date";
		/**
		 * ���ͣ�1�����ˣ�2��ȫ����Ա
		 */
		public static final String COLUMN_TYPE = "type";

		public static final String[] COLUMNS = { COLUMN_FK_ENTERPRISE_ID,
				COLUMN_NAME, COLUMN_PHONE, COLUMN_EMAIL, COLUMN_SAFE_PAPER,
				COLUMN_ISSUE_DATE, COLUMN_VALID_DATE, COLUMN_TYPE };
	}

	/**
	 * �������ݱ�
	 */
/*	public static final class DocumentContent implements BaseColumns {
		private DocumentContent() {
		};

		public static final String TABLE_NAME = "Tb_DocumentContent";
		*//**
		 * ��ҵid�����
		 * <P>
		 * Type:INTEGER
		 * </P>
		 *//*
		public static final String COLUMN_FK_ENTERPRISE_ID = "enterprise_id";
		*//**
		 * ��������
		 *//*
		public static final String COLUMN_CONTENT = "content";
		*//**
		 * ��������
		 *//*
		public static final String COLUMN_TYPE = "type";
		*//**
		 * ������
		 *//*
		public static final String COLUMN_NO = "num";
		*//**
		 * ӡ�µ�ַ
		 *//*
		public static final String COLUMN_SEAL = "seal";
	}*/

	/**
	 * ��ҵ�����
	 */
	public static final class Document implements BaseColumns {
		private Document() {
		};

		public static final String TABLE_NAME = "Tb_Document";
		/**
		 * ��ҵid�����
		 * <P>
		 * Type:INTEGER
		 * </P>
		 */
		public static final String COLUMN_FK_ENTERPRISE_ID = "enterprise_id";
		/**
		 * ��������id�����
		 * <P>
		 * Type:INTEGER
		 * </P>
		 */
		public static final String COLUMN_FK_CONTENT_ID = "content_id";

		/**
		 * ��������
		 */
		public static final String COLUMN_FK_DOCUMENT_TYPE = "document_type";
		/**
		 * ������
		 */
		public static final String COLUMN_NO = "num";
/*		*//**
		 * ִ����Ա1������Member�����
		 *//*
		public static final String COLUMN_FK_MEMBER_ID1="member_id1";
		*//**
		 * ִ����Ա2������Member�����
		 *//*
		public static final String COLUMN_FK_MEMBER_ID2="member_id2";*/
		/**
		 * ִ����Ա1
		 */
		public static final String COLUMN_OFFICER1 = "officer1";
		/**
		 * ֤����1
		 */
		public static final String COLUMN_CERTIFICATE_NO1 = "certificate_no1";
		/**
		 * ִ����Ա2
		 */
		public static final String COLUMN_OFFICER2 = "officer2";
		/**
		 * ֤����2
		 */
		public static final String COLUMN_CERTIFICATE_NO2 = "certificate_no2";
		/**
		 * ִ��ʱ��
		 */
		public static final String COLUMN_CREATE_DATE = "create_date";
		/**
		 * ����ʱ��
		 */
		public static final String COLUMN_MATURITY_DATE = "maturity_date";
		/**
		 * ��������
		 */
		public static final String COLUMN_CONTENT = "content";
		/**
		 * ӡ�µ�ַ
		 */
		public static final String COLUMN_SEAL = "seal";
		
		public static final String COLUMNS[] = { COLUMN_FK_ENTERPRISE_ID,
				COLUMN_FK_CONTENT_ID, COLUMN_NO, COLUMN_FK_DOCUMENT_TYPE,
				COLUMN_OFFICER1, COLUMN_CERTIFICATE_NO1, COLUMN_OFFICER2,
				COLUMN_CERTIFICATE_NO2, COLUMN_CREATE_DATE,
				COLUMN_MATURITY_DATE, COLUMN_CONTENT, COLUMN_SEAL };
	}
}
