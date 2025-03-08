package com.cocoa.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.ibatis.javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cocoa.domain.EpCommentDTO;
import com.cocoa.domain.ToonUserDTO;
import com.cocoa.service.EpCommentService;
import com.cocoa.service.SessionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/*")
@RequiredArgsConstructor
public class EpCommentController {

	private final EpCommentService epcommentservice;
	private final SessionService sessionservice;
	
	/*
     * 최신 댓글 목록 조회
     * GET /lastestComment?epId={epId}
     * return "comment"
     * */
	@GetMapping("/lastestComment")
	public String lastestComment(@RequestParam Integer epId, Model model) {
		log.info("값:" + epId);
		List<EpCommentDTO> epct = epcommentservice.findLatestComment(epId);
		model.addAttribute("EpCommentDTO", epct);
		return "comment";
	}

	/*
     * 베스트 댓글 목록 조회
     * GET /bestComment?epId={epId}
     * return "comment"
     * */
	@GetMapping("/bestComment")
	public String bestComment(@RequestParam Integer epId, Model model) {
		List<EpCommentDTO> epct = epcommentservice.findBestComment(epId);
		model.addAttribute("EpCommentDTO", epct);
		return "comment";
	}

	@PostMapping("/like/{commentId}")
	public @ResponseBody boolean likeComment(@PathVariable int commentId, HttpServletRequest request) {
		
		ToonUserDTO ToonUserDTO = sessionservice.getLoggedInUser(request);
		if (epcommentservice.likeSelectEpcomment(commentId, ToonUserDTO.getUserId()) == 1) {// 좋아요 여부 체크(좋아요 누른적 있으면 취소 처리)
			epcommentservice.dislikeComment(commentId, ToonUserDTO.getUserId());
			return false; // 좋아요 취소

		} else {
			epcommentservice.likeComment(commentId, ToonUserDTO.getUserId());
			return true; // 좋아요한 것

		}
	}

	@PostMapping(value = "/newcomment", produces = "application/json")
	@ResponseBody
	public int epcommentWrite(EpCommentDTO EpCommentDTO, HttpServletRequest request) {

		ToonUserDTO ToonUserDTO = sessionservice.getLoggedInUser(request);
		EpCommentDTO ep = new EpCommentDTO();
		ep.setUserId(ToonUserDTO.getUserId());
		ep.setEpId(Integer.parseInt(request.getParameter("epid")));
		ep.setCommentBody(request.getParameter("writedata"));
		return epcommentservice.newComment(ep);
	}

	@DeleteMapping(value = "/removecomment", produces = "application/json")
	@ResponseBody
	public int deleteComment(@RequestParam("commentId") int commentId) {
		log.info("삭제 요청된 댓글 ID: " + commentId);
		return epcommentservice.deleteComment(commentId);

	}

	@PutMapping(value = "/modifycomment", produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> modifyComment(@RequestBody EpCommentDTO EpCommentDTO, HttpServletRequest request) {
		log.info("댓글수정 : "+EpCommentDTO.getCommentId()+EpCommentDTO.getCommentBody());
		
		//로그인 여부 체크
		ToonUserDTO loggedInUser = sessionservice.getLoggedInUser(request);
		if (loggedInUser  == null) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인이 필요합니다."); 
		} 
		
		return epcommentservice.modifyComment(EpCommentDTO, loggedInUser);

	}

}