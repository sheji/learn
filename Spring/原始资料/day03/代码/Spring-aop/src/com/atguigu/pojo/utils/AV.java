package com.atguigu.pojo.utils;

import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
/**
 * @Order(10)注解可以指定切面执行顺序<br/>
 * 	数字越小越优先
 */
@Order(10)
@Component
// @Aspect 表示我是一个切面类
@Aspect
public class AV {
	
//	1、定义一个空的静态方法
//	2、使用@Pointcut注解定义切入点表达式
//	3、在需要复用切入点表达式的地方，使用方法调用代替
	@Pointcut("execution(public int com.atguigu.pojo.Calculator.*(int,int))")
	public static void pointcut1() {}
	
	/**
	 * @Before 表示前置通知<br/>
	 *         value 需要写上切入点表达式
	 */
	@Before(value = "pointcut1()")
	public static void logBefore(JoinPoint jp) {
		// jp.getSignature().getName() 获取方法名
		// jp.getArgs() 调用方法时的参数
		System.out.println("V 前置日记是：当前是【" + jp.getSignature().getName() + "】操作，参数是：" + Arrays.asList(jp.getArgs()));
	}

	/**
	 * @After 后置通知<br/>
	 */
	@After(value = "pointcut1()")
	public static void logAfter(JoinPoint jp) {
		System.out.println("V 后置日记是：当前是【" + jp.getSignature().getName() + "】操作，参数是：" + Arrays.asList(jp.getArgs()));
	}

	/**
	 * @AfterThrowing异常通知<br/>
	 * 	1、在异常通知参数中添加一个Exception e的参数接收抛出的异常<br/>
	 *  2、在异常通知注解中使用属性throwing="参数名"
	 */
	@AfterThrowing(value = "pointcut1()",throwing="e")
	public static void logAfterThrowing(JoinPoint jp,Exception e) {
		System.out.println("V 异常日记是：当前是【" + jp.getSignature().getName() + "】操作，异常是：" + e);
	}

	/**
	 * @AfterReturning 是返回通知
	 * 	1、在返回通知上添加一个Object result的参数<br/>
	 *  2、在返回通知注解上使用属性returning = "result"标明哪个参数接收返回值
	 */
	@AfterReturning(value = "pointcut1()", returning = "result")
	public static void logAfterReturning(JoinPoint jp, Object result) {
		System.out.println("V 返回日记是：当前是【" + jp.getSignature().getName() + "】操作，结果是：" + result);
	}

}
