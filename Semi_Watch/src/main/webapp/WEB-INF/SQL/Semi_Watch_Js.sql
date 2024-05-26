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

/* 회원 */
CREATE TABLE tbl_member (
	userid VARCHAR2(20) NOT NULL, /* 아이디 */
    username NVARCHAR2(20) NOT NULL, /*  */
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
    userimg varchar2(200) default 'usernomal.jpg',
    CONSTRAINT PK_TBL_MEMBER_USERID PRIMARY KEY (USERID), -- userid 기본키 설정
    CONSTRAINT UQ_TBL_MEMBER_MOBILE UNIQUE(MOBILE),         -- mobile 유니크키 설정
    CONSTRAINT UQ_TBL_MEMBER_EMAIL UNIQUE(EMAIL),           -- email 유니크키 설정
    CONSTRAINT CK_TBL_MEMBER_GENDER check(GENDER in(1,2)),  -- gender 1,2 체크인제약 (1 남자, 2 여자)
    CONSTRAINT CK_TBL_MEMBER_STATUS check(STATUS in(0,1)),  -- status 0,1 체크인제약 (1 정상회원, 0 탈퇴회원)
    CONSTRAINT CK_TBL_MEMBER_IDLE check(idle in(0,1)) -- idle 0,1 체크인제약 (1 활동회원, 0 휴면회원)
);
-- Table TBL_MEMBER이(가) 생성되었습니다.

alter table tbl_member drop constraint UQ_TBL_MEMBER_MOBILE;
alter table tbl_member drop constraint UQ_TBL_MEMBER_EMAIL;
/* 상품 */
CREATE TABLE tbl_product (
	pdno VARCHAR2(30) NOT NULL, /* 상품코드 */
	pdname NVARCHAR2(30) NOT NULL, /* 상품명 */
	brand NVARCHAR2(20), /* 상품브랜드 */
	price NUMBER(10) DEFAULT 0 NOT NULL, /* 상품정가 */
	saleprice NUMBER(10) DEFAULT 0, /* 상품판매가 */
	pdimg1 VARCHAR2(100) DEFAULT 'noimg.png', /* 상품이미지 */
	pd_content NVARCHAR2(1000), /* 상품상세내용 */
    pd_contentimg VARCHAR2(100), /* 상품상세이미지*
	pdinputdate DATE DEFAULT sysdate NOT NULL, /* 상품등록일자 */
	pdstatus NUMBER(1) DEFAULT 1, /* 상품상태 */
    point number(8) default 0 , /* 구매시 적립 포인트 */
    CONSTRAINT PK_TBL_PRODUCT_PDNO PRIMARY KEY (pdno) -- pdno 기본키 설정
);
-- Table TBL_PRODUCT이(가) 생성되었습니다.

/* 상품상세 */
CREATE TABLE tbl_pd_detail (
	pd_detailno VARCHAR2(30) NOT NULL, /* 상품상세일련번호 */
	fk_pdno VARCHAR2(30), /* 상품코드 */
	color NVARCHAR2(10), /* 상품색상 */
	pd_qty NUMBER(10), /* 재고수량 */
    CONSTRAINT PK_tbl_pd_detail_pd_detailno PRIMARY KEY (pd_detailno), -- 상품상세일련번호 기본키설정
    CONSTRAINT FK_tbl_pd_detail_fk_pdno FOREIGN KEY(fk_pdno) REFERENCES tbl_product(pdno) -- 상품테이블의 기본키를 외래키로 받음
);
-- Table TBL_PD_DETAIL이(가) 생성되었습니다.


/* 주문 */
CREATE TABLE tbl_order (
	ordercode VARCHAR2(30) NOT NULL, /* 주문코드 */
	fk_userid VARCHAR2(20), /* 아이디 */
	total_price NUMBER(10) DEFAULT 0, /* 주문총액 */
	total_orderdate DATE DEFAULT sysdate, /* 주문일자 */
    CONSTRAINT PK_tbl_order_ordercode PRIMARY KEY (ordercode), -- 주문코드 기본키설정
    CONSTRAINT FK_tbl_order_FK_USERID FOREIGN KEY(FK_USERID) REFERENCES TBL_MEMBER(USERID) -- 회원테이블의 아이디를 외래키로 받음
);
-- Table TBL_ORDER이(가) 생성되었습니다.

