package com.quantilyse.collector.handler;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.quantilyse.collector.model.Feed;

public class SimpleLoggerHandler implements Handler {
	
	private static final Logger log = LoggerFactory.getLogger(SimpleLoggerHandler.class);

	@Override
	public boolean execute(HandlerContext context) {
		// TODO Auto-generated method stub
		Feed feed = context.getFeed();
		log.debug("Handling feed: [" + feed.getProvider() + ":" + feed.getId() + "] text:" + feed.getText());
		return false;
	}

	@Override
	public void init(Properties props) {
		// Nothing to do
	}
	
	@Override
	public void release() {
		// Nothing to do
	}

}
