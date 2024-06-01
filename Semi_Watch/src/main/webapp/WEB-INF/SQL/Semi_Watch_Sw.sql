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

desc tbl_cart;

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

commit;

desc tbl_order;

select *
from tbl_order;

select ordercode, total_price, total_orderdate
from tbl_order
where fk_userid = 'yuseonwoo';

select *
from tbl_product;

select *
from tbl_order;

--insert into tbl_cart(cartno, fk_userid, fk_pdno, cart_qty)
--values(seq_tbl_cart_cartno.nextval, 'yuseonwoo', '117' , 1);
desc tbl_cart;


--insert into tbl_order(ordercode,fk_userid, total_price ,total_orderdate)
--values('1','yuseonwoo',20000, default);

commit;

select *
from tbl_order
where fk_userid='yuseonwoo';

select *
from tbl_orderdetail;

select C.cartno, C.fk_userid, C.fk_pdno, C.cart_qty, P.pdname, P.pdimg1, P.saleprice 
FROM 
(   select cartno, fk_userid, fk_pdno, cart_qty , registerday
    from tbl_cart
    where fk_userid = 'yuseonwoo' 
)C 
JOIN tbl_product P 
on C.fk_pdno = P.pdno
order by C.cartno desc;

-- 주문 코드, 주문 가격, 주문갯수, 총 금액, 주문 날짜. 
-- 주문내역 1차로 조인한 것
SELECT ORDERCODE, ORDER_PRICE, ORDER_QTY, TOTAL_PRICE, TOTAL_ORDERDATE
from 
(
select *
from tbl_order
where fk_userid='yuseonwoo'
)O
JOIN tbl_orderdetail D
on O.fk_userid = D.fk_userid;
------------------------------------------------------------------------------

---------------tbl_orderdetail이랑 tbl_pd_detail 조인해온것임 여기서
-- 상품상세번호, 상품 번호, 컬러 가져올 수 있음 2차 조인
select pd_detailno, fk_pdno, color
from 
(
select *
from tbl_pd_detail 
) T
JOIN tbl_orderdetail D
ON D.fk_pd_detailno = T.pd_detailno;

-- tbl_product 랑 tbl_pd_detail
-- 조인한것 3차 조인
select pdno, pdname, saleprice
from 
(
select *
from tbl_pd_detail
) T
join tbl_product U
on T.fk_pdno = U.pdno;


----- 조인 (주문상세 / 상품상세 조인)

DESC tbl_orderdetail;
DESC tbl_pd_detail;

select fk_pdno, color
from 
(
select * 
from tbl_orderdetail
)A
JOIN tbl_pd_detail B
ON A.FK_PD_DETAILNO = B.PD_DETAILNO;

-- tbl_pd_detail 이랑 tbl_product 조인

select pdname, pdno, saleprice
from
(
select *
from tbl_pd_detail
)B
join tbl_product C
on B.fk_pdno = C.pdno;


SELECT B.pdname, B.pdno, B.saleprice, A.color
FROM 
(
    SELECT * 
    FROM tbl_orderdetail
) A
JOIN tbl_pd_detail C ON A.FK_PD_DETAILNO = C.PD_DETAILNO
JOIN tbl_product B ON C.fk_pdno = B.pdno;

-- tbl_product / tbl_pd_detail / tbl_orderdetail 합친거
SELECT B.pdname, B.pdno, B.saleprice, C.color
FROM 
(
    SELECT * 
    FROM tbl_orderdetail
) A
JOIN tbl_pd_detail C ON A.FK_PD_DETAILNO = C.PD_DETAILNO
JOIN tbl_product B ON C.fk_pdno = B.pdno;






desc tbl_order;
desc tbl_orderdetail;
desc tbl_pd_detail;
desc tbl_product;

------------------------------------------------------------------------------------------------------------------
-- 주문내역에서 최종적으로 들어와야 하는 컬럼들(조인 끝남)
select ordercode, total_price, total_orderdate, fk_pdno, color, pdname 
from 
(
    select ordercode, total_price, total_orderdate, fk_pdno, color
    from 
    (
    select ordercode, total_price, total_orderdate, fk_pd_detailno
    from 
    (
        select ordercode, total_price, total_orderdate  
        from tbl_order
        where fk_userid = 'yuseonwoo'
    ) A
    join tbl_orderdetail B
    on A.ordercode = B.fk_ordercode
) C
join tbl_pd_detail D
on C.fk_pd_detailno = D.pd_detailno
) E 
join tbl_product F
on E.fk_pdno = F.pdno;

----------------------------------------------------------------------------------------------------

desc tbl_order;
desc tbl_orderdetail;
desc tbl_pd_detail;
desc tbl_product;

select *
from tbl_order;

select *
from tbl_orderdetail;

select *
from tbl_product;

select *
from tbl_pd_detail;

desc tbl_order;


--insert into tbl_order(ordercode,fk_userid, total_price ,total_orderdate)
--values('1','yuseonwoo',20000, default);
insert into tbl_order(ordercode,fk_userid, total_price ,total_orderdate)
values('2','yuseonwoo',30000, default);

insert into tbl_order(ordercode,fk_userid, total_price ,total_orderdate)
values('3','yuseonwoo',40000, default);

insert into tbl_order(ordercode,fk_userid, total_price ,total_orderdate)
values('4','yuseonwoo',50000, default);


