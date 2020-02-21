//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.gmall.util;

import java.io.File;
import java.util.ResourceBundle;

public final class Constants {

    public static class Lucene {
        public static final String CREATE_TABLE_INDEX_PROCESS_TIPS = "table_index_process_tips";
        public static final String CREATE_COLUMN_INDEX_PROCESS_TIPS = "column_index_process_tips";
        public static final String CREATE_COMPARE_INDEX_PROCESS_TIPS = "compare_index_process_tips";
        public static final String CREATE_CODOMAIN_INDEX_PROCESS_TIPS = "codomain_index_process_tips";

        // 批量创建lucene索引条数
        public static final Integer BATCH_CREATE_THRESHOLD = 2000;
        /**
         * 全文检索索引路径
         */
        public static final String LUCENE_COMPARE_PATH = String.format("%s%s", getBase(), "compare");
        public static final String LUCENE_TABLE_PATH = String.format("%s%s", getBase(), "table");
        public static final String LUCENE_COLUMN_PATH = String.format("%s%s", getBase(), "column");
        public static final String LUCENE_BASE_PATH_KEY = "lucene.index.base.path";
        public static final String LUCENE_CODOMAIN_PATH = String.format("%s%s", getBase(), "codomain");

        private static String getBase() {
            String value = "";
            ResourceBundle bundle = ResourceBundle.getBundle("config");
            if (bundle != null) {
                value = bundle.getString(LUCENE_BASE_PATH_KEY);
                if (!value.endsWith("/") || !value.endsWith("\\")) {
                    value = value + File.separator;
                }
            }
            return value;
        }
    }

    public static class DateBase {

        public static final String MYSQLDATABASETYPE = "MySql";
        public static final String MYSQLDRIVER = "com.mysql.jdbc.Driver";
        public static final String MYSQLDATABASENAME = "mysql";
        public static final String MYSQLURL = "jdbc:mysql://";
        public static final String MYSQLUSERNAME = "";
        public static final String MYSQLPASSWROD = "";
        public static final String MYSQLPORT = "";
        public static final String MYSQLIP = "";

        public static final String ORACLEDATABASETYPE = "Oracle";
        public static final String ORACLEDRIVER = "oracle.jdbc.driver.OracleDriver";
        public static final String ORACLEDATABASENAME = "Oracle";
        public static final String ORACLEURL = "jdbc:oracle:thin:@";
        public static final String ORACLEUSERNAME = "";
        public static final String ORACLEPASSWROD = "";
        public static final String ORACLEPORT = "";
        public static final String ORACLEIP = "";

        public static final String POSTGRESQLDATABASETYPE = "PostgreSQL";
        public static final String POSTGRESQLDRIVER = "org.postgresql.Driver";
        public static final String POSTGRESQLDATABASENAME = "PostgreSQL";
        public static final String POSTGRESQLURL = "jdbc:postgresql://";
        public static final String POSTGRESQLUSERNAME = "";
        public static final String POSTGRESQLPASSWROD = "";
        public static final String POSTGRESQLPORT = "";
        public static final String POSTGRESQLIP = "";

        public static final String MSSQLDATABASETYPE = "MSSQL";
        public static final String MSSQLDRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        public static final String MSSQLDATABASENAME = "PostgreSQL";
        public static final String MSSQLURL = "jjdbc:sqlserver://";
        public static final String MSSQLUSERNAME = "";
        public static final String MSSQLPASSWROD = "";
        public static final String MSSQLPORT = "";
        public static final String MSSQLIP = "";
    }

    public static class Token {
        // token过期时间（单位：毫秒）
        public static final Long TOKEN_EXPIRE_TIME = 365 * 30 * 24 * 60 * 60 * 1000L;
        // token私钥
        public static final String SECRET_KEY = "qh9Gw8rK92GYs6NV4BR9VlzGiYPkhIukuRcrOxOizs4";

    }

    public static class Request {
        public static final String AUTHORIZATION = "Authorization";

        public static final String USER_ID = "userId";
    }

    public static class Response {

        public static final String STATUS = "status";
        public static final String MSG = "message";
        public static final String ERROR = "error";
        public static final String DATA = "data";

        public static final String SUCCESS = "success";
        public static final String FAILED = "failed";
        public static final String UNKNOW_ERROR = "未知错误！";
        public static final String SERVER_ERROR = "服务器异常！";
        public static final String TOKEN_INVALID_ERROR = "token超时";

        public static final String NOT_FOUNT = "数据库无数据";
    }

