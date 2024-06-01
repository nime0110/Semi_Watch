package shop.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.ss_2_MemberDAO;
import member.model.ss_2_MemberDAO_imple;
import my.util.MyUtil;
import review.domain.ReviewVO;
import shop.domain.ImageVO;
import shop.domain.ProductVO;
import shop.model.ss_2_ProductDAO;
import shop.model.ss_2_ProductDAO_imple;

public class ItemDetail extends AbstractController {
	
	private ss_2_ProductDAO pdao = null;
	
	public ItemDetail() {
		pdao = new ss_2_ProductDAO_imple();
	}
	private void redirect(HttpServletRequest request) {
		super.setRedirect(true);
		super.setViewPage(request.getContextPath()+"/item/itemList.flex");
	}
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		/*
		 * //사용자 url 쿼리문 조작 막기 String referer = request.getHeader("referer");
		 * 
		 * if(referer == null) { super.setRedirect(true);
		 * super.setViewPage(request.getContextPath()+"/index.flex"); return; }
		 */
		
		String pdno = request.getParameter("pdno"); //제품번호
		//제품번호를 가지고서 해당 제품의 정보 조회
		
		
		//쿼리문을 사용자가 문자열로 입력했을 경우 처리
		try {
			Integer.parseInt(pdno);
		} catch (NumberFormatException e) {
			redirect(request);
			return;
		}
		
		//
		ProductVO pvo = pdao.selectOneProductBypdno(pdno); // 제품 하나를 리턴
		System.out.println("pvo : " + pvo);
		if(pvo == null) {
			redirect(request);
			return;
		}	
		//제품번호를 가지고서 제품의 추가된 이미지 정보 가져오기	
		List<String> imgList = pdao.getImagesByPnum(pdno);
		
		//제품번호를 가지고서 제품의 색상 조회해오기
		List<String> colorList = pdao.getColorsByPnum(pdno);
		
		// ------------------ 리뷰 시작 -----------------------// 

		// 리뷰 페이징 처리 >>>>>>>>>>>>>>>
	    String currentShowPageNo = request.getParameter("currentShowPageNo"); // 자기자신의 페이지 위치(1,2,3,4..) 넘김
	    //마우스를 클릭해야만 넘어온 값이 있고 처음에는 넘어온 값이 없으므로 초기화를 아래에서 해준다.	   
	    if(currentShowPageNo == null) {
		   currentShowPageNo = "1";
		}
	    

	
	    Map<String, String> paraMap = new HashMap<>();

	    paraMap.put("currentShowPageNo", currentShowPageNo); // 현재 내가 보고자 하는 페이지 번호
	    String sizePerPage = "3"; // 화면에서의 페이지당 리뷰 갯수
	    
	    paraMap.put("sizePerPage", sizePerPage);// 한 페이지당 회원의 크기
	    paraMap.put("pdno", pdno);// 제품번호

	    
	    // 페이지 바 만들기 - 페이징 처리를 위한 검색이 있는/없는 리뷰에 대한 총페이지 수를 알아와야 한다.
	    int totalPage = pdao.getTotalPage(paraMap);
	    // System.out.println("totalPage = " + totalPage); 테스트용 총페이지 수!!
	    request.setAttribute("totalPage", totalPage);
	    
		//리뷰 갖고오기 - 상품번호 넣어주면 각각 행 작성자, 별점, 내용, 작성일자랑 |  총 리뷰 갯수도 구해와야함. 그리고 평군별점도 필요함
		List<Map<String, String>> rvMapList = pdao.getReviewsBypnum(paraMap);
		
	      
    
        try {
          if( Integer.parseInt(currentShowPageNo) > totalPage ||
            Integer.parseInt(currentShowPageNo) <= 0 ) {
            currentShowPageNo = "1"; 
            paraMap.put("currentShowPageNo", currentShowPageNo);
          };
        } catch(NumberFormatException e) { //문자열 입력한 경우
          currentShowPageNo = "1"; 
          paraMap.put("currentShowPageNo", currentShowPageNo); // 맵에 들어갔었기 때문에 장난쳐왔다면 맵에 1이 들어가야 한다.
        } 
        
        
	    String pageBar = "";
	    int blockSize = 10; // blockSize 는 블럭(토막)당 보여지는 페이지 번호의 개수이다.
	    int loop = 1;// loop 는 1 부터 증가하여 1개 블럭을 이루는 페이지번호의 개수(지금은 10개)까지만 증가하는 용도이다.
	    int pageNo = ( (Integer.parseInt(currentShowPageNo) - 1)/blockSize ) * blockSize + 1;
	       //pageNo는 페이지 바에서 보여지는 첫번째 번호이다.
	       
	       // ** [맨처음] [이전]만들기 ** //
	    pageBar += "<li class='page-item'><a class='page-link' href='itemDetail.flex?pdno="+pdno+"&sizePerPage="+sizePerPage+"&currentShowPageNo=1'>[맨처음]</a></li>";
	    if(pageNo != 1) {
	      pageBar += "<li class='page-item'><a class='page-link' href='itemDetail.flex?pdno="+pdno+"&sizePerPage="+sizePerPage+"&currentShowPageNo="+(pageNo-1)+"'>[이전]</a></li>";         
	    }
	       
	    while( !(loop > blockSize || pageNo > totalPage) ) {
	         if(pageNo == Integer.parseInt(currentShowPageNo)) {           
	           pageBar += "<li class='page-item active'><a class='page-link' href='#'>"+pageNo+"</a></li>";
	         }
	         else {
	           pageBar += "<li class='page-item'><a class='page-link' href='itemDetail.flex?pdno="+pdno+"&sizePerPage="+sizePerPage+"&currentShowPageNo="+pageNo+"'>"+pageNo+"</a></li>";
	         }
	         loop++;   // 1 2 3 4 5 6 7 8 9 10 
	         pageNo++; // 1 2 3 4 5 6 7 8 9 10
	                   // 만약 currentShowPageNo가 13 이라면 pageNo는 공식에 의해서 11, 11 부터 10번 반복이기 때문에
	                   // 11 12 13 14 15 16 17 18 19 20 이 반복되게 된다. 
	                   // 21 22 23 24 25 26 27 28 29 30
	                   // 31 32 33 34 35 36 37 38 39 40
	                   // 41 42 <42 에서 멈춰야 한다!!(중요) 그래서 전체페이지 수를 넘으면 안됨. 
	       }//end of while()------------------------------------
	       
	       
	       // ** [다음] [마지막]만들기 ** //
	       // pageNo ==> 11 / 21 / 31 이럴때
	       if(pageNo <= totalPage) {         
	         pageBar += "<li class='page-item'><a class='page-link' href='itemDetail.flex?sizePerPage="+sizePerPage+"&currentShowPageNo="+pageNo+"'>[다음]</a></li>";
	       }
	       pageBar += "<li class='page-item'><a class='page-link' href='itemDetail.flex?pdno="+pdno+"&sizePerPage="+sizePerPage+"&currentShowPageNo="+totalPage+"'>[마지막]</a></li>";

	      
	      
	      
		
		request.setAttribute("pvo", pvo);
		request.setAttribute("imgList", imgList);
		request.setAttribute("colorList", colorList);
		request.setAttribute("rvMapList", rvMapList);
	    request.setAttribute("sizePerPage", sizePerPage);
	    request.setAttribute("pageBar", pageBar);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/item/itemDetail.jsp");
		

	}

}
