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
import com.meokja.vo.ScoreVO;

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

	public void scoreInsert(ScoreVO scoreVO) {
		
		logger.info("ScoreService의 scoreInsert()");
		
		ScoreDAO mapper = sqlSession.getMapper(ScoreDAO.class);	
		mapper.scoreInsert(scoreVO);
	}

	public double avgScore(String member_id) {
		
		logger.info("ScoreService의 avgScore()");
		ScoreDAO mapper = sqlSession.getMapper(ScoreDAO.class);	
		return mapper.avgScore(member_id);
	}

	public PartyVO score_selectByparty_id(int party_id) {

		logger.info("ScoreService의 score_selectByparty_id()");
		ScoreDAO mapper = sqlSession.getMapper(ScoreDAO.class);
		return mapper.score_selectByparty_id(party_id);
	}
	
}
