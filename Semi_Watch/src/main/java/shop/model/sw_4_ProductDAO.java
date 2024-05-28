package shop.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import shop.domain.CartVO;
import shop.domain.ImageVO;
import shop.domain.ProductVO;

public interface sw_4_ProductDAO {
	
	// 장바구니에 담은 제품 정보 보여주기
	List<ProductVO> select_product() throws SQLException;
	
	// 장바구니 담기 
	int addCart(Map<String, String> paraMap) throws SQLException;
	
	// 로그인한 사용자의 이름으로 된 장바구니 조회해오기.
	List<CartVO> selectProductCart(String userid) throws SQLException;
	
	// 포인트 업데이트 시키기 (우리 장바구니에서는 필요 없음)
	Map<String, String> selectCartSumPricePoint(String userid);

	
	
	
	
	
}
