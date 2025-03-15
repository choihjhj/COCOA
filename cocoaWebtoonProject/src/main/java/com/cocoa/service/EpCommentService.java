package com.cocoa.service;

import java.util.List;
import org.springframework.http.ResponseEntity;
import com.cocoa.domain.EpCommentDTO;


public interface EpCommentService {

	public List<EpCommentDTO> findBestComment(int epId); 			//베스트 댓글 조회

	public List<EpCommentDTO> findLatestComment(int epId); 			//최신 댓글 조회

	public boolean likeComment(int commentId, String userId);		//좋아요 추가

	public boolean dislikeComment(int commentId, String userId);	//좋아요 삭제

	public ResponseEntity<String> newComment(EpCommentDTO epcommnet, String userId);		//댓글 추가

	public ResponseEntity<String> deleteComment(int commentId, String userId);				//댓글 삭제

	public ResponseEntity<String> modifyComment(EpCommentDTO epcomment, String userId);		//댓글 수정
}