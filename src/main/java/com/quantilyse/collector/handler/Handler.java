package com.quantilyse.collector.handler;

import java.util.Properties;

import com.quantilyse.collector.model.Feed;

/**
 * Interface for the handler.
 * 
 * @author ysahn
 *
 */
public interface Handler {

	/**
	 * Handles the data.
	 *  
	 * @param data
	 * 
	 * @return true if the processing of this feed has been completed, 
	 * or false if the processing of this Context should be delegated 
	 * to a subsequent handler.
	 * Basically, true will stop and return (i.e. will not call next
	 * handler if it was chained, e.g. in CompositeHandler )
	 */
	public boolean execute(HandlerContext context);
	
	/**
	 * Releases any resources allocated
	 */
	public void init(Properties props) throws RuntimeException;
	
	/**
	 * Releases any resources allocated
	 */
	public void release();
}
