<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	String ctxPath = request.getContextPath();
%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<%-- 문자를 숫자로 변환해주는!!!! taglib!!!!  --%>
<style>
    .pimg {
        width: 100px;
        height: auto;
    }
    .subject {
        font-weight: bold;
        background-color: #f8f9fa;
    }
    .info-row p {
        margin: 0.5rem 0;
        
    }
    .info-row {
    	font-weight : normal;
    }
    .info-section {
    	font-weight : bold;
    	font-size: 20px;
        margin: 2% 0;
    }
    .info-section hr {
        margin: 0.5rem 0;
    }
</style>
<jsp:include page="../../header1.jsp" />
<div class="container-fluid" style="margin-bottom:5%;">
    <div class="col-xl-9 mt-4 mx-auto">
        <div>
            <span class="h4" style="font-weight: bold;">주문상품정보</span>
            <hr style="border: solid 3px black;">
        </div>
        <table id="userinfo" class="table table-bordered" style="width: 100%;">
            <colgroup>
                <col style="width: 55%;">
                <col style="width: 20%;">
                <col style="width: 25%;">
            </colgroup>
            <thead class="text-center subject thead-bordered">
                <!-- 구매자 정보 -->
                <tr>
                    <td colspan="3">
                        <div class="info-section">구매자 정보</div>
                        <hr>
                        <c:set var="info" value="${requestScope.admin_odrinfo}"/>
                        <div class="d-flex justify-content-between info-row">
                            <p class="col-2">이름 : ${info.username}</p>
                            <p class="col-2">아이디 : ${info.userid}</p>
                            <p class="col-4">연락처 : ${info.mobile}</p>
                            <p class="col-4">이메일 : ${info.email}</p>
                        </div>
                    </td>
                </tr>
                <!-- 결제정보 -->
                <tr>
                    <td colspan="3">
                    	 <div class="info-section">결제 정보</div>
                        <hr>
                        <div class="d-flex justify-content-center info-row">
                        <p class="col-3">주문번호 : ${info.ordercode}</p>
                        <p class="col-3">주문일자 : ${info.total_orderdate}</p>
                        <p class="col-3">주문총금액 : <fmt:formatNumber value="${info.total_price}" type="number" pattern="#,###" /> 원</p>
                        </div>
                    </td>
                </tr>
                <!-- 배송지정보 -->
                <tr>
                    <td colspan="3">
                        <div class="info-section">배송지 정보</div>
                        <hr>
                        <div class="d-flex flex-wrap info-row">
                            <p class="col-12">받는사람 : ${info.delivery_name}</p>
                            <p class="col-12">연락처 : ${info.delivery_mobile}</p>
                            <p class="col-12">주소 : ${info.delivery_postcode}&nbsp;${info.delivery_address}</p>
                            <p class="col-12">배송요청사항 : ${info.delivery_msg}</p>
                        </div>
                    </td>
                </tr>
                
            </thead>
            <tbody>
            	<!-- 상품정보, 진행상태, 구매후기 헤더 -->
                <tr class="subject py-3 text-center">
                    <th scope="col">구매상품정보</th>
                    <th scope="col">배송진행상태</th>
                    <th scope="col">구매후기</th>
                </tr>
                <!-- 상품정보 및 배송상태 리뷰 쓰는 버튼 생성 -->
                <c:forEach var="list" items="${requestScope.admin_odrdetail_list}">
                <tr class="py-2">
                    <td style="display:flex; align-items: center;">
                    	
                        <a href="#">
                            <img class="pimg" src="<%= ctxPath%>/images/product/${list.pdimg1}" alt="상품 이미지" />
                        </a>
                        <div class="ml-4">
                            <p class="py-1">브랜드 : ${list.brand}</p>
                            <p class="py-1">상품명 : ${list.pdname}</p>
                            <p class="py-1">상품코드 : ${list.pdno}</p>
                            <p class="py-1">컬러명 : ${list.color}<span> / 구매수량 : ${list.order_qty}개</span></p>
                            <p class="py-1">상품상세코드 : ${list.pd_detailno}<span> / 남은재고수량 : ${list.pd_qty}개</span></p>
                            <p class="py-1">개당가격 : <fmt:formatNumber value="${list.saleprice}" type="number" groupingUsed="true" />원</p>
                            <p class="py-1">개당적립포인트 : <fmt:formatNumber value="${list.point}" type="number" groupingUsed="true" />Point</p>
                        </div>
                    </td>
                    <td class="text-center align-middle">
							${info.delivery_status}
                    </td>
                    <td class="text-center align-middle">
                        <button class="btn btn-sm btn-outline-dark change_btn" type="button" id="change_btn">리뷰 작성</button>
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
	
<jsp:include page="../../footer.jsp"/>	
	