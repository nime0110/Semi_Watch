package order.controller;

import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import order.model.jh_3_OrderDAO;
import order.model.jh_3_OrderDAO_imple;

public class OrderListDetail extends AbstractController {
	
	private jh_3_OrderDAO odao = null;
	
	public OrderListDetail() {
		odao = new jh_3_OrderDAO_imple();
	}
	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 주소창 장난질 예방
        String referer = request.getHeader("referer");
        if (referer == null) {
            // URL을 통해 직접 접근한 경우 홈 페이지로 리디렉션
            super.setRedirect(true);
            super.setViewPage(request.getContextPath() + "/index.flex");
            return;
        }
		
		if(!super.checkLogin(request)) {
			String message = "로그인 후 이용가능 합니다.";
			String loc = request.getContextPath()+"/login/login.flex";
            
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
            
			super.setRedirect(false); 
			super.setViewPage("/WEB-INF/msg.jsp");
		}
		else {
			
			// 테스트용 데이터
			/*
				보여저야할 데이터
				브랜드 => 상품테이블
				제품이미지 => 상품테이블
				제품명 => 상품테이블
				옵션 => 제품상세테이블
				개당가격 => 상품테이블
				수량 => 주문상세테이블
				주문일자 => 주문테이블
				배송상태 => 주문상세테이블
				
				배송정보 => 배송지테이블
				최종결제금액 => 주문테이블
				
				제품번호	=> 리뷰용
				상품상세번호 => 리뷰용
			 */
			
			String ordcode = request.getParameter("odrcode");	// 주문번호
			// 제품 정보
			List<Map<String, String>> ordDetail_List = odao.getordDetailInfo(ordcode);
			
			// 구매 배송정보
			Map<String, String> ordInfo = odao.getordInfo(ordcode);
			
			request.setAttribute("ordDetail_List", ordDetail_List);
			request.setAttribute("ordInfo", ordInfo);
			
			super.setViewPage("/WEB-INF/order/orderListDetail.jsp");

		}
		
		
	}// end of public void execute---------------

}
