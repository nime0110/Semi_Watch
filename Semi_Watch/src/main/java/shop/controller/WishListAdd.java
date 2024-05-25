package shop.controller;

import java.sql.SQLException;
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
import shop.domain.Product_DetailVO;
import shop.model.ss_2_ProductDAO;
import shop.model.ss_2_ProductDAO_imple;

public class WishListAdd extends AbstractController {
	private ss_2_ProductDAO pdao = null;
	
	public WishListAdd() {
		pdao = new ss_2_ProductDAO_imple();
	}
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		/* ---------- 위시리스트 관련 코드 ------------*/
		
		String pdnos = request.getParameter("pdnos"); 
		  String selectedColors = request.getParameter("selectedColors"); // 색상 정보를 받음
		
		System.out.println("selectedColors:" + selectedColors);
		//System.out.println(pdnames);
		String[] no_arr = pdnos.split(",");
		 String[] color_arr = selectedColors.split(",");
		//각 문자열에 작은따옴표 추가
		for (int i = 0; i < no_arr.length; i++) {
			no_arr[i] = no_arr[i].trim();
		}
		
        String pdno = String.join(", ", no_arr);
        
        System.out.println("pdno:" + pdno); // PR-H1000-9DR GPR-H1000-9DR GA-2100-2A2DR
     // 'PR-H1000-9DR', 'GPR-H1000-9DR' , 'GA-2100-2A2DR'
        
        // 화면에서 찜하기를 눌렀을 때 해당하는 상품의 정보를 VO에 담아서 반환하는 메소드
        List<ProductVO> wishList = pdao.getWishListItem(pdno);
        
        //들어온 컬러 코드와 제품번호로 제품상세번호 가져오는 메소드 
        List<Product_DetailVO> wishDetailList = pdao.getWishDetailByPnum(pdno, selectedColors);
        
        System.out.println("1.헤헤헤헤~~~~~~~ pdno : " + pdno);
        // 1.헤헤헤헤~~~~~~~ pdno : 99
        
        JSONArray jsonArr = new JSONArray();// []
        if(wishList.size() > 0) {
            for (int i = 0; i < wishList.size(); i++) {
                ProductVO pvo = wishList.get(i);
                String color = "색상 미선택";
                
                for (String colorInfo : color_arr) {
                    String[] parts = colorInfo.split(":");
                    if (parts[0].equals(pvo.getPdno())) {
                        color = parts[1];
                        break;
                    }
                }

                JSONObject jsonObj = new JSONObject(); 
                jsonObj.put("pdname", pvo.getPdname());
                jsonObj.put("pdimg", pvo.getPdimg1());
                jsonObj.put("pdsaleprice", pvo.getSaleprice());
                jsonObj.put("pdno", pvo.getPdno());
                jsonObj.put("color", color);
                
                jsonArr.put(jsonObj); 
            }
        }//end of  if(wishList.size() > 0) -----------------------------
        
        String json = jsonArr.toString(); //문자열로 변환
        // 데이터가 없을시 "[]"로 된다.
        
        // [{"pdname":"새우깡", "color":"빨강"}], [{"pdname":"감자깡", "color":"주황"}]
        //

        request.setAttribute("json", json);
        System.out.println("json:" + json);
        
        super.setViewPage("/WEB-INF/jsonview.jsp");
        
        
        /* ---------- 위시리스트 관련 코드 ------------*/

        
	}


}
