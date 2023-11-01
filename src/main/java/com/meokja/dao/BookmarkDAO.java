package com.meokja.dao;

import java.util.ArrayList;
import java.util.Map;

import com.meokja.vo.MemberVO;
import com.meokja.vo.PartyVO;

public interface BookmarkDAO {

	ArrayList<PartyVO> bookmarkList(MemberVO user);

	int bookmarkChk(Map<String, Object> map);

	void bookmarkInsert(Map<String, Object> map);

	void bookmarkDelete(Map<String, Object> map);

}
