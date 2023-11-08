package com.meokja.meokja;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.meokja.dao.MemberDAO;
import com.meokja.service.LoginService;
import com.meokja.vo.MemberVO;

@Controller
public class LoginController {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private LoginService loginService;
	
	// 로그인
    @RequestMapping("/login")
    public void login(HttpSession session, HttpServletResponse response, MemberVO memberVO) throws IOException {
        logger.info("LoginController의 login()");
        
        // loginService를 통해 로그인 처리
        String loginMessage = loginService.login(session, memberVO);
        
        // 상황에 따른 alert창 생성
        printScriptMessage(response, loginMessage);
    }
	
	// 로그아웃
    @RequestMapping("/logout")
    public void logout(HttpSession session, HttpServletResponse response) throws IOException {
        logger.info("LoginController의 logout()");

        // loginService를 통해 로그아웃 처리
        String logoutMessage = loginService.logout(session);
        
        // 로그아웃 alert창 생성
        printScriptMessage(response, logoutMessage);
    }
	
	// 아이디 찾기	
    @RequestMapping("/idSerch")
    public void idSerch(HttpServletResponse response, MemberVO memberVO) throws IOException {
        logger.info("LoginController의 idSerch()");

        // loginService를 통해 아이디 찾기 처리
        String idSearchMessage = loginService.idSearch(memberVO);
        printScriptMessage(response, idSearchMessage);
    }
	
	// 비밀번호 찾기
    @RequestMapping("/pwSerch")
    public void pwSerch(HttpServletResponse response, MemberVO memberVO) throws IOException {
        logger.info("LoginController의 pwSerch()");

        // loginService를 통해 비밀번호 찾기 처리
        String pwSearchMessage = loginService.pwSearch(memberVO);
        
        printScriptMessage(response, pwSearchMessage);
    }
    
    // 비밀번호 찾기
    @RequestMapping("/pwdChange")
    public void pwdChange(HttpServletResponse response, MemberVO memberVO) throws IOException {
    	logger.info("LoginController의 pwdChange()");
    	logger.info("{}", memberVO);
    	// loginService를 통해 비밀번호 변경
    	String pwdChangeMessage = loginService.pwdChange(memberVO);
    	printScriptMessage(response, pwdChangeMessage);
    }    
    
    // 공통 메소드
    private void printScriptMessage(HttpServletResponse response, String scriptMessage) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>");
        out.println(scriptMessage);
        out.println("</script>");
        out.flush();
    }
	
}
