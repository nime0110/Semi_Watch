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

	// 전체 상픔리스트 페이지수 알아오기
	int getTotalPage(Map<String, String> paraMap) throws SQLException;

	/* >>> 뷰단(memberList.jsp)에서 "페이징 처리시 보여주는 순번 공식" 에서 사용하기 위해 
    검색이 있는 또는 검색이 없는 회원의 총개수 알아오기 시작 <<< */
	int getTotalProductCount(Map<String, String> paraMap) throws SQLException;


	
	
	
	
}
