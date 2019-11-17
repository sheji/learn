package com.atguigu.mapper.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import com.atguigu.mapper.BookMapper;
import com.atguigu.pojo.Book;

public class BookMapperTest {

	static SqlSessionFactory sqlSessionFactory;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources
				.getResourceAsStream("mybatis-config.xml"));
	}

	@Test
	public void testDeleteByPrimaryKey() {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			BookMapper mapper = session.getMapper(BookMapper.class);

			mapper.deleteByPrimaryKey(8);

			session.commit();
		} finally {
			session.close();
		}
	}

	@Test
	public void testInsert() {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			BookMapper mapper = session.getMapper(BookMapper.class);

			mapper.insert(new Book(null, "国哥好久不帅了", "0105",
					new BigDecimal(999), 999999, 1));

			session.commit();
		} finally {
			session.close();
		}
	}

	@Test
	public void testSelectByPrimaryKey() {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			BookMapper mapper = session.getMapper(BookMapper.class);

			System.out.println(mapper.selectByPrimaryKey(1));

		} finally {
			session.close();
		}
	}

	@Test
	public void testSelectAll() {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			BookMapper mapper = session.getMapper(BookMapper.class);

			mapper.selectAll().forEach(System.out::println);

		} finally {
			session.close();
		}
	}

	@Test
	public void testUpdateByPrimaryKey() {
		SqlSession session = sqlSessionFactory.openSession();
		try {
			BookMapper mapper = session.getMapper(BookMapper.class);

			mapper.updateByPrimaryKey(new Book(8, "国哥又可以帅一次", "0105",
					new BigDecimal(999), 999999, 1));

			session.commit();
		} finally {
			session.close();
		}
	}

}
