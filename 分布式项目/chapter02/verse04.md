# 第四节 用户登录功能

## 1.基于令牌的登录机制

![image](../images/image34.png)

### ①好处

- 隐藏用户的私密、关键信息
- 让浏览器端数据存储可以不依赖于Cookie，服务器端可以不依赖于Session

### ②token存储的两种方式

##### [1]使用Cookie、Session机制存储

![images](../images/image82.png)

##### [2]使用基于HTML5的存储对象存储

[W3CSchool相关参考文档](https://www.w3cschool.cn/html5/html5-webstorage.html)

| 对象名         | 作用                                                         |
| -------------- | ------------------------------------------------------------ |
| localStorage   | 没有时间限制的数据存储，对应整个Web应用，相当于服务端域对象中的application |
| sessionStorage | 针对一个 session 的数据存储，当用户关闭浏览器窗口后，数据会被删除。相当于服务端域对象中的session |

![images](../images/image83.png)

## 2.具体实现

### ①执行流程

- 接收表单数据
  - 登录账号
  - 密码
- 根据登录账号查询MemberPO对象
  - 没有查询到：登录失败
  - 查询到对象：继续执行
- 比较密码
  - 不一致：登录失败
  - 一致：继续执行
- 生成token值
- 将token拼接固定前缀作为KEY，将MemberId作为VALUE存入Redis
- 封装MemberSignSuccessVO对象
  - 用户昵称：username
  - 邮箱地址：email
  - 令牌：token
- 返回MemberSignSuccessVO对象

![images](../images/image84.png)

### ②根据登录账号查询MemberPO对象

#### [1]接口方法

所在工程：distribution-crowd-1-common<br/>

所在接口：com.atguigu.crowd.api.DataBaseOperationRemoteService

```java
@RequestMapping("/retrieve/member/by/login/acct")
ResultEntity<MemberPO> retrieveMemberByLoginAcct(@RequestParam("loginAcct") String loginAcct);
```

#### [2]与接口对应的Controller方法

所在工程：distribution-crowd-3-database-provider

所在类：com.atguigu.crowd.controller.MemberController

```java
@RequestMapping("/retrieve/member/by/login/acct")
public ResultEntity<MemberPO> retrieveMemberByLoginAcct(@RequestParam("loginAcct") String loginAcct) {
	
	MemberPO memberPO = null;
    
	try {
		memberPO = memberService.getMemberByLoginAcct(loginAcct);
	} catch (Exception e) {
		e.printStackTrace();
		
		return ResultEntity.failed(e.getMessage());
	}
	
	return ResultEntity.successWithData(memberPO);
}
```

Service方法

```java
@Override
public MemberPO getMemberByLoginAcct(String loginAcct) {
	
	// 1.创建MemberPOExample对象
	MemberPOExample example = new MemberPOExample();
	
	// 2.封装查询条件
	example.createCriteria().andLoginacctEqualTo(loginAcct);
	
	// 3.执行QBC查询
	List<MemberPO> memberList = memberPOMapper.selectByExample(example);
	
	// 4.判断查询结果集合的有效性
	if(CrowdUtils.collectionEffectiveCheck(memberList)) {
		
		// 5.如果查询结果集合有效，则返回第一个元素
		return memberList.get(0);
	}
	
	// 6.如果集合无效则返回null
	return null;
}
```

### ③生成token的工具方法

所在工程：distribution-crowd-1-common<br/>

所在类：com.atguigu.crowd.util.CrowdConstant<br/>

```java
public static final String REDIS_MEMBER_SING_TOKEN_PREFIX = "SIGNED_MEMBER_";
```

所在类：com.atguigu.crowd.util.CrowdUtils

```java
/**
 * 生成用户登录成功后使用的token
 * @return
 * @author 封捷
 */
public static String generateToken() {
	
	return CrowdConstant.REDIS_MEMBER_SING_TOKEN_PREFIX + UUID.randomUUID().toString().replaceAll("-", "");
}
```

### ⑤封装MemberSignSuccessVO类

所在工程：distribution-crowd-1-common

所在包：com.atguigu.crowd.entity.vo

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberSignSuccessVO {
	
	private String username;
	private String email;
	private String token;

}
```

### ⑥具体执行登录的方法

所在工程：distribution-crowd-5-member-manager<br/>

所在类：com.atguigu.crowd.controller.MemberController

```java
@RequestMapping("/member/manager/login")
public ResultEntity<MemberSignSuccessVO> login(
		@RequestParam("loginAcct") String loginAcct, 
		@RequestParam("userPswd") String userPswd) {

	// 1.根据登录账号查询数据库获取MemberPO对象
	ResultEntity<MemberPO> resultEntity = dataBaseOperationRemoteService.retrieveMemberByLoginAcct(loginAcct);
	
	if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
		return ResultEntity.failed(resultEntity.getMessage());
	}
	
	// 2.获取MemberPO对象
	MemberPO memberPO = resultEntity.getData();
	
	// 3.检查MemberPO对象是否为空
	if(memberPO == null) {
		return ResultEntity.failed(CrowdConstant.MESSAGE_LOGIN_FAILED);
	}
	
	// 4.获取从数据库查询得到的密码
	String userpswdDatabase = memberPO.getUserpswd();
	
	// 5.比较密码
	boolean matcheResult = passwordEncoder.matches(userPswd, userpswdDatabase);
	
	if(!matcheResult) {
		return ResultEntity.failed(CrowdConstant.MESSAGE_LOGIN_FAILED);
	}
	
	// 6.生成token
	String token = CrowdUtils.generateToken();
	
	// 7.从MemberPO对象获取memberId
	String memberId = memberPO.getId() + "";
	
	// 8.将token和memberId存入Redis
	ResultEntity<String> resultEntitySaveToken = redisRemoteService.saveNormalStringKeyValue(token, memberId, 30);
	
	if(ResultEntity.FAILED.equals(resultEntitySaveToken.getResult())) {
		return ResultEntity.failed(resultEntitySaveToken.getMessage());
	}
	
	// 9.封装MemberSignSuccessVO对象
	MemberSignSuccessVO memberSignSuccessVO = new MemberSignSuccessVO();
	
	BeanUtils.copyProperties(memberPO, memberSignSuccessVO);
	
	memberSignSuccessVO.setToken(token);
	
	// 10.返回结果
	return ResultEntity.successWithData(memberSignSuccessVO);
}
```

## 3.退出登录

所在工程：distribution-crowd-5-member-manager

所在类：com.atguigu.crowd.controller.MemberController

```java
	@RequestMapping("/member/manager/logout")
	public ResultEntity<String> logout(@RequestParam("token") String token) {
		
		return redisRemoteService.removeByKey(token);
	}
```

