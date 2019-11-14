[TOC]

## 第三节 注册功能

### 1.总体思路

![images](../images/image11.png)

### 2.创建数据库表

```sql
create table t_member
(
   id                   int(11) not null auto_increment,
   loginacct            varchar(255) not null,
   userpswd             char(200) not null,
   username             varchar(255),
   email                varchar(255),
   authstatus           tinyint(4) comment '实名认证状态 0 - 未实名认证， 1 - 实名认证申请中， 2 - 已实名认证',
   usertype             tinyint(4) comment ' 0 - 个人， 1 - 企业',
   realname             varchar(255),
   cardnum              varchar(255),
   accttype             tinyint(4) comment '0 - 企业， 1 - 个体， 2 - 个人， 3 - 政府',
   primary key (id)
);
```

逆向工程生成相关资源。<br/>

※P.S.：两种Service对比<br/>

![images](../images/image81.png)

### 3.短信发送API调用

```java
// 调用短信发送接口时的访问地址
String host = "https://feginesms.market.alicloudapi.com";

// 具体访问路径
String path = "/codeNotice";

// 请求方式
String method = "GET";

// 登录阿里云后，进入管理控制台->云市场->已购买服务，复制AppCode
String appcode = "61f2eaa3c43e42ad82c26fbbe1061311";
Map<String, String> headers = new HashMap<String, String>();

// 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
headers.put("Authorization", "APPCODE " + appcode);

// 封装请求参数
Map<String, String> querys = new HashMap<String, String>();

// 验证码
querys.put("param", "7558");

// 接收短信的手机号
querys.put("phone", "13547119500");

// 签名编号
querys.put("sign", "1");

// 模板编号
querys.put("skin", "1");

// JDK 1.8示例代码请在这里下载： http://code.fegine.com/Tools.zip

try {
	/**
	 * 重要提示如下: HttpUtils请从
	 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
	 * 或者直接下载： http://code.fegine.com/HttpUtils.zip 下载
	 *
	 * 相应的依赖请参照
	 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
	 * 相关jar包（非pom）直接下载： http://code.fegine.com/aliyun-jar.zip
	 */
	HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
	// System.out.println(response.toString());如不输出json, 请打开这行代码，打印调试头部状态码。
	// 状态码: 200 正常；400 URL无效；401 appCode错误； 403 次数用完； 500 API网管错误
	// 获取response的body
	System.out.println(EntityUtils.toString(response.getEntity()));
} catch (Exception e) {
	e.printStackTrace();
}
```

