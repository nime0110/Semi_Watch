package order.controller;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Calendar;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import my.util.MyUtil;
import order.model.js_5_OrderDAO;
import order.model.js_5_OrderDAO_imple;
import order.model.sw_4_OrderDAO;
import order.model.sw_4_OrderDAO_imple;

public class OrderList extends AbstractController {

	private js_5_OrderDAO odao = null;
	
	public OrderList() {
		odao = new js_5_OrderDAO_imple();
	}
	
	public static Date addMonth(Date date, int months) {
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, months);
		
		return cal.getTime();
		
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
			
			String userid = loginuser.getUserid();

			String sizePerPage = request.getParameter("sizePerPage");
			
			if(sizePerPage == null || 
			  !"8".equals(sizePerPage)  ) {
				
				sizePerPage = "8";
				// 한페이지당 보여줄 개수
				
			}
			
			String currentShowPageNo = request.getParameter("currentShowPageNo");
			
			if(currentShowPageNo == null) {
				// 맨처음에는 첫번째 페이지를 보여줘야하므로
				currentShowPageNo = "1";
				
			}
			
			String startDate = request.getParameter("startDate");
			String endDate = request.getParameter("endDate");
			
			Map<String, String> paraMap = new HashMap<>();

			paraMap.put("startDate", startDate);
			paraMap.put("endDate", endDate);
			
			if (startDate == null || startDate.trim().isEmpty() || endDate == null || endDate.trim().isEmpty()) {
				
				//현재 날짜를 가져와서 "yyyy-MM-dd" 형식으로 변환
				Date today = new Date();
				
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				
				Date before3Mon = addMonth(today,-3);
				
				String currentDate = sdf.format(today);
				String monDate3 = sdf.format(before3Mon);
				
				paraMap.put("startDate", monDate3);
				paraMap.put("endDate", currentDate);
				
			}
			
			
			
			paraMap.put("currentShowPageNo", currentShowPageNo); 
			
			paraMap.put("sizePerPage", sizePerPage);
			
			paraMap.put("userid", userid);
			
			
			int totalPage = 0;
			
			
			if(!"admin".equals(userid)) {
				
				totalPage = odao.userGetTotalPage(paraMap);
				
			}
			if("admin".equals(userid)) {
				
				totalPage = odao.adminGetTotalPage(paraMap);
				
			}
			try {
				
				if(Integer.parseInt(currentShowPageNo) > totalPage ||
				  Integer.parseInt(currentShowPageNo) <= 0 ) {
					// get방식으로 웹주소창에 보려는 페이지의 값이 토탈페이지를넘어가거나(말도안되게 큰값) 보려는 페이지값이 0이하일때 를 막아주는 if문
					
					currentShowPageNo = "1";
					paraMap.put("currentShowPageNo", currentShowPageNo);
					
				}
				
			}catch(NumberFormatException e) {
				// 숫자형태가아니고 문자형태일때를 막아주기위한 catch문
				
				currentShowPageNo = "1";
				
				paraMap.remove("currentShowPageNo");
				paraMap.put("currentShowPageNo", currentShowPageNo);
				
			}
			String pageBar = "";
			
			int blockSize = 5;
			// blockSize 는 블럭(토막)당 보여지는 페이지 번호의 개수이다. 1 2 3 4 5
			
			int loop = 1;

			int pageNo = ( (Integer.parseInt(currentShowPageNo) - 1) / blockSize ) * blockSize + 1 ;
			// pageNo 는 페이지바에서 보여지는 첫번째 번호 즉, 보여지는 페이지의 시작값
			// 1~10 페이지
			
			
			// *** [맨처음] [이전] 만들기 *** //
			
			// 맨처음 버튼 구성하기
			pageBar += "<li class='page-item'><a href='"+ request.getContextPath() +"/order/orderList.flex?sizePerPage=" + sizePerPage + 
					"&currentShowPageNo=1&startDate=" + startDate + "&endDate=" + endDate +"'>처음</a></li>";
			
