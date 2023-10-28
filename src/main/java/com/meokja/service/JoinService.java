package com.meokja.service;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meokja.api.NaverSMTP;
import com.meokja.dao.JoinDAO;
import com.meokja.vo.JoinVO;
import com.meokja.vo.MemberVO;
import com.meokja.vo.PartyVO;

@Service
public class JoinService {
	
	private static final Logger logger = LoggerFactory.getLogger(JoinService.class);	
	
	@Autowired
	private SqlSession sqlSession;
	
    public String joinInsert(HttpSession session, JoinVO joinVO) {
        
    	logger.info("JoinService의 joinInsert()");
    	
    	JoinDAO mapper = sqlSession.getMapper(JoinDAO.class);
    	
    	// 세션에 저장된 회원정보
        MemberVO user = (MemberVO) session.getAttribute("user");
        
        joinVO.setMember_id(user.getMember_id());
        logger.info("JoinService line 42 {}", joinVO);
        if(joinVO.getContents() == null || joinVO.getContents() == "") {
        	joinVO.setContents("참여하겠습니다.");
        }
        mapper.joinInsert(joinVO);
        
        return "redirect:selectByIdx?party_id=" +joinVO.getParty_id()+"&job=article";
    }

	public JoinVO selectByJoin_id(JoinVO joinVO) {
		
		logger.info("JoinService의 selectByJoin_id()");
		
		JoinDAO mapper = sqlSession.getMapper(JoinDAO.class);
		
		return  mapper.selectByJoin_id(joinVO);
	}

	public String joinGrant(JoinVO joinVO, PartyVO vo, int currentPage) {
		
		logger.info("JoinService의 joinGrant()");
		
		int limitNum = joinVO.getLimitNum();
		int count = joinVO.getCount() + 1;
		JoinDAO mapper = sqlSession.getMapper(JoinDAO.class);
		mapper.joinGrant(joinVO);
		if(limitNum == count) {
			mapper.deleteJoin(joinVO);
		}
		
		// 메일 보내기
		String from = "ajrwkd1@naver.com";
		String to = joinVO.getEmail(); 
		String subject = "신청하신 모임의 참여가 승인되었습니다.";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
		String mealed_at = sdf.format(vo.getMealed_at());
		String content = joinVO.getMember_id() + "님 신청하신 " + mealed_at + " 모임 참여가 승인되었습니다.";
		
		// 폼값(이메일 내용) 저장
		Map<String, String> emailInfo = new HashMap<String, String>();
		emailInfo.put("from", from);		// 보내는 사람
		emailInfo.put("to", to);			// 받는 사람
		emailInfo.put("subject", subject);	// 제목
		emailInfo.put("content", content);
		emailInfo.put("format", "text/plain;charset=UTF-8");
		logger.info("line137 {}", emailInfo);
		try {
			NaverSMTP smtpServer = new NaverSMTP();	// 메일 전송 클래스 생성
			smtpServer.emailSending(emailInfo);		// 전송
			System.out.print("이메일 전송 성공");
		} catch (Exception e) {
			System.out.print("이메일 전송 실패");
		}
		
		String grantMessage = "alert('승인완료!!!');\n";
		grantMessage  += "location.href='selectByIdx?party_id=" +joinVO.getParty_id()+ "&currentPage=" +currentPage+ "&job=article';";
		return grantMessage;
	}

	public String joinReject(JoinVO joinVO, PartyVO vo, int currentPage) {
		
		logger.info("JoinService의 joinReject()");
		
		JoinDAO mapper = sqlSession.getMapper(JoinDAO.class);
		mapper.joinReject(joinVO);
		
		// 메일 보내기
		String from = "ajrwkd1@naver.com";
		String to = joinVO.getEmail(); 
		String subject = "신청하신 모임의 참여가 거절되었습니다.";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy년 MM월 dd일");
		String mealed_at = sdf.format(vo.getMealed_at());
		String content = joinVO.getMember_id() + "님 신청하신 " + mealed_at + " 모임 참여가 거절되었습니다.";
		
		// 폼값(이메일 내용) 저장
		Map<String, String> emailInfo = new HashMap<String, String>();
		emailInfo.put("from", from); // 보내는 사람
		emailInfo.put("to", to); // 받는 사람
		emailInfo.put("subject", subject); // 제목
		emailInfo.put("content", content);
		emailInfo.put("format", "text/plain;charset=UTF-8");
		logger.info("line137 {}", emailInfo);
		try {
			NaverSMTP smtpServer = new NaverSMTP(); // 메일 전송 클래스 생성
			smtpServer.emailSending(emailInfo); // 전송
			System.out.print("이메일 전송 성공");
		} catch (Exception e) {
			System.out.print("이메일 전송 실패");
			e.printStackTrace();
		}
		
		String rejectMessage = "alert('거절 완료!!!');\n";
		rejectMessage  += "location.href='selectByIdx?party_id=" +joinVO.getParty_id()+ "&currentPage=" +currentPage+ "&job=article';";
		return rejectMessage;
	}


    
    
}
