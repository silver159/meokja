package com.meokja.service;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meokja.dao.JoinDAO;
import com.meokja.vo.JoinVO;
import com.meokja.vo.MemberVO;

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
	
    
    
}
