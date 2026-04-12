package com.cocoa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cocoa.domain.EpCommentDTO;

public interface EpCommentMapper {
	public List<EpCommentDTO> selectLikDesc(int epId);
	
	public List<EpCommentDTO> selectUserLikDesc(@Param("epId") int epId, @Param("userId") String userId);

	public List<EpCommentDTO> selecDateDesc(int epId);
	
	public List<EpCommentDTO> selecUserDateDesc(@Param("epId") int epId, @Param("userId") String userId);

	public int likeUpdateEpcomment(int commentId);

	public int likeInsertLikecomment(@Param("commentId") int commentId, @Param("userId") String userId);

	public int likeSelectEpcomment(@Param("commentId") int commentId, @Param("userId") String userId);

	public int dislikeUpdateEpcomment(int commentId);

	public int dislikeDeleteLikecomment(@Param("commentId") int commentId, @Param("userId") String userId);

	public int insertComment(EpCommentDTO epcommnet);

	public int deleteComment(EpCommentDTO epcomment);
	
	public int updateComment(EpCommentDTO epcomment);
	
	public int checkIfEpComment(EpCommentDTO epcomment);
	
	public int getLikeCntByCommentId(int commentId);
	
}