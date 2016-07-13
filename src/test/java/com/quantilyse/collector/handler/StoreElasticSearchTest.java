package com.quantilyse.collector.handler;

import java.net.UnknownHostException;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.junit.Test;

import com.quantilyse.collector.model.Feed;

public class StoreElasticSearchTest {

	@Test
	public void testExecute() throws UnknownHostException
	{
		String file = getClass().getResource("/testdata/twitter.sample1.json").getFile();
		
		Properties props = new Properties();
		StoreElasticSearchHandler storeEsHandler = new StoreElasticSearchHandler(props);
		storeEsHandler.init(new Properties());
		
		Feed feed = new Feed("test");
		feed.setCreatedAt(new Date());
		feed.setId("123");
		feed.setLang("en");
		feed.setText("This is an intersting text");
		feed.setAttribute("sentiment", "positive");
		
		HandlerContext ctx = new HandlerContext(feed);
		
		storeEsHandler.execute(ctx);
		System.out.println(ctx.getAttributes().toString());
		
		HashMap<String, Object> result = (HashMap<String, Object>)ctx.getAttribute("result:StoreElasticSearch");
		String id = (String)result.get("id");
		
		GetResponse getResponse = storeEsHandler.getEsClient().prepareGet("stream", "feed", id).get();
		System.out.println(getResponse.getSource().toString());
		
		DeleteResponse response = storeEsHandler.getEsClient().prepareDelete("stream", "feed", id)
		        .get();
		
		storeEsHandler.release();
	}
	
	
}
