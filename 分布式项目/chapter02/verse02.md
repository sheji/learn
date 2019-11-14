## 第二节 实体类封装数据方式进阶

### 1.原本的实体类

![image](../images/image09.png)

### 2.分布式架构环境下实体类

![images](../images/image10.png)

- VO：view object视图对象，封装和视图层交互的数据
- DO：data object数据对象，封装和具体数据源对应的数据
- DTO：data transfer object数据传输对象，封装从provider传输给consumer的数据
- PO：persist object持久化对象，封装要持久化的数据
- BO：business object业务对象，封装和业务逻辑模块对应的数据



### 3.说明

以上设定仅仅是为了在分布式架构环境下更好的封装数据而创建的新概念，不需要任何jar包的支持，所以没有语法层面的要求。根据实际需要设置即可，没有硬性要求。