---------------- 성심 작업 -------------------
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

SELECT *
FROM tbl_member;

SELECT *
FROM tbl_loginhistory;

-------------------------------- 관리자 계정 ---------------------------------------------
insert into tbl_member(userid, pw, username, email, mobile, address, detail_address, extra_address, postcode,  gender, birthday) 
values('admin', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382', '관리자', 'Uqqfgdi052vJc9GbT3XIhooUlaQvPVtsd04skgjrpIg=', 'FBd1MwxTJeUlm/KdsyMc0w==', 
       '경기 군포시 오금로 15-17', '402동 202호', ' (금정동)', '15864', '2', '1990-04-22');
commit;
----------------------------------------------------------------------------------------

-- 로그인 쿼리 -- 
SELECT userid, username, pwdchangegap, 
NVL( lastlogingap, trunc(months_between(sysdate,registerday)) ) AS lastlogingap, 
	                  idle, 
	                      mobile, email, postcode, address, detail_address, extra_address,
                          to_char(registerday, 'yyyy-mm-dd') AS registerday  
                          FROM 
	         ( select userid, username,  
	                    trunc( months_between(sysdate, lastpwdchangedate) ) AS pwdchangegap,
	                    registerday, idle, 
	               mobile, email, postcode, address, detail_address, extra_address 
	         from tbl_member where status = 1 and userid = ? and pw = ?) M 
	        CROSS JOIN 
	         ( select trunc( months_between(sysdate, max(logindate)) ) AS lastlogingap 
	         from tbl_loginhistory  where fk_userid = ?) H ;
             


-- 더미데이터 삽입 쿼리 -- 
insert into tbl_member(userid, pw, username, email, mobile, address, detail_address, extra_address, postcode,  gender, birthday) 
values('youinna', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382', '유인나', 'Uqqfgdi052vJc9GbT3XIhooUlaQvPVtsd04skgjrpIg=', 'FBd1MwxTJeUlm/KdsyMc0w==', 
       '경기 군포시 오금로 15-17', '101동 102호', ' (금정동)', '15864', '2', '2001-10-11');

insert into tbl_member(userid, pw, username, email, mobile, address, detail_address, extra_address, postcode,  gender, birthday) 
values('dayhon', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382', '강다현', 'Uqqfgdi052vJc9GbT3XIhooUlaQvPVtsd04skgjrpIg=', 'FBd1MwxTJeUlm/KdsyMc0w==', 
       '경기 군포시 오금로 15-17', '101동 102호', ' (금정동)', '15864', '2', '2001-10-11');
commit;

insert into tbl_member(userid, pw, username, email, mobile, address, detail_address, extra_address, postcode,  gender, birthday) 
values('minkyong', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382', '강민경', 'Uqqfgdi052vJc9GbT3XIhooUlaQvPVtsd04skgjrpIg=', 'FBd1MwxTJeUlm/KdsyMc0w==', 
       '경기 군포시 오금로 15-17', '101동 103호', ' (금정동)', '15864', '2', '2001-10-11');
commit;

insert into tbl_member(userid, pw, username, email, mobile, address, detail_address, extra_address, postcode,  gender, birthday) 
values('sooo12', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382', '강수연', 'Uqqfgdi052vJc9GbT3XIhooUlaQvPVtsd04skgjrpIg=', 'FBd1MwxTJeUlm/KdsyMc0w==', 
       '경기 군포시 오금로 15-17', '101동 104호', ' (금정동)', '15864', '2', '2001-10-11');
commit;

insert into tbl_member(userid, pw, username, email, mobile, address, detail_address, extra_address, postcode,  gender, birthday) 
values('binfolder1', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382', '권은빈', 'Uqqfgdi052vJc9GbT3XIhooUlaQvPVtsd04skgjrpIg=', 'FBd1MwxTJeUlm/KdsyMc0w==', 
       '경기 군포시 오금로 15-17', '102동 101호', ' (금정동)', '15864', '2', '2001-10-11');
commit;

insert into tbl_member(userid, pw, username, email, mobile, address, detail_address, extra_address, postcode,  gender, birthday) 
values('riri03', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382', '남규리', 'Uqqfgdi052vJc9GbT3XIhooUlaQvPVtsd04skgjrpIg=', 'FBd1MwxTJeUlm/KdsyMc0w==', 
       '경기 군포시 오금로 15-17', '102동 102호', ' (금정동)', '15864', '2', '2001-10-11');
commit;

