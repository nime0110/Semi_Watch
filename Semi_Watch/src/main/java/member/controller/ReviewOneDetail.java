package member.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import member.model.ky_1_MemberDAO;
import member.model.ky_1_MemberDAO_imple;
import review.domain.ReviewVO;

public class ReviewOneDetail extends AbstractController {

	private ky_1_MemberDAO mdao = null;
	
	public ReviewOneDetail() {
		
		mdao = new ky_1_MemberDAO_imple();
		
	}	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// === 관리자(admin)로 로그인 했을때만 조회가 가능하도록 한다. === //
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		if( loginuser != null && "admin".equals(loginuser.getUserid()) ) {
			// 관리자(admin)로 로그인 했을 경우
			String method = request.getMethod();
			
			if("POST".equalsIgnoreCase(method)) {
				// POST 방식일 때
				String reviewno = request.getParameter("reviewno");
				String goBackURL = request.getParameter("goBackURL");
				
				ReviewVO rvo = mdao.selectOneReview(reviewno);
				
				request.setAttribute("rvo", rvo);
				request.setAttribute("goBackURL", goBackURL);
				
				super.setRedirect(false);
		        super.setViewPage("/WEB-INF/member/admin/reviewOneDetail.jsp");
				
				
			}// end of if("POST".equalsIgnoreCase(method))
			
			
		}
		else {
		      // 로그인을 안한 경우 또는 일반사용자로 로그인 한 경우
		      String message = "관리자만 접근이 가능합니다.";
		      String loc = "javascript:history.back()";

		      request.setAttribute("message", message);
		      request.setAttribute("loc", loc);

		      // super.setRedirect(false);
		      super.setViewPage("/WEB-INF/msg.jsp");			
			
		}
			

	}// end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception 

}