			if(pageNo != 1) { // 가장 처음부분이 1이 아니라면 [이전] 페이지 버튼을 보여준다.
				
				// [이전] 구성하기
				pageBar += "<li class='page-item'><a href='"+ request.getContextPath() +"/order/orderList.flex?sizePerPage=" + sizePerPage + 
						"&currentShowPageNo=" + (pageNo - 1) + "&startDate=" + startDate + "&endDate=" + endDate + "'><<</a></li>";
				
			}
			
				
			while( !( loop > blockSize || pageNo > totalPage ) ) {  
				// loop가 11이되면 탈출시켜주려하면서 , paegno 즉, 보여지는 페이지가 전체페이지갯수를 넘어가면 안되기때문에 탈출조건 추가
				
				
				if(pageNo == Integer.parseInt(currentShowPageNo) ) {
					// 해당페이지번호가 현재 보고자하는 페이지와 같다면
					
					pageBar += "<li class='page-item'><a class='active' href='#'>" + pageNo + "</a></li>";
					
					
				}
				else {
					
					pageBar += "<li class='page-item'><a href='"+ request.getContextPath() +"/order/orderList.flex?sizePerPage=" + sizePerPage + 
							"&currentShowPageNo=" + pageNo + "&startDate=" + startDate + "&endDate=" + endDate + "'>" + pageNo + "</a></li>";
					//  1 + 2 + ... 10 , 11 + 12 ... 20
					
				}
				
				loop++; 
				
				pageNo++; 
				
			} // end of while while( !( loop > blockSize || pageNo > totalPage ) ) { }
			
			
			// *** [다음] [마지막] 만들기 *** //
			
			if( pageNo <= totalPage ) {
				// 
				// 즉, pageNo가 마지막페이지이거나 마지막페이지보다 크게되면 [다음]버튼을 보여주지 않는다!!!
				
				// [다음] 구성하기
				pageBar += "<li class='page-item'><a href='"+ request.getContextPath() +"/order/orderList.flex?sizePerPage=" + sizePerPage +
						"&currentShowPageNo=" + pageNo + "&startDate=" + startDate + "&endDate=" + endDate + "'>>></a></li>";
				// pageNo가 되는이유는 while문을 다돌고 나오면 PageNO 는 11 , 21 이런식으로 된다!!!
				
			}
			
			// [마지막] 구성하기
			pageBar += "<li class='page-item'><a href='"+ request.getContextPath() +"/order/orderList.flex?sizePerPage=" + sizePerPage + 
					"&currentShowPageNo=" + totalPage + "&startDate=" + startDate + "&endDate=" + endDate + "'>마지막</a></li>";
			
			
			String currentURL = MyUtil.getCurrentURL(request);
			
			request.setAttribute("sizePerPage", sizePerPage); // 페이지당 보여줄 상품 갯수 (6) 넘겨주기
			
			request.setAttribute("pageBar", pageBar); // 페이지바 구해서 넘겨주기
			
			request.setAttribute("currentURL", currentURL); // 상품목록[검색된결과가 저장되있고 데이터가 반영되는]으로 돌아가기위해서 뷰단에 넘겨준다
			
			request.setAttribute("currentShowPageNo", currentShowPageNo); // 현재 페이지번호 
			
			request.setAttribute("userid", userid);
			
			
			if(!"admin".equals(userid) && super.checkLogin(request)) {
			
				List<Map<String, String>> order_map_List = odao.getOrderList(paraMap);
				
				request.setAttribute("order_map_List", order_map_List);
			
			}
			
			if("admin".equals(userid) && super.checkLogin(request)) {
				
				
				List<Map<String, String>> order_list_admin = odao.getOdrListAdmin(paraMap);
				
				request.setAttribute("order_list_admin", order_list_admin);
				
				request.setAttribute("startDate", paraMap.get("startDate"));
				request.setAttribute("endDate", paraMap.get("endDate"));
			
			}
			
		   super.setRedirect(false);
		   super.setViewPage("/WEB-INF/order/orderList.jsp");
		   
		}// end of else
		  
		   
	} // end of execute
	
	
	
}
	
	

	