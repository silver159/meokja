package com.meokja.service;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meokja.dao.MemberDAO;
import com.meokja.vo.MemberVO;

@Service
public class LoginService {
	
	private static final Logger logger = LoggerFactory.getLogger(LoginService.class);
	
	@Autowired
    private SqlSession sqlSession;
	
	@Autowired
	private MemberVO user;
	
	public String login(HttpSession session, MemberVO memberVO) {
		
		logger.info("loginService의 login()");
		
		MemberDAO mapper = sqlSession.getMapper(MemberDAO.class);
		
		// 아이디 조회
		user = mapper.selectById(memberVO.getMember_id());
		logger.info("loginService의 line30 {} ", user);
		
		// 상황에 맞게 alert창 문구 생성
		String loginMessage = "";
		if (user == null) {
			loginMessage = "alert('정보가 일치하지 않습니다.');\n";
			loginMessage += "location.href='loginPage';";
		} else if (memberVO.getPw().equals(user.getPw())) {
			// 세션에 로그인 정보 추가
			session.setAttribute("user", user);
			loginMessage += "alert('" + user.getMember_id() + "님 반갑습니다.');\n";
			loginMessage += "location.href='list';";
		} else {
			loginMessage = "alert('정보가 일치하지 않습니다.');\n";
			loginMessage += "location.href='loginPage';";
		}
		return loginMessage;
	}

	public String logout(HttpSession session) {
		
		logger.info("loginService의 login()");
		
		// 세션에서 로그인 정보 제거
		session.removeAttribute("user");
		
		String logoutMessage = "alert('로그아웃하셨습니다.');\n";
		logoutMessage += "location.href='list';";
		return logoutMessage;
	}

    public String idSearch(MemberVO memberVO) {
    	
        logger.info("loginService의 idSearch()");
        logger.info("loginService의 line64 {} ", memberVO);
        
        MemberDAO mapper = sqlSession.getMapper(MemberDAO.class);
        // 같은 이름 주민번호 정보 가져오기
        String member_id = mapper.selectByName(memberVO);
        logger.info("loginService의 line68 {} ", member_id);
        
        // 상황에 맞게 alert창 문구 생성
        String idSearchMessage;
        if(member_id ==  null) {
        	idSearchMessage = "alert('일치하는 정보가 없습니다.');\n";
        	idSearchMessage += "location.href='idSerchPage';";
		} else {
			idSearchMessage = "alert('" + memberVO.getName() + "님의 아이디는" + member_id + "입니다.');\n";
			idSearchMessage += "location.href='loginPage';";
		}
        
        // 아이디 찾기 결과에 따른 메시지 반환
        return idSearchMessage; 
    }
	
    public String pwSearch(MemberVO memberVO) {
    	
        logger.info("loginService의 pwSearch()");
        
        MemberDAO mapper = sqlSession.getMapper(MemberDAO.class);
        user = mapper.pwSerch(memberVO);
        
        // 상황에 맞게 alert창 문구 생성
        String pwSearchMessage ="";
        if(user ==  null) {
        	pwSearchMessage = "alert('일치하는 정보가 없습니다.');\n";
        	pwSearchMessage += "location.href='pwSerchPage';";
		} else {
			pwSearchMessage = "alert('비밀번호를 변경해 주세요.');\n";
			pwSearchMessage += "location.href='pwdChangePage';";
		}
        
        // 비밀번호 찾기 결과에 따른 메시지 반환
        return pwSearchMessage; 
    }
	
}
































