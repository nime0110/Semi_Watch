package order.controller;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;

public class CheckOutEnd extends AbstractController {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*
		// 테스트용 코드
		request.setAttribute("userid", "jhknvg123");
		super.setViewPage("/WEB-INF/order/paymentGateway.jsp");
		*/
		
		// 주소창 장난질 예방
        String referer = request.getHeader("referer");
        if (referer == null) {
            // URL을 통해 직접 접근한 경우 홈 페이지로 리디렉션
            super.setRedirect(true);
            super.setViewPage(request.getContextPath() + "/index.flex");
            return;
        }
        
		
		// 실제 코드
		if(super.checkLogin(request)) {	// 로그인 여부 확인
			
			String userid = request.getParameter("userid");
			
			HttpSession session = request.getSession();
			MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
			
			if(userid.equals(loginuser.getUserid())) {
				
				request.setAttribute("userid", userid);
				super.setViewPage("/WEB-INF/order/paymentGateway.jsp");
				
			}
			else { // 다른사용자꺼 결제시도 하는 경우
				String message = "다른 사용자의 결제 시도는 불가합니다.!!";
	            String loc = "javascript:history.back()";
	            
	            request.setAttribute("message", message);
	            request.setAttribute("loc", loc);
	            
	            // super.setRedirect(false);
	            super.setViewPage("/WEB-INF/msg.jsp");
			}
			
			
		}
		else {	// 로그인한 사용자가 다른 사용자의 코인을 충전하려고 결제를 시도하는 경우 
            String message = "결제하려면 로그인 먼저 하셔야 합니다.";
            String loc = request.getContextPath()+"/login/login.flex";
            
            request.setAttribute("message", message);
            request.setAttribute("loc", loc);
            
         // super.setRedirect(false);
            super.setViewPage("/WEB-INF/msg.jsp");
            
		}
		
		
		
	
		
	}// end of public void execute(HttpServletRequest request, HttpServletResponse response)


}
