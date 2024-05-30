package shop.model;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import shop.domain.CartVO;
import shop.domain.ImageVO;
import shop.domain.ProductVO;
import shop.domain.Product_DetailVO;

public interface js_5_ProductDAO {

	// 전체 상픔리스트 가져오기
	List<ProductVO> select_product_pagin(Map<String, String> paraMap) throws SQLException;

	// 전체 상픔리스트 페이지수 알아오기
	int getTotalPage(Map<String, String> paraMap) throws SQLException;

	/* >>> 뷰단(memberList.jsp)에서 "페이징 처리시 보여주는 순번 공식" 에서 사용하기 위해 
    검색이 있는 또는 검색이 없는 회원의 총개수 알아오기 시작 <<< */
	int getTotalProductCount(Map<String, String> paraMap) throws SQLException;

	// 브랜드명을 조회하는 검색메소드
	List<ProductVO> search_product_pagin_brand(Map<String, String> paraMap) throws SQLException;

	// 상품명을 조회하는 검색메소드
	List<ProductVO> search_product_pagin_pdname(Map<String, String> paraMap) throws SQLException;

	// 브랜드명을 검색해서 나오는 토탈페이지구하기
	int search_brand_TotalPage(Map<String, String> paraMap) throws SQLException;

	// 상품명을 조회해서 나오는 토탈페이지구하기
	int search_pdname_TotalPage(Map<String, String> paraMap) throws SQLException;

	// 제품번호 채번해오기
	int getPnumOfProduct() throws SQLException;

	// tbl_product 테이블에 제품정보 insert 하기
	int productinsert(ProductVO pvo) throws SQLException;

	// >>> tbl_product_imagefile 테이블에 제품의 추가이미지 파일명 insert 하기 <<<
	int product_imagefile_insert(Map<String, String> paraMap) throws SQLException;

	// 상품추가정보입력을 위한 상품명, 브랜도 조회해오기
	ProductVO select_extrainfo(String setpdno) throws SQLException;

	// 상품추가정보 입력
	int insert_product_detail(Map<String, String> paraMap) throws SQLException;

	// 관리자가 상품을 수정하기위한 상품리스트의 total 페이지개수
	int get_admin_ProductTotalPage(Map<String, String> paraMap) throws SQLException;

	// 관리자가 상품을 수정하기위한 상품리스트
	List<ProductVO> select_admin_product_pagin(Map<String, String> paraMap) throws SQLException;

	// 관리자가 상품을 수정하기위한 상품리스트의 total 개수
	int get_admin_TotalProductCount(Map<String, String> paraMap) throws SQLException;

	// 특정 상품번호의 상품정보를 가져오는 메소드1
	ProductVO selectOneProductInfo(String pdno) throws SQLException;

	// 상품삭제하기 
	int delete_product(String pdno) throws SQLException;

	// 삭제할 상품이미지 파일명 가져오기
	List<String> select_imgfilename(String pdno) throws SQLException;

	// 상품테이블 업데이트 후 자식테이블 삭제 후 insert ==> 수동커밋!!
	int delete_after_insert(Map<String, String> paraMap);

	// 삭제할 추가 상품이미지 파일명 가져오기
	List<String> select_extraimgfilename(String pdno2) throws SQLException;

	// 상품수정페이지에 띄울 상품상세정보(색상별 재고)
	List<Product_DetailVO> selectOnePDetail(String pdno) throws SQLException;

	// 상품수정페이지에 띄울 상품추가이미지
	List<ImageVO> extraimgfilename(String pdno) throws SQLException;

	// 로그인한 유저의 장바구니 테이블 불러오기
	List<CartVO> selectProductCart(String userid) throws SQLException;

	// 장바구니 테이블에서 특정 상품을 장바구니에서 비우기
	int delCart(String cartno) throws SQLException;

	// 장바구니 테이블에서 특정 상품 장바구니수량 변경하기
	int updateCart(Map<String, String> paraMap) throws SQLException;


	


	


	
	
	
	
}