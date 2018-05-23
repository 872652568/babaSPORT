package cn.babasport.service.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.stereotype.Service;

import cn.babasport.mapper.product.ProductMapper;
import cn.babasport.mapper.product.SkuMapper;
import cn.babasport.pojo.page.Pagination;
import cn.babasport.pojo.product.Brand;
import cn.babasport.pojo.product.Product;
import cn.babasport.pojo.product.ProductQuery;
import cn.babasport.pojo.product.Sku;
import cn.babasport.pojo.product.SkuQuery;
import redis.clients.jedis.Jedis;

@Service("searchService")
public class SearchServiceImpl implements SearchService {
	//上传github测试
	@Resource
	private SolrServer solrServer;
	@Resource
	private Jedis jedis;

	@Resource
	private ProductMapper productMapper;

	@Resource
	private SkuMapper skuMapper;

	@Override
	public Pagination selectProductsFromSolr(String keyword, String price, Long brandId, Integer pageNo)
			throws Exception {
		// 一、创建SolrQuery封装查询条件
		SolrQuery solrQuery = new SolrQuery();
		ProductQuery productQuery = new ProductQuery(); // 结果分页需要的条件
		productQuery.setPageNo(Pagination.cpn(pageNo));
		productQuery.setPageSize(8);
		StringBuilder params = new StringBuilder(); // 分页工具栏需要的参数
		// 1、根据关键字检索
		if (keyword != null && !"".equals(keyword)) {
			solrQuery.setQuery("name_ik:" + keyword);
			params.append("keyword=").append(keyword);
		}
		// 2、关键字高亮
		solrQuery.setHighlight(true);
		solrQuery.addHighlightField("name_ik");
		solrQuery.setHighlightSimplePre("<font color='red'>");
		solrQuery.setHighlightSimplePost("</font>");
		// 3、价格排序
		solrQuery.setSort("price", ORDER.asc);
		// 4、结果分页
		solrQuery.setStart(productQuery.getStartRow()); // 其始行
		solrQuery.setRows(productQuery.getPageSize()); // 每页显示条数
		// 5、根据品牌、价格进行过滤
		if (brandId != null) {
			solrQuery.addFilterQuery("brandId:" + brandId);
			params.append("&brandId=").append(brandId);
		}
		if (price != null && !"".equals(price)) { // price:[min TO max]
			String[] prices = price.split("-");
			if (prices.length == 2) { // 价格区间段
				solrQuery.addFilterQuery("price:[" + prices[0] + " TO " + prices[1] + "]");
			} else {
				solrQuery.addFilterQuery("price:[" + price + " TO *]");
			}
			params.append("&price=").append(price);
		}

		// 二、根据条件查询
		QueryResponse queryResponse = solrServer.query(solrQuery);
		SolrDocumentList results = queryResponse.getResults(); // 普通结果集
		int totalCount = (int) results.getNumFound();
		Map<String, Map<String, List<String>>> hl = queryResponse.getHighlighting(); // 高亮结果集

		// 三、处理结果集
		List<Product> list = new ArrayList<>();
		for (SolrDocument solrDocument : results) {
			Product product = new Product();
			// 设置数据
			String id = solrDocument.get("id").toString();
			String imgUrl = solrDocument.get("url").toString();
			// String price = solrDocument.get("price").toString();
			// String brandId = solrDocument.get("brandId").toString();
			// 判断高亮结果集中是否有名称
			List<String> names = hl.get(id).get("name_ik");
			if (names != null && names.size() > 0) {
				product.setName(names.get(0));
			} else {
				product.setName(solrDocument.get("name_ik").toString());
			}
			product.setId(Long.parseLong(id));
			product.setImgUrl(imgUrl);
			product.setBrandId(Long.parseLong(solrDocument.get("brandId").toString()));
			product.setPrice(solrDocument.get("price").toString());
			list.add(product);
		}

		// 四、创建分页对象并且填充数据
		Pagination pagination = new Pagination(productQuery.getPageNo(), productQuery.getPageSize(), totalCount, list);
		// 构建分页工具栏
		String url = "/Search";
		pagination.pageView(url, params.toString());
		return pagination;
	}

	// 前台系统品牌列表
	@Override
	public List<Brand> selectBrandsFromRedis() {
		Map<String, String> map = jedis.hgetAll("brand");
		Set<Entry<String, String>> entrySet = map.entrySet();
		List<Brand> brands = new ArrayList<>();
		for (Entry<String, String> entry : entrySet) {
			Brand brand = new Brand();
			brand.setId(Long.parseLong(entry.getKey()));
			brand.setName(entry.getValue());
			brands.add(brand);
		}
		return brands;
	}

	// 根据id获取品牌名称
	@Override
	public String selectBrandNameFromRedisById(Long brandId) {
		String name = jedis.hget("brand", String.valueOf(brandId));
		return name;
	}

	@Override
	public void insertProductToSolr(Long id) throws Exception {
		Product goods = productMapper.selectByPrimaryKey(id);
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", id); // 商品主键
		doc.addField("url", goods.getImgUrl()); // 商品图片
		doc.addField("name_ik", goods.getName()); // 商品名称
		doc.addField("brandId", goods.getBrandId()); // 商品所属品牌
		// 价格从sku中去取，显示最低价格在页面中
		// select price from bbs_sku where product_id = ? order by price asc
		// limit 0,1
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
