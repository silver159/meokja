	
package com.meokja.meokja;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.meokja.service.PartyService;
import com.meokja.service.ScoreService;
import com.meokja.vo.MemberList;
import com.meokja.vo.MemberVO;
import com.meokja.vo.PartyVO;
import com.meokja.vo.ScoreVO;


@Controller
public class ScoreController {
	
	private static final Logger logger = LoggerFactory.getLogger(ScoreController.class);
	
	@Autowired
	MemberVO user;
	
	@Autowired
	MemberVO memberVO;
	
	@Autowired
	ScoreVO scoreVO;
	
	@Autowired
	MemberList memberList;
	
	@Autowired
	private ScoreService scoreService;
	
	@Autowired
	private PartyService partyService;
	
	@RequestMapping(value = "/scorePage", method = RequestMethod.POST)
	public String score(HttpServletRequest request, Model model, PartyVO partyVO, HttpSession session) {
		logger.info("ScoreController의 score()");
		
		user = (MemberVO) session.getAttribute("user");
		
		// 가져온 party_id값으로 모임정보 가져오기.		
		partyVO = partyService.score_selectByparty_id(partyVO.getParty_id());
		
		// 평가할 회원아이디 lsit
		ArrayList<String> id_list = new ArrayList<String>();
		
		// 모임장 아이디
		id_list.add(partyVO.getMember_id());
		
		logger.info("line 69{}", id_list);
		
		// 참가자 아이디
		memberList.setList(scoreService.scoreList(partyVO.getParty_id()));
		for (MemberVO vo : memberList.getList()) {
			id_list.add(vo.getMember_id());
		}
		// 평가는 나는 빼고
		id_list.remove(user.getMember_id());
		
		logger.info("81line {}", id_list);
		
		memberList.setList(scoreService.scoreMemeberList(id_list));
		
		logger.info("86line {}", memberList);
		
		// 파티정보
		model.addAttribute("vo", partyVO);
		// 평가할 회원정보
		model.addAttribute("scoreList", memberList);
		
		return "score";
	}
	
	@RequestMapping(value = "/score", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> score(Model model, @RequestBody HashMap<String, Object> data) {
		System.out.println("ScoreController의 score");
		logger.info("94line {}", data);
		
		List<String> member_idList = (List<String>) data.get("member_id");
		List<Integer> scoreList = (List<Integer>) data.get("score");
		int party_id = (Integer) data.get("party_id");
		
		logger.info("100line {}", party_id);
		logger.info("101line {}", member_idList);
		logger.info("102line {}", scoreList.get(0));
		for(int i = 0; i < scoreList.size(); i++) {
			scoreVO.setMember_id(member_idList.get(0));
			scoreVO.setScore(scoreList.get(0));
			scoreVO.setParty_id(party_id);
			scoreService.scoreInsert(scoreVO);
		}
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("result", "success");
		map.put("message", "평가 완료");
		return map;
	}
}
