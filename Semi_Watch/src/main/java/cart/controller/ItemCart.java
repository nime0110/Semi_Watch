package cart.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import shop.domain.ProductVO;
import shop.model.sw_4_ProductDAO;
import shop.model.sw_4_ProductDAO_imple;

public class ItemCart extends AbstractController {
	
	private sw_4_ProductDAO mdao = null;
	
	public ItemCart() {
	      mdao = new sw_4_ProductDAO_imple();
	   }
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		  /*
			  String pdname = request.getParameter("pdname");
			  String brand = request.getParameter("brand");
			  String price = request.getParameter("price");
			  String saleprice = request.getParameter("saleprice");
			  String pdimg1 = request.getParameter("pdimg1");
			 
			  Map<String, String> paraMap = new HashMap<>(); // 보내기 위해 담아준것
			  
			  paraMap.put("pdname",pdname);
			  paraMap.put("brand", brand);
			  paraMap.put("price", price);
			  paraMap.put("saleprice", saleprice);
			  paraMap.put("pdimg1", pdimg1);
		  */
		///////////////////////////////////////////////////
		  
		// mdao.select_product(paraMap);
		List<ProductVO> ProductList = mdao.select_product();
		
		request.setAttribute("ProductList", ProductList);
		
		
		super.setRedirect(false);
	    super.setViewPage("/WEB-INF/item/itemCart.jsp");
	}

}
