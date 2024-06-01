package order.controller;

import java.util.HashMap;

import java.util.Map;


import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import order.model.js_5_OrderDAO;
import order.model.js_5_OrderDAO_imple;

public class DeliveryUpdateJSON extends AbstractController {
	
	private js_5_OrderDAO odao = null;
	
	public DeliveryUpdateJSON() {
		odao = new js_5_OrderDAO_imple();
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {


		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		
		String ordercode = request.getParameter("ordercode");
		String deliveryType = request.getParameter("deliveryType");
        
	      
        if(!"admin".equals(loginuser.getUserid())) {
           request.setAttribute("message", "관리자만 접근 가능합니다!!");
           request.setAttribute("loc", "javascript:history.back()"); 
         
           super.setRedirect(false);
           super.setViewPage("/WEB-INF/msg.jsp");
           return;
        }
      
        else {
         // 로그인을 했을 경우
        	
        	Map<String,String> paraMap = new HashMap<>();
        	paraMap.put("ordercode", ordercode);
        	paraMap.put("deliveryType", deliveryType);
        	
            int result = odao.deliveryUpdate(paraMap);
        	
            if(result == 1) {
            	
            	JSONObject json_obj = new JSONObject();
                   
                json_obj.put("result", result);
                 
	            request.setAttribute("json", json_obj.toString());
	            
	            super.setRedirect(false);
	            super.setViewPage("/WEB-INF/jsonview.jsp");
            
            } // end of if
            
        } // end of else

	} // end of execute

}
