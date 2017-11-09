alter table t_daily_check add first_area varchar2(100);
alter table t_daily_check add second_area varchar2(100);
alter table t_daily_check add third_area varchar2(100);
alter table t_hidden_un_serious add HIDDEN_TYPE1 varchar2(100);
alter table t_hidden_un_serious add HIDDEN_TYPE2 varchar2(100);
alter table T_LAW_YH_INFO add TYPE1 varchar2(100);
alter table T_LAW_YH_INFO add TYPE2 varchar2(100);


CREATE OR REPLACE PROCEDURE SP_DAILY_CHECK_SYN(IN_SYN_FROM_CODE        in varchar2,
                                               IN_UUID                 IN VARCHAR2,
                                               IN_ASSEMBLEID           IN VARCHAR2, --业务ID：检查ＩＤ＋隐患ＩＤ 或其他 标准化、乡镇、执法过来的id
                                               IN_DEPARTMENT           IN VARCHAR2, --实施部门
                                               IN_CHECK_DATE           IN VARCHAR2, -- 检查日期
                                               IN_CHECK_TYPE           IN VARCHAR2, --检查类别
                                               IN_CHECK_PERSON         IN VARCHAR2, --检查人
                                               IN_CHECK_MAJOR_ACCIDENT IN VARCHAR2, --检查情况(主要隐患)
                                               IN_CHECK_CASE           IN VARCHAR2, --检查情况(整改意见)
                                               IN_REPORT_ORG           IN VARCHAR2, --抄告单位
                                               IN_REPORT_ORG_ID        IN VARCHAR2, --抄告单ID
                                               IN_RE_CHECK_TIME        IN VARCHAR2, --复查情况(时间)
                                               IN_RE_CHECK_OPINION     IN VARCHAR2, --复查情况(意见)
                                               IN_BOOK_ID              IN VARCHAR2, --书面通知ID
                                               IN_REC_FINISH_TIME      IN VARCHAR2,
                                               IN_HIDDEN_TYPE          IN VARCHAR2,
                                               IN_HIDDEN_SOURCE        IN VARCHAR2,
                                               IN_HIDDEN_LEVEL         IN VARCHAR2,
                                               IN_HIDDEN_INPUT_TIME    IN VARCHAR2,
                                               IN_REC_STATUS           IN VARCHAR2,
                                               IN_RE_CHECK_CASE        IN VARCHAR2,
                                               IN_FIRST_AREA           IN VARCHAR2,
                                               IN_SECOND_AREA          IN VARCHAR2,
                                               IN_THIRD_AREA           IN VARCHAR2,
                                               IN_IS_DELETED IN INTEGER,
                                               
                                               IN_CREATE_TIME IN VARCHAR2,
                                               IN_MODIFY_TIME IN VARCHAR2,
                                               
                                               OUT_RESULT OUT VARCHAR2) IS

  C_TIME_R DATE; --当前时间

  SP_NAME CONSTANT VARCHAR2(100) := 'SP_DAILY_CHECK_SYN';

  TB_NAME CONSTANT VARCHAR2(100) := 'T_DAILY_CHECK';

  IN_MAIN_PARAM_R VARCHAR2(4000); --主要参数记录

  N_ROW NUMBER;

  T_COMPANY_ID NUMBER;

  T_DAILY_CHECK_ID NUMBER;

  T_RECORD_STATE S_DAILY_CHECK_INC.RECORD_STATE%TYPE;

  T_DAILY_CHECK_CLOB CLOB;

  T_SYN_NO NUMBER;

  T_CREATE_TIME DATE;
  T_MODIFY_TIME DATE;

  T_ASSEMBLEID S_DAILY_CHECK_INC.ID%TYPE;

