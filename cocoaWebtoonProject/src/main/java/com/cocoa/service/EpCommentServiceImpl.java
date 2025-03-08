package com.cocoa.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.cocoa.domain.EpCommentDTO;
import com.cocoa.domain.ToonUserDTO;
import com.cocoa.exception.NotFoundException;
import com.cocoa.mapper.EpCommentMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@RequiredArgsConstructor
@Service
@Log4j
public class EpCommentServiceImpl implements EpCommentService {

	private final EpCommentMapper epcommentmapper;

	@Override
	@Transactional(readOnly = true)
	public List<EpCommentDTO> findBestComment(int epId) {
		log.info("epId : " + epId);
		return epcommentmapper.selectLikDesc(epId);
	}

	@Override
	@Transactional(readOnly = true)
	public List<EpCommentDTO> findLatestComment(int epId) {
		log.info("epId : " + epId);
		return epcommentmapper.selecDateDesc(epId);
	}

	@Override
	@Transactional(readOnly = true)
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
	@Transactional
	public int newComment(EpCommentDTO epcommnet) {
		log.info(epcommnet);
		return epcommentmapper.insertComment(epcommnet);

	}

	@Override
	@Transactional
	public int deleteComment(int epcommentId) {
		log.info(epcommentId);
		return epcommentmapper.deleteComment(epcommentId);
	}

	@Override
	@Transactional
	public ResponseEntity<String> modifyComment(EpCommentDTO epcomment, ToonUserDTO loggedInUser) {
		epcomment.setUserId(loggedInUser.getUserId()); 
		
		//댓글 존재 여부 체크
		int checkResult = checkIfEpComment(epcomment);
        if (checkResult == 0) {
            throw new NotFoundException("댓글을 찾을 수 없습니다.");
        }
        
        // 댓글 수정 로직        
        int isModified = epcommentmapper.updateComment(epcomment);
        System.out.println("isModified : "+isModified);
        
        if(isModified == 1) {
        	System.out.println("success");
        	return ResponseEntity.ok("success");
        } else {
        	System.out.println("failure");
        	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("failure");
        }
		
	}


	public int checkIfEpComment(EpCommentDTO epcomment) {
		return epcommentmapper.checkIfEpComment(epcomment);
	}

}