package order.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import shop.domain.ImageVO;

public interface jh_3_OrderDAO {
	
	// 주문상세정보(제품) 가져오기
	List<Map<String, String>> getordDetailInfo(String ordcode) throws SQLException;
	
	
	// 주문결제, 배송지정보 가져오기
	Map<String, String> getordInfo(String ordcode) throws SQLException;
	
	
	
	
	
}
