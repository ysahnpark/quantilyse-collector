package com.quantilyse.analyser.sentiment;

import java.io.IOException;
import java.util.Properties;

import org.junit.Test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;

public class SimpleSentimentAnalyserTest {
	
	SimpleSentimentAnalyser analyzer;

	@Before
	public void setup() throws IOException
	{
		Properties config = new Properties();
		this.analyzer = new SimpleSentimentAnalyser(config);
		
		analyzer.init();
		
		Assert.assertEquals("Valence not found", new Integer(2), analyzer.getValence("ability"));
	}
	
	@Test
	public void testPositive() throws IOException
	{
		double valence = this.analyzer.analyse("I love java. It is a great language");
		
		double expected = 6;
		Assert.assertEquals(expected, valence, 0.01);
	}
	
	@Test
	public void testNegative() throws IOException
	{
		double valence = this.analyzer.analyse("I hate to say but sometimes life is hard");
		
		double expected = -4;
		Assert.assertEquals(expected, valence, 0.01);
	}
}
