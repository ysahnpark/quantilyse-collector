package com.quantilyse.collector;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.quantilyse.collector.handler.CompositeHandler;
import com.quantilyse.collector.handler.StoreElasticSearch;
import com.quantilyse.collector.plug.TwitterPlug;

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
	    
	    props.setProperty("consumerKey", "kJsgdgnxImSJPJxSTNn0LY2o3");
	    props.setProperty("consumerSecret", "zVkZu3sezVCUFFlwLCAdD2SIVFVBOWBpFMVXCrNKMeBRl2Yu06");
	    props.setProperty("token", "39404624-W9w8LkGMkqmEUdU3ZfW6DbFh4SvjLN9pnRhxmXIJQ");
	    props.setProperty("secret", "C136wqsaCrSpbo6OcBXLkizTd8gUpmaa4E0Rxz3eha4V3");

	    // @TODO: Use factory
	    CompositeHandler handler = new CompositeHandler();
	    handler.addHandler(new StoreElasticSearch(props));
	    
	    TwitterPlug twitterPlug = new TwitterPlug(props, handler);
	    twitterPlug.init();
	    try {
	    	twitterPlug.run();
	    } catch (Exception e) {
	    	log.info("Exception ", e);
	    }
	    twitterPlug.stop();
	    
	}
}
