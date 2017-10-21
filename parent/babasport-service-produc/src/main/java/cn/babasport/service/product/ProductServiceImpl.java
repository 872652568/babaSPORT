package cn.babasport.service.product;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.babasport.mapper.product.ProductMapper;
import cn.babasport.mapper.product.SkuMapper;
import cn.babasport.pojo.page.Pagination;
import cn.babasport.pojo.product.Product;
import cn.babasport.pojo.product.ProductQuery;
import cn.babasport.pojo.product.ProductQuery.Criteria;
import cn.babasport.pojo.product.Sku;
import cn.babasport.pojo.product.SkuQuery;
import redis.clients.jedis.Jedis;

@Service("productService")
public class ProductServiceImpl implements ProductService {

	@Resource
	private ProductMapper productMapper;

	@Resource
	private SkuMapper skuMapper;

	@Resource
	private Jedis jedis;
	

	@Resource
	private SolrServer solrServer;

	@Override
	public Pagination selectProductsHavePage(String name, Long brandId, Boolean isShow, Integer pageNo) {
		// 1、创建ProductQuery对象用来封装查询条件
		ProductQuery productQuery = new ProductQuery();
		Criteria criteria = productQuery.createCriteria();
		StringBuilder params = new StringBuilder(); // 分页工具栏需要的条件
		if (name != null && !"".equals(name)) {
			criteria.andNameLike("%" + name + "%");
			params.append("name=").append(name);
		}
		if (brandId != null) {
			criteria.andBrandIdEqualTo(brandId);
			params.append("&brandId=").append(brandId);
		}
		if (isShow != null) {
			criteria.andIsShowEqualTo(isShow);
			params.append("&isShow=").append(isShow);
		} else { // 默认查询下架
			criteria.andIsShowEqualTo(false);
			params.append("&isShow=").append(false);
		}
		productQuery.setPageNo(Pagination.cpn(pageNo));
		productQuery.setPageSize(5);
		productQuery.setOrderByClause("id desc"); // 根据id降序

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

	@Override
	public void insertProduct(Product product) {
		// 1,保存商品信息
		product.setIsShow(false);// 录入商品默认是下架
		product.setCreateTime(new Date());
		// 通过redis生成产品id
		Long id = jedis.incr("pno");
		product.setId(id);

		productMapper.insertSelective(product);
		// 2,初始该商品对应的库存信息
		String[] colors = product.getColors().split(",");// 颜色的结果集
		String[] sizes = product.getSizes().split(",");// 尺码结果集
		for (String size : sizes) {
			for (String color : colors) {
				Sku sku = new Sku();
				// sku.setProductId(product.getId());
				sku.setProductId(id);
				sku.setColorId(Long.parseLong(color));
				sku.setSize(size);
				sku.setMarketPrice(0f);
				sku.setPrice(0f);
				sku.setDeliveFee(0f);
				sku.setStock(0);
				sku.setUpperLimit(0);
				skuMapper.insertSelective(sku);
			}
		}
	}

	@Transactional
	@Override
	public void isShow(Long[] ids) throws Exception {
		// 1、更改isShow为true
		Product product = new Product();
		product.setIsShow(true);
		for (Long id : ids) {
			product.setId(id);
			productMapper.updateByPrimaryKeySelective(product);
			
			// 2、将商品信息保存到索引库
			Product goods = productMapper.selectByPrimaryKey(id);
			SolrInputDocument doc = new SolrInputDocument();
			doc.addField("id", id); // 商品主键
			doc.addField("url", goods.getImgUrl()); // 商品图片
			doc.addField("name_ik", goods.getName()); // 商品名称
			doc.addField("brandId", goods.getBrandId()); // 商品所属品牌
			// 价格从sku中去取，显示最低价格在页面中
			// select price from bbs_sku where product_id = ? order by price asc limit 0,1
			SkuQuery skuQuery = new SkuQuery();
			skuQuery.setFields("price");
			skuQuery.createCriteria().andProductIdEqualTo(id);
			skuQuery.setOrderByClause("price asc");
			skuQuery.setPageNo(1);
			skuQuery.setPageSize(1);
			List<Sku> skus = skuMapper.selectByExample(skuQuery);
			doc.addField("price", skus.get(0).getPrice()); // 商品价格
			solrServer.add(doc);
			solrServer.commit();
		}
		
	}

}
