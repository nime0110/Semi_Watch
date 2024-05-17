package shop.domain;

public class Product_DetailVO {
	
	
	private String pd_detailno; 
	private String fk_pdno;
	private String color; 
	private int pd_qty;
	
	
	public String getPd_detailno() {
		return pd_detailno;
	}
	public void setPd_detailno(String pd_detailno) {
		this.pd_detailno = pd_detailno;
	}
	public String getFk_pdno() {
		return fk_pdno;
	}
	public void setFk_pdno(String fk_pdno) {
		this.fk_pdno = fk_pdno;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public int getPd_qty() {
		return pd_qty;
	}
	public void setPd_qty(int pd_qty) {
		this.pd_qty = pd_qty;
	}

}