insert into tbl_order(ordercode,fk_userid, total_price ,total_orderdate)
values('5','yuseonwoo',60000, default);


insert into tbl_order(ordercode,fk_userid, total_price ,total_orderdate)
values('6','yuseonwoo',70000, default);

insert into tbl_order(ordercode,fk_userid, total_price ,total_orderdate)
values('7','yuseonwoo',80000, default);

insert into tbl_order(ordercode,fk_userid, total_price ,total_orderdate)
values('8','yuseonwoo',90000, default);

insert into tbl_order(ordercode,fk_userid, total_price ,total_orderdate)
values('9','yuseonwoo',100000, default);

insert into tbl_order(ordercode,fk_userid, total_price ,total_orderdate)
values('10','yuseonwoo',110000, default);

commit;

select *
from tbl_order;
----------------------------------------------------tbl_order 예시 인서트 끝------------------------

desc tbl_orderdetail;

select *
from tbl_order;

delete from tbl_order
where fk_userid='yuseonwoo';

commit;

select *
from user_sequences;
 


select*
from tbl_orderdetail;

select*
from tbl_pd_detail;

select *
from tbl_product;

select *
from tbl_member;
--------------------------------------------------------------------------------------------------------------------
select *
from user_sequences;

--insert into tbl_cart(cartno, fk_userid, fk_pdno, cart_qty)
--values(seq_tbl_cart_cartno.nextval, 'yuseonwoo', '117' , 1);



insert into tbl_order(ordercode, fk_userid, total_price, total_orderdate)
values(seq_tbl_order_ordercode.nextval, 'yuseonwoo', 100000, default);

select * 
from tbl_order;

insert into tbl_order(ordercode, fk_userid, total_price, total_orderdate)
values(seq_tbl_order_ordercode.nextval, 'yuseonwoo', 100000, default);-- insert

insert into tbl_order(ordercode, fk_userid, total_price, total_orderdate)
values(seq_tbl_order_ordercode.nextval, 'yuseonwoo', 200000, default);

insert into tbl_order(ordercode, fk_userid, total_price, total_orderdate)
values(seq_tbl_order_ordercode.nextval, 'yuseonwoo', 300000, default);

insert into tbl_order(ordercode, fk_userid, total_price, total_orderdate)
values(seq_tbl_order_ordercode.nextval, 'yuseonwoo', 400000, default);

insert into tbl_order(ordercode, fk_userid, total_price, total_orderdate)
values(seq_tbl_order_ordercode.nextval, 'yuseonwoo', 500000, default);

commit;


select *
from tbl_order;


----------------------------------------------------------- tbl_order다시 삽입---------------------------

--insert into tbl_cart(cartno, fk_userid, fk_pdno, cart_qty)
--values(seq_tbl_cart_cartno.nextval, 'yuseonwoo', '117' , 1);
desc tbl_orderdetail;

select *
from tbl_member
where username = '유선우';

select *
from tbl_orderdetail;

select *
from user_sequences;

select *
from tbl_order

select *
from tbl_pd_detail;


insert into tbl_orderdetail(order_detailno, fk_userid, fk_ordercode, order_qty, delivery_status, delivery_date, order_price, fk_pd_detailno)
values(seq_tbl_od_odno.nextval, 'yuseonwoo', '1', 1, null, default, 1000,'48'); -- 삽입성공

insert into tbl_orderdetail(order_detailno, fk_userid, fk_ordercode, order_qty, delivery_status, delivery_date, order_price, fk_pd_detailno)
values(seq_tbl_od_odno.nextval, 'yuseonwoo', '2', 2, null, default, 2000,'49');

insert into tbl_orderdetail(order_detailno, fk_userid, fk_ordercode, order_qty, delivery_status, delivery_date, order_price, fk_pd_detailno)
values(seq_tbl_od_odno.nextval, 'yuseonwoo', '3', 3, null, default, 3000,'50');

insert into tbl_orderdetail(order_detailno, fk_userid, fk_ordercode, order_qty, delivery_status, delivery_date, order_price, fk_pd_detailno)
values(seq_tbl_od_odno.nextval, 'yuseonwoo', '4', 4, null, default, 4000,'51');

insert into tbl_orderdetail(order_detailno, fk_userid, fk_ordercode, order_qty, delivery_status, delivery_date, order_price, fk_pd_detailno)
values(seq_tbl_od_odno.nextval, 'yuseonwoo', '5', 5, null, default, 5000,'52');

commit;

select * 
from tbl_orderdetail;


select ordercode, E.fk_userid as fk_userid , total_price, total_orderdate, fk_pdno, color, pdname 
    				    from 
    				   ( 
    				      select ordercode, C.fk_userid, total_price, total_orderdate, fk_pdno, color 
    				     from 
    				      ( 
    				     select ordercode, A.fk_userid, total_price, total_orderdate, fk_pd_detailno 
    				     from 
    				     ( 
    				         select ordercode, fk_userid, total_price, total_orderdate 
    				         from tbl_order 
                          ) A 
    				     join tbl_orderdetail B 
    				      on A.ordercode = B.fk_ordercode 
    				   ) C 
    				   join tbl_pd_detail D 
    				    on C.fk_pd_detailno = D.pd_detailno 
    				    ) E 
    				    join tbl_product F
    				    on E.fk_pdno = F.pdno;
                        
                        
select username
from tbl_member
where userid = ?;



                        