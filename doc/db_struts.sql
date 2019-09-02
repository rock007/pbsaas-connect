/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2017/5/15 15:28:13                           */
/*==============================================================*/


drop table if exists chat_friend;

drop table if exists chat_group;

drop table if exists chat_group_member;

drop table if exists chat_message;

drop table if exists conn_session;

drop table if exists oauth_access_token;

drop table if exists oauth_refresh_token;

drop table if exists sys_dict;

drop table if exists sys_log;

drop table if exists sys_message;

drop table if exists sys_role;

drop table if exists sys_user;

drop table if exists sys_user_role;

/*==============================================================*/
/* Table: chat_friend                                           */
/*==============================================================*/
create table chat_friend
(
   to_user              varchar(128) not null,
   friend_user          varchar(128) not null,
   nick_name            varchar(50) comment '��ע����',
   accept_date          datetime comment '��������ʱ��',
   req_status           int comment '����״̬,0:ĩ����,-1:refus,1:accept',
   create_date          datetime,
   primary key (to_user, friend_user)
);

alter table chat_friend comment '����';

/*==============================================================*/
/* Table: chat_group                                            */
/*==============================================================*/
create table chat_group
(
   id                   int not null auto_increment,
   group_name           varchar(50) comment '����',
   note                 varchar(512) comment '����',
   remarks              varchar(512) comment '����',
   status               int comment '״̬,0:����,-1ɾ��',
   group_level          int comment 'Ⱥ��ȼ�',
   own_user             varchar(128) comment 'ӵ���ߣ�������',
   create_date          datetime,
   primary key (id)
);

alter table chat_group comment 'Ⱥ��';

/*==============================================================*/
/* Table: chat_group_member                                     */
/*==============================================================*/
create table chat_group_member
(
   group_id             int not null,
   user_id              varchar(128) not null,
   nick_name            varchar(50),
   primary key (group_id, user_id)
);

alter table chat_group_member comment 'Ⱥ���Ա';

/*==============================================================*/
/* Table: chat_message                                          */
/*==============================================================*/
create table chat_message
(
   id                   int not null auto_increment,
   content              varchar(1024) comment '��Ϣ',
   group_id             int comment 'Ⱥ��id����Ϊ��',
   to_user              varchar(128) comment '������',
   send_from            varchar(128) comment '������',
   status               int comment '״̬��0 ĩ��,1:�Ѷ�',
   rec_date             datetime,
   send_date            datetime,
   primary key (id)
);

alter table chat_message comment '������Ϣ';

/*==============================================================*/
/* Table: conn_session                                          */
/*==============================================================*/
create table conn_session
(
   uid                  varchar(128) not null,
   user_id              varchar(128),
   ip                   varchar(50),
   ticket               varchar(128),
   create_date          datetime,
   primary key (uid)
);

alter table conn_session comment '�ͻ�������';

/*==============================================================*/
/* Table: oauth_access_token                                    */
/*==============================================================*/
create table oauth_access_token
(
   token_id             varchar(128) not null,
   token                blob,
   authentication_id    varchar(256),
   user_name            varchar(256),
   client_id            varchar(256),
   authentication       blob,
   refresh_token        varchar(256),
   primary key (token_id)
);

/*==============================================================*/
/* Table: oauth_refresh_token                                   */
/*==============================================================*/
create table oauth_refresh_token
(
   token_id             varchar(128) not null,
   token                blob,
   authentication       blob,
   primary key (token_id)
);

/*==============================================================*/
/* Table: sys_dict                                              */
/*==============================================================*/
create table sys_dict
(
   id                   int not null auto_increment,
   mtype                int comment '����',
   mkey                 varchar(50) comment '����',
   text                 varchar(128) comment 'ȡֵ',
   flag                 int comment '��ʶ״̬',
   create_date          datetime,
   primary key (id)
);

alter table sys_dict comment 'ϵͳ�ֵ�';

/*==============================================================*/
/* Table: sys_log                                               */
/*==============================================================*/
create table sys_log
(
   id                   int not null auto_increment,
   title                varchar(256),
   msg                  varchar(512),
   content              varchar(2048),
   error                text,
   create_user          varchar(128),
   create_ip            varchar(50),
   create_date          date,
   primary key (id)
);

alter table sys_log comment 'ϵͳ������־';

/*==============================================================*/
/* Table: sys_message                                           */
/*==============================================================*/
create table sys_message
(
   id                   int not null auto_increment,
   msg_type             int comment '��Ϣ���ͣ�����',
   content              varchar(1024) comment '��Ϣ����',
   status               int comment '״̬0:δ����1���Ѷ�',
   send_to              varchar(128) comment '������',
   send_from            varchar(128) comment '������',
   create_date          datetime comment '����ʱ��',
   read_date            datetime comment '�Ķ�ʱ��',
   primary key (id)
);

alter table sys_message comment '��Ϣ';

/*==============================================================*/
/* Table: sys_role                                              */
/*==============================================================*/
create table sys_role
(
   id                   int not null auto_increment,
   authority            varchar(100) comment '��ɫ��',
   primary key (id)
);

alter table sys_role comment '��ɫ';

/*==============================================================*/
/* Table: sys_user                                              */
/*==============================================================*/
create table sys_user
(
   id                   varchar(128) not null,
   account_name         varchar(20) comment '�û���',
   pwd                  varchar(128),
   mobile               varchar(20) comment '�绰',
   sex                  int comment '�Ա�',
   province             varchar(20) comment 'ʡ��',
   city                 varchar(50) comment '����',
   user_level           int comment '�û��ȼ�',
   user_money           decimal(10,2) comment '�û����',
   avatar               varchar(512) comment '�û�ͷ��',
   create_date          date,
   last_date            date,
   last_ipaddress       varchar(20),
   qq_openid            varchar(128),
   qq_token             varchar(128),
   qq_nickname          varchar(128),
   wx_openid            varchar(128),
   wx_token             varchar(128),
   wx_nickname          varchar(128),
   wb_openid            varchar(128),
   wb_token             varchar(128),
   wb_nickname          varchar(128),
   reg_from             varchar(128) comment 'ע�᷽ʽ',
   status               int comment '�߼�ɾ�����',
   birthday             varchar(10) comment '����',
   nickname             varchar(20) comment '�ǳ�',
   primary key (id)
);

alter table sys_user comment '�˺�';

/*==============================================================*/
/* Table: sys_user_role                                         */
/*==============================================================*/
create table sys_user_role
(
   user_id              varchar(128) not null comment '�û�id',
   role_id              int not null comment '��ɫid',
   authority_data       varchar(512),
   primary key (user_id, role_id)
);

alter table sys_user_role comment '�û���ɫ������';

