package shop.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.ss_2_MemberDAO;
import member.model.ss_2_MemberDAO_imple;
import shop.domain.ImageVO;
import shop.domain.ProductVO;
import shop.model.ss_2_ProductDAO;
import shop.model.ss_2_ProductDAO_imple;

public class WishListAdd extends AbstractController {
	private ss_2_ProductDAO pdao = null;
	
	public WishListAdd() {
		pdao = new ss_2_ProductDAO_imple();
	}
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String pdnames = request.getParameter("pdnames"); // GPR-H1000-9DR,GPR-H1000-9DR,GA-2100-2A2DR
		//System.out.println(pdnames);
		String[] name_arr = pdnames.split(",");
		
		//각 문자열에 작은따옴표 추가
		for (int i = 0; i < name_arr.length; i++) {
			name_arr[i] = "'" + name_arr[i].trim() + "'";
		}
		
        String pdname = String.join(", ", name_arr);
        System.out.println(pdname); // PR-H1000-9DR GPR-H1000-9DR GA-2100-2A2DR
     // 'PR-H1000-9DR', 'GPR-H1000-9DR' , 'GA-2100-2A2DR'
        
        // 화면에서 찜하기를 눌렀을 때 해당하는 상품의 정보를 VO에 담아서 반환하는 메소드
        List<ProductVO> wishList = pdao.getWishListItem(pdname);
        
        /*
        cnum code   cname
         1  100000 전자제품
         2  200000 의류
         3  300000 도서
       */
        
        JSONArray jsonArr = new JSONArray();// []
        if(wishList.size() > 0) {
        	//pdname, pdimg1, price
          //db에서 조회해온 결과물이 있을 경우
          for(ProductVO pvo : wishList) {
            JSONObject jsonObj = new JSONObject(); 
            jsonObj.put("pdname", pvo.getPdname());// 
            jsonObj.put("pdimg", pvo.getPdimg1());// {"cnum":1, "code":100000}
            jsonObj.put("pdprice", pvo.getPrice());// {"cnum":1, "code":100000,"cname":전자제품} 이런식으로
            
            jsonArr.put(jsonObj);
                      
            
          }//end of for(ProductVO pvo : wishList)--------
        }//end of  if(wishList.size() > 0) -----------------------------
        
        String json = jsonArr.toString(); //문자열로 변환
        // 데이터가 없을시 "[]"로 된다.
        
        
        request.setAttribute("json", json);
        
        super.setViewPage("/WEB-INF/jsonview.jsp");
        
        
        
        
        
	}

}