BEGIN

  C_TIME_R := SYSDATE();

  IN_MAIN_PARAM_R := '''' || IN_UUID || ''',''' || IN_SYN_FROM_CODE ||
                     ''',''' || IN_CHECK_DATE || ''',''' ||
                     IN_RE_CHECK_TIME || ''',''' || IN_HIDDEN_INPUT_TIME ||
                     ''',''' || IN_ASSEMBLEID || '''';

  IF IN_ASSEMBLEID IS NULL AND IN_SYN_FROM_CODE IS NULL THEN
    RAISE PKG_CONSTANT.SYN_ERROR_EXCEPTION;
  END IF;

  IF IN_SYN_FROM_CODE = PKG_CONSTANT.SUPPORT_SYS_NBXZJD_CODE THEN
    T_ASSEMBLEID := 'XZJD_' || IN_ASSEMBLEID;
  ELSIF IN_SYN_FROM_CODE = PKG_CONSTANT.SUPPORT_SYS_NBXZZF_CODE THEN
    T_ASSEMBLEID := 'XZZF_' || IN_ASSEMBLEID;
  ELSIF IN_SYN_FROM_CODE = PKG_CONSTANT.SUPPORT_SYS_NBBZH_CODE THEN
    T_ASSEMBLEID := 'BZH_' || IN_ASSEMBLEID;
  ELSIF IN_SYN_FROM_CODE = PKG_CONSTANT.SUPPORT_SYS_CENTER_CODE THEN
    T_ASSEMBLEID := IN_ASSEMBLEID;
  ELSE
    RAISE PKG_CONSTANT.SYN_ERROR_EXCEPTION;
  END IF;

  SELECT ID INTO T_COMPANY_ID FROM T_COMPANY WHERE UUID = IN_UUID;

  --是否存在
  SELECT COUNT(1)
    INTO N_ROW
    FROM T_DAILY_CHECK T
   WHERE T.ASSEMBLEID = T_ASSEMBLEID;

  IF N_ROW > 1 THEN
    RAISE PKG_CONSTANT.SYN_MULTI_CONFLICT_EXCEPTION;
  ELSIF N_ROW = 0 THEN
    T_DAILY_CHECK_ID := NULL;
  ELSE
    SELECT ID
      INTO T_DAILY_CHECK_ID
      FROM T_DAILY_CHECK
     WHERE ASSEMBLEID = T_ASSEMBLEID;
  END IF;

  T_CREATE_TIME := NULL;
  IF IN_CREATE_TIME IS NOT NULL THEN
    T_CREATE_TIME := TO_DATE(IN_CREATE_TIME, 'YYYY-MM-DD HH24:MI:SS');
  END IF;

  T_MODIFY_TIME := NULL;
  IF IN_MODIFY_TIME IS NOT NULL THEN
    T_MODIFY_TIME := TO_DATE(IN_MODIFY_TIME, 'YYYY-MM-DD HH24:MI:SS');
  END IF;

  IF T_DAILY_CHECK_ID IS NOT NULL THEN
    IF IN_IS_DELETED = PKG_CONSTANT.DELETED_YES THEN
      T_RECORD_STATE := PKG_CONSTANT.RECORD_ST_DELETE;
    ELSE
      T_RECORD_STATE := PKG_CONSTANT.RECORD_ST_UPDATE;
    END IF;
  
    UPDATE T_DAILY_CHECK
       SET COMPANY_ID = T_COMPANY_ID,
           
           DEPARTMENT           = IN_DEPARTMENT,
           CHECK_DATE           = TO_DATE(IN_CHECK_DATE,
                                          'YYYY-MM-DD HH24:MI:SS'),
           CHECK_TYPE           = IN_CHECK_TYPE,
           CHECK_PERSON         = IN_CHECK_PERSON,
           CHECK_MAJOR_ACCIDENT = IN_CHECK_MAJOR_ACCIDENT,
           CHECK_CASE           = IN_CHECK_CASE,
           REPORT_ORG           = IN_REPORT_ORG,
           REPORT_ORG_ID        = IN_REPORT_ORG_ID,
           RE_CHECK_TIME        = TO_DATE(IN_RE_CHECK_TIME,
                                          'YYYY-MM-DD HH24:MI:SS'),
           RE_CHECK_OPINION     = IN_RE_CHECK_OPINION,
           BOOK_ID              = IN_BOOK_ID,
           
           REC_FINISH_TIME   = TO_DATE(IN_REC_FINISH_TIME,
                                       'YYYY-MM-DD HH24:MI:SS'),
           HIDDEN_TYPE       = IN_HIDDEN_TYPE,
           HIDDEN_SOURCE     = IN_HIDDEN_SOURCE,
           HIDDEN_LEVEL      = IN_HIDDEN_LEVEL,
           HIDDEN_INPUT_TIME = TO_DATE(IN_HIDDEN_INPUT_TIME,
                                       'YYYY-MM-DD HH24:MI:SS'),
           REC_STATUS        = IN_REC_STATUS,
           RE_CHECK_CASE=IN_RE_CHECK_CASE,
           FIRST_AREA = IN_FIRST_AREA,
           SECOND_AREA = IN_SECOND_AREA,
           THIRD_AREA = IN_THIRD_AREA,
           IS_DELETED   = IN_IS_DELETED,
           RECORD_STATE = T_RECORD_STATE,
           MODIFY_TIME  = NVL(T_MODIFY_TIME, C_TIME_R)
     WHERE ID = T_DAILY_CHECK_ID;
  ELSE
  
    T_RECORD_STATE := PKG_CONSTANT.RECORD_ST_INSERT;
    INSERT INTO T_DAILY_CHECK
      (ID,
       CHECK_DATE,
       DEPARTMENT,
       CHECK_TYPE,
       CHECK_PERSON,
       CHECK_MAJOR_ACCIDENT,
       CHECK_CASE,
       REPORT_ORG,
       RE_CHECK_TIME,
       RE_CHECK_OPINION,
       COMPANY_ID,
       
       REC_FINISH_TIME,
       HIDDEN_TYPE,
       HIDDEN_SOURCE,
       HIDDEN_LEVEL,
       HIDDEN_INPUT_TIME,
       REC_STATUS,
       RE_CHECK_CASE,
       IS_DELETED,
       CREATE_TIME,
       MODIFY_TIME,
       ASSEMBLEID,
       REPORT_ORG_ID,
       BOOK_ID,
       FIRST_AREA,
       SECOND_AREA,
       THIRD_AREA)
    VALUES
      (SEQ_DAILY_CHECK.NEXTVAL,
       
       TO_DATE(IN_CHECK_DATE, 'YYYY-MM-DD HH24:MI:SS'),
       IN_DEPARTMENT,
       IN_CHECK_TYPE,
       IN_CHECK_PERSON,
       IN_CHECK_MAJOR_ACCIDENT,
       IN_CHECK_CASE,
       IN_REPORT_ORG,
       
       TO_DATE(IN_RE_CHECK_TIME, 'YYYY-MM-DD HH24:MI:SS'),
       IN_RE_CHECK_OPINION,
       T_COMPANY_ID,
       
       TO_DATE(IN_REC_FINISH_TIME, 'YYYY-MM-DD HH24:MI:SS'),
       IN_HIDDEN_TYPE,
       IN_HIDDEN_SOURCE,
       IN_HIDDEN_LEVEL,
       TO_DATE(IN_HIDDEN_INPUT_TIME, 'YYYY-MM-DD HH24:MI:SS'),
       IN_REC_STATUS,
       IN_RE_CHECK_CASE,
       IN_IS_DELETED,
       NVL(T_CREATE_TIME, C_TIME_R),
       NVL(T_CREATE_TIME, C_TIME_R),
       T_ASSEMBLEID,
       IN_REPORT_ORG_ID,
       IN_BOOK_ID,
       IN_FIRST_AREA,
       IN_SECOND_AREA,
       IN_THIRD_AREA);
  END IF;
  OUT_RESULT := PKG_CONSTANT.RESULT_SUCCESS;
  COMMIT;

  --产生增量数据
  T_DAILY_CHECK_CLOB := FUN_DAILY_CHECK_JSON(T_ASSEMBLEID);
  T_SYN_NO           := TO_NUMBER(GEN_DAILY_CHECK_SYN_NO());
  INSERT INTO S_DAILY_CHECK_INC
    (ID, SYN_NO, SYN_DATA, RECORD_STATE, CREATE_TIME)
  VALUES
    (T_ASSEMBLEID, T_SYN_NO, T_DAILY_CHECK_CLOB, T_RECORD_STATE, C_TIME_R);
  COMMIT;

  --更新同步表信息;
  UPDATE C_SYN_TBL_INFO
     SET IS_HAS_SYN = 0
   WHERE OP_CODE = PKG_SYN_CONST.SYN_GEN_DAILY_CHECK_CODE;
  COMMIT;