insert into tbl_member(userid, pw, username, email, mobile, address, detail_address, extra_address, postcode,  gender, birthday) 
values('moonone', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382', '문예원', 'Uqqfgdi052vJc9GbT3XIhooUlaQvPVtsd04skgjrpIg=', 'FBd1MwxTJeUlm/KdsyMc0w==', 
       '경기 군포시 오금로 15-17', '102동 104호', ' (금정동)', '15864', '2', '2001-10-11');
commit;

insert into tbl_member(userid, pw, username, email, mobile, address, detail_address, extra_address, postcode,  gender, birthday) 
values('frozenhuman', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382', '설인아', 'Uqqfgdi052vJc9GbT3XIhooUlaQvPVtsd04skgjrpIg=', 'FBd1MwxTJeUlm/KdsyMc0w==', 
       '경기 군포시 오금로 15-17', '103동 101호', ' (금정동)', '15864', '2', '2001-10-11');
commit;


insert into tbl_member(userid, pw, username, email, mobile, address, detail_address, extra_address, postcode,  gender, birthday) 
values('notyujin', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382', '안유진', 'Uqqfgdi052vJc9GbT3XIhooUlaQvPVtsd04skgjrpIg=', 'FBd1MwxTJeUlm/KdsyMc0w==', 
       '경기 군포시 오금로 15-17', '103동 201호', ' (금정동)', '15864', '2', '2001-10-11');
commit;

insert into tbl_member(userid, pw, username, email, mobile, address, detail_address, extra_address, postcode,  gender, birthday) 
values('tae123', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382', '김태형', 'Uqqfgdi052vJc9GbT3XIhooUlaQvPVtsd04skgjrpIg=', 'FBd1MwxTJeUlm/KdsyMc0w==', 
       '경기 군포시 오금로 15-17', '203동 101호', ' (금정동)', '15864', '1', '2001-10-11');
commit;

insert into tbl_member(userid, pw, username, email, mobile, address, detail_address, extra_address, postcode,  gender, birthday) 
values('smartmin', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382', '김명민', 'Uqqfgdi052vJc9GbT3XIhooUlaQvPVtsd04skgjrpIg=', 'FBd1MwxTJeUlm/KdsyMc0w==', 
       '경기 군포시 오금로 15-17', '203동 103호', ' (금정동)', '15864', '1', '2000-10-11');
commit;


insert into tbl_member(userid, pw, username, email, mobile, address, detail_address, extra_address, postcode,  gender, birthday) 
values('pado1', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382', '박해일', 'Uqqfgdi052vJc9GbT3XIhooUlaQvPVtsd04skgjrpIg=', 'FBd1MwxTJeUlm/KdsyMc0w==', 
       '경기 군포시 오금로 15-17', '203동 104호', ' (금정동)', '15864', '1', '1975-10-11');
commit;


insert into tbl_member(userid, pw, username, email, mobile, address, detail_address, extra_address, postcode,  gender, birthday) 
values('seodo0', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382', '서도영', 'Uqqfgdi052vJc9GbT3XIhooUlaQvPVtsd04skgjrpIg=', 'FBd1MwxTJeUlm/KdsyMc0w==', 
       '경기 군포시 오금로 15-17', '204동 101호', ' (금정동)', '15864', '1', '1985-10-20');
commit;


insert into tbl_member(userid, pw, username, email, mobile, address, detail_address, extra_address, postcode,  gender, birthday) 
values('jinjin1', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382', '연우진', 'Uqqfgdi052vJc9GbT3XIhooUlaQvPVtsd04skgjrpIg=', 'FBd1MwxTJeUlm/KdsyMc0w==', 
       '경기 군포시 오금로 15-17', '204동 103호', ' (금정동)', '15864', '1', '1995-10-20');
commit;


insert into tbl_member(userid, pw, username, email, mobile, address, detail_address, extra_address, postcode,  gender, birthday) 
values('treeme', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382', '윤나무', 'Uqqfgdi052vJc9GbT3XIhooUlaQvPVtsd04skgjrpIg=', 'FBd1MwxTJeUlm/KdsyMc0w==', 
       '경기 군포시 오금로 15-17', '204동 104호', ' (금정동)', '15864', '1', '1996-06-20');
commit;

insert into tbl_member(userid, pw, username, email, mobile, address, detail_address, extra_address, postcode,  gender, birthday) 
values('humanchoice', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382', '전인택', 'Uqqfgdi052vJc9GbT3XIhooUlaQvPVtsd04skgjrpIg=', 'FBd1MwxTJeUlm/KdsyMc0w==', 
       '경기 군포시 오금로 15-17', '301동 101호', ' (금정동)', '15864', '1', '1996-06-20');
commit;


