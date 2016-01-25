/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     2015/2/12 15:14:06                           */
/*==============================================================*/



/*==============================================================*/
/* Table: bank_define                                           */
/*==============================================================*/
create table bank_define
(
   bank_code            char(3) not null comment '���б���',
   bank_name            varchar(150) comment '��������',
   is_avaliable         char default '0' comment '�Ƿ����(1:�ǣ�0����)',
   primary key (bank_code)
);

alter table bank_define comment '������Ϣ��';

/*==============================================================*/
/* Table: login_log                                             */
/*==============================================================*/
create table login_log
(
   login_log_id         int not null auto_increment comment '����',
   user_code            int comment '�û�����',
   login_time           timestamp comment '��¼ʱ��',
   primary key (login_log_id)
);

/*==============================================================*/
/* Index: idx_login_log_user_code                               */
/*==============================================================*/
create index idx_login_log_user_code on login_log
(
   user_code
);

/*==============================================================*/
/* Table: product_detail                                        */
/*==============================================================*/
create table product_detail
(
   product_detail_id    int not null auto_increment comment '����',
   product_code         varchar(20) comment '��Ʒ����',
   original_total_amount decimal(16,2) comment 'ԭʼ�ܿ�Ͷ���',
   current_total_amount decimal(16,2) comment '��ǰ��Ͷ���',
   lock_start_time      timestamp comment '��Ʒ������ʼʱ��',
   lock_end_time        timestamp comment '��Ʒ��������ʱ��',
   product_detail_desc  varchar(300) comment '��Ʒ��Ϣ����',
   created_Date         timestamp comment '����ʱ��',
   updated_date         timestamp comment '�޸�ʱ��',
   created_by           varchar(150) comment '������',
   updated_by           varchar(150) comment '������',
   primary key (product_detail_id)
);

alter table product_detail comment '��Ʒ��ϸ(һ������¾����ծȨ��Ϣ��product_info���¼����������Ϣ)';

/*==============================================================*/
/* Table: product_info                                          */
/*==============================================================*/
create table product_info
(
   product_code         varchar(20) not null comment '��Ʒ����',
   product_name         varchar(20) comment '��Ʒ����',
   total_amount         decimal(16,2) comment '��Ʒ����ܶ�',
   product_desc         varchar(300) comment '��Ʒ����',
   bid_start_time       timestamp comment '��ƷͶ�꿪ʼʱ��',
   bid_end_time         timestamp comment '��ƷͶ�����ʱ��',
   lock_start_time      timestamp comment '��Ʒ������ʼʱ��',
   lock_end_time        timestamp comment '��Ʒ��������ʱ��',
   predicate_rate_min   decimal(5,4) comment 'Ԥ�������������',
   predicate_rate_max   decimal(5,4) comment 'Ԥ�������������',
   created_date         timestamp comment '����ʱ��',
   updated_date         timestamp comment '�޸�ʱ��',
   created_by           varchar(20) comment '������',
   updated_by           varchar(20) comment '������',
   primary key (product_code)
);

alter table product_info comment '��Ʒ��Ϣ';

/*==============================================================*/
/* Table: product_rate                                          */
/*==============================================================*/
create table product_rate
(
   product_code         varchar(20) comment '��Ʒ����',
   rate_effective_date  timestamp comment '��Ч����',
   rate_invalide_date   timestamp,
   rate                 decimal(5,4) comment '����',
   created_date         timestamp comment '����ʱ��',
   updated_date         timestamp comment '�޸�ʱ��'
);

alter table product_rate comment '��Ʒ����';

/*==============================================================*/
/* Index: idx_product_rate_prod_code                            */
/*==============================================================*/
create index idx_product_rate_prod_code on product_rate
(
   product_code
);

/*==============================================================*/
/* Table: product_reward                                        */
/*==============================================================*/
create table product_reward
(
   product_reward_id    int not null auto_increment comment '����',
   product_code         varchar(20) comment '��Ʒ����',
   reword_code          varchar(20) comment '��������',
   original_amount      decimal(16,2) comment '��Ʒ��Ӧ����ԭʼ���',
   current_amount       decimal(16,2) comment '��Ʒ������ǰ���ý��',
   primary key (product_reward_id)
);

