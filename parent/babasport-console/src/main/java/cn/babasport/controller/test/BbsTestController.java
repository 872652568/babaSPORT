package cn.babasport.controller.test;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.babasport.pojo.test.BbsTest;
import cn.babasport.service.test.BbsTestService;
@Controller
public class BbsTestController {
		@Resource 
		private BbsTestService bbsTestService;
		
		@RequestMapping("/test.do")
		public String test(){
			BbsTest bbs = new BbsTest();
			bbs.setName("送比你");
			bbs.setBirthday(new Date());
			bbsTestService.insertBbsTest(bbs);
			return "test";
		}
		
}
