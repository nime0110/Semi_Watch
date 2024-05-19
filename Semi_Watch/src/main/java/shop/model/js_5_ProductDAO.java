package shop.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import member.domain.MemberVO;
import shop.domain.ImageVO;
import shop.domain.ProductVO;

public interface js_5_ProductDAO {

	// 전체 상픔리스트 가져오기
	List<ProductVO> select_product_pagin(Map<String, String> paraMap) throws SQLException;
	
	
	
	
	
}
