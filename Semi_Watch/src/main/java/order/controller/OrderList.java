package order.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;

import member.model.sw_4_MemberDAO_imple;
import my.util.MyUtil;
import order.domain.OrderVO;
import order.model.sw_4_OrderDAO;
import order.model.sw_4_OrderDAO_imple;

public class OrderList extends AbstractController {

	private static final String String = null;
	private sw_4_OrderDAO odao = null;
	
	public OrderList() {
		odao = new sw_4_OrderDAO_imple();
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// 로그인 또는 로그아웃을 하면 시작페이지로 가는 것이 아니라 방금 보았던 그 페이지로 그대로 가기 위한 것임.
		super.goBackURL(request);
		
	//	if(super.checkLogin(request)) {
		
		 // == 내 아이디로 로그인 했을 때만 회원조회가 가능하도록 해야 한다. == //
		HttpSession session = request.getSession();
		MemberVO loginuser = (MemberVO) session.getAttribute("loginuser"); 
			
		String userid=loginuser.getUserid();
		//	System.out.println("loginuserid : " + userid);

		// 메소드에 넣기
		List<OrderVO> orderList = odao.orderUserId(userid); // 로그인된 사람의 아이디가 들어옴.
	//	request.setAttribute("orderList", orderList);
		
		//	orderList = odao.orderUserId(fk_userid);
		//}
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/order/orderList.jsp");
	
		}
		
	}
	
	

	