alter table product_reward comment '��ƷͶ�꽱��';

/*==============================================================*/
/* Index: idx_product_reward_prod_code                          */
/*==============================================================*/
create index idx_product_reward_prod_code on product_reward
(
   product_code
);

/*==============================================================*/
/* Index: idx_product_reward_reward_code                        */
/*==============================================================*/
create index idx_product_reward_reward_code on product_reward
(
   reword_code
);

/*==============================================================*/
/* Table: product_rule                                          */
/*==============================================================*/
create table product_rule
(
   product_code         varchar(20) comment '��Ʒ����',
   invest_amount_min    decimal(16,2) comment '��СͶ�ʶ�',
   invest_amount_max    decimal(16,2) comment '���Ͷ�ʶ�',
   per_copy_amount      decimal(16,2) comment 'ÿ�ݿ�Ͷ���(Ԫ/��)',
   effective_date       timestamp comment '��Ч����',
   invidate_date        timestamp comment 'ʧЧ����'
);

alter table product_rule comment '��Ʒ����';

/*==============================================================*/
/* Index: idx_product_rule_prod_code                            */
/*==============================================================*/
create index idx_product_rule_prod_code on product_rule
(
   product_code
);

/*==============================================================*/
/* Table: report_business_statistical                           */
/*==============================================================*/
create table report_business_statistical
(
   statistical_date     timestamp not null comment 'ͳ������',
   total_deposit_amount decimal(16,2) comment '������ת����',
   total_withdraw_amount decimal(16,2) comment '������ת�����',
   total_interest       decimal(16,4) comment '����������',
   primary key (statistical_date)
);

alter table report_business_statistical comment 'ҵ��ͳ�Ʊ���
';

/*==============================================================*/
/* Table: reward_define                                         */
/*==============================================================*/
create table reward_define
(
   reward_code          varchar(20) not null comment '����',
   reward_name          varchar(150) comment '����',
   type                 varchar(4) comment '���ͣ�0001:�ֽ�ȯ 0002:��Ϣȯ��',
   amount               decimal(16,2) comment '�����Ľ��',
   reward_desc          varchar(20) comment '����',
   effective_days       int comment '��Ч����',
   effective_date       timestamp comment '��Ч�ڿ�ʼʱ��',
   invilate_date        timestamp comment 'ʧЧʱ�䣨��Ч�ڽ���ʱ�䣩',
   primary key (reward_code)
);

alter table reward_define comment '��Ʒ�����';

/*==============================================================*/
/* Table: third_platform                                        */
/*==============================================================*/
create table third_platform
(
   platform_id          char(2) not null comment 'ƽ̨id',
   platform_name        varchar(100) comment 'ƽ̨����',
   is_available         char default '0' comment '�Ƿ����(1:�� 0:��)',
   primary key (platform_id)
);

alter table third_platform comment '������ƽ̨';

/*==============================================================*/
/* Table: third_trade_log                                       */
/*==============================================================*/
create table third_trade_log
(
   ttl_id               int not null auto_increment comment '����',
   order_id             varchar(32) comment '������',
   platform_id          char(2) comment 'ƽ̨id��01��֧���� 02����Ǯ��',
   request_data         text comment '���͸�������ƽ̨������',
   response_data        text comment '������ƽ̨���ص�����',
   response_status      varchar(3) comment '������ƽ̨���ص�״̬��1���ɹ� 0��ʧ�ܣ�',
   created_date         timestamp comment '����ʱ��',
   updated_date         timestamp comment '����ʱ��',
   primary key (ttl_id)
);

alter table third_trade_log comment '������ƽ̨������ˮ��¼';

/*==============================================================*/
/* Table: user                                                  */
/*==============================================================*/
create table user
(
   user_code            int not null auto_increment comment '�û�����',
   user_name            varchar(150) comment '�û���',
   mobile_number        varchar(15) comment '�ֻ���',
   device_id            varchar(100) comment '�豸��',
   login_password       varchar(32) comment '��¼����(md5����)',
   hand_password        varchar(32) comment '��������(md5����)',
   created_date         timestamp comment '����ʱ��',
   updated_date         timestamp comment '�޸�ʱ��',
   primary key (user_code)
);

