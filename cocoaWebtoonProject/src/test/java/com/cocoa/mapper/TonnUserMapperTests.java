package com.cocoa.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cocoa.domain.EpCommentDTO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class TonnUserMapperTests {

	@Setter(onMethod_ = @Autowired)
	private EpCommentMapper mapper;

	/*
	 * @Test public void testInsertComment() { EpCommentDTO epcomment = new
	 * EpCommentDTO(); epcomment.setUserId("qwe"); epcomment.setEpId(1);
	 * epcomment.setCommentBody("이게 만화냐?");
	 * 
	 * mapper.insertComment(epcomment); }
	 */
	
//	@Test
//	public void testUpdateLikeCnt() {
//		int result = mapper.updateLikeCnt(81);
//		log.info("실행 결과: " +result);
//	}
	
//	@Test
//	public void testfind() {
//		mapper.findBestComment(1).forEach(data -> log.info(data));
//	}
}
