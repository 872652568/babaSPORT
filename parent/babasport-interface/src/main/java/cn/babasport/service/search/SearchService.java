package cn.babasport.service.search;

import cn.babasport.pojo.page.Pagination;

public interface SearchService {
	
	//前台系统根据关键字进行检索
	public Pagination selectProductsFromSolr(String keyword ,Integer pageNo) throws Exception;

}
