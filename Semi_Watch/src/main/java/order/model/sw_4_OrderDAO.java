package order.model;

import java.sql.SQLException;
import java.util.List;

import order.domain.OrderVO;
import shop.domain.ImageVO;

public interface sw_4_OrderDAO {
	 
	 // 로그인한 아이디로 주문한 내역 가져오기
	 public List<OrderVO> orderUserId(String userid) throws SQLException;

	
	
	
	
	
	
	
	
	
}
