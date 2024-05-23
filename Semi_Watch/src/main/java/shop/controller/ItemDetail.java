package shop.controller;

import java.sql.SQLException;
import java.util.List;

import common.controller.AbstractController;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import member.model.ss_2_MemberDAO;
import member.model.ss_2_MemberDAO_imple;
import shop.domain.ImageVO;

public class ItemDetail extends AbstractController {
	
	private ss_2_MemberDAO mdao = null;
	
	public ItemDetail() {
		mdao = new ss_2_MemberDAO_imple();
	}
	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		try {
			ImageVO mainimgvo = mdao.imageSelect();
			request.setAttribute("mainimgvo", mainimgvo); //저장소에 넣어줘야 쓸수있다.
			
			List<ImageVO> imgList = mdao.imageSelectAll();
			request.setAttribute("imgList", imgList); //저장소에 넣어줘야 쓸수있다.
			
			
			
			super.setRedirect(false);
			super.setViewPage("/WEB-INF/item/itemDetail.jsp");
			
		} catch(SQLException e) {
			// 에러가 뜨면 sendredirect 로 보내준다. 
			e.printStackTrace();
			super.setRedirect(true); //sendredirect 임!!
			super.setViewPage(request.getContextPath() + "/index.flex"); // /MyMVC/error.up
		}

	}

}
