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
FROM all_tables;


SELECT * 
FROM user_tables;

select *
from tbl_member;

update tbl_member set username = '강지훈2'
where userid = 'jhkvng';

rollback;
/*
address
detail_address
extra_address
postcode
*/


update tbl_member set pw = 'qwer1234@', lastpwdchangedate = sysdate
where userid = 'jhkvng123';

rollback;

select *
from tbl_member
where userid = 'admin';

select *
from TBL_PRODUCT;

select *
from TBL_CART;

select *
from USER_SEQUENCES;

insert into tbl_main_image(imgno, imgfilename) values(seq_main_image.nextval, '미샤.png');  

insert into TBL_CART(cartno, fk_pdno, fk_userid, cart_qty, registerday)
            values(SEQ_TBL_CART_CARTNO.nextval, '2', 'jhkvng123', 1, default);


-- 테이블당 컬럼 코멘트조회 --
    select column_name, comments
    from user_col_comments
    where table_name = '테이블명';
    
    
    
 
-- 테이블 코멘트조회 --
    select *
    from user_tab_comments;

-- 생성된 시퀀스 조회 --
    select * from user_tab_comments;

select color 
from tbl_pd_detail
where fk_pdno = 95;


commit; -- 커밋 필수

select *
from tbl_pd_detail;


tbl_product


select color, pd_qty
from tbl_pd_detail
where fk_pdno = 95;


select *
from TBL_CART;

select * 
from TBL_PD_DETAIL;

select cartno, pdname, brand, pd_detailno, color, saleprice, pd_qty, cart_qty, pdimg1, point, pdno
from tbl_cart C join tbl_pd_detail D
on C.fk_pd_detailno = D.pd_detailno
join tbl_product P on
D.fk_pdno = P.pdno
where C.fk_userid = 'jhkvng123';

SELECT PDNO, PD_DETAILNO, PDNAME, SALEPRICE, PDIMG1, COLOR
FROM
(
select pdno, pdname, saleprice, pdimg1
from tbl_product
where pdno = 117
) P
JOIN tbl_pd_detail D
ON P.pdno = D.fk_pdno
WHERE D.pd_detailno = 49;
