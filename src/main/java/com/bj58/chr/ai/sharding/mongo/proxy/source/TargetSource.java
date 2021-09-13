package com.bj58.chr.ai.sharding.mongo.proxy.source;

public interface TargetSource extends TargetClassAware {

	boolean isStatic();

	public Object getTarget();

}
