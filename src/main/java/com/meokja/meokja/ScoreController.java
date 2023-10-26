	
package com.meokja.meokja;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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

import com.meokja.dao.JoinDAO;
import com.meokja.dao.MemberDAO;
import com.meokja.dao.PartyDAO;
import com.meokja.dao.ScoreDAO;
import com.meokja.vo.JoinList;
import com.meokja.vo.JoinVO;
import com.meokja.vo.MemberList;
import com.meokja.vo.MemberVO;
import com.meokja.vo.PartyList;
import com.meokja.vo.PartyVO;


@Controller
public class ScoreController {
	
	private static final Logger logger = LoggerFactory.getLogger(ScoreController.class);
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	JoinVO joinVO;
	
	@Autowired
	MemberVO user;
	
	@Autowired
	MemberVO memberVO;
	
	@Autowired
	MemberList memberList;
	
	@RequestMapping(value = "/score", method = RequestMethod.POST)
	public String score(HttpServletRequest request, Model model, PartyVO partyVO, HttpSession session) {
		logger.info("ScoreController의 score()");
		ScoreDAO mapper = sqlSession.getMapper(ScoreDAO.class);
		
		user = (MemberVO) session.getAttribute("user");
		
		// 가져온 party_id값으로 모임정보 가져오기.		
		partyVO = mapper.score_selectByparty_id(partyVO.getParty_id());
		
	// 평가할 회원아이디 lsit
		ArrayList<String> id_list = new ArrayList<String>();
		
		// 모임장 아이디
		id_list.add(partyVO.getMember_id());
		
		logger.info("line 69{}", id_list);
		
		// 참가자 아이디
		memberList.setList(mapper.scoreList(partyVO.getParty_id()));
		for (MemberVO vo : memberList.getList()) {
			id_list.add(vo.getMember_id());
		}
		// 평가는 나는 빼고
		id_list.remove(user.getMember_id());
		
		logger.info("81line {}", id_list);
		
		memberList.setList(mapper.scoreMemeberList(id_list));
		
		logger.info("86line {}", memberList);
		
		// 파티정보
		model.addAttribute("vo", partyVO);
		// 평가할 회원정보
		model.addAttribute("scoreList", memberList);
		
		
		return "score";
	}

	
	private PrintWriter getPrintWriter(HttpServletResponse response) throws IOException {
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter out = response.getWriter();
	    return out;
	}
}