```java
package com.aliyun.api.gateway.demo.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class HttpUtils {
	
	/**
	 * get
	 * 
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doGet(String host, String path, String method, 
			Map<String, String> headers, 
			Map<String, String> querys)
            throws Exception {    	
    	HttpClient httpClient = wrapClient(host);

    	HttpGet request = new HttpGet(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
        	request.addHeader(e.getKey(), e.getValue());
        }
        
        return httpClient.execute(request);
    }
	
	/**
	 * post form
	 * 
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @param bodys
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doPost(String host, String path, String method, 
			Map<String, String> headers, 
			Map<String, String> querys, 
			Map<String, String> bodys)
            throws Exception {    	
    	HttpClient httpClient = wrapClient(host);

    	HttpPost request = new HttpPost(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
        	request.addHeader(e.getKey(), e.getValue());
        }

        if (bodys != null) {
            List<NameValuePair> nameValuePairList = new ArrayList<NameValuePair>();

            for (String key : bodys.keySet()) {
                nameValuePairList.add(new BasicNameValuePair(key, bodys.get(key)));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(nameValuePairList, "utf-8");
            formEntity.setContentType("application/x-www-form-urlencoded; charset=UTF-8");
            request.setEntity(formEntity);
        }

        return httpClient.execute(request);
    }	
	
	/**
	 * Post String
	 * 
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doPost(String host, String path, String method, 
			Map<String, String> headers, 
			Map<String, String> querys, 
			String body)
            throws Exception {    	
    	HttpClient httpClient = wrapClient(host);

    	HttpPost request = new HttpPost(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
        	request.addHeader(e.getKey(), e.getValue());
        }

        if (StringUtils.isNotBlank(body)) {
        	request.setEntity(new StringEntity(body, "utf-8"));
        }

        return httpClient.execute(request);
    }
	
	/**
	 * Post stream
	 * 
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doPost(String host, String path, String method, 
			Map<String, String> headers, 
			Map<String, String> querys, 
			byte[] body)
            throws Exception {    	
    	HttpClient httpClient = wrapClient(host);

    	HttpPost request = new HttpPost(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
        	request.addHeader(e.getKey(), e.getValue());
        }

        if (body != null) {
        	request.setEntity(new ByteArrayEntity(body));
        }

        return httpClient.execute(request);
    }
	
	/**
	 * Put String
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doPut(String host, String path, String method, 
			Map<String, String> headers, 
			Map<String, String> querys, 
			String body)
            throws Exception {    	
    	HttpClient httpClient = wrapClient(host);

    	HttpPut request = new HttpPut(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
        	request.addHeader(e.getKey(), e.getValue());
        }

        if (StringUtils.isNotBlank(body)) {
        	request.setEntity(new StringEntity(body, "utf-8"));
        }

        return httpClient.execute(request);
    }
	
	/**
	 * Put stream
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @param body
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doPut(String host, String path, String method, 
			Map<String, String> headers, 
			Map<String, String> querys, 
			byte[] body)
            throws Exception {    	
    	HttpClient httpClient = wrapClient(host);

    	HttpPut request = new HttpPut(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
        	request.addHeader(e.getKey(), e.getValue());
        }

        if (body != null) {
        	request.setEntity(new ByteArrayEntity(body));
        }

        return httpClient.execute(request);
    }
	
	/**
	 * Delete
	 *  
	 * @param host
	 * @param path
	 * @param method
	 * @param headers
	 * @param querys
	 * @return
	 * @throws Exception
	 */
	public static HttpResponse doDelete(String host, String path, String method, 
			Map<String, String> headers, 
			Map<String, String> querys)
            throws Exception {    	
    	HttpClient httpClient = wrapClient(host);

    	HttpDelete request = new HttpDelete(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
        	request.addHeader(e.getKey(), e.getValue());
        }
        
        return httpClient.execute(request);
    }
	
	private static String buildUrl(String host, String path, Map<String, String> querys) throws UnsupportedEncodingException {
    	StringBuilder sbUrl = new StringBuilder();
    	sbUrl.append(host);
    	if (!StringUtils.isBlank(path)) {
    		sbUrl.append(path);
        }
    	if (null != querys) {
    		StringBuilder sbQuery = new StringBuilder();
        	for (Map.Entry<String, String> query : querys.entrySet()) {
        		if (0 < sbQuery.length()) {
        			sbQuery.append("&");
        		}
        		if (StringUtils.isBlank(query.getKey()) && !StringUtils.isBlank(query.getValue())) {
        			sbQuery.append(query.getValue());
                }
        		if (!StringUtils.isBlank(query.getKey())) {
        			sbQuery.append(query.getKey());
        			if (!StringUtils.isBlank(query.getValue())) {
        				sbQuery.append("=");
        				sbQuery.append(URLEncoder.encode(query.getValue(), "utf-8"));
        			}        			
                }
        	}
        	if (0 < sbQuery.length()) {
        		sbUrl.append("?").append(sbQuery);
        	}
        }
    	
    	return sbUrl.toString();
    }
	
	private static HttpClient wrapClient(String host) {
		HttpClient httpClient = new DefaultHttpClient();
		if (host.startsWith("https://")) {
			sslClient(httpClient);
		}
		
		return httpClient;
	}
	
	private static void sslClient(HttpClient httpClient) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
                public void checkClientTrusted(X509Certificate[] xcs, String str) {
                	
                }
                public void checkServerTrusted(X509Certificate[] xcs, String str) {
                	
                }
            };
            ctx.init(null, new TrustManager[] { tm }, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = httpClient.getConnectionManager();
            SchemeRegistry registry = ccm.getSchemeRegistry();
            registry.register(new Scheme("https", 443, ssf));
        } catch (KeyManagementException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchAlgorithmException ex) {
        	throw new RuntimeException(ex);
        }
    }
}
```

