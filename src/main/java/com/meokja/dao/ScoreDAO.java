package com.meokja.dao;

import java.util.ArrayList;

import com.meokja.vo.MemberVO;
import com.meokja.vo.PartyVO;

public interface ScoreDAO {

	ArrayList<MemberVO> scoreList(int party_id);

	ArrayList<MemberVO> scoreMemeberList(ArrayList<String> member_idList);

	PartyVO score_selectByparty_id(int party_id);


}
