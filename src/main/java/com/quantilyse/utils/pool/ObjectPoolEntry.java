package com.quantilyse.utils.pool;

public class ObjectPoolEntry<T> {
	
	private final T object;
	private boolean inUse = false;
	
	public ObjectPoolEntry(T object)
	{
		this.object = object;
	}
	

}
