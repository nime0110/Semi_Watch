package order.domain;

import member.domain.MemberVO;

public class OrderVO {
	
	private String ordercode;	// 주문코드
	private String fk_userid;	// 아이디(외래키)
	private int total_price;	// 주문총액
	private String total_orderdate; // 주문일자
		
	private MemberVO mvo; // fk_userid 조회용?
	
	
	public MemberVO getMvo() {
		return mvo;
	}
	public void setMvo(MemberVO mvo) {
		this.mvo = mvo;
	}
	
	////////////////////////////////////////////
	
	
	public String getOrdercode() {
		return ordercode;
	}
	public void setOrdercode(String ordercode) {
		this.ordercode = ordercode;
	}
	public String getFk_userid() {
		return fk_userid;
	}
	public void setFk_userid(String fk_userid) {
		this.fk_userid = fk_userid;
	}
	public int getTotal_price() {
		return total_price;
	}
	public void setTotal_price(int total_price) {
		this.total_price = total_price;
	}
	public String getTotal_orderdate() {
		return total_orderdate;
	}
	public void setTotal_orderdate(String total_orderdate) {
		this.total_orderdate = total_orderdate;
	}

}
