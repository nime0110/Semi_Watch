package order.controller;

import java.util.ArrayList;
import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.jh_3_MemberDAO;
import member.model.jh_3_MemberDAO_imple;
import shop.domain.CartVO;
import shop.model.jh_3_ProductDAO;
import shop.model.jh_3_ProductDAO_imple;

public class CheckOut extends AbstractController {
	
	
	private jh_3_ProductDAO pdao = null;
	
	public CheckOut() {
		pdao = new jh_3_ProductDAO_imple();
	}	

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 가정은 다음과 같다.
		/*
			장바구니 or 바로구매 시 값을 넘겨받는다.
			1. 장바구니
			- 체크된 제품의 제품코드, 제품상세코드 이거 두개만 받아오면 셀렉해서 값을 띄어주면 된다.
			- 혹은 장바구니에서 값을 넘겨주는데, 다넘겨주던가 (제품번호, 제품이미지, 제품명, 주문수량, 옵션명, 포인트 까지)
			
			그래서 받은 값이 복수값이라면, list 로 넘겨주기 때문에 받은값을 받아와서 보여주면된다.
		*/	
			// 예시
			// 상품정보 전달해주기
			
			
			
			
		/*	
			2. 바로구매
			- 제품번호, 제품명, 주문수량, 옵션명 까지만 받아오면 가능함
		*/
		
		
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/order/checkOut.jsp");
		
		
		
		
		
		
		// 실제 구동소스
/*		
		if(super.checkLogin(request)) {	// 로그인 되어진 상태인지
			
			
			
		}
		else {
			String message = "로그인 후 이용가능 합니다.";
			String loc = request.getContextPath()+"/login/login.flex";
            
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
            
			super.setRedirect(false); 
			super.setViewPage("/WEB-INF/msg.jsp");
		}
*/		
	}// end of public void execute(HttpServletRequest request, HttpServletResponse response) ----

}
