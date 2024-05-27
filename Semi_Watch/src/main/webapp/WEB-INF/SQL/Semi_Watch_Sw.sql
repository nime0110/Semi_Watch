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



--------------------------------------------------------------------------------------------------

select *
from tbl_product;


select pdname, brand, price, saleprice, pdimg1
from tbl_product
where pdno in(1,2,3);

 select *
 from user_tab_comments;
 
 
  -- 테이블당 컬럼 코멘트조회 --
     select column_name, comments
     from user_col_comments
     where table_name = '테이블명';
     
    -- 테이블 코멘트조회 --
     select *
     from user_tab_comments;

-- 생성된 시퀀스 조회 --
select * from user_tab_comments;


-- 상품번호 1,2,3 장바구니에 넣기 시험해봄 --
select pdname, brand, price, saleprice, pdimg1
from tbl_product 
where pdno in(1,2,3);

desc tbl_product;

 select *
 from tbl_cart;
 
 select *
 from tbl_member;
 
 select *
 from tbl_product;

-- 장바구니 담기
-- 장바구니에 이미 제품이 들어있다는 전제로, 넣어주는 제품이 이미 있는 것인지 아닌지를 확인시켜 주는것
-- 이미 담겨있는 제품에 수량 + 해주기

update tbl_cart set cart_qty = cart_qty + ?
where cartno = ?;	

-- 아예 장바구니에 없는 제품 넣어주는것
insert into tbl_cart(cartno, fk_userid, fk_pdno, cart_qty, registerday)
values(seq_tbl_cart_cartno.nextval, 'yuseonwoo', '1', '12', default);

select *
from tbl_cart;





-- 내 이름으로 장바구니 목록 만들어보기
SELECT C.cartno, C.fk_userid, C.fk_pdno, C.cart_qty, P.pdname, P.pdimg1, P.saleprice
FROM
(
    SELECT cartno, fk_pdno, fk_userid, cart_qty, registerday
    FROM tbl_cart
    WHERE fk_userid = 'yuseonwoo'
) C
JOIN tbl_product P
ON C.fk_pdno = P.pdno
ORDER BY C.cartno DESC;
 
select * 
from tbl_cart;

select C.cartno, C.fk_userid, C.fk_pdno, C.cart_qty, P.pdname, P.pdimg1, P.saleprice 
FROM 
(   select cartno, fk_userid, fk_pdno, cart_qty , registerday
    from tbl_cart
    where fk_userid = 'yuseonwoo' 
)C 
JOIN tbl_product P 
on C.fk_pdno = P.pdno
order by C.cartno desc;

insert into tbl_cart(cartno, fk_userid, fk_pdno, cart_qty)
values(seq_tbl_cart_cartno.nextval, 'yuseonwoo', '117' , 1);

