package com.xxxxx.devsuit.container.event;

public interface ContainerEvent <S, V>{

	S source();
	V value();
}
