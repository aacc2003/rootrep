package com.xxxxx.devsuit.event;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotifierBus implements Notifier {
	
	public final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public <L> void register(L listner) {
		// TODO Auto-generated method stub

	}

	@Override
	public <L> void unregister(L listner) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean dispatcher(Object... events) {
		// TODO Auto-generated method stub
		return false;
	}

}
