package com.cocoa.mapper;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.cocoa.domain.PurchaseDTO;

import lombok.Setter;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class PurchaseMapperTests {

	@Setter(onMethod_ = @Autowired)
	private PurchaseMapper mapper;

	@Test
	public void test() {
		List<Integer> result = mapper.selectByUserId("qwe");
		result.forEach(var -> log.info(var));
	}
}
