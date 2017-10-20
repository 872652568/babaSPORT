package cn.babasport.service.color;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.babasport.mapper.product.ColorMapper;
import cn.babasport.pojo.product.Color;
import cn.babasport.pojo.product.ColorQuery;

@Service("colorService")
public class ColorServiceImpl implements ColorService {

	@Resource
	private ColorMapper colorMapper;

	@Override
	public List<Color> selectColorByParentIdNotEqualsZero() {
		ColorQuery colorQuery = new ColorQuery();
		colorQuery.createCriteria().andParentIdNotEqualTo(0L);
		List<Color> colors = colorMapper.selectByExample(colorQuery);
		return colors;
	}

}