alter table user comment '��Ա��Ϣ��';

/*==============================================================*/
/* Index: idx_user_name                                         */
/*==============================================================*/
create index idx_user_name on user
(
   user_code
);

/*==============================================================*/
/* Index: idx_mobile_number                                     */
/*==============================================================*/
create index idx_mobile_number on user
(
   mobile_number
);

/*==============================================================*/
/* Table: user_account                                          */
/*==============================================================*/
create table user_account
(
   user_code            int comment '�û�����',
   amount               decimal(16,2) comment '�û��˺����',
   total_interest       decimal(16,4) comment '��Ϣ������',
   investing_amount     decimal(16,2) comment '��Ͷ���',
   lock_amount          decimal(16,2) comment '�������'
);

alter table user_account comment '�û��˻���Ϣ';

/*==============================================================*/
/* Index: idx_user_account_user_code                            */
/*==============================================================*/
create index idx_user_account_user_code on user_account
(
   user_code
);

/*==============================================================*/
/* Table: user_bind_card                                        */
/*==============================================================*/
create table user_bind_card
(
   user_code            int comment '�û�����',
   bank_card_number     varchar(25) comment '���п���',
   bank_code            char(3) comment '���б��',
   reserve_mobile       varchar(15) comment 'Ԥ���ֻ���',
   short_number         varchar(10) comment '�̿���(��Ǯ֧��ʹ��)'
);

alter table user_bind_card comment '�����п���Ϣ';

/*==============================================================*/
/* Table: user_detail                                           */
/*==============================================================*/
create table user_detail
(
   user_code            int comment '�û�����',
   certificate_type     char(2) comment '֤������(01:���֤)',
   certificate_number   varchar(20) comment '֤������',
   real_name            varchar(150) comment '����',
   pay_password         varchar(32) comment '֧������(md5)',
   is_mobile_authen     char default '0' comment '�ֻ��Ƿ���֤(1:��0:��)',
   mobile_authen_time   timestamp comment '�ֻ���֤ʱ��',
   is_name_authen       char default '0' comment '����Ƿ���֤',
   name_authen_time     timestamp comment '�����֤ʱ��',
   register_time        timestamp comment 'ע��ʱ��'
);

alter table user_detail comment '��֤��Ϣ��';

/*==============================================================*/
/* Index: idx_user_detail_user_code                             */
/*==============================================================*/
create index idx_user_detail_user_code on user_detail
(
   user_code
);

/*==============================================================*/
/* Table: user_interest_detail                                  */
/*==============================================================*/
create table user_interest_detail
(
   user_product_id      int comment '����',
   actual_interest      decimal(16,2) comment 'ʵ��������',
   actual_interest_rate decimal(16,2) comment 'ʵ��������',
   created_date         timestamp comment '����ʱ��',
   updated_date         timestamp comment '�޸�ʱ��'
);

alter table user_interest_detail comment '�û���Ʒ�����¼��(ÿ�ս�����Ϣ��¼)';

/*==============================================================*/
/* Index: idx_user_interest_creat_date                          */
/*==============================================================*/
create index idx_user_interest_creat_date on user_interest_detail
(
   created_date
);

/*==============================================================*/
/* Index: idx_user_interest_user_prd_id                         */
/*==============================================================*/
create index idx_user_interest_user_prd_id on user_interest_detail
(
   user_product_id
);

/*==============================================================*/
/* Table: user_order                                            */
/*==============================================================*/
create table user_order
(
   order_id             varchar(32) not null comment '������ˮ��',
   user_code            int comment '�û�����',
   product_code         varchar(20) comment '��Ʒ����',
   amount               decimal(16,2) comment 'Ͷ�ʽ��',
   order_type           char(2) comment '���ͣ�01����ֵ 02������ 03����Ϣ����',
   status               char(2) comment '����״̬��01��������(�ʽ�����) 02����Ʒ������ 03�����������',
   message              varchar(200) comment '���������Ϣ�������ɹ���ʧ��ԭ��',
   created_date         timestamp comment '����ʱ��',
   updated_date         timestamp comment '�޸�ʱ��',
   primary key (order_id)
);

