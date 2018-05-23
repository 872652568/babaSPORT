package cn.babasport.mqmessage;

import javax.annotation.Resource;
import javax.jms.Message;
import javax.jms.MessageListener;

import org.apache.activemq.command.ActiveMQTextMessage;

import cn.babasport.service.search.SearchService;

//自定义消息监听器
public class CustomMessageListener implements MessageListener {
	
	@Resource
	private SearchService searchService;

	/*
	 * (non-Javadoc)
	 * <p>Title: onMessage</p> 
	 * <p>Description: 商品保存到索引库中</p> 
	 * @param arg0 
	 * @see javax.jms.MessageListener#onMessage(javax.jms.Message)
	 */
	@Override
	public void onMessage(Message message) {
		try {
			// 获取消息
			ActiveMQTextMessage activeMQTextMessage = (ActiveMQTextMessage) message;
			String id = activeMQTextMessage.getText();
			System.out.println("solr---id:"+id);
			// 消费消息
			searchService.insertProductToSolr(Long.parseLong(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}



}
