import java.util.Date;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.babasport.mapper.test.BbsTestMapper;
import cn.babasport.pojo.test.BbsTest;
import cn.babasport.service.test.BbsTestService;


@ContextConfiguration(locations={"classpath:spring-config.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
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
			bbsTest.setBrithday(new Date());
			bbsTestMapper.insertBbsTest(bbsTest);
		}
		
		//测试spring管理service的bean
		@Test
		public void testService(){
			BbsTest bbsTest = new BbsTest();
			bbsTest.setName("jack");
			bbsTest.setBrithday(new Date());
			bbsTestMapper.insertBbsTest(bbsTest);
		}
}
