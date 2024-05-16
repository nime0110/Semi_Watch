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
	money NUMBER(10) DEFAULT 0, /* 충전금액 */
    CONSTRAINT PK_TBL_MEMBER_USER_ID PRIMARY KEY (USER_ID), 
    CONSTRAINT UQ_TBL_MEMBER_MOBILE UNIQUE(MOBILE),
    CONSTRAINT UQ_TBL_MEMBER_EMAIL UNIQUE(EMAIL),
    CONSTRAINT CK_TBL_MEMBER_GENDER check(GENDER in(1,2)),
    CONSTRAINT CK_TBL_MEMBER_STATUS check(STATUS in(0,1)),
    CONSTRAINT CK_TBL_MEMBER_IDLE check(PUBLIC_STATUS in(0,1))
);

/* 상품 */
CREATE TABLE tbl_product (
	pdno VARCHAR2(30) NOT NULL, /* 상품코드 */
	pdname NVARCHAR2(30) NOT NULL, /* 상품명 */
	brand NVARCHAR2(20), /* 상품브랜드 */
	price NUMBER(10) DEFAULT 0 NOT NULL, /* 상품정가 */
	saleprice NUMBER(10) DEFAULT 0, /* 상품판매가 */
	pdimg1 VARCHAR2(50) DEFAULT noimg.png, /* 상품이미지 */
	category NVARCHAR2(20) NOT NULL, /* 상품카테고리 */
	pdinputdate DATE DEFAULT sysdate NOT NULL, /* 상품등록일자 */
	pdstatus NUMBER(1), /* 상품상태 */
	color NVARCHAR2(10), /* 상품색상 */
	pd_content NVARCHAR2(200), /* 상품상세내용 */
	pd_qty NUMBER(10) DEFAULT 1, /* 재고수량 */
    CONSTRAINT PK_TBL_PRODUCT_PDNO PRIMARY KEY (PDNO)
);

/* 주문 */
CREATE TABLE tbl_order (
	ordercode VARCHAR2(30) NOT NULL, /* 주문코드 */
	fk_userid VARCHAR2(20), /* 아이디 */
	total_price NUMBER(10) DEFAULT 0, /* 주문총액 */
	total_orderdate DATE DEFAULT sysdate, /* 주문일자 */
    CONSTRAINT PK_tbl_order_ordercode PRIMARY KEY (ordercode),
    CONSTRAINT FK_tbl_order_FK_USERID FOREIGN KEY(FK_USERID) REFERENCES TBL_MEMBER(USERID)
);

/* 로그인기록 */
CREATE TABLE tbl_loginhistory (
	fk_userid VARCHAR2(20) NOT NULL, /* 아이디 */
	logindate DATE NOT NULL, /* 로그인시각 */
	clientip VARCHAR2(20) NOT NULL, /* 접속ip주소 */
    CONSTRAINT FK_tbl_loginhistory_fk_userid FOREIGN KEY(fk_userid) REFERENCES TBL_MEMBER(USERID)
);

/* 상품추가이미지 */
CREATE TABLE tbl_product_img (
	img_no NUMBER NOT NULL, /* 이미지번호 */
	fk_pdno VARCHAR2(30), /* 상품코드 */
	pd_extraimg VARCHAR2(50), /* 상품추가이미지 */
    CONSTRAINT PK_TBL_PRODUCT_IMG_IMG_NO PRIMARY KEY (IMG_NO),
    CONSTRAINT FK_TBL_PRODUCT_IMG_FK_PDNO FOREIGN KEY(FK_PDNO) REFERENCES TBL_PRODUCT(PDNO)
);

/* 장바구니 */
CREATE TABLE tbl_cart (
	cartno VARCHAR2(30) NOT NULL, /* 장바구니코드 */
	fk_pdno VARCHAR2(30), /* 상품코드 */
	fk_userid VARCHAR2(20), /* 아이디 */
	cart_qty NUMBER(5) DEFAULT 0, /* 장바구니수량 */
    CONSTRAINT PK_TBL_CART_CARTNO PRIMARY KEY (CARTNO),
    CONSTRAINT FK_TBL_CART_FK_USERID FOREIGN KEY(FK_USERID) REFERENCES TBL_MEMBER(USERID),
    CONSTRAINT FK_TBL_CART_FK_PDNO FOREIGN KEY(FK_PDNO) REFERENCES TBL_PRODUCT(PDNO)
);

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
    CONSTRAINT PK_TBL_ORDERDETAIL_ORDER_DETAILNO PRIMARY KEY (ORDER_DETAILNO),
    CONSTRAINT FK_TBL_ORDERDETAIL_FK_USERID FOREIGN KEY(FK_USERID) REFERENCES TBL_MEMBER(USERID),
    CONSTRAINT FK_TBL_ORDERDETAIL_FK_PDNO FOREIGN KEY(FK_PDNO) REFERENCES TBL_PRODUCT(PDNO),
    CONSTRAINT FK_TBL_ORDERDETAIL_FK_ORDERCODE FOREIGN KEY(FK_ORDERCODE) REFERENCES TBL_ORDER(ORDERCODE)
);

/* 사용자리뷰 */
CREATE TABLE tbl_review (
	reviewno VARCHAR2(30) NOT NULL, /* 리뷰코드 */
	fk_pdno VARCHAR2(30) NOT NULL, /* 상품코드 */
	fk_userid VARCHAR2(20), /* 아이디 */
	review_content VARCHAR2(100), /* 리뷰내용 */
	starpoint NUMBER(1) DEFAULT 0, /* 별점 */
	review_date DATE, /* 리뷰작성일자 */
    CONSTRAINT PK_tbl_review_reviewno PRIMARY KEY (reviewno),
    CONSTRAINT FK_tbl_review_FK_USERID FOREIGN KEY(FK_USERID) REFERENCES TBL_MEMBER(USERID),
    CONSTRAINT FK_tbl_review_FK_PDNO FOREIGN KEY(FK_PDNO) REFERENCES TBL_PRODUCT(PDNO),
    CONSTRAINT CK_tbl_review_starpoint CHECK (starpoint BETWEEN 1 AND 5)
);







