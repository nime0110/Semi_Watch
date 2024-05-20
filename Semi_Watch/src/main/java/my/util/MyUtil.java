package my.util;

import jakarta.servlet.http.HttpServletRequest;

public class MyUtil {
  //*** ? 다음의 데이터까지 포함한 현재 URL 주소를 알려주는 메소드를 생성 *** //
  public static String getCurrentURL(HttpServletRequest request) {
    String currentURL = request.getRequestURL().toString(); //현재 URL 주소를 알아온다.
    // System.out.println("currentURL => " + currentURL);
    // http://localhost:9090/MyMVC/member/memberList.up 까지만
    String queryString = request.getQueryString();
    // System.out.println("queryString => " + queryString);
    //queryString => searchType=name&searchWord=%EC%9C%A0&sizePerPage=5&currentShowPageNo=10
    //queryString => null (POST 방식일 경우)
    
    if(queryString != null) {
      currentURL += "?"+ queryString;
      //http://localhost:9090/MyMVC/member/memberList.up?searchType=name&searchWord=%EC%9C%A0&sizePerPage=5&currentShowPageNo=10 처럼 나옴
      //리턴값은 /member/memberList.up?searchType=name&searchWord=%EC%9C%A0&sizePerPage=5&currentShowPageNo=10 
    }
    String ctxPath = request.getContextPath();
    //      /MyMVC
    int beginIndex = currentURL.indexOf(ctxPath) + ctxPath.length();
    //       27    =             21              +        6
    
    currentURL = currentURL.substring(beginIndex);
    //System.out.println("currentURL=>" + currentURL);
    
    return currentURL;
    
  } //end of  public static String getCurrentURL(HttpServletRequest request) --------- 
}