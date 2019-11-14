# 第三章 项目模块 第三节 创建项目管理工程

## 1.坐标

group id：com.atguigu.crowd<br/>

artifact id：distribution-crowd-6-project-manager<br/>

同样还是以MavenModule方式创建<br/>

## 2.依赖信息

```xml
<dependency>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
	<groupId>org.springframework.cloud</groupId>
	<artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
<dependency>
	<groupId>com.atguigu.crowd</groupId>
	<artifactId>distribution-crowd-1-common</artifactId>
	<version>0.0.1-SNAPSHOT</version>
</dependency>
```

## 3.application.yml

```yml
server:
  port: 5000
spring:
  application:
    name: project-manager
eureka:
  client:
    service-url:
      defaultZone: http://localhost:1000/eureka/
  instance:
    prefer-ip-address: true
```

## 4.主启动类

```java
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class CrowdMainType {
	
	public static void main(String[] args) {
		SpringApplication.run(CrowdMainType.class, args);
	}

}
```

