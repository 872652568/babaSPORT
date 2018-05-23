package cn.babasport.service.staticpage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import freemarker.template.Configuration;
import freemarker.template.Template;


public class StaticPageServiceImpl implements StaticPageService,ServletContextAware  {
	private Configuration configuration;
	private ServletContext servletContext;
	
	/**
	 * 
	 * @Title: setFreeMarkerConfigurer
	 * @Description: 注入freeMarkerConfigurer：1、获取configuration；2、指定模板位置
	 * @param freeMarkerConfigurer
	 * @return void
	 * @throws
	 */
	public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
		this.configuration = freeMarkerConfigurer.getConfiguration();
	}
	
	/**
	 * 
	 * @Title: setServletContext
	 * @Description: 实现ServletContextAware帮助我们注入setServletContext
	 * @param servletContext
	 * @return void
	 * @throws
	 */
	@Override
	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}


	/*
	 * (non-Javadoc)
	 * <p>Title: index</p> 
	 * <p>Description: 生成静态页的入口方法</p> 
	 * @param id
	 * @param rootMap 
	 */
	@Override
	public void index(String id, Map<String, Object> rootMap) {
		try {
			// 1、创建Configuration并且指定模板位置
			// 之前入门程序：new对象并且指定模板位置（硬编码）
			// 将静态页发布到项目的真实路径
			String pathname = "/html/product/"+id+".html";
			String path = servletContext.getRealPath(pathname);
			File file = new File(path);
			File parentFile = file.getParentFile();
			if(!parentFile.exists()){
				parentFile.mkdirs(); // 创建多级目录
			}
			// 2、获取模板
			Template template = configuration.getTemplate("product.html");
			// 3、准备数据：谁调用谁传递
			// 4、模板+数据：输出
			Writer out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			template.process(rootMap, out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
