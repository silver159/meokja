package com.meokja.vo;

import lombok.Data;

@Data
public class ScoreVO {
	
	private int score_id;		// 식별자
	private int party_id;		// 모임 ID
	private String member_id;	// 회원 ID
	private int score;			// 평가점수
}
