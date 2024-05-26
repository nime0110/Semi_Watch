package shop.controller;



import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
			
		// == 관리자(admin)로 로그인 했을 때만 제품등록이 가능하도록 한다. == //
		HttpSession session = request.getSession();
		
		super.goBackURL(request); // 세션에 현재 url을 저장해둔다.
		
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		if(loginuser != null && "admin".equals(loginuser.getUserid() ) ) {
			
			
			
			String searchType = request.getParameter("searchType");
			// 검색할 구분 즉, DB에서 컬럼이 해당된다 
			String searchWord = request.getParameter("searchWord");
			// 검색할 단어 즉, DB에서 like 혹은 where 
			
			String sizePerPage = request.getParameter("sizePerPage");
			// 폼태그에서 읽어온 한페이지당 보여줄 행의 갯수
			
			String currentShowPageNo = request.getParameter("currentShowPageNo");
			
			
			
			if(searchType == null || ( !"pdno".equals(searchType) &&
			   !"brand".equals(searchType) && !"pdname".equals(searchType) ) ) {
				// searchType이 get방식일때 select태그에 있는값이 아닐때를 막아주기 위한 조건문  && 과 || 무슨차이임?  
				
				searchType = "";
				
			}
			if(searchWord == null || 
			   ( searchWord != null && searchWord.trim().isEmpty() ) ) {
				// .isBlank ==> 내용이 없거나, 공백도 포함 == .trim().isEmpty()
				
				searchWord = "";
				
			}
			
			Map<String, String> paraMap = new HashMap<>();
			
			paraMap.put("searchType", searchType);
			paraMap.put("searchWord", searchWord.toUpperCase());
			
			if(sizePerPage == null || 
			  (!"10".equals(sizePerPage) && 
			   !"5".equals(sizePerPage) && 
			   !"3".equals(sizePerPage) ) ) {
				// submit 은 검색을 눌러야만 전송을해주기때문에 기본값은 null 이 나온다, 그러므로 null일경우 기본값을 10으로 세팅해준다
				sizePerPage = "10";
				
			}
					
			if(currentShowPageNo == null) {
				// 맨처음에는 첫번째 페이지를 보여줘야하므로
				currentShowPageNo = "1";
				
			}
			
			
			paraMap.put("currentShowPageNo", currentShowPageNo); // 조회할 페이지 번호
			
			paraMap.put("sizePerPage", sizePerPage); // 한페이지당 보여줄 행 갯수
			
			
			int totalPage = pdao.get_admin_ProductTotalPage(paraMap);
			
			// System.out.println("~~~~ 확인용 totalPage => " +totalPage); // 10일때 21
			
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
			
			int blockSize = 10;
			// blockSize 는 블럭(토막)당 보여지는 페이지 번호의 개수이다. 1 2 3 4 .. 10
			
			int loop = 1;
			// loop 는 1 부터 증가하여 1개 블럭을 이루는 페이지번호의 개수(지금은 10개)까지만 증가하는 용도이다. 
			
			int pageNo = ( (Integer.parseInt(currentShowPageNo) - 1) / blockSize ) * blockSize + 1 ;

			// *** [맨처음] [이전] 만들기 *** //
			
			// 맨처음 버튼 구성하기
			pageBar += "<li class='page-item'><a class='page-link' href='itemUpdateList.flex?searchType="
					+ searchType + "&searchWord=" + searchWord + "&sizePerPage=" + sizePerPage + 
					"&currentShowPageNo=1'>[맨처음]</a></li>";
			
			if(pageNo != 1) { // 가장 처음부분이 1이 아니라면 [이전] 페이지 버튼을 보여준다.
				
				// [이전] 구성하기
				pageBar += "<li class='page-item'><a class='page-link' href='itemUpdateList.flex?searchType="
						+ searchType + "&searchWord=" + searchWord + "&sizePerPage=" + sizePerPage + 
						"&currentShowPageNo=" + (pageNo - 1) + "'>[이전]</a></li>";
				
			}
			
				
			while( !( loop > blockSize || pageNo > totalPage ) ) {  
				// loop가 11이되면 탈출시켜주려하면서 , paegno 즉, 보여지는 페이지가 전체페이지갯수를 넘어가면 안되기때문에 탈출조건 추가
				
				
				if(pageNo == Integer.parseInt(currentShowPageNo) ) {
					// 해당페이지번호가 현재 보고자하는 페이지와 같다면
					
					pageBar += "<li class='page-item active'><a class='page-link' href='#'>" + pageNo + "</a></li>";
					// 
					
				}
				else {
					
					pageBar += "<li class='page-item'><a class='page-link' href='itemUpdateList.flex?searchType="
							+ searchType + "&searchWord=" + searchWord + "&sizePerPage=" + sizePerPage + 
							"&currentShowPageNo=" + pageNo + "'>" + pageNo + "</a></li>";
					//  1 + 2 + ... 10 , 11 + 12 ... 20
					
				}
				
				loop++; 
				
				pageNo++; 
				
			} // end of 
			
			
			// *** [다음] [마지막] 만들기 *** //
			
			if( pageNo <= totalPage ) {
				// 
				// 즉, pageNo가 마지막페이지이거나 마지막페이지보다 크게되면 [다음]버튼을 보여주지 않는다!!!
				
				// [다음] 구성하기
				pageBar += "<li class='page-item'><a class='page-link' href='itemUpdateList.flex?searchType="
						+ searchType + "&searchWord=" + searchWord + "&sizePerPage=" + sizePerPage + 
						"&currentShowPageNo=" + pageNo + "'>[다음]</a></li>";
				// pageNo가 되는이유는 while문을 다돌고 나오면 PageNO 는 11 , 21 이런식으로 된다!!!
				
			}
			
			
			// [마지막] 구성하기
			pageBar += "<li class='page-item'><a class='page-link' href='itemUpdateList.flex?searchType="
					+ searchType + "&searchWord=" + searchWord + "&sizePerPage=" + sizePerPage + 
					"&currentShowPageNo=" + totalPage + "'>[마지막]</a></li>";
			
			
			// *** ======== 페이지바 만들기 끝 ======= *** //
			
			
			// *** ====== 현재 페이지를 돌아갈 페이지(goBackURL)로 주소 지정하기 ======= *** //
			
			String currentURL = MyUtil.getCurrentURL(request);
			// 회원조회를 했을시 현재 그 페이지로 그대로 되돌아가길 위한 용도로 쓰임. 즉, 캐시를 이용한(데이터반영안된) 뒤로가기말고 반영된 이전페이지로의 이동
			
			// System.out.println("currentURL => " + currentURL);
			// currentURL => /member/memberList.up?searchType=name&searchWord=%EC%9C%A0&sizePerPage=5&currentShowPageNo=15
			
			
			List<ProductVO> productList = pdao.select_admin_product_pagin(paraMap);
			
			request.setAttribute("productList", productList);
			
			if(searchType != null || ( "brand".equalsIgnoreCase(searchType) &&
			   "pdname".equalsIgnoreCase(searchType) && "pdno".equals(searchType) ) ) {
				
				// searchType이 get방식일때 select태그에 있는값이 아닐때를 막아주기 위한 조건문  && 과 || 무슨차이임?  
				
				request.setAttribute("searchType", searchType);
						
			}
			if(searchWord == null || 
			    !searchWord.trim().isEmpty() ) {
				// .isBlank ==> 내용이 없거나, 공백도 포함 == .trim().isEmpty()
				
				request.setAttribute("searchWord", searchWord);
				
			}
			
			request.setAttribute("sizePerPage", sizePerPage);
			
			request.setAttribute("pageBar", pageBar); // 페이지바 구해서 넘겨주기
			
			request.setAttribute("currentURL", currentURL); 
			
			
			int total_admin_ProductCount = pdao.get_admin_TotalProductCount(paraMap);
			
			// System.out.println("~~~ 확인용 : " + totalMemberCount);
			
			if(total_admin_ProductCount > 0) {
				
				request.setAttribute("total_admin_ProductCount", total_admin_ProductCount);
				
			}
			
			request.setAttribute("currentShowPageNo", currentShowPageNo);
					
			
			
		
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/item/admin/itemUpdateList.jsp");
		
		}else {
	         // 로그인을 안한 경우 또는 일반사용자로 로그인 한 경우 
	         String message = "관리자만 접근이 가능합니다.";
	         String loc = request.getContextPath()+"/index.flex";
	         
	         request.setAttribute("message", message);
	         request.setAttribute("loc", loc);
	         
	      //   super.setRedirect(false);
	         super.setViewPage("/WEB-INF/msg.jsp");
	         
	    }
		
		
		
		
		
	} // end of execute

}
