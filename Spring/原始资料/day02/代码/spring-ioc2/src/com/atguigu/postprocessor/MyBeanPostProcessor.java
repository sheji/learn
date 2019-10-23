package com.atguigu.postprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import com.atguigu.pojo.Person;

public class MyBeanPostProcessor implements BeanPostProcessor {
	/**
	 * 初始化方法之后调用
	 */
	@Override
	public Object postProcessAfterInitialization(Object bean, String id) throws BeansException {
		System.out.println("初始化方法之后。正在初始化的对象bean->" + bean + ",正在初始化对象的id值->" + id);
		
		if ("p21".equals(id)) {
			Person person = (Person) bean;
			person.setName("这是我给的值");
		}
		
		return bean;
	}

	/**
	 * 初始化方法之前调用
	 */
	@Override
	public Object postProcessBeforeInitialization(Object bean, String id) throws BeansException {
		System.out.println("初始化方法之前。正在初始化的对象bean->" + bean + ",正在初始化对象的id值->" + id);
		return bean;
	}

}
