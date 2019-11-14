```yml
server:
  port: 2000
spring:
  datasource:
    username: root
    password: 123456
    url: jdbc:mysql://192.168.56.200:3306/distributed-crowd?useSSL=false&useUnicode=true&characterEncoding=UTF-8
    driver-class-name: com.mysql.jdbc.Driver
  application:
    name: database-provider #当前微服务名称，注册服务信息时必须有
mybatis:
  config-location: classpath:mybatis/mybatis-config.xml
  mapper-locations: classpath:mybatis/mapper/*.xml
eureka:
  client:
    service-url:
      defaultZone: http://localhost:1000/eureka/
  instance:
    prefer-ip-address: true
```

