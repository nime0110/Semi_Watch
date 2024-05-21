<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="../../header1.jsp" />

<style type="text/css">
   
   table#memberTbl {
      width: 80%;
      margin: 0 auto;
   }
   
   table#memberTbl th {
      text-align: center;
      font-size: 14pt;
   }
   
   table#memberTbl tr.memberInfo:hover {
      background-color: #e6ffe6;
      cursor: pointer;
   }
   
   form[name="member_search_frm"] {
      border: solid 0px red;
      width: 80%;
      margin: 0 auto 3% auto;
   }
   
   form[name="member_search_frm"] button.btn-secondary {
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
			const frm = document.member_search_frm;
			frm.submit();
		})
		
		
		// **** 특정 회원을 클릭하면 그 회원의 상세정보를 보여주도록 한다. **** //
		$("table#memberTbl tr.memberInfo").click( e => {
		
			const userid = $(e.target).parent().children(".userid").text();
			// alert(userid);
			
			const frm = document.memberOneDetail_frm;
			frm.userid.value = userid;
			frm.action = "${pageContext.request.contextPath}/member/memberOneDetail.flex"; 
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

		
		const frm = document.member_search_frm;
		frm.submit();
	} // end of goSearch()--------------
	

	
	
</script>

<div class="container" style="padding: 3% 0; border: solid 1px red;">
   <h2 class="text-center mb-5"> 회원전체 목록 </h2>
   
   <form name="member_search_frm">
      <select name="searchType">
         <option value="">검색대상</option>
         <option value="username">회원명</option>
         <option value="userid">아이디</option>
         <option value="email">이메일</option>
      </select>
      &nbsp;
      <input type="text" name="searchWord" /> 
       <input type="text" style="display: none;" /> 
      
      <button type="button" class="btn btn-secondary" onclick="goSearch()">검색</button>
      
      <span style="font-size: 12pt; font-weight: bold;">페이지당 회원명수&nbsp;-&nbsp;</span>
      <select name="sizePerPage">
         <option value="10">10명</option>
         <option value="5">5명</option>
         <option value="3">3명</option>      
      </select>
   </form>
   
   <table class="table table-bordered" id="memberTbl">
      <thead>
          <tr>
             <th>번호</th>
             <th>아이디</th>
             <th>회원명</th>
             <th>이메일</th>
             <th>성별</th>	
          </tr>
      </thead>
      <tbody>
	      <c:if test="${not empty requestScope.memberList}">      
	          <c:forEach var="membervo" items="${requestScope.memberList}" varStatus="status">
	          	<tr class="memberInfo">
                 	<fmt:parseNumber var="currentShowPageNo" value="${requestScope.currentShowPageNo}" />
                 	<fmt:parseNumber var="sizePerPage" value="${requestScope.sizePerPage}" />
                 	<%-- fmt:parseNumber 은 문자열을 숫자형식으로 형변환 시키는 것이다. --%>
                 	<%-- 데이터개수 - (페이지번호 - 1) * 1페이지당보여줄개수 - 인덱스번호 => 순번 --%>
	          		<td>${(requestScope.totalMemberCount) - (currentShowPageNo -1) * sizePerPage - (status.index)}</td>
	          		<td class="userid">${membervo.userid}</td>
	          		<td>${membervo.username}</td>
	          		<td>${membervo.email}</td>
	          		<td>
	          			<c:choose>
	          				<c:when test="${membervo.gender == '1'}">남</c:when>
	          				<c:otherwise>여</c:otherwise>
	          			</c:choose>
	          		</td>
	          	</tr>
	          </c:forEach>
	      </c:if>
	      <c:if test="${empty requestScope.memberList}">      
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

<form name="memberOneDetail_frm">
	<input type="hidden" name="userid"/>
	<input type="hidden" name="goBackURL" value="${requestScope.currentURL}"/>
	<%-- 폼 전송을 통해 URL 을 보낸다. --%>
</form>
<jsp:include page="../../footer.jsp" />
