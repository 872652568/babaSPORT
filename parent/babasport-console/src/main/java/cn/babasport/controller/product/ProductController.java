package cn.babasport.controller.product;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.babasport.pojo.page.Pagination;
import cn.babasport.pojo.product.Brand;
import cn.babasport.pojo.product.Color;
import cn.babasport.pojo.product.Product;
import cn.babasport.service.brand.BrandService;
import cn.babasport.service.color.ColorService;
import cn.babasport.service.product.ProductService;

//商品管理
@Controller
@RequestMapping("/product")
public class ProductController {

	@Resource
	private BrandService brandService;

	@Resource
	private ProductService productService;

	@Resource
	private ColorService colorService;

	// 商品列表查询
	@RequestMapping("/list.do")
	public String list(String name, Long brandId, Boolean isShow, Integer pageNo, Model model) {
		// 初始化品牌信息
		List<Brand> brands = brandService.selectBrandsNoPage(null, 1);
		model.addAttribute("brands", brands);
		// 查询商品列表信息
		Pagination pagination = productService.selectProductsHavePage(name, brandId, isShow, pageNo);
		model.addAttribute("pagination", pagination);
		// 查询条件回显
		model.addAttribute("name", name);
		model.addAttribute("brandId", brandId);
		model.addAttribute("isShow", isShow);
		model.addAttribute("pageNo", pageNo);
		return "product/list";
	}

	@RequestMapping("/add.do")
	public String add(Model model) {

		// 初始化品牌信息
		List<Brand> brands = brandService.selectBrandsNoPage(null, 1);
		model.addAttribute("brands", brands);
		// 初始化颜色信息
		List<Color> colors = colorService.selectColorByParentIdNotEqualsZero();
		model.addAttribute("colors", colors);
		return "product/add";
	}

	@RequestMapping("/save.do")
	public String save(Product product) {
		productService.insertProduct(product);
		return "redirect:list.do";

	}

	@RequestMapping("isShow.do")
	public String isShow(Long[] ids) throws Exception {
		productService.isShow(ids);
		return "redirect:list.do";
	}

}
