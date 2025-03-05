package com.cocoa.mapper;

import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import com.cocoa.domain.WebToonDTO;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class SearchMapperTests {
	
	@Autowired
	private SearchMapper mapper;
	
	@Test
	@Transactional
	public void searchTest() {
		//given
		
		String keyword = "미";
		
		//when
		List<WebToonDTO> result = mapper.selectBySearchBox(keyword);
		
		//then
		//assertNotNull(result);
	    //assertTrue(result.size() > 0);
		
		result.stream().forEach(webToonDTO -> log.info("--- WebToon: " + webToonDTO));
	}

}
