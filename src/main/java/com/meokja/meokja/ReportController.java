package com.meokja.meokja;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meokja.dao.PartyDAO;
import com.meokja.dao.ReportDAO;
import com.meokja.service.ReportService;
import com.meokja.vo.MemberVO;
import com.meokja.vo.ReportVO;


@Controller
public class ReportController {
	
	private static final Logger logger = LoggerFactory.getLogger(ReportController.class);
	
	@Autowired
	private ReportService reportService;
	
	@RequestMapping("/reportInsert")
	public void reportInsert(HttpServletRequest request, HttpServletResponse response, HttpSession session, ReportVO reportVO) throws IOException {
		logger.info("ReportController의 reportInsert()");
		
		// 회원정보
		MemberVO user = (MemberVO) session.getAttribute("user");
		int currentPage = Integer.parseInt( request.getParameter("currentPage"));
		reportVO.setMember_id(user.getMember_id());
		logger.info("48line {}",reportVO);
		
		printScriptMessage(response, reportService.reportInsert(reportVO, currentPage));
	}
	
    // 공통 메소드
    private void printScriptMessage(HttpServletResponse response, String scriptMessage) throws IOException {
        response.setContentType("text/html; charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<script>");
        out.println(scriptMessage);
        out.println("</script>");
        out.flush();
    }
}
