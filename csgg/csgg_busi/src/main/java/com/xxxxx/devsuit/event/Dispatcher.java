package com.xxxxx.devsuit.event;

import java.util.concurrent.Executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Dispatcher {

	private static final Logger logger = LoggerFactory.getLogger(Dispatcher.class);
	
	private Executor executor;
	
	public Dispatcher(Executor executor) {
		this.executor = executor;
	}

	public void execute(DispatcherTask task) {
		if (task.isAsync()) {
			executor.execute(task);
		} else {
			task.run();
		}
	}
	
	public static class DispatcherTask implements Runnable{

		private SubscriberWrapper wrapper ;
		
		private Object[] events;
				
		public DispatcherTask(SubscriberWrapper wrapper , Object[] events) {
			this.wrapper = wrapper;
			this.events = events;
		}
		
		public boolean isAsync() {
			return wrapper.isAsync();
		}
		
		@Override
		public void run() {
			wrapper.invocation(events);
		}
		
	}
}
