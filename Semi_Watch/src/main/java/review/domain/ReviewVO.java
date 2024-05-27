package review.domain;

import member.domain.MemberVO;
import shop.domain.ProductVO;

public class ReviewVO {

	// < insert 용도 시작>
	
	private String reviewno;           // 리뷰번호
	private String fk_pdno;            // 제품번호
	private String fk_userid;          // 회원명
	private String review_content;     // 리뷰내용
	private String starpoint;          // 별점
	private String review_date;        // 리뷰작성일
	private String review_status;      // 리뷰스테이터스
	
	private MemberVO mvo;
	
	private ProductVO pvo;
	
	
	
	
	public MemberVO getMvo() {
		return mvo;
	}
	public void setMvo(MemberVO mvo) {
		this.mvo = mvo;
	}
	public ProductVO getPvo() {
		return pvo;
	}
	public void setPvo(ProductVO pvo) {
		this.pvo = pvo;
	}
	
	
	
	public String getReviewno() {
		return reviewno;
	}
	public void setReviewno(String reviewno) {
		this.reviewno = reviewno;
	}
	public String getFk_pdno() {
		return fk_pdno;
	}
	public void setFk_pdno(String fk_pdno) {
		this.fk_pdno = fk_pdno;
	}
	public String getFk_userid() {
		return fk_userid;
	}
	public void setFk_userid(String fk_userid) {
		this.fk_userid = fk_userid;
	}
	public String getReview_content() {
		return review_content;
	}
	public void setReview_content(String review_content) {
		this.review_content = review_content;
	}
	public String getStarpoint() {
		return starpoint;
	}
	public void setStarpoint(String starpoint) {
		this.starpoint = starpoint;
	}
	public String getReview_date() {
		return review_date;
	}
	public void setReview_date(String review_date) {
		this.review_date = review_date;
	}
	public String getReview_status() {
		return review_status;
	}
	public void setReview_status(String review_status) {
		this.review_status = review_status;
	}
	
	
	
	
	
}
