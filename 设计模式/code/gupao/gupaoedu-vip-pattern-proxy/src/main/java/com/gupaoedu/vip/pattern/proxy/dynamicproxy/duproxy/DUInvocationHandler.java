package com.gupaoedu.vip.pattern.proxy.dynamicproxy.duproxy;

import java.lang.reflect.Method;

public interface DUInvocationHandler {
    public Object invoke(Object proxy, Method method, Object[] args)
            throws Throwable;
}
