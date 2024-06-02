package order.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import order.model.js_5_OrderDAO;
import order.model.js_5_OrderDAO_imple;

public class AdminOneReviewJSON extends AbstractController {
	
	private js_5_OrderDAO odao = null;
	
	public AdminOneReviewJSON() {
		odao = new js_5_OrderDAO_imple();
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		
		String ruserid = request.getParameter("ruserid");
		String rpdno = request.getParameter("rpdno");
        
	      
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
        	paraMap.put("ruserid", ruserid);
        	paraMap.put("rpdno", rpdno);
        	
            Map<String,String> onereviewmap = odao.AdminOneReview(paraMap);
        	
            JSONObject json_obj = new JSONObject();
            
            if(onereviewmap == null) {
            	
            	json_obj.put("result", 0);
            	
            }else {
            	json_obj.put("result", 1);
            	
            	json_obj.put("reviewno", onereviewmap.get("reviewno"));
            	json_obj.put("fk_pdno", onereviewmap.get("fk_pdno"));
            	json_obj.put("pdname", onereviewmap.get("pdname"));
            	json_obj.put("fk_userid", onereviewmap.get("fk_userid"));
            	json_obj.put("username", onereviewmap.get("username"));
            	json_obj.put("review_content", onereviewmap.get("review_content"));
            	json_obj.put("starpoint", onereviewmap.get("starpoint"));
            	json_obj.put("review_date", onereviewmap.get("review_date"));
            	
            }
            
    
            request.setAttribute("json", json_obj.toString());
            
            super.setRedirect(false);
            super.setViewPage("/WEB-INF/jsonview.jsp");
      
            
        } // end of else
		

	}

}
