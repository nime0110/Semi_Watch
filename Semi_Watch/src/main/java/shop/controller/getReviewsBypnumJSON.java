package shop.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.ss_2_MemberDAO;
import member.model.ss_2_MemberDAO_imple;
import shop.model.ss_2_ProductDAO;
import shop.model.ss_2_ProductDAO_imple;

public class getReviewsBypnumJSON extends AbstractController {
	
	private ss_2_ProductDAO pdao = null;
	
	public getReviewsBypnumJSON() {
		pdao = new ss_2_ProductDAO_imple();
	}
		
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// TODO Auto-generated method stub
		// ------------------ 리뷰 시작 -----------------------// 
        Map<String, String> paraMap = new HashMap<>();
        
        String pdno =request.getParameter("pdno");
        String currentShowPageNo =request.getParameter("currentShowPageNo");
        String sizePerPage =request.getParameter("sizePerPage");
        paraMap.put("pdno", pdno);
        paraMap.put("currentShowPageNo", currentShowPageNo);
        paraMap.put("sizePerPage", sizePerPage);

        int totalPage = pdao.getTotalPage(paraMap);
		List<Map<String, String>> rvMapList = pdao.getReviewsBypnum(paraMap);
		
		JSONArray jsonArr = new JSONArray(); //json 배열

		JSONObject jsonObj = new JSONObject(); //json객체
	    jsonObj.put("totalPage", totalPage);

        for (Map<String, String> review : rvMapList) {
            JSONObject reviewObj = new JSONObject(review);
            jsonArr.put(reviewObj);
        }
        jsonArr.put(jsonObj);

        String json = jsonArr.toString();
        System.out.println("json: " + json);

	    request.setAttribute("json", json);
	    super.setViewPage("/WEB-INF/jsonview.jsp");
	}

}
