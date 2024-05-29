package shop.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import shop.domain.CartVO;
import shop.domain.ImageVO;
import shop.domain.ProductVO;

public interface jh_3_ProductDAO {
	
	// 장바구니에 있는 제품 정보(제품명, 이미지, 가격, 옵션명 등)
	List<ProductVO> select_odrProductInfo(Map<String, String[]> paraMap) throws SQLException;
	
	
	
	
	
	
}
