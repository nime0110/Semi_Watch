<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
<%-- 문자를 숫자로 변환해주는!!!! taglib!!!!  --%>

<jsp:include page="../header1.jsp" />

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
   
   table#memberTbl tr.productInfo:hover {
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
			
			const frm = document.member_search_frm;
			
			frm.submit();
		});
		
		
		// **** 특정 회원을 클릭하면 그 회원의 상세정보를 보여주도록 한다. **** //
		$("table#productTbl tr.productInfo").click( e => {
			
			// alert( $(e.target).parent().html() );
			<%-- 이벤트의 선택자는 td의 상위인 tr로 잡았지만, e.target(클릭하는곳)은 td가 되어진다. --%>
			
			const userid = $(e.target).parent().find(".userid").text();
			<%-- find 활용좀 하자 --%>
			
			
			const frm = document.productOneDetail_frm;
			
			frm.pdno.value = pdno;

			frm.action = "${pageContext.request.contextPath}/product/productOneDetail.up";
			frm.method = "post";
			frm.submit();
			
			
			
		}); // end of $("table#memberTbl tr.memberInfo").click( e => {})
		
		
		<%--  .jsp 파일에서 사용되어지는 것들 
	     console.log('${pageContext.request.contextPath}');  // 컨텍스트패스   /MyMVC
	     console.log('${pageContext.request.requestURL}');   // 전체 URL     http://localhost:9090/MyMVC/WEB-INF/member/admin/memberList.jsp
	     console.log('${pageContext.request.scheme}');       // http        http
	     console.log('${pageContext.request.serverName}');   // localhost   localhost
	     console.log('${pageContext.request.serverPort}');   // 포트번호      9090
	     console.log('${pageContext.request.requestURI}');   // 요청 URI     /MyMVC/WEB-INF/member/admin/memberList.jsp 
	     console.log('${pageContext.request.servletPath}');  // 파일명       /WEB-INF/member/admin/memberList.jsp 
	    --%>
		
		
		
		
	}); // end of $(document).ready(function(){} 
	
	function goSearch(){
		
		const searchType = $("select[name='sizePerPage']").val();
		
		if(searchType == ""){
			
			alert('검색대상을 선택하세요!!');
			
			return; // goSearch() 함수를 종료한다.
		} 
		
		// 현재 가정은 공백이여도 진행시킨다는 가정임
		const frm = document.member_search_frm;
		
		// frm.action = "memberList.up"; // form 태그에 action 이 명기되지 않았으면 현재보이는 URL 경로로 submit 되어진다.
		
		// frm.method = "get"; // form 태그에 method 를 명기하지 않으면 "get" 방식이다.
		
		frm.submit();
		
	} // end of function goSearch(){} 

