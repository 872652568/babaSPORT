package cn.babasport.service.brand;

import java.util.List;

import cn.babasport.pojo.page.Pagination;
import cn.babasport.pojo.product.Brand;
import cn.babasport.pojo.product.BrandQuery;

public interface BrandService {
	// 品牌查询列表 不分页
	public List<Brand> selectBrandsNoPage(String name, Integer isDisplay);

	// 品牌列表分页查询
	public Pagination selectBrandsHavePage(String name, Integer isDisplay, Integer pageNo);

	// 根据id主键查询
	public Brand selectBrandById(Long id);

	// 更新品牌
	public void updateBrand(Brand brand);

	// 添加品牌
	public void insertBrand(Brand brand);
	
	// 批量删除
	public void deleteBatchBrands(Long[] ids);
	
	//通过id单独删除品牌
	public void deleteById(Long id);
}
