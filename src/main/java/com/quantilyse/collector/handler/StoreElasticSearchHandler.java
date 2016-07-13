package com.quantilyse.collector.handler;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Properties;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class StoreElasticSearchHandler implements Handler
{
	
	private static final Logger log = LoggerFactory.getLogger(StoreElasticSearchHandler.class);
	
	protected Properties props;
	protected ObjectMapper mapper = new ObjectMapper(); // create once, reuse
	protected Client client;

	public StoreElasticSearchHandler(Properties props)
	{
		this.props = props;
		// I am trying to serialize into ISO format but it is throwing NumberFormatException
		//this.mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		//this.mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

	}

	/**
	 * Initializes the handler by creating a client instance.
	 * @throws UnknownHostException
	 */
	public synchronized void init(Properties props) throws RuntimeException
	{
		if (this.client != null) {
			return;
		}
		String host = this.props.getProperty("host", "localhost");
		int port = Integer.parseInt(this.props.getProperty("port", "9300"));

		log.info("Initializing {host: " + host + ", port:"+port + "}");
		try {
			this.client = TransportClient.builder().build()
			        .addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
		} catch (UnknownHostException e) {
			new IllegalArgumentException(e);
		}
	}
	
	public void release()
	{
		this.client.close();
	}
	
	/**
	 * Mostly for testing purpose
	 * @return
	 */
	public Client getEsClient()
	{
		return this.client;
	}
	

	@Override
	public boolean execute(HandlerContext context)
	{
		
		byte[] json;
		try {
			json = mapper.writeValueAsBytes(context.getFeed());

			// @TODO: parameterize index & type
			IndexResponse response = this.client.prepareIndex("stream", "feed")
			        .setSource(json)
			        .get();
			
			HashMap<String, Object> result = new HashMap<String, Object>();
			result.put("index", response.getIndex());
			result.put("type", response.getType());
			result.put("id", response.getId());
			result.put("version", response.getVersion());
			result.put("created", response.isCreated());
			
			context.getAttributes().put("result:StoreElasticSearch", result);
			
			log.info("Indexed: {index: "+response.getIndex()+", type:"+response.getType()
				+", id:"+response.getId()+", version:"+ response.getVersion() +", created:"+ response.isCreated());
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			throw new IllegalArgumentException(e);
		}
		
		return false;
	}

}