```xml
<dependency>
	<groupId>com.alibaba</groupId>
	<artifactId>fastjson</artifactId>
	<version>1.2.15</version>
</dependency>
<dependency>
	<groupId>org.apache.httpcomponents</groupId>
	<artifactId>httpclient</artifactId>
	<version>4.2.1</version>
</dependency>
<dependency>
	<groupId>org.apache.httpcomponents</groupId>
	<artifactId>httpcore</artifactId>
	<version>4.2.1</version>
</dependency>
<dependency>
	<groupId>commons-lang</groupId>
	<artifactId>commons-lang</artifactId>
	<version>2.6</version>
</dependency>
<dependency>
	<groupId>org.eclipse.jetty</groupId>
	<artifactId>jetty-util</artifactId>
	<version>9.3.7.v20160115</version>
</dependency>
```

### 4.封装发送短信的工具方法

#### ①加入依赖

##### [1]distribution-crowd-parent工程

```xml
<dependency>
	<groupId>com.alibaba</groupId>
	<artifactId>fastjson</artifactId>
	<version>1.2.15</version>
</dependency>
<dependency>
	<groupId>commons-lang</groupId>
	<artifactId>commons-lang</artifactId>
	<version>2.6</version>
</dependency>
<dependency>
	<groupId>org.eclipse.jetty</groupId>
	<artifactId>jetty-util</artifactId>
	<version>9.3.7.v20160115</version>
</dependency>
```

httpclient和httpcore在项目中本身已经加入，不再重复加入，避免jar包冲突。

##### [2]distribution-crowd-1-common工程

```xml
		<dependency>
			<groupId>com.alibaba</groupId>
			<artifactId>fastjson</artifactId>
		</dependency>
		<dependency>
			<groupId>org.apache.httpcomponents</groupId>
			<artifactId>httpclient</artifactId>
		</dependency>
		<dependency>
			<groupId>org.apache.httpcomponents</groupId>
			<artifactId>httpcore</artifactId>
		</dependency>
		<dependency>
			<groupId>commons-lang</groupId>
			<artifactId>commons-lang</artifactId>
		</dependency>
		<dependency>
			<groupId>org.eclipse.jetty</groupId>
			<artifactId>jetty-util</artifactId>
		</dependency>
```

#### ②加入HttpUtils类

参见上面的笔记

#### ③创建工具方法类

