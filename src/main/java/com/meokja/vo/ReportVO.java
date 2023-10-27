package com.meokja.vo;

import java.util.Date;

import lombok.Data;

@Data
public class ReportVO {
	
	private int report_id;		// 신고 ID
	private String member_id;		// 생성자 ID
	private int party_id; 		// 모임 ID
	private Date created_at;   	// 작성일
}
