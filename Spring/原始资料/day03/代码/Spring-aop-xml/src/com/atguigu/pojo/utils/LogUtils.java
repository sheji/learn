package com.atguigu.pojo.utils;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;

public class LogUtils {

	public static void logBefore(JoinPoint jp) {
		// jp.getSignature().getName() 获取方法名
		// jp.getArgs() 调用方法时的参数
		System.out.println("前置日记是：当前是【" + jp.getSignature().getName() + "】操作，参数是：" + Arrays.asList(jp.getArgs()));
	}

	public static void logAfter(JoinPoint jp) {
		System.out.println("后置日记是：当前是【" + jp.getSignature().getName() + "】操作，参数是：" + Arrays.asList(jp.getArgs()));
	}

	public static void logAfterThrowing(JoinPoint jp, Exception e) {
		System.out.println("异常日记是：当前是【" + jp.getSignature().getName() + "】操作，异常是：" + e);
	}

	public static void logAfterReturning(JoinPoint jp, Object result) {
		System.out.println("返回日记是：当前是【" + jp.getSignature().getName() + "】操作，结果是：" + result);
	}

}
