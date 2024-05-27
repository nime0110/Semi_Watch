package shop.controller;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import shop.model.js_5_ProductDAO;
import shop.model.js_5_ProductDAO_imple;

public class ItemAdminDelete extends AbstractController {
	
	private js_5_ProductDAO pdao = null;
	
	public ItemAdminDelete() {
		pdao = new js_5_ProductDAO_imple();
	}
	

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		if(loginuser != null && "admin".equals(loginuser.getUserid() ) ) {
			
			String pdno = (String)request.getParameter("pdno");
			
			
			List<String> imglist = null;
			
			try {
				
				imglist = pdao.select_imgfilename(pdno);
				
				System.out.println("상품 이미지 파일명 가져오기 성공");
				
			}catch(SQLException e) {
				
				System.out.println("상품 이미지 파일명 가져오기 실패");
				
				return;
				
			}	
				
			int n = pdao.delete_product(pdno); 
			
			if(n == 1) {
				
				ServletContext svlCtx = session.getServletContext();
	            String uploadFileDir = svlCtx.getRealPath("/images/product");
				
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
				
				
				JSONObject jsonObj = new JSONObject(); // { }
                
                jsonObj.put("result", n);
                
                
                String json = jsonObj.toString(); // 문자열로 변환 
                request.setAttribute("json", json);
                
                super.setRedirect(false);
                super.setViewPage("/WEB-INF/jsonview.jsp"); 
				
				
			} // end of if db에서 데이터 삭제성공시
				
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
