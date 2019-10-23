package com.atguigu.mapper.test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import com.atguigu.mapper.UserMapper;
import com.atguigu.pojo.User;

public class UserMapperTest {

	@Test
	public void testQueryUsersByNameAndSex() throws IOException {

		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
				.build(Resources.getResourceAsStream("mybatis-config.xml"));

		SqlSession session = sqlSessionFactory.openSession();

		try {
			UserMapper mapper = session.getMapper(UserMapper.class);

			mapper.queryUsersByNameAndSex(new User(null, null, 15)).forEach(
					System.out::println);

		} finally {
			session.close();
		}
	}

	@Test
	public void testUpdateUser() throws IOException {
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
				.build(Resources.getResourceAsStream("mybatis-config.xml"));

		SqlSession session = sqlSessionFactory.openSession();

		try {
			UserMapper mapper = session.getMapper(UserMapper.class);

			mapper.updateUser(new User(1, null, 10));

			session.commit();
		} finally {
			session.close();
		}
	}

	@Test
	public void testQueryUsersByIds() throws IOException {
		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
				.build(Resources.getResourceAsStream("mybatis-config.xml"));

		SqlSession session = sqlSessionFactory.openSession();

		try {
			UserMapper mapper = session.getMapper(UserMapper.class);

			List<Integer> ids = new ArrayList<Integer>();
			
			ids.add(1);
			ids.add(5);
			ids.add(8);
			ids.add(9);
			ids.add(19
			
			mapper.queryUsersByIds(ids)
					.forEach(System.out::println);
		} finally {
			session.close();
		}

	}

	@Test
	public void testQueryUsersByNameAndSexChoose() throws IOException {

		SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
				.build(Resources.getResourceAsStream("mybatis-config.xml"));

		SqlSession session = sqlSessionFactory.openSession();

		try {
			UserMapper mapper = session.getMapper(UserMapper.class);

			mapper.queryUsersByNameAndSexChoose(new User(null, null, 15))
					.forEach(System.out::println);

		} finally {
			session.close();
		}
	}

}
