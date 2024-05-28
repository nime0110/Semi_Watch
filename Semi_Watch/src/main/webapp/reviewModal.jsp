<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%
	String ctxPath = request.getContextPath();
%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="EUC-KR">
	
	<%-- 공용 제이쿼리/부트스트랩 --%>
	<script src="<%= ctxPath%>/js/jquery-3.7.1.min.js" type="text/javascript"></script>
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.3.2/jquery.rateyo.min.css">
	<!-- Latest compiled and minified JavaScript -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/rateYo/2.3.2/jquery.rateyo.min.js"></script>
	<script type="text/javascript">
		$(function () {
		 
			$("#rateYo").rateYo({
		        rating: 3, // 초기 별점
		        fullStar: true // 별점을 정수로만 설정할 경우
		    }).on("rateyo.set", function (e, data) {
		        // 사용자가 별점을 선택했을 때 실행되는 이벤트
		        $("#ratingValue").text(data.rating); // 선택한 별점을 표시
		        console.log("The rating is " + data.rating); // 선택한 별점을 콘솔에 출력
		    });
		 
		});
		
		// Getter
		var normalFill = $("#rateYo").rateYo("option", "fullStar"); //returns true
		 
		// Setter
		$("#rateYo").rateYo("option", "fullStar", true); //returns a jQuery Element
	</script>
	<title>모달</title>
</head>

<body>
<div id="rateYo"></div>
<p>Selected Rating: <span id="ratingValue">0</span></p>
</body>

</html>