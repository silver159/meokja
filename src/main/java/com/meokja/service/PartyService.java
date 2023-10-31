package com.meokja.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meokja.dao.PartyDAO;
import com.meokja.vo.MemberVO;
import com.meokja.vo.Param;
import com.meokja.vo.PartyVO;

@Service
public class PartyService {
	
	private static final Logger logger = LoggerFactory.getLogger(PartyService.class);
	
	@Autowired
	private SqlSession sqlSession;
	
	public PartyVO selectByParty_id(int party_id) {
		
		logger.info("PartyService의 selectByParty_id()");
		
		PartyDAO mapper = sqlSession.getMapper(PartyDAO.class);
		
		return mapper.selectByParty_id(party_id);
	}

	public int selectCount() {
		
		logger.info("PartyService의 selectCount()");
		
		PartyDAO mapper = sqlSession.getMapper(PartyDAO.class);
		return mapper.selectCount();
	}

	public ArrayList<PartyVO> selectList(HashMap<String, Integer> hmap) {
		
		logger.info("PartyService의 selectList()");
		
		PartyDAO mapper = sqlSession.getMapper(PartyDAO.class);
		return mapper.selectList(hmap);
	}

	public ArrayList<PartyVO> selectSlider() {
		
		logger.info("PartyService의 selectSlider()");
		
		PartyDAO mapper = sqlSession.getMapper(PartyDAO.class);
		return mapper.selectSlider();
	}

	public int selectCountMulti(Param param) {
		
		logger.info("PartyService의 selectCountMulti()");
		
		PartyDAO mapper = sqlSession.getMapper(PartyDAO.class);
		return mapper.selectCountMulti(param);
	}

	public ArrayList<PartyVO> selectListMulti(Param param) {
		
		logger.info("PartyService의 selectListMulti()");
		
		PartyDAO mapper = sqlSession.getMapper(PartyDAO.class);
		return mapper.selectListMulti(param);
	}

	public void insert(PartyVO partyVO) {
		
		logger.info("PartyService의 insert()");
		
		PartyDAO mapper = sqlSession.getMapper(PartyDAO.class);
		mapper.insert(partyVO);
	}

	public ArrayList<PartyVO> create_myList(MemberVO user) {
		
		logger.info("PartyService의 create_myList()");
		
		PartyDAO mapper = sqlSession.getMapper(PartyDAO.class);
		return mapper.create_myList(user);
	}

	public ArrayList<PartyVO> join_myList(MemberVO user) {
		
		logger.info("PartyService의 join_myList()");
		
		PartyDAO mapper = sqlSession.getMapper(PartyDAO.class);
		return mapper.join_myList(user);
	}

	public ArrayList<PartyVO> score_myList(MemberVO user) {
		
		logger.info("PartyService의 score_myList()");
		
		PartyDAO mapper = sqlSession.getMapper(PartyDAO.class);
		return mapper.score_myList(user);
	}

	public String partyUpdate(PartyVO partyVO, int currentPage) {
		
		logger.info("PartyService의 partyUpdate()");
		
		PartyDAO mapper = sqlSession.getMapper(PartyDAO.class);
		mapper.partyUpdate(partyVO);
		String partyUpdateMessage = "alert('수정완료!!!');\n";
		partyUpdateMessage += "location.href='selectByIdx?party_id="+partyVO.getParty_id()+"&currentPage="+currentPage+"&job=article';";
		return partyUpdateMessage;
	}

	public String partyDelete(PartyVO partyVO) {
		
		logger.info("PartyService의 partyDelete()");
		
		PartyDAO mapper = sqlSession.getMapper(PartyDAO.class);
		mapper.partyDelete(partyVO);
		String partyDeleteMessage = "alert('삭제완료!!!');\n";
		partyDeleteMessage += "location.href='list';";
		return partyDeleteMessage;
	}

	public PartyVO score_selectByparty_id(int party_id) {
		
		logger.info("PartyService의 score_selectByparty_id()");
		
		PartyDAO mapper = sqlSession.getMapper(PartyDAO.class);
		return mapper.score_selectByparty_id(party_id);
	}
	
	
	
}
