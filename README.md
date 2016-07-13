# Quantilyse Overview #

## About ##
Quantilyse is a platform for analysing feeds and data in the social network. 
Currently a simple sentiment analyser is included.

With Quantilyse individuals and organizations can obtain trends and quantified metrics
providing valuable insights for social analysis, marketing strategy, product roadmap, etc.

### How it works ###
Quantilyse consists of 

- *QuantilyseServer (Collector)* The server that collects the data from source (e.g. Twitter) and process them through different handlers. By default, the the processing 
includes the analysing sentiment and storing to ElasticSearch. 
- *[Kibana](https://www.elastic.co/products/kibana) (Visualizer)* Visualised the data
stored in ElasticSearch 

#### How the sentiment analysis work ####
This system includes out-of-the box a simple sentiment analyser.  The Simple sentiment analyser is
based on [this article](http://mammothdata.com/sentiment-analysis-on-enrons-emails-with-apache-spark/).
The mechanism is very simple: 
1. !t loads a sentiment lexicon which is a text file containing tuples of lexicon and valence.
2. For each feed, it tokenizes the text and calculates the summation of the valence of all words.

For future work, we will add more advanced NLP-based sentiment analysis using 
[Stanford's Sentiment Analyser](http://nlp.stanford.edu/sentiment/)

## Documentation (WIP) ##
- [Installing and Running](INSTALL.md)
- [Development](DEVELOPMENT.md)

## Analyser vs Analyzer ##
The domain name quantiyzer.com was taken so I decided to go the "British" way using 's' instead of 'z'.


## Copyright, License, and Contributors Agreement ##
Young-Suk Ahn Park.

Licensed under MIT license.

https://apps.twitter.com/

./bin/kibana plugin --install elastic/sense 

./bin/kibana

Note: On Mac, localhost does not work. use 127.0.0.1 instead 
http://127.0.0.1:5601/app/sense