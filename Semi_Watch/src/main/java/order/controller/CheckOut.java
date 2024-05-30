package order.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import member.model.jh_3_MemberDAO;
import member.model.jh_3_MemberDAO_imple;
import shop.domain.CartVO;
import shop.domain.ProductVO;
import shop.model.jh_3_ProductDAO;
import shop.model.jh_3_ProductDAO_imple;

public class CheckOut extends AbstractController {
	
	
	private jh_3_ProductDAO pdao = null;
	
	public CheckOut() {
		pdao = new jh_3_ProductDAO_imple();
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
		
		
		// 실제 구동소스
		if(super.checkLogin(request)) {	// 로그인 되어진 상태인지
			HttpSession session = request.getSession();
			MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
			
			
			// 장바구니 또는 제품상세페이지(바로구매)에서 값을 받아온다.
			String str_pdno = request.getParameter("str_pdno");					// 제품번호
			String str_cart_qty = request.getParameter("str_cart_qty");			// 구매수량
			String str_cartno = request.getParameter("str_cartno");				// 장바구니번호
			String str_pd_detailno = request.getParameter("str_pd_detailno");	// 제품상세번호
				
			String str_pdPriceArr = request.getParameter("str_pdPriceArr");		// 상품별 총가격(개당단가 * 수량)
			String str_pdPointArr = request.getParameter("str_pdPointArr");		// 상품별 총포인트(개당포인트 * 수량)
			
		
			System.out.println("제품번호 "+str_pdno);
			System.out.println("구매수량 "+str_cart_qty);
			System.out.println("장바구니번호 "+str_cartno);
			System.out.println("제품상세번호 "+str_pd_detailno);
			System.out.println("상품별총가격 "+str_pdPriceArr);
			System.out.println("상품별총포인트 "+str_pdPointArr);
		/*	
			제품번호 158,153,173,174,170,95
			구매수량 3,1,6,3,3,5
			장바구니번호 30,31,28,29,34,35
			제품상세번호 109,104,124,125,121,16
			상품별총가격 178560000,118000,488880000,48270000,33000000,350000
			상품별총포인트 1785600,1180,4888800,482700,330000,3500
		*/
			
			// 받아온 문자열을 배열로 바꿔준다.(장바구니는 
			String[] pdnoArr = str_pdno.split("\\,");
			String[] cart_qtyArr = str_cart_qty.split("\\,");
			String[] pd_detailnoArr = str_pd_detailno.split("\\,");
			
			String[] pdPriceArr = str_pdPriceArr.split("\\,");
			String[] pdPointArr = str_pdPointArr.split("\\,");
			
			// 구매시 적립되는 총포인트 합계
			int totalPoint = 0;
			for(int i=0; i<pdPointArr.length; i++) {
				totalPoint += Integer.parseInt(pdPointArr[i]);
			}
			
			// 구매시 총 제품구매가격 합계
			int totalPrice = 0;
			for(int i=0; i<pdPriceArr.length; i++) {
				totalPrice += Integer.parseInt(pdPriceArr[i]);
			}
			
			
			System.out.println("확인용 포인트 합계 : "+totalPoint);
			
			// === 장바구니테이블(tbl_cart)에 delete 할 데이터 ===
	     	if(str_cartno != null) {
	     		// 특정제품을 바로주문하기를 한 경우라면 str_cartno 의 값은 null 이 된다.
	     		String[] cartnoArr = str_cartno.split("\\,");
	     		request.setAttribute("cartnoArr", cartnoArr);
	     	}
	     	
			// 필요한 데이터 가져오기
			// 제품테이블 : 제품명, 제품이미지, 제품가격
			// 장바구니 : 필요한건 넘겨받음
			// 제품상세테이블 : 옵션명
			
			Map<String, String[]> paraMap = new HashMap<>();
			paraMap.put("pdnoArr", pdnoArr);
			paraMap.put("pd_detailnoArr", pd_detailnoArr);
			
			List<ProductVO> pvoList = pdao.select_odrProductInfo(paraMap);
			
			request.setAttribute("userid", loginuser.getUserid());	// 유저아이디 결제하기시 검증용
			request.setAttribute("pvoList", pvoList);
			request.setAttribute("pdnoArr", pdnoArr);
			request.setAttribute("pd_detailnoArr", pd_detailnoArr);
			request.setAttribute("cart_qtyArr", cart_qtyArr);
			
			request.setAttribute("pdPriceArr", pdPriceArr);
			request.setAttribute("totalPoint", totalPoint);
			request.setAttribute("totalPrice", totalPrice);
			
	     	
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/order/checkOut.jsp");
			
		}
		else {
			String message = "로그인 후 이용가능 합니다.";
			String loc = request.getContextPath()+"/login/login.flex";
            
			request.setAttribute("message", message);
			request.setAttribute("loc", loc);
            
			super.setRedirect(false); 
			super.setViewPage("/WEB-INF/msg.jsp");
		}
		
	}// end of public void execute(HttpServletRequest request, HttpServletResponse response) ----

}
