package shop.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import shop.domain.ImageVO;
import shop.domain.ProductVO;

public interface sw_4_ProductDAO {
	
	// 장바구니에 담은 제품 정보 보여주기
	List<ProductVO> select_product() throws SQLException;
	
	
	
}