insert into tbl_member(userid, pw, username, email, mobile, address, detail_address, extra_address, postcode,  gender, birthday) 
values('jungdongnam', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382', '정동남', 'Uqqfgdi052vJc9GbT3XIhooUlaQvPVtsd04skgjrpIg=', 'FBd1MwxTJeUlm/KdsyMc0w==', 
       '경기 군포시 오금로 15-17', '301동 102호', ' (금정동)', '15864', '1', '1980-06-20');
commit;


insert into tbl_member(userid, pw, username, email, mobile, address, detail_address, extra_address, postcode,  gender, birthday) 
values('jewelryjung', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382', '정보석', 'Uqqfgdi052vJc9GbT3XIhooUlaQvPVtsd04skgjrpIg=', 'FBd1MwxTJeUlm/KdsyMc0w==', 
       '경기 군포시 오금로 15-17', '301동 103호', ' (금정동)', '15864', '1', '1980-06-20');
commit;


insert into tbl_member(userid, pw, username, email, mobile, address, detail_address, extra_address, postcode,  gender, birthday) 
values('victory', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382', '조승우', 'Uqqfgdi052vJc9GbT3XIhooUlaQvPVtsd04skgjrpIg=', 'FBd1MwxTJeUlm/KdsyMc0w==', 
       '경기 군포시 오금로 15-17', '301동 201호', ' (금정동)', '15864', '1', '1980-06-20');
commit;


insert into tbl_member(userid, pw, username, email, mobile, address, detail_address, extra_address, postcode,  gender, birthday) 
values('kickticket', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382', '차인표', 'Uqqfgdi052vJc9GbT3XIhooUlaQvPVtsd04skgjrpIg=', 'FBd1MwxTJeUlm/KdsyMc0w==', 
       '경기 군포시 오금로 15-17', '301동 202호', ' (금정동)', '15864', '1', '1980-06-20');
commit;


insert into tbl_member(userid, pw, username, email, mobile, address, detail_address, extra_address, postcode,  gender, birthday) 
values('bestchoi', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382', '최상', 'Uqqfgdi052vJc9GbT3XIhooUlaQvPVtsd04skgjrpIg=', 'FBd1MwxTJeUlm/KdsyMc0w==', 
       '경기 군포시 오금로 15-17', '301동 203호', ' (금정동)', '15864', '1', '1970-06-20');
commit;

insert into tbl_member(userid, pw, username, email, mobile, address, detail_address, extra_address, postcode,  gender, birthday) 
values('tigersick', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382', '황범식', 'Uqqfgdi052vJc9GbT3XIhooUlaQvPVtsd04skgjrpIg=', 'FBd1MwxTJeUlm/KdsyMc0w==', 
       '경기 군포시 오금로 15-17', '301동 204호', ' (금정동)', '15864', '1', '1970-06-20');
commit;


insert into tbl_member(userid, pw, username, email, mobile, address, detail_address, extra_address, postcode,  gender, birthday) 
values('bestactor1', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382', '최민식', 'Uqqfgdi052vJc9GbT3XIhooUlaQvPVtsd04skgjrpIg=', 'FBd1MwxTJeUlm/KdsyMc0w==', 
       '경기 군포시 오금로 15-17', '301동 301호', ' (금정동)', '15864', '1', '1970-06-20');
commit;


insert into tbl_member(userid, pw, username, email, mobile, address, detail_address, extra_address, postcode,  gender, birthday) 
values('jin12', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382', '김석진', 'Uqqfgdi052vJc9GbT3XIhooUlaQvPVtsd04skgjrpIg=', 'FBd1MwxTJeUlm/KdsyMc0w==', 
       '경기 군포시 오금로 15-17', '401동 101호', ' (금정동)', '15864', '1', '1970-06-20');
commit;


insert into tbl_member(userid, pw, username, email, mobile, address, detail_address, extra_address, postcode,  gender, birthday) 
values('juuun1', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382', '김남준', 'Uqqfgdi052vJc9GbT3XIhooUlaQvPVtsd04skgjrpIg=', 'FBd1MwxTJeUlm/KdsyMc0w==', 
       '경기 군포시 오금로 15-17', '401동 102호', ' (금정동)', '15864', '1', '1970-06-20');
commit;

insert into tbl_member(userid, pw, username, email, mobile, address, detail_address, extra_address, postcode,  gender, birthday) 
values('juuun2', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382', '전종석', 'Uqqfgdi052vJc9GbT3XIhooUlaQvPVtsd04skgjrpIg=', 'FBd1MwxTJeUlm/KdsyMc0w==', 
       '경기 군포시 오금로 15-17', '401동 103호', ' (금정동)', '15864', '1', '1970-06-20');
commit;


