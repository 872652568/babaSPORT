package cn.babasport.service.staticpage;

import java.util.Map;

import javax.servlet.ServletContext;

public interface StaticPageService {
	/**
	 * 
	 * 说明：	生成静态页的入口方法
	 * @param id 作为静态页的名称
	 * @param rootMap 静态页需要展示的数据
	 * @author Mr.Song
	 * @time：2017年10月25日 下午10:48:53
	 */
	public void index(String id,Map<String, Object> rootMap);

	
}
