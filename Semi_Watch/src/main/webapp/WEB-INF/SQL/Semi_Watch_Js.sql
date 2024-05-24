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
	logindate DATE default sysdate NOT NULL , /* 로그인시각 */
	clientip VARCHAR2(20)default '127.0.0.1' NOT NULL , /* 접속ip주소 */
    CONSTRAINT FK_tbl_loginhistory_fk_userid FOREIGN KEY(fk_userid) REFERENCES TBL_MEMBER(USERID) -- 회원테이블의 아이디를 외래키로 받음
);
-- Table TBL_LOGINHISTORY이(가) 생성되었습니다.




/* 상품추가이미지 */
CREATE TABLE tbl_product_img (
	img_no varchar2 (20) NOT NULL, /* 이미지번호 */
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
   
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content , category, point)
    values(seq_tbl_product_pdno.nextval, 'EFV-C120D-1ADF', 'G-SHOCK', 165000, 145000, seq_tbl_product_pdno.nextval || '_thum.png',
    '관리의 번거로움에서 벗어나 삶에 집중할 수 있는 10년 배터리 탑재로 배터리 수명을 걱정할 필요가 없는 아날로그-디지털 콤비네이션 타입 EFV-C120 모델입니다. EFV-C120은 EFV-C110의 후속 모델로서, 아날로그-디지털 콤비네이션 시계의 모든 장점과 긴 배터리 수명을 제공하면서도 더욱 컴팩트하고 심플하게 디자인되었습니다. 또한, 월드 타임, 스톱워치, 알람, 카운트다운 타이머, 최대 100m 방수 성능으로 일상생활에서의 실용성을 겸비한 모델입니다.'
    , '남성용' , 1450);
    commit;
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'GA-2300-1ADR', 'G-SHOCK', 145000, 120000, seq_tbl_product_pdno.nextval || '_thum.png',
    '1983년 출시 이후 끊임없는 강인함을 요구해 진화를 계속하는 터프니스 워치 G-SHOCK에서, 트렌드에 맞게 소형화, 슬림화 한 미니멀한 디자인의 콤비 모델 GA-2300입니다. 케이스 직경은 45.4mm×42.1mm, 두께는 11.6mm로 대폭 사이즈를 감소시켜, 불필요한 부분을 없앤 미니멀한 디자인으로 근미래적인 이미지를 지향했습니다.'
     , 1200);
    commit;
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'MRG-BF1000B-1ADR', 'G-SHOCK', 690000 , 590000, seq_tbl_product_pdno.nextval || '_thum.png',
    'G-SHOCK의 최상급 라인업 「MR-G」시리즈인 MRG-BF1000은, ISO 규격 200m 다이빙용 방수 기능을 가지는 전용 다이버즈 워치 「FROGMAN」에 티타늄 외장을 채용하고, 정교한 마감을 실시한 MR-G입니다. 강도가 높은 프로텍트 구조와 글라스를 보호하는 페이스 가드 기능을 갖췄고, 스크류 백의 백-케이스에는 개구리의 캐릭터를 아름답게 그려냈습니다. 밴드는 젖은 잠수복 위에서도 착용 가능하게 설계되어 있고, 쉽게 교체할 수 있습니다.'
     , 5900);
    commit;
    
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'YACHT-MASTER', '롤렉스', 58000000 , 5800000, seq_tbl_product_pdno.nextval || '_thum.png',
    '롤렉스 YACHT-MASTER II 116688 44mm 남성용 시계입니다. 전체18K 옐로우 골드, 천연 다이아몬드 셋팅 베젤 및 러그, 화이트 다이얼 - 야광처리시, 분, 초침 및 인덱스, 3시방향 날짜표시, 오토메틱입니다. M단위이며, 시계의 상태는 신품과 동일합니다.'
     , 58000);
     commit;
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'DAYTONA', '롤렉스', 105710000 , 10571000, seq_tbl_product_pdno.nextval || '_thum.png',
    '동심원 형태의 카운터를 갖춘 아이스 블루 다이얼은 크로마라이트 처리된 골드 아플리케 시각 표식과 시계 바늘로 뛰어난 가독성을 자랑합니다. 중앙의 초침은 1/8초를 읽을 수 있는 정확성을 자랑하며, 다이얼 위 두 개의 카운터는 각각 경과 시간과 분을 표시합니다. 이 기능을 이용해 코스 주행 시간을 계산하고, 우승 전략을 결정할 수 있습니다.'
     , 105710);
     commit; 

    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'GMT-MASTER II', '롤렉스', 23080000 , 2308000, seq_tbl_product_pdno.nextval || '_thum.png',
    '이 모델은 블랙 다이얼과 두 가지 색상이 어우러진 그레이 및 블랙 세라믹 소재의 세라크롬 베젤 인서트를 장착하고 있습니다. GMT-마스터 II는 12시간을 기준으로 하는 일반적인 시, 분, 초침 외에도 24시간용 시침 및 24시간이 음각으로 표시된 양방향 회전 베젤을 장착하고 있습니다. 눈에 띄는 컬러로 표시된 24시간용 시침이 가리키는 베젤의 눈금을 통해 제1시간대의 “본국 시각”을 확인할 수 있습니다. 시침은 분침과 초침에 영향을 주지 않고 독립적으로 움직이기 때문에 정밀한 타임키핑에 영향을 미치지 않고 새로운 시간대를 적용할 수 있습니다.' 
     , 23080);
     commit; 
     
     insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, '1908', '롤렉스', 30490000 , 3049000, seq_tbl_product_pdno.nextval || '_thum.png',
    '모든 다이얼은 크기가 작은 예술 작품과 같습니다. 장식적인 요소 및 전반적인 디자인과 함께 색상, 빛 반사, 표면 질감은 시계에 고유한 특징을 부여합니다. 롤렉스는 다이얼 제작 및 사내 모든 생산 과정을 관장합니다. 이처럼 롤렉스 다이얼 매뉴팩쳐는 전통적인 기량과 최신 기술을 결합하여 시간이 지나도 흠잡을 데 없는 가장 높은 수준의 품질을 달성하겠다는 브랜드 철학을 완벽하게 구현합니다.'
     , 30490);
     commit; 
     
      insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'SKY-DWELLER', '롤렉스', 59120000 , 5912000, seq_tbl_product_pdno.nextval || '_thum.png',
    '롤렉스에서 자체 개발하여 특허를 획득한 스카이-드웰러의 새로운 오이스터플렉스(Oysterflex) 브레슬릿은 메탈 브레슬릿을 대체하면서도 스포티한 느낌을 줍니다. 브레슬릿은 티타늄과 니켈 합금 소재의 유연한 메탈 블레이드에 의해 시계의 케이스와 오이스터클라스프(Oysterclasp)에 연결되어 있습니다. 블레이드에는 특히 외부 환경적 영향에 대한 저항성과 내구성, 인체에 대한 안전성이 탁월한 고성능 블랙 엘라스토머가 오버몰딩되어 있습니다.'
     , 59120);
     commit; 
     
     insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'OYSTER PERPETUAL 41', '롤렉스', 8830000 , 880000, seq_tbl_product_pdno.nextval || '_thum.png',
    '다이얼은 고유의 정체성과 탁월한 가독성을 완성하는 롤렉스 시계만의 얼굴입니다. 변색 방지를 위해 골드 시각 표식 사용하며 모든 롤렉스 다이얼은 완벽함을 유지하기 위해 롤렉스 자체 디자인 및 수작업으로 제작됩니다.'
     , 8800);
     commit; 
     
      insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'DATEJUST 36', '롤렉스', 12390000 , 1230000 , seq_tbl_product_pdno.nextval || '_thum.png',
    '다이얼은 고유의 정체성과 탁월한 가독성을 완성하는 롤렉스 시계만의 얼굴입니다. 변색 방지를 위해 골드 시각 표식 사용하며 모든 롤렉스 다이얼은 완벽함을 유지하기 위해 롤렉스 자체 디자인 및 수작업으로 제작됩니다.'
     , 12300);
     commit; 
     
     insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'LADY-DATEJUST', '롤렉스', 58120000 , 5810000 , seq_tbl_product_pdno.nextval || '_thum.png',
    '이 다이얼의 특징은 18캐럿 골드에 다이아몬드 세팅, 18캐럿 골드 소재 로마 숫자 IX에 8개의 다이아몬드 세팅입니다. 선레이 피니시 기법은 오이스터 퍼페츄얼 컬렉션의 다양한 다이얼에 섬세한 빛을 선사합니다. 다이얼 중심부에서 바깥쪽으로 뻗어나가는 결은 정교한 브러싱 기술로 완성되었습니다. 결을 따라 일정하게 발산되는 빛이 손목의 움직임에 맞춰 은은하게 퍼져나갑니다. 선레이 피니시 작업이 끝나면 물리적 증착(PVD) 또는 전기도금 기술을 통해 다이얼에 색상을 입히는 과정이 시작됩니다. 얇게 코팅되어 광택이 나도록 피니시 작업이 완료되면 마침내 매혹적인 다이얼이 완성됩니다.'
     , 58100);
     commit; 
     
     insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'AIR-KING', '롤렉스', 10260000 , 1020000 , seq_tbl_product_pdno.nextval || '_thum.png',
    '에어-킹의 상징적인 블랙 다이얼은 운항 시간을 쉽게 파악할 수 있도록 대형 3시, 6시, 9시 아워 마커와 또렷한 분 눈금을 채택했으며, 다이얼에는 1950년대 모델과 동일한 디자인의 Air-King 레터링이 표시되어 있습니다.'
     , 10200);
     commit; 
     
       insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'SEA-DWELLER', '롤렉스', 25220000 , 2520000 , seq_tbl_product_pdno.nextval || '_thum.png',
    '다이버는 60분 눈금이 새겨진 Sea-Dweller의 한 방향 회전 베젤을 통해 다이빙 및 감압 시간을 정확하게 확인하여 안전한 잠수를 할 수 있습니다. 롤렉스 특허로 자체 제작된 블랙 세라크롬 베젤 디스크는 견고한 세라믹으로 제작되어 스크래치가 거의 발생하지 않으며 자외선에 노출되어도 색상이 변하지 않습니다. 베젤의 눈금은 PVD(물리적 증착) 공법을 이용하여 골드 박막으로 코팅 처리하였습니다. 매끈한 블랙 다이얼 위에는 푸른색 야광이 오랫동안 지속되는 대형 크로마라이트 시각 표식과 시계 바늘이 장착되어 어두운 환경에서도 뛰어난 가독성을 제공합니다.'
     , 25200);
     commit; 
     
      insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'OYSTER PATTERN', '롤렉스', 8830000 , 830000 , seq_tbl_product_pdno.nextval || '_thum.png',
    '셀리브레이션(Celebration)이라는 이름의 이 새로운 모티프는 오이스터 퍼페츄얼 31, 오이스터 퍼페츄얼 36, 오이스터 퍼페츄얼 41에 사용됩니다. 블랙 테두리를 두른 다양한 크기의 버블로 구성되어 있으며, 2020년에 선보인 래커 다이얼의 선명한 색상을 캡슐화하여 배경색은 터콰이즈 블루를, 버블에는 캔디 핑크, 옐로우, 코랄 레드 및 그린을 사용합니다.'
     , 8300);
     commit; 
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'DAY-DATE 36', '롤렉스', 81200000 , 75000000 , seq_tbl_product_pdno.nextval || '_thum.png',
    '이 다이얼의 특징은 18캐럿 골드 시각 표식에 다이아몬드 32개 세팅, 18캐럿 골드 로마 숫자 VI와 IX에 다이아몬드 24개 세팅입니다. 다양한 모양과 크기의 라인이 예술적인 카메오 패턴을 형성하는 오렌지 색상 스톤인 카넬리언으로 커팅되었습니다.다이얼은 고유의 정체성과 탁월한 가독성을 자랑하는 롤렉스 시계만의 얼굴입니다. 변색 방지를 위해 골드 시각 표식 사용하며 모든 롤렉스 다이얼은 완벽함을 유지하기 위해 롤렉스 자체 디자인 및 수작업으로 제작됩니다.'
     , 750000);
     commit;

     insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'DATEJUST 31', '롤렉스', 63540000 , 60000000 , seq_tbl_product_pdno.nextval || '_thum.png',
    '다이얼은 고유의 정체성과 탁월한 가독성을 완성하는 롤렉스 시계만의 얼굴입니다. 변색 방지를 위해 골드 시각 표식 사용하며 모든 롤렉스 다이얼은 완벽함을 유지하기 위해 롤렉스 자체 디자인 및 수작업으로 제작됩니다.'
     , 60000);
     commit;
     -- 롤렉스 17번까지 올린거임 추후 등록을 위해
     
 
     
     
     -- 카시오
     insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'W-217H-3AVDF', '카시오', 35000 , 33000 , seq_tbl_product_pdno.nextval || '_thum.png',
    '대형 디스플레이와 데일리 컬러로 일상생활에서의 실용적인 모델입니다. 자연의 느낌을 담은 베이지와 그린 색상에 오렌지로 포인트를 더하고, 스타일리시한 아웃도어 다이얼은 가독성이 좋습니다. 또한 최대 50m 방수, 배터리 수명 7년, LED 백라이트 등 편리한 기능을 갖추고 있어 일상에서의 실용적인 착용이 가능합니다.'
     , 330);
     commit;
     
     insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'A-168WEHA-9ADF', '카시오', 100000 , 88000 , seq_tbl_product_pdno.nextval || '_thum.png',
    '1983년에 카시오가 발매한 당시 세계 초소형의 계산기 「SL-800」을 모티브로 디자인한 모델입니다. 「SL-800」은, 신용 카드 사이즈의 계산기로, 두께는 불과 0.8mm, 무게 12그램의 슬림, 소형 사이즈를 실현한 메모리 첨부 8자릿수 계산기입니다. 클래식 디자인이 인기인 「A168」에, 「SL-800」의 블랙, 골드의 베이스 컬러나 숫자 등에 사용된 컬러를 곳곳에 채용한 향수를 불러일으키는 모델입니다.'
     , 880);
 
     
     insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'CA-500WEGG-9BDF', '카시오', 160000 , 140000 , seq_tbl_product_pdno.nextval || '_thum.png',
    '1976년에 카시오가 발매한 시계 기능을 탑재한 복합 계산기 「CQ-1」을 모티브로 디자인한 모델입니다. 「CQ-1」은, 시계, 알람, 스톱워치, 계산 기능을 갖춘 당시 획기적인 상품으로, 「덴크로」의 애칭으로 널리 인기를 얻었습니다. 8자리 계산 기능을 탑재한 카시오의 아이코닉 한 시리즈 「CA-500」에, 당시 발매된 「CQ-1」과 같은 컬러를 채용한 향수를 불러일으키는 모델입니다.'
     , 1400);
     commit;
     
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'LRW-200HS-4EVDF', '카시오', 64000  , 60000 , seq_tbl_product_pdno.nextval || '_thum.png',
    '반투명 베젤과 밴드로 된 컴팩트한 아날로그시계의 빛나는 광채가 매력적인 모델입니다. 바다에서 영감을 받은 인덱스 마크와 메탈릭 광택이 있는 선레이 다이얼이 어우러져 정교하고 세련된 디자인을 완성합니다.'
    , 600);
    commit;
     
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'MW-240B-3BVDF', '카시오', 53000 , 50000 , seq_tbl_product_pdno.nextval || '_thum.png',
    '클래식한 디자인에 활동적인 느낌을 더한 제품으로, 대형 케이스와 벨크로 고정 밴드가 있는 심플한 아날로그 워치입니다. 다이얼의 오렌지 엑센트와 흙빛 컬러의 투톤 밴드가 아웃도어 패션을 완벽하게 지원하는 독특한 디자인을 연출합니다. 또한, 최대 50미터까지 견딜 수 있는 방수 성능으로 실생활에서 안심하고 착용할 수 있습니다.'
    , 500);
    commit;
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'W-219HB-3AVDF', '카시오', 55000 , 45000 , seq_tbl_product_pdno.nextval || '_thum.png',
    '활동적인 느낌을 더한 다기능 디지털 워치로 착용하고 외부 활동을 즐기는데 적합합니다. 벨크로 고정 밴드와 흙빛 컬러로 야외 패션을 완벽하게 지원하고, 최대 50m 방수 성능, 배터리 수명 7년, LED 백라이트 등 일상생활에 있어 실용적인 편리한 기능을 갖추고 있습니다.'
    , 450);
    commit;
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'MWQ-100-1AVDF', '카시오', 110000 , 98000 , seq_tbl_product_pdno.nextval || '_thum.png',
    '아날로그-디지털 콤비네이션으로 편의성을 제공하면서 메탈 소재의 대담함과 세련미가 느껴지는 모델입니다. 메탈 인덱스 마크가 페이스의 깊이와 입체감을 더하며, 다이얼의 선레이 스타일링으로 부드러운 분위기를 연출합니다. 또한, 100m 방수, 전지 수명 10년, 월드 타임 등 일상생활에서의 편리한 기능을 제공합니다.'
    , 980);
    commit;
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'W-800H-2AVDF', '카시오', 42000 , 37000 , seq_tbl_product_pdno.nextval || '_thum.png',
    '시간을 한눈에 명확하게 표시하는 큰 디스플레이가 있는 심플한 디자인의 디지털 워치입니다. 100m 방수, 배터리 수명 10년, LED 백라이트 등 일상생활에서의 편리한 기능을 갖추고 있습니다.'
    , 370);
    commit;
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'MTD-125D-9AVDF', '카시오', 165000 , 145000 , seq_tbl_product_pdno.nextval || '_thum.png',
    '해양 스포츠를 즐기는 유저들을 위한 이상적인 모델입니다. 100m 방수 및 스포티한 디자인의 편리한 요일 및 날짜 표기가 실용성을 더합니다.'
    , 1450);
    commit;
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'LA-700WE-7ADF', '카시오', 73000 , 68000 , seq_tbl_product_pdno.nextval || '_thum.png',
    '미니멀한 디자인이 세련된 느낌을 주는 모델입니다. 26.3mm 직경의 소형 라운드 케이스이므로, 액세서리 감각으로 착용을 즐길 수 있습니다. 메탈릭감이 있는 다이얼을 채택하여 반짝임이 추가된 고급스러운 마무리와 더불어 스톱워치나 알람 등 일상생활에서의 편리한 기능도 갖추고 있습니다.'
    , 680);
    commit;
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'A-1000MB-1BEF', '카시오', 250000  , 220000 , seq_tbl_product_pdno.nextval || '_thum.png',
    '풀 메탈 타입의 PREMIUM 시리즈입니다. 케이스 측면을 미러 마감하고, 베젤 케이스 경사면은 헤어 라인 마감으로 질감을 높였습니다. 조개껍질 무늬의 다이얼과 뜨개질 형상의 밀라네제 밴드에 의해, 클래식 감이 있는 형상과 컬러링으로 완성했습니다. 고휘도 LED 백라이트와 스톱워치, 알람 등 편리한 기능도 갖추고 있습니다.'
    , 2200);
    commit;
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'LTP-B140D-1AVDF', '카시오', 110000 , 98000 , seq_tbl_product_pdno.nextval || '_thum.png',
    '클래식 메탈 아날로그 CASIO의 대담한 정사각형 버전입니다. 은은하게 빛나는 바 인덱스는 세련된 다이얼 색상과 대비되어 시크하고 우아한 디자인을 선사합니다. 5기압 방수 성능 탑재로 일상생활에서 안심하고 착용할 수 있습니다.'
    , 980);
    commit;
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'MTP-V006D-1CUDF', '카시오', 48000  , 42000 , seq_tbl_product_pdno.nextval || '_thum.png',
    '어떤 스타일이든 완성하는 심플하고 우아한 분위기의 모델입니다. 시침, 분침, 초침의 상징적인 모양을 중심으로 디자인된 클래식 아날로그 시계로 캐주얼한 룩에서 격식을 갖춘 의상까지 손쉽게 연출할 수 있습니다. 심플하고 세련된 바 인덱스 마크와 3시 정각 위치의 편리한 날짜 디스플레이가 세련된 디자인과 기능적 편리함을 제공합니다. 매일 사용하기 적합한 방수 성능으로 세안하거나 우천 시 또는 외출할 때도 안심할 수 있습니다.'
    , 420);
    commit;
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'A-1100B-1DF', '카시오', 300000, 278000, seq_tbl_product_pdno.nextval || '_thum.png',
    '1970년대를 오늘에 이르게 하는 52QS-14B 부활의 시대를 초월한 매력을 경험해 보십시오. 리바이벌된 디자인은 풀 메탈 케이스를 전문적으로 연마하여 완성도 높은 마무리를 자랑합니다. 오리지널 모델의 상징적인 전면 버튼을 메탈로 복제하여 스타일리시함을 더했습니다. 은은하게 새겨진 모드 인디케이터와 프리미엄 광택은 고급스러움과 함께 시간의 시련을 견디는 인상적인 레트로 룩을 연출합니다.'
    , 2780);
    commit;
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'A-120WEGG-1BDF', '카시오', 125000, 110000, seq_tbl_product_pdno.nextval || '_thum.png',
    '카시오가 1980년대에 선보였던 「멀티 컬러 디자인」을 모티브로 한, 복고풍의 POP 디지털 워치입니다. 네 개의 프런트 버튼이 디자인의 엑센트가 됩니다. 또한 밴드와 메탈 버튼에 IP 마감을 실시하였습니다. 향수적인 감성과 다가올 미래감이 융합된 레트로 퓨처한 디자인에, 스톱워치나 알람 등 일상생활에서 활용 가능한 실용적인 기능도 갖추고 있습니다.'
    , 1100);
    commit;
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'LF-20W-1ADF', '카시오', 51000, 47500, seq_tbl_product_pdno.nextval || '_thum.png',
    '아날로그 스타일의 시계와 디지털 시간 디스플레이를 모두 갖추어, 매력적이고 유행을 타지 않는 디자인으로 시간을 즐겁게 보내세요. 컴팩트하고 편안한 착용감과 자연스러운 색상으로 어디에나 어울려 언제나 모든 손목에 맞는 스타일입니다. 밴드는 재생 가능한 유기 소재의 바이오 기반 레진으로 제작되었습니다. LED 조명, 스톱워치, 알람, 타이머, 세계 시간 같은 기능으로 이 시계는 어떤 상황에도 스타일과 실용적인 기능을 겸비합니다.'
    , 475);
    commit;
    
    
    -- 여기부터 다시 G-SHOCK
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'GBA-900CB-1ADR', 'G-SHOCK', 175000, 150000, seq_tbl_product_pdno.nextval || '_thum.png',
    'G-SHOCK의 스포츠 라인 GBA-900입니다. 해변에서 운동을 하는 러너를 이미지화하고, 컬러 다이얼의 선명한 색감이 이목을 끄는 디자인으로, 시 분침에는 실버 증착 도장을 채택하여 시인성을 높였습니다. 가속도 센서나 거리 계측 기능, 스마트폰 링크 등 트레이닝에 도움이 되는 기능을 탑재하고, GPS 기능과 연계하여 계측 거리를 보정할 수 있으며, 한 번 보정 후에는 다음부터 시계 단독으로 주행 거리를 측정할 수 있습니다.'
    , 1500);
    commit;
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'GMA-S120VA-7ADR', 'G-SHOCK', 165000, 140000, seq_tbl_product_pdno.nextval || '_thum.png',
    '끊이지 않는 강인함을 요구해 진화를 계속하는 터프니스 워치 G-SHOCK에서, 비치 리조트를 테마로 한 모델입니다. 베이스 모델은 인기 있는 디지털 아날로그 콤비 모델을 소형화한 GMA-S120으로, 화이트 원톤으로 정리하면서도, 다이얼이나 인덱스 파트에는 실버의 증착 가공을 실시해, 반짝이고 화려한 페이스로 완성했습니다. 실버 메탈 가공이 되어 있기 때문에 실버 액세서리와도 잘 어울리며 여름의 상쾌한 비치 리조트 패션의 코디에도 좋은 시리즈입니다.'
    , 1400);
    commit;
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'GA-100MF-1ADR', 'G-SHOCK', 135000, 120000, seq_tbl_product_pdno.nextval || '_thum.png',
    '1983년 출시 이후, 끊임없는 강인함을 요구하며 진화를 계속하는 터프니스 워치 G-SHOCK에서, 게임이라는 가상 문화가 지속적으로 성장하면서 가상 문화의 영향력이 사회 각계각층으로 확대됨에 따라 이 시대의 트렌드에 맞춰 가상과 미래의 전자적 분위기를 시계 디자인에 접목시킨 모델입니다. 화려한 네온톤을 사용하여 가상적이고 미래적인 느낌을 표현하였고, 컬러풀한 네거티브 디스플레이와 다크 블랙 베젤, 밴드를 조합해 독특한 외관을 선사합니다.'
    , 1200);
    commit;
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'GM-2100GC-1ADR', 'G-SHOCK', 290000, 275000, seq_tbl_product_pdno.nextval || '_thum.png',
    '터프니스를 추구하며 진화를 계속하는 G-SHOCK에서, 그런지 패션에 영감을 받은, GRUNGE CAMOUFLAGE Series입니다. 90년대에 등장한 그런지 패션으로부터 영감을 받아 메탈 베젤에는 특별한 가공으로 금속이 벗겨져 떨어져 나간 듯한 무늬를 배치해, 그런지 패션의 멋스러움을 표현했습니다. 인 다이얼에는 메탈릭 도장으로 메탈 베젤에 매치하는 스타일을 구현했습니다. 포멀한 착장부터 캐주얼룩까지 스타일링하기 좋은 컬러의 모델입니다.'
    , 2750);
    commit;
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'GA-B001CBRS-6ADR', 'G-SHOCK', 155000, 150000, seq_tbl_product_pdno.nextval || '_thum.png',
    '1983년의 발매 이후, 끊이지 않는 강인함을 요구하며 진화를 계속하는 터프니스 워치 G-SHOCK에서, 근미래의 테크놀로지와 SF 감을 표현한 모델입니다. SF의 세계를 이미지화하고 사이버와 우주를 이미지 한 컬러를 채택했습니다. 스마트폰 링크로 간편하게 시간 설정이 가능하고, 유니크한 색상이 눈길을 끄는 근미래적인 디자인으로 완성했습니다.'
    , 1500);
    commit;
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'DW-6900U-1DR', 'G-SHOCK', 120000, 105000, seq_tbl_product_pdno.nextval || '_thum.png',
    '「Absolute Toughness」 궁극의 터프니스를 추구해 역사를 쌓아 온 G-SHOCK에서, 1990년대에 인기를 얻은 클래식 G-SHOCK, 6900 시리즈의 스테디 모델입니다. 대형 액정화면과 LED 백라이트로 실용적인 사용으로부터 스트리트, 스포츠 씬까지 다양한 스타일에 맞는 범용성이 높은 모델입니다.'
    , 1050);
    commit;
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'GMA-S110VW-2ADR', 'G-SHOCK', 205000, 198000, seq_tbl_product_pdno.nextval || '_thum.png',
    '끊이지 않는 강인함을 요구하며 진화를 계속하는 터프니스 워치 G-SHOCK에서, 디지털 문화를 상기시키는 근미래를 테마로 한 모델입니다. SF의 세계관을 방불케 하는 클리어 스켈레톤 컬러를 채용하고, 상단 문자판과 인덱스는 증착 가공을 실시하여 세련된 디자인으로 완성했습니다. 베이스 모델은 인기 있는 GA-110 시리즈를 소형화, 슬림화 한 GMA-S110으로 하여, 섬세한 파트가 겹쳐진 입체적인 다이얼 디자인에 디지털 표시와 시침 분침이 균형 있게 배치되어 시인성을 높였습니다.'
    , 1980);
    commit;
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'DW-6900HD-8DR', 'G-SHOCK', 120000, 105000, seq_tbl_product_pdno.nextval || '_thum.png',
    '1983년의 발매 이후, 끊이지 않는 강인함을 요구하며 진화를 계속하는 터프니스 워치 G-SHOCK에서, 사람들의 잠재 능력을 암흑에서 빛나는 축광 파트로 이미지 한, HIDDEN GLOW 시리즈입니다. 어둠을 형상화한 그레이 베이스 컬러로 통일하고, 페이스 부분에는 축광 파트를 채용하여 화이트의 엑센트 컬러로 모노톤의 세계에 떠오르는 빛을 이미지화한 컬러링으로 완성하였습니다. 어둠 속에서 다이얼에 사용된 축광 파트가 빛을 뿜어내어 에너지 넘치는 느낌을 주는 마무리로 되어있습니다. 캐주얼 스타일부터 워크 스타일까지 포인트가 되는 멋진 디자인입니다.'
    , 1050);
    commit;
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'GPR-H1000-9DR', 'G-SHOCK', 660000, 580000, seq_tbl_product_pdno.nextval || '_thum.png',
    '가혹한 환경하에서의 사용을 상정한 G-SHOCK의 MASTER OF G 시리즈 「레인지 맨」으로 심박계와 GPS 기능을 탑재한 터프니스 워치입니다. 머드 레지스트 구조에 더해 고화질, 고 콘트라스트로 보기 쉬운 MIP 액정을 채용하였고, 상황에 맞게 화면을 사용자 정의할 수 있습니다. 가혹한 환경에서의 활동을 지원하고, 자신의 운동 상황을 파악하면서 활동이 가능합니다. 조수의 간만을 나타내는 타이드 그래프도 탑재하였고, 밴드 표면의 질감은 갑작스러운 비 등으로 젖은 손가락으로도 미끄러지지 않게 되어 있습니다.'
    , 5800);
    commit;
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'GA-110CD-1A9DR', 'G-SHOCK', 160000, 140000, seq_tbl_product_pdno.nextval || '_thum.png',
    'G-SHOCK에서 완전히 새로운 컬러를 적용한 제품으로 산뜻하고 대담한 분위기를 연출할 수 있는 모델입니다. 특유의 대형 케이스가 특징인 GA-110을 베이스로 하여, 밴드와 베젤에 클래식한 블랙 컬러를 적용하였고, 다이얼에 새로운 컬러를 더했습니다. 멋진 디자인에 산뜻한 느낌이 더해져 트렌디하고 패셔너블한 스트리트 스타일의 액세서리를 제공합니다.'
    , 1400);
    commit;
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'MRG-B2000SG-1ADR', 'G-SHOCK', 8740000, 8200000, seq_tbl_product_pdno.nextval || '_thum.png',
    'G-SHOCK 최상급 라인 MR-G의 G-SHOCK 40주년 한정 모델입니다. MR-G를 위해서 특별 제작된 투구 「쇼게키마루・가이」를 모티브로, 무장의 긍지와 미의식을 예로부터 전해지는 전통 기법으로 표현했습니다. 베젤을 투구의 전립에 비추어, 금속 세공 장인 고바야시 마사오 씨가 호랑이의 모습과 바위 알갱이 패턴을 손으로 하나하나 새기고, 핸드메이드에 의한 제품마다 각각 특별한 존재감이 있는 외관으로 완성하였습니다. 밴드에는 투구의 순백색의 투구 미늘을 이미지화하여 화이트 불소 러버의 듀라 소프트 밴드를 채용하였습니다.'
    , 82000);
    commit;
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'GA-2200NC-7ADR', 'G-SHOCK', 150000, 130000, seq_tbl_product_pdno.nextval || '_thum.png',
    '1983년 발매 이후, 끊이지 않는 강인함을 요구하며 진화를 계속하는 터프니스 워치 G-SHOCK에서 자연 속에 있는 광물들을 이미지화한 모델 Natural color 시리즈입니다. 베이스 모델에는 빅 케이스의 GA-2200으로, 어스 컬러를 사용하여 원톤으로 심플하게 완성했습니다. 캐주얼 스타일부터 아웃도어 스타일까지 포인트가 되는 멋진 디자인입니다.'
    , 1300);
    commit;
    
    
    -- 여기서부터 세이코
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'SRPG87K1', '세이코', 380000, 280000, seq_tbl_product_pdno.nextval || '_thum.png',
    '세이코가 생각하는 손목시계는 사람들과 가장 친숙한 액세서리입니다. 최고의 손목시계는 사용자와 교감하며 조화를 이루고 기능적으로나 감성적으로 만족감을 선사합니다. 세이코는 사용자와 제품간의 상호 교감을 만들어내기 위해 감성 테크놀로지에 포커를 맞춰 기술 개발을 하고 있습니다.'
    , 2800);
    commit;
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'SUR399P1', '세이코', 290000, 220000, seq_tbl_product_pdno.nextval || '_thum.png',
    '세이코가 생각하는 손목시계는 사람들과 가장 친숙한 액세서리입니다. 최고의 손목시계는 사용자와 교감하며 조화를 이루고 기능적으로나 감성적으로 만족감을 선사합니다. 세이코는 사용자와 제품간의 상호 교감을 만들어내기 위해 감성 테크놀로지에 포커를 맞춰 기술 개발을 하고 있습니다.'
    , 2200);
    commit;
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'SXDG93J1', '세이코', 290000, 220000, seq_tbl_product_pdno.nextval || '_thum.png',
    '세이코가 생각하는 손목시계는 사람들과 가장 친숙한 액세서리입니다. 최고의 손목시계는 사용자와 교감하며 조화를 이루고 기능적으로나 감성적으로 만족감을 선사합니다. 세이코는 사용자와 제품간의 상호 교감을 만들어내기 위해 감성 테크놀로지에 포커를 맞춰 기술 개발을 하고 있습니다.'
    , 2200);
    commit;
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'SSB427P1', '세이코', 380000, 330000, seq_tbl_product_pdno.nextval || '_thum.png',
    '세이코가 생각하는 손목시계는 사람들과 가장 친숙한 액세서리입니다. 최고의 손목시계는 사용자와 교감하며 조화를 이루고 기능적으로나 감성적으로 만족감을 선사합니다. 세이코는 사용자와 제품간의 상호 교감을 만들어내기 위해 감성 테크놀로지에 포커를 맞춰 기술 개발을 하고 있습니다.'
    , 3300);
    commit;
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'SSB339J1', '세이코', 450000, 380000, seq_tbl_product_pdno.nextval || '_thum.png',
    '세이코가 생각하는 손목시계는 사람들과 가장 친숙한 액세서리입니다. 최고의 손목시계는 사용자와 교감하며 조화를 이루고 기능적으로나 감성적으로 만족감을 선사합니다. 세이코는 사용자와 제품간의 상호 교감을 만들어내기 위해 감성 테크놀로지에 포커를 맞춰 기술 개발을 하고 있습니다.'
    , 3800);
    commit;
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'SSB345P1', '세이코', 450000, 380000, seq_tbl_product_pdno.nextval || '_thum.png',
    '세이코가 생각하는 손목시계는 사람들과 가장 친숙한 액세서리입니다. 최고의 손목시계는 사용자와 교감하며 조화를 이루고 기능적으로나 감성적으로 만족감을 선사합니다. 세이코는 사용자와 제품간의 상호 교감을 만들어내기 위해 감성 테크놀로지에 포커를 맞춰 기술 개발을 하고 있습니다.'
    , 3800);
    commit;
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'SRPF19K1', '세이코', 710000, 580000, seq_tbl_product_pdno.nextval || '_thum.png',
    '세이코가 생각하는 손목시계는 사람들과 가장 친숙한 액세서리입니다. 최고의 손목시계는 사용자와 교감하며 조화를 이루고 기능적으로나 감성적으로 만족감을 선사합니다. 세이코는 사용자와 제품간의 상호 교감을 만들어내기 위해 감성 테크놀로지에 포커를 맞춰 기술 개발을 하고 있습니다.'
    , 5800);
    commit;
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'SRPD57K1', '세이코', 500000, 380000, seq_tbl_product_pdno.nextval || '_thum.png',
    '세이코가 생각하는 손목시계는 사람들과 가장 친숙한 액세서리입니다. 최고의 손목시계는 사용자와 교감하며 조화를 이루고 기능적으로나 감성적으로 만족감을 선사합니다. 세이코는 사용자와 제품간의 상호 교감을 만들어내기 위해 감성 테크놀로지에 포커를 맞춰 기술 개발을 하고 있습니다.'
    , 3800);
    commit;
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'SRPG71K1', '세이코', 380000, 180000, seq_tbl_product_pdno.nextval || '_thum.png',
    '세이코가 생각하는 손목시계는 사람들과 가장 친숙한 액세서리입니다. 최고의 손목시계는 사용자와 교감하며 조화를 이루고 기능적으로나 감성적으로 만족감을 선사합니다. 세이코는 사용자와 제품간의 상호 교감을 만들어내기 위해 감성 테크놀로지에 포커를 맞춰 기술 개발을 하고 있습니다.'
    , 1800);
    commit;
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'SNP139J1', '세이코', 1370000, 1200000, seq_tbl_product_pdno.nextval || '_thum.png',
    '세이코가 생각하는 손목시계는 사람들과 가장 친숙한 액세서리입니다. 최고의 손목시계는 사용자와 교감하며 조화를 이루고 기능적으로나 감성적으로 만족감을 선사합니다. 세이코는 사용자와 제품간의 상호 교감을 만들어내기 위해 감성 테크놀로지에 포커를 맞춰 기술 개발을 하고 있습니다.'
    , 12000);
    commit;
    
    insert into tbl_product(pdno, pdname, brand, price,saleprice, pdimg1, pd_content ,  point)
    values(seq_tbl_product_pdno.nextval, 'SUR404P1', '세이코', 360000, 180000, seq_tbl_product_pdno.nextval || '_thum.png',
    '세이코가 생각하는 손목시계는 사람들과 가장 친숙한 액세서리입니다. 최고의 손목시계는 사용자와 교감하며 조화를 이루고 기능적으로나 감성적으로 만족감을 선사합니다. 세이코는 사용자와 제품간의 상호 교감을 만들어내기 위해 감성 테크놀로지에 포커를 맞춰 기술 개발을 하고 있습니다.'
    , 1800);
    commit;
    
   -- test 상품상세코드
   String a = " ' a ' ,  '  b '  ,  ' c  ' " ;
   
   select * 
   from tbl_product
   where pdname in ( " ' a ' ,  '  b '  ,  ' c  ' "      );
   
   create sequence seq_test_pdno
    start with 1
    increment by 1
    nomaxvalue
    nominvalue
    nocycle
    nocache;
   
   insert into tbl_pd_detail (pd_detailno, fk_pdno, color, pd_qty) values('d' || seq_test_pdno.nextval , 3, '블랙',20); 
    commit;
    insert into tbl_pd_detail (pd_detailno, fk_pdno, color, pd_qty) values('d' || seq_test_pdno.nextval , 3, '화이트',25); 
    commit;
    insert into tbl_pd_detail (pd_detailno, fk_pdno, color, pd_qty) values('d' || seq_test_pdno.nextval , 3, '핑크',15); 
    commit;
    
    select pdno, pdname, pd_content, price, saleprice, pd_qty, color
    from tbl_product P join tbl_pd_detail D
    on P.pdno = D.fk_pdno;
    

    select * from tbl_product_img;
    insert into tbl_product_img (img_no, fk_pdno, pd_extraimg) values ('e' || seq_test_pdno.nextval , 3 , '3_extra_1.png');
    commit;
    insert into tbl_product_img (img_no, fk_pdno, pd_extraimg) values ('e' || seq_test_pdno.nextval , 3 , '3_extra_2.png');
    commit;
    insert into tbl_product_img (img_no, fk_pdno, pd_extraimg) values ('e' || seq_test_pdno.nextval , 3 , '3_extra_3.png');
    commit;
    
    select pdno, pdname, pd_content, price, saleprice, pd_qty, color
    from tbl_product P join tbl_pd_detail D
    on P.pdno = D.fk_pdno;
    -- 상품의 정보과 재고, 컬러까지 조인해서 결과를 내는 sql문
    
    select pd_extraimg
    from tbl_product_img
    where fk_pdno = 3; 
    -- 상품번호 3번의 추가이미지를 갖고오는 sql문 (3을 ?위치홀더로 쓰면될듯)
    
    select * from tbl_product where brand like '%'||'브' ||'%'
    
    select * from tbl_product;
    
  
    select * from tbl_product where pdno = 16;
    update tbl_product set saleprice = 75000000 where pdno = 16;
    update tbl_product set price = 81000000 where pdno = 16;
    commit;

    select * from tbl_product where pdno = 6;                                    
    update tbl_product set saleprice = 100000000 where pdno = 6;
    update tbl_product set price = 105710000 where pdno = 6;
    commit;

     
    select distinct brand from tbl_product; 
    
    
    select * from tbl_member where userid = 'admin';
   
  
