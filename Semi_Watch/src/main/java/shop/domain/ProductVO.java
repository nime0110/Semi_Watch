package shop.domain;

public class ProductVO {

	
	private String 	pdno;       // 제품번호
	private String 	pdname;      // 제품명
	private String  brand;		// 브랜드
	private String  pdimg1;    // 제품이미지1   이미지파일명
	private long 	price;      // 제품 정가
	private long 	saleprice;  // 제품 판매가(할인해서 팔 것이므로)
	private String 	pd_content; // 제품설명 
	private int 	point;      // 제품구매시 적립되는 포인트(마일리지)    
	private int     pdstatus;	// 제품상태(등록중, 비등록중)
	private String 	pdinputdate; // 제품등록일자	
	
	////////////////////////// 여기까지 insert 용도 /////////////////////////////
	
	
	private Product_DetailVO pdvo; // tbl_pd_detail 조인 select 용도
	
	
	public Product_DetailVO getPdvo() {
		return pdvo;
	}

	public void setPdvo(Product_DetailVO pdvo) {
		this.pdvo = pdvo;
	}

	/*
	    제품판매가와 포인트점수 컬럼의 값은 관리자에 의해서 변경(update)될 수 있으므로
	    해당 제품의 판매총액과 포인트부여 총액은 판매당시의 제품판매가와 포인트 점수로 구해와야 한다.  
	*/
	private long totalPrice;         // 판매당시의 제품판매가 * 주문량
	private int  totalPoint;         // 판매당시의 포인트점수 * 주문량 
		
	
	public ProductVO() { }
	
	public ProductVO( 	String pdno ,
						String pdname,
						String brand,
						String pdimg1,
						long price,
						long saleprice,
						String category,
						String pd_content, 
						int point,    
						int pdstatus,
						String pdinputdate) {
	
		this.pdno = pdno;
		this.pdname = pdname;
		this.brand = brand;
		this.pdimg1 = pdimg1;
		this.price = price;
		this.saleprice = saleprice;
		this.point = point;
		this.pdstatus = pdstatus;
		this.pdinputdate = pdinputdate;
		
	}

	
	



	

	
	public String getPdno() {
		return pdno;
	}

	public void setPdno(String pdno) {
		this.pdno = pdno;
	}

	public String getPdname() {
		return pdname;
	}

	public void setPdname(String pdname) {
		this.pdname = pdname;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}


	public String getPdimg1() {
		return pdimg1;
	}

	public void setPdimg1(String pdimg1) {
		this.pdimg1 = pdimg1;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public long getSaleprice() {
		return saleprice;
	}

	public void setSaleprice(long saleprice) {
		this.saleprice = saleprice;
	}

	public String getPd_content() {
		return pd_content;
	}

	public void setPd_content(String pd_content) {
		this.pd_content = pd_content;
	}

	public int getPoint() {
		return point;
	}

	public void setPoint(int point) {
		this.point = point;
	}

	public int getPdstatus() {
		return pdstatus;
	}

	public void setPdstatus(int pdstatus) {
		this.pdstatus = pdstatus;
	}

	public String getPdinputdate() {
		return pdinputdate;
	}

	public void setPdinputdate(String pdinputdate) {
		this.pdinputdate = pdinputdate;
	}

	public void setTotalPrice(long totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setTotalPoint(int totalPoint) {
		this.totalPoint = totalPoint;
	}

	///////////////////////////////////////////////
	// *** 제품의 할인률 ***
	public int getDiscountPercent() {
		// 정가   :  판매가 = 100 : x
		
		// 5000 : 3800 = 100 : x
		// x = (3800*100)/5000 
		// x = 76
		// 100 - 76 ==> 24% 할인
		
		// 할인률 = 100 - (판매가 * 100) / 정가
		return (int) (100 - (saleprice * 100)/price);
		
	}
	
	
	/////////////////////////////////////////////////
	// *** 제품의 총판매가(실제판매가 * 주문량) 구해오기 ***
	public void setTotalPriceTotalPoint(int oqty) {   
		// int oqty 이 주문량이다.
	
		totalPrice = saleprice * oqty; // 판매당시의 제품판매가 * 주문량
		totalPoint = point * oqty;     // 판매당시의 포인트점수 * 주문량 
	}
	
	public long getTotalPrice() {
		return totalPrice;
	}
	
	public int getTotalPoint() {
		return totalPoint;
	}
	
}
