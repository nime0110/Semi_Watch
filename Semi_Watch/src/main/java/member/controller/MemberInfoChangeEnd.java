package member.controller;

import java.sql.SQLException;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import member.model.jh_3_MemberDAO;
import member.model.jh_3_MemberDAO_imple;

public class MemberInfoChangeEnd extends AbstractController {
	
	private jh_3_MemberDAO mdao = null;
	
	public MemberInfoChangeEnd() {
		mdao = new jh_3_MemberDAO_imple();
	}	

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String method = request.getMethod();
		
		if("POST".equalsIgnoreCase(method)) {	// post 방식일 경우에만 실행
			
			String infoUpdate = request.getParameter("infoUpdate");
			String login_userid = request.getParameter("login_userid");
			
			MemberVO member = new MemberVO();
			member.setUserid(login_userid);
			
			String message = "";
			String loc = "";
			
			try {
				int result = 0;
				
				
				if("pwd".equals(infoUpdate)){	// 비밀번호 변경일 경우에만
					String newPassword = request.getParameter("newPassword");
					if(newPassword == null) {
						System.out.println("비밀번호 값 null 임");
					}
					
					member.setPw(newPassword);
					
//					result = mdao.updatePWD(member);
					
				}
				else if("email".equals(infoUpdate)){
					String newemail = request.getParameter("newemail");
					if(newemail == null) {
						System.out.println("이메일 값 null 임");
					}
					
					member.setEmail(newemail);
					
//					result = mdao.updateEmail(member);
				}
				else if("phone".equals(infoUpdate)){
					String newemail = request.getParameter("newemail");
					if(newemail == null) {
						System.out.println("이메일 값 null 임");
					}
					
					member.setEmail(newemail);
					
//					result = mdao.updatePhone(member);
				}
				else if("post".equals(infoUpdate)){
					String newemail = request.getParameter("newemail");
					if(newemail == null) {
						System.out.println("이메일 값 null 임");
					}
					
					member.setEmail(newemail);
					
//					result = mdao.updatePost(member);
				}
				
				
				
				message = "회원정보 수정 성공!!";
				loc = request.getContextPath()+"//member/memberInfoChange.flex"; // 다시페이지로 이동한다.
			
			
			}catch(Exception e) {
				message = "SQL구문 에러발생";
				loc = "javascript:history.back()"; // 자바스크립트를 이용한 이전페이지로 이동하는 것. 
				e.printStackTrace();
			}
			
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
			
			// request.setAttribute("memberEditEnd", true); 필요없을듯
			
		//	super.setRedirect(false); 
			super.setViewPage("/WEB-INF/msg.jsp");
			
		
		   
		}// end of if("POST".equalsIgnoreCase(method))---

	}

}
