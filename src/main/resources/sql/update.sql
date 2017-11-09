update da_industry_parameter t set t.name= '商务' where  t.code = 'maoyi';
update  da_statistic t set t.qtrc_num  = 0 where t.s_type = 2 and t.qtrc_num is null;
update fk_enum t set t.enum_name = '商务委' where t.enum_code = 'maoyi';
update fk_role t set t.role_name = '商务部门' where t.role_code = 'ROLE_MAOYI';
update fk_role t set t.role_name = '县级商务部门' where t.role_code = 'ROLE_MAOYI_CITY';
update department t set t.name = '商务', t.city_level = '商务委' where t.code = 'maoyi';

-----------------------------------updated 2015.11.20------------------
-- Create sequence 
create sequence S_DA_DANGER_IMAGE
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

create table DA_DANGER_IMAGE
(
  ID                 NUMBER(19) not null,
  NAME               VARCHAR2(200),
  PATH               VARCHAR2(200),
  DA_NOMAL_DANGER_ID NUMBER(19),
  DA_DANGER_ID       NUMBER(19),
  USER_ID            NUMBER(19),
  CREATE_TIME        DATE,
  MODIFY_TIME        DATE,
  IS_DELETED         INTEGER default 0
);


create table T_CLIENT_AUTH_KEY
(
  ID          NUMBER(10) not null,
  USER_ID     NUMBER(10),
  STATUS      VARCHAR2(200),
  AUTH_KEY    VARCHAR2(200),
  ACTION_DATE DATE,
  CREATE_TIME DATE,
  modity_time DATE,
  is_deleted     NUMBER(2)
);

-- Create sequence 
create sequence S_T_CLIENT_AUTH_KEY
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;

create table T_CLIENT_CHANGE_LOG
(
  ID              NUMBER(19) not null,
  PACKAGE_EN_NAME VARCHAR2(255),
  PACKAGE_ZH_NAME VARCHAR2(255),
  VERSION_CODE    VARCHAR2(255),
  VERSION_NUM     NUMBER(10),
  PUBLISH_DATE    DATE,
  CHANGE_LOG      VARCHAR2(2000),
  FILE_INFO       VARCHAR2(255),
  OS              VARCHAR2(255),
  Type            VARCHAR2(255),
  ORIGNAL_FILE_NAME VARCHAR2(200),
  CREATE_TIME DATE,
  MODITY_TIME DATE,
  IS_DELETED  NUMBER(2)
);


-- Create table
create table T_HIDDEN_SUMMARY
(
  ID            NUMBER(19) not null,
  HIDDEN_DATE   DATE,
  DESCRIPTION   VARCHAR2(2000),
  IS_COMPLETED   NUMBER(1),
  COMPLETE_DATE DATE,
  HIDDEN_LEVEL  VARCHAR2(100),
  HIDDEN_SOURCE VARCHAR2(50),
  TYPE_1        VARCHAR2(50),
  TYPE_2        VARCHAR2(50),
  COMPANY_ID    NUMBER(19),
  COMPANY_NAME  VARCHAR2(500),
  UUID          VARCHAR2(50),
  FROM_SYS      VARCHAR2(50),
  FROM_TABLE	VARCHAR2(50),
  FROM_ID       VARCHAR2(50),
  FIRST_AREA    VARCHAR2(200) default 0,
  SECOND_AREA   VARCHAR2(100) default 0,
  THIRD_AREA    VARCHAR2(100) default 0,
  FOURTH_AREA   VARCHAR2(100),
  FIFTH_AREA    VARCHAR2(100),
  TEMP1         VARCHAR2(50),
  TEMP2         VARCHAR2(1000),
  CREATE_TIME   DATE,
  MODITY_TIME   DATE,
  IS_DELETED    NUMBER(1)
);
-- Add comments to the columns 
comment on column T_HIDDEN_SUMMARY.HIDDEN_DATE
  is '隐患发现时间';
comment on column T_HIDDEN_SUMMARY.DESCRIPTION
  is '隐患描述';
comment on column T_HIDDEN_SUMMARY.IS_COMPLETE
  is '是否整改完成';