alter table user_order comment '�û�������ˮ��¼';

/*==============================================================*/
/* Index: idx_user_order_user_code                              */
/*==============================================================*/
create index idx_user_order_user_code on user_order
(
   user_code
);

/*==============================================================*/
/* Index: idx_user_order_prod_code                              */
/*==============================================================*/
create index idx_user_order_prod_code on user_order
(
   product_code
);

/*==============================================================*/
/* Index: idx_user_order_updated_date                           */
/*==============================================================*/
create index idx_user_order_updated_date on user_order
(
   updated_date
);

/*==============================================================*/
/* Index: idx_user_order_created_date                           */
/*==============================================================*/
create index idx_user_order_created_date on user_order
(
   created_date
);

/*==============================================================*/
/* Table: user_product                                          */
/*==============================================================*/
create table user_product
(
   user_product_id      int not null auto_increment comment '����',
   user_code            int comment '�û�����',
   product_code         varchar(20) comment '��Ʒ����',
   current_amount       decimal(16,2) comment '��ǰͶ�ʽ�=ǰһ��Ͷ�ʶ�+��ֵ-����+��Ϣ��',
   total_interest       decimal(16,2) comment '��Ʒ������',
   status               decimal(16,4) comment 'Ͷ��״̬��1����Ʒ������ 2�����������',
   created_date         timestamp comment '����ʱ��',
   updated_date         timestamp comment '�޸�ʱ��',
   primary key (user_product_id)
);

alter table user_product comment '�û��ɹ�����Ĳ�Ʒ�б����';

/*==============================================================*/
/* Index: pk_user_product_id                                    */
/*==============================================================*/
create index pk_user_product_id on user_product
(
   user_product_id
);

/*==============================================================*/
/* Index: idx_user_product_user_code                            */
/*==============================================================*/
create index idx_user_product_user_code on user_product
(
   user_code
);

/*==============================================================*/
/* Index: idx_user_product_prod_code                            */
/*==============================================================*/
create index idx_user_product_prod_code on user_product
(
   product_code
);

/*==============================================================*/
/* Table: user_product_detail                                   */
/*==============================================================*/
create table user_product_detail
(
   user_product_detail_id int not null auto_increment comment '����',
   user_product_id      int comment '�û���Ʒ����',
   product_detail_id    int comment 'Ͷ�ʲ�Ʒ��ϸid',
   invest_amount        decimal(16,2) comment 'Ͷ�ʽ��',
   created_date         timestamp comment '����ʱ��',
   updated_date         timestamp comment '����ʱ��',
   primary key (user_product_detail_id)
);

alter table user_product_detail comment '�����Ʒ��ϸ';

/*==============================================================*/
/* Index: idx_user_prod_detail_user_prod_id                     */
/*==============================================================*/
create index idx_user_prod_detail_user_prod_id on user_product_detail
(
   user_product_id
);

/*==============================================================*/
/* Index: idx_user_prod_detail_prod_detail_id                   */
/*==============================================================*/
create index idx_user_prod_detail_prod_detail_id on user_product_detail
(
   product_detail_id
);

/*==============================================================*/
/* Index: idx_user_prod_detail_creat_date                       */
/*==============================================================*/
create index idx_user_prod_detail_creat_date on user_product_detail
(
   created_date
);

/*==============================================================*/
/* Table: user_reward                                           */
/*==============================================================*/
create table user_reward
(
   user_reward_id       int not null auto_increment comment '����',
   user_code            int comment '�û�����',
   product_reward_id    int,
   reward_code          varchar(20) comment '��Ʒ����',
   reward_amount        decimal(16,2) comment '��Ʒ���',
   status               char comment '��Ʒ״̬(1:���� 2:ʧЧ)',
   created_Date         timestamp comment '����ʱ��',
   updated_date         timestamp comment '�޸�ʱ��',
   primary key (user_reward_id)
);