/* 로그인기록 */
CREATE TABLE tbl_loginhistory (
	fk_userid VARCHAR2(20) NOT NULL, /* 아이디 */
	logindate DATE default sysdate NOT NULL , /* 로그인시각 */
	clientip VARCHAR2(20)default '127.0.0.1' NOT NULL , /* 접속ip주소 */
    CONSTRAINT FK_tbl_loginhistory_fk_userid FOREIGN KEY(fk_userid) REFERENCES TBL_MEMBER(USERID) -- 회원테이블의 아이디를 외래키로 받음
);
-- Table TBL_LOGINHISTORY이(가) 생성되었습니다.




/* 상품추가이미지 */
CREATE TABLE tbl_product_img (
	img_no varchar2 (20) NOT NULL, /* 이미지번호 */
	fk_pdno VARCHAR2(30), /* 상품코드 */
	pd_extraimg VARCHAR2(100), /* 상품추가이미지 */
    CONSTRAINT PK_TBL_PRODUCT_IMG_IMG_NO PRIMARY KEY (IMG_NO), -- 상품설명페이지 들어가는 추가이미지 기본키
    CONSTRAINT FK_TBL_PRODUCT_IMG_FK_PDNO FOREIGN KEY(FK_PDNO) REFERENCES TBL_PRODUCT(PDNO) -- 상품테이블의 상품번호를 외래키로 받음
);
-- Table TBL_PRODUCT_IMG이(가) 생성되었습니다.

/* 장바구니 */
CREATE TABLE tbl_cart (
	cartno VARCHAR2(30) NOT NULL, /* 장바구니코드 */
	fk_pdno VARCHAR2(30), /* 상품코드 */
	fk_userid VARCHAR2(20), /* 아이디 */
	cart_qty NUMBER(5) DEFAULT 0, /* 장바구니수량 */
    CONSTRAINT PK_TBL_CART_CARTNO PRIMARY KEY (CARTNO), -- 장바구니테이블 기본키 설정
    CONSTRAINT FK_TBL_CART_FK_USERID FOREIGN KEY(FK_USERID) REFERENCES TBL_MEMBER(USERID), -- 회원테이블의 아이디를 외래키로 받음
    CONSTRAINT FK_TBL_CART_FK_PDNO FOREIGN KEY(FK_PDNO) REFERENCES TBL_PRODUCT(PDNO) -- 상품테이블의 상품번호를 외래키로 받음
);
-- Table TBL_CART이(가) 생성되었습니다.

/* 주문상세 */
CREATE TABLE tbl_orderdetail (
	order_detailno VARCHAR2(30) NOT NULL, /* 주문상세코드 */
	fk_userid VARCHAR2(20), /* 아이디 */
	fk_pdno VARCHAR2(30) NOT NULL, /* 상품코드 */
	fk_ordercode VARCHAR2(30), /* 주문코드 */
	order_qty NUMBER(5) NOT NULL, /* 주문수량 */
	delivery_status NVARCHAR2(10), /* 배송상태 */
	delivery_date DATE, /* 배송일자 */
	order_price NUMBER(10), /* 주문상품가격 */
    CONSTRAINT PK_TBL_OD_ORDER_DETAILNO PRIMARY KEY (ORDER_DETAILNO), -- 주문상세코드 기본키설정
    CONSTRAINT FK_TBL_OD_FK_USERID FOREIGN KEY(FK_USERID) REFERENCES TBL_MEMBER(USERID), -- 회원테이블의 아이디를 외래키로 받음
    CONSTRAINT FK_TBL_OD_FK_PDNO FOREIGN KEY(FK_PDNO) REFERENCES TBL_PRODUCT(PDNO), -- 상품테이블의 상품번호를 외래키로 받음
    CONSTRAINT FK_TBL_OD_FK_ORDERCODE FOREIGN KEY(FK_ORDERCODE) REFERENCES TBL_ORDER(ORDERCODE) -- 주문테이블의 주문번호를 외래키로 받음
);

