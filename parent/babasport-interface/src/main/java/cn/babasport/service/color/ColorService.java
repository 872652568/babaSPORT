package cn.babasport.service.color;

import java.util.List;

import cn.babasport.pojo.product.Color;

public interface ColorService {
	/**
	 * 
	 * 说明：查询颜色结果集
	 * @return
	 * @author Mr.Song
	 * @time：2017年10月19日 下午5:00:26
	 */
	public List<Color> selectColorByParentIdNotEqualsZero();
}
