package shop.controller;



import java.io.File;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import member.domain.MemberVO;
import shop.domain.ProductVO;
import shop.model.js_5_ProductDAO;
import shop.model.js_5_ProductDAO_imple;


public class ItemRegister extends AbstractController {

	private js_5_ProductDAO pdao = null;
	   
    public ItemRegister() {
       pdao = new js_5_ProductDAO_imple();
    }
    
    private String extractFileName(String partHeader) {
    	
 	   for(String cd : partHeader.split("\\;")) {
 		   
 	       if(cd.trim().startsWith("filename")) {
 	    	   
 	          String fileName = cd.substring(cd.indexOf("=") + 1).trim().replace("\"", "");
 	            
 	          int index = fileName.lastIndexOf(File.separator); // File.separator 란? OS가 Windows 이라면 \ 이고, OS가 Mac, Linux, Unix / 을 말하는 것이다.
 	            
 	          return fileName.substring(index + 1);
 	            
 	       }
 	       
 	   }
 	      return null;
 	}// end of private String extractFileName(String partHeader)-------------------
	
	
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// == 관리자(admin)로 로그인 했을 때만 제품등록이 가능하도록 한다. == //
				HttpSession session = request.getSession();
				
				MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
				
				if(loginuser != null && "admin".equals(loginuser.getUserid() ) ) {
					// 관리자 (admin)로 로그인 했을 경우
					
					String method = request.getMethod();
					
					if(!"POST".equalsIgnoreCase(method)) { // "GET" 이라면
						
						// 일단 카테고리목록을 조회해와서 <select> 태그에 넣어주기
						/*
						List<CategoryVO> categoryList = pdao.selectCategorList();
						
						request.setAttribute("categoryList", categoryList);
						*/
						/*
						List<SpecVO> specList = pdao.selectSpec();
						
						request.setAttribute("specList", specList);
						*/
						
						super.setRedirect(false);
			            super.setViewPage("/WEB-INF/item/itemRegister.jsp");
			            
					} // end of if get방식일때
					else { // "POST" 이라면 
						// ==== formdata .ajax 로 전송되어진 이후 작업!!! ==== //
						
						// 1. 첨부되어진 파일을 디스크의 어느 경로에 업로드 할 것인지 그 경로를 설정해야 한다. 
			            ServletContext svlCtx = session.getServletContext();
			            String uploadFileDir = svlCtx.getRealPath("/images/product/product_thum"); // ********* (운영경로)/images 로 저장경로가 설정되어있다.
			            
			            // ==== >>> 파일을 업로드 해준다. <<< ==== //
			            
			            String pdimg1 = null;
			            
		                String attachCount = request.getParameter("attachCount");
						// attachCount 가 추가이미지 파일의 개수이다. null "1" ~ "10"
					 	// System.out.println("~~~~~~~ attachCount : " + attachCount);
					 	// ~~~~~~~ attachCount : 4
					 	
		                // String pname = request.getParameter("pname");
		                // System.out.println("~~~~~~~ pname : " + pname);
		                // ~~~~~~~ pname : 테스트제품
		                
		                int n_attachCount = 0;
		                
		                if(attachCount != null) {
		                	
		                	n_attachCount = Integer.parseInt(attachCount);
		                	// 넘어온 추가이미지파일 개수 int로
		                }
		                
		                String[] arr_attachFileName = new String[n_attachCount];
		                // 추가이미지 파일명들을 저장시키는 용도의 배열 
		                
		                int idx_attach = 0; // 추가이미지 ++ 시키려는 인덱스용도의 변수
		                
		                Collection<Part> parts = request.getParts(); // import ==> jakarta.servlet.http.Part;
		                // getParts()를 사용하여 form 태그로 부터 넘어온 데이터들을 각각의 Part로 하나하나씩 받는다.
		                // 약간 request.parameter 말고 파일에서 쓸수잇는 방식?( 파일크기, 파일형식?)
		           
		                for(Part part : parts) {
		              	 
		                	if(part.getHeader("Content-Disposition").contains("filename=")) { // form 태그에서 전송되어온 것이 파일일 경우
		                		
		                		String fileName = extractFileName(part.getHeader("Content-Disposition"));
		                		
		                		if(part.getSize() > 0) {
		                			
		                			String newFilename = fileName.substring(0, fileName.lastIndexOf(".")); // 확장자를 뺀 파일명 알아오기 ==> 0부터 .png 가 되기전까지
		                			
		                			newFilename += "_"+String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance());
		                			
		                			newFilename += System.nanoTime();
		                			
		                			newFilename += fileName.substring(fileName.lastIndexOf(".")); // 확장자 붙이기 ==> .부터 뒤를 붙인다는 뜻
		                			
		                			// >>> 파일을 지정된 디스크 경로에 저장해준다. 이것이 바로 파일을 업로드 해주는 작업이다. <<<
		                            part.write(uploadFileDir + File.separator + newFilename);
		                            
		                            part.delete(); // 임시저장된 파일 데이터를 제거
		                            
		                            
		                            if("pdimg1".equals(part.getName())) {
		                            	pdimg1 = newFilename;
		                            }
		                            
		                            else if(part.getName().startsWith("attach") ) { // 추가이미지 파일만큼 반복 (현재 for문 안이니까)
		                                arr_attachFileName[idx_attach++] = newFilename; // 여기서 추가이미지파일명을 배열에 넣어주는것임
		                            }
		                            
		                			
		                		} // end of if
		                	
		                	} // end of if(part.getHeader("Content-Disposition").contains("filename=")) { // form 태그에서 전송되어온 것이 파일일 경우
		                	else { // form 태그에서 전송되어온 것이 파일이 아닐 경우 (프로젝트때는 하지말래)
		                        String formValue = request.getParameter(part.getName());
		                        System.out.printf("파일이 아닌 경우 파라미터(name)명 : %s, value : %s \n"
		                                        , part.getName(), formValue);
		                    }
		                    System.out.println("");
		                    
		                	
		                } // end of for
		                
		             // === 첨부 이미지 파일, 제품설명서 파일을 올렸으니 그 다음으로 제품정보를 (제품명, 정가, 제품수량,...) DB의 tbl_product 테이블에 insert 를 해주어야 한다.  ===
		                
		                String pdname = request.getParameter("pdname");       // 제품명
		                String brand = request.getParameter("brand");  		  // 브랜드
		                String price = request.getParameter("price");         // 제품 정가
		                String saleprice = request.getParameter("saleprice"); // 제품 판매가(할인해서 팔 것이므로)
		                String pd_content = request.getParameter("pdcontent");   // 제품설명
		                
		                
		             // !!!! 크로스 사이트 스크립트 공격에 대응하는 안전한 코드(시큐어코드) 작성하기 !!!! //  
		                String point = request.getParameter("point");         // 포인트 점수
		                
		                
		                // 제품번호 채번해오기
		                int pdno = pdao.getPnumOfProduct();
		                
		                ProductVO pvo = new ProductVO();

		                pvo.setPdno(String.valueOf(pdno));   // 제품번호(Primary Key)
		                pvo.setBrand(brand); // 브랜드
		                pvo.setPdname(pdname); // 제품명
		                pvo.setPrice(Integer.parseInt(price)); // 제품 정가
		                pvo.setSaleprice(Integer.parseInt(saleprice)); // 제품 판매가(할인해서 팔 것이므로)
		                pvo.setPdimg1(pdimg1);   // 제품이미지1 
		                pvo.setPd_content(pd_content); // 제품설명
		                pvo.setPoint(Integer.parseInt(point)); // 포인트
		                
		                // tbl_product 테이블에 제품정보 insert 하기
		                int n = pdao.productinsert(pvo);
		                
		                int result = 0;
		                
		                if(n==1) {
		                	
		                	result = 1;
		                }
		                
		                // === 추가이미지파일이 있다라면 tbl_product_imagefile 테이블에 제품의 추가이미지 파일명 insert 해주기 === //
		                
		                if(n==1 && n_attachCount > 0) {
		                	
		                	result = 0;
		                	
		                	Map<String, String> paraMap = new HashMap<>();
		                	
		                	paraMap.put("pdno", String.valueOf(pdno));
		                	// pnum 은 위에서 채번해온 제품번호이다.
		                	
		                	
		                	int cnt = 0; // 추가이미지 파일 insert문이 돌아가는 횟수를 표현하는 용도의 변수
		                	
		                	for(int i=0; i<n_attachCount; i++) {
		                		
		                		String attachFileName = arr_attachFileName[i];
		                		
		                		paraMap.put("attachFileName", attachFileName);
		                		// 키값을 고정적으로 써도되는 이유는 insert문이 for문으로 같이돌기 때문에 반복할때마다 덮어씌운다! 
		                		
		                		// >>> tbl_product_imagefile 테이블에 제품의 추가이미지 파일명 insert 하기 <<<
		                    	int attach_insert_result = pdao.product_imagefile_insert(paraMap);
		                    	
		                    	if(attach_insert_result == 1) {
		                    		
		                    		cnt++;
		                    		
		                    	} // end of if (추가이미지 파일 insert가 잘되었다면)
		                		
		                    	if(cnt == n_attachCount) {
		                    		
		                    		result = 1;
		                    		break;
		                    	} // end of if  (추가이미지 개수만큼 반복문 성공이 되었다면)
		                    	
		                	} // end of for (int i=0; i<n_attachCount; i++) { 추가이미지 개수만큼 도는 for문
		                	
		                	
		                } // end of if(n==1 && n_attachCount > 0) {
		                
		                JSONObject jsonObj = new JSONObject(); // { }
		                
		                jsonObj.put("result", result);
		                jsonObj.put("setpdno", pdno);
		                
		                String json = jsonObj.toString(); // 문자열로 변환 
		                request.setAttribute("json", json);
		                
		                super.setRedirect(false);
		                super.setViewPage("/WEB-INF/jsonview.jsp"); 
		                
		                // 첨부파일의 파일명(파일서버에 업로드 되어진 실제파일명) 알아오기
		                
					} // end of else (POST일때만 해당되는)
					
				} // end of big-if (로그인한유저가 관리자라면의 if)
				else {
			         // 로그인을 안한 경우 또는 일반사용자로 로그인 한 경우 
			         String message = "관리자만 접근이 가능합니다.";
			         String loc = "javascript:history.back()";
			         
			         request.setAttribute("message", message);
			         request.setAttribute("loc", loc);
			         
			      //   super.setRedirect(false);
			         super.setViewPage("/WEB-INF/msg.jsp");
			    }
			
		
	} // end of 

}
