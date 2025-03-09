package com.cocoa.controller;

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.cocoa.domain.EpCommentDTO;
import com.cocoa.domain.EpisodeDTO;
import com.cocoa.service.EpCommentService;
import com.cocoa.service.EpisodeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/*")
@RequiredArgsConstructor
public class EpisodeController {

	private final EpisodeService episodeService;
	private final EpCommentService epCommentService;


	/*
	 * 에피소드 페이지 요청
	 * GET /episode?toonId={toonId}&epId={epId}
	 * return "episode"
	 * */
	@GetMapping("/episode")
	public String episode(@RequestParam("toonId")int toonId,
						  @RequestParam("epId") int epId,
						  Model model,
						  RedirectAttributes rttr) {
		log.info("에피소드 요청 툰아이디: " + toonId + " 에피소드 아이디: " + epId);

		// 에피소드 정보 가져오기
		EpisodeDTO episodeDTO = episodeService.getEpisode(epId);
		if (episodeDTO == null) {
			return "redirect:/layout"; // 에피소드가 없으면 기본 페이지로 리다이렉트
		}

		// 댓글 가져오기
		List<EpCommentDTO> epComments = epCommentService.findBestComment(epId);

		// 모델에 데이터 추가
		model.addAttribute("EpisodeDTO", episodeDTO);
		model.addAttribute("EpCommentDTO", epComments);
		model.addAttribute("EpIds", episodeService.getEpidsByToonId(toonId)); //페이징처리 위해 epid 리스트 저장

		return "episode";

	}

	

}