EXCEPTION
  WHEN PKG_CONSTANT.SYN_MULTI_CONFLICT_EXCEPTION THEN
    OUT_RESULT := PKG_CONSTANT.RESULT_ERROR;
    P_CORRECT_ERROR_SYN_TEMP(TB_NAME,
                             IN_MAIN_PARAM_R,
                             SP_NAME,
                             PKG_CONSTANT.SYN_MULTI_CONFLICT);
  WHEN PKG_CONSTANT.SYN_MATCH_ERROR_EXCEPTION THEN
    OUT_RESULT := PKG_CONSTANT.RESULT_ERROR;
    P_CORRECT_ERROR_SYN_TEMP(TB_NAME,
                             IN_MAIN_PARAM_R,
                             SP_NAME,
                             PKG_CONSTANT.SYN_MATCH_ERROR);
  WHEN OTHERS THEN
    OUT_RESULT := PKG_CONSTANT.RESULT_ERROR;
    P_CORRECT_ERROR_SYN_TEMP(TB_NAME,
                             IN_MAIN_PARAM_R || '-----' || SQLERRM,
                             SP_NAME,
                             PKG_CONSTANT.SYN_ERROR);
  
END SP_DAILY_CHECK_SYN;



CREATE OR REPLACE PROCEDURE SP_LAW_YH_INFO_SYN(
        IN_UUID in varchar2,
        IN_LAW_YH_INFO_ID IN VARCHAR2,
        IN_INTENDANCEL_ID IN NUMBER,  -- 执法监督检查id
        IN_FILL_DATE IN VARCHAR2,  -- 隐患发现日期
        IN_IS_DONE IN NUMBER,  -- 是否整改 0:未整改 1：已整改
        IN_TYPE IN VARCHAR2,  -- 隐患类型，就是隐患来源，文书类型
        IN_BOOK_ID IN NUMBER,  -- 文书ID
        IN_CONTENT IN VARCHAR2,  -- 隐患内容
        IN_MANAGEMENT_LEVEL1 IN VARCHAR2,  -- 管理分类1级
        IN_FIRST_AREA IN VARCHAR2,  -- 检查单位所属一级区域
        IN_SECOND_AREA IN VARCHAR2,  -- 检查单位所属二级区域
        IN_THIRD_AREA IN VARCHAR2,  -- 检查单位所属三级区域
        IN_ZF_COMPANY_ID IN NUMBER,  -- 执法企业ID
        IN_XCJCJL_L_ID  IN NUMBER,  -- 现场检查记录l id
        IN_DONE_DATE  IN VARCHAR2,
        IN_IS_DELETED IN NUMBER,
        IN_CREATE_TIME IN VARCHAR2,
        IN_MODIFY_TIME IN VARCHAR2,
        IN_TYPE1 IN VARCHAR2,
        IN_TYPE2 IN VARCHAR2,
        OUT_RESULT out VARCHAR2) is
  /*-----------------------------------------------------------------------------------------------
   --说明：Generated By Genc Code Build Tool
   --修改记录：@date 2014-10-23 8:19:29 zhaozhi3758
  --------------------------------------------------------------------------------------------------*/

  C_TIME_R Date; --当前时间

  SP_NAME CONSTANT VARCHAR2(100) := 'SP_LAW_YH_INFO_SYN';

  TB_NAME CONSTANT VARCHAR2(100) := 'T_LAW_YH_INFO';

  IN_MAIN_PARAM_R VARCHAR2(4000);

  N_ROW NUMBER;

  T_LAW_YH_INFO_ID NUMBER; -- 中心库生成主键

  T_COMPANY_ID NUMBER;

  T_RECORD_STATE S_LAW_YH_INFO_INC.RECORD_STATE%TYPE;

  T_LAW_YH_INFO_CLOB CLOB;

  T_SYN_NO NUMBER;

  T_CREATE_TIME DATE;
  T_MODIFY_TIME DATE;

