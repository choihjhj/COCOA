package com.cocoa.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cocoa.domain.EpCommentDTO;
import com.cocoa.domain.EpisodeDTO;
import com.cocoa.domain.ToonUserDTO;
import com.cocoa.service.EpCommentService;
import com.cocoa.service.EpisodeService;
import com.cocoa.service.PurchaseService;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/*")
@RequiredArgsConstructor
public class EpisodeController {

	private final EpisodeService episodeservice;
	private final EpCommentService epcommentservice;
	private final PurchaseService purchaseservice;

	@GetMapping("/episode")
	public String episode(int toonId, int epId, Model model, RedirectAttributes rttr, HttpServletRequest request) {
		log.info("에피소드 요청 툰아이디: "+ toonId+" 에피소드 아이디: "+epId);


		EpisodeDTO episodeDTO = episodeservice.getEpisode(epId);
		if (episodeDTO == null) {
			return "redirect:/layout";
		}
				
		// 에피소드가 무료인지 검사
		if (episodeDTO.getEpStatus() == 0) {
			log.info("무료 에피소드 요청");
			// 에피소드가 무료이면 바로 보여주기
			List<EpCommentDTO> epct = epcommentservice.findBestComment(epId);
			model.addAttribute("EpisodeDTO", episodeDTO);
			model.addAttribute("EpCommentDTO", epct);
			model.addAttribute("EpIds",episodeservice.findMinMaxEpBytoonId(toonId));
			return "episode";
		} else {
			HttpSession session = request.getSession();
			ToonUserDTO toonUserDTO = (ToonUserDTO) session.getAttribute("ToonUserDTO");
			// 에피소드가 유료라면 로그인 검사
			if (toonUserDTO == null) {
				// 로그인 하지 않았다면
				rttr.addAttribute("origin","purchase");
				rttr.addAttribute("toonId",episodeDTO.getToonId());
				return "redirect:/login";
			} else {
				// 해당 회차 구매 여부 검사
				if (purchaseservice.getPurchasedEpId(toonUserDTO.getUserId()).contains(epId)) {
					// 로그인 했는데 구매 했다면
					List<EpCommentDTO> epct = epcommentservice.findBestComment(epId);
					model.addAttribute("EpisodeDTO", episodeDTO);
					model.addAttribute("EpCommentDTO", epct);
					model.addAttribute("EpIds",episodeservice.findMinMaxEpBytoonId(toonId));
					return "episode";
				} else {
					// 로그인 했는데 구매 하지 않앗다면 구매페이지로 이동
					rttr.addAttribute("epId", epId);
					return "redirect:/purchase";
				}
			}

		}

	}

}