insert into tbl_member(userid, pw, username, email, mobile, address, detail_address, extra_address, postcode,  gender, birthday) 
values('hoho2', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382', '전종호', 'Uqqfgdi052vJc9GbT3XIhooUlaQvPVtsd04skgjrpIg=', 'FBd1MwxTJeUlm/KdsyMc0w==', 
       '경기 군포시 오금로 15-17', '401동 103호', ' (금정동)', '15864', '1', '1970-06-20');
commit;

insert into tbl_member(userid, pw, username, email, mobile, address, detail_address, extra_address, postcode,  gender, birthday) 
values('pretty1', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382', '전지현', 'Uqqfgdi052vJc9GbT3XIhooUlaQvPVtsd04skgjrpIg=', 'FBd1MwxTJeUlm/KdsyMc0w==', 
       '경기 군포시 오금로 15-17', '402동 103호', ' (금정동)', '15864', '2', '1990-06-20');
commit;


insert into tbl_member(userid, pw, username, email, mobile, address, detail_address, extra_address, postcode,  gender, birthday) 
values('silverme', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382', '고은미', 'Uqqfgdi052vJc9GbT3XIhooUlaQvPVtsd04skgjrpIg=', 'FBd1MwxTJeUlm/KdsyMc0w==', 
       '경기 군포시 오금로 15-17', '402동 104호', ' (금정동)', '15864', '2', '1990-06-22');
commit;

insert into tbl_member(userid, pw, username, email, mobile, address, detail_address, extra_address, postcode,  gender, birthday) 
values('white1', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382', '백수련', 'Uqqfgdi052vJc9GbT3XIhooUlaQvPVtsd04skgjrpIg=', 'FBd1MwxTJeUlm/KdsyMc0w==', 
       '경기 군포시 오금로 15-17', '402동 104호', ' (금정동)', '15864', '2', '1990-06-22');
commit;


insert into tbl_member(userid, pw, username, email, mobile, address, detail_address, extra_address, postcode,  gender, birthday) 
values('rightbottom', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382', '선우서하', 'Uqqfgdi052vJc9GbT3XIhooUlaQvPVtsd04skgjrpIg=', 'FBd1MwxTJeUlm/KdsyMc0w==', 
       '경기 군포시 오금로 15-17', '402동 201호', ' (금정동)', '15864', '2', '1990-04-22');
commit;

--- 더미 데이터 삽입 쿼리 끝 --

--- admin 회원목록 페이지 관련 쿼리 작성 --
select userid, username, email, gender 
from tbl_member
where userid != 'admin'
order by registerday desc ;




--- admin 회원목록 페이지 관련 쿼리 작성 --
select ceil(count(*)/3) 
from tbl_member
where userid != 'admin'
order by registerday desc;
-- 전체 갯수 / ? => 페이지 갯수 

-- 페이징 처리를 한 모든 회원 또는 검색한 회원 목록 보여주기 쿼리문---

select rno, userid, username, email, gender 
from (
    select rownum AS rno, userid, username, email, gender 
    from (
        select userid, username, email, gender 
        from tbl_member
        where userid != 'admin' 
        order by registerday desc
    ) V
) T
where T.rno between 6 and 10;
------------------------------------

select pd_extraimg, pdimg1, pdno, brand, price, saleprice from tbl_product a JOIN tbl_product_img b
ON a.pdno = b.fk_pdno;

select * from tbl_product_img;
select * from tbl_product;

select pdname, pdimg1, price
from tbl_product 
where pdname IN ( 'DAYTONA' );


select pdname, pdimg1, price
from tbl_product 
where pdno IN ( '103', '104' );

select color from tbl_pd_detail where fk_pdno = 99; 

---------------------------- 상품상세 쿼리
select pdno, pdname, brand, price, saleprice, pdimg1, pd_content
from tbl_product; -- 상품 테이블

select fk_pdno, color from tbl_pd_detail; --색상 테이블

select pd_extraimg from tbl_product_img
where fk_pdno = 95;

select pdno, pdname, brand, price, saleprice, pdimg1, pd_content, NVL(color, '없음') as color
from (
    select pdno, pdname, brand, price, saleprice, pdimg1, pd_content
    from tbl_product
    where pdno = 95
) A
join tbl_pd_detail B
on a.pdno = b.fk_pdno;
-- 색상이 없을 경우 없음 처리


select pdno, pdname, brand, pdimg1, price, saleprice, pd_content,  color 
from ( 
select pdno, pdname, brand, price, saleprice, pdimg1, pd_content 
 from tbl_product 
where pdno =  95
 ) A 
join tbl_pd_detail B 
 on a.pdno = b.fk_pdno;
