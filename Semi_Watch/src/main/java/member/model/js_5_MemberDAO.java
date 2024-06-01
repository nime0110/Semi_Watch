package member.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import member.domain.MemberVO;

public interface js_5_MemberDAO {

	// 월별 방문횟수 통계(로그인)
	List<Map<String, String>> Purchase_byMonthvisitCnt(String userid) throws SQLException ;

	
	
	
	
	
}
