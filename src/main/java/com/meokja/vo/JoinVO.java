package com.meokja.vo;

import java.util.Date;

import lombok.Data;

@Data
public class JoinVO extends JoinMember {
	
	private int join_id; 			// 참여 ID
	private int party_id; 			// 모임 ID
	private String member_id;		// 생성자 ID
	private String contents;		// 글내용
	private Date created_at;   		// 작성일
	private String status = "대기";	// 수락 이후 '수락'으로 변경
	
	private int limitNum;         // 인원제한
	private int count;            // 참여인원
}
