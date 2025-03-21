package com.cocoa.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class EpCommentMapperTests {

	@Autowired
	private EpCommentMapper mapper;

	
	@Test
	@Transactional
	public void selectCommentTest() {
		//given
		int commentId = 65;
		String userId = "122";
		
		//when
		int result = mapper.likeSelectEpcomment(commentId, userId);
		
		//then
		log.info("likeSelectEpcommentTest 결과 : "+result);
	}
}
