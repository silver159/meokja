package com.meokja.service;

import java.util.ArrayList;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meokja.dao.ScoreDAO;
import com.meokja.vo.MemberVO;
import com.meokja.vo.PartyVO;

@Service
public class ScoreService {
	
	private static final Logger logger = LoggerFactory.getLogger(ScoreService.class);
	
	@Autowired
    private SqlSession sqlSession;
	
	public ArrayList<MemberVO> scoreList(int party_id) {
		
		logger.info("ScoreService의 scoreList()");
		
		ScoreDAO mapper = sqlSession.getMapper(ScoreDAO.class);		
		return mapper.scoreList(party_id);		
	}

	public ArrayList<MemberVO> scoreMemeberList(ArrayList<String> id_list) {
		
		logger.info("ScoreService의 scoreMemeberList()");
		
		ScoreDAO mapper = sqlSession.getMapper(ScoreDAO.class);		
		return mapper.scoreMemeberList(id_list);		
		
	}
	
}
