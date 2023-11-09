package com.meokja.meokja;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meokja.service.BookmarkService;
import com.meokja.vo.MemberVO;
import com.meokja.vo.PartyList;

@Controller
public class BookmarkController {
	
	private static final Logger logger = LoggerFactory.getLogger(BookmarkController.class);
	
	@Autowired
	private MemberVO user;
	
	@Autowired
	private BookmarkService bookmarkService;
	
	@RequestMapping("/bookmark")
	public String bookmark(HttpServletRequest request, Model model, HttpSession session, HttpServletResponse response) {
		logger.info("BookmarkController의 bookmark");
		user = (MemberVO) session.getAttribute("user");
		logger.info("line33 {}", user);
		
		// 즐겨찾기한 모임 리스트
		PartyList bookmarkList =  new PartyList();
		bookmarkList.setList(bookmarkService.bookmarkList(user));
		
		// 저장한 모임 리스트
//		PartyList saveList =  new PartyList();
//		saveList.setList(bookmarkService.saveList(user));
		
		// 즐겨찾기한 모임 리스트
		model.addAttribute("bookmarkList", bookmarkList);
		
		// 저장한 모임 리스트
//		model.addAttribute("saveList", saveList);
		
		return "bookmark";
	}
	
	@RequestMapping(value = "/bookmarkInsert", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> bookmarkInsert(HttpSession session, @RequestBody String data) {
	    logger.info("BookmarkController의 bookmarkInsert()");
	    user = (MemberVO) session.getAttribute("user");
	    
	    Map<String, String> map = new HashMap<String, String>();
	    
	    // ObjectMapper 객체 생성
 		ObjectMapper objectMapper = new ObjectMapper();
 		
 		try {
 		    // JSON 문자열 파싱
 		    Map<String, String> jsonMap = objectMapper.readValue(data, new TypeReference<Map<String, String>>() {});

 		    // "delIds" 키에 해당하는 값을 추출
 		    int party_id = Integer.parseInt(jsonMap.get("party_id"));
 		    logger.info("line77 - {}",party_id);
 		    bookmarkService.bookmarkInsert(party_id, user.getMember_id());
 		    
 			map.put("result", "success");
 			map.put("message", "데이터 처리 완료");
 			return map;
 		} catch (Exception e) { 
 			map.put("result", "error");
 		    map.put("message", "데이터 처리 실패");
 		}
	    return map;
	}

	
	@RequestMapping(value = "/bookmarkDelete", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, String> bookmarkDelete(HttpSession session, @RequestBody String data) {
		logger.info("BookmarkController의 bookmarkDelete");
		user = (MemberVO) session.getAttribute("user");
		
		Map<String, String> map = new HashMap<String, String>();
	    
	    // ObjectMapper 객체 생성
 		ObjectMapper objectMapper = new ObjectMapper();
 		
 		try {
 		    // JSON 문자열 파싱
 		    Map<String, String> jsonMap = objectMapper.readValue(data, new TypeReference<Map<String, String>>() {});

 		    // "delIds" 키에 해당하는 값을 추출
 		    int party_id = Integer.parseInt(jsonMap.get("party_id"));
 		    logger.info("line77 - {}",party_id);
 		    bookmarkService.bookmarkDelete(party_id, user.getMember_id());
 		    
 			map.put("result", "success");
 			map.put("message", "데이터 처리 완료");
 			return map;
 		} catch (Exception e) { 
 			map.put("result", "error");
 		    map.put("message", "데이터 처리 실패");
 		}
	    return map;
	}
}
