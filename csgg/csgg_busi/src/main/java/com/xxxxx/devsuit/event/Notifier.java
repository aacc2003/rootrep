package com.xxxxx.devsuit.event;

public interface Notifier {

	<L> void register(L listener);
	
	<L> void unregister(L listener);
	
	boolean dispatcher(Object... events);
}
