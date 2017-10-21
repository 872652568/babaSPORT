
import java.util.Date;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrServer;

import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


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
		
		@Resource
		private SolrServer solrServer;
		
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
	
		//测试solr
		@Test
		public void testSolrServer() throws Exception{
			//封装数据
			SolrInputDocument doc = new SolrInputDocument();
			doc.addField("id", 1);
			doc.addField("name_ik", "spring与solr进行整合");
			//保存
			solrServer.add(doc);
			solrServer.commit();
		}
}
