package shop.controller;


import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import shop.domain.ImageVO;
import shop.domain.ProductVO;
import shop.domain.Product_DetailVO;
import shop.model.js_5_ProductDAO;
import shop.model.js_5_ProductDAO_imple;

public class ItemAdminOneDetail extends AbstractController {
	
	private js_5_ProductDAO pdao = null;
	
	public ItemAdminOneDetail() {
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
				
				// 클릭한 상품번호에대한 상품테이블 정보
				ProductVO pvo = pdao.selectOneProductInfo(pdno);
				
				request.setAttribute("pvo", pvo);
				
				// 클릭한 상품번호에대한 색상별 재고
				List<Product_DetailVO> pdlist = pdao.selectOnePDetail(pdno);
				
				request.setAttribute("pdlist", pdlist);
				
				// 클릭한 상품번호에대한 추가이미지파일
				List<ImageVO> imglist = pdao.extraimgfilename(pdno);
				
				request.setAttribute("imglist", imglist);
				
				super.setRedirect(false);
				super.setViewPage("/WEB-INF/item/admin/itemAdminOneDetail.jsp");
				
				
			} // end of post 
			
			
		}

	}

}
