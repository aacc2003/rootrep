package com.xxxxx.devsuit.event;

public interface Notifier {

	<L> void register(L listner);
	
	<L> void unregister(L listner);
	
	boolean dispatcher(Object... events);
}