</script>

	<div class="container" style="padding: 3% 0;">
	   <h2 class="text-center mb-5">상품전체 목록(관리자용)</h2>
	   
	   <form name="product_search_frm">
	      <select name="searchType">
	         <option value="">검색대상</option>
	         <option value="brand">브랜드명</option>
	         <option value="pdno">상품번호</option>
	         <option value="pdname">상품명</option>
	      </select>
	      &nbsp;
	      <input type="text" name="searchWord" />
	      <%--	 **************** input 태그 갯수 주의사항 !!!! ****************
	             form 태그내에서 데이터를 전송해야 할 input 태그가 만약에 1개 밖에 없을 경우에는
	             input 태그내에 값을 넣고나서 그냥 엔터를 해버리면 submit 되어져 버린다.
	             그래서 유효성 검사를 거친후 submit 을 하고 싶어도 input 태그가 만약에 1개 밖에 없을 경우라면 
	             유효성검사가 있더라도 유효성검사를 거치지 않고 바로 submit 되어진다. 
	             이것을 막으려면 input 태그가 2개 이상 존재하면 된다.  
	             그런데 실제 화면에 보여질 input 태그는 1개 이어야 한다.
	             이럴 경우 아래와 같이 해주면 된다. 
	             또한 form 태그에 action 이 명기되지 않았으면 현재보이는 URL 경로로 submit 되어진다.   
	        --%>
	        
	       <input type="text" style="display: none;" /> <%-- 조심할 것은 type="hidden" 이 아니다. 히든 쓰면안댐!!! --%> 
	      
	      <button type="button" class="btn btn-secondary" onclick="goSearch()">검색</button>
	      
	      <span style="font-size: 12pt; font-weight: bold;">페이지당 보여줄 상품갯수&nbsp;-&nbsp;</span>
	      <select name="sizePerPage">
	         <option value="10">10개</option>
	         <option value="5">5개</option>
	         <option value="3">3개</option>      
	      </select>
	   </form>
	   
	   <table class="table table-bordered" id="productTbl">
	      <thead>
	          <tr>
	             <th class="col-sm-1 col-md-1">상품<br>번호</th>
	             <th class="col-sm-2 col-md-2">상품명</th>
	             <th class="col-sm-1 col-md-1">브랜드</th>
	             <th class="col-sm-1 col-md-1">상품<br>상태</th>
	             <th class="col-sm-2 col-md-2">정가</th>
	             <th class="col-sm-2 col-md-2">판매가</th>
	             <th class="col-sm-1 col-md-1">남은<br>재고</th>
	          </tr>
	      </thead>
	      
	      <tbody>
	          
	          <c:if test="${not empty requestScope.productList}">
	          
		          <c:forEach var="productvo" items="${requestScope.productList}" varStatus="status" >
		          
		          <fmt:parseNumber var="currentShowPageNo" value="${requestScope.currentShowPageNo}" />
		          <%-- fmt포맷으로 숫자로 변환하고 그 결과를 var에 지정한 변수명에 담는다!!!!!  --%>
		          <%-- fmt:parseNumber 은 문자열을 숫자형식으로 형변환 시키는 것이다. --%>
		          
		          <fmt:parseNumber var="sizePerPage" value="${requestScope.sizePerPage}" />
		          
		          <%-- totalMemberCount 는 int로 넘어왔고, currentShowPageNo은 String 이기때문에  --%>
		          	
		          <tr class="memberInfo">
		          <%-- 선택자를 잡을건데 복수개이기때문에 주는 class다!!!  --%>	
		          	<td>${(requestScope.totalMemberCount) - (currentShowPageNo - 1) * (sizePerPage) - (status.index)}</td>
		          	<%-- >>> 페이징 처리시 보여주는 순번 공식 <<<
                     데이터개수 - (페이지번호 - 1) * 1페이지당보여줄개수 - 인덱스번호 => 순번 
                  
                     <예제>
                     데이터개수 : 12
                     1페이지당보여줄개수 : 5
                  
                     ==> 1 페이지       
                     12 - (1-1) * 5 - 0  => 12
                     12 - (1-1) * 5 - 1  => 11
                     12 - (1-1) * 5 - 2  => 10
                     12 - (1-1) * 5 - 3  =>  9
                     12 - (1-1) * 5 - 4  =>  8
                  
                     ==> 2 페이지
                     12 - (2-1) * 5 - 0  =>  7
                     12 - (2-1) * 5 - 1  =>  6
                     12 - (2-1) * 5 - 2  =>  5
                     12 - (2-1) * 5 - 3  =>  4
                     12 - (2-1) * 5 - 4  =>  3
                  
                     ==> 3 페이지
                     12 - (3-1) * 5 - 0  =>  2
                     12 - (3-1) * 5 - 1  =>  1 
                 --%>
		          	
		          	
		          	<td class="pdno">${productvo.pdno}</td>
		          	<td>${productvo.pdname}</td>
		          	<td>${productvo.brand}</td>
		          	<td>  
		          	<%--   	   <c:choose>
		          					<c:when test="${membervo.gender == '1'}">
		          					남
		          					</c:when>
		          					<c:otherwise>
		          					여
		          					</c:otherwise> 
		          			   </c:choose>  --%>
		          	${productvo.pdstatus}
		          	</td>
		          	<td>${productvo.price}</td>
		          	<td>${productvo.saleprice}</td>
		          	<td>재고수량해야함</td>
		          </tr>
		          	          	
		          </c:forEach>
	          
	          </c:if>
	          
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


<jsp:include page="../footer.jsp" />
