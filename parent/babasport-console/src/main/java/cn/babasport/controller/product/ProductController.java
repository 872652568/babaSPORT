package cn.babasport.controller.product;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.babasport.pojo.page.Pagination;
import cn.babasport.pojo.product.Brand;
import cn.babasport.service.brand.BrandService;
import cn.babasport.service.product.ProductService;

//商品管理
@Controller
@RequestMapping("/product")
public class ProductController {
		
	@Resource
	private BrandService brandService;
	
	@Resource
	private ProductService productService;
	
	//商品列表查询
	@RequestMapping("/list.do")
	public String list(String name, Long brandId, Boolean isShow, Integer pageNo,Model model){
		// 初始化品牌信息
		List<Brand> brands = brandService.selectBrandsNoPage(null, 1);
		model.addAttribute("brands", brands);
		// 查询商品列表信息
		Pagination pagination = productService.selectProductHavePage(name, brandId, isShow, pageNo);
		model.addAttribute("pagination", pagination);
		// 查询条件回显
		model.addAttribute("name", name);
		model.addAttribute("brandId", brandId);
		model.addAttribute("isShow", isShow);
		model.addAttribute("pageNo", pageNo);
		return "product/list";
		
	}
}
