package com.quantilyse.collector.handler;

import java.util.ArrayList;

public class CompositeHandler implements Handler {
	
	private ArrayList<Handler> handlers = new ArrayList<Handler>(); 

	@Override
	public boolean execute(HandlerContext context) {
		boolean completed = false;
		for (Handler handler: handlers) {
			completed = handler.execute(context); 
			if (completed) {
				return completed;
			}
		}
		return completed;
	}
	
	public void addHandler(Handler handler)
	{
		this.handlers.add(handler);
	}

}
