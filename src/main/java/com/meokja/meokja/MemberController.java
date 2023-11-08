package com.meokja.meokja;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.meokja.dao.MemberDAO;
import com.meokja.service.MemberService;
import com.meokja.vo.MemberVO;


@Controller
public class MemberController {
	
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private MemberVO mo;
	
	@Autowired
	private MemberService memberService;
	
	@RequestMapping(value = "/memberServlet", method = RequestMethod.POST)
	@ResponseBody
	public String memberServlet(HttpServletRequest request, Model model, HttpServletResponse response, MemberVO memberVO, HttpSession session) {
		
		logger.info("PageController의 memberServlet()");
        logger.info("{} line47", memberVO);
        
        int result = memberService.IDCheck(memberVO);
        // 1있다. 0 없다.
        logger.info("{} line129", result);
        
        return String.valueOf(result);
	}
	
	// 회원 가입
	@RequestMapping("/member")
	public void member(MultipartHttpServletRequest request, Model model, MemberVO memberVO, HttpServletResponse response) throws IOException {
		
		logger.info("MemberController의 member()");
		logger.info("{} line60", memberVO);
		
		// 사진 저장 경로 지정
		String rootUplordDir = "C:" + File.separator + "upload" + File.separator + "memberphoto"; // C:\Upload\memberphoto
		logger.info("uploadDirectory: {}", rootUplordDir);
		
		// 사진 저장 & 이름 지정
		Iterator<String> iterator = request.getFileNames();
	    MultipartFile multipartFile = null;
	    String uploadFilename = iterator.next();
	    multipartFile = request.getFile(uploadFilename);
	    logger.info("aaa: {}", multipartFile);
//	    logger.info("uploadFilename: {}", uploadFilename);
	    String originalName = multipartFile.getOriginalFilename();
	    logger.info("originalName: {}", originalName);
	    
	    String photo = uploadFile(originalName);
	    if(originalName != null && originalName.length() != 0) {
	    	try {
	    		multipartFile.transferTo(new File(rootUplordDir + File.separator + photo));
	        } catch (Exception e) {}
	    } else {
	    	// 사진 없을 시 기본 사진 지정
	    	photo = "default.jpg";
	    }
	    
	    // 사진 이름 저장
	    memberVO.setPhoto(photo);
	    
	    String memberInsertMessage = memberService.memberInsert(memberVO);
		
        printScriptMessage(response, memberInsertMessage);
	}
	
	// 회원정보 수정
	@RequestMapping("/myProfileOK")
	public void myProfileOK(MultipartHttpServletRequest request, HttpSession session, Model model, MemberVO memberVO, HttpServletResponse response) throws IOException {
		
		logger.info("MemberController의 myProfileOK()");
		logger.info("{} line99", memberVO);
		
		// 사진 저장 경로 지정
		String rootUplordDir = "C:" + File.separator + "upload" + File.separator + "memberphoto"; // C:\Upload\memberphoto
		
		// 사진 저장 & 이름 지정
		Iterator<String> iterator = request.getFileNames();
		MultipartFile multipartFile = null;
		String uploadFilename = iterator.next();
		multipartFile = request.getFile(uploadFilename);
		String originalName = multipartFile.getOriginalFilename();
		logger.info("originalName: {}", originalName);

		String photo = uploadFile(originalName);
		String defaultImgCheck = request.getParameter("defaultImgCheck");
		if(originalName != null && originalName.length() != 0) {
			try {
				multipartFile.transferTo(new File(rootUplordDir + File.separator + photo));
	        } catch (Exception e) {}
		} else if(defaultImgCheck.equals("N")) {
			photo = "default.jpg";
	    } else {
	        photo = mo.getPhoto();
	    }
		memberVO.setPhoto(photo);
		logger.info("{}", memberVO);
		memberService.myProfileUpdate(memberVO);
		
        String profileUpdateMessage = memberService.selectById(memberVO.getMember_id(), session);
        printScriptMessage(response, profileUpdateMessage);
	}
	
    // 공통 메소드
	
	// PrintWriter
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
}
