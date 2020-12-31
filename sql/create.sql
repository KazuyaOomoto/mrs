-- 時間帯情報
create table M_TIME (
  time_cd integer not null
  , time_name character varying(20) not null
  , constraint M_TIME_PKC primary key (time_cd)
) ;

-- 会議室情報
create table M_ROOM (
  room_cd integer not null
  , room_name character varying(20) not null
  , constraint M_ROOM_PKC primary key (room_cd)
) ;

-- 予約情報
create table T_RESERVE (
  reserve_date date not null
  , time_cd integer not null
  , room_cd integer default 1 not null
  , emp_no character(8) not null
  , tel character varying(11) not null
  , purpose character varying(20) not null
  , constraint T_RESERVE_PKC primary key (reserve_date,time_cd,room_cd)
) ;

-- 従業員情報
create table M_EMPLOYEE (
  emp_no character(8) not null
  , password character varying(60)
  , department character varying(12)
  , emp_name character varying(20)
  , default_tel character varying(11)
  , constraint M_EMPLOYEE_PKC primary key (emp_no)
) ;

comment on table M_TIME is '時間帯情報';
comment on column M_TIME.time_cd is '時間帯コード';
comment on column M_TIME.time_name is '時間帯名';

comment on table M_ROOM is '会議室情報';
comment on column M_ROOM.room_cd is '会議室コード';
comment on column M_ROOM.room_name is '会議室名';

comment on table T_RESERVE is '予約情報';
comment on column T_RESERVE.reserve_date is '予約日';
comment on column T_RESERVE.time_cd is '時間帯コード';
comment on column T_RESERVE.room_cd is '会議室コード';
comment on column T_RESERVE.emp_no is '従業員番号';
comment on column T_RESERVE.tel is '連絡先';
comment on column T_RESERVE.purpose is '利用目的';

comment on table M_EMPLOYEE is '従業員情報';
comment on column M_EMPLOYEE.emp_no is '従業員番号';
comment on column M_EMPLOYEE.password is 'パスワード';
comment on column M_EMPLOYEE.department is '部署';
comment on column M_EMPLOYEE.emp_name is '氏名';
comment on column M_EMPLOYEE.default_tel is '既定連絡先';

--pgcryptoモジュールを有効にする
CREATE EXTENSION pgcrypto;
