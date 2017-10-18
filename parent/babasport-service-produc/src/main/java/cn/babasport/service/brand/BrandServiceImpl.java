package cn.babasport.service.brand;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.babasport.mapper.product.BrandMapper;
import cn.babasport.pojo.page.Pagination;
import cn.babasport.pojo.product.Brand;
import cn.babasport.pojo.product.BrandQuery;

@Service("brandService")
public class BrandServiceImpl implements BrandService {

	@Resource
	private BrandMapper brandMapper;

	@Override
	public List<Brand> selectBrandsNoPage(String name, Integer isDisplay) {
		// 创建 brandQuery对象 用来封装查询条件
		BrandQuery brandQuery = new BrandQuery();
		if (name != null && !"".equals(name)) {
			brandQuery.setName(name);
		}
		if (isDisplay != null) {
			brandQuery.setIsDisplay(isDisplay);
		}
		// 根据条件查询
		List<Brand> brands = brandMapper.selectBrandsNoPage(brandQuery);

		return brands;
	}

	@Override
	public Pagination selectBrandsHavePage(String name, Integer isDisplay, Integer pageNo) {

		// 1.创建BrandQuery对象用来封装查询条件
		BrandQuery brandQuery = new BrandQuery();
		StringBuilder params = new StringBuilder();
		if (name != null && !"".equals(name)) {
			brandQuery.setName(name);
			params.append("name=").append(name);
		}
		if (isDisplay != null) {
			brandQuery.setIsDisplay(isDisplay);
			params.append("&isDisplay=").append(isDisplay);
		}
		brandQuery.setPageNo(Pagination.cpn(pageNo));
		brandQuery.setPageSize(4);
		// 2.根据条件查询
		List<Brand> list = brandMapper.selectBrandHavePage(brandQuery);
		int count = brandMapper.selectBrandCount(brandQuery);
		// 3.创建分页对象并且填充数据
		Pagination pagination = new Pagination(brandQuery.getPageNo(), brandQuery.getPageSize(), count, list);
		// 构建分页工具栏
		String url = "/brand/list.do";
		pagination.pageView(url, params.toString());

		return pagination;
	}

	@Override
	public Brand selectBrandById(Long id) {
		Brand brandById = brandMapper.selectBrandById(id);
		return brandById;
	}

	@Transactional
	@Override
	public void updateBrand(Brand brand) {
		brandMapper.updateBrand(brand);
	}

	@Transactional
	@Override
	public void insertBrand(Brand brand) {
		brandMapper.insertBrand(brand);
	}

	@Override
	public void deleteBatchBrands(Long[] ids) {
		brandMapper.deleteBatchBrands(ids);
	}

	@Override
	public void deleteById(Long id) {
		brandMapper.deleteById(id);
		
	}

}