```java
public class CrowdUtils {
    /**
	 * 验证集合是否有效
	 * @param c		待验证集合
	 * @return		验证结果（true：有效，false：无效）
	 * @author 封捷
	 */
	public static <E> boolean collectionEffectiveCheck(Collection<E> c) {
		return (c != null) && (c.size() > 0);
	}
	
	/**
	 * 验证字符串是否有效
	 * @param source	待验证字符串
	 * @return			验证结果（true：有效，false：无效）
	 * @author 封捷
	 */
	public static boolean strEffectiveCheck(String source) {
		return (source != null) && (source.length() > 0);
	}
	
	/**
	 * 生成随机验证码
	 * @param length	验证码长度
	 * @return			生成的验证码
	 * @throws	RuntimeException 验证码长度必须大于0
	 * @author 封捷
	 */
	public static String randomCode(int length) {
		
		if(length <= 0) {
			throw new RuntimeException(CrowdConstant.MESSAGE_RANDOM_CODE_LENGTH_INVALID);
		}
		
		StringBuilder builder = new StringBuilder();
		
		for(int i = 0; i < length; i++) {
			
			// 1.生成随机数
			double doubleRandom = Math.random();
			
			// 2.调整
			int integerRandom = (int) (doubleRandom * 10);
			
			// 3.拼接
			builder.append(integerRandom);
		}
		
		return builder.toString();
	}
		
	/**
	 * 发送验证码短信
	 * @param appcode		阿里云市场中调用API时识别身份的appCode
	 * @param randomCode	验证码值
	 * @param phoneNum		接收验证码短信的手机号
	 */
	public static void sendShortMessage(String appcode, String randomCode, String phoneNum) {
		// 调用短信发送接口时的访问地址
		String host = "https://feginesms.market.alicloudapi.com";
		
		// 具体访问路径
		String path = "/codeNotice";
		
		// 请求方式
		String method = "GET";
		
		// 登录阿里云后，进入管理控制台->云市场->已购买服务，复制AppCode
		// String appcode = "61f2eaa3c43e42ad82c26fbbe1061311";
		Map<String, String> headers = new HashMap<String, String>();
		
		// 最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
		headers.put("Authorization", "APPCODE " + appcode);
		
		// 封装请求参数
		Map<String, String> querys = new HashMap<String, String>();
		
		// 验证码
		querys.put("param", randomCode);
		
		// 接收短信的手机号
		querys.put("phone", phoneNum);
		
		// 签名编号
		querys.put("sign", "1");
		
		// 模板编号
		querys.put("skin", "1");
		
		// JDK 1.8示例代码请在这里下载： http://code.fegine.com/Tools.zip
		
		try {
			/**
			 * 重要提示如下: HttpUtils请从
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
			 * 或者直接下载： http://code.fegine.com/HttpUtils.zip 下载
			 *
			 * 相应的依赖请参照
			 * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
			 * 相关jar包（非pom）直接下载： http://code.fegine.com/aliyun-jar.zip
			 */
			HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
			// System.out.println(response.toString());如不输出json, 请打开这行代码，打印调试头部状态码。
			// 状态码: 200 正常；400 URL无效；401 appCode错误； 403 次数用完； 500 API网管错误
			// 获取response的body
			System.out.println(EntityUtils.toString(response.getEntity()));
		} catch (Exception e) {
			e.printStackTrace();
			
			throw new RuntimeException(e.getMessage());
		}
	}

}
```



### 5.ResultEntity

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResultEntity<T> {
	
	private String result;
	private String message;
	private T data;
	
	public static final String SUCCESS = "SUCCESS";
	public static final String FAILED = "FAILED";
	public static final String NO_MSG = "NO_MSG";
	public static final String NO_DATA = "NO_DATA";
	
	public static ResultEntity<String> successNoData() {
		return new ResultEntity<>(SUCCESS, NO_MSG, NO_DATA);
	}
	
	public static <T> ResultEntity<T> successWithData(T data) {
		return new ResultEntity<>(SUCCESS, NO_MSG, data);
	}
	
	public static <T> ResultEntity<T> failed(String message) {
		return new ResultEntity<>(FAILED, message, null);
	}

}
```



### 6.创建发送短信验证码的handler方法

![image](../images/image12.png)

#### ①Redis中保存验证码数据的key

​	使用用户接受验证码的手机号进行区分。<br/>

​	格式：RANDOM_CODE_[手机号]<br/>

​	例如：RANDOM_CODE_13512345678

| KEY                     | VALUE |
| ----------------------- | ----- |
| RANDOM_CODE_13512345678 | 7754  |
| RANDOM_CODE_18843218765 | 6631  |
| ……                      | ……    |

​	所以可以将前缀保存到常量中

```java
public static final String REDIS_RANDOM_CODE_PREFIX = "RANDOM_CODE_";
```

#### ②声明redis-provider功能的接口

所在工程：distribution-crowd-1-common<br/>

接口全类名：com.atguigu.crowd.api.RedisOperationRemoteService

##### [1]需要加入依赖

```xml
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```



##### [2]声明接口

Redis提供的功能只需要针对String类型的key和value来进行操作即可。

```java
@FeignClient(value="redis-provider")
public interface RedisOperationRemoteService {

	/**
	 * 将字符串类型的键值对保存到Redis时调用的远程方法
	 * @param normalKey
	 * @param normalValue
	 * @param timeoutMinute	超时时间（单位：分钟）
	 * 		-1表示无过期时间
	 * 		正数表示过期时间分钟数
	 * 		0和null值不接受
	 * @return
	 */
	@RequestMapping("/save/normal/string/key/value")
	ResultEntity<String> saveNormalStringKeyValue(@RequestParam("normalKey") String normalKey, @RequestParam("normalValue") String normalValue, @RequestParam("timeoutMinute") Integer timeoutMinute);
	