/* 사용자리뷰 */
CREATE TABLE tbl_review (
	reviewno VARCHAR2(30) NOT NULL, /* 리뷰코드 */
	fk_pdno VARCHAR2(30) NOT NULL, /* 상품코드 */
	fk_userid VARCHAR2(20), /* 아이디 */
	review_content VARCHAR2(100), /* 리뷰내용 */
	starpoint NUMBER(1) DEFAULT 1, /* 별점 */
	review_date DATE default sysdate, /* 리뷰작성일자 */
    review_status NUMBER(1) DEFAULT 0, /* 리뷰선정코드  */
    CONSTRAINT PK_tbl_review_reviewno PRIMARY KEY (reviewno), -- 리뷰코드 기본키설정
    CONSTRAINT FK_tbl_review_FK_USERID FOREIGN KEY(FK_USERID) REFERENCES TBL_MEMBER(USERID), -- 회원테이블의 아이디를 외래키로 받음
    CONSTRAINT FK_tbl_review_FK_PDNO FOREIGN KEY(FK_PDNO) REFERENCES TBL_PRODUCT(PDNO), -- 상품테이블의 상품번호를 외래키로 받음
    CONSTRAINT CK_tbl_review_starpoint CHECK (starpoint BETWEEN 1 AND 5); -- 별점 체크제약 1부터 5까지만
-- Table TBL_REVIEW이(가) 생성되었습니다.
    
/* 주문배송지 */
CREATE TABLE tbl_delivery (
	ordercode VARCHAR2(30) NOT NULL, /* 주문코드 */
	delivery_name NVARCHAR2(20), /* 받는사람성명 */
	delivery_address VARCHAR2(200), /* 받는사람주소 */
	delivery_detail_address VARCHAR2(200), /* 받는사람 상세주소 */
	delivery_extra_address VARCHAR2(200), /* 받는사람 주소참고항목 */
	delivery_mobile VARCHAR2(200), /* 받는사람 연락처 */
	delivery_email VARCHAR2(200), /* 받는사람 이메일 */
    delivery_msg VARCHAR2(100) default '부재시 연락주세요', /* 배송메시지 */
    CONSTRAINT PK_tbl_delivery PRIMARY KEY (ordercode),
    CONSTRAINT FK_tbl_order_TO_tbl_delivery FOREIGN KEY (ordercode)	REFERENCES tbl_order(ordercode)    
);

CONSTRAINT UK_tbl_delivery_mobile UNIQUE(delivery_mobile), /* 받는사람 연락처 유니크키 */
CONSTRAINT UK_tbl_delivery_email UNIQUE(delivery_email) /* 받는사람 이메일 유니크키 */


COMMENT ON TABLE tbl_delivery IS '주문배송지 테이블';
COMMENT ON COLUMN tbl_delivery.ordercode IS '주문코드(기본키이자 외래키 1:1관계)';
COMMENT ON COLUMN tbl_delivery.delivery_name IS '받는사람성명';
COMMENT ON COLUMN tbl_delivery.delivery_address IS '받는사람주소';
COMMENT ON COLUMN tbl_delivery.delivery_detail_address IS '받는사람 상세주소';
COMMENT ON COLUMN tbl_delivery.delivery_extra_address IS '받는사람 주소참고항목';
COMMENT ON COLUMN tbl_delivery.delivery_mobile IS '받는사람 연락처(주문이기때문에 유니크x)';
COMMENT ON COLUMN tbl_delivery.delivery_email IS '받는사람 이메일(주문이기때문에 유니크x)';
COMMENT ON COLUMN tbl_delivery.delivery_msg IS '배송시에 요청하는 배송메시지 default 부재시 연락주세요';



	
    

    create sequence seq_tbl_product_pdno
    start with 1
    increment by 1
    nomaxvalue
    nominvalue
    nocycle
    nocache;
    -- Sequence SEQ_TBL_PRODUCT_pdno이(가) 생성되었습니다.
    
    
    select rno , pdno, pdname,brand,price,saleprice,pdimg1
    from 
    ( 
        select rownum as rno, pdno, pdname,brand,price,saleprice,pdimg1
        from 
        ( 
            select rownum,pdno, pdname,brand,price,saleprice,pdimg1
            from tbl_product 
            where pdstatus = 1 
            order by pdno desc 
        ) v 
    ) T 
    where T.rno between ? and ? ;
    
    select * from tbl_product where * = 'p';
   


   
   create sequence seq_test_pdno
    start with 1
    increment by 1
    nomaxvalue
    nominvalue
    nocycle
    nocache;
   
   
    select pdno, pdname, pd_content, price, saleprice, pd_qty, color
    from tbl_product P join tbl_pd_detail D
    on P.pdno = D.fk_pdno;
    

 
    
    select pdno, pdname, pd_content, price, saleprice, pd_qty, color
    from tbl_product P join tbl_pd_detail D
    on P.pdno = D.fk_pdno;
    -- 상품의 정보과 재고, 컬러까지 조인해서 결과를 내는 sql문
    
    select pd_extraimg
    from tbl_product_img
    where fk_pdno = 3; 
    -- 상품번호 3번의 추가이미지를 갖고오는 sql문 (3을 ?위치홀더로 쓰면될듯)
 
    ------------------------------------------ 0523
     create sequence seq_tbl_order_ordercode
     start with 1
     increment by 1
     nomaxvalue
     nominvalue
     nocycle
     nocache;
     -- Sequence SEQ_TBL_CART_CARTNO이(가) 생성되었습니다.




  select * from user_sequences;
     
     comment on table TBL_PRODUCT_IMG
     is '상품의 추가이미지(페이지가 상품상세페이지일때)가 담겨있는 테이블';
    
     comment on column TBL_PRODUCT_IMG.PD_DETAILNO
     is '상품상세테이블의 기본키인 상품상세번호 (시퀀스 사용 : SEQ_PRODUCT_IMG)';
    
     comment on column TBL_PRODUCT_IMG.pd_extraimg
     is '해당 상품의 추가 이미지파일명';
    
     comment on column TBL_PD_DETAIL.PD_QTY
     is '상품상세번호별 재고수량(즉, 컬러별 재고수량을 의미함)';
    
     comment on column TBL_PRODUCT_IMG.FK_PDNO
     is '상품번호 tbl_product 테이블의 pdno 컬럼을 참조한다.';
    
     comment on column tbl_orderdetail.ORDER_PRICE
     is '해당 주문의 배송비';
     
     select column_name, comments
     from user_col_comments
     where table_name = 'TBL_REVIEW';
    
    select * from tbl_product_img
    
    -- 테이블당 컬럼 코멘트조회 --
     select column_name, comments
     from user_col_comments
     where table_name = '테이블명';
     
    -- 테이블 코멘트조회 --
     select *
     from user_tab_comments;
    
    
     
     
     select *
     from tbl_cart;
     
     select cartno, fk_userid, fk_pnum, oqty, registerday 
     from tbl_cart
     order by cartno asc;
     
     select * from user_constraints where constraint_type = 'R'
    
    select distinct pd_extraimg, pdno , pdname , brand, pd_content, pdimg1
    from TBL_PRODUCT P join TBL_pd_detail D
    on P.pdno = D.fk_pdno
    join TBL_Product_img I
    on D.fk_pdno = I.fk_pdno;
    
    select *
    from TBL_PRODUCT P right join TBL_pd_detail D
    on P.pdno = D.fk_pdno
    join TBL_Product_img I
    on D.fk_pdno = I.fk_pdno
    where ;
    
    select * from tbl_orderdetail;
    
    
    select * from tbl_product ;
    select * from tbl_pd_detail;
    select rno,  pdno, pdname, brand, saleprice, pdstatus, pd_qty , color
    from
    (
    select rownum as rno , pdno, pdname, brand, saleprice, pdstatus, pd_qty , 
            CASE WHEN color = N'none' THEN N'단일색상' ELSE color END AS color
    from tbl_product P full join tbl_pd_detail D
    on P.pdno = D.fk_pdno
    ) P
    where pdstatus != 0 ;
    
    select * from tbl_pd_detail;
    select * from tbl_product_img where fk_pdno = 105; 

    select pdimg1, pd_contentimg , pdno , pd_extraimg
    from tbl_product P join tbl_product_img I
    on P.pdno = I.fk_pdno
    where pdno = 112;
    
    select
    imgfilename
    from (
    select pd_extraimg as imgfilename from tbl_product_img where fk_pdno = 116 
    union
    select pdimg1 as imgfilename  from tbl_product where pdno = 116
    union
    select pd_contentimg as imgfilename from tbl_product where pdno = 116 
    );

    
     select pdno, pdname , brand , price, saleprice, pdimg1, 
	                  	 pd_content , to_char(pdinputdate, 'yyyy-mm-dd') as pdinputdate,
	                  	 pdstatus , point , pd_contentimg , pd_qty 
	                  	 from tbl_product P join tbl_pd_detail D 
	                  	 on P.pdno = D.fk_pdno 
	                   where pdno = 112;

select * from tbl_product;

update tbl_product set saleprice = 9700000 ,point = 97000 where pdno= 162;
commit;


    

