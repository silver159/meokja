package com.meokja.dao;

import java.util.ArrayList;

import com.meokja.vo.MemberVO;
import com.meokja.vo.PartyVO;
import com.meokja.vo.ScoreVO;

public interface ScoreDAO {

	ArrayList<MemberVO> scoreList(int party_id);

	ArrayList<MemberVO> scoreMemeberList(ArrayList<String> member_idList);

	void scoreInsert(ScoreVO scoreVO);

}
