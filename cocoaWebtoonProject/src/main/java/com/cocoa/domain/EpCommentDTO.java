package com.cocoa.domain;

import java.util.Date;

import lombok.Data;

@Data
public class EpCommentDTO {
	private int commentId;
	private String userId;
	private int epId;
	private Date writeDate;
	private int likeCnt;
	private String commentBody;
	private int isLiked = 0; //좋아요 누른적 없음이 디폴트 
}