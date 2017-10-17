package cn.babasport.utils.converter;

import org.springframework.core.convert.converter.Converter;

//自定义类型转换器 :去掉查询条件中的空格
public class CustomParamsConverter implements Converter<String, String>{

	@Override
	public String convert(String source) {
		if (source != null && !"".equals(source)) {
			source = source.trim();
			
			//输入一组空格变成空字符串
			if (!"".equals(source)) {
				return source;
			}
			
		}
		
		return null;
	}
		
}
