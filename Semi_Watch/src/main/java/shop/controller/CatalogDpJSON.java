package shop.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.domain.ProductVO;
import shop.model.ky_1_ProductDAO;
import shop.model.ky_1_ProductDAO_imple;

public class CatalogDpJSON extends AbstractController {

	private ky_1_ProductDAO pdao = null;
	
	public CatalogDpJSON() {
		
		pdao = new ky_1_ProductDAO_imple();
	}	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String start = request.getParameter("start");
		String len = request.getParameter("len");
		
		Map<String, String> paraMap = new HashMap<>();
		paraMap.put("start", start); // "1"
		paraMap.put("len", len); // 6
		
		String end = String.valueOf((Integer.parseInt(start) + Integer.parseInt(len) - 1));
		
		// System.out.println("확인용 start : " + start);
		// System.out.println("확인용 len : " + len);
		// System.out.println("확인용 end : " + end);
		
		paraMap.put("end", end); // 6
		
		// 최신 등록순으로 6개의 상품 이미지를 가져오기
		List<ProductVO> productList = pdao.selectByRegiDate(paraMap);
		
		JSONArray jsonArr = new JSONArray(); // [] 배열형태
		
		if(productList.size() > 0) { // DB 조회결과가 있을 경우
			
			
			
			for(ProductVO pvo : productList) {
				
				JSONObject jsonObj = new JSONObject(); // {} 하나의 객체
				
				jsonObj.put("pdno", pvo.getPdno());
				jsonObj.put("pdimg1", pvo.getPdimg1());
				
				jsonArr.put(jsonObj); // [{"pdno":"블라블라"}, {"pdimg1":"블라블라"}]
				
			}// end of for
			
		}// end of if(productList.size() > 0)
 		
		String json = jsonArr.toString();
		
		System.out.println("확인용 json : " + json);
		
		request.setAttribute("json", json);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/jsonview.jsp");
		
	}// end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception 

}
