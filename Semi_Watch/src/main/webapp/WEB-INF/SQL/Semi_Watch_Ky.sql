-- 오라클 계정 생성을 위해서는 SYS 또는 SYSTEM 으로 연결하여 작업을 해야 합니다. [SYS 시작] --
show user;
-- USER이(가) "SYS"입니다.

-- 오라클 계정 생성시 계정명 앞에 c## 붙이지 않고 생성하도록 하겠습니다.
alter session set "_ORACLE_SCRIPT"=true;
-- Session이(가) 변경되었습니다.

-- 오라클 계정명은 MYMVC_USER 이고 암호는 gclass 인 사용자 계정을 생성합니다.
create user semi_orauser2 identified by gclass default tablespace users; 
-- User MYMVC_USER이(가) 생성되었습니다.

-- 위에서 생성되어진 MYMVC_USER 이라는 오라클 일반사용자 계정에게 오라클 서버에 접속이 되어지고,
-- 테이블 생성 등등을 할 수 있도록 여러가지 권한을 부여해주겠습니다.
grant connect, resource, create view, unlimited tablespace to semi_orauser2;
-- Grant을(를) 성공했습니다.


show user;
-- USER이(가) "SEMI_ORAUSER2"입니다.

select *
from tbl_product;


CREATE TABLE tbl_member (
   userid VARCHAR2(20) NOT NULL, /* 아이디 */
   username NVARCHAR2(20) NOT NULL, /* 성명 */
   email VARCHAR2(200) NOT NULL, /* 이메일 */
   pw VARCHAR2(200) NOT NULL, /* 비밀번호 */
   mobile VARCHAR2(200) NOT NULL, /* 휴대폰번호 */
   address VARCHAR2(200), /* 주소 */
   detail_address VARCHAR2(200), /* 상세주소 */
   extra_address VARCHAR2(200), /* 주소참고항목 */
   postcode VARCHAR2(10), /* 우편번호 */
   birthday VARCHAR2(10) NOT NULL, /* 생년월일 */
   gender VARCHAR2(1) NOT NULL, /* 성별 */
   registerday DATE DEFAULT sysdate, /* 가입일자 */
   lastpwdchangedate DATE DEFAULT sysdate, /* 마지막암호변경일자 */
   status NUMBER(1) DEFAULT 1 NOT NULL, /* 회원탈퇴유무 */
   idle NUMBER(1) DEFAULT 0 NOT NULL, /* 휴면유무 */
   mileage NUMBER(10) DEFAULT 0, /* 마일리지 */
    CONSTRAINT PK_TBL_MEMBER_USERID PRIMARY KEY (USERID), -- userid 기본키 설정
    CONSTRAINT UQ_TBL_MEMBER_MOBILE UNIQUE(MOBILE),         -- mobile 유니크키 설정
    CONSTRAINT UQ_TBL_MEMBER_EMAIL UNIQUE(EMAIL),           -- email 유니크키 설정
    CONSTRAINT CK_TBL_MEMBER_GENDER check(GENDER in(1,2)),  -- gender 1,2 체크인제약 (1 남자, 2 여자)
    CONSTRAINT CK_TBL_MEMBER_STATUS check(STATUS in(0,1)),  -- status 0,1 체크인제약 (1 정상회원, 0 탈퇴회원)
    CONSTRAINT CK_TBL_MEMBER_IDLE check(idle in(0,1)) -- idle 0,1 체크인제약 (1 활동회원, 0 휴면회원)
);

insert into tbl_member(userid, pw, username, email, mobile, address, detail_address, extra_address, postcode,  gender, birthday) 
    values('youinna', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382', '유인나', 'Uqqfgdi052vJc9GbT3XIhooUlaQvPVtsd04skgjrpIg=', 'FBd1MwxTJeUlm/KdsyMc0w==', 
           '경기 군포시 오금로 15-17', '101동 102호', ' (금정동)', '15864', '2', '2001-10-11');


insert into tbl_member (userid, username, email, pw, mobile, address, detail_address, extra_address, postcode,
birthday, gender)
values ('kimkh', '김경현', 'sgRjKPj1rNWRP1IWRFtg4A==', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382',
'aJ3vlstFXDdjq0SvwGkNYQ==', '경기 군포시 오금로 15-17', '101동 102호', ' (금정동)', '15864', '2001-10-11', '1');