	/**
	 * 根据key查询对应value时调用的远程方法
	 * @param normalKey
	 * @return
	 */
	@RequestMapping("/retrieve/string/value/by/string/key")
	ResultEntity<String> retrieveStringValueByStringKey(@RequestParam("normalKey") String normalKey);
	
	/**
	 * 根据key删除对应value时调用的远程方法
	 * @param key
	 * @return
	 */
	@RequestMapping("/redis/remove/by/key")
	ResultEntity<String> removeByKey(@RequestParam("key") String key);
	
}

```

#### ③redis-provider功能实现

所在工程：distribution-crowd-4-redis-provider<br/>

全类名：com.atguigu.crowd.controller.RedisOperationController<br/>

友情提示：RedisOperationController不需要也不建议实现RedisOperationRemoteService接口。

```java
@RestController
public class RedisOperationController {
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	/**
	 * 将字符串类型的键值对保存到Redis时调用的远程方法
	 * @param normalKey
	 * @param normalValue
	 * @param timeoutMinute	超时时间（单位：分钟）
	 * @return
	 */
	@RequestMapping("/save/normal/string/key/value")
	ResultEntity<String> saveNormalStringKeyValue(
			@RequestParam("normalKey") String normalKey, 
			@RequestParam("normalValue") String normalValue, 
			@RequestParam("timeoutMinute") Integer timeoutMinute) {
		
		// 对输入数据进行验证
		if(!CrowdUtils.strEffectiveCheck(normalKey) || !CrowdUtils.strEffectiveCheck(normalValue)) {
			return ResultEntity.failed(CrowdConstant.MESSAGE_REDIS_KEY_OR_VALUE_INVALID);
		}
		
		// 获取字符串操作器对象
		ValueOperations<String, String> operator = redisTemplate.opsForValue();
		
		// 判断timeoutMinute值：是否为无效值
		if(timeoutMinute == null || timeoutMinute == 0) {
			return ResultEntity.failed(CrowdConstant.MESSAGE_REDIS_KEY_TIME_OUT_INVALID);
		}
		
		// 判断timeoutMinute值：是否为不设置过期时间
		if(timeoutMinute == -1) {
			
			// 按照不设置过期时间的方式执行保存
			try {
				operator.set(normalKey, normalValue);
			} catch (Exception e) {
				e.printStackTrace();
				
				return ResultEntity.failed(e.getMessage());
			}
			
			// 返回结果
			return ResultEntity.successNoData();
		}
		
		// 按照设置过期时间的方式执行保存
		try {
			operator.set(normalKey, normalValue, timeoutMinute, TimeUnit.MINUTES);
		} catch (Exception e) {
			e.printStackTrace();
			
			return ResultEntity.failed(e.getMessage());
		}
		
		return ResultEntity.successNoData();
	}
	
	/**
	 * 根据key查询对应value时调用的远程方法
	 * @param normalKey
	 * @return
	 */
	@RequestMapping("/retrieve/string/value/by/string/key")
	ResultEntity<String> retrieveStringValueByStringKey(@RequestParam("normalKey") String normalKey) {
		
		// 对输入数据进行验证
		if(!CrowdUtils.strEffectiveCheck(normalKey)) {
			return ResultEntity.failed(CrowdConstant.MESSAGE_REDIS_KEY_OR_VALUE_INVALID);
		}
		
		try {
			String value = redisTemplate.opsForValue().get(normalKey);
			
			return ResultEntity.successWithData(value);
		} catch (Exception e) {
			e.printStackTrace();
			
			return ResultEntity.failed(e.getMessage());
		}
		
	}
	
