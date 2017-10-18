package cn.babasport.mapper.product;

import java.util.List;

import cn.babasport.pojo.product.Brand;
import cn.babasport.pojo.product.BrandQuery;

public interface BrandMapper {

	// 品牌列表查询 不分页
	public List<Brand> selectBrandsNoPage(BrandQuery brandQuery);

	// 品牌列表总条数
	public int selectBrandCount(BrandQuery brandQuery);

	// 品牌列表查询分页
	public List<Brand> selectBrandHavePage(BrandQuery brandQuery);

	// 根据主键查询
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
