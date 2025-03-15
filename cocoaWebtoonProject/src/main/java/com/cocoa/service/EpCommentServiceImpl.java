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
	public List<EpCommentDTO> findBestComment(int epId, ToonUserDTO ToonUserDTO) {
		log.info("epId : " + epId);

		if(ToonUserDTO != null) {			
			return epcommentmapper.selectUserLikDesc(epId, ToonUserDTO.getUserId()); //로그인유저가 댓글 좋아요 누른 것 있는지 체크

		} else {
			return epcommentmapper.selectLikDesc(epId);
		}


	}



	@Override
	@Transactional(readOnly = true)
	public List<EpCommentDTO> findLatestComment(int epId, ToonUserDTO ToonUserDTO) {
		log.info("epId : " + epId);
		if(ToonUserDTO != null) {			
			return epcommentmapper.selecUserDateDesc(epId, ToonUserDTO.getUserId()); //로그인유저가 댓글 좋아요 누른 것 있는지 체크

		} else {
			return epcommentmapper.selecDateDesc(epId);
		}
		

	}

	@Transactional
	@Override
	public boolean likeComment(int commentId, String userId) {
		//좋아요 여부 체크
		if (epcommentmapper.likeSelectEpcomment(commentId, userId) == 1) {  //좋아요 있는데 추가요청은 에러처리
			throw new NotFoundException("좋아요 추가 요청 실패");
		}

		// 좋아요 추가
		if (!epcommentmapper.likeInsertLikecomment(commentId, userId)) {
			throw new NotFoundException("좋아요 추가 요청 실패");
		}

		// 좋아요 cnt 1증가 업데이트
		if (!epcommentmapper.likeUpdateEpcomment(commentId)) {
			throw new NotFoundException("좋아요 추가 요청 실패");
		}

		return true;	//추가처리 성공표시

	}

	@Transactional
	@Override
	public boolean dislikeComment(int commentId, String userId) {
		//좋아요 여부 체크
		if (epcommentmapper.likeSelectEpcomment(commentId, userId) == 0) { //좋아요 없는데 취소요청은 에러 처리
			throw new NotFoundException("좋아요 취소 요청 실패");
		}

		// 좋아요 취소
		if (!epcommentmapper.dislikeDeleteLikecomment(commentId, userId)) {
			throw new NotFoundException("좋아요 취소 요청 실패");
		}

		// 좋아요 cnt 1감소 업데이트
		if (!epcommentmapper.dislikeUpdateEpcomment(commentId)) {
			throw new NotFoundException("좋아요 취소 요청 실패");
		}

		return false; //취소처리 성공표시

	}

	@Override
	@Transactional
	public ResponseEntity<String> newComment(EpCommentDTO epcommnet, String userId) {
		log.info("epcommnet : "+epcommnet+",userId : "+userId);

		epcommnet.setUserId(userId);

		//댓글 추가 로직
		return handleCommentResult(epcommentmapper.insertComment(epcommnet));

	}

	@Override
	@Transactional
	public ResponseEntity<String> deleteComment(int epcommentId,String userId) {
		log.info("epcommentId : "+epcommentId+", userId : "+userId);

		EpCommentDTO epcomment = new EpCommentDTO();
		epcomment.setCommentId(epcommentId);
		epcomment.setUserId(userId);

		//댓글 존재 여부 체크
		int checkResult = checkIfEpComment(epcomment);
		if (checkResult == 0) {
			throw new NotFoundException("댓글을 찾을 수 없습니다.");
		}

		//댓글 삭제
		return handleCommentResult(epcommentmapper.deleteComment(epcomment));

	}

	@Override
	@Transactional
	public ResponseEntity<String> modifyComment(EpCommentDTO epcomment, String userId) {
		epcomment.setUserId(userId); 

		//댓글 존재 여부 체크
		if (checkIfEpComment(epcomment) == 0) {
			throw new NotFoundException("댓글을 찾을 수 없습니다.");
		}

		// 댓글 수정 로직        
		return handleCommentResult(epcommentmapper.updateComment(epcomment));		
	}


	private ResponseEntity<String> handleCommentResult(int result) {
		if (result == 1) {
			log.info("success");
			return ResponseEntity.ok("success");
		} else {
			log.info("failure");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("failure");
		}
	}

	private int checkIfEpComment(EpCommentDTO epcomment) {
		return epcommentmapper.checkIfEpComment(epcomment);
	}

}