	/**
	 * 根据key删除对应value时调用的远程方法
	 * @param key
	 * @return
	 */
	@RequestMapping("/redis/remove/by/key")
	ResultEntity<String> removeByKey(@RequestParam("key") String key) {
		
		// 对输入数据进行验证
		if(!CrowdUtils.strEffectiveCheck(key)) {
			return ResultEntity.failed(CrowdConstant.MESSAGE_REDIS_KEY_OR_VALUE_INVALID);
		}
		
		try {
			redisTemplate.delete(key);
			
			return ResultEntity.successNoData();
		} catch (Exception e) {
			e.printStackTrace();
			
			return ResultEntity.failed(e.getMessage());
		}
		
	}

}
```

#### ④member-manager的handler方法

##### [1]注意

- 主启动类上需要写@EnableFeignClients注解
- @Autowired装配RedisOperationRemoteService

##### [2]代码

```java
@RequestMapping("/member/manager/send/code")
public ResultEntity<String> sendCode(@RequestParam("phoneNum") String phoneNum) {
	
	if(!CrowdUtils.strEffectiveCheck(phoneNum)) {
		return ResultEntity.failed(CrowdConstant.MESSAGE_PHONE_NUM_INVALID);
	}
	
	// 1.生成验证码
	int length = 4;
	String randomCode = CrowdUtils.randomCode(length);
	
	// 2.保存到Redis
	Integer timeoutMinute = 15;
	
	String normalKey = CrowdConstant.REDIS_RANDOM_CODE_PREFIX + phoneNum;
	
	ResultEntity<String> resultEntity = redisRemoteService.saveNormalStringKeyValue(normalKey, randomCode, timeoutMinute);
	
	if(ResultEntity.FAILED.equals(resultEntity.getResult())) {
		return resultEntity;
	}
	
	// 3.发送验证码到用户手机
	String appcode = "61f2eaa3c43e42ad82c26fbbe1061311";
	try {
		CrowdUtils.sendShortMessage(appcode, randomCode, phoneNum);
		
		return ResultEntity.successNoData();
	} catch (Exception e) {
		e.printStackTrace();
		
		return ResultEntity.failed(e.getMessage());
	}
	
}
```

### 7.分布式事务

#### ①举例

发送短信操作和Redis保存操作在执行时都有可能失败。如果发生其中一个操作失败，另一个成功，那么数据整体状态就会发生**<font color="red">不一致</font>**的情况。<br/>

#### ②分析

如果是在以前关系型数据库的事务操作中，可以采用回滚机制，一个事务多个操作中有任何一个失败，则整个事务全部回滚。<br/>

在分布式环境下，没办法将多个具体操作纳入到一个统一的事务中，一起提交、一起回滚。<br/>

也不能使用逆操作手动撤销，因为逆操作除了功能和原本操作不同，其他方面和原本的操作性质是一样的。<br/>

#### ③解决办法

![image](../images/image13.png)

### 8.从yml配置文件中读取appCode

#### ①yml配置

这些配置项节点全部都是我们自定义的，完全没有框架的支持。这是允许的。

```yml
crowd:
  short:
    message:
      appCode: 61f2eaa3c43e42ad82c26fbbe1061311
```

#### ②在Java类中引用方式

```java
	@Value("${crowd.short.message.appCode}")
	private String appCode;
```

意义：以后在SpringBoot环境下开发时，如果某些信息不能在Java代码中写死，就可以使用这样的机制在yml配置文件中配置，再使用@Value注解读取。yml或properties文件都可以。

### 9.执行注册

#### ①流程分析

- 接收表单数据
  - 登录账号
  - 密码
  - 手机号
  - 验证码
- 检查验证码是否有效
  - 无效：返回失败信息，停止执行
  - 有效：继续执行
- 检查手机号是否有效
  - 无效：返回失败信息，停止执行
  - 有效：继续执行
- 拼接随机验证码的KEY
- 远程调用redis-provider的方法获取对应的验证码值
  - 没有查询到值：返回失败信息，停止执行
  - 查询到有效值：继续执行
- 进行比较
  - 表单提交的验证码
  - Redis取回的验证码
- 不一致：返回失败信息，停止执行
- 一致
  - 从Redis中删除当前KEY对应的验证码
  - 继续执行
- 远程调用database-provider方法检查登录账号是否被占用
  - 已经被占用：返回失败信息，停止执行
  - 没有被占用：继续执行
- 密码加密
- 远程调用database-provider方法执行保存操作

#### ②封装MemberVO

所在工程：distribution-crowd-1-common

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberVO {
	
	private String loginacct;
	
	private String userpswd;
	
	private String phoneNum;
	
	private String randomCode;

}
```

