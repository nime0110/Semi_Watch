package order.controller;

import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import order.model.js_5_OrderDAO;
import order.model.js_5_OrderDAO_imple;

public class AdminOrderDetail extends AbstractController {
	
	private js_5_OrderDAO odao = null;
	
	public AdminOrderDetail() {
		odao = new js_5_OrderDAO_imple();
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
		
		String userid = loginuser.getUserid();
		
		if(!"admin".equals(userid)){
			
			request.setAttribute("message", "관리자만 접근 가능합니다!!");
			request.setAttribute("loc", "javascript:history.back()"); 
         
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
			
		}
		
		String odrcode = request.getParameter("odrcode");
		
		// 주문코드에 해당하는 구매자정보 조회
		Map<String,String> admin_odrinfo = odao.adminGetOrderInfo(odrcode);
		
		request.setAttribute("admin_odrinfo", admin_odrinfo);
		
		// 주문코드에 해당하는 상품정보 조회
		List<Map<String,String>> admin_odrdetail_list = odao.adminGetOrderDetail(odrcode);
		
		request.setAttribute("admin_odrdetail_list", admin_odrdetail_list);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/order/admin/adminOrderDetail.jsp");

	} // end of execute

}
