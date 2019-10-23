package com.atguigu.proxy;

import static org.hamcrest.CoreMatchers.nullValue;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import com.atguigu.pojo.Calculate;
import com.atguigu.pojo.Calculator;
import com.atguigu.pojo.utils.LogUtils;

import sun.util.logging.resources.logging;

public class JdkProxyFactory {

	public static Object createJdkProxy(Object target) {
		// 创建一个jdk动态代理对象
		/**
		 * 代理还可以在原有功能的基本上，添加更多功能。而不改变原来的代码。
		 */
		return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
				new InvocationHandler() {

					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						// result用来接收目标方法的返回值
						Object result = null;
						try {
							try {
								// 前置通知
								LogUtils.logBefore(method.getName(), args);
								// 调用目标对象的方法
								result = method.invoke(target, args);
							} finally {
								// 后置通知
								LogUtils.logAfter(method.getName(), args);
							}
							// 返回通知
							LogUtils.logAfterReturning(method.getName(), result);
						} catch (Exception e) {
							// 异常通知
							LogUtils.logAfterThrowing(method.getName(), e);
							throw e;
						}
						// 返回目标对象的返回值
						return result;
					}
				});

	}

	public static void main(String[] args) {

//		Calculate target = new Calculator();
		// jdk动态代理要求被代理的对象必须有接口
		// Jdk动态代理创建出来的代理对象，会实现所有目标对象的接口
//		Calculator proxy = (Calculator) createJdkProxy(target);

//		System.out.println( proxy instanceof Calculate );
//		System.out.println( proxy instanceof Calculator );
		
//		System.out.println(proxy.add(100, 100));
//		System.out.println(proxy.div(100, 0));
		
	}

}
