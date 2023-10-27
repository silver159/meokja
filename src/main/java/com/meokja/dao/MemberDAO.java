package com.meokja.dao;

import com.meokja.vo.MemberVO;

public interface MemberDAO {
	
	int IDCheck(MemberVO memberVO);

	void memberInsert(MemberVO memberVO);

	MemberVO selectById(String member_id);

	String selectByName(MemberVO memberVO);

	MemberVO pwSerch(MemberVO memberVO);

	void myProfileUpdate(MemberVO memberVO);

}
