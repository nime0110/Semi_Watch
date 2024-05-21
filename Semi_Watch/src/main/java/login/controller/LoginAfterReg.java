package login.controller;

import java.util.HashMap;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import member.model.ky_1_MemberDAO;
import member.model.ky_1_MemberDAO_imple;

public class LoginAfterReg extends AbstractController {

	private ky_1_MemberDAO mdao = null;
	
	public LoginAfterReg() {
		
		mdao = new ky_1_MemberDAO_imple();
		
	}	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod();
		if(!"POST".equalsIgnoreCase(method)) { // get 방식으로 들어온다면
			
			String message = "비정상적인 경로 접근이 감지되었습니다.";
			String loc = "javascript:history.back()";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
			
			return; // public void execute(HttpServletRequest request, HttpServletResponse response) 메소드 종료 
			
		}
		
		// post 방식이라면		
		String userid = request.getParameter("userid");
		String pwd = request.getParameter("pw");
		
		//== 클라이언트의 아이피 주소를 알아오는 것 ==// 
		String clientip = request.getRemoteAddr();
		//먼저 C:\NCS\workspace_jsp\MyMVC\src\main\webapp\JSP 파일을 실행시켰을 때 IP 주소가 제대로 출력되기위한 방법.txt에 있는 방법 실행할 것
		// System.out.println("확인용 clientip : " + clientip);
		// 확인용 clientip : 0:0:0:0:0:0:0:1
		
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("userid", userid);
		paraMap.put("pwd", pwd);
		paraMap.put("clientip", clientip);
		
		MemberVO loginuser = mdao.loginAfterReg(paraMap);
		
		if(loginuser != null) { // 유저아이디가 있을 경우
			
			HttpSession session = request.getSession();
			// WAS 메모리에 생성되어져 있는 session 을 불러오는 것이다.
			
			session.setAttribute("loginuser", loginuser);
			
			super.setRedirect(true);
			super.setViewPage(request.getContextPath()+"/index.flex");		
			
		}
		else { // 유저아이디가 없을 경우
			
			String message = "로그인 실패";
			String loc = "javascript:history.back()";
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
			
		}
		
		
            
	}// end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception			
}