comment on column T_HIDDEN_SUMMARY.COMPLETE_DATE
  is '整改完成日期';
comment on column T_HIDDEN_SUMMARY.HIDDEN_LEVEL
  is '隐患等级:一般隐患 重大隐患';
comment on column T_HIDDEN_SUMMARY.HIDDEN_SOURCE
  is '隐患来源';
comment on column T_HIDDEN_SUMMARY.TYPE_1
  is '隐患分类1';
comment on column T_HIDDEN_SUMMARY.TYPE_2
  is '隐患分类2';
comment on column T_HIDDEN_SUMMARY.COMPANY_ID
  is '中心库企业ID';
comment on column T_HIDDEN_SUMMARY.FROM_SYS
  is '数据来自哪个业务系统';
comment on column T_HIDDEN_SUMMARY.FROM_TABLE
  is '来自中心库的哪个表';
comment on column T_HIDDEN_SUMMARY.FROM_ID
  is '中心库fromTable表数据ID';
comment on column T_HIDDEN_SUMMARY.UUID
  is '中心库企业UUID';
alter table T_HIDDEN_SUMMARY
  add constraint T_HIDDEN_SUMMARY_PK primary key (ID);
-- Create/Recreate indexes 
create index HIDDEN_SUMMARY_SOURCE on T_HIDDEN_SUMMARY (HIDDEN_SOURCE);
create index HIDDEN_SUMMARY_TYPE1 on T_HIDDEN_SUMMARY (TYPE_1);
create index HIDDEN_SUMMARY_TYPE2 on T_HIDDEN_SUMMARY (TYPE_2);

  
-- Create sequence 
create sequence S_HIDDEN_SUMMARY
minvalue 1
maxvalue 9999999999999999999999999999
start with 1
increment by 1
cache 20;


alter table da_normal_danger add is_from_app number(1);
update da_normal_danger set is_from_app = 0;--更新达标，分批更新

create or replace view vdanger as
select t.id  as  yh_id  ,t.description,t.danger_add,
t.id,
  t.danger_no,
  d.uuid,
  d.uuid  as  uuid1,
   d.first_area,
       d.second_area,
       d.third_area,
       t.azard_happen_time  as happen_time,
       t.create_time,
       t.modify_time,
       g.finish_date as finish_date,
       t.finish_date as plan_date,
       t.is_deleted,
       t.is_synchro,
       case  when    g.finish_date is not null and  g.finish_date<= t.finish_date    then  1 else  0 end as IS_NORMAL_CHANGE,
       g.id  as gid,
       case  when  g.id is not  null  then  1 else  0 end  as  RESOLVE_STATE,
       g.gorver_content,
       p1.code hidden_type1,
       p2.code hidden_type2
    from   DA_DANGER t
  left join da_company  d  on d.id=t.par_da_com_id
   --left join t_company  c  on c.Company_Name=d.company_name
   left join DA_DANGER_GORVER g  on g.par_da_dan_id=t.id
   --重大隐患关联多个隐患分类，按照人 物 管理 优先级进行获取
   left join (
        select dip.code, v.par_da_dan_id, v.par_da_ind_id from (
        	--min硬编码，认为人物管理的ID从小到大
          select min(r.par_da_ind_id) par_da_ind_id, r.par_da_dan_id from DA_DANGER_TYPE_REL r 
           group by r.par_da_dan_id
        ) v  left join da_industry_parameter dip on dip.id = v.par_da_ind_id
        where dip.is_deleted = 0
   ) p1 on p1.par_da_dan_id = t.id
   left join (
        select dip.code, r.par_da_dan_id, dip.par_da_ind_id from DA_DANGER_SECOND_TYPE_REL r
         left join da_industry_parameter dip on dip.id = r.PAR_DA_SECOND_IND_ID
          where dip.is_deleted = 0
   ) p2 on (p2.par_da_dan_id = t.id and p2.par_da_ind_id = p1.par_da_ind_id)
   

   
权限配置 隐患汇总统计 注意 URL那边 需要加一个空格


etl_syn_info 增加3个表T_HIDDEN_UN_SERIOUS，T_LAW_YH_INFO，T_HIDDEN_INFO 的记录和 增量号