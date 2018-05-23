package cn.babasport.service.cms;

import java.util.List;

import cn.babasport.pojo.product.Product;
import cn.babasport.pojo.product.Sku;

public interface CmsService {
	//根据主键查询
	public  Product selectProductById(Long id);
	//根据商品信获的库存信息
	public List<Sku> selectSkusByProductId(Long productId);
}
