package com.quantilyse.collector.handler;

import java.util.HashMap;

import com.quantilyse.collector.model.Feed;

public class HandlerContext {

	public Feed feed;
	
	public HashMap<String, Object> attributes = new HashMap<String, Object>();
	
	public HandlerContext(Feed feed)
	{
		if (feed == null) {
			throw new IllegalArgumentException();
		}
		this.feed = feed;
	}

	public Feed getFeed() {
		return feed;
	}

	public void setFeed(Feed feed) {
		this.feed = feed;
	}

	public HashMap<String, Object> getAttributes() {
		return attributes;
	}

	public void setAttributes(HashMap<String, Object> attributes) {
		this.attributes = attributes;
	}
	
	public Object getAttribute(String key)
	{
		return this.attributes.get(key);
		
	}
	
}
