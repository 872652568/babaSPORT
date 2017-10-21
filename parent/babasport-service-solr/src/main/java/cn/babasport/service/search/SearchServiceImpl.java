package cn.babasport.service.search;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Service;

import cn.babasport.pojo.page.Pagination;
import cn.babasport.pojo.product.Product;
import cn.babasport.pojo.product.ProductQuery;
@Service("searchService")
public class SearchServiceImpl implements SearchService {
	
	@Resource
	private SolrServer solrServer;

	@Override
	public Pagination selectProductsFromSolr(String keyword, Integer pageNo) throws Exception {
		// 一、创建SolrQuery封装查询条件
		SolrQuery solrQuery = new SolrQuery();
		ProductQuery productQuery = new ProductQuery(); // 结果分页需要的条件
		productQuery.setPageNo(Pagination.cpn(pageNo));
		productQuery.setPageSize(8);
		StringBuilder params = new StringBuilder(); // 分页工具栏需要的参数
		// 1、根据关键字检索
		if(keyword != null && !"".equals(keyword)){
			solrQuery.setQuery("name_ik:"+keyword);
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
		solrQuery.setStart(productQuery.getStartRow());  // 其始行
		solrQuery.setRows(productQuery.getPageSize());   // 每页显示条数
		
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
			String price = solrDocument.get("price").toString();
			String brandId = solrDocument.get("brandId").toString();
			// 判断高亮结果集中是否有名称
			List<String> names = hl.get(id).get("name_ik");
			if(names != null && names.size() > 0){
				product.setName(names.get(0));
			}else{
				product.setName(solrDocument.get("name_ik").toString());
			}
			product.setId(Long.parseLong(id));
			product.setImgUrl(imgUrl);
			product.setBrandId(Long.parseLong(brandId));
			product.setPrice(price);
			list.add(product);
		}
		
		// 四、创建分页对象并且填充数据
		Pagination pagination = new Pagination(productQuery.getPageNo(), productQuery.getPageSize(), totalCount, list);
		// 构建分页工具栏
		String url = "/Search";
		pagination.pageView(url, params.toString());
		return pagination;
	}


}
