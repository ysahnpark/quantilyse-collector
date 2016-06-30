package com.quantilyse.analyser.sentiment;

import java.io.IOException;
import java.util.Properties;

import org.junit.Test;

import org.junit.Assert;

public class SimpleSentimentAnalyserTest {

	@Test
	public void testInit() throws IOException
	{
		Properties config = new Properties();
		SimpleSentimentAnalyser sentAnal = new SimpleSentimentAnalyser(config);
		
		sentAnal.init();
		
		Assert.assertEquals("Valence not found", new Integer(2), sentAnal.getValence("ability"));
	}
}
