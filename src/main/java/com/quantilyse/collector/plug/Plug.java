package com.quantilyse.collector.plug;

import com.quantilyse.collector.model.Feed;

public interface Plug {

	/**
	 * Initializes the plug
	 */
	public void init();
	
	/**
	 * Builds the feed object from the source data
	 */
	public Feed buildFeed();
	
	/**
	 * Start collecting the feed
	 */
	public void run();
	
	public void stop();
}
