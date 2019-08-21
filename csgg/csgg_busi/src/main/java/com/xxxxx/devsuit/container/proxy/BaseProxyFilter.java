package com.xxxxx.devsuit.container.proxy;

public abstract class BaseProxyFilter implements ProxyFilter {
	
	private int order;
	
	public BaseProxyFilter(int order) {
		
		this.order = order;
	}

	@Override
	public int compareTo(ProxyFilter o) {
		
		return order > o.getOrder() ? -1 : (order == o.getOrder() ? 0 : 1);
	}

	@Override
	public int getOrder() {
		
		return order;
	}

}
