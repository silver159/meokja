package com.meokja.meokja;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.meokja.dao.JoinDAO;
import com.meokja.dao.MemberDAO;
import com.meokja.dao.PartyDAO;
import com.meokja.dao.ReportDAO;
import com.meokja.service.BookmarkService;
import com.meokja.service.JoinService;
import com.meokja.service.MemberService;
import com.meokja.service.PartyService;
import com.meokja.service.ReportService;
import com.meokja.vo.JoinList;
import com.meokja.vo.JoinVO;
import com.meokja.vo.MemberVO;
import com.meokja.vo.Param;
import com.meokja.vo.PartyList;
import com.meokja.vo.PartyVO;
import com.meokja.vo.ReportVO;


@Controller
public class PartyController {
	
	private static final Logger logger = LoggerFactory.getLogger(PartyController.class);

	@Autowired
	private MemberVO user;
	@Autowired
	private PartyVO partyVO;
	@Autowired
	private MemberVO memberVO;
	@Autowired
	private ReportVO reportVO;
	@Autowired
	private JoinList joinList;
	@Autowired
	private PartyList partyList;
	
	@Autowired
	private PartyService partyService;
	
	@Autowired
	private MemberService memberService;
	
	@Autowired
	private ReportService reportService;
	
	@Autowired
	private JoinService joinService;
	
	@Autowired
	private BookmarkService bookmarkService;
	
	// 검색어 없는 요청
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String listGET(Model model, Param param) {
		
		logger.info("line73 {}", param);
		// 검색어 있는 경우
		if(param.getLocal_category() != null && param.getLocal_category() != "") {
			// 검색버튼 누른 검색어 요청으로 보내준다.
			listPOST(model, param);
			return"list";
		}
		// 이제부터 검색어 없는 요청
		logger.info("PartyController의 listGET()");
		logger.info("line46 {}", param);

		// 검색어 없이 처음 list를 요청한 경우
		// 페이징 작업
		int currentPage = param.getCurrentPage();
		int totalCount = partyService.selectCount();
		partyList.initPartyList(totalCount, currentPage);
		HashMap<String, Integer> hmap = new HashMap<String, Integer>();
		hmap.put("startNo", partyList.getStartNo());
		hmap.put("endNo", partyList.getEndNo());
		partyList.setList(partyService.selectList(hmap));

		logger.info("line67 {}", partyList);
		
		model.addAttribute("partyList", partyList);
		model.addAttribute("sliderList", sliderList(partyService));
		model.addAttribute("currentPage", currentPage);
		return"list";
	}
	
	// 검색버튼 누른 검색어 요청
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public String listPOST(Model model, Param param) {
		
		logger.info("PartyController의 listPOST()");
		logger.info("line80 {}", param);
		
		int currentPage = param.getCurrentPage();
		int totalCount = partyService.selectCountMulti(param);
		logger.info("line86 {}", totalCount);
		
		partyList.initPartyList(totalCount, currentPage);
		param.setStartNo(partyList.getStartNo());
		param.setEndNo(partyList.getEndNo());
		partyList.setList(partyService.selectListMulti(param));
		logger.info("totalCount : {}", partyList);
		
		model.addAttribute("partyList", partyList);
		model.addAttribute("sliderList", sliderList(partyService));
		model.addAttribute("location", param.getLocal_category());
		model.addAttribute("condition", param.getCondition());
		model.addAttribute("category", param.getFood_category());
		model.addAttribute("item", param.getItem());
		
		return"list";
	}

	// 선택한 모임에 들어가기
	@RequestMapping("/selectByIdx")
	public String selectByIdx(HttpServletResponse response, HttpServletRequest request, Model model, HttpSession session) throws IOException {
		logger.info("PartyController의 selectByIdx()");
		user = (MemberVO) session.getAttribute("user");
//		로그인이 되어있지 않을 경우
		if (user == null) {
			String warnnigMessage = "alert('로그인 후 이용해주세요.');\n";
			warnnigMessage += "location.href='loginPage';";
			printScriptMessage(response, warnnigMessage);
		}else {
			
			// 넘어오는 데이터 2가지 받기		
			int currentPage = Integer.parseInt(request.getParameter("currentPage"));
			int	party_id = Integer.parseInt(request.getParameter("party_id"));
			System.out.println(party_id);
			// 메인글 1건을 얻어오는 메소드를 호출한다.	
			partyVO = partyService.selectByParty_id(party_id);
			
			// 메인글 1건의 종속한 Join List를 얻어온다.
			joinList.setList(joinService.selectJoinList(party_id));
			logger.info("line162 {}", joinList);
			
			// 메인글의 모임장 정보를 가져온다.
			logger.info("line165 {}", partyVO);
			memberVO = memberService.selectById(partyVO.getMember_id());
			logger.info("line162 {}", memberVO);
			
			// 메인글의 내가 신고한 내역이 있는지 확인한다.
			reportVO.setMember_id(user.getMember_id());
			reportVO.setParty_id(party_id);
			logger.info("line167 {}", reportVO);
			
			// 메인글 1건의 종속한 report DB 중 회원정보한 일치한 report 조회
			int reportCount = reportService.reportCount(reportVO);
			logger.info("line175 {}", reportCount);
			String isReport = reportCount == 0 ? "N" : "Y";
			logger.info("{} line177", isReport);
			
			// 즐겨찾기 여부 확인힌다.
			boolean bookmarkChk = bookmarkService.bookmarkChk(party_id, user.getMember_id());
			
			model.addAttribute("master", memberVO);
			model.addAttribute("isReport", isReport);
			model.addAttribute("joinList", joinList);
			model.addAttribute("vo", partyVO);
			model.addAttribute("bookmarkChk", bookmarkChk);
			model.addAttribute("currentPage", currentPage);
			model.addAttribute("enter", "\r\n");
		}
		
		String job = request.getParameter("job");
		return job;
	}
	
