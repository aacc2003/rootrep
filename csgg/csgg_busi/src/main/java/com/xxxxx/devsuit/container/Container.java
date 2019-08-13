package com.xxxxx.devsuit.container;

import com.xxxxx.devsuit.result.StandardResult;

public interface Container {

	<ORDER, RESULT extends StandardResult> RESULT execute(ORDER order, String serviceName, OperationContext opContext);
}
