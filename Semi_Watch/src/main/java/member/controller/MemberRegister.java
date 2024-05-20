package member.controller;

import java.sql.SQLException;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.domain.MemberVO;
import member.model.ky_1_MemberDAO;
import member.model.ky_1_MemberDAO_imple;

public class MemberRegister extends AbstractController {

	private ky_1_MemberDAO mdao = null;
	
	public MemberRegister() {
		
		mdao = new ky_1_MemberDAO_imple();
		
	}	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String method = request.getMethod(); // "GET" or "POST"
		
		if("GET".equalsIgnoreCase(method)) {
			// super.setRedirect(false);
			super.setViewPage("/WEB-INF/member/memberRegister.jsp");
		}
		
		else {
			String username = request.getParameter("username");
			String userid = request.getParameter("userid");
			String pw = request.getParameter("pw");
			String email = request.getParameter("email");
			String hp1 = request.getParameter("hp1");
			String hp2 = request.getParameter("hp2");
			String hp3 = request.getParameter("hp3");
			String postcode = request.getParameter("postcode");
			String address = request.getParameter("address");
			String detailaddress = request.getParameter("detailAddress");
			String extraaddress = request.getParameter("extraAddress");
			String gender = request.getParameter("gender");
			String birthday = request.getParameter("birthday");
			
			String mobile = hp1+hp2+hp3;
			
			MemberVO member = new MemberVO();
			member.setUserid(userid);
			member.setPw(pw);
			member.setUsername(username);
			member.setEmail(email);
			member.setMobile(mobile);
			member.setPostcode(postcode);
			member.setAddress(address);
			member.setDetail_address(detailaddress);
			member.setExtra_address(extraaddress);
			member.setGender(gender);
			member.setBirthday(birthday);
			/*
			// === 회원가입이 성공되어지면 "회원가입 성공" 이라는 alert 를 띄우고 시작페이지로 이동한다. === //
			String message = "";
			String loc = "";
			
			try {
				
				int n = mdao.registerMember(member);
				
				if(n == 1) {
					message = "회원가입 성공";
					loc = request.getContextPath()+"/index.flex"; // 시작페이지로 이동한다.
				}
				
			} catch (SQLException e) {
				message = "회원가입 실패";
				loc = "javascript:history.back()";// 자바스크립트를 이용해 이전페이지로 이동하는 것
				e.printStackTrace();
			}
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
			*/
			
			// ### 회원가입이 성공되어지면 자동으로 로그인 되도록 하겠다. ### //
			try {
				
				int n = mdao.registerMember(member);
				
				if(n == 1) {
					request.setAttribute("userid", userid);
					request.setAttribute("pw", pw);
					
					super.setRedirect(false);
					super.setViewPage("/WEB-INF/login/goLogin.jsp");
				}
				
			} catch (SQLException e) {	
				e.printStackTrace();
				
				String message = "회원가입 실패";
				String loc = "javascript:history.back()";// 자바스크립트를 이용해 이전페이지로 이동하는 것
				
				request.setAttribute("message", message);
				request.setAttribute("loc", loc);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/msg.jsp");
				
			}	
			
		}

	}

}