    public static class Http {
        public static final Integer _100 = 100; //CONTINUE
        public static final Integer _200 = 200; //OK
        public static final Integer _300 = 300; //MULTIPLE_CHOICES
        public static final Integer _400 = 400; //BAD_REQUEST
        public static final Integer _401 = 401; //UNAUTHORIZED
        public static final Integer _403 = 403; //FORBIDDEN
        public static final Integer _404 = 404; //FILE_NOT_FOUND
        public static final Integer _500 = 500; //INTERNAL_SERVER_ERROR
    }

    public static class Template {
        public static final Integer TEMPLATE_UPLOAD_THRESHOLD = 500;
        public static final String TEMPLATE_FIELD_CODE = "template_upload_field";

        public static final String TABLE_NAME = "table_name";
        public static final String TABLE_NAME_CN_MFRS = "table_name_cn_mfrs";
        public static final String TABLE_NAME_CN_ASSIGN = "table_name_cn_assign";
        public static final String TABLE_ATTRIBUTE = "table_attribute";
        public static final String TABLE_REPEAT_FLAG = "repeat_flag";
        public static final String TABLE_REPEAT_ID = "repeat_id";
        public static final String TABLE_MST_SLV_FLAG = "mst_slv_flag";
        public static final String FIELD_NAME = "field_name";
        public static final String FIELD_NAME_CN_MFRS = "field_name_cn_mfrs";
        public static final String FIELD_NAME_CN_ASSIGN = "field_name_cn_assign";
        public static final String FIELD_IS_PRIVACY = "is_privacy";
        public static final String FIELD_PRIVACY_GRADE = "privacy_grade";
        public static final String FIELD_REMARKS = "remarks";
        public static final String INNER_CODE = "inner_code";
        public static final String DRS_NAME = "drs_name";
        public static final String DATA_SET_NAME = "dataset_name";
        public static final String IS_CONTROL_FIELD = "is_control_field";
        public static final String CONTROL_FIELD_TYPE = "control_field_type";
        public static final String DATA_TIME_FORMAT = "date_format";
    }

    public static class EXCEL_EXPORT {
        public static final String EXCEL_EXPORT_BASE_PATH = "excel.export.base.path";
        public static final String EXCEL_TEMPLATE_SUB_PATH = "template";
        public static final String EXCEL_FIELD_SUB_PATH = "field";
        public static final String EXCEL_REF_SUB_PATH = "ref";
        public static final String EXCEL_REF_SUB_DATASET_PATH = "dataset";
        public static final String EXCEL_TEMPLATE_EXPORT_FILENAME = "template_all.zip";
        public static final String EXCEL_FIELD_EXPORT_FILENAME = "medical_fields_all.zip";
        public static final String EXCEL_REF_EXPORT_FILENAME = "dataset_ref_all.zip";
        public static final String EXCEL_REF_DATASET_EXPORT_FILENAME = "ref_all_group_by_dataset.zip";
    }

    public static final String EXCLUDE_PERMISSION_CONTEXT_ATTR_NAME = "urlMappings";


    public static class Dict {
        public static final String SYS_SEX = "sys_sex";
        public static final String SYS_STATE = "sys_state";
        public static final String ACCOUNT_STATE = "account_state";
        public static final String DATASET_PROPERTY = "dataset_property";
        public static final String STANDARD_TYPE = "standard_type";
        public static final String DATAMETA_TYPE = "datameta_type";
        public static final String EM_IS_AGREE = "EM_isAgree";
        public static final String EM_AUDIT_STATU = "EM_auditStatu";
        public static final String PRIVACY_LEVEL = "privacy_level";
        public static final String VALUE_TYPE = "value_type";
        public static final String VALUE_FORMAT = "value_format";
        public static final String MEDICAL_ORG_TYPE = "medical_org_type";
        public static final String MEDICAL_ORG_GRADE = "medical_org_grade";
        public static final String MEDICAL_ORG_LEVEL = "medical_org_level";
        public static final String BUSINESS_TYPE = "business_type";
        public static final String ORGANIZATION_TYPE = "organization_type";
        public static final String DATABASE_TYPE = "database_type";
        public static final String DATABASE_CONN_METHOD = "database_conn_method";
        public static final String TABLE_ATTR = "table_attr";
        public static final String TABLE_TYPE = "table_type";
        public static final String MASTER_SUB_FLAG = "master_sub_flag";
        public static final String ORGANIZATION_ATTR = "organization_attr";
        public static final String MENU_LEVEL = "menu_level";
        public static final String SIGN_STATUS = "sign_status";
        public static final String DATA_STATISTICS_TYPE = "data_statistics_type";
        public static final String TEMPLATE_UPLOAD_FIELD = "template_upload_field";
        public static final String DATASET_DATABASE_CATEGORY = "dataset_database_category";
        public static final String MFR_SYSTEM_CATEGORY = "mfr_system_category";
        public static final String MAPPING_STATUS = "mapping_status";
        public static final String EM_ISAGREE = "EM_isAgree";
    }

