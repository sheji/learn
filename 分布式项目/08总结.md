# 第八章 总结

## 1.架构

### ①单一架构

ui→依赖→component→依赖→common→依赖→entity<br/>

在parent中配置继承和聚合。<br/>

对应项目后台功能。

### ②分布式架构

对应项目前台功能。

![image01](.\images\image01.png)

## 2.业务功能

- 后台
  - Admin登录
  - 权限管理
    - Admin维护
    - Role维护
    - Menu维护
    - 权限控制：SpringSecurity
- 前台
  - Member登录
  - Member注册
  - 项目创建
  - 支付

## 3.技术点

- 构建工具：Maven
- 建模工具：PowerDesigner
- 框架主体：
  - Spring
  - SpringMVC
  - MyBatis
    - 插件：PageHelper
- 权限控制框架
  - SpringSecurity
- 前端框架
  - jQuery的Pagination
  - BootStrap
  - layer
  - zTree
- 分布式架构框架
  - SpringBoot
    - 整合MyBatis
    - 整合Redis
    - 使用thymeleaf作为视图模板
  - SpringCloud
    - 注册中心：Eureka
    - 远程方法的声明式调用：Feign
    - 负载均衡：Ribbon
  - Session共享：SpringSession
    - 使用Redis作为存储Session仓库
  - 使用swagger生成接口文档辅助测试
- 第三方接口
  - 短信发送接口
  - OSS对象存储服务
  - alipay支付（沙箱环境）