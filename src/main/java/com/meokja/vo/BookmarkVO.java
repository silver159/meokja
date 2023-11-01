package com.meokja.vo;

import lombok.Data;

@Data
public class BookmarkVO {
	
	private int bookmark_id;	// 즐겨찾기 ID
	private int party_id; 		// 모임 ID
	private String member_id;	// 유저 ID
	
}
