package com.cocoa.service;

import java.util.List;

import com.cocoa.domain.EpCommentDTO;

public interface EpCommentService {

	public EpCommentDTO findComment(int commentId);

	public List<EpCommentDTO> findBestComment(int epId);

	public List<EpCommentDTO> findLatestComment(int epId);

	public int likeSelectEpcomment(int commentId, String userId);

	public boolean likeComment(int commentId, String userId);

	public boolean dislikeComment(int commentId, String userId);

	public int newComment(EpCommentDTO epcommnet);

	public int deleteComment(int commentId);

	public int modifyComment(EpCommentDTO epcomment);
}