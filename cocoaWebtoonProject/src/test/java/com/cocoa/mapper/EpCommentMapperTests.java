package com.cocoa.mapper;

import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cocoa.domain.ToonUserDTO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class EpCommentMapperTests {

	@Setter(onMethod_ = @Autowired)
	private ToonUserMapper mapper;

	/*
	 * @Test public void testInsert() { Date currentDate = new Date(); ToonUserDTO u
	 * = new ToonUserDTO("hidd333dhi", "123", currentDate, "은성박", "01012341234", 5);
	 * int result = mapper.insert(u); log.info("실행결과는 : "+result);
	 * System.out.println(result); }
	 */

	/*
	 * @Test public void testSelectByUserIdPwd() {
	 * log.info(mapper.selectByUserIdPwd("aa")); }
	 */

	/*
	 * @Test public void testDeleteByUserId() { mapper.deleteByUserId("aa"); }
	 */
	
	@Test
	public void testselectCphistory() {
		List result = mapper.selectCphistory("11");
		result.forEach(item -> log.info(item));
	}
}
