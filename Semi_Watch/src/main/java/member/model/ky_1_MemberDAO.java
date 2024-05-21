package member.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import member.domain.MemberVO;

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
	
	

	
	
	
	
	
}
