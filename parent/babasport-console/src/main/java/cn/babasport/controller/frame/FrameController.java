package cn.babasport.controller.frame;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//完成tab页面切换
@Controller
@RequestMapping("frame")
public class FrameController {
		
	//跳转product_main页面
	@RequestMapping("product_main.do")
	public String product_main(){
		
		return "frame/product_main";
	}
	
	
	//product_main跳转加载product_left页面
	@RequestMapping("product_left.do")
	public String product_left(){
		
		return "frame/product_left";
	}
}
