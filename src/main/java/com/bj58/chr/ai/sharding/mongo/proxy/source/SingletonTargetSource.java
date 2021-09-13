package com.bj58.chr.ai.sharding.mongo.proxy.source;

import java.io.Serializable;

/**
 * 目标单例 保持对象
 * @author liuwenqing02
 */
public class SingletonTargetSource implements TargetSource, Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 5403453457258658807L;
    
    private final Object target;


	public SingletonTargetSource(Object target) {
		this.target = target;
	}


	@Override
	public Class<?> getTargetClass() {
		return this.target.getClass();
	}

	@Override
	public Object getTarget() {
		return this.target;
	}

	@Override
	public boolean isStatic() {
		return true;
	}

}
