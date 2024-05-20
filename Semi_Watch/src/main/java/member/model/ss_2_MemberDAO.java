package member.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import member.domain.MemberVO;

public interface ss_2_MemberDAO {

	//로그인 처리 메소드
	MemberVO login(Map<String, String> paraMap) throws SQLException;
	
	// 페이지 바 만들기 - 페이징 처리를 위한 검색이 있는/없는 회원에 대한 총페이지 수 알아오기 
	int getTotalPage(Map<String, String> paraMap) throws SQLException;
	
    // 페이징 처리를 한 모든 회원 또는 검색한 회원 목록 보여주기 
	List<MemberVO> select_Member_paging(Map<String, String> paraMap) throws SQLException;

	// 뷰단(memberList.jsp)에서 "페이징 처리시 보여주는 순번 공식" 에서 사용하기 위해 검색이 있는 또는 검색이 없는 회원의 총개수 알아오기
	int getTotalMemberCount(Map<String, String> paraMap) throws SQLException;

	//회원 상세정보 보여주기
	MemberVO selectOneMember(String userid) throws SQLException;

	// userid로 데이터베이스에서 유저 정보를 가져오기 - 자동 로그인 부분
	MemberVO getMemberByUserid(String userid) throws SQLException;
	
	
	
	
	
}
