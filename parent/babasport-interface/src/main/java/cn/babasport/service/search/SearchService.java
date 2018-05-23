package cn.babasport.service.search;

import java.util.List;

import cn.babasport.pojo.page.Pagination;
import cn.babasport.pojo.product.Brand;

public interface SearchService {
	
	//前台系统根据关键字进行检索
	public Pagination selectProductsFromSolr(String keyword ,String price,Long brandId,Integer pageNo) throws Exception;
	
	
	//前台系统品牌列表
	public List<Brand> selectBrandsFromRedis();

	//根据id获取品牌名称
	public String selectBrandNameFromRedisById(Long brandId);
	
	//将商品信息保存到索引库
	public void insertProductToSolr(Long id) throws Exception;

}