delete from  tbl_product_img; 
delete from tbl_product where pdno in (96);
  commit;
delete from tbl_pd_detail;
commit;  
  
select * from tbl_product_img;
select * from tbl_pd_detail;
select * from tbl_product order by pdno desc;



     select * from tbl_product;
    
   
    
    ------------------------------------------ 0523
     create sequence seq_tbl_order_ordercode
     start with 1
     increment by 1
     nomaxvalue
     nominvalue
     nocycle
     nocache;
     -- Sequence SEQ_TBL_CART_CARTNO이(가) 생성되었습니다.
     
 pdno VARCHAR2(30) NOT NULL, /* 상품코드 */
	pdname NVARCHAR2(30) NOT NULL, /* 상품명 */
	brand NVARCHAR2(20), /* 상품브랜드 */
	price NUMBER(10) DEFAULT 0 NOT NULL, /* 상품정가 */
	saleprice NUMBER(10) DEFAULT 0, /* 상품판매가 */
	pdimg1 VARCHAR2(50) DEFAULT 'noimg.png', /* 상품이미지 */
	pd_content NVARCHAR2(1000), /* 상품상세내용 */
	pdinputdate DATE DEFAULT sysdate NOT NULL, /* 상품등록일자 */
	pdstatus NUMBER(1) DEFAULT 0, /* 상품상태 0은 재고 및 색상 등록전, 1은 재고 및 색상 등록*/
    point /* 상품 구매시 적립포인트 */
	



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
    
    
    select * from tbl_product 
    select * from tbl_pd_detail
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
    select * from tbl_product_img; 

    select * from tbl_product ;
    
    delete from tbl_product where pdno not in(95, 99 ,105 ,107);
    commit;
    
    delete from tbl_member where email= 'Uqqfgdi052vJc9GbT3XIhooUlaQvPVtsd04skgjrpIg=';









    

