package com.meokja.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meokja.dao.PartyDAO;
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
	
	
	
}
