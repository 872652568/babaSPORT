package cn.babasport.service.test;



import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.babasport.mapper.test.BbsTestMapper;
import cn.babasport.pojo.test.BbsTest;
@Service("bbsTestService")
@Transactional
public class BbsTestServiceImpl implements BbsTestService {

	@Resource
	private BbsTestMapper bbsTestMapper;
	
	@Override
	public void insertBbsTest(BbsTest bbsTest) {
		bbsTestMapper.insertBbsTest(bbsTest);
		
	}

}