BEGIN

  IF IN_LAW_YH_INFO_ID  IS NULL THEN
  	 RAISE PKG_CONSTANT.SYN_ERROR_EXCEPTION;
  END IF;

  C_TIME_R := SYSDATE();

  -- 定制错误异常信息
  IN_MAIN_PARAM_R := '' || IN_LAW_YH_INFO_ID || '-------'||  IN_UUID;

  SELECT ID INTO T_COMPANY_ID FROM T_COMPANY WHERE UUID = IN_UUID;

  SELECT COUNT(1) INTO N_ROW FROM T_LAW_YH_INFO T WHERE T.LAW_YH_INFO_ID = IN_LAW_YH_INFO_ID ; -- 定义字段时 以表名为主

  IF N_ROW > 1 THEN
    RAISE PKG_CONSTANT.SYN_MULTI_CONFLICT_EXCEPTION;
  ELSIF N_ROW = 0 THEN
    T_LAW_YH_INFO_ID:=NULL;
  ELSE
    SELECT ID INTO T_LAW_YH_INFO_ID FROM T_LAW_YH_INFO WHERE LAW_YH_INFO_ID=  IN_LAW_YH_INFO_ID ;
  END IF;

  T_CREATE_TIME := NULL;
  IF IN_CREATE_TIME IS NOT NULL THEN
    T_CREATE_TIME := TO_DATE(IN_CREATE_TIME, 'YYYY-MM-DD HH24:MI:SS');
  END IF;

  T_MODIFY_TIME := NULL;
  IF IN_MODIFY_TIME IS NOT NULL THEN
    T_MODIFY_TIME := TO_DATE(IN_MODIFY_TIME, 'YYYY-MM-DD HH24:MI:SS');
  END IF;

  -- 新增、修改表数据
  IF T_LAW_YH_INFO_ID IS NOT NULL THEN
    -- 设置数据状态
    IF IN_IS_DELETED = PKG_CONSTANT.DELETED_YES THEN
      T_RECORD_STATE := PKG_CONSTANT.RECORD_ST_DELETE;
    ELSE
      T_RECORD_STATE := PKG_CONSTANT.RECORD_ST_UPDATE;
    END IF;
    -- 执行修改
    UPDATE T_LAW_YH_INFO
       SET
	        INTENDANCEL_ID=IN_INTENDANCEL_ID,
	        FILL_DATE=TO_DATE(IN_FILL_DATE, 'YYYY-MM-DD HH24:MI:SS'),
          DONE_DATE=TO_DATE(IN_DONE_DATE, 'YYYY-MM-DD HH24:MI:SS'),
	        IS_DONE=IN_IS_DONE,
	        TYPE=IN_TYPE,
	        BOOK_ID=IN_BOOK_ID,
	        CONTENT=IN_CONTENT,
	        MANAGEMENT_LEVEL1=IN_MANAGEMENT_LEVEL1,
	        FIRST_AREA=IN_FIRST_AREA,
	        SECOND_AREA=IN_SECOND_AREA,
	        THIRD_AREA=IN_THIRD_AREA,
	        COMPANY_ID=T_COMPANY_ID,
	        ZF_COMPANY_ID=IN_ZF_COMPANY_ID,
	        IS_DELETED=IN_IS_DELETED,
	        MODIFY_TIME  = NVL(T_MODIFY_TIME, C_TIME_R),
	        LAW_YH_INFO_ID=IN_LAW_YH_INFO_ID,
          XCJCJL_L_ID=IN_XCJCJL_L_ID,
          TYPE1 = IN_TYPE1,
          TYPE2 = IN_TYPE2,
          RECORD_STATE = T_RECORD_STATE
     WHERE ID = T_LAW_YH_INFO_ID;
  ELSE
    -- 执行新增
    T_RECORD_STATE := PKG_CONSTANT.RECORD_ST_INSERT;
    T_LAW_YH_INFO_ID := SEQ_LAW_YH_INFO.NEXTVAL;
    INSERT INTO T_LAW_YH_INFO (
          ID,
	        INTENDANCEL_ID,
	        FILL_DATE,
          DONE_DATE,
	        IS_DONE,
	        TYPE,
	        BOOK_ID,
	        CONTENT,
	        MANAGEMENT_LEVEL1,
	        FIRST_AREA,
	        SECOND_AREA,
	        THIRD_AREA,
	        COMPANY_ID,
	        ZF_COMPANY_ID,
	        IS_DELETED,
	        CREATE_TIME,
	        MODIFY_TIME,
	        LAW_YH_INFO_ID,
          XCJCJL_L_ID,
          TYPE1,
          TYPE2,
          RECORD_STATE
        ) VALUES (
          T_LAW_YH_INFO_ID,
	        IN_INTENDANCEL_ID,
	        TO_DATE(IN_FILL_DATE, 'YYYY-MM-DD HH24:MI:SS'),
          TO_DATE(IN_DONE_DATE, 'YYYY-MM-DD HH24:MI:SS'),
	        IN_IS_DONE,
	        IN_TYPE,
	        IN_BOOK_ID,
	        IN_CONTENT,
	        IN_MANAGEMENT_LEVEL1,
	        IN_FIRST_AREA,
	        IN_SECOND_AREA,
	        IN_THIRD_AREA,
	        T_COMPANY_ID,
	        IN_ZF_COMPANY_ID,
	        IN_IS_DELETED,
	        NVL(T_CREATE_TIME, C_TIME_R),
	        NVL(T_MODIFY_TIME, C_TIME_R),
	        IN_LAW_YH_INFO_ID,
          IN_XCJCJL_L_ID,
          IN_TYPE1,
          IN_TYPE2,
          T_RECORD_STATE);
  END IF;
  OUT_RESULT := PKG_CONSTANT.RESULT_SUCCESS;
  COMMIT;

  -- 产生增量数据
  T_LAW_YH_INFO_CLOB := FUN_LAW_YH_INFO_JSON(T_LAW_YH_INFO_ID);
  T_SYN_NO        := TO_NUMBER(GEN_LAW_YH_INFO_SYN_NO());
  INSERT INTO S_LAW_YH_INFO_INC
    (ID, SYN_NO, SYN_DATA, RECORD_STATE, CREATE_TIME)
  VALUES
    (IN_LAW_YH_INFO_ID, T_SYN_NO, T_LAW_YH_INFO_CLOB, T_RECORD_STATE, C_TIME_R);
  COMMIT;


  UPDATE C_SYN_TBL_INFO
     SET IS_HAS_SYN = 0
   WHERE OP_CODE =PKG_SYN_CONST.SYN_GEN_LAW_YH_INFO_CODE;
  COMMIT;

