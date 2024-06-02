package member.controller;

import java.io.File;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import member.domain.MemberVO;
import member.model.jh_3_MemberDAO;
import member.model.jh_3_MemberDAO_imple;

public class MemberInfoChangeEnd extends AbstractController {
	
	private jh_3_MemberDAO mdao = null;
	
	public MemberInfoChangeEnd() {
		mdao = new jh_3_MemberDAO_imple();
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
		String method = request.getMethod();
		System.out.println("MemberInfoChangeEnd 클래스 작동!");
		
		String message = "";
		String loc = "";
		
		if("POST".equalsIgnoreCase(method)) {	// post 방식일 경우에만 실행
			
			String infoUpdate = request.getParameter("infoUpdate");
			String userid = request.getParameter("userid");
			
			HttpSession session = request.getSession();
			MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
			
			
			Map<String, String> paraMap = new HashMap<>();
			paraMap.put("userid", userid);
			
			
			
			try {
				int result = 0;
				
				
				if("img".equals(infoUpdate)) {
					
					System.out.println("사진변경 컨트롤러 옴");
					// 유저 이미지 관련 내용
					// 1. 첨부되어진 파일을 디스크의 어느 경로에 업로드 할 것인지 경로를 설정
					ServletContext svlCtx = session.getServletContext();
		            String uploadFileDir = svlCtx.getRealPath("/images");
		            
		            String userimg = null;
		            
		            
		            // 추가이미지 파일의 개수 이거 필요없음 파일은 하나만 가능해야함
		            // String attachCount = request.getParameter("attachCount");
		            
		            Collection<Part> parts = request.getParts(); // import ==> jakarta.servlet.http.Part;
		            // getParts()를 사용하여 form 태그로 부터 넘어온 데이터들을 각각의 Part로 하나하나씩 받는다.
					
		            for(Part part : parts) {
					
		            	if(part.getHeader("Content-Disposition").contains("filename=")) {
		            		// form 태그에서 전송되어온 것이 파일일 경우
		            		
		            		String fileName = extractFileName(part.getHeader("Content-Disposition"));
		            		
		            		String newFilename = fileName.substring(0, fileName.lastIndexOf(".")); // 확장자를 뺀 파일명 알아오기 ==> 0부터 .png 가 되기전까지
		            		
		            		newFilename += "_"+String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", Calendar.getInstance());
		            	
		            		newFilename += System.nanoTime();
		            		
		            		newFilename += fileName.substring(fileName.lastIndexOf("."));
		            		// 확장자 붙이기 ==> .부터 뒤를 붙인다는 뜻
		            		
		            		// 파일을 지정된 디스크 경로에 저장해준다. 이것이 바로 파일을 업로드 해주는 작업
		            		part.write(uploadFileDir + File.separator + newFilename);
		            		
		            		part.delete(); // 임시저장된 파일 데이터를 제거
		            	
		            		if("userimg".equals(part.getName())) {
		            			userimg = newFilename;
                            }
		            	
		            	
		            	
		            	}
		            	else { // form 태그에서 전송되어온 것이 파일이 아닐 경우 (프로젝트때는 하지말래)
	                        String formValue = request.getParameter(part.getName());
	                        System.out.printf("파일이 아닌 경우 파라미터(name)명 : %s, value : %s \n"
	                                        , part.getName(), formValue);
	                    }
		            	
		            }// end of for(Part part : parts) {----
		            
		            System.out.println("사진변경 파일명 확인 : "+userimg);
		            
		            // userimg 값 넣기
		            paraMap.put("userimg", userimg);
		            
		            // 업데이트
		            result = mdao.updateIMG(paraMap);
		            		            
		            JSONObject jsonObj = new JSONObject();
	                
	                jsonObj.put("result", result);
	                
	                String json = jsonObj.toString(); // 문자열로 변환 
	                request.setAttribute("json", json);
	                
	                super.setRedirect(false);
	                super.setViewPage("/WEB-INF/jsonview.jsp");

		            
				}
				
				
				if("pwd".equals(infoUpdate)){	// 비밀번호 변경일 경우에만
					String newPassword = request.getParameter("newPassword");
					System.out.println("비밀번호 변경 에 들어옴");
					
					if(newPassword == null) {
						System.out.println("비밀번호 값 null 임");
					}
					System.out.println(newPassword);
					
					paraMap.put("newPassword", newPassword);
					
					result = mdao.updatePWD(paraMap);
					
					loginuser.setPw(newPassword);
					
					message = "비밀번호 변경이 완료되었습니다.";
					
					
				}
				
				if("email".equals(infoUpdate)){
					String newEmail = request.getParameter("newEmailSave");
					if(newEmail == null) {
						System.out.println("이메일 값 null 임");
					}
					
					paraMap.put("newEmail", newEmail);
					
					result = mdao.updateEmail(paraMap);
					
					loginuser.setEmail(newEmail);
					
					message = "이메일 변경이 완료되었습니다.";
					
				}
				
				if("mobile".equals(infoUpdate)){
					String newMoblie = request.getParameter("newMoblieSave");
					
					if(newMoblie == null) {
						System.out.println("전화번호 값 null 임");
					}
					
					paraMap.put("newMoblie", newMoblie);
					
					result = mdao.updateMobile(paraMap);
					
					loginuser.setMobile(newMoblie);
					
					message = "전화번호 변경이 완료되었습니다.";
					
					
				}
				
				if("post".equals(infoUpdate)){
					String postcode = request.getParameter("pcode");	// 우편번호
					String addr = request.getParameter("addr");	// 도로명
					String extraAddr = request.getParameter("extraAddr");	// 구주소동
					String addressDetail = request.getParameter("addressDetail");	// 상세주소
					
					if(postcode == null) {
						System.out.println("우편번호 값 null 임");
					}
					
					paraMap.put("postcode", postcode);
					paraMap.put("addr", addr);
					paraMap.put("extraAddr", extraAddr);
					paraMap.put("addressDetail", addressDetail);
					
					result = mdao.updatePost(paraMap);
					
					loginuser.setPostcode(postcode);
					loginuser.setAddress(addr);
					loginuser.setExtra_address(extraAddr);
					loginuser.setDetail_address(addressDetail);
					
					message = "주소 변경이 완료되었습니다.";
					
				}
				
				
				if(result==0) {
					message = "변경 실패.";
					loc = request.getContextPath()+"/index.flex";
				}
				else {
					loc = request.getContextPath()+"/member/memberInfoChange.flex"; // 다시페이지로 이동한다.
				}
				
				
			
			
			}catch(SQLException e) {
				message = "SQL구문 에러발생";
				loc = "javascript:history.back()"; // 자바스크립트를 이용한 이전페이지로 이동하는 것. 
				e.printStackTrace();
			}
			
			
			

		}// end of if("POST".equalsIgnoreCase(method))---
		else {	// get 방식이면
			message = "비정상적인 경로로 들어왔습니다.";
			loc = request.getContextPath()+"/index.flex";
		}
		
		
		request.setAttribute("message", message);
		request.setAttribute("loc", loc);
		
		// request.setAttribute("memberEditEnd", true); 필요없을듯
		
		super.setRedirect(false); 
		super.setViewPage("/WEB-INF/msg.jsp");

	}

}
