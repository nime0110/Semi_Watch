package shop.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import member.domain.MemberVO;
import shop.domain.ProductVO;
import shop.model.js_5_ProductDAO;
import shop.model.js_5_ProductDAO_imple;

public class ItemSetup extends AbstractController {
	
	private js_5_ProductDAO pdao = null;
	   
    public ItemSetup() {
       pdao = new js_5_ProductDAO_imple();
       
    }
    
    String pdno; // get일때 넣어서 post일때 map에 put 해줄용도

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		// == 관리자(admin)로 로그인 했을 때만 제품등록이 가능하도록 한다. == //
		HttpSession session = request.getSession();
		
		MemberVO loginuser = (MemberVO)session.getAttribute("loginuser");
		
		if(loginuser != null && "admin".equals(loginuser.getUserid() ) ) {
			
			String method = request.getMethod();
			
			if(!"POST".equals(method)) {
				
				String setpdno = request.getParameter("setpdno");
				
				pdno = setpdno;
				
				request.setAttribute("setpdno", setpdno);
				
				ProductVO pvo = pdao.select_extrainfo(setpdno);
				
				request.setAttribute("pvo", pvo);
				
			//   super.setRedirect(false);
		         super.setViewPage("/WEB-INF/item/itemSetup.jsp");
				
				
			}
			else {
				Map<String, String> paraMap = new HashMap<>();
				
				// System.out.println("2--"+pdno);
				
				paraMap.put("pdno", pdno);
				
				String[] color = new String[3];
				String[] pqty = new String[3];
				
				List<String> setvalue = new ArrayList<>();
				
				for(int i=0; i<3; i++) {
					
					color[i] = request.getParameter("color"+(i+1));
					pqty[i] = request.getParameter("pqty"+(i+1));
					
					// System.out.println(color[i]);
					// System.out.println(pqty[i]);
					
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
				
				if(!setvalue.get(0).isEmpty() && !setvalue.get(1).isEmpty()) {
					
					color1 = setvalue.get(0);
					pqty1 = setvalue.get(1);
				}
				if(!setvalue.get(2).isEmpty() && !setvalue.get(3).isEmpty()) {
					
					color2 = setvalue.get(2);
					pqty2 = setvalue.get(3);
				}
				if(!setvalue.get(4).isEmpty() && !setvalue.get(5).isEmpty()) {
					
					color3 = setvalue.get(4);
					pqty3 = setvalue.get(5);
				}
				/*
				System.out.println("color1 : " + color1);
				System.out.println("pqty1 : " + pqty1);
				System.out.println("color2 : " + color2);
				System.out.println("pqty2 : " + pqty2);
				System.out.println("color3 : " + color3);
				System.out.println("pqty3 : " + pqty3);
				*/
				
				paraMap.put("color1", color1);
				paraMap.put("pdqty1", pqty1);
				paraMap.put("color2", color2);
				paraMap.put("pdqty2", pqty2);
				paraMap.put("color3", color3);
				paraMap.put("pdqty3", pqty3);
				
				int n = pdao.insert_product_detail(paraMap);
				
				if(n > 0) {
					
					
				   super.setRedirect(true);
			       super.setViewPage(request.getContextPath()+"/item/itemList.flex");
					
				}
				
			}
			
		
		
		}else {
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
