package com.quantilyse.collector.model;

import java.util.Date;
import java.util.HashMap;

/**
 * Bean class that represents a stream (feed)
 * @author ysahn
 *
 */
public class Feed {

	private String provider; // "twitter"
	private String originalData;
	private String id; // twitter's id
	
	private Date createdAt;
	private String text;
	private String lang;
	
	private int favoriteCount; // favorite, like, or equivalent.
	
	private Place place;
	
	private HashMap<String, String> attributes = new HashMap<String, String>();

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getOriginalData() {
		return originalData;
	}

	public void setOriginalData(String originalData) {
		this.originalData = originalData;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public int getFavoriteCount() {
		return favoriteCount;
	}

	public void setFavoriteCount(int favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	public Place getPlace() {
		return place;
	}

	public void setPlace(Place place) {
		this.place = place;
	}

	public HashMap<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(HashMap<String, String> attributes) {
		this.attributes = attributes;
	}
	
	public String getAttribute(String key)
	{
		return this.attributes.get(key);
	}
	
	public void setAttribute(String key, String value)
	{
		this.attributes.put(key, value);
	}
	
}
