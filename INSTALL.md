# Quantilyse -  Installing and Running #

## Installing Required Components ##
Quantilyse has the following requirements:
- [Java 8](https://java.com/en/download/)
- [Gradle](https://gradle.org/gradle-download/)
- [ElasticSearch](https://www.elastic.co/downloads/elasticsearch)
- [Kibana](https://www.elastic.co/products/kibana)

Just follow the installation of the software as specified in the links for you operating system.

Additionally you can install Kibana sense plugin which provides a simple ES tool for querying.
` ./bin/kibana plugin --install elastic/sense` 


## Installing Quantilyse ##
At the moment, you will have to clone the repo and build it.

`git clone https://github.com/ysahnpark/quantilyse-collector`

Then in the under the folder, run the gradle build

For *nix: `./gradlew build `

For Windows: `./gradlew.bat build `

When successful, the buid should have created created the following jar file `build/libs/quantilyse-collector-0.1.0.jar`

The server application will try to pick up the `.env` file in the folder of execution.

Create the `.env` file with the following data 
 
	# There are secret keys here, shhhh!
	# This file SHOULD NOT go into repo.
	
	# Twitter's
	plug.twitter.terms=<list of terms that the server should subscribe to, e.g. education (no quotes)>
	plug.twitter.consumerKey=<Your Twitter's consumer key>
	plug.twitter.consumerSecret=<Your Twitter's consumer secret>
	plug.twitter.secret=<Your Twitter's secret>
	plug.twitter.token=<Your Twitter's token> 


### Twitter Oauth ###
If you do not have an twitter account (really???), please go ahead and create one.
Then go do [Twitter App](https://apps.twitter.com/) and create an application.
A twitter application will generate all the keys and and token required for connecting and
receiving [Twitter stream](https://dev.twitter.com/streaming/overview).


## Running the server ##
It's time to run the server.=!

First run elastic search:

`<ES_INSTALL_DIR>/bin/elasticsearch`

Then run kibana

`<KIBANA_INSTALL_DIR>/bin/kibana`

Now you can run the server

`java -jar build/libs/quantilyse-collector-0.1.0.jar`

If the configuration, e.g. Twitter Oauth values, are correct, the server should start collecting
feeds.

You should be able to see the data using Kibana. Launch your browser and open up `http://127.0.0.1:5601`.

Note: On Mac, localhost may not work, use 127.0.0.1 instead. 

If you have installed Kibana sense, you should be able to run ES queries on the following page:
`http://127.0.0.1:5601/app/sense`.

** This system is still under development **