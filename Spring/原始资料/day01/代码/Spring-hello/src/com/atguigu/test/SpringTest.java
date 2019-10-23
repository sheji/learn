package com.atguigu.test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import com.atguigu.pojo.Person;

public class SpringTest {

	@Test
	public void test1() throws Exception {
		// applicationContext.xml是Spring的配置文件，
		// 我们需要先有一个Spring容器（Spring IOC 容器），再从容器中获取配置的bean对象
		//ApplicationContext接口表示Spring IOC容器
		// Spring容器（Spring IOC容器） 在初始化的时候需要一个配置文件
		// ClassPathXmlApplicationContext表示从Classpath类路径下加载你指定的配置文件名，生成Spring容器
//		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicaionContext.xml");
		// 从文件系统路径中加载指定的xml配置文件生成Spring容器
		ApplicationContext applicationContext = new FileSystemXmlApplicationContext("config/applicaionContext.xml");
		
		// getBean是从Spring容器中获取指定id值的bean对象
		Person person = (Person) applicationContext.getBean("p1");
	
		System.out.println(person);
		System.out.println(applicationContext.getBean("p11"));
		System.out.println(applicationContext.getBean("p1"));
		System.out.println(applicationContext.getBean("p1"));
		System.out.println(applicationContext.getBean("p1"));
	}
	
	@Test
	public void test2() throws Exception {
		// 先创建Spring容器对象
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicaionContext.xml");
		// 通过类型获取Spring容器中的对象
		/**
		 * 如果通过类型查找bean对象。<br/>
		 * 	1、找到一个就直接返回<br/>
		 *  2、找到两个就报错<br/>
		 *  3、没有找到也报错
		 */
		Person person = applicationContext.getBean(Person.class);
		
		System.out.println( person );
	}
	
	@Test
	public void test3() throws Exception {
		// 先创建Spring容器对象
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicaionContext.xml");
		
		Person p3 = (Person) applicationContext.getBean("p3");
		
		System.out.println(p3);
		
	}
	
	@Test
	public void test4() throws Exception {
		// 先创建Spring容器对象
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicaionContext.xml");
		
		System.out.println( applicationContext.getBean("p4") );
		
	}
	
	@Test
	public void test5() throws Exception {
		// 先创建Spring容器对象
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicaionContext.xml");
		
		System.out.println( applicationContext.getBean("p5") );
		
	}
	
	@Test
	public void test6() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicaionContext.xml");
		
		System.out.println( applicationContext.getBean("p6") );
	}
	
	@Test
	public void test7() throws Exception {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicaionContext.xml");
		
		Person person = (Person) applicationContext.getBean("p7");
		
		System.out.println( person.getName().length() );
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
