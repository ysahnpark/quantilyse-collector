package com.quantilyse.utils;

import java.util.Map;
import java.util.Properties;

public class PropertiesUtils {

	public static Properties filterByPrefix(Properties source, String prefix, boolean removePrefix)
	{
		Properties result = new Properties();
		
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
