package shop.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import shop.domain.ImageVO;
import shop.domain.ProductVO;

public interface ky_1_ProductDAO {
	
	// 최신 등록순으로 6개의 상품 이미지를 가져오기
	List<Map<String, String>> selectByRegiDate() throws SQLException;
	
	// tbl_map(위, 경도) 테이블에 있는 정보를 가져오기(select)
	List<Map<String, String>> selectCenterMap() throws SQLException;
	
	
	
	
	
}
