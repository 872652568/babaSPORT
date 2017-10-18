package cn.babasport.service.product;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.babasport.mapper.product.ProductMapper;
import cn.babasport.pojo.page.Pagination;
import cn.babasport.pojo.product.Product;
import cn.babasport.pojo.product.ProductQuery;
import cn.babasport.pojo.product.ProductQuery.Criteria;
@Service("productService")
public class ProductServiceImpl implements ProductService {
	
	@Resource
	private ProductMapper productMapper;
	
	@Override
	public Pagination selectProductHavePage(String name, Long brandId, Boolean isShow, Integer pageNo) {
		// 1、创建ProductQuery对象用来封装查询条件
				ProductQuery productQuery = new ProductQuery();
				Criteria criteria = productQuery.createCriteria();
				StringBuilder params = new StringBuilder(); // 分页工具栏需要的条件
				if(name != null && !"".equals(name)){
					criteria.andNameLike("%"+name+"%");
					params.append("name=").append(name);
				}
				if(brandId != null){
					criteria.andBrandIdEqualTo(brandId);
					params.append("&brandId=").append(brandId);
				}
				if(isShow != null){
					criteria.andIsShowEqualTo(isShow);
					params.append("&isShow=").append(isShow);
				}else{ // 默认查询下架
					criteria.andIsShowEqualTo(false);
					params.append("&isShow=").append(false);
				}
				productQuery.setPageNo(Pagination.cpn(pageNo));
				productQuery.setPageSize(5);
				
				// 2、根据条件查询
				List<Product> list = productMapper.selectByExample(productQuery);
				int totalCount = productMapper.countByExample(productQuery);
				
				// 3、创建分页对象并且填充数据
				Pagination pagination = new Pagination(productQuery.getPageNo(), productQuery.getPageSize(), totalCount, list);
				// 4、构建分页工具栏
				String url = "/product/list.do";
				pagination.pageView(url, params.toString());
				return pagination;
	}

}
