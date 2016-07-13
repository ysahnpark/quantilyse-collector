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
	
	/**
	 * Initializes the analyzer by loading the valence file into the memory
	 * @throws IOException
	 */
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

        	// Skipping phrases with multiple terms, e.g. “not good”. Just simplification.
        	if( entryParts.length == 2 ){
        		String word = entryParts[0];
        		Integer valence = Integer.parseInt(entryParts[1]);
        		this.lexiconEntries.put(word, valence);
        	}
        } 
        //istream.close();

		this.initialized = true;
	}

	/**
	 * Analyse a text for sentiment level: >0 -> positive sentiment, <0 negative sentiment
	 * @return a number that represents the sentiment magnitude. 
	 * @throws IOException 
	 */
	public double analyse(String text) throws IOException
	{
		double sentimentScore = 0;
		if (!this.initialized) {
			this.init();
		}
		
		String[] textTokens = text.split( "\\s+" );
		for( String textToken : textTokens )
		{
			if( this.lexiconEntries.containsKey(textToken))
			{
				int valence = this.lexiconEntries.get(textToken);
				sentimentScore = sentimentScore + (double)valence;
			}
		}
		
		
		return sentimentScore;
	}
	
	/**
	 * Returns the valence of the given term
	 * @param term
	 * @return
	 */
	public Integer getValence(String term)
	{
		return lexiconEntries.get(term);
	}
}