/*==============================================================*/
/* Table: user_reward_consume_log                               */
/*==============================================================*/
create table user_reward_consume_log
(
   consume_log_id       int not null auto_increment comment '����',
   user_reward_id       int comment '���ѵĽ�Ʒid',
   consume_amount       decimal(16,2) comment '�ɷѽ��',
   consume_purpose      varchar(100) comment '������;',
   created_date         timestamp comment '����ʱ��',
   updated_date         timestamp comment '�޸�ʱ��',
   primary key (consume_log_id)
);

alter table user_reward_consume_log comment '������Ϣ����';

alter table login_log add constraint FK_ref_loginlog_user foreign key (user_code)
      references user (user_code) on delete restrict on update restrict;

alter table product_detail add constraint FK_ref_prod_detail_prod foreign key (product_code)
      references product_info (product_code) on delete restrict on update restrict;

alter table product_rate add constraint FK_ref_prod_rate_prod foreign key (product_code)
      references product_info (product_code) on delete restrict on update restrict;

alter table product_reward add constraint FK_ref_prod_reward_prod foreign key (product_code)
      references product_info (product_code) on delete restrict on update restrict;

alter table product_reward add constraint FK_ref_prod_reward_reward_def foreign key (reword_code)
      references reward_define (reward_code) on delete restrict on update restrict;

alter table product_rule add constraint FK_ref_prod_rule_prod foreign key (product_code)
      references product_info (product_code) on delete restrict on update restrict;

alter table third_trade_log add constraint FK_ref_third_trade_log_user_order foreign key (order_id)
      references user_order (order_id) on delete restrict on update restrict;

alter table third_trade_log add constraint FK_ref_trade_log_plat foreign key (platform_id)
      references third_platform (platform_id) on delete restrict on update restrict;

alter table user_account add constraint FK_ref_user_account_code foreign key (user_code)
      references user (user_code) on delete restrict on update restrict;

alter table user_bind_card add constraint FK_ref_user_bind_card_bank_def foreign key (bank_code)
      references bank_define (bank_code) on delete restrict on update restrict;

alter table user_bind_card add constraint FK_ref_user_bind_card_user foreign key (user_code)
      references user (user_code) on delete restrict on update restrict;

alter table user_detail add constraint FK_ref_user_detail_user foreign key (user_code)
      references user (user_code) on delete restrict on update restrict;

alter table user_interest_detail add constraint FK_ref_user_interest_user_prod foreign key (user_product_id)
      references user_product (user_product_id) on delete restrict on update restrict;

alter table user_order add constraint FK_ref_user_order_prod foreign key (product_code)
      references product_info (product_code) on delete restrict on update restrict;

alter table user_order add constraint FK_ref_user_order_user foreign key (user_code)
      references user (user_code) on delete restrict on update restrict;

alter table user_product add constraint FK_ref_user_prod_prod foreign key (product_code)
      references product_info (product_code) on delete restrict on update restrict;

alter table user_product add constraint FK_ref_user_prod_user foreign key (user_code)
      references user (user_code) on delete restrict on update restrict;

alter table user_product_detail add constraint FK_ref_user_prod_detail_prod_detail foreign key (product_detail_id)
      references product_detail (product_detail_id) on delete restrict on update restrict;

alter table user_product_detail add constraint FK_ref_user_prod_detail_prod_id foreign key (user_product_id)
      references user_product (user_product_id) on delete restrict on update restrict;

alter table user_reward add constraint FK_ref_user_reward_prod_reward foreign key (product_reward_id)
      references product_reward (product_reward_id) on delete restrict on update restrict;

alter table user_reward add constraint FK_ref_user_reward_reward_def foreign key (reward_code)
      references reward_define (reward_code) on delete restrict on update restrict;

alter table user_reward add constraint FK_ref_user_reward_user foreign key (user_code)
      references user (user_code) on delete restrict on update restrict;

alter table user_reward_consume_log add constraint FK_ref_user_reward_consume_user_reward foreign key (user_reward_id)
      references user_reward (user_reward_id) on delete restrict on update restrict;