    public static class Query {

        public static final String PAGE = "page";
        public static final String PAGE_SIZE = "pageSize";
        public static final String ORDER_FIELD = "orderField";
        public static final String ORDER_RULE = "orderRule";


        public static class DataSet {

        }

        public static class ValueDomainStrucSrc {
            public static final String OPERATION_STATUS = "operationStatus";
            public static final String IS_OBTAIN = "isObtain";
        }

        public static class DataSetStruct {
            public static final String PUBLISH_STATUS = "publishStatus";
        }

        public static class Metadata {
        }

        public static class Dict {
        }

        public static class MedicalOrg {
        }

        public static class Manufacturer {
            public static final String MFR_NAME = "mfrName";
            public static final String MFR_CODE = "mfrCode";
            public static final String PROVINCE = "province";
        }

        public static class Organization {
            public static final String ORGANIZATION_TYPE = "organizationType";
            public static final String VALID_FLAG = "validFlag";
        }

        public static class DataResourceItem {
            public static final String PRIVACY_LEVEL = "privacyLevel";
            public static final String IS_PRIVACY = "isPrivacy";
            public static final String ORGANIZATION_TYPE = "organizationType";
            public static final String VALID_FLAG = "validFlag";
        }

        public static class TableField {
            public static final String TABLE_TYPE = "tableType";
            public static final String TABLE_ATTR = "tableAttr";
            public static final String SYSTEM_TYPE = "systemType";
        }

        public static class Table {
            public static final String TABLE_TYPE = "tableType";
            public static final String TABLE_ATTR = "tableAttr";
            public static final String REPEAT_TABLE_FLAG = "repeatTableFlag";
            public static final String TABLE_ROW_RANGE = "tableRowRange";
            public static final String CORRELATION_STATE = "correlation_state";
        }

        public static class DatabaseAddr {
            public static final String SYSTEM_TYPE = "systemType";
            public static final String DATABASE_TYPE = "databaseType";
        }

        public static class Database {
            public static final String SYSTEM_TYPE = "systemType";
            public static final String VALID_FLAG = "validFlag";
        }

        public static class OrgSystem {
            public static final String SYSTEM_TYPE = "systemType";
            public static final String VALID_FLAG = "validFlag";
        }

        public static class Org {
            public static final String FK_CLASS = "fkClass";
            public static final String FK_GRADE = "fkGrade";
            public static final String FK_LEVEL = "fkLevel";
            public static final String FK_BUSINESS_NATURE = "fkBusinessNature";
            public static final String VALID_FLAG = "validFlag";
        }

        public static class MfrsTableField {
            public static final String FIELD_PK_FLAG = "fieldPkFlag";
            public static final String IS_NULLABLE = "isNullable";
            public static final String SYSTEM_TYPE = "systemType";
            public static final String TABLE_ATTR = "tableAttr";
            public static final String VALID_FLAG = "validFlag";
        }

        public static class MfrsTable {
            public static final String SYSTEM_TYPE = "systemType";
            public static final String TABLE_ATTR = "tableAttr";
            public static final String VALID_FLAG = "validFlag";
        }

        public static class MfrsSystem {
            public static final String SYSTEM_TYPE = "systemType";
            public static final String VALID_FLAG = "validFlag";
        }

        public static class Mfrs {
            public static final String VALID_FLAG = "validFlag";
        }

        public static class Statistic {
            public static final String FK_PROVINCE = "fkProvince";
            public static final String FK_CITY = "fkCity";
            public static final String FK_GRADE = "fkGrade";
        }

        public static class Mark {
            public static final String TABLE_ROW_RANGE = "tableRowRange";
            public static final String FIELD_REF_DICT = "fieldRefDict";
            public static final String IS_CONTROL_FIELD = "isControlField";
            public static final String CONTROL_FIELD_TYPE = "controlFieldType";
            public static final String IS_PRIVACY = "isPrivacy";
            public static final String PRIVACY_LEVEL = "privacyLevel";
            public static final String FK_GRADE = "fkGrade";
            public static final String DATASET_NAME = "datasetName";
            public static final String TABLE_ATTR = "tableAttr";
        }
    }

}


