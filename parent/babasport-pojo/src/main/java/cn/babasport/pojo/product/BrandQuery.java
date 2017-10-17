package cn.babasport.pojo.product;

import java.io.Serializable;
//业务层 封装查询条件
public class BrandQuery implements Serializable {

	// part1:查询条件
	private String name; // 品牌名称
	private Integer isDisplay; // 是否可用 0 不可用 1 可用
	
	// part2:分页属性
	private Integer startRow; 		 // 其始行
	private Integer pageSize = 3;    // 每页显示的条数
	private Integer pageNo = 1; 	 // 当前页码
	
	public Integer getStartRow() {
		return startRow;
	}
	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.startRow = (pageNo -1) * pageSize;
		this.pageSize = pageSize;
	}
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.startRow = (pageNo -1) * pageSize;
		this.pageNo = pageNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getIsDisplay() {
		return isDisplay;
	}
	public void setIsDisplay(Integer isDisplay) {
		this.isDisplay = isDisplay;
	}
}
