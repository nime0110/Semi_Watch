package shop.controller;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import my.util.MyUtil;
import shop.domain.ProductVO;
import shop.model.js_5_ProductDAO;
import shop.model.js_5_ProductDAO_imple;


public class ItemList extends AbstractController {

	private js_5_ProductDAO pdao = null;
	
	public ItemList() {
		pdao = new js_5_ProductDAO_imple();
	}
	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// hidden 폼태그에서 보내진 brand, sort 읽어오기
		String brand = request.getParameter("brand");
		String sort = request.getParameter("sort");
		String searchWord = request.getParameter("searchWord");
		
		String colname = "pdno";
		 // System.out.println(brand);
		 // System.out.println(sort);
		
		String sort2 = request.getParameter("sort");
		
		if(sort == null ||  "신상품순".equals(sort) ) {
			// sort 가 읽어온 값이 없거나 신상품순과 일치하다면
			sort = "desc"; // 상품번호 내림차순인데 일단 임시
			
		}
		else if("인기상품순".equals(sort) ) {
			// 아닌경우도 일단 임시로 오름차순
			colname = "pdstatus";
			
			sort = "desc";
		}
		else if("낮은가격순".equals(sort)) {
			
			colname = "saleprice";
			
			sort = "asc";
			
		}else if("높은가격순".equals(sort)) {
			
			colname = "saleprice";
		
			sort = "desc";
			
		}
		

		if(searchWord == null || 
		   ( searchWord != null && searchWord.trim().isEmpty() ) ) {
			// .isBlank ==> 내용이 없거나, 공백도 포함 == .trim().isEmpty()
			
			searchWord = "";
			
		}
		
		String sizePerPage = request.getParameter("sizePerPage");
		// 폼태그에서 읽어온 한페이지당 보여줄 행의 갯수
		
		String currentShowPageNo = request.getParameter("currentShowPageNo");
		// 조회할 페이지 번호
		
		if(sizePerPage == null || 
		  !"6".equals(sizePerPage)  ) {
			
			sizePerPage = "6";
			// 한페이지당 보여줄 상품의 개수
			
		}
				
		if(currentShowPageNo == null) {
			// 맨처음에는 첫번째 페이지를 보여줘야하므로
			currentShowPageNo = "1";
			
		}

		Map<String, String> paraMap = new HashMap<>();
		
		paraMap.put("currentShowPageNo", currentShowPageNo); // 조회할 페이지 번호
		
		paraMap.put("sizePerPage", sizePerPage); // 한페이지당 보여줄 상품의 개수
		
		// 첫페이지 로드시 기본상태에서 전체보기에 class=active 를 부여하기 위한 if
		if(brand == null ||  "전체보기".equals(brand) || (!"G-SHOCK".equals(brand) &&
		   !"세이코".equals(brand) && !"롤렉스".equals(brand) && !"카시오".equals(brand) ) ) {
			
			brand = "전체보기";
			request.setAttribute("brand", brand);
		}
		// System.out.println(brand);
		// System.out.println(sort);
		
		// 위의 if문을 제외한 g-shock, 세이코, 롤렉스, 카시오일때 해주는 setAttribute
		request.setAttribute("brand", brand);
		
		if(sort2 == null || "신상품순".equals(sort2) || (!"인기상품순".equals(sort2) &&
			!"낮은가격순".equals(sort2) && !"높은가격순".equals(sort2) ) ) {
			
			sort2 = "신상품순";
			request.setAttribute("sort", sort2); 
		}
		request.setAttribute("sort", sort2); 
		
		if(brand == null ||  "전체보기".equals(brand) || (!"G-SHOCK".equals(brand) &&
		!"세이코".equals(brand) && !"롤렉스".equals(brand) && !"카시오".equals(brand) ) ) {
			// db에 where brand = ? 로 보내주기전에 목록에 있는값이 아니면 "" 로 넣어주기위한 if 문
			
			brand = "";
			
		}
				
		paraMap.put("brand", brand); // 위의 if문을 제외한 g-shock, 세이코, 롤렉스, 카시오일때 해주는 put		
		paraMap.put("sort", sort); // 임시로 desc , asc만 들어가는중
		paraMap.put("colname", colname);
		paraMap.put("searchWord", searchWord);
		
		int totalPage = 0;
		
