package shop.controller;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.model.js_5_ProductDAO;
import shop.model.js_5_ProductDAO_imple;

public class Purchase_byMonthCnt_JSON extends AbstractController {

	private js_5_ProductDAO pdao = null;
	
	public Purchase_byMonthCnt_JSON() {
	      pdao = new js_5_ProductDAO_imple();
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		String userid = request.getParameter("userid");
      
    
      if(!"admin".equals(userid)) {
         request.setAttribute("message", "통계는 관리자만 접근 가능합니다!!");
         request.setAttribute("loc", "javascript:history.back()"); 
       
         super.setRedirect(false);
         super.setViewPage("/WEB-INF/msg.jsp");
         return;
      }
    
      else {
       // 로그인을 했을 경우
      	
      	// System.out.println("userid"+userid);
      	 
          List<Map<String, String>> Purchase_map_List = pdao.Purchase_byMonthCnt(userid);
      	
          JSONArray json_arr = new JSONArray();
          
          if(Purchase_map_List.size() > 0) {
          	
              for(Map<String, String> map : Purchase_map_List) {
              	
                 JSONObject json_obj = new JSONObject();
                 
                 json_obj.put("brand", map.get("brand"));
	               json_obj.put("cnt", map.get("cnt"));
	               json_obj.put("sumpay", map.get("sumpay"));
	               json_obj.put("order_pct", map.get("order_pct"));
	               json_obj.put("m_01", map.get("m_01"));
	               json_obj.put("m_02", map.get("m_02"));
	               json_obj.put("m_03", map.get("m_03"));
	               json_obj.put("m_04", map.get("m_04"));
	               json_obj.put("m_05", map.get("m_05"));
	               json_obj.put("m_06", map.get("m_06"));
	               json_obj.put("m_07", map.get("m_07"));
	               json_obj.put("m_08", map.get("m_08"));
	               json_obj.put("m_09", map.get("m_09"));
	               json_obj.put("m_10", map.get("m_10"));
	               json_obj.put("m_11", map.get("m_11"));
	               json_obj.put("m_12", map.get("m_12"));
	               
                 json_arr.put(json_obj);
                 
              }// end of for--------------
              
          } // end of if
          
          request.setAttribute("json", json_arr.toString());
          
          super.setRedirect(false);
          super.setViewPage("/WEB-INF/jsonview.jsp");
          
      } // end of else

	}

}
