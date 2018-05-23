package cn.babasport.mqmessage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;

import cn.babasport.pojo.product.Color;
import cn.babasport.pojo.product.Product;
import cn.babasport.pojo.product.Sku;
import cn.babasport.service.cms.CmsService;
import cn.babasport.service.staticpage.StaticPageService;

/**
 * 
 * @ClassName: CmsCustomMessageListener
 * @Description: 自定义消息监听器
 * @date 2017年10月24日 下午12:16:58
 */
public class CmsCustomMessageListener implements MessageListener {

	@Resource
	private StaticPageService staticPageService;
	
	@Resource
	private CmsService cmsService;
	
	/*
	 * (non-Javadoc)
	 * <p>Title: onMessage</p> 
	 * <p>Description: 生成静态页</p> 
	 * @param arg0 
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Override
	public void onMessage(Message message) {
		try {
			// 获取消息
			ActiveMQTextMessage activeMQTextMessage = (ActiveMQTextMessage) message;
			String id = activeMQTextMessage.getText();
			System.out.println("cms---id:"+id);
			// 消费消息
			Map<String, Object> rootMap = new HashMap<>();
			// 商品信息、库存信息、颜色信息
			Product product = cmsService.selectProductById(Long.parseLong(id));
			rootMap.put("product", product);
			List<Sku> skus = cmsService.selectSkusByProductId(Long.parseLong(id));
			rootMap.put("skus", skus);
			Set<Color> colors = new HashSet<>();
			for (Sku sku : skus) {
				colors.add(sku.getColor());
			}
			rootMap.put("colors", colors);
			staticPageService.index(id, rootMap);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}