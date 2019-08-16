package com.xxxxx.devsuit.container.monitor.system;

import com.xxxxx.devsuit.container.ServerContextHolder;
import com.xxxxx.devsuit.container.event.FinishServiceEvent;
import com.xxxxx.devsuit.container.event.BeforeServiceEvent;
import com.xxxxx.devsuit.event.Subscribe;

public class ThreadHolderListner {

	  @Subscribe(priority = Integer.MAX_VALUE)
	    public void addHolder(BeforeServiceEvent event){
		  ServerContextHolder.set(event.value());
	    }

	    @Subscribe(priority = Integer.MAX_VALUE)
	    public void removeHolder(FinishServiceEvent event){
	    	ServerContextHolder.clean();
	    }
}
