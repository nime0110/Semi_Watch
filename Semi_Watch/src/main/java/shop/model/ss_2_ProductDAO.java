package shop.model;

import java.sql.SQLException;
import java.util.List;

import shop.domain.ImageVO;
import shop.domain.ProductVO;

public interface ss_2_ProductDAO {

	// 화면에서 찜하기를 눌렀을 때 해당하는 상품의 정보를 VO에 담아서 반환하는 메소드
	List<ProductVO> getWishListItem(String pdname) throws SQLException;
	
	
	
	
	
}
