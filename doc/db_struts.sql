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
   nick_name            varchar(50) comment '备注名字',
   accept_date          datetime comment '接收请求时间',
   req_status           int comment '接收状态,0:末处理,-1:refus,1:accept',
   create_date          datetime,
   primary key (to_user, friend_user)
);

alter table chat_friend comment '好友';

/*==============================================================*/
/* Table: chat_group                                            */
/*==============================================================*/
create table chat_group
(
   id                   int not null auto_increment,
   group_name           varchar(50) comment '名称',
   note                 varchar(512) comment '公告',
   remarks              varchar(512) comment '描述',
   status               int comment '状态,0:正常,-1删除',
   group_level          int comment '群组等级',
   own_user             varchar(128) comment '拥有者，创建者',
   create_date          datetime,
   primary key (id)
);

alter table chat_group comment '群组';

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

alter table chat_group_member comment '群组成员';

/*==============================================================*/
/* Table: chat_message                                          */
/*==============================================================*/
create table chat_message
(
   id                   int not null auto_increment,
   content              varchar(1024) comment '消息',
   group_id             int comment '群组id，可为空',
   to_user              varchar(128) comment '接收者',
   send_from            varchar(128) comment '发送者',
   status               int comment '状态，0 末读,1:已读',
   rec_date             datetime,
   send_date            datetime,
   primary key (id)
);

alter table chat_message comment '聊天消息';

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

alter table conn_session comment '客户端连接';

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
   mtype                int comment '类型',
   mkey                 varchar(50) comment '名称',
   text                 varchar(128) comment '取值',
   flag                 int comment '标识状态',
   create_date          datetime,
   primary key (id)
);

alter table sys_dict comment '系统字典';

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

alter table sys_log comment '系统跟踪日志';

/*==============================================================*/
/* Table: sys_message                                           */
/*==============================================================*/
create table sys_message
(
   id                   int not null auto_increment,
   msg_type             int comment '消息类型，区别',
   content              varchar(1024) comment '消息内容',
   status               int comment '状态0:未读，1：已读',
   send_to              varchar(128) comment '接收者',
   send_from            varchar(128) comment '发送者',
   create_date          datetime comment '创建时间',
   read_date            datetime comment '阅读时间',
   primary key (id)
);

alter table sys_message comment '消息';

/*==============================================================*/
/* Table: sys_role                                              */
/*==============================================================*/
create table sys_role
(
   id                   int not null auto_increment,
   authority            varchar(100) comment '角色名',
   primary key (id)
);

alter table sys_role comment '角色';

/*==============================================================*/
/* Table: sys_user                                              */
/*==============================================================*/
create table sys_user
(
   id                   varchar(128) not null,
   account_name         varchar(20) comment '用户名',
   pwd                  varchar(128),
   mobile               varchar(20) comment '电话',
   sex                  int comment '性别',
   province             varchar(20) comment '省份',
   city                 varchar(50) comment '城市',
   user_level           int comment '用户等级',
   user_money           decimal(10,2) comment '用户金额',
   avatar               varchar(512) comment '用户头像',
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
   reg_from             varchar(128) comment '注册方式',
   status               int comment '逻辑删除标记',
   birthday             varchar(10) comment '生日',
   nickname             varchar(20) comment '昵称',
   primary key (id)
);

alter table sys_user comment '账号';

/*==============================================================*/
/* Table: sys_user_role                                         */
/*==============================================================*/
create table sys_user_role
(
   user_id              varchar(128) not null comment '用户id',
   role_id              int not null comment '角色id',
   authority_data       varchar(512),
   primary key (user_id, role_id)
);

alter table sys_user_role comment '用户角色关联表';

