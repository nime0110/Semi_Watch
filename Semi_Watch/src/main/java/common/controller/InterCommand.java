package common.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface InterCommand {
// interface는 접근제한자 public과 미완성메소드(추상메소드)가 생략 되어있다
	
	void execute(HttpServletRequest request, HttpServletResponse response) throws Exception;
}
