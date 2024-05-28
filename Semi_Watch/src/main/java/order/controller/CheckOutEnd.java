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
		String totalCost_str = request.getParameter("totalCost");
		
		System.out.println("확인용 totalCost_str : "+totalCost_str);
		
		int totalCost = Integer.parseInt(totalCost_str);
		
		request.setAttribute("totalCost", totalCost);
		
		System.out.println("확인용 값넘어오는지? : "+totalCost_str);
		*/
		
		super.setViewPage("/WEB-INF/order/paymentGateway.jsp");
		
		
		
		
		
		
		
		
		
		
		
		/*	
		String userid = request.getParameter("userid");	// 임의로 변경 가능한 값
		String coinmoney = request.getParameter("coinmoney");	// 충전금액
		String productName = "새우깡";
		int productPrice = Integer.parseInt(coinmoney);
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		if(loginuser.getUserid().equals(userid)) {	// 로그인한 userid 와 url에 보낸 userid 가 같은경우
			
			request.setAttribute("userid", loginuser.getUserid());
			request.setAttribute("email", loginuser.getEmail());
			request.setAttribute("name", loginuser.getName());
			request.setAttribute("mobile", loginuser.getMobile());
			request.setAttribute("productName", productName);
			request.setAttribute("productPrice", productPrice);
			
			// System.out.println("!@!# 확인용 email : "+loginuser.getEmail());
			// System.out.println("!@!# 확인용 mobile : "+loginuser.getMobile());
			
			super.setViewPage("/WEB-INF/member/paymentGateway.jsp");
			
		}
		else {	// 로그인한 사용자가 다른 사용자의 코인을 충전하려고 결제를 시도하는 경우 
            String message = "다른 사용자의 코인충전 결제 시도는 불가합니다.!!";
            String loc = "javascript:history.back()";
            
            request.setAttribute("message", message);
            request.setAttribute("loc", loc);
            
         // super.setRedirect(false);
            super.setViewPage("/WEB-INF/msg.jsp");
            
		}// end of if(loginuser.getUserid().equals(userid))-----
		*/
		
	}// end of public void execute(HttpServletRequest request, HttpServletResponse response)


}