EXCEPTION
  WHEN PKG_CONSTANT.SYN_MULTI_CONFLICT_EXCEPTION THEN
    OUT_RESULT := PKG_CONSTANT.RESULT_ERROR;
    P_CORRECT_ERROR_SYN_TEMP(TB_NAME,
                             IN_MAIN_PARAM_R,
                             SP_NAME,
                             PKG_CONSTANT.SYN_MULTI_CONFLICT);
  WHEN PKG_CONSTANT.SYN_MATCH_ERROR_EXCEPTION THEN
    OUT_RESULT := PKG_CONSTANT.RESULT_ERROR;
    P_CORRECT_ERROR_SYN_TEMP(TB_NAME,
                             IN_MAIN_PARAM_R,
                             SP_NAME,
                             PKG_CONSTANT.SYN_MATCH_ERROR);
  WHEN OTHERS THEN
    OUT_RESULT := PKG_CONSTANT.RESULT_ERROR;
    P_CORRECT_ERROR_SYN_TEMP(TB_NAME,
                             IN_MAIN_PARAM_R || '-----' || SQLERRM,
                             SP_NAME,
                             PKG_CONSTANT.SYN_ERROR);

END SP_LAW_YH_INFO_SYN;


CREATE OR REPLACE PROCEDURE SP_HIDDEN_UN_SER_SYN(IN_UUID IN VARCHAR2,

                                                 IN_YH_ID               IN NUMBER,
                                                 IN_HIDDEN_DES          IN VARCHAR2,
                                                 IN_HIDDEN_POSITION     IN VARCHAR2,
                                                 IN_PUT_SUPERVISE_LEVEL IN VARCHAR2,
                                                 IN_PLAN_FINISH_TIME    IN VARCHAR2,
                                                 IN_IS_NORMAL_CHANGE    IN VARCHAR2,
                                                 IN_FINISH_TIME         IN VARCHAR2,
                                                 IN_HAPPEN_TIME         IN VARCHAR2,
                                                 IN_RESOLVE_STATE       IN VARCHAR2,
                                                 IN_HIDDEN_NO           IN VARCHAR2,

                                                 IN_FIRST_AREA  IN VARCHAR2,
                                                 IN_SECOND_AREA IN VARCHAR2,
                                                 IN_THIRD_AREA  IN VARCHAR2,
                                                 IN_GORVER_CONTENT IN VARCHAR2,
                                                 IN_HIDDEN_TYPE1 IN VARCHAR2,
                                                 IN_HIDDEN_TYPE2 IN VARCHAR2,

                                                 IN_IS_DELETED IN INTEGER,

                                                 IN_CREATE_TIME IN VARCHAR2,
                                                 IN_MODIFY_TIME IN VARCHAR2,

                                                 OUT_RESULT out VARCHAR2) is
  /*---------------------------------------------------------------------------------------------------
   --功能：同步  未治理重大隐患信息
   --调用者：
   --说明：
   --修改记录：2013-XX-XX by maomj
   --最后版本：
  --------------------------------------------------------------------------------------------------*/
  C_TIME_R Date; --当前时间

  SP_NAME CONSTANT VARCHAR2(100) := 'SP_HIDDEN_UN_SER_SYN';

  TB_NAME CONSTANT VARCHAR2(100) := 'T_HIDDEN_UN_SERIOUS';

  IN_MAIN_PARAM_R VARCHAR2(4000); --主要参数记录

  N_ROW NUMBER;

  T_COMPANY_ID NUMBER;

  T_HIDDEN_UN_SER_ID NUMBER;

  T_RECORD_STATE S_HIDDEN_UN_SER_INC.RECORD_STATE%TYPE;

  T_HIDDEN_UN_SER_CLOB CLOB;

  T_SYN_NO NUMBER;

  T_CREATE_TIME DATE;
  T_MODIFY_TIME DATE;

