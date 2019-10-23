package com.atguigu.mapper;

import java.util.List;

import com.atguigu.pojo.User;

public interface UserMapper {
	/**
	 * 根据user对象中的lastName和sex属性查询用户 <br/>
	 * 希望lastName值不能为null， 性别只能是0和1，有效值
	 * 
	 * @param user
	 * @return
	 */
	public List<User> queryUsersByNameAndSex(User user);

	/**
	 * 如果lastName有值，则使用它查询<br/>
	 * 如果lastName没有值，sex值有效，则使用sex查询<br/>
	 * 否则，你自己添加一个查询条件（默认）
	 */
	public List<User> queryUsersByNameAndSexChoose(User user);

	public int updateUser(User user);
	/**
	 * 执行类似的语句：select * from t_user where id in (1,3,4);
	 */
	public List<User> queryUsersByIds(List<Integer> ids);

}
