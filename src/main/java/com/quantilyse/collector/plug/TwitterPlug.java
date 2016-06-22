package com.quantilyse.collector.plug;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.quantilyse.collector.handler.Handler;
import com.quantilyse.collector.handler.HandlerContext;
import com.quantilyse.collector.model.Feed;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.event.Event;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Feed Plug that listens to twitter's public streams.
 * 
 * @see https://github.com/twitter/hbc
 * 
 * @author ysahn
 *
 */
public class TwitterPlug implements Plug
{
	private static final Logger log = LoggerFactory.getLogger(TwitterPlug.class);
	
	private ObjectMapper mapper = new ObjectMapper();
	
	private Client client; // hosebirdClient;
	private Properties config;
	private Handler handler;
	
	/** Set up your blocking queues: Be sure to size these properly based on expected TPS of your stream */
	private BlockingQueue<String> msgQueue = new LinkedBlockingQueue<String>(100000);
	
	private BlockingQueue<Event> eventQueue = new LinkedBlockingQueue<Event>(1000);

	public TwitterPlug(Properties config, Handler handler)
	{
		this.config = config;
		this.handler = handler;
	}

	// @Override
	public void init() 
	{

		/** Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth) */
		Hosts hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
		StatusesFilterEndpoint hosebirdEndpoint = new StatusesFilterEndpoint();
		// Optional: set up some followings and track terms
		
		String terms = this.config.getProperty("terms");
		List<String> termList = Lists.newArrayList(terms.split(","));
		//hosebirdEndpoint.followings(followings);
		hosebirdEndpoint.trackTerms(termList);
		log.info("Using Terms: " + termList.toString());

		// These secrets should be read from a config file
		String consumerKey = this.config.getProperty("consumerKey");
		String consumerSecret = this.config.getProperty("consumerSecret");
		String token = this.config.getProperty("token");
		String secret = this.config.getProperty("secret");
		Authentication auth = new OAuth1(consumerKey, consumerSecret, token, secret);
		
		String username = this.config.getProperty("username");
		String password = this.config.getProperty("password");
		//Authentication auth = new BasicAuth(username, password);
		log.info("BadicAuth with " + username + " " + password);
		
		ClientBuilder builder = new ClientBuilder()
				  .name("Hosebird-Client-01")            // optional: mainly for the logs
				  .hosts(hosebirdHosts)
				  .authentication(auth)
				  .endpoint(hosebirdEndpoint)
				  .processor(new StringDelimitedProcessor(this.msgQueue))
				  .eventMessageQueue(this.eventQueue);   // optional: use this if you want to process client events

		this.client = builder.build();
	}
	
	// @Override
	public Feed buildFeed(String sourceData)
	{
		Feed feed = new Feed("twitter");
		feed.setOriginalData(sourceData);
		
		try {
			Map<String,Object> userData = this.mapper.readValue(sourceData, Map.class);
			feed.setId(String.valueOf(userData.get("id")));
			feed.setText((String)userData.get("text"));
			// TODO: parase date
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.warn("Error parsing JSON message.", e);
		}

		
		return feed;
	}
	
	// @Override
	public void run() throws InterruptedException
	{
		// Attempts to establish a connection.
		this.client.connect();
		// Do whatever needs to be done with messages
	    for (int msgRead = 0; msgRead < 1000; msgRead++)
	    {
			String msg = this.msgQueue.take();
			//System.out.println(msg);
			Feed feed = this.buildFeed(msg);
			HandlerContext ctx = new HandlerContext(feed);
			this.handler.execute(ctx);
	    }
	}
	
	// @Override
	public void stop()
	{
	    this.client.stop();
	}
}
