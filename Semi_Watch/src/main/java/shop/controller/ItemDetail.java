package shop.controller;

import java.sql.SQLException;
import java.util.List;

import common.controller.AbstractController;
import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.ss_2_MemberDAO;
import member.model.ss_2_MemberDAO_imple;
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
		
		//사용자 url 쿼리문 조작 막기
		String referer = request.getHeader("referer");
		
		if(referer == null) {
			super.setRedirect(true);
			super.setViewPage(request.getContextPath()+"/index.flex");
			return;
		}
		
		
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
		
		
		request.setAttribute("pvo", pvo);
		request.setAttribute("imgList", imgList);
		request.setAttribute("colorList", colorList);
		
		super.setRedirect(false);
		super.setViewPage("/WEB-INF/item/itemDetail.jsp");
		

	}

}
