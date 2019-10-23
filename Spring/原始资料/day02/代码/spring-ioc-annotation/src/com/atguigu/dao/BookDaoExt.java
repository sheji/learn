package com.atguigu.dao;

import org.springframework.stereotype.Repository;
/**
 * @Repository 注解表示<br/>
 * <bean id="bookDao" class="com.atguigu.dao.BookDao" /><br/>
 * @Repository("abc") 表示
 * <bean id="abc" class="com.atguigu.dao.BookDao" /><br/>
 */
@Repository
public class BookDaoExt extends BookDao{
}
