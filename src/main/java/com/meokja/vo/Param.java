package com.meokja.vo;

import lombok.Data;

@Data
public class Param {
	
	private int currentPage = 1;
	
	private int startNo;
	private int endNo;
	private int minLimitAge = 19;
	private int maxLimitAge = 80;
	private String condition;
	private String item;
	private String food_category;
	private String local_category;
//	신고용 
	private int reportIdx;
	private String reportId;
	
}
