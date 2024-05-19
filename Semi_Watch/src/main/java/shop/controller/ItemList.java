package shop.controller;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.domain.MemberVO;
import shop.domain.ProductVO;
import shop.model.js_5_ProductDAO;
import shop.model.js_5_ProductDAO_imple;


public class ItemList extends AbstractController {

	private js_5_ProductDAO pdao = null;
	
	public ItemList() {
		pdao = new js_5_ProductDAO_imple();
	}
	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		String brand = request.getParameter("brand");
		String sort = request.getParameter("sort");
		
		// 기본상태에서 전체보기에 class=active 를 부여하기 위한 if
		if(brand == null ||  "전체보기".equals(brand) || (!"G-SHOCK".equals(brand) &&
		   !"세이코".equals(brand) && !"롤렉스".equals(brand) && !"카시오".equals(brand) ) ) {
			
			brand = "전체보기";
		}
		// System.out.println(brand);
		// System.out.println(sort);
		
		request.setAttribute("brand", brand);
		request.setAttribute("sort", sort);
		
		if(brand == null ||  "전체보기".equals(brand) || (!"G-SHOCK".equals(brand) &&
		!"세이코".equals(brand) && !"롤렉스".equals(brand) && !"카시오".equals(brand) ) ) {
			
			brand = "";
			
		}
		
		if(sort == null ||  "신상품순".equals(sort) ) {
			
			
			sort = "desc";
			
		}
		else {
			
			sort = "asc";
		}
		/*
		if(searchWord == null || 
		   ( searchWord != null && searchWord.trim().isEmpty() ) ) {
			// .isBlank ==> 내용이 없거나, 공백도 포함 == .trim().isEmpty()
			
			searchWord = "";
			
		}
		*/
		Map<String, String> paraMap = new HashMap<>();
		
		paraMap.put("brand", brand);
		paraMap.put("sort", sort);
		
		List<ProductVO> productList = pdao.select_product_pagin(paraMap);
		
		request.setAttribute("productList", productList);
		
			
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/item/itemList.jsp");
		
		
	}

}
