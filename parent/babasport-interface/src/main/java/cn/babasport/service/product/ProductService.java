package cn.babasport.service.product;

import cn.babasport.pojo.page.Pagination;

public interface ProductService {
		//商品列表查询
		public Pagination selectProductHavePage(String name,Long brandId,Boolean isShow,Integer pageNo);
}
