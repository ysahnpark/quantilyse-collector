package com.quantilyse.analyser.sentiment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * A simple sentiment analyser that returns the normalized valence number of a text.
 * Uses sentiment lexicon
 * http://neuro.imm.dtu.dk/wiki/AFINN
 * to to get the valence number which are summed and normalized in the provided text.
 * 
 * @see http://mammothdata.com/sentiment-analysis-on-enrons-emails-with-apache-spark/
 * 
 * @author ysahn
 *
 */
public class SimpleSentimentAnalyser {
	
	private boolean initialized = false;
	private Properties config;
	
	private Map<String, Integer> lexiconEntries = new HashMap<String, Integer>();
	
	public SimpleSentimentAnalyser(Properties config)
	{
		this.config = config;
	}
	
	public synchronized void init() throws IOException
	{
		if (this.initialized) {
			return;
		}
		// load the emotion lexicon
		String lexiconFilename = this.config.getProperty("lexicon.file");
		BufferedReader br;
		if (lexiconFilename != null) {
			br = new BufferedReader(new FileReader(lexiconFilename));
		} else {
			InputStream istream = getClass().getResourceAsStream("/data/lexicon/AFINN-111.txt");
			br = new BufferedReader(new InputStreamReader(istream));
		}
		
		String line;
        while((line = br.readLine()) != null) {
        	
        	String[] entryParts = line.split("\\s+");

        	// we do this to skip multi-word phrases, like “can’t stand” and “cashing in”
        	if( entryParts.length == 2 ){
        		String word = entryParts[0];
        		Integer valence = Integer.parseInt(entryParts[1]);
        		lexiconEntries.put(word, valence);
        	}
        } 
        //istream.close();

		this.initialized = true;
	}

	/**
	 * Analyse a text for sentiment level: >0 -> positive sentiment, <0 negative sentiment
	 * @return
	 * @throws IOException 
	 */
	public float analyse(String text) throws IOException
	{
		float sentimentScore = 0;
		if (!this.initialized) {
			this.init();
		}
		
		String[] textTokens = text.split( "\\s+" );
		for( String textToken : textTokens )
		{
			if( this.lexiconEntries.containsKey(textToken))
			{
				int valence = this.lexiconEntries.get(textToken);
				sentimentScore = sentimentScore + (float)valence;
			}
		}
		
		
		return sentimentScore;
	}
	
	public Integer getValence(String term)
	{
		return lexiconEntries.get(term);
	}
}
