package cn.babasport.service.cms;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.babasport.mapper.product.ColorMapper;
import cn.babasport.mapper.product.ProductMapper;
import cn.babasport.mapper.product.SkuMapper;
import cn.babasport.pojo.product.Product;
import cn.babasport.pojo.product.Sku;
import cn.babasport.pojo.product.SkuQuery;
@Service("cmsService")
public class CmsServiceImpl implements CmsService {
	@Resource
	private ProductMapper productMapper;
	@Resource
	private SkuMapper skuMapper; 
	@Resource
	private ColorMapper colorMapper;
	
	
	//根据主键查询
	@Override
	public Product selectProductById(Long id) {
		Product product = productMapper.selectByPrimaryKey(id);
		return product;
	}
	//根据商品id获得库存信息
	@Override
	public List<Sku> selectSkusByProductId(Long productId) {
		SkuQuery skuQuery = new SkuQuery();
		skuQuery.createCriteria().andProductIdEqualTo(productId).andStockGreaterThan(0);;
		List<Sku> skus = skuMapper.selectByExample(skuQuery);
		//填充颜色信息
		for (Sku sku : skus) {
			sku.setColor(colorMapper.selectByPrimaryKey(sku.getColorId()));
		}
		
		return skus;
	}

}
