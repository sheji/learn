package com.atguigu.proxy;

import java.lang.reflect.Method;

import com.atguigu.pojo.Calculate;
import com.atguigu.pojo.Calculator;
import com.atguigu.pojo.utils.LogUtils;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibProxyFactory {

	public static void main(String[] args) {
		Calculator target = new Calculator();
		// jdk动态代理要求被代理的对象必须有接口
		// Jdk动态代理创建出来的代理对象，会实现所有目标对象的接口
		Calculator proxy = (Calculator) createCglibProxy(target);

		System.out.println( proxy instanceof Calculate );
		System.out.println( proxy instanceof Calculator );
		
//		System.out.println(proxy.add(100, 100));
//		System.out.println(proxy.div(100, 0));
	}
	
	
	public static Object createCglibProxy(Object target) {

		// cglib是通过创建一个类去继承指定的类，达到增强效果

		// 这是Cglibr工具类，可以用来产生代理对象
		Enhancer enhancer = new Enhancer();
		// 设置需要继承的类
		enhancer.setSuperclass(target.getClass());
		// 设置一个方法拦截器
		enhancer.setCallback(new MethodInterceptor() {
			/**
			 * 在这里给原有方法做增强操作 第一个参数是代理对象<br/>
			 * 第二个参数是目标方法的反射类<br/>
			 * 第三个参数是调用方法时传入的参数<br/>
			 * 第四个参数是调用代理的方法的反射类<br/>
			 */
			@Override
			public Object intercept(Object proxy, Method method, Object[] args, MethodProxy methodProxy)
					throws Throwable {

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
		
		// 创建cglib代理对象
		return enhancer.create();

	}

}
