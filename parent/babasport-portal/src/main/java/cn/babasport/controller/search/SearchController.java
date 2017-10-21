package cn.babasport.controller.search;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
/**
 * 
 *
 *   █████▒█    ██  ▄████▄   ██ ▄█▀       ██████╗ ██╗   ██╗ ██████╗
 * ▓██   ▒ ██  ▓██▒▒██▀ ▀█   ██▄█▒        ██╔══██╗██║   ██║██╔════╝
 * ▒████ ░▓██  ▒██░▒▓█    ▄ ▓███▄░        ██████╔╝██║   ██║██║  ███╗
 * ░▓█▒  ░▓▓█  ░██░▒▓▓▄ ▄██▒▓██ █▄        ██╔══██╗██║   ██║██║   ██║
 * ░▒█░   ▒▒█████▓ ▒ ▓███▀ ░▒██▒ █▄       ██████╔╝╚██████╔╝╚██████╔╝
 *  ▒ ░   ░▒▓▒ ▒ ▒ ░ ░▒ ▒  ░▒ ▒▒ ▓▒       ╚═════╝  ╚═════╝  ╚═════╝
 *  ░     ░░▒░ ░ ░   ░  ▒   ░ ░▒ ▒░
 *  ░ ░    ░░░ ░ ░ ░        ░ ░░ ░
 *           ░     ░ ░      ░  ░
 * 说明：访问门户主页
 * @author 大富豪.嘉祥先生
 * @version 1.0
 * @date 2017年10月21日
 */
import org.springframework.web.bind.annotation.RequestMapping;

import cn.babasport.pojo.page.Pagination;
import cn.babasport.service.search.SearchService;

@Controller
public class SearchController {

	@Resource
	private SearchService searchService;

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/Search")
	public String search(String keyword, Integer pageNo, Model model) throws Exception {
		System.err.println("阿斯doi奥斯达接送i多家奥斯i家");
		Pagination pagination = searchService.selectProductsFromSolr(keyword, pageNo);
		model.addAttribute("pagination", pagination);
		System.err.println("阿斯doi奥斯达接送i多家奥斯i家");
		return "search";

	}
}
