package com.cocoa.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.ibatis.javassist.NotFoundException;
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

	@GetMapping("/lastestComment")
	public String lastestComment(@RequestParam(required = false) Integer epId, Model model) {
		log.info("값:" + epId);
		List<EpCommentDTO> epct = epcommentservice.findLatestComment(epId);
		model.addAttribute("EpCommentDTO", epct);
		return "comment";
	}

	@GetMapping("/bestComment")
	public String bestComment(@RequestParam(required = false) Integer epId, Model model) {
		List<EpCommentDTO> epct = epcommentservice.findBestComment(epId);
		model.addAttribute("EpCommentDTO", epct);
		return "comment";
	}

	@PostMapping("/like/{commentId}")
	public @ResponseBody boolean likeComment(@PathVariable int commentId, HttpServletRequest request) {
		
		ToonUserDTO ToonUserDTO = sessionservice.getLoggedInUser(request);
		if (epcommentservice.likeSelectEpcomment(commentId, ToonUserDTO.getUserId()) == 1) {// 좋아요 누른적 있음 취소해야함
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
	public int modifyComment(@RequestBody EpCommentDTO EpCommentDTO) throws NotFoundException {
		log.info("댓글수정 : "+EpCommentDTO.getCommentId()+EpCommentDTO.getCommentBody());
		EpCommentDTO ep = (EpCommentDTO) epcommentservice.findComment(EpCommentDTO.getCommentId());
		if (ep == null) {
			throw new NotFoundException("댓글을 찾을 수 없습니다");
		}
		ep.setCommentBody(EpCommentDTO.getCommentBody());
		return epcommentservice.modifyComment(ep);
	}

}