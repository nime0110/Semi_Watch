package order.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import shop.domain.ImageVO;

public interface ss_2_OrderDAO {
	

	// 리뷰 테이블에 insert 하는 메소드
	int insertReview(String productNo, String reviewText, String rating, String userid) throws SQLException;

	// 리뷰 테이블에 insert 하기 전 이미 리뷰가 작성된 상품인지 select 
	Map<String, String> isReviewExists(String productNo, String userid) throws SQLException;

	// 리뷰 테이블 업데이트 메소드
	int updateReview(String productNo, String reviewText, String rating, String userid) throws SQLException;

	// 리뷰 테이블에서 삭제  
	int deleteReview(String productNo, String userid) throws SQLException;

	
	
}
