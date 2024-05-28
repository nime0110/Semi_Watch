package member.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;

public class MemberInfoChange extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if(checkLogin(request)) {
//			HttpSession session = request.getSession();
//			MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
//			String userid = (String) loginuser.getUserid();
			
			// request.setAttribute("userid", userid);
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/member/memberInfoChange.jsp");
		}
		else {
			super.setRedirect(true);
			super.setViewPage(request.getContextPath()+"/login/login.flex");
		}
		
		
		

	}

}