BEGIN
  IF IN_YH_ID IS NULL THEN
    RAISE PKG_CONSTANT.SYN_ERROR_EXCEPTION;
  END IF;
  C_TIME_R := SYSDATE();

  IN_MAIN_PARAM_R := '''' || IN_UUID || ''',''' || IN_YH_ID || '''';
  SELECT ID INTO T_COMPANY_ID FROM T_COMPANY WHERE UUID = IN_UUID;

  --是否存在
  SELECT COUNT(1)
    INTO N_ROW
    FROM T_HIDDEN_UN_SERIOUS T
   WHERE T.YH_ID = IN_YH_ID;

  IF N_ROW > 1 THEN
    RAISE PKG_CONSTANT.SYN_MULTI_CONFLICT_EXCEPTION;
  ELSIF N_ROW = 0 THEN
    T_HIDDEN_UN_SER_ID := NULL;
  ELSE
    SELECT ID
      INTO T_HIDDEN_UN_SER_ID
      FROM T_HIDDEN_UN_SERIOUS
     WHERE YH_ID = IN_YH_ID;
  END IF;

  T_CREATE_TIME := NULL;
  IF IN_CREATE_TIME IS NOT NULL THEN
    T_CREATE_TIME := TO_DATE(IN_CREATE_TIME, 'yyyy-mm-dd hh24:mi:ss');
  END IF;

  T_MODIFY_TIME := NULL;
  IF IN_MODIFY_TIME IS NOT NULL THEN
    T_MODIFY_TIME := TO_DATE(IN_MODIFY_TIME, 'yyyy-mm-dd hh24:mi:ss');
  END IF;

  IF T_HIDDEN_UN_SER_ID IS NOT NULL THEN
    IF IN_IS_DELETED = PKG_CONSTANT.DELETED_YES THEN
      T_RECORD_STATE := PKG_CONSTANT.RECORD_ST_DELETE;
    ELSE
      T_RECORD_STATE := PKG_CONSTANT.RECORD_ST_UPDATE;
    END IF;

    UPDATE T_HIDDEN_UN_SERIOUS
       SET HIDDEN_DES          = IN_HIDDEN_DES,
           HIDDEN_POSITION     = IN_HIDDEN_POSITION,
           PUT_SUPERVISE_LEVEL = IN_PUT_SUPERVISE_LEVEL,
           PLAN_FINISH_TIME    = TO_DATE(IN_PLAN_FINISH_TIME,
                                         'YYYY-MM-DD HH24:MI:SS'),
           IS_NORMAL_CHANGE    = IN_IS_NORMAL_CHANGE,
           FINISH_TIME         = TO_DATE(IN_FINISH_TIME,
                                         'YYYY-MM-DD HH24:MI:SS'),
           HAPPEN_TIME         = TO_DATE(IN_HAPPEN_TIME,
                                         'YYYY-MM-DD HH24:MI:SS'),
           RESOLVE_STATE       = IN_RESOLVE_STATE,
           HIDDEN_NO           = IN_HIDDEN_NO,

           FIRST_AREA  = IN_FIRST_AREA,
           SECOND_AREA = IN_SECOND_AREA,
           THIRD_AREA  = IN_THIRD_AREA,
           GORVER_CONTENT = IN_GORVER_CONTENT,
           HIDDEN_TYPE1 = IN_HIDDEN_TYPE1,
           HIDDEN_TYPE2 = IN_HIDDEN_TYPE2,
           COMPANY_ID   = T_COMPANY_ID,
           IS_DELETED   = IN_IS_DELETED,
           RECORD_STATE = T_RECORD_STATE,
           MODIFY_TIME  = NVL(T_MODIFY_TIME, C_TIME_R)
     WHERE ID = T_HIDDEN_UN_SER_ID;
  ELSE

    T_RECORD_STATE := PKG_CONSTANT.RECORD_ST_INSERT;
    INSERT INTO T_HIDDEN_UN_SERIOUS
      (ID,
       HIDDEN_DES,
       HIDDEN_POSITION,
       PUT_SUPERVISE_LEVEL,
       PLAN_FINISH_TIME,
       IS_NORMAL_CHANGE,
       FINISH_TIME,
       HAPPEN_TIME,
       COMPANY_ID,

       YH_ID,
       RESOLVE_STATE,
       HIDDEN_NO,

       FIRST_AREA,
       SECOND_AREA,
       THIRD_AREA,
       GORVER_CONTENT,
       HIDDEN_TYPE1,
       HIDDEN_TYPE2,
       RECORD_STATE,
       IS_DELETED,
       CREATE_TIME,
       MODIFY_TIME)
    VALUES
      (SEQ_HIDDEN.NEXTVAL,
       IN_HIDDEN_DES,
       IN_HIDDEN_POSITION,
       IN_PUT_SUPERVISE_LEVEL,

       TO_DATE(IN_PLAN_FINISH_TIME, 'YYYY-MM-DD HH24:MI:SS'),
       IN_IS_NORMAL_CHANGE,

       TO_DATE(IN_FINISH_TIME, 'YYYY-MM-DD HH24:MI:SS'),

       TO_DATE(IN_HAPPEN_TIME, 'YYYY-MM-DD HH24:MI:SS'),
       T_COMPANY_ID,

       IN_YH_ID,
       IN_RESOLVE_STATE,
       IN_HIDDEN_NO,

       IN_FIRST_AREA,
       IN_SECOND_AREA,
       IN_THIRD_AREA,
       IN_GORVER_CONTENT,
       IN_HIDDEN_TYPE1,
       IN_HIDDEN_TYPE2,

       T_RECORD_STATE,
       IN_IS_DELETED,
       NVL(T_CREATE_TIME, C_TIME_R),
       NVL(T_MODIFY_TIME, C_TIME_R));
  END IF;
  OUT_RESULT := PKG_CONSTANT.RESULT_SUCCESS;
  COMMIT;

  --产生增量数据
  T_HIDDEN_UN_SER_CLOB := FUN_HIDDEN_UN_SER_JSON(IN_YH_ID);
  T_SYN_NO             := TO_NUMBER(GEN_HIDDEN_UN_SER_SYN_NO());
  INSERT INTO S_HIDDEN_UN_SER_INC
    (ID, SYN_NO, SYN_DATA, RECORD_STATE, CREATE_TIME)
  VALUES
    (IN_YH_ID, T_SYN_NO, T_HIDDEN_UN_SER_CLOB, T_RECORD_STATE, C_TIME_R);
  COMMIT;

  --更新同步表信息;
  UPDATE C_SYN_TBL_INFO
     SET IS_HAS_SYN = 0
   WHERE OP_CODE = PKG_SYN_CONST.SYN_GEN_HIDDEN_UN_SER_CODE;
  COMMIT;

EXCEPTION
  WHEN PKG_CONSTANT.SYN_MULTI_CONFLICT_EXCEPTION THEN
    OUT_RESULT := PKG_CONSTANT.RESULT_ERROR;
    P_CORRECT_ERROR_SYN_TEMP(TB_NAME,
                             IN_MAIN_PARAM_R,
                             SP_NAME,
                             PKG_CONSTANT.SYN_MULTI_CONFLICT);
  WHEN PKG_CONSTANT.SYN_MATCH_ERROR_EXCEPTION THEN
    OUT_RESULT := PKG_CONSTANT.RESULT_ERROR;
    P_CORRECT_ERROR_SYN_TEMP(TB_NAME,
                             IN_MAIN_PARAM_R,
                             SP_NAME,
                             PKG_CONSTANT.SYN_MATCH_ERROR);
  WHEN OTHERS THEN
    OUT_RESULT := PKG_CONSTANT.RESULT_ERROR;
    P_CORRECT_ERROR_SYN_TEMP(TB_NAME,
                             IN_MAIN_PARAM_R || '-----' || SQLERRM,
                             SP_NAME,
                             PKG_CONSTANT.SYN_ERROR);

END SP_HIDDEN_UN_SER_SYN;
