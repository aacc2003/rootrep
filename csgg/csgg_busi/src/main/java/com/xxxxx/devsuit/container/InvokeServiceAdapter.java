package com.xxxxx.devsuit.container;

import com.xxxxx.devsuit.result.StandardResult;

public abstract class InvokeServiceAdapter<ORDER, RESULT extends StandardResult> extends AbstractInvokeService<ORDER, RESULT> {

	@Override
	public void before(ServiceContext<ORDER, RESULT> serviceContext) {
		
	}

	@Override
	public void after(ServiceContext<ORDER, RESULT> serviceContext) {
		
	}

	@Override
	public void end(ServiceContext<ORDER, RESULT> serviceContext) {
		
	}

}
