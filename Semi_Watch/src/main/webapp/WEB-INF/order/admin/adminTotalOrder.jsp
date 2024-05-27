<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<%-- 문자를 숫자로 변환해주는!!!! taglib!!!!  --%>

<jsp:include page="../../header1.jsp" />

<%-- 직접 넣은 사용자 css --%>
<style type="text/css">
   
   table#productTbl {
      width: 85%;
      margin: 0 auto;
   }
   
   table#productTbl th {
      text-align: center;
      font-size: 14pt;
   }
   
   table#productTbl tr.productInfo:hover {
      background-color: #e6ffe6;
      cursor: pointer;
   }
   
   form[name="product_search_frm"] {
      border: solid 0px red;
      width: 80%;
      margin: 0 auto 3% auto;
   }
   
   form[name="product_search_frm"] button.btn-secondary {
      margin-left: 2%;
      margin-right: 32%;
   }
   
   div#pageBar {
      border: solid 0px red;
      width: 80%;
      margin: 3% auto 0 auto;
      display: flex;
   }
   
   div#pageBar > nav {
      margin: auto;
   }
   
</style>

<script type="text/javascript">

	$(document).ready(function(){
		
		
		if( "${requestScope.searchType}" != "" 
		 && "${requestScope.searchWord}" != ""){
			// 자바스크립트한테 주거나 자바스크립트한테 받는건 애지간하면 "" 써라ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏ
			
			$("select[name='searchType']").val("${requestScope.searchType}");
			$("input:text[name='searchWord']").val("${requestScope.searchWord}");
		}
		
		if( "${requestScope.sizePerPage}" != "" ){
				// 자바스크립트한테 주거나 자바스크립트한테 받는건 애지간하면 "" 써라ㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏㅏ
			$("select[name='sizePerPage']").val("${requestScope.sizePerPage}");		
		}
		
		
		$("input:text[name='searchWord']").bind('keydown', function(e){
			
			if(e.keyCode == 13){
				// 엔터 == 13
				
				goSearch();
				
			}
			
		}); // enb of $("input:text[name='searchWord']").bind('keydown', function(e){})
		
		
		
		$("select[name='sizePerPage']").bind('change', function(){
			
			const frm = document.product_search_frm;
			
			frm.submit();
		});
		
	 
		// **** 특정 상품을 클릭하면 그 상품의 상세정보를 보여주도록 한다. **** //
		$("table#productTbl tr.productInfo").click( e => {
			
			// alert( $(e.target).parent().html() );
			<%-- 이벤트의 선택자는 td의 상위인 tr로 잡았지만, e.target(클릭하는곳)은 td가 되어진다. --%>
			
			const pdno = $(e.target).parent().find(".pdno").text();
			<%-- find 활용좀 하자 --%>
			
			
			const frm = document.productOneDetail_frm;
			
			frm.pdno.value = pdno;

			frm.action = "${pageContext.request.contextPath}/admin/itemAdminOneDetail.flex";
			frm.method = "post";
			frm.submit();
			
			
			
		}); // end of $("table#memberTbl tr.memberInfo").click( e => {}) 
		
		
		
	}); // end of $(document).ready(function(){} 
	
	function goSearch(){
		
		const searchType = $("select[name='searchType']").val();
		
		if(searchType == ""){
			
			alert('검색대상을 선택하세요!!');
			
			return; // goSearch() 함수를 종료한다.
		} 
		
		// 현재 가정은 공백이여도 진행시킨다는 가정임
		const frm = document.product_search_frm;
		
		// frm.action = "memberList.up"; // form 태그에 action 이 명기되지 않았으면 현재보이는 URL 경로로 submit 되어진다.
		
		// frm.method = "get"; // form 태그에 method 를 명기하지 않으면 "get" 방식이다.
		
		frm.submit();
		
	} // end of function goSearch(){} 

</script>

	<div class="container" style="padding: 3% 0;">
	   <h2 class="text-center mb-5">주문내역전체 목록(관리자용)</h2>
	   
	   <form name="product_search_frm">
	      <select name="searchType">
	         <option value="">검색대상</option>
	         <option value="cartno">주문번호명</option>
	         <option value="brand">주문번호명</option>
	         <option value="pdname">상품명</option>
	      </select>
	      &nbsp;
	      <input type="text" name="searchWord" />
	      <input type="text" style="display: none;" /> <%-- 조심할 것은 type="hidden" 이 아니다. 히든 쓰면안댐!!! --%> 
	      
	      <button type="button" class="btn btn-secondary" onclick="goSearch()">검색</button>
	      
	      <span style="font-size: 12pt; font-weight: bold;">페이지당 주문목록수&nbsp;:&nbsp;</span>
	      <select name="sizePerPage">
	         <option value="10">10개</option>
	         <option value="5">5개</option>
	         <option value="3">3개</option>      
	      </select>
	   </form>
	   
	   <table class="table table-bordered" id="productTbl">
	      <thead>
	          <tr>
	             <th class="col-sm-1 col-md-1">주문<br>번호</th>
	             <th class="col-sm-1 col-md-1">주문일자</th>
	             <th class="col-sm-3 col-md-3">상품정보</th>
	             <th class="col-sm-1 col-md-1">주문<br>수량</th>
	             <th class="col-sm-1 col-md-1">주문총금액</th>
	             <th class="col-sm-1 col-md-1">배송상태</th>
	             
	          </tr>
	      </thead>
	      
	      <tbody>
	          
	         
	          
		          <c:forEach var="productvo" items="${requestScope.productList}" varStatus="status" >
		          
		          <fmt:parseNumber var="currentShowPageNo" value="${requestScope.currentShowPageNo}" />

		          <fmt:parseNumber var="sizePerPage" value="${requestScope.sizePerPage}" />
		          
		          <tr class="productInfo">
		          	
		          	<td class="pdno">${productvo.pdno}</td>
		          	<td>${productvo.pdname}</td>
		          	<td>${productvo.brand}</td>
		          	<td>  
		          	<c:choose>
        					<c:when test="${productvo.pdstatus == '1'}">
        					인기상품
        					</c:when>
        					<c:when test="${productvo.pdstatus == '2'}">
        					일반상품
        					</c:when>
        					<c:otherwise>
        					준비중인 상품
        					</c:otherwise> 
        			</c:choose>  
		          			   
		          	
		          	</td>
		          	<td><fmt:formatNumber pattern="###,###"> ${productvo.price}</fmt:formatNumber>원</td>
		          	<td><fmt:formatNumber pattern="###,###"> ${productvo.saleprice}</fmt:formatNumber>원</td>
		          	
		          </tr>
		          	          	
		          </c:forEach>
	          
	        
	          
	          <c:if test="${empty requestScope.productList}">
						          
	          	<tr>
	          		<td colspan="7">데이터가 존재하지 않습니다.</td>
	          	</tr>
	          
	          </c:if>
	          
	      </tbody>
	   </table>     
	
	    <div id="pageBar">
	       <nav>
	          <ul class="pagination">
	          	<li>${requestScope.pageBar}</li>
	          </ul>
	       </nav>
	    </div>
	</div>

	<%-- 담아서 넘겨주기위한 수단의 폼태그 --%>
	<form name="productOneDetail_frm">
		<input type="hidden" name="pdno" />
		<input type="hidden" name="goBackURL" value="${requestScope.currentURL}" />
	</form>


<jsp:include page="../../footer.jsp" />
