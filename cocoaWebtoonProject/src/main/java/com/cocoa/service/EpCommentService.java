package com.cocoa.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.cocoa.domain.EpCommentDTO;
import com.cocoa.domain.ToonUserDTO;

public interface EpCommentService {

	//public int checkIfEpComment(int commentId, String userId);		//댓글 여부 체크

	public List<EpCommentDTO> findBestComment(int epId); 			//베스트 댓글 조회

	public List<EpCommentDTO> findLatestComment(int epId); 			//최신 댓글 조회

	public int likeSelectEpcomment(int commentId, String userId);	//좋아요 여부 체크

	public boolean likeComment(int commentId, String userId);		//좋아요 추가

	public boolean dislikeComment(int commentId, String userId);	//좋아요 삭제

	public int newComment(EpCommentDTO epcommnet);					//댓글 추가

	public int deleteComment(int commentId);						//댓글 삭제

	public ResponseEntity<String> modifyComment(EpCommentDTO epcomment, ToonUserDTO loggedInUser);		//댓글 수정
}