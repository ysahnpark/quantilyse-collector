package com.quantilyse.collector;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.quantilyse.collector.handler.CompositeHandler;
import com.quantilyse.collector.handler.SentimentAnalysisHandler;
import com.quantilyse.collector.handler.SimpleLoggerHandler;
import com.quantilyse.collector.handler.StoreElasticSearchHandler;
import com.quantilyse.collector.plug.TwitterPlug;
import com.quantilyse.utils.PropertiesUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Spring component that registers as ApplicationReadyEvent listener.
 * 
 * This class is  
 * 
 * @author ysahn
 *
 */
@Component
public class Bootstrap
{	
	private static final Logger log = LoggerFactory.getLogger(Bootstrap.class);

	@EventListener
	public void onApplicationReady(ApplicationReadyEvent ready) {
	    log.info("onApplicationReady");
	    
	    Properties props = new Properties();
	    props.setProperty("terms", "education");
	    
	    Properties env = new Properties();
	    FileInputStream fileInput;
		try {
			fileInput = new FileInputStream(new File(".env"));
		    env.load(fileInput);
		    props.putAll(env);
		} catch (IOException e) {
			log.warn(".env file not found", e);
		}
		
		Properties plugProps = PropertiesUtils.filterByPrefix(env, "plug.twitter.", true);
		
		log.debug("consumerKey: " + plugProps.getProperty("consumerKey"));
		log.debug("consumerSecret: " + plugProps.getProperty("consumerSecret"));
		log.debug("token: " + plugProps.getProperty("token"));
		log.debug("secret: " + plugProps.getProperty("secret"));

	    // @TODO: Use factory
	    CompositeHandler handler = new CompositeHandler();
	    handler.addHandler(new SentimentAnalysisHandler());
	    handler.addHandler(new StoreElasticSearchHandler(props));
	    //handler.addHandler(new SimpleLoggerHandler());
	    // TODO: provide proper
	    Properties handlerProps = PropertiesUtils.filterByPrefix(env, "handler.", true);
	    handler.init(handlerProps);
	    
	    TwitterPlug twitterPlug = new TwitterPlug(plugProps, handler);
	    twitterPlug.init();
	    try {
	    	twitterPlug.start();
	    	// Testing pupose: Listen for 5 seconds
	    	// log.info("Waiting for 10 secs ");
		    //Thread.sleep(10*1000);
	    } catch (Exception e) {
	    	log.warn("Exception ", e);
	    }
	    // TODO: Graceful shutdownt - release all the resources (Handlers);
	    //twitterPlug.stop();
	    
	}
}
