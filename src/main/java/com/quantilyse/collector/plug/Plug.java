package com.quantilyse.collector.plug;

import com.quantilyse.collector.model.Feed;

/**
 * Interface for the Plug classes that collects the feed from provider, e.g. Twitter.
 * @author ysahn
 *
 */
public interface Plug extends Runnable {

	/**
	 * Initializes the plug
	 */
	public void init();
	
	/**
	 * Builds the feed object from the source data
	 */
	public Feed buildFeed(String sourceData);
	
	/**
	 * Start collecting the feed
	 */
	public void start();
	
	/**
	 * Stop collecting the feed
	 */
	public void stop();
}
