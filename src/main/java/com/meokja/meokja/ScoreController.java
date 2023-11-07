	
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

import com.meokja.service.MemberService;
import com.meokja.service.PartyService;
import com.meokja.service.ScoreService;
import com.meokja.vo.JoinVO;
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
	JoinVO joinVO;
	
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
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value = "/scorePage", method = RequestMethod.POST)
	public String scorePage(Model model, PartyVO partyVO, HttpSession session) {
    	logger.info("ScoreController의 scorePage()");
      
    	// 가져온 party_id값으로 모임정보 가져오기.      
    	partyVO = scoreService.score_selectByparty_id(partyVO.getParty_id());
      
    	user = (MemberVO) session.getAttribute("user");
      
    	joinVO.setParty_id(partyVO.getParty_id());
    	joinVO.setMember_id(user.getMember_id());
      
    	// 모임원 정보
    	ArrayList<MemberVO> joinMember = memberService.joinMemberList(joinVO);

    	// 모임장 정보
	    MemberVO master = memberService.selectById(partyVO.getMember_id());
	      
	    // 모임원 정보와 모임장 정보를 합친 후 memberList에 담고 브라우저에 데이터를 넘긴다.  
	    joinMember.add(master);
	    memberList.setList(joinMember);
	      
	    // 파티정보
	    model.addAttribute("vo", partyVO);
	    // 평가할 회원정보
	    model.addAttribute("scoreList", memberList);
	      
	     return "score";
    }
	
	@RequestMapping(value = "/score", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> score(Model model, @RequestBody HashMap<String, Object> data) {
		logger.info("ScoreController의 score()");
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
	
	@RequestMapping(value = "/myScore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> myScore(Model model, @RequestBody String member_id) {
		logger.info("ScoreController의 myScore()");

		
		double avg = scoreService.avgScore(member_id);
		logger.info("127line {}", avg);
		
		double prime = avg - Math.floor(avg);
		logger.info("131line {}", prime);
		
		double myScore = Math.floor(avg) + (prime <= 0.5 ?  0 : 0.5);
		logger.info("135line {}", myScore);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("result", "success");
		map.put("myScore", Double.toString(myScore));
		
		return map;
	}
}