#### ⑤在database-provider中创建其他组件

- MemberController
- MemberService
- MemberServiceImpl

![image](../images/image14.png)

#### ⑥在database-provider中查找登录账号数量

##### [1]接口声明

所在工程：distribution-crowd-1-common

```java
@FeignClient("database-provider")
public interface DataBaseOperationRemoteService {
	
	@RequestMapping("/retrieve/loign/acct/count")
	ResultEntity<Integer> retrieveLoignAcctCount(@RequestParam("loginAcct") String loginAcct);

}
```

##### [2]database-provider中和接口方法对应的Controller方法

```java
@RequestMapping("/retrieve/loign/acct/count")
public ResultEntity<Integer> retrieveLoignAcctCount(
		@RequestParam("loginAcct") String loginAcct) {
	
	if(!CrowdUtils.strEffectiveCheck(loginAcct)) {
		return ResultEntity.failed(CrowdConstant.MESSAGE_LOGINACCT_INVALID);
	}
	
	try {
		int count = memberService.getLoginAcctCount(loginAcct);
		
		return ResultEntity.successWithData(count);
		
	} catch (Exception e) {
		e.printStackTrace();
		
		return ResultEntity.failed(e.getMessage());
	}
}
```

##### [3]database-provider中Controller方法调用的Service方法

```java
@Override
public Integer getLoignAcctCount(String loginAcct) {
	
	// 1.创建MemberPOExample
	MemberPOExample example = new MemberPOExample();
	
	// 2.封装查询条件
	example.createCriteria().andLoginacctEqualTo(loginAcct);
	
	// 3.执行查询
	return memberPOMapper.countByExample(example);
}
```

#### ⑦在database-provider中保存Member

##### [1]接口方法声明

所在工程：distribution-crowd-1-common

所在接口：com.atguigu.crowd.api.DataBaseOperationRemoteService

```java
@RequestMapping("/save/member/remote")
ResultEntity<String> saveMemberRemote(@RequestBody MemberPO memberPO);
```

<font color="red"><b>注意：一定要写@RequestBody注解，如果没写数据就传不过来。</b></font>

![image](../images/image15.png)

##### [2]和接口对应的Controller方法

所在工程：distribution-crowd-3-database-provider

所在类：com.atguigu.crowd.controller.MemberController

```java
@RequestMapping("/save/member/remote")
public ResultEntity<String> saveMemberRemote(@RequestBody MemberPO memberPO) {
	
	try {
		// 执行保存
		memberService.saveMemberPO(memberPO);
	} catch (Exception e) {
		e.printStackTrace();
		
		return ResultEntity.failed(e.getMessage());
	}
	
	return ResultEntity.successNoData();
}
```

Service方法

```java
@Override
@Transactional(readOnly=false, propagation=Propagation.REQUIRES_NEW, rollbackFor=Exception.class)
public void saveMemberPO(MemberPO memberPO) {
	memberPOMapper.insert(memberPO);
}
```

如果没有设置readOnly=false，那么会遇到下面异常：

```java
{
    "result": "FAILED",
    "message": "\r\n### Error updating database.  Cause: java.sql.SQLException: Connection is read-only. Queries leading to data modification are not allowed\r\n### The error may involve com.atguigu.crowd.mapper.MemberPOMapper.insert-Inline\r\n### The error occurred while setting parameters\r\n### SQL: insert into t_member (id, loginacct, userpswd,        username, email, authstatus,        usertype, realname, cardnum,        accttype)     values (?, ?, ?,        ?, ?, ?,        ?, ?, ?,        ?)\r\n### Cause: java.sql.SQLException: Connection is read-only. Queries leading to data modification are not allowed\n; Connection is read-only. Queries leading to data modification are not allowed; nested exception is java.sql.SQLException: Connection is read-only. Queries leading to data modification are not allowed",
    "data": null
}
```



