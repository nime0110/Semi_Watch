package shop.controller;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import member.domain.MemberVO;
import shop.model.js_5_ProductDAO;
import shop.model.js_5_ProductDAO_imple;

public class ItemAdminUpdate extends AbstractController {
	
	private js_5_ProductDAO pdao = null;
	
	public ItemAdminUpdate() {
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
			
			if("POST".equalsIgnoreCase(method)) { // "POST" 이라면
				
				// ==== formdata .ajax 로 전송되어진 이후 작업!!! ==== //
				
				// 1. 첨부되어진 파일을 디스크의 어느 경로에 업로드 할 것인지 그 경로를 설정해야 한다. 
	            ServletContext svlCtx = session.getServletContext();
	            String uploadFileDir = svlCtx.getRealPath("/images/product"); // ********* (운영경로)/images 로 저장경로가 설정되어있다.
	            
	            // ==== >>> 파일을 업로드 해준다. <<< ==== //
	            
	            String pdimg1 = null;
	            String pd_contentimg = null;
	            
                String attachCount = request.getParameter("attachCount");
				
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
                            if("pd_contentimg".equals(part.getName())) {
                            	pd_contentimg = newFilename;
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
                
             
				String[] color = new String[3];
				String[] pqty = new String[3];
				
				List<String> setvalue = new ArrayList<>();
				
				for(int i=0; i<3; i++) {
					
					if( request.getParameter("color"+(i+1)) == null) {
						break;
					}
					
					color[i] = request.getParameter("color"+(i+1));
					pqty[i] = request.getParameter("pqty"+(i+1));
					
					 System.out.println(color[i]);
					 System.out.println(pqty[i]);
					
					if((color[i] != null && !"".equals(color[i]) ) && (pqty[i] != null && !"".equals(pqty[i]) ) ){
						
						setvalue.add(color[i]);
						setvalue.add(pqty[i]);
					}
					
				} // end of for
				
				String color1 = null;
				String pqty1 = null;
				
				String color2 = null;
				String pqty2 = null;
				
				String color3 = null;
				String pqty3 = null;
				
				int setvalue_size = setvalue.size();
				
				if( setvalue_size <= 6 && !setvalue.get(0).isBlank() && !setvalue.get(1).isBlank()) {
					
					color1 = setvalue.get(0);
					pqty1 = setvalue.get(1);
				}
				
				if( setvalue_size >= 4 && setvalue_size <= 6 && !setvalue.get(2).isBlank() && !setvalue.get(3).isBlank()) {
					
					color2 = setvalue.get(2);
					pqty2 = setvalue.get(3);
				}
				
				if( setvalue_size == 6 && !setvalue.get(4).isBlank() && !setvalue.get(5).isBlank()) {
					
					color3 = setvalue.get(4);
					pqty3 = setvalue.get(5);
				}
				
				System.out.println("color1 : " + color1);
				System.out.println("pqty1 : " + pqty1);
				System.out.println("color2 : " + color2);
				System.out.println("pqty2 : " + pqty2);
				System.out.println("color3 : " + color3);
				System.out.println("pqty3 : " + pqty3);
				
				// update, delete ,insert 할거 받아오기
                
                String pdno = request.getParameter("pdno");			  // 상품번호
                String pdname = request.getParameter("pdname");       // 상품명
                String brand = request.getParameter("brand");  		  // 브랜드
                String price = request.getParameter("price");         // 상품 정가
                String saleprice = request.getParameter("saleprice"); // 성품 판매가(할인해서 팔 것이므로)
                String pd_content = request.getParameter("pdcontent");   // 제품설명
                String pdstatus = request.getParameter("pdstatus");
                
             // !!!! 크로스 사이트 스크립트 공격에 대응하는 안전한 코드(시큐어코드) 작성하기 !!!! //  
                String point = request.getParameter("point");         // 포인트 점수
                
                Map<String, String> paraMap = new HashMap<>();
				
				// System.out.println("2--"+pdno);
				
				paraMap.put("pdno", pdno);
				paraMap.put("pdname", pdname);
				paraMap.put("brand", brand);
				paraMap.put("price", price);
				paraMap.put("saleprice", saleprice);
				paraMap.put("pd_content", pd_content);
				paraMap.put("pdstatus", pdstatus);
				paraMap.put("point", point);
				paraMap.put("pdimg1", pdimg1);
				paraMap.put("pd_contentimg", pd_contentimg);
				
				paraMap.put("color1", color1);
				paraMap.put("pdqty1", pqty1);
				paraMap.put("color2", color2);
				paraMap.put("pdqty2", pqty2);
				paraMap.put("color3", color3);
				paraMap.put("pdqty3", pqty3);
				
				List<String> imglist = null;
				
				String pdno2 = paraMap.get("pdno");
				
				try {
					
					imglist = pdao.select_extraimgfilename(pdno2);
					
					System.out.println("상품 이미지 파일명 가져오기 성공");
					
				}catch(SQLException e) {
					
					System.out.println("상품 이미지 파일명 가져오기 실패");
					
					return;
					
				}
				
				// 상품테이블 업데이트 후 자식테이블 삭제 후 insert ==> 수동커밋!!
				int sum = pdao.delete_after_insert(paraMap);
                
                // System.out.println(sum);
                int result = 0;
                
                if(sum == 1) {
                	
                	result = 1;
                	
                	if(imglist != null && imglist.size() > 0) {
                		
	    				for (String filename : imglist) {
	    					
	    					File imageFile = new File(uploadFileDir, filename);
	    					
	    					if (imageFile.exists()) {
	    						
	    		                boolean deleteResult = imageFile.delete();
	    		                
	    		                if (deleteResult) {
	    		                	
	    		                    System.out.println("이미지 파일 삭제 성공 : " + filename);
	
	    		                } else {
	    		                	
	    		                    System.out.println("이미지 파일 삭제 실패 : " + filename);
	    		                    
	    		                    return;
	    		                }
	    		            } else {
	    		            	
	    		                System.out.println("삭제할 이미지 파일이 없음 : " + filename);
	    		                
	    		                return;
	    		                
	    		            }
	    					
	    				} // end of for
    				
                	} // end of if(imglist != null) { 가져온 이미지파일이 있다면
                	
                } // 다중 sql문 성공여부 확인
                
                // === 추가이미지파일이 있다라면 tbl_product_imagefile 테이블에 제품의 추가이미지 파일명 insert 해주기 === //
                
                if(result ==1 &&n_attachCount > 0) {
                	
                	result = 0;
                	
                	Map<String, String> paraMap2 = new HashMap<>();
                	
                	paraMap2.put("pdno", paraMap.get("pdno"));
                	
                	int cnt = 0; // 추가이미지 파일 insert문이 돌아가는 횟수를 표현하는 용도의 변수
                	
                	for(int i=0; i<n_attachCount; i++) {
                		
                		String attachFileName = arr_attachFileName[i];
                		
                		paraMap2.put("attachFileName", attachFileName);
                		// 키값을 고정적으로 써도되는 이유는 insert문이 for문으로 같이돌기 때문에 반복할때마다 덮어씌운다! 
                		
                		// >>> tbl_product_imagefile 테이블에 제품의 추가이미지 파일명 insert 하기 <<<
                    	int attach_insert_result = pdao.product_imagefile_insert(paraMap2);
                    	
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
		
		
		

	}

}
