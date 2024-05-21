package shop.controller;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.domain.MemberVO;
import my.util.MyUtil;
import shop.domain.ProductVO;
import shop.model.js_5_ProductDAO;
import shop.model.js_5_ProductDAO_imple;


public class ItemUpdateList extends AbstractController {

	private js_5_ProductDAO pdao = null;
	
	public ItemUpdateList() {
		pdao = new js_5_ProductDAO_imple();
	}
	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/item/itemUpdateList.jsp");
		
		
	}

}
