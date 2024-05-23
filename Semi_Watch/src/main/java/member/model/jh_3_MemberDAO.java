package member.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import member.domain.MemberVO;

public interface jh_3_MemberDAO {
	
	// 기존 비밀번호와 변경하고자하는 비밀번호가 일치하는지 확인
	boolean pwdDuplicateCheck(Map<String, String> paraMap) throws SQLException;
	
	
	// 비밀번호 변경
	int updatePWD(Map<String, String> paraMap) throws SQLException;

	
	// 이메일 변경
	int updateEmail(Map<String, String> paraMap) throws SQLException;

	
	// 전화번호 변경
	int updateMobile(Map<String, String> paraMap) throws SQLException;


	// 주소 변경
	int updatePost(Map<String, String> paraMap) throws SQLException;

	
	
	
	
	
}
