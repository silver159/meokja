package com.meokja.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meokja.dao.BookmarkDAO;
import com.meokja.vo.MemberVO;
import com.meokja.vo.PartyVO;

@Service
public class BookmarkService {
	
	private static final Logger logger = LoggerFactory.getLogger(BookmarkService.class);
	
	@Autowired
	private SqlSession sqlSession;
	
	public ArrayList<PartyVO> bookmarkList(MemberVO user) {
		
		logger.info("BookmarkService의 bookmarkList()");
		
		BookmarkDAO mapper = sqlSession.getMapper(BookmarkDAO.class);		
		return mapper.bookmarkList(user);
	}

	public boolean bookmarkChk(int party_id, String member_id) {
		
		logger.info("BookmarkService의 bookmarkList()");
		
		BookmarkDAO mapper = sqlSession.getMapper(BookmarkDAO.class);
		
		// HashMap 생성 및 변수 추가
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("party_id", party_id);
		map.put("member_id", member_id);
		
		int bookmarkCount = mapper.bookmarkChk(map);
		boolean bookmarkChk = false;
		if(bookmarkCount != 0) {
			bookmarkChk = true;
		}
		
		return bookmarkChk;
	}

	public void bookmarkInsert(int party_id, String member_id) {
		
		logger.info("BookmarkService의 bookmarkInsert()");
		
		BookmarkDAO mapper = sqlSession.getMapper(BookmarkDAO.class);
		
		// HashMap 생성 및 변수 추가
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("party_id", party_id);
		map.put("member_id", member_id);
		mapper.bookmarkInsert(map);
	}

	public void bookmarkDelete(int party_id, String member_id) {
		
		logger.info("BookmarkService의 bookmarkDelete()");
		
		BookmarkDAO mapper = sqlSession.getMapper(BookmarkDAO.class);
		
		// HashMap 생성 및 변수 추가
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("party_id", party_id);
		map.put("member_id", member_id);
		mapper.bookmarkDelete(map);		
	}

}
