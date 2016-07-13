package com.quantilyse.collector.handler;

import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Properties;

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
	
	@Override
	public void init(Properties props) {
		synchronized(this) {
			for (ListIterator<Handler> iterator = this.handlers.listIterator(); iterator.hasNext();) {
				final Handler listElement = iterator.next();
				listElement.init(props);
			}
		}
	}

	@Override
	public void release() {
		synchronized(this) {
			for (ListIterator<Handler> iterator = this.handlers.listIterator(this.handlers.size()); iterator.hasPrevious();) {
				final Handler listElement = iterator.previous();
				listElement.release();
			}
		}
	}

}
