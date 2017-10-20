package cn.babasport.service.sku;

import java.util.List;

import cn.babasport.pojo.product.Sku;

public interface SkuService {
	//查询该商品对应的库存
	public List<Sku> selectSkuByProductId(Long productId);
	//更新库存
	public void updateSku(Sku sku);
}
