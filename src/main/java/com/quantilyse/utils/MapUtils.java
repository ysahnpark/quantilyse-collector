package com.quantilyse.utils;

import java.util.HashMap;
import java.util.Map;

public class MapUtils {

	public static Map<Object, Object> filterByPrefix(Map<Object, Object> source, String prefix, boolean removePrefix)
	{
		Map<Object, Object> result = new HashMap<Object, Object>();
		
		for(Map.Entry<Object, Object> entry: source.entrySet())
		{
			if (entry.getKey() instanceof String && ((String)entry.getKey()).startsWith(prefix)) {
				String key = (String)entry.getKey();
				if (removePrefix) {
					key = key.substring(prefix.length());
				}
				result.put(key, entry.getValue());
			}
		}
		
		return result;
	}
}
