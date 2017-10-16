import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import cn.babasport.mapper.test.BbsTestMapper;
import cn.babasport.pojo.test.BbsTest;
import cn.babasport.service.test.BbsTestService;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring-config.xml"})
public class TestSM {
		@Resource
		private BbsTestMapper bbsTestMapper;
		
		@Resource
		private BbsTestService bbsTestService;
		//测试sm整合
		@Test
		public void testMybatis(){
			BbsTest bbsTest = new BbsTest();
			bbsTest.setName("tom");
			bbsTest.setBirthday(new Date());
			bbsTestMapper.insertBbsTest(bbsTest);
		}
		
		//测试spring管理service的bean
		@Test
		public void testService(){
			BbsTest bbsTest = new BbsTest();
			bbsTest.setName("jack");
			bbsTest.setBirthday(new Date());
			bbsTestService.insertBbsTest(bbsTest);
		}
	
}
