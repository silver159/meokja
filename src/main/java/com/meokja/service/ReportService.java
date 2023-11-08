package com.meokja.service;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meokja.dao.ReportDAO;
import com.meokja.vo.ReportVO;

@Service
public class ReportService {
	
	private static final Logger logger = LoggerFactory.getLogger(ReportService.class);
	
	@Autowired
	private SqlSession sqlSession;

	public int reportCount(ReportVO reportVO) {
		
		logger.info("ReportService의 reportCount()");
		
		ReportDAO mapper = sqlSession.getMapper(ReportDAO.class);
		int reportCount = mapper.reportCount(reportVO);
		logger.info("reportCount-{}",reportCount);
		return reportCount;
	}

	public String reportInsert(ReportVO reportVO, int currentPage) {
		
		logger.info("ReportService의 reportCount()");
		
		ReportDAO mapper = sqlSession.getMapper(ReportDAO.class);
		mapper.reportInsert(reportVO);
		String reportMessage = "alert('신고 완료!!!');\n";
		logger.info("{}", reportVO.getParty_id());
		logger.info("{}", currentPage);
		reportMessage += "location.href='list';";
		return reportMessage;
	}

}
