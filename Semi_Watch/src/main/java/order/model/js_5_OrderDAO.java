package order.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import shop.domain.ImageVO;

public interface js_5_OrderDAO {

	// 유저 주문내역 미완
	List<Map<String, String>> getOrderList(Map<String, String> paraMap) throws SQLException;

	// 관리자가 조회하는 전체 주문내역
	List<Map<String, String>> getOdrListAdmin(Map<String, String> paraMap) throws SQLException;

	// 배송상태 업데이트
	int deliveryUpdate(Map<String, String> paraMap) throws SQLException;

	// 개인유저의 주문내역 토탈페이지 구하기
	int userGetTotalPage(Map<String, String> paraMap) throws SQLException;

	// 관리자의 전체주문내역 토탈페이지 구하기
	int adminGetTotalPage(Map<String, String> paraMap) throws SQLException;

	// 개인유저의 구매 확정짓기
	int deliveryComplete(String ordercode) throws SQLException;

	// 관리자가 조회하는 상세주문내역 상품정보
	List<Map<String, String>> adminGetOrderDetail(String odrcode) throws SQLException;

	// 관리자가 조회하는 상세주문내역 구매자정보
	Map<String, String> adminGetOrderInfo(String odrcode) throws SQLException;
	
	
	
	
	
}
