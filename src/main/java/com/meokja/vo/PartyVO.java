package com.meokja.vo;

import java.util.Date;

import lombok.Data;

@Data
public class PartyVO {
	
	private int party_id; 			// 모임 ID
	private String member_id;		// 생성자 ID
	private String food_category;  	// 음식 카테고리
	private String local_category; 	// 지역 카테고리
	private String subject;  		// 글제목
	private String contents;		// 글내용
	private String map;		      	// 가게 맵 키위드
	private Date mealed_at;    		// 식사일
	private Date created_at;   		// 작성일
	private String thumbnail;   	// 썸네일
	private int limitNum = 2;		// 인원수 제한
	private int reportCount;
	private String status = "일반";	// 삭제 후 '삭제'으로 변경
	
}
