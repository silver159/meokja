package com.meokja.service;

import java.util.ArrayList;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meokja.dao.MemberDAO;
import com.meokja.vo.JoinVO;
import com.meokja.vo.MemberVO;

@Service
public class MemberService {

	private static final Logger logger = LoggerFactory.getLogger(MemberService.class);
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private MemberVO user;

	public int IDCheck(MemberVO memberVO) {
		
		logger.info("MemberService의 IDCheck()");
		
		MemberDAO mapper = sqlSession.getMapper(MemberDAO.class);
		return mapper.IDCheck(memberVO);
	}

	public String memberInsert(MemberVO memberVO) {
		
		logger.info("MemberService의 IDCheck()");
		
		MemberDAO mapper = sqlSession.getMapper(MemberDAO.class);
		mapper.memberInsert(memberVO);
		String memberInsertMessage = "alert('회원 가입을 축하드립니다~!');\n";
		memberInsertMessage += "location.href='loginPage';";
		return memberInsertMessage;
	}

	public void myProfileUpdate(MemberVO memberVO) {
		
		logger.info("MemberService의 myProfileUpdate()");
		
		MemberDAO mapper = sqlSession.getMapper(MemberDAO.class);
		mapper.myProfileUpdate(memberVO);
	}
	
	public String selectById(String member_id, HttpSession session) {
		
		logger.info("MemberService의 selectById()");
		
		MemberDAO mapper = sqlSession.getMapper(MemberDAO.class);
		user = mapper.selectById(member_id);
		session.setAttribute("user", user);
		String profileUpdateMessage = "alert('" + user.getName() + " 님의 개인 정보가 수정되었습니다 ~!');\n";
		profileUpdateMessage += "location.href='list';";
		return profileUpdateMessage;
	}

	public MemberVO selectById(String member_id) {
		
		logger.info("MemberService의 selectById()");
		
		MemberDAO mapper = sqlSession.getMapper(MemberDAO.class);
		return mapper.selectById(member_id);
	}

	public ArrayList<MemberVO> joinMemberList(JoinVO joinVO) {

		logger.info("MemberService의 joinMemberList()");
		MemberDAO mapper = sqlSession.getMapper(MemberDAO.class);
		return mapper.joinMemberList(joinVO);
	}
}
