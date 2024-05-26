package shop.controller;

import java.io.PrintWriter;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import shop.domain.ProductVO;
import shop.domain.Product_DetailVO;
import shop.model.ss_2_ProductDAO;
import shop.model.ss_2_ProductDAO_imple;

public class AddToCartJSON extends AbstractController {
	private ss_2_ProductDAO pdao = null;
	
	public AddToCartJSON() {
		pdao = new ss_2_ProductDAO_imple();
	}
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
	    response.setContentType("application/json; charset=UTF-8"); //응답하는 컨텐츠 타입 선택 text/html 시 html 코드 작성해서 응답
	    // 클라이언트에 전달할 출력물 작성에 사용됨 - PrintWriter: 출력 스트림, repsonse.getWriter(); : 출력 스트림을 가져온다.
        PrintWriter out = response.getWriter(); //응답 스트림에 텍스트로 기록 - 서블릿으로 들어온 요청 => 텍스트로 응답 즉, 출력스트림
        JSONObject jsonObj = new JSONObject();

        // === 먼저 로그인 유무 검사하기
        if (!super.checkLogin(request)) {
            // 로그인을 하지 않은 상태라면
            jsonObj.put("message", "장바구니에 담으려면 먼저 로그인 부터 하세요!!");
            jsonObj.put("loginRequired", true);

            out.print(jsonObj.toString()); //jsonObj에 담은 걸 문자열로바꿔서 출력한뒤에 
            return; //리턴
        } else {
            // 로그인 한 상태라면
            String cartItems = request.getParameter("cartItems");
            System.out.println("~~~~~~~~~~~~~~~~~~ cartItems:" + cartItems);
           /*
            cartItems:[{"productno":"112","productName":"MW-240B-3BVDF","productPrice":"47,000","productColor":"none","productImage":"/Semi_Watch/images/product/product_thum/22_thum_20240524205324632091007060100.png"},{"productno":"99","productName":"테스트시계99","productPrice":"800,000","productColor":"색상:white","productImage":"/Semi_Watch/images/product/product_thum/40_extra_3_20240523165225531230499283500.png"},{"productno":"95","productName":"진짜테스트시계","productPrice":"70,000","productColor":"색상:pink",
            "productImage":"/Semi_Watch/images/product/product_thum/3_thum_20240523001616471461017989300.png"}] 
            */
            // JSON 문자열을 JSONArray로 변환
            JSONArray jsonArray = new JSONArray(cartItems);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject item = jsonArray.getJSONObject(i);
                String pdno = item.getString("productno");
                String pdName = item.getString("productName");
                String pdPrice = item.getString("productPrice");
                String pdColor = item.getString("productColor");
                String pdImage = item.getString("productImage");
                // 상세번호를 DB에 보내주어야 하므로 들어온 컬러 / 상세번호로 조회 컬러는 없을경우 none으로 넘어옴.
                
                //들어온 컬러 코드와 제품번호로 제품상세번호 가져오는 메소드 
                List<Product_DetailVO> wishDetailList = pdao.getWishDetailByPnum(pdno, pdColor);
                
                String pdDetailNo = "";
                if (!wishDetailList.isEmpty()) {
                    for (Product_DetailVO pdvo : wishDetailList) {
                    	
                        pdDetailNo = pdvo.getPd_detailno();
                    }
                }
                System.out.println("============== pdDetailNo: " + pdDetailNo);
                
                // --- DB에 해당 제품 장바구니에 insert해주는 메소드
                // 장바구니 테이블 칼럼 : cartno, fk_pdno, fk_userid, cart_qty, registerday?? 
                // 세션스코프에 저장된 로그인 유저 불러와서 걔의 유저아이디, 가입일자 가져오고 넣어줌 ( !! 자동로그인 테스트 필요 !!)
                
                //int n = pdao.productInsert(pdDetailNo );
                
               // -------
                // 장바구니에 추가하는 서비스 호출
                // cartService.addProductToCart(userId, product);
            }

            // 성공 메시지를 클라이언트로 반환 - 추후 페이지 이동 처리예정(모달창 - confirm? 고민중)
            jsonObj.put("message", "상품이 장바구니에 추가되었습니다.");
            jsonObj.put("loginRequired", false);

            out.print(jsonObj.toString());
        }
	}
}