	// 모임 생성
	@RequestMapping("/partyInsert")
	public String partyInsert(MultipartHttpServletRequest request, Model model, HttpSession session, PartyVO partyVO) {
		logger.info("PartyController의 partyInsert()");
		logger.info("{}", partyVO);
		
		// 저장 위치 지정
		String rootUplordDir = "C:" + File.separator + "upload" + File.separator + "thumbnail"; // C:\Upload\thumbnail
		// 파일 저장
		Iterator<String> iterator = request.getFileNames();
		MultipartFile multipartFile = null;
		String uploadFilename = iterator.next();
		multipartFile = request.getFile(uploadFilename);
//	    logger.info("uploadFilename: {}", uploadFilename);
		String originalName = multipartFile.getOriginalFilename();
//	    logger.info("originalName: {}", originalName);
		String photo = uploadFile(originalName);
		logger.info("photo: {}", photo);
		
		if(originalName != null && originalName.length() != 0) {
			try {
				multipartFile.transferTo(new File(rootUplordDir + File.separator + photo));
			} catch (Exception e) {}
		}
		
		logger.info("{}", partyVO);
		String dateObject1 = request.getParameter("dateObject1");
		String dateObject2 = request.getParameter("dateObject2"); 
		String combinedDateTimeString1 = dateObject1 + " " + dateObject2;
		SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date mealDate = null;
		try {
			mealDate = dateTimeFormat.parse(combinedDateTimeString1);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		partyVO.setMealed_at(mealDate);
		partyVO.setThumbnail(photo);
	    logger.info("{}", partyVO);
		
		partyService.insert(partyVO);
		
		user = (MemberVO) session.getAttribute("user");
		
		return "redirect:list";
	}
	
	// 유저별 모임 리스트
	@RequestMapping("/mylist")
	public String mylist(HttpServletRequest request, Model model, HttpSession session) {
		
		logger.info("PartyController의 mylist()");
		user = (MemberVO) session.getAttribute("user");
		logger.info("line247 {}", user);
		
		// 생성한 모임 리스트
		PartyList list_create = new PartyList();
		list_create.setList(partyService.create_myList(user));
		logger.info("line252 {}", list_create);
		
		// 참여한 모임 리스트
		PartyList list_join = new PartyList();
		list_join.setList(partyService.join_myList(user));
		logger.info("line257 {}", list_join);
		
		// 평가할 모임 리스트
		PartyList list_score = new PartyList();
		list_score.setList(partyService.score_myList(user));
		
		logger.info("line263 {}", list_score);
		
		// 생성한 모임 리스트
		model.addAttribute("list_create", list_create);
		// 참여한 모임 리스트
		model.addAttribute("list_join", list_join);
		// 평가할 모임 리스트
		model.addAttribute("list_score", list_score);
		
		return "mylist";
	}
	
	// 모임 수정
	@RequestMapping("/partyUpdate")
	public void partyUpdate(HttpServletResponse response, HttpServletRequest request, PartyVO partyVO) throws IOException {
		logger.info("PartyController의 partyUpdate()");
		logger.info("line272 {}", partyVO);
		
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		
		printScriptMessage(response, partyService.partyUpdate(partyVO, currentPage));
	}
	
	// 모임 삭제
	@RequestMapping("/partyDelete")
	public void partyDelete(HttpServletResponse response, HttpServletRequest request, PartyVO partyVO) throws IOException {
		logger.info("PartyController의 partyDelete()");
		
		printScriptMessage(response, partyService.partyDelete(partyVO));
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
	
    // UUID
	private String uploadFile(String originalName) {
		UUID uuid = UUID.randomUUID();
		String savedName =  uuid.toString()+"_"+originalName;
	    return savedName;
	}
	
	// list 상단의 슬라이더 생성 메소드
	private PartyList sliderList(PartyService partyService) {
		PartyList list = new PartyList();
		list.setList(partyService.selectSlider());
	    return list;
	}
}
