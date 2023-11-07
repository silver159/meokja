package com.meokja.vo;

import lombok.Data;

@Data
public class MemberVO {
	
	private String member_id;		// 회원 ID
	private String pw;				// 비밀번호
	private String name;			// 이름
	private String nickname;		// 별명
	private int age;				// 나이
	private String gender;			// 성별
	private String jumin;	    	// 주민번호
	private String email;			// 이메일
	private String phone;			// 전화번호	
	private String photo;			// 프로필사진
	private String postCode;		// 우편번호
	private String address;			// 주소
	private String detailAddress;	// 상세주소
}
