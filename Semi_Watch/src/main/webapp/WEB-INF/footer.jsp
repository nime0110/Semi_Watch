<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String ctxPath = request.getContextPath(); //현재 컨텍스트 패스는 /MyMVC
%>    
<link rel="stylesheet" href="<%= ctxPath%>/css/reset.css">
<link rel="stylesheet" type="text/css" href="<%= ctxPath%>/css/index/index.css" />
    <!-- footer // S -->
    <footer>
      <div>
        <!-- 프로젝트 팀명 -->
        <div class="team_name">Project Team 2.</div>
        <ul class="team_members">
          <li><a href="#">Heo Seongsim</a></li>
          <li><a href="#">member 1</a></li>
          <li><a href="#">member 2</a></li>
          <li><a href="#">member 3</a></li>
          <li><a href="#">member 4</a></li>
        </ul>
      </div>
      <div class="footer_info">
        <div class="project_name">Timeless</div>
        <div class="sns_box">
          <a href="#"><i class="fa-brands fa-github"></i></a>
          <a href="#"><i class="fa-brands fa-github"></i></a>
          <a href="#"><i class="fa-brands fa-github"></i></a>
        </div>
        <div class="copyright">© Project Team 2 Corp.</div>
      </div>
    </footer>
    <!-- footer // E -->

</body>
</html>