		if(searchWord == null || searchWord.trim() == "") {
			
			totalPage = pdao.getTotalPage(paraMap);
			// 특정 조회조건에서 페이지갯수가 몇개인지 읽어오는 메소드
			
		}
		else {
			
			totalPage = pdao.search_brand_TotalPage(paraMap);
			
			
			totalPage += pdao.search_pdname_TotalPage(paraMap);
		}
		
		
		// System.out.println("totalPage : " + totalPage);
		
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
		pageBar += "<li class='page-item'><a class='page-link' href='itemList.flex?brand="
				+ brand + "&sort=" + sort2 + "&sizePerPage=" + sizePerPage + "&searchWord=" + searchWord +
				"&currentShowPageNo=1'>[맨처음]</a></li>";
		
		if(pageNo != 1) { // 가장 처음부분이 1이 아니라면 [이전] 페이지 버튼을 보여준다.
			
			// [이전] 구성하기
			pageBar += "<li class='page-item'><a class='page-link' href='itemList.flex?brand="
					+ brand + "&sort=" + sort2 + "&sizePerPage=" + sizePerPage + "&searchWord=" + searchWord +
					"&currentShowPageNo=" + (pageNo - 1) + "'>[이전]</a></li>";
			
		}
		
			
		while( !( loop > blockSize || pageNo > totalPage ) ) {  
			// loop가 11이되면 탈출시켜주려하면서 , paegno 즉, 보여지는 페이지가 전체페이지갯수를 넘어가면 안되기때문에 탈출조건 추가
			
			
			if(pageNo == Integer.parseInt(currentShowPageNo) ) {
				// 해당페이지번호가 현재 보고자하는 페이지와 같다면
				
				pageBar += "<li class='page-item active'><a class='page-link' href='#'>" + pageNo + "</a></li>";
				
				
			}
			else {
				
				pageBar += "<li class='page-item'><a class='page-link' href='itemList.flex?brand="
						+ brand + "&sort=" + sort2 + "&sizePerPage=" + sizePerPage + "&searchWord=" + searchWord +
						"&currentShowPageNo=" + pageNo + "'>" + pageNo + "</a></li>";
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
			pageBar += "<li class='page-item'><a class='page-link' href='itemList.flex?brand="
					+ brand + "&sort=" + sort2 + "&sizePerPage=" + sizePerPage + "&searchWord=" + searchWord +
					"&currentShowPageNo=" + pageNo + "'>[다음]</a></li>";
			// pageNo가 되는이유는 while문을 다돌고 나오면 PageNO 는 11 , 21 이런식으로 된다!!!
			
		}
		
		
		// [마지막] 구성하기
		pageBar += "<li class='page-item'><a class='page-link' href='itemList.flex?brand="
				+ brand + "&sort=" + sort2 + "&sizePerPage=" + sizePerPage + "&searchWord=" + searchWord +
				"&currentShowPageNo=" + totalPage + "'>[마지막]</a></li>";
		
		// *** ======== 페이지바 만들기 끝 ======= *** //
		
		
		// *** ====== 현재 페이지를 돌아갈 페이지(goBackURL)로 주소 지정하기 ======= *** //
		
		String currentURL = MyUtil.getCurrentURL(request);
	
		
		
		if(searchWord == null || 
		    !searchWord.trim().isEmpty() ) {
			// .isBlank ==> 내용이 없거나, 공백도 포함 == .trim().isEmpty()
			
			request.setAttribute("searchWord", searchWord);
			
		}
		
		request.setAttribute("sizePerPage", sizePerPage); // 페이지당 보여줄 상품 갯수 (6) 넘겨주기
		
		request.setAttribute("pageBar", pageBar); // 페이지바 구해서 넘겨주기
		
		request.setAttribute("currentURL", currentURL); // 상품목록[검색된결과가 저장되있고 데이터가 반영되는]으로 돌아가기위해서 뷰단에 넘겨준다
		
		request.setAttribute("currentShowPageNo", currentShowPageNo); // 현재 페이지번호 
		
	
		List<ProductVO> productList = new ArrayList<>();
		
		if(searchWord.trim() == "" || searchWord == null) {
			
			productList = pdao.select_product_pagin(paraMap);
			// 페이지바와 브랜드 선택까지적용된 상품목록 select해오기
			
		}
		else {
			
			productList = pdao.search_product_pagin_brand(paraMap); // 브랜드명을 조회하는
			
			productList.addAll(pdao.search_product_pagin_pdname(paraMap)); // 상품명을 조회하는
				
			
		}
		
		request.setAttribute("productList", productList);
		
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/item/itemList.jsp");
		
		
	}

}
