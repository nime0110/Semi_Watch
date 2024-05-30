package order.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import order.domain.OrderVO;
import order.model.sw_4_OrderDAO;
import order.model.sw_4_OrderDAO_imple;

public class OrderList extends AbstractController {

	private sw_4_OrderDAO odao = null;
	
	public OrderList() {
		odao = new sw_4_OrderDAO_imple();
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		String referer = request.getHeader("referer");
		// request.getHeader("referer"); 은 이전 페이지의 URL을 가져오는 것이다.
		/*
		if(referer == null) {
			// referer == null 은 웹브라우저 주소창에 URL 을 직접 입력하고 들어온 경우이다. 
				super.setRedirect(true);
				super.setViewPage(request.getContextPath()+"/index.flex");
				return;
		}
		*/
		if(!super.checkLogin(request)) {
			
			request.setAttribute("message" , "주문내역을 조회하려면 로그인 하세요!!");
			request.setAttribute("loc", "javascript:history.back()");
		   
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/msg.jsp");
			return;
		}	
			
		else {
			// 로그인 했을 경우
			
			HttpSession session = request.getSession();
			MemberVO loginuser = (MemberVO) session.getAttribute("loginuser");
			
			// *** 관리자가 아닌 일반사용자로 로그인 했을 경우에는 자신이 주문한 내역만 페이징 처리하여 조회를 해오고, 
	        //     관리자로 로그인을 했을 경우에는 모든 사용자들의 주문내역을 페이징 처리하여 조회해온다.
			
			// 여러 테이블을 조인해야 할때는 Map으로 하는게 가장 편함
			String userid = loginuser.getUserid();
		 // System.out.println("loginuserid : " + userid);
			List<Map<String, String>> order_map_List = odao.getOrderList(userid);
			
		
		
			// 메소드에 넣기
			// List<OrderVO> orderList = odao.orderUserId(userid); // 로그인된 사람의 아이디가 들어옴.
		    // List<Map<String,String>> order_map_List = odao.getOrderList("paraMap");
		    // List<OrderVO> orderList = odao.orderUserId("yuseonwoo"); // 로그인된 사람의 아이디가 들어옴.(임의로 내꺼 넣어줌)
		 
		 /*
		   for(OrderVO odrvo: orderList) {
			   System.out.println("~~~ 확인용 odrvo.getOrdercode : " + odrvo.getOrdercode());
			   System.out.println("~~~ 확인용 odrvo.getOrdercode : " + odrvo.getTotal_price());
			   System.out.println("~~~ 확인용 odrvo.getOrdercode : " + odrvo.getTotal_orderdate());
		   }
		*/
		   System.out.println("사이즈"+order_map_List.size());
		   request.setAttribute("order_map_List", order_map_List);
		   
		}
		   super.setRedirect(false);
		   super.setViewPage("/WEB-INF/order/orderList.jsp");
		}
		
	}
	
	

	