package com.meokja.vo;

import lombok.Data;

@Data
public abstract class JoinMember {

	String member_id;	// 회원 ID
	String pw;			// 비밀번호
	String name;		// 이름
	String nickname;	// 별명
	int age;			// 나이
	String gender;		// 성별
	String jumin;	    // 주민번호
	String email;		// 이메일
	String phone;		// 전화번호	
	String photo;		// 프로필사진
	String postCode;	// 우편번호
	String address;		// 주소
	
}
