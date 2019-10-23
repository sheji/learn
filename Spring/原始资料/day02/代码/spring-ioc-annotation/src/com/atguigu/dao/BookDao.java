package com.atguigu.dao;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
/**
 * @Repository 注解表示<br/>
 * <bean id="bookDao" class="com.atguigu.dao.BookDao" /><br/>
 * @Repository("abc") 表示
 * <bean id="abc" class="com.atguigu.dao.BookDao" /><br/>
 */
@Repository
@Scope("prototype")
public class BookDao {
}