-- 재고 where 조건문 추가필

select color 
from tbl_pd_detail
where fk_pdno = 99;

select *
from tbl_pd_detail;


SELECT pd_detailno
FROM tbl_product A
JOIN tbl_pd_detail B
ON A.pdno = B.fk_pdno
WHERE (A.pdno = 95 AND B.color LIKE 'pink')
   OR (A.pdno = 99 AND B.color LIKE 'none');
   
SELECT * FROM tbl_cart;

insert into tbl_cart(cartno, fk_userid, cart_qty, registerday, fk_pd_detailno ) 
       values(person_seq.nextval, ?, ?, ?, ?);

select userid, username, email, mobile, postcode, address, detail_address, extra_address, gender 
		 , birthday, to_char(registerday, 'yyyy-mm-dd') AS registerday 
from tbl_member;

--------------------------------------------------------------------------------------------

select *
from tbl_cart 
where fk_userid = 'nime0110' and fk_pd_detailno = ? 

select * 
from tbl_pd_detail
 where fk_pdno IN ( 118 ) and  pd_qty > 0; 
 
 
select pdname, pdimg1, saleprice, pdno 
from tbl_pd_detail 
 where fk_pdno = 172 and color = 'none';
 
select pdname, pdimg1, saleprice, pdno 
from tbl_product

select pd_detailno, fk_pdno, color
from tbl_pd_detail 

---------------------------------

select pdname, pdimg1, saleprice, pdno 
from tbl_product A JOIN tbl_pd_detail B
ON A.pdno = B.fk_pdno;

select pd_detailno, fk_pdno, color
from tbl_pd_detail 

select *
from tbl_review;

select fk_pdno, fk_userid, review_content, starpoint, review_date		        
from tbl_review 
where fk_pdno = 112;

select fk_pdno, fk_userid, review_content, starpoint		        
from tbl_review 
where fk_pdno = 112;

SELECT fk_pdno, AVG(starpoint) 
FROM tbl_review
GROUP BY fk_pdno;

----------------------
      
select fk_pdno, fk_userid, review_content, starpoint		        
from tbl_review A
where fk_pdno = 112;

SELECT fk_pdno, AVG(starpoint) 
FROM tbl_review B
GROUP BY fk_pdno;      

-------------------------------------------------

select A.fk_pdno, A.fk_userid, A.review_content, A.starpoint, B.avg_starpoint, B.reviewcount, to_char(to_date(A.review_date, 'yyyy-mm-dd'),'yy-mm-dd')     
from tbl_review A
JOIN
(SELECT fk_pdno, trunc(AVG(starpoint),1) as avg_starpoint, COUNT(review_content) as reviewcount
FROM tbl_review 
GROUP BY fk_pdno) B
ON A.fk_pdno = B.fk_pdno
where A.fk_pdno = 112;

select * from tbl_product
where pdno = 112;

------- 리뷰 페이징 처리 ----- 3명씩 보이게 함
select ceil(count(*)/3) 
from tbl_review 
where fk_pdno = 112;
;
 


select rno, userid, username, email, gender 
from (
    select rownum AS rno, userid, username, email, gender 
    from (
        select userid, username, email, gender 
        from tbl_review 
        where userid != 'admin' 
        order by registerday desc
    ) V
) T
where T.rno between 6 and 10;
   

SELECT rno, fk_pdno, fk_userid, review_content, starpoint, avg_starpoint, reviewcount, review_date
FROM (
    SELECT rownum AS rno, A.fk_pdno, A.fk_userid, A.review_content, A.starpoint, B.avg_starpoint, B.reviewcount, 
           TO_CHAR(TO_DATE(A.review_date, 'yyyy-mm-dd'), 'yy-mm-dd') AS review_date
    FROM tbl_review A
    JOIN (
        SELECT fk_pdno, TRUNC(AVG(starpoint), 1) AS avg_starpoint, COUNT(review_content) AS reviewcount
        FROM tbl_review 
        GROUP BY fk_pdno
    ) B ON A.fk_pdno = B.fk_pdno
    WHERE A.fk_pdno = 112
) T
WHERE T.rno BETWEEN 1 AND 3;

select reviewno from tbl_review
where fk_pdno = 132 and fk_userid = 'nime0110';

delete from tbl_review where fk_pdno = 132 and fk_userid = 'nime0110';
commit;

select * from tbl_review;

--- 리뷰수정 쿼리 ---
UPDATE tbl_review
   SET review_content = '엥? 시계 별론데요',
       starpoint = 1
 WHERE fk_pdno = 178 and fk_userid = 'nime0110';
 commit;
                