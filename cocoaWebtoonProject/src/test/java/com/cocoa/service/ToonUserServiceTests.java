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
public class ToonUserServiceTests {

	@Setter(onMethod_ = @Autowired)
	private ToonUserService service;

//	@Test
//	public void test() {
//		service.findLatestComment(1).forEach(data -> log.info(data));
//	}
	
	@Test
	public void test() {
		log.info(service.idDupCheck("1233"));
	}

}
