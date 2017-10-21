package cn.babasport.service.product;

import cn.babasport.pojo.page.Pagination;
import cn.babasport.pojo.product.Product;

public interface ProductService {
	// 商品列表查询
	public Pagination selectProductsHavePage(String name, Long brandId, Boolean isShow, Integer pageNo);

	// 添加商品
	public void insertProduct(Product product);
	
	//商品上架
	public void isShow(Long[] ids) throws Exception;
}