#### ⑧密码加密处理

使用SpringSecurity中的BCryptPasswordEncoder加密工具进行密码加密和密码比较。

##### [1]创建配置类

所在工程：distribution-crowd-5-member-manager<br/>

全类名：com.atguigu.crowd.config.CrowdConfig<br/>

```java
package com.atguigu.crowd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class CrowdConfig {
	
	@Bean
	public BCryptPasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
```

##### [2]在需要的地方装配

```java
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
```

#### ⑨最终注册逻辑代码

所在工程：distribution-crowd-5-member-manager<br/>

所在类：com.atguigu.crowd.controller.MemberController

```java
@RequestMapping("/member/register")
public ResultEntity<String> register(@RequestBody MemberVO memberVO) {
	
	// 1.获取验证码数据并进行有效性检测
	String randomCode = memberVO.getRandomCode();
	
	if(!CrowdUtils.strEffectiveCheck(randomCode)) {
		return ResultEntity.failed(CrowdConstant.MESSAGE_RANDOM_CODE_INVALID);
	}
	
	// 2.获取手机号数据并进行有效性检测
	String phoneNum = memberVO.getPhoneNum();

	if(!CrowdUtils.strEffectiveCheck(phoneNum)) {
		return ResultEntity.failed(CrowdConstant.MESSAGE_PHONENUM_INVALID);
	}
	
	// 3.拼接Redis存储验证码的KEY
	String randomCodeKey = CrowdConstant.REDIS_RANDOM_CODE_PREFIX + phoneNum;
	
	// 4.远程调用redis-provider的方法查询对应验证码
	ResultEntity<String> randomCodeRemoteResultEntity = redisOperationRemoteService.retrieveRandomCodeRemote(randomCodeKey);
	
	if(ResultEntity.FAILED.equals(randomCodeRemoteResultEntity.getResult())) {
		return randomCodeRemoteResultEntity;
	}
	
	// 5.检查远程获取的验证码是否存在
	String randomCodeRemote = randomCodeRemoteResultEntity.getData();
	
	if(!CrowdUtils.strEffectiveCheck(randomCodeRemote)) {
		
		return ResultEntity.failed(CrowdConstant.MESSAGE_RANDOM_CODE_OUT_OF_DATE);
		
	}
	
	// 6.将“表单验证码”和“Redis验证码”进行比较
	if(!Objects.equal(randomCode, randomCodeRemote)) {
		return ResultEntity.failed(CrowdConstant.MESSAGE_RANDOM_CODE_NOT_MATCH);
	}
	
	// 7.检测登录账号是否被占用
	String loginacct = memberVO.getLoginacct();
	ResultEntity<Integer> loignAcctCountResultEntity = dataBaseOperationRemoteService.retrieveLoignAcctCount(loginacct);
	
	if(ResultEntity.FAILED.equals(loignAcctCountResultEntity.getResult())) {
		return randomCodeRemoteResultEntity;
	}
	
	Integer loignAcctCount = loignAcctCountResultEntity.getData();
	
	if(loignAcctCount > 0) {
		return ResultEntity.failed(CrowdConstant.MESSAGE_LOGIN_ACCT_ALREADY_IN_USE);
	}
	
	// 8.加密
	String userpswd = memberVO.getUserpswd();
	
	if(!CrowdUtils.strEffectiveCheck(userpswd)) {
		return ResultEntity.failed(CrowdConstant.MESSAGE_PASSWORD_INVALID);
	}
	
	userpswd = passwordEncoder.encode(userpswd);
	memberVO.setUserpswd(userpswd);
	
	// 9.将VO对象转换为PO对象
	MemberPO memberPO = new MemberPO();
	BeanUtils.copyProperties(memberVO, memberPO);
	
	// 10.执行保存操作
	ResultEntity<String> saveMemberRemoteResultEntity = dataBaseOperationRemoteService.saveMemberRemote(memberPO);
	
	return saveMemberRemoteResultEntity;
}
```

