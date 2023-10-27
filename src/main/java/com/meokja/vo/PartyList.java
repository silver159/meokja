package com.meokja.vo;

import java.util.ArrayList;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PartyList {
	
	ArrayList<PartyVO> list = new ArrayList<PartyVO>();
	
	private	int pageSize = 8;		// 
	private	int totalCount = 0; 	// 
	private	int totalPage = 0; 		// 
	private	int currentPage = 1; 	// 
	private	int startNo = 0; 		// 
	private	int endNo = 0; 			// 
	private	int startPage = 0; 		// 
	private	int endPage = 0; 		// 
	
	
	public void initPartyList(int totalCount, int currentPage) {
		
		this.totalCount = totalCount;
		this.currentPage = currentPage;
		calculator();
	}	
	
	// pageSize, totalCount, currentPage
	private void calculator() {
		totalPage = (totalCount - 1) / pageSize + 1;
		currentPage = currentPage > totalPage ? totalPage : currentPage;
 		startNo = (currentPage - 1) * pageSize + 1;
		endNo = startNo + pageSize - 1;
		endNo = endNo > totalCount ? totalCount : endNo;
		startPage = (currentPage-1)/10 * 10  + 1;
		endPage = startPage + 9;
		endPage = endPage > totalPage ? totalPage : endPage;
	}
}
