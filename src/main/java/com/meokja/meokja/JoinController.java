	
package com.meokja.meokja;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meokja.api.NaverSMTP;
import com.meokja.dao.JoinDAO;
import com.meokja.dao.PartyDAO;
import com.meokja.service.JoinService;
import com.meokja.service.PartyService;
import com.meokja.vo.JoinList;
import com.meokja.vo.JoinVO;
import com.meokja.vo.MemberVO;
import com.meokja.vo.PartyList;
import com.meokja.vo.PartyVO;


@Controller
public class JoinController {
	
	private static final Logger logger = LoggerFactory.getLogger(JoinController.class);
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	JoinVO joinVO;
	
	@Autowired
	MemberVO user;
	
	@Autowired
	@Qualifier("partyVO")
	PartyVO vo;
	
	@Autowired
	JoinList joinList;
	@Autowired
	PartyList partyList;
	
	@Autowired
	JoinService joinService;
	
	@Autowired
	PartyService partyService;
	
	// 파티 참여
    @RequestMapping(value = "/joinInsert", method = RequestMethod.POST)
    public String joinInsert(HttpServletRequest request, HttpSession session, Model model, JoinVO joinVO) {
    	logger.info("JoinController의 joinInsert()");
    	int currentPage = Integer.parseInt(request.getParameter("currentPage"));
    	String joinInsert = joinService.joinInsert(session, joinVO);
    	
    	return joinInsert + "&currentPage=" + currentPage;
    }
	
    // 확인바람
	@RequestMapping(value = "/joinServlet", method = RequestMethod.POST)
	@ResponseBody
	public String repleServlet(HttpServletRequest request, Model model, PartyVO partyVO) {
		logger.info("JoinController의 joinServlet()");
		JoinDAO mapper = sqlSession.getMapper(JoinDAO.class);
		logger.info("{} line134", partyVO);
		
		// 세션에 저장된 회원정보
		HttpSession session = request.getSession();
		MemberVO user = (MemberVO) session.getAttribute("user");
		
		
// 수정할 코드
		int result = 0;
//		int result = mapper.selectById_Idx(reportVO);
		// 1있다. 0 없다.
		logger.info("{} line140", result);
		
		return String.valueOf(result);
	}
	
	// 모임 수락
	@RequestMapping(value = "/joinOK", method = RequestMethod.GET)
	public void joinOK(HttpServletRequest request, HttpServletResponse response, Model model, JoinVO joinVO) throws IOException {
		logger.info("JoinController의 joinOK()");
		logger.info("line102 {}", joinVO);
		
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		
		// 정보 가져오기
		joinVO = joinService.selectByJoin_id(joinVO);
        vo = partyService.selectByParty_id(joinVO.getParty_id());
		
        // 참여 승인
        String grantMessage = joinService.joinGrant(joinVO, vo, currentPage);
        printScriptMessage(response, grantMessage);
	}
	
	// 모임 거절
	@RequestMapping(value = "/joinNO", method = RequestMethod.GET)
	public void joinNO(HttpServletRequest request, HttpServletResponse response, Model model, JoinVO joinVO) throws IOException {
		logger.info("JoinController의 joinNO()");
		logger.info("line157 {}", joinVO);
		
		// 정보 가져오기
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		joinVO = joinService.selectByJoin_id(joinVO);
		vo = partyService.selectByParty_id(joinVO.getParty_id());
		
		// 참여 거절
		String rejectMessage = joinService.joinReject(joinVO, vo, currentPage);
		printScriptMessage(response, rejectMessage);
	}
	
	@RequestMapping(value = "/joinCHK", method = RequestMethod.POST)
	@ResponseBody
	public String joinCHK(HttpServletRequest request, HttpServletResponse response, Model model, JoinVO joinVO, HttpSession session) {
		logger.info("line205 {}", joinVO);
		
		JoinDAO mapper = sqlSession.getMapper(JoinDAO.class);
		user = (MemberVO) session.getAttribute("user");
		joinVO.setMember_id(user.getMember_id());
		int result = mapper.joinCHK(joinVO);
		
		return String.valueOf(result);
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
