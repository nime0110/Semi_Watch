<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../../header1.jsp" />

<style type="text/css">
   
   table#reviewTbl {
      width: 80%;
      margin: 0 auto;
   }
   
   table#reviewTbl th {
      text-align: center;
      font-size: 14pt;
   }
   
   table#reviewTbl tr.reviewInfo:hover {
      background-color: #e6ffe6;
      cursor: pointer;
   }
   
   form[name="review_search_frm"] {
      border: solid 0px red;
      width: 80%;
      margin: 0 auto 3% auto;
   }
   
   form[name="review_search_frm"] button.btn-secondary {
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

		if("${requestScope.searchType}" != "" && "${requestScope.searchWord}" != "") {
			$("select[name='searchType']").val("${requestScope.searchType}"); //값 설정
			$("input:text[name='searchWord']").val("${requestScope.searchWord}");
		}
				  
		if("${requestScope.sizePerPage}" != "") {
			$("select[name='sizePerPage']").val("${requestScope.sizePerPage}");		
		}

		$("input:text[name='searchWord']").bind("keydown", function(e){
			if(e.keyCode == 13) {
				goSearch();
			}
		});
		// **** select 태그에 대한 이벤트는 click 이 아니라 change 이다. **** //
		$("select[name='sizePerPage']").bind("change", function() {
			const frm = document.review_search_frm;
			frm.submit();
		})
		
		
		// **** 특정 리뷰을 클릭하면 그 리뷰의 상세정보를 보여주도록 한다. **** //
		$("table#reviewTbl tr.reviewInfo").click( e => {
		
			const reviewno = $(e.target).parent().children(".reviewno").text();
			// alert(userid);
			$("input:hidden[name='reviewno']").val(reviewno);
			
			const frm = document.reviewOneDetail_frm;
			frm.action = "${pageContext.request.contextPath}/member/reviewOneDetail.flex"; 
			frm.method = "post";
			frm.submit();
		})
				
		
	}); // end of jqready ------------------- 
	function goSearch() {
		//유효성 검사
		const searchType = $("select[name='searchType']").val();
		if(searchType == "") {
			alert("검색대상을 선택하세요!!");
			return;// goSearch() 함수를 종료한다
		}

		
		const frm = document.review_search_frm;
		frm.submit();
	} // end of goSearch()--------------
	

	
	
</script>

<div class="container" style="padding: 3% 0; border: solid 1px red;">
   <h2 class="text-center mb-5"> 리뷰전체 목록 </h2>
   
   <form name="review_search_frm">
      <select name="searchType">
         <option value="">검색대상</option>
         <option value="pdname">제품명</option>
         <option value="userid" >아이디</option>
         <option value="brand">브랜드</option>
      </select>
      &nbsp;
      <input type="text" name="searchWord" /> 
       <input type="text" style="display: none;" /> 
      
      <button type="button" class="btn btn-secondary" onclick="goSearch()">검색</button>
      
      <span style="font-size: 12pt; font-weight: bold;">페이지당 리뷰수&nbsp;-&nbsp;</span>
      <select name="sizePerPage">
         <option value="10">10개</option>
         <option value="5">5개</option>
         <option value="3">3개</option>      
      </select>
   </form>
   
   <table class="table table-bordered" id="reviewTbl">
      <thead>
          <tr>
             <th>리뷰번호</th> 
             <th>유저아이디</th>
             <th>유저명</th>
             <th>제품명</th>
             <th>브랜드</th>
             <th>리뷰내용</th>
             <th>별점</th>	
          </tr>
      </thead>
      <tbody>
	      <c:if test="${not empty requestScope.reviewList}">      
	          <c:forEach var="rvo" items="${requestScope.reviewList}" varStatus="status">
	          	<tr class="reviewInfo">
	          		<fmt:parseNumber var="currentShowPageNo" value="${requestScope.currentShowPageNo}" />
                 	<fmt:parseNumber var="sizePerPage" value="${requestScope.sizePerPage}" />
	          		<td class="reviewno">${rvo.reviewno}</td>
	          		<td class="userid">${rvo.mvo.userid}</td>
	          		<td>${rvo.mvo.username}</td>
	          		<td>${rvo.pvo.pdname}</td>
	          		<td>${rvo.pvo.brand}</td>
	          		<td>${rvo.review_content}</td>
	          		<td>${rvo.starpoint}</td>
	          	</tr>
	          </c:forEach>
	      </c:if>
	      <c:if test="${empty requestScope.reviewList}">      
	          <tr>
	          	 <td colspan="5">데이터가 존재하지 않습니다.</td>
	          </tr>
	      </c:if>
      </tbody> 
   </table>     

    <div id="pageBar">
       <nav>
			<ul class="pagination">
				${requestScope.pageBar}
			</ul>
       </nav>
    </div>
</div>

<form name="reviewOneDetail_frm">
	<input type="hidden" name="reviewno"/>
	<input type="hidden" name="goBackURL" value="${requestScope.currentURL}"/>
	<%-- 폼 전송을 통해 URL 을 보낸다. --%>
</form>
<jsp:include page="../../footer.jsp" />
