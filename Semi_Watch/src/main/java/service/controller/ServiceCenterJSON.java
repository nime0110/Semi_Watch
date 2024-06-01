package service.controller;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.model.ky_1_ProductDAO;
import shop.model.ky_1_ProductDAO_imple;

public class ServiceCenterJSON extends AbstractController {

	private ky_1_ProductDAO pdao = null;
	
	public ServiceCenterJSON() {
		
		pdao = new ky_1_ProductDAO_imple();
		
	}	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// tbl_map(위, 경도) 테이블에 있는 정보를 가져오기(select)
		List<Map<String, String>> centerMapList = pdao.selectCenterMap();
		
		JSONArray jsonArr = new JSONArray(); // []
		
		if(centerMapList.size() > 0) {
			for(Map<String, String> centerMap : centerMapList) {
				JSONObject jsonobj = new JSONObject(); // {}
				
				String storename = centerMap.get("STORENAME");
				String storeurl = centerMap.get("STOREURL");
				String storeimg = centerMap.get("STOREIMG");
				String storeaddress = centerMap.get("STOREADDRESS");
				double lat = Double.parseDouble(centerMap.get("LAT"));
				double lng = Double.parseDouble(centerMap.get("LNG"));
				int zIndex = Integer.parseInt(centerMap.get("ZINDEX"));
				
				jsonobj.put("storename", storename);
				jsonobj.put("storeurl", storeurl);
				jsonobj.put("storeimg", storeimg);
				jsonobj.put("storeaddress", storeaddress);
				jsonobj.put("lat", lat);
				jsonobj.put("lng", lng);
				jsonobj.put("zIndex", zIndex);
				
				jsonArr.put(jsonobj);	
			}// end of for
			
		}// end of if(centerMapList.size() > 0) 
		
		String json = jsonArr.toString();
		request.setAttribute("json", json);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/jsonview.jsp");

	}// end of public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception 

}
