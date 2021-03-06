package cn.babasport.controller.brand;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cn.babasport.pojo.page.Pagination;
import cn.babasport.pojo.product.Brand;
import cn.babasport.service.brand.BrandService;

@Controller
@RequestMapping("/brand")
public class BrandController {

	@Resource
	private BrandService brandService;

	// 品牌列表查询
	@RequestMapping("/list.do")
	public String list(String name, @RequestParam(defaultValue = "1") Integer isDisplay, Integer pageNo, Model model) {
		// 点击品牌管理列表时 默认查询可用的品牌
		// List<Brand> brands = brandService.selectBrandsNoPage(name,
		// isDisplay);
		// model.addAttribute("brands",brands);
		Pagination pagination = brandService.selectBrandsHavePage(name, isDisplay, pageNo);
		model.addAttribute("pagination", pagination);

		// 查询条件回显
		model.addAttribute("name", name);
		model.addAttribute("isDisplay", isDisplay);
		model.addAttribute("pageNo", pageNo);
		return "brand/list";

	}

	// 品牌修改列表查询主键id
	@RequestMapping("/edit.do")
	public String edit(Long id, Model model) {

		Brand brand = brandService.selectBrandById(id);
		model.addAttribute("brand", brand);

		return "brand/edit";
	}

	// 更新品牌
	@RequestMapping("/update.do")
	public String update(Brand brand) {
		brandService.updateBrand(brand);
		return "redirect:list.do";
	}

	// 跳转添加品牌页面
	@RequestMapping("/add.do")
	public String add() {
		return "brand/add";
	}

	// 提交添加品牌列表
	@RequestMapping("/save.do")
	public String save(Brand brand) {
		brandService.insertBrand(brand);
		return "redirect:list.do";
	}

	// 批量删除
	@RequestMapping("/deleteBatchBrands.do")
	public String deleteBatchBrands(Long[] ids) {
		brandService.deleteBatchBrands(ids);

		return "forward:list.do";
	}

	@RequestMapping("/deleteById.do")
	public String deleteById(Long id) {
		brandService.deleteById(id);
		return "redirect:list.do";
	}
}
