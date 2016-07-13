package com.quantilyse.collector.handler;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.quantilyse.analyser.sentiment.SimpleSentimentAnalyser;
import com.quantilyse.collector.model.Feed;

public class SentimentAnalysisHandler implements Handler {
	
	private static final Logger log = LoggerFactory.getLogger(SentimentAnalysisHandler.class);
	
	SimpleSentimentAnalyser analyzer;
	
	public SentimentAnalysisHandler()
	{
		this.analyzer = new SimpleSentimentAnalyser(new Properties());
	}

	@Override
	public boolean execute(HandlerContext context) {
		// TODO Auto-generated method stub
		Feed feed = context.getFeed();
		
		try {
			double sentimentLevel = this.analyzer.analyse(feed.getText());
			context.getFeed().setAttribute("sentimentLevel", sentimentLevel);
			log.info("Sentiment of feed: [" + feed.getProvider() + ":" + feed.getId() + "] is " + sentimentLevel + "; text:" + feed.getText());
		} catch (IOException e) {
			log.warn("Error while analysing the sentiment", e);
		}
		
		return false;
	}

	@Override
	public void init(Properties props) {
		// Nothing to do
	}
	
	@Override
	public void release() {
		// Nothing to do
	}

}
