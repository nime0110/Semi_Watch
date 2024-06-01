package shop.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;

public class Chart extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		String userid = loginuser.getUserid();
		
		// === 로그인 유무 검사하기 === //
		  if(!"admin".equals(userid)) {
		     // 로그인 하지 않은 경우이라면
		     
		     request.setAttribute("message", "통계를 조회하려면 관리자만 가능합니다!!");
		     request.setAttribute("loc", "javascript:history.back()"); 
		     
		  //   super.setRedirect(false);
		     super.setViewPage("/WEB-INF/msg.jsp");
		     return; // 종료
		  }
		  
		  else { // 로그인 한 경우이라면 
		     
		     super.setRedirect(false);
		     super.setViewPage("/WEB-INF/chart/chart.jsp");
		  }
		
		
		
		
		
	} // end of execute

}
