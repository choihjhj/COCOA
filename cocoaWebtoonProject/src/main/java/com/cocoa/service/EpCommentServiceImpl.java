package com.cocoa.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cocoa.domain.EpCommentDTO;
import com.cocoa.mapper.EpCommentMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@RequiredArgsConstructor
@Service
@Log4j
public class EpCommentServiceImpl implements EpCommentService {

	private final EpCommentMapper epcommentmapper;

	@Override
	public List<EpCommentDTO> findBestComment(int epId) {
		log.info("epId : " + epId);
		return epcommentmapper.selectLikDesc(epId);
	}

	@Override
	public List<EpCommentDTO> findLatestComment(int epId) {
		log.info("epId : " + epId);
		return epcommentmapper.selecDateDesc(epId);
	}

	@Override
	public int likeSelectEpcomment(int commentId, String userId) {
		return epcommentmapper.likeSelectEpcomment(commentId, userId);
	}

	@Transactional
	@Override
	public boolean likeComment(int commentId, String userId) {

		return epcommentmapper.likeUpdateEpcomment(commentId) ? epcommentmapper.likeInsertLikecomment(commentId, userId)
				: false;
	}

	@Transactional
	@Override
	public boolean dislikeComment(int commentId, String userId) {

		return epcommentmapper.dislikeUpdateEpcomment(commentId)
				? epcommentmapper.dislikeDeleteLikecomment(commentId, userId)
				: false;
	}

	@Override
	public int newComment(EpCommentDTO epcommnet) {
		log.info(epcommnet);
		return epcommentmapper.insertComment(epcommnet);

	}

	@Override
	public int deleteComment(int epcommentId) {
		log.info(epcommentId);
		return epcommentmapper.deleteComment(epcommentId);
	}

	@Override
	public int modifyComment(EpCommentDTO epcomment) {
		log.info(epcomment);
		return epcommentmapper.updateComment(epcomment);
	}

	@Override
	public EpCommentDTO findComment(int commentId) {
		log.info("commentId : " + commentId);
		return epcommentmapper.selectComment(commentId);
	}

}