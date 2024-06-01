package shop.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import review.domain.ReviewVO;
import shop.domain.ImageVO;
import shop.domain.ProductVO;
import shop.domain.Product_DetailVO;

public interface ss_2_ProductDAO {

	// 화면에서 찜하기를 눌렀을 때 해당하는 상품의 정보를 VO에 담아서 반환하는 메소드
	List<ProductVO> getWishListItem(String pdno) throws SQLException;

	//제품번호를 통해서 해당 제품의 정보 조회
	ProductVO selectOneProductBypdno(String pdno) throws SQLException;

	//제품번호를 통해서 썸네일 이미지 정보 조회
	List<String> getImagesByPnum(String pdno) throws SQLException;

	//제품번호를 가지고서 제품의 색상 정보 가져오기
	List<String> getColorsByPnum(String pdno) throws SQLException;

	//들어온 컬러 코드와 제품번호로 제품상세번호 가져오는 메소드 
	List<Product_DetailVO> getWishDetailByPnum(String pdno, String selectedColor) throws SQLException;

	//위시리스트 -> 장바구니 insert 메소드
	int wishProductInsert(String pdDetailNo, String userid) throws SQLException;
	
	//상품상세 -> 장바구니 insert 메소드
	int DetailProductInsert(String pdDetailNo, String userid, String quantity) throws SQLException;
	
	//선택한 물품 위시리스트에 넣는 메소드
	List<ProductVO> wishAdd(Map<String, Object> paraMap)  throws SQLException;

	// 리뷰 테이블에 insert 하는 메소드
	int insertReview(String productNo, String reviewText, String rating, String userid)  throws SQLException;

	// 제품번호로 리뷰를 가져오는 메소드
	public List<Map<String, String>> getReviewsBypnum(Map<String, String> paraMap) throws SQLException;

	// 페이지 바 만들기 - 페이징 처리를 위한 리뷰에 대한 총페이지 수를 알아와야 한다.
	int getTotalPage(Map<String, String> paraMap) throws SQLException;

	
	
	
	
}
