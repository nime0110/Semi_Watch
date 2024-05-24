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

select *
from tbl_loginhistory;

select *
from tbl_member
where userid = 'kimkh3';

commit;

select *
from tbl_loginhistory;


SELECT userid, NVL( lastlogingap, trunc( months_between(sysdate, registerday)) ) AS lastlogingap
FROM
( select userid, registerday
from tbl_member
where status = 1 and userid = ? and pw = ? ) M
CROSS JOIN
( select trunc( months_between(sysdate, max(logindate)) ) AS lastlogingap
from tbl_loginhistory
where fk_userid = ? ) H;


/* 사용자리뷰 */
CREATE TABLE tbl_review (
   reviewno VARCHAR2(30) NOT NULL, /* 리뷰코드 */
   fk_pdno VARCHAR2(30) NOT NULL, /* 상품코드 */
   fk_userid VARCHAR2(20), /* 아이디 */
   review_content VARCHAR2(100), /* 리뷰내용 */
   starpoint NUMBER(1) DEFAULT 1, /* 별점 */
   review_date DATE default sysdate, /* 리뷰작성일자 */
    review_status NUMBER(1) DEFAULT 0, /* 리뷰 */
    CONSTRAINT PK_tbl_review_reviewno PRIMARY KEY (reviewno), -- 리뷰코드 기본키설정
    CONSTRAINT FK_tbl_review_FK_USERID FOREIGN KEY(FK_USERID) REFERENCES TBL_MEMBER(USERID), -- 회원테이블의 아이디를 외래키로 받음
    CONSTRAINT FK_tbl_review_FK_PDNO FOREIGN KEY(FK_PDNO) REFERENCES TBL_PRODUCT(PDNO), -- 상품테이블의 상품번호를 외래키로 받음
    CONSTRAINT CK_tbl_review_starpoint CHECK (starpoint BETWEEN 1 AND 5); -- 별점 체크제약 1부터 5까지만
-- Table TBL_REVIEW이(가) 생성되었습니다.



select *
from tbl_review;

create sequence seq_reviewno
start with 1
increment by 1
nomaxvalue
nominvalue
nocycle
nocache;
-- Sequence SEQ_REVIEWNO이(가) 생성되었습니다.

commit;

update tbl_review set reviewno='1' where fk_pdno = '5'; 

insert into tbl_review(reviewno, fk_pdno, fk_userid, review_content, starpoint)
values(seq_reviewno.nextval,'5','kimkh','비싼 시계 처음 구입해봤는데 돈값 합니다. 강력 추천합니다! b','5');

insert into tbl_review(reviewno, fk_pdno, fk_userid, review_content, starpoint)
values(seq_reviewno.nextval,'6','kimkh','비싼 시계 처음 구입해봤는데 돈값 합니다. 강력 추천합니다! b','5');

insert into tbl_review(reviewno, fk_pdno, fk_userid, review_content, starpoint)
values(seq_reviewno.nextval,'7','kimkh','시계 멋있습니다. 강력 추천합니다! b','5');

insert into tbl_review(reviewno, fk_pdno, fk_userid, review_content, starpoint)
values(seq_reviewno.nextval,'8','kimkh','가격에 비해 많이 아쉽습니다!','1');

insert into tbl_review(reviewno, fk_pdno, fk_userid, review_content, starpoint)
values(seq_reviewno.nextval,'9','kimkh','그냥저냥 쓸만 합니다','3');

insert into tbl_review(reviewno, fk_pdno, fk_userid, review_content, starpoint)
values(seq_reviewno.nextval,'25','kimkh','그냥저냥 쓸만 합니다','3');

insert into tbl_review(reviewno, fk_pdno, fk_userid, review_content, starpoint)
values(seq_reviewno.nextval,'26','kimkh','그냥저냥 쓸만 합니다','3');

insert into tbl_review(reviewno, fk_pdno, fk_userid, review_content, starpoint)
values(seq_reviewno.nextval,'27','kimkh','그냥저냥 쓸만 합니다','3');

insert into tbl_review(reviewno, fk_pdno, fk_userid, review_content, starpoint)
values(seq_reviewno.nextval,'28','kimkh','그냥저냥 쓸만 합니다','3');

insert into tbl_review(reviewno, fk_pdno, fk_userid, review_content, starpoint)
values(seq_reviewno.nextval,'29','kimkh','그냥저냥 쓸만 합니다','3');

insert into tbl_review(reviewno, fk_pdno, fk_userid, review_content, starpoint)
values(seq_reviewno.nextval,'30','kimkh','그냥저냥 쓸만 합니다','3');

commit;


(select reviewno, fk_pdno, fk_userid, review_content, starpoint  
from tbl_review) R
JOIN
(select brand
from tbl_product) P

-- 관리자 리뷰관리에서 띄워줄 셀렉트문
SELECT R.reviewno, P.pdname, M.userid, M.username, P.brand, R.review_content, R.starpoint
FROM tbl_review R JOIN tbl_product P
ON R.fk_pdno = P.pdno JOIN tbl_member M
on R.fk_userid = M.userid
where userid != 'admin';

-- 관리자 리뷰상세보기에서 띄워줘야 할 것
SELECT R.reviewno AS reviewno, P.pdname AS pdname, P.pdimg1 AS pdimg1, M.userid AS userid, M.username AS username, P.brand AS brand, R.review_content AS review_content, R.starpoint AS starpoint
FROM tbl_review R JOIN tbl_product P
ON R.fk_pdno = P.pdno JOIN tbl_member M
on R.fk_userid = M.userid
where reviewno = '1';



 select count(*) 
 from tbl_review R JOIN tbl_product P
 on R.fk_pdno = P.pdno
 where fk_userid != 'admin'
 
 select ceil(count(*)/?) 
 from tbl_review R JOIN tbl_product P
 on R.fk_pdno = P.pdno
 where fk_userid != 'admin'

 
SELECT rno, reviewno, pdname, userid, username, brand, review_content, starpoint
FROM 
(
    SELECT rownum AS rno, R.reviewno AS reviewno, P.pdname AS pdname, M.userid AS userid,
    M.username AS username, P.brand AS brand, R.review_content AS review_content, R.starpoint AS starpoint 
    FROM tbl_review R 
    JOIN tbl_product P ON R.fk_pdno = P.pdno 
    JOIN tbl_member M ON R.fk_userid = M.userid 
    WHERE M.userid != 'admin'
    ORDER BY R.review_date DESC
) 
WHERE rno BETWEEN 1 AND 5;



SELECT userid, username, email, mobile, postcode, address, detail_address, NVL( lastlogingap, trunc( months_between(sysdate, registerday)) ) AS lastlogingap 
FROM
( select userid, username, email, mobile, postcode, address||' '||extra_address AS address, detail_address, registerday 
from tbl_member 
where status = 1 and userid = 'kimkh7') M 
CROSS JOIN 
( select trunc( months_between(sysdate, max(logindate)) ) AS lastlogingap 
from tbl_loginhistory 
where fk_userid = 'kimkh7' ) H 

 SELECT userid, username, email, mobile, postcode, address, detail_address, NVL( lastlogingap, trunc( months_between(sysdate, registerday)) ) AS lastlogingap 
FROM 
( select userid, username, email, mobile, postcode, address||' '||extra_address AS address, detail_address, registerday 
from tbl_member 
where status = 1 and userid = 'kimkh22' ) M 
CROSS JOIN 
(select trunc( months_between(sysdate, max(logindate)) ) AS lastlogingap 
from tbl_loginhistory 
where fk_userid = 'kimkh22' ) H


SELECT R.reviewno AS reviewno, P.brand AS brand, P.pdname AS pdname, P.pdimg1 AS pdimg1, M.userid AS userid, M.username AS username, R.review_content AS review_content, R.starpoint AS starpoint 
FROM tbl_review R JOIN tbl_product P 
ON R.fk_pdno = P.pdno JOIN tbl_member M 
ON R.fk_userid = M.userid 
where reviewno = 7; 

delete from tbl_review where reviewno = 4

rollback;

select *
from tbl_review
order by review_date desc;


SELECT rno, reviewno, pdname, userid, username, brand, review_content, starpoint 
FROM 
( 
SELECT rownum AS rno, R.reviewno AS reviewno, P.pdname AS pdname, M.userid AS userid, 
M.username AS username, P.brand AS brand, R.review_content AS review_content, R.starpoint AS starpoint 
FROM tbl_review R 
JOIN tbl_product P ON R.fk_pdno = P.pdno 
JOIN tbl_member M ON R.fk_userid = M.userid 
WHERE M.userid != 'admin' 
ORDER BY rno DESC) 