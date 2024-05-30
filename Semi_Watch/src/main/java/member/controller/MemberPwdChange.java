package member.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import member.model.ky_1_MemberDAO;
import member.model.ky_1_MemberDAO_imple;

public class MemberPwdChange extends AbstractController {

	private ky_1_MemberDAO mdao = null;
	
	public MemberPwdChange() {
		
		mdao = new ky_1_MemberDAO_imple();
		
	}
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		if("GET".equalsIgnoreCase(method)) { // "GET" 일 경우
		
		
		if(checkLogin(request)) {
			HttpSession session = request.getSession();
			MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
			String userid = (String) loginuser.getUserid();
			
			request.setAttribute("userid", userid);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/member/memberPwdChange.jsp");
		}
		else {
			super.setRedirect(true);
			super.setViewPage(request.getContextPath()+"/login/login.flex");
		}	

		}// end of if
		else { // "POST" 일 경우
			
			String userid = request.getParameter("userid");
			String pw = request.getParameter("pw");
			
			Map<String, String> paraMap = new HashMap<>(); // 비밀번호 변경할 유저를 선택하기 위해 객체를 맵에 담아줌
			
			paraMap.put("userid", userid);
			paraMap.put("pw", pw);
			
			// === 비밀번호 변경이 성공하면 "비밀번호 변경 성공!!" alert 을 띄우고 시작페이지로 이동하게 설정 === // 
			String message = "";
			String loc = "";
			
			try {
				int n = mdao.updatePwd(paraMap);
				
				if(n == 1) {
					HttpSession session = request.getSession();
					MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
					
					loginuser.setPw(pw);
					
					message = "비밀번호 변경 성공!!";
					loc = request.getContextPath()+"/index.flex";
					
				}
				
			} catch (SQLException e) {
				message = "SQL문에 오류가 있습니다.";
				loc = "javascript:history.back()"; // 이전페이지로 이동
				e.printStackTrace();
			}
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
			
		}
	}// end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception
}
