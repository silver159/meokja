package com.meokja.service;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meokja.dao.PartyDAO;
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
	
}
