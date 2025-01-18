package com.cocoa.service;

import java.util.Date;

import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.executor.ReuseExecutor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cocoa.domain.ToonUserDTO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class EpCommentServiceTests {

	@Setter(onMethod_ = @Autowired)
	private ToonUserService service;

	/*
	 * @Test public void testSignUp() { Date currentDate = new Date(); ToonUserDTO u
	 * = new ToonUserDTO("Tessst", "123", currentDate, "은성박", "01012341234", 5);
	 * 
	 * int result = service.signUp(u);
	 * 
	 * log.info("회원가입 결과는:" + result); }
	 */

	/*
	 * @Test public void testLogin() { log.info("-----------------------");
	 * log.info("로그인 결과는" + service.login("qq11", "1243"));
	 * log.info("-----------------------"); }
	 */

	/*
	 * @Test public void testRemoveUser() { int result = service.removeUser("qwe");
	 * log.info("삭제 결과는"+result); }
	 */

}
