package com.cocoa.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
import com.cocoa.exception.UnauthorizedAccessException;
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
	public String lastestComment(@RequestParam("epId") Integer epId, Model model, HttpServletRequest request) {
		log.info("값:" + epId);
		ToonUserDTO ToonUserDTO = sessionservice.getLoggedInUser(request);
		List<EpCommentDTO> epct = epcommentservice.findLatestComment(epId,ToonUserDTO);
		model.addAttribute("EpCommentDTO", epct);
		return "comment";
	}

	/*
     * 베스트 댓글 목록 조회
     * GET /bestComment?epId={epId}
     * return "comment"
     * */
	@GetMapping("/bestComment")
	public String bestComment(@RequestParam("epId") Integer epId, Model model, HttpServletRequest request) {
		ToonUserDTO ToonUserDTO = sessionservice.getLoggedInUser(request);
		List<EpCommentDTO> epct = epcommentservice.findBestComment(epId,ToonUserDTO);
		model.addAttribute("EpCommentDTO", epct);
		return "comment";
	}

	/*
     * 좋아요 추가
     * POST /like/{commentId}
     * return @ResponseBody boolean //True 추가 성공
     * */
	@PostMapping("/like/{commentId}")
	public @ResponseBody boolean likeComment(@PathVariable int commentId, HttpServletRequest request) {
		log.info("like");
		ToonUserDTO ToonUserDTO = sessionservice.getLoggedInUser(request);
		return epcommentservice.likeComment(commentId, ToonUserDTO.getUserId());
	}
	
	/*
     * 좋아요 삭제
     * DELETE /removeLike/{commentId}
     * return @ResponseBody boolean //false 취소 성공
     * */
	@DeleteMapping("/removeLike/{commentId}")
	public @ResponseBody boolean removeLike(@PathVariable int commentId, HttpServletRequest request) {
		log.info("removeLike");
	    ToonUserDTO ToonUserDTO = sessionservice.getLoggedInUser(request);
	    return epcommentservice.dislikeComment(commentId, ToonUserDTO.getUserId());
	}

	/*
     * 댓글 추가
     * POST /newcomment
     * return @ResponseBody int
     * */
	@PostMapping(value = "/newcomment", produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> epcommentWrite(EpCommentDTO EpCommentDTO, HttpServletRequest request) {
		log.info("댓글내용 : "+EpCommentDTO.getCommentBody()+", epid : "+EpCommentDTO.getEpId());
		
		//로그인 여부 체크 후 댓글 추가		
		return epcommentservice.newComment(EpCommentDTO, validateLoggedInUser(request));
	}

	/*
     * 댓글 삭제
     * DELETE /removecomment
     * return @ResponseBody int
     * */
	@DeleteMapping(value = "/removecomment", produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> deleteComment(@RequestParam("commentId") int commentId, HttpServletRequest request) {
		log.info("삭제 요청된 댓글 ID: " + commentId);
		
		//로그인 여부 체크 후 댓글 삭제
		return epcommentservice.deleteComment(commentId, validateLoggedInUser(request));

	}

	/*
     * 댓글 수정
     * PUT /modifycomment
     * return @ResponseBody ResponseEntity<String>
     * */
	@PutMapping(value = "/modifycomment", produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> modifyComment(@RequestBody EpCommentDTO EpCommentDTO, HttpServletRequest request) {
		log.info("댓글수정 : "+EpCommentDTO.getCommentId()+EpCommentDTO.getCommentBody());
		
		//로그인 여부 체크 후 댓글 수정		
		return epcommentservice.modifyComment(EpCommentDTO, validateLoggedInUser(request));

	}
	
	//로그인 여부 체크 메서드 - return userId
	private String validateLoggedInUser(HttpServletRequest request) {
		ToonUserDTO loggedInUser = sessionservice.getLoggedInUser(request);
		log.info("loggedInUser : "+loggedInUser);
		if (loggedInUser  == null) {
			throw new UnauthorizedAccessException("로그인이 필요합니다.");
		} 
		return loggedInUser.getUserId();
	}

}