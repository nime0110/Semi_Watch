package order.domain;

import member.domain.MemberVO;
import shop.domain.ProductVO;
import shop.domain.Product_DetailVO;

// 파일이름이 틀렷음 OrderDetailVO
public class OrderDetrailVO {
	
	private String order_detailno;  // 주문상세일련번호
	private String fk_userid;  		// 유저 아이디(외래키)
	private String fk_ordercode;  	// 주문번호 (외래키)
	private int order_qty;  		// 주문수량
	private String delivery_status; // 배송상태
	private String delivary_date; 	// 배송일자
	private int order_price;        // 주문상품가격 	
	private String fk_pd_detailno;  // 상품상세일련번호(외래키)
	
	private MemberVO mvo;	// 유저 아이디 받아오기
	private OrderVO ovo;	// 주문번호 받아오기
	private Product_DetailVO pdvo; // 상품상세일련번호 받아오기
	private ProductVO pvo;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////	
	
	
	
	public ProductVO getPvo() {
		return pvo;
	}
	public void setPvo(ProductVO pvo) {
		this.pvo = pvo;
	}
	public String getOrder_detailno() {
		return order_detailno;
	}
	public void setOrder_detailno(String order_detailno) {
		this.order_detailno = order_detailno;
	}
	public String getFk_userid() {
		return fk_userid;
	}
	public void setFk_userid(String fk_userid) {
		this.fk_userid = fk_userid;
	}
	public String getFk_ordercode() {
		return fk_ordercode;
	}
	public void setFk_ordercode(String fk_ordercode) {
		this.fk_ordercode = fk_ordercode;
	}
	public int getOrder_qty() {
		return order_qty;
	}
	public void setOrder_qty(int order_qty) {
		this.order_qty = order_qty;
	}
	public String getDelivery_status() {
		return delivery_status;
	}
	public void setDelivery_status(String delivery_status) {
		this.delivery_status = delivery_status;
	}
	public String getDelivary_date() {
		return delivary_date;
	}
	public void setDelivary_date(String delivary_date) {
		this.delivary_date = delivary_date;
	}
	public int getOrder_price() {
		return order_price;
	}
	public void setOrder_price(int order_price) {
		this.order_price = order_price;
	}
	public String getFk_pd_detailno() {
		return fk_pd_detailno;
	}
	public void setFk_pd_detailno(String fk_pd_detailno) {
		this.fk_pd_detailno = fk_pd_detailno;
	}
	public MemberVO getMvo() {
		return mvo;
	}
	public void setMvo(MemberVO mvo) {
		this.mvo = mvo;
	}
	public OrderVO getOvo() {
		return ovo;
	}
	public void setOvo(OrderVO ovo) {
		this.ovo = ovo;
	}
	public Product_DetailVO getPdvo() {
		return pdvo;
	}
	public void setPdvo(Product_DetailVO pdvo) {
		this.pdvo = pdvo;
	}
	
	
}
