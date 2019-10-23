package com.atguigu.spring.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.atguigu.dao.EmployeeDao;
import com.atguigu.pojo.Employee;
import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.swing.internal.plaf.basic.resources.basic_de;

import jdk.nashorn.internal.runtime.UserAccessorProperty;

@ContextConfiguration(locations = "classpath:applicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringTest {

	@Autowired
	DataSource dataSource;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	@Autowired
	EmployeeDao employeeDao;

	@Test
	public void testDataSource() throws Exception {
		System.out.println(dataSource.getConnection());
		System.out.println(jdbcTemplate);
	}

	// 实验2：将id=5的记录的salary字段更新为1300.00
	@Test
	public void test2() throws Exception {
		String sql = "update employee set salary = ? where id = ?";
		// update方法执行insret、update、delete语句
		jdbcTemplate.update(sql, new BigDecimal(1300), 5);
	}

	// 实验3：批量插入
	@Test
	public void test3() throws Exception {
		String sql = "insert into employee(`name`,`salary`) values(?,?)";
		/**
		 * 一条sql语句参数是一个一维数组，那么多条sql语句，它的参数是多个一维数组
		 */
		List<Object[]> batchArgs = new ArrayList<Object[]>();

		batchArgs.add(new Object[] { "飞龙", new BigDecimal(99999) });
		batchArgs.add(new Object[] { "英哥", new BigDecimal(9999) });
		batchArgs.add(new Object[] { "国哥", new BigDecimal(999) });

		jdbcTemplate.batchUpdate(sql, batchArgs);
	}

	// 实验4：查询id=5的数据库记录，封装为一个Java对象返回
	@Test
	public void test4() throws Exception {
		String sql = "select id,name,salary from employee where id = ?";
		/**
		 * 第一个参数是sql语句<br/>
		 * RowMapper接口，它的实现类可以帮我们把查询到的resultSet每一行记录封装成为javaBean返回
		 */
		Employee employee = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Employee>(Employee.class), 5);
		System.out.println(employee);
	}

	// 实验5：查询salary>4000的数据库记录，封装为List集合返回
	@Test
	public void test5() throws Exception {
		String sql = "select id,name,salary from employee where salary > ?";
		/**
		 * 查询一行记录使用queryForObject。<br/>
		 * 查询多行记录，使用query方法
		 */
		jdbcTemplate.query(sql, new BeanPropertyRowMapper<Employee>(Employee.class), new BigDecimal(4000))
				.forEach(System.out::println);
	}

	// 实验6：查询最大salary
	@Test
	public void test6() throws Exception {
		String sql = "select max(salary) from employee";
		BigDecimal salary = jdbcTemplate.queryForObject(sql, BigDecimal.class);
		System.out.println(salary);
	}

	// 实验7：使用带有具名参数的SQL语句插入一条员工记录，并以Map形式传入参数值
	// <!-- namedParameterJdbcTemplate -->
	@Test
	public void test7() throws Exception {
		/**
		 * :name 相当于 ? 占位符，name就是这个参数的名称
		 */
		String sql = "insert into employee(`name`,`salary`) values(:name,:salary)";

		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", "我是命名（具名）参数的");
		paramMap.put("salary", new BigDecimal(1234));

		namedParameterJdbcTemplate.update(sql, paramMap);

	}

	// 实验8：重复实验7，以SqlParameterSource形式传入参数值
	@Test
	public void test8() throws Exception {
		/**
		 * :name 相当于 ? 占位符，name就是这个参数的名称
		 */
		String sql = "insert into employee(`name`,`salary`) values(:name,:salary)";

		Employee employee = new Employee(null, "我是具名参数插入的", new BigDecimal(30000));

		/**
		 * SqlParameterSource给sql语句传入需要的参数值
		 */
		namedParameterJdbcTemplate.update(sql, new BeanPropertySqlParameterSource(employee));
	}

	//
	// 实验9：创建Dao，自动装配JdbcTemplate对象
	@Test
	public void test9() throws Exception {
		Employee employee = new Employee(null, "我是Dao插入的", new BigDecimal(1234));
		employeeDao.saveEmployee(employee);
	}

	// 实验10：通过继承JdbcDaoSupport创建JdbcTemplate的Dao
	@Test
	public void test10() throws Exception {
		Employee employee = new Employee(null, "我是JdbcDaoSupport插入的", new BigDecimal(1234));
		employeeDao.saveEmployee(employee);
	}
}
