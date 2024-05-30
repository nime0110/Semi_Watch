package cart.controller;


import java.util.List;


import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import shop.domain.CartVO;
import shop.model.js_5_ProductDAO;
import shop.model.js_5_ProductDAO_imple;


public class ItemCart extends AbstractController {
	
	private js_5_ProductDAO pdao = null;
	
	public ItemCart() {
	      pdao = new js_5_ProductDAO_imple();
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 주소창 직접 접근 방지
        String referer = request.getHeader("referer");
        if (referer == null) {
            // URL을 통해 직접 접근한 경우 홈 페이지로 리디렉션
            super.setRedirect(true);
            super.setViewPage(request.getContextPath() + "/index.flex");
            return;
        }
		
    	// 로그인 여부 확인
        if (!super.checkLogin(request)) {
            // 로그인하지 않은 경우 로그인 메시지와 함께 이전 페이지로 이동
            request.setAttribute("message", "장바구니를 보려면 먼저 로그인 부터 하세요!!");
            request.setAttribute("loc", "javascript:history.back()");
            super.setRedirect(false);
            super.setViewPage("/WEB-INF/msg.jsp");
            return;
        }
		
	    // 로그인한 경우 장바구니 정보 조회
	    HttpSession session = request.getSession();
        MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
        System.out.println("~~~확인용: " +  loginuser.getUserid());
       
        // 로그인한 사용자의 장바구니 정보를 데이터베이스에서 조회
        List<CartVO> cartList = pdao.selectProductCart(loginuser.getUserid());
   
        request.setAttribute("cartList", cartList);
        // 장바구니 페이지로 이동
        super.setRedirect(false);
        super.setViewPage("/WEB-INF/item/itemCart.jsp");
        
    }
}
        