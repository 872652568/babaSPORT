package cn.babasport.controller.center;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//后台管理系统页面跳转
@Controller
@RequestMapping("/center")
public class CenterController {
	
	
	
	//访问后台系统主页
	@RequestMapping("/index.do")
	public String index(){
		
		return "index";
	}
	
	//主页访问main页面
	@RequestMapping("/main.do")
	public String main(){
		return "main";
	}
	
	//主页访问top页面
	@RequestMapping("top.do")
	public String top(){
		return "top";
	}
	
	//main页面访问left页面
	@RequestMapping("left.do")
	public String left(){
		return "left";
	}
	//main页面访问right页面
	@RequestMapping("right.do")
	public String right(){
		return "right";
	}
}
