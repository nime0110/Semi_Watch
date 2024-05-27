package shop.domain;

public class CartVO {

	private String cartno;			// 장바구니 번호
	private String fk_userid;		// 사용자 ID
	private String fk_pdno;			// 제품번호
	private int cart_qty;			// 주문량
	private String registerday;		// 장바구니 입력날짜	
	
	private ProductVO prod;
	
	public CartVO(){};
		
	public CartVO(String cartno, String fk_userid, String fk_pdno, int cart_qty, String registerday ) {
		this.cartno = cartno;
		this.fk_userid = fk_userid;
		this.fk_pdno = fk_pdno;
		this.cart_qty = cart_qty;
		this.registerday = registerday;
	}

	public String getCartno() {
		return cartno;
	}

	public void setCartno(String cartno) {
		this.cartno = cartno;
	}

	public String getFk_userid() {
		return fk_userid;
	}

	public void setFk_userid(String fk_userid) {
		this.fk_userid = fk_userid;
	}

	public String getFk_pdno() {
		return fk_pdno;
	}

	public void setFk_pdno(String fk_pdno) {
		this.fk_pdno = fk_pdno;
	}

	public int getCart_qty() {
		return cart_qty;
	}

	public void setCart_qty(int cart_qty) {
		this.cart_qty = cart_qty;
	}

	public String getRegisterday() {
		return registerday;
	}

	public void setRegisterday(String registerday) {
		this.registerday = registerday;
	}

	public ProductVO getProd() {
		return prod;
	}

	public void setProd(ProductVO prod) {
		this.prod = prod;
	}
	
	
}
