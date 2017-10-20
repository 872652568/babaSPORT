package cn.babasport.service.sku;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.babasport.mapper.product.ColorMapper;
import cn.babasport.mapper.product.SkuMapper;
import cn.babasport.pojo.product.Sku;
import cn.babasport.pojo.product.SkuQuery;
@Service("skuService")
public class SkuServiceImpl implements SkuService {
	
	@Resource
	private SkuMapper skuMapper;
	
	@Resource
	private ColorMapper colorMapper;
	
	@Override
	public List<Sku> selectSkuByProductId(Long productId) {
		SkuQuery skuQuery = new SkuQuery();
		skuQuery.createCriteria().andProductIdEqualTo(productId);
		List<Sku> skus = skuMapper.selectByExample(skuQuery);
		//填充颜色信息
		for (Sku sku : skus) {
			sku.setColor(colorMapper.selectByPrimaryKey(sku.getColorId()));
		}
		
		return skus;
	}
	
	
	@Transactional
	@Override
	public void updateSku(Sku sku) {
		skuMapper.updateByPrimaryKeySelective(sku);
		
	}

}
