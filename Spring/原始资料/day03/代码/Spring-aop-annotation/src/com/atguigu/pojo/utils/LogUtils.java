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

@Component
// @Aspect 表示我是一个切面类
@Aspect
@Order(1)
public class LogUtils {
	
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
		System.out.println("前置日记是：当前是【" + jp.getSignature().getName() + "】操作，参数是：" + Arrays.asList(jp.getArgs()));
	}

	/**
	 * @After 后置通知<br/>
	 */
	@After(value = "pointcut1()")
	public static void logAfter(JoinPoint jp) {
		System.out.println("后置日记是：当前是【" + jp.getSignature().getName() + "】操作，参数是：" + Arrays.asList(jp.getArgs()));
	}

	/**
	 * @AfterThrowing异常通知<br/>
	 * 	1、在异常通知参数中添加一个Exception e的参数接收抛出的异常<br/>
	 *  2、在异常通知注解中使用属性throwing="参数名"
	 */
	@AfterThrowing(value = "pointcut1()",throwing="e")
	public static void logAfterThrowing(JoinPoint jp,Exception e) {
		System.out.println("异常日记是：当前是【" + jp.getSignature().getName() + "】操作，异常是：" + e);
	}

	/**
	 * @AfterReturning 是返回通知
	 * 	1、在返回通知上添加一个Object result的参数<br/>
	 *  2、在返回通知注解上使用属性returning = "result"标明哪个参数接收返回值
	 */
	@AfterReturning(value = "pointcut1()", returning = "result")
	public static void logAfterReturning(JoinPoint jp, Object result) {
		System.out.println("返回日记是：当前是【" + jp.getSignature().getName() + "】操作，结果是：" + result);
	}
	/**
	 * @throws Throwable 
	 * @Around 环绕通知 （环绕通知需要自己执行目标方法）
	 * 1、环绕通知优先于普通通知先执行。
	 * 2、环绕通知一定要把目标方法的返回值返回
	 * 3、环绕通知收到异常后，一定要往外抛，否则普通异常通知收不到。
	
	@Around(value="pointcut1()")
	public static Object around(ProceedingJoinPoint pjp) throws Throwable {
		
		Object result = null;
		try {
			try {
				System.out.println("环绕 的 前置通知");
				// 执行目标方法
				result = pjp.proceed();
				
			} finally {
				System.out.println("环绕 的 后置通知");
			}
			System.out.println("环绕 的 返回值通知");
		} catch (Exception e) {
			System.out.println("环绕 的 异常通知");
			throw e;
		}
		return result;
	} */

}
