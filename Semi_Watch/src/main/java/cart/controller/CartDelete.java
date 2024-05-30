package cart.controller;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.model.js_5_ProductDAO;
import shop.model.js_5_ProductDAO_imple;

public class CartDelete extends AbstractController {

	private js_5_ProductDAO pdao = null;
	
	public CartDelete() {
	      pdao = new js_5_ProductDAO_imple();
	}
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		if(!super.checkLogin(request)) {
			
			request.setAttribute("message", "장바구니를 수정하려면 로그인이 되어야합니다!!");
	        request.setAttribute("loc", "javascript:history.back()"); 
	         
	      //  super.setRedirect(false);
	         super.setViewPage("/WEB-INF/msg.jsp");
	         return;
			
		} // end of if
		else {
			
			String method = request.getMethod();
			
			if("POST".equalsIgnoreCase(method) && super.checkLogin(request)) {
				
				
				String cartno = request.getParameter("cartno");
				
				// 장바구니 테이블에서 특정 상품을 장바구니에서 비우기
				int n = pdao.delCart(cartno);
				
				JSONObject jsonObj = new JSONObject(); // { }
			
				jsonObj.put("n", n); // {"n":1}
				
				String json = jsonObj.toString(); // "{"n":1}"
				
				request.setAttribute("json", json);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/jsonview.jsp");
				
				
			} // end of if("POST".equalsIgnoreCase(method)) {
				
		} // end of else
	}

}
