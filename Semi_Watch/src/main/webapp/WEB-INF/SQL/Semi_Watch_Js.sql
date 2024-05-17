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
    CONSTRAINT PK_TBL_MEMBER_USERID PRIMARY KEY (USERID), -- userid 기본키 설정
    CONSTRAINT UQ_TBL_MEMBER_MOBILE UNIQUE(MOBILE),         -- mobile 유니크키 설정
    CONSTRAINT UQ_TBL_MEMBER_EMAIL UNIQUE(EMAIL),           -- email 유니크키 설정
    CONSTRAINT CK_TBL_MEMBER_GENDER check(GENDER in(1,2)),  -- gender 1,2 체크인제약 (1 남자, 2 여자)
    CONSTRAINT CK_TBL_MEMBER_STATUS check(STATUS in(0,1)),  -- status 0,1 체크인제약 (1 정상회원, 0 탈퇴회원)
    CONSTRAINT CK_TBL_MEMBER_IDLE check(idle in(0,1)) -- idle 0,1 체크인제약 (1 활동회원, 0 휴면회원)
);
-- Table TBL_MEMBER이(가) 생성되었습니다.


    
    

/* 상품 */
CREATE TABLE tbl_product (
	pdno VARCHAR2(30) NOT NULL, /* 상품코드 */
	pdname NVARCHAR2(30) NOT NULL, /* 상품명 */
	brand NVARCHAR2(20), /* 상품브랜드 */
	price NUMBER(10) DEFAULT 0 NOT NULL, /* 상품정가 */
	saleprice NUMBER(10) DEFAULT 0, /* 상품판매가 */
	pdimg1 VARCHAR2(50) DEFAULT 'noimg.png', /* 상품이미지 */
	pd_content NVARCHAR2(1000), /* 상품상세내용 */
	category NVARCHAR2(20) NOT NULL, /* 상품카테고리 */
	pdinputdate DATE DEFAULT sysdate NOT NULL, /* 상품등록일자 */
	pdstatus NUMBER(1) DEFAULT 1, /* 상품상태 */
    point number(8) default 0 , /* 구매시 적립 포인트 */
    CONSTRAINT PK_TBL_PRODUCT_PDNO PRIMARY KEY (pdno) -- pdno 기본키 설정
);
-- Table TBL_PRODUCT이(가) 생성되었습니다.



/* 상품상세 */
CREATE TABLE tbl_pd_detail (
	pd_detailno VARCHAR2(30) NOT NULL, /* 상품상세코드 */
	fk_pdno VARCHAR2(30), /* 상품코드 */
	color NVARCHAR2(10), /* 상품색상 */
	pd_qty NUMBER(10), /* 재고수량 */
    CONSTRAINT PK_tbl_pd_detail_pd_detailno PRIMARY KEY (pd_detailno), -- 상품상세코드 기본키설정
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
	logindate DATE NOT NULL, /* 로그인시각 */
	clientip VARCHAR2(20) NOT NULL, /* 접속ip주소 */
    CONSTRAINT FK_tbl_loginhistory_fk_userid FOREIGN KEY(fk_userid) REFERENCES TBL_MEMBER(USERID) -- 회원테이블의 아이디를 외래키로 받음
);
-- Table TBL_LOGINHISTORY이(가) 생성되었습니다.

/* 상품추가이미지 */
CREATE TABLE tbl_product_img (
	img_no NUMBER NOT NULL, /* 이미지번호 */
	fk_pdno VARCHAR2(30), /* 상품코드 */
	pd_extraimg VARCHAR2(50), /* 상품추가이미지 */
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
-- Table TBL_ORDERDETAIL이(가) 생성되었습니다.

/* 사용자리뷰 */
CREATE TABLE tbl_review (
	reviewno VARCHAR2(30) NOT NULL, /* 리뷰코드 */
	fk_pdno VARCHAR2(30) NOT NULL, /* 상품코드 */
	fk_userid VARCHAR2(20), /* 아이디 */
	review_content VARCHAR2(100), /* 리뷰내용 */
	starpoint NUMBER(1) DEFAULT 0, /* 별점 */
	review_date DATE, /* 리뷰작성일자 */
    review_status NUMBER(1) DEFAULT 0, /* 리뷰 */
    CONSTRAINT PK_tbl_review_reviewno PRIMARY KEY (reviewno), -- 리뷰코드 기본키설정
    CONSTRAINT FK_tbl_review_FK_USERID FOREIGN KEY(FK_USERID) REFERENCES TBL_MEMBER(USERID), -- 회원테이블의 아이디를 외래키로 받음
    CONSTRAINT FK_tbl_review_FK_PDNO FOREIGN KEY(FK_PDNO) REFERENCES TBL_PRODUCT(PDNO), -- 상품테이블의 상품번호를 외래키로 받음
    CONSTRAINT CK_tbl_review_starpoint CHECK (starpoint BETWEEN 1 AND 5); -- 별점 체크제약 1부터 5까지만
-- Table TBL_REVIEW이(가) 생성되었습니다.


    insert into tbl_member(userid, pw, username, email, mobile, address, detail_address, extra_address, postcode,  gender, birthday) 
    values('youinna', '9695b88a59a1610320897fa84cb7e144cc51f2984520efb77111d94b402a8382', '유인나', 'Uqqfgdi052vJc9GbT3XIhooUlaQvPVtsd04skgjrpIg=', 'FBd1MwxTJeUlm/KdsyMc0w==', 
           '경기 군포시 오금로 15-17', '101동 102호', ' (금정동)', '15864', '2', '2001-10-11');
           -- 1 행 이(가) 삽입되었습니다.
    commit;
    -- 커밋 완료.
    
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content , category, point)
    values(seq_tbl_product_pdno.nextval, 'GA-2100-1A3DR', 'G-SHOCK', 145000, 120000, seq_tbl_product_pdno.nextval || '_thum.png',
    '터프니스를 추구하여 진화를 이어온 G-SHOCK에서, 블랙을 베이스로 네온 컬러를 악센트 컬러로 채용한 NEON ACCENT 시리즈를 소개합니다. 블랙을 베이스로 한 시크한 외관 디자인에 더한, 네온 컬러의 그린을 다이얼의 곳곳에 사용해, 매력적인 느낌을 선사함과 동시에 시인성을 높이고 있습니다. 옥타곤 베젤이 특징적인 베스트 셀러 GA-2100의 컬러 바리에이션으로, 쿨한 컬렉션을 추가했습니다. 아웃도어 씬을 넘어 스트릿 패션에도 최적화된 디자인과 컬러감으로 모든 스타일에 스타일링하기 매력적인 시즌 칼라입니다.'
    , '남성용' , 1200);
    -- 1 행 이(가) 삽입되었습니다.

    commit;
    -- 커밋 완료.
    

    create sequence seq_tbl_product_pdno
    start with 1
    increment by 1
    nomaxvalue
    nominvalue
    nocycle
    nocache;
    -- Sequence SEQ_TBL_PRODUCT_pdno이(가) 생성되었습니다.
    
    select * from tbl_product;
    
    
