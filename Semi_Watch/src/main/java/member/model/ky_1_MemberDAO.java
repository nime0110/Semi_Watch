package member.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import member.domain.MemberVO;
import review.domain.ReviewVO;

public interface ky_1_MemberDAO {
	
	// 회원가입을 해주는 메소드 (tbl_member 테이블에 insert)
	int registerMember (MemberVO member) throws SQLException;	
	
	// ID 중복검사 (tbl_member 테이블에서 userid 가 존재하면 true 를 리턴해주고, userid 가 존재하지 않으면 false 를 리턴한다)
	boolean idDuplicateCheck(String userid) throws SQLException;
	
	// email 중복검사 (tbl_member 테이블에서 email 가 존재하면 true 를 리턴해주고, email 가 존재하지 않으면 false 를 리턴한다)
	boolean emailDuplicateCheck(String email) throws SQLException;

	// 로그인 처리
	MemberVO login(Map<String, String> paraMap) throws SQLException;
	
	// 아이디 찾기(성명, 이메일을 입력받아서 해당 사용자의 아이디를 알려준다.)
	String findUserid(Map<String, String> paraMap) throws SQLException;
	
	// 비밀번호 찾기(아이디, 이메일을 입력받아서 해당 사용자가 존재하는지 유무를 알려준다.)
	Map<String, Object> isUserExist(Map<String, String> paraMap) throws SQLException;
	
	// 비밀번호 수정하기(맵에서 유저 아이디랑 새로운 비밀번호를 가져와서 데이터베이스 상에 업데이트 해준다.)
	int pwdUpdate(Map<String, String> paraMap) throws SQLException;
	
	// 회원가입유저 오토 로그인 처리
	MemberVO loginAfterReg(Map<String, String> paraMap) throws SQLException;
	
	// 페이지 바 만들기 - 페이징 처리를 위한 검색이 있는/없는 리뷰에 대한 총페이지 수 알아오기 
	int getTotalPage(Map<String, String> paraMap) throws SQLException;
	
	// 페이징 처리를 한 모든 리뷰 또는 검색한 리뷰 목록 보여주기 
	List<ReviewVO> select_review_paging(Map<String, String> paraMap) throws SQLException;
	
	// 뷰단(memberList.jsp)에서 "페이징 처리시 보여주는 순번 공식" 에서 사용하기 위해 검색이 있는 또는 검색이 없는 회원의 총개수 알아오기 시작
	int getTotalReviewCount(Map<String, String> paraMap) throws SQLException;
	
	// 입력받은 reviewno 를 가지고 하나의 리뷰정보를 리턴시켜주는 메소드
	ReviewVO selectOneReview(String reviewno) throws SQLException;
	
	// 입력받은 reviewno 를 가지고 리뷰정보를 삭제해주는 메소드
	int deleteOneReview(String reviewno) throws SQLException;
	
	

	
	
	
	
	
}
