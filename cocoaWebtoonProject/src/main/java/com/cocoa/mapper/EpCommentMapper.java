package com.cocoa.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cocoa.domain.EpCommentDTO;

public interface EpCommentMapper {
	public List<EpCommentDTO> selectLikDesc(int epId);

	public List<EpCommentDTO> selecDateDesc(int epId);

	public boolean likeUpdateEpcomment(int commentId);

	public boolean likeInsertLikecomment(@Param("commentId") int commentId, @Param("userId") String userId);

	public int likeSelectEpcomment(@Param("commentId") int commentId, @Param("userId") String userId);

	public boolean dislikeUpdateEpcomment(int commentId);

	public boolean dislikeDeleteLikecomment(@Param("commentId") int commentId, @Param("userId") String userId);

	public int insertComment(EpCommentDTO epcommnet);

	public int deleteComment(int commentId);
	
	public int updateComment(EpCommentDTO epcomment);
	
	public EpCommentDTO selectComment(int commentId);
	
}