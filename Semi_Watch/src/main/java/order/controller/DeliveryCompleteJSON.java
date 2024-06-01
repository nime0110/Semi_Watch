package order.controller;



import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import order.model.js_5_OrderDAO;
import order.model.js_5_OrderDAO_imple;

public class DeliveryCompleteJSON extends AbstractController {

private js_5_OrderDAO odao = null;
	
	public DeliveryCompleteJSON() {
		odao = new js_5_OrderDAO_imple();
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {


		if(!super.checkLogin(request)) {
			
			request.setAttribute("message", "주문내역을 조회하려면 먼저 로그인 부터 하세요!!");
	        request.setAttribute("loc", "javascript:history.back()"); 
	         
	         super.setRedirect(false);
	         super.setViewPage("/WEB-INF/msg.jsp");
	         return;
			
		}else {
         // 로그인을 했을 경우
        	
			String ordercode = request.getParameter("ordercode");
			
            int result = odao.deliveryComplete(ordercode);
        	
            if(result == 1) {
            	
            	JSONObject json_obj = new JSONObject();
                   
                json_obj.put("result", result);
                 
	            request.setAttribute("json", json_obj.toString());
	            
	            super.setRedirect(false);
	            super.setViewPage("/WEB-INF/jsonview.jsp");
            
            } // end of if
            
        } // end of else
        
	}// end of execute    

}
