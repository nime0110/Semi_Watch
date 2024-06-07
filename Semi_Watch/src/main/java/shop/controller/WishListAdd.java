package shop.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.ss_2_MemberDAO;
import member.model.ss_2_MemberDAO_imple;
import shop.domain.ImageVO;
import shop.domain.ProductVO;
import shop.domain.Product_DetailVO;
import shop.model.ss_2_ProductDAO;
import shop.model.ss_2_ProductDAO_imple;

public class WishListAdd extends AbstractController {
	private ss_2_ProductDAO pdao = null;
	
	public WishListAdd() {
		pdao = new ss_2_ProductDAO_imple();
	}
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    String pdnos = request.getParameter("pdnos"); // 99, 99, 95
	    String selectedColors = request.getParameter("selectedColors"); // white, pink, white 그때그때 다르게 들

	    System.out.println("===== selectedColors:" + selectedColors); 

	    String[] colorSplit = selectedColors.split(",");
	    String[] pdnoArray = pdnos.replace(" ", "").split(",");

	    Map<String, Object> paraMap = new HashMap<>();
	    paraMap.put("colorsArray", colorSplit);
	    paraMap.put("pdnoArray", pdnoArray);

	    List<ProductVO> wishList = pdao.wishAdd(paraMap);

	    JSONArray jsonArr = new JSONArray();
	    if (!wishList.isEmpty()) {
	        for (int i = 0; i < wishList.size(); i++) {
	            ProductVO pvo = wishList.get(i);
	            JSONObject jsonObj = new JSONObject();
	            jsonObj.put("pdname", pvo.getPdname());
	            jsonObj.put("pdimg", pvo.getPdimg1());
	            jsonObj.put("pdsaleprice", pvo.getSaleprice());
	            jsonObj.put("pdno", pvo.getPdno());
	            jsonObj.put("color", pvo.getPdvo().getColor() );

	            System.out.println("~~~~~ pdname: " + pvo.getPdname() + ", color: " + colorSplit[i]);
	            //~~~~~ pdname: GMA-S2100BS-4ADR, color: none

	            jsonArr.put(jsonObj);
	        }
	    } else {
	        System.out.println("wishList is empty.");
	    }

	    String json = jsonArr.toString();
	    System.out.println("json: " + json); //json: [{"pdno":"176","color":"none","pdimg":"new_2_thum_20240601183921524680952774800.png","pdsaleprice":135000,"pdname":"GMA-S2100BS-4ADR"},{"pdno":"174","color":"none","pdimg":"25_thum_202405261133238777773987000.png","pdsaleprice":16090000,"pdname":"DATEJUST 41"}]


	    request.setAttribute("json", json);
	    super.setViewPage("/WEB-INF/jsonview.jsp");
	}



}
