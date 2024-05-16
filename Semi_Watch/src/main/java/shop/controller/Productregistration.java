package shop.controller;



import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class Productregistration extends AbstractController {

	
	 
	 
	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/product/productregistration.jsp");
		// super.setViewPage("/template_orgin.jsp"); // 확인했다가 지웠음
		
	}

}
