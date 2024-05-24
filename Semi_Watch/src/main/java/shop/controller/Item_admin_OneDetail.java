package shop.controller;

import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import shop.domain.ProductVO;
import shop.model.js_5_ProductDAO;
import shop.model.js_5_ProductDAO_imple;

public class Item_admin_OneDetail extends AbstractController {
	
	private js_5_ProductDAO pdao = null;
	
	public Item_admin_OneDetail() {
		pdao = new js_5_ProductDAO_imple();
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// === 관리자(admin)로 로그인 했을때만 조회가 가능하도록 한다. === //
		
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		if( loginuser.getUserid() != null &&
		   "admin".equals( loginuser.getUserid() ) ) {
			// 관리자 (admin)로 로그인 했을 경우
			
			String method = request.getMethod();
			
			if("POST".equalsIgnoreCase(method) ) {
				// admin이면서 post방식일때만
				
				String pdno = request.getParameter("pdno");
				String goBackURL = request.getParameter("goBackURL");
				
				// System.out.println("goBackURL ==> " + goBackURL);
				// goBackURL ==> /member/memberList.up?searchType=name&searchWord=%EC%9C%A0&sizePerPage=5&currentShowPageNo=15
				request.setAttribute("goBackURL", goBackURL);
				
				List<ProductVO> pvo = pdao.selectOneProductInfo(pdno);
							
				request.setAttribute("pvo", pvo);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/member/admin/memberOneDetail.jsp");
				
				
			} // end of post 
			
			
		}

	}

}
