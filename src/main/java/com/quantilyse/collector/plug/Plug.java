package com.quantilyse.collector.plug;

import com.quantilyse.collector.model.Feed;

/**
 * Interface for the Plug classes that collects the feed from provider, e.g. Twitter.
 * @author ysahn
 *
 */
public interface Plug extends Runnable {

	/**
	 * Initializes the plug.
	 * E.g. Establish DB connection.
	 */
	public void init();
	
	/**
	 * Builds the feed object from the source data.
	 * This is called by the worker thread each time a feed is received, just before passing to the
	 * handler.
	 */
	public Feed buildFeed(String sourceData);
	
	/**
	 * Start collecting the feeds.
	 * Spawns a worker thread for the collection of feeds.
	 */
	public void start();
	
	/**
	 * Stop collecting the feeds.
	 */
	public void stop();
}
