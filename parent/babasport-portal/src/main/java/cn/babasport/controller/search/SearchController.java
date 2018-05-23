package cn.babasport.controller.search;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import cn.babasport.pojo.product.Brand;
import cn.babasport.pojo.product.Color;
import cn.babasport.pojo.product.Product;
import cn.babasport.pojo.product.Sku;
import cn.babasport.service.cms.CmsService;
import cn.babasport.service.search.SearchService;

@Controller
public class SearchController {

	@Resource
	private SearchService searchService;
	@Resource
	private CmsService cmsService;

	@RequestMapping("/")
	public String index() {
		return "index";
	}

	@RequestMapping("/Search")
	public String search(String keyword, Integer pageNo, String price, Long brandId, Model model) throws Exception {
		// 品牌信息
		List<Brand> brands = searchService.selectBrandsFromRedis();
		model.addAttribute("brands", brands);

		// 商品信息
		Pagination pagination = searchService.selectProductsFromSolr(keyword, price, brandId, pageNo);
		model.addAttribute("pagination", pagination);
		// 查询条件
		model.addAttribute("keyword", keyword);
		model.addAttribute("price", price);
		model.addAttribute("brandId", brandId);

		// 筛选条件的回显
		Map<String, String> map = new HashMap<>();
		if (brandId != null) {
			map.put("品牌：", searchService.selectBrandNameFromRedisById(brandId));
		}
		if (price != null && !"".equals(price)) {
			String[] prices = price.split("-");
			if (prices.length == 2) {
				map.put("价格：", price);
			} else {
				map.put("价格：", price + "以上");
			}
		}
		model.addAttribute("map", map);
		return "search";

	}
	
	//去商品详情页
	@RequestMapping("/product/detail")
	public String detail(Long id,Model model){
		//商品信息
		Product product = cmsService.selectProductById(id);
		model.addAttribute("product", product);
		//库存信息
		List<Sku> skus = cmsService.selectSkusByProductId(id);
		model.addAttribute("skus", skus);
		
		//颜色信息
		Set<Color> colors = new HashSet<>();
		for (Sku sku : skus) {
			colors.add(sku.getColor());
		}
		model.addAttribute("colors", colors);
		return "product";
	}


}
