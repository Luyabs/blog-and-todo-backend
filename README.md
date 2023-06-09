# blog-and-todo-backend

## Api文档路径
http://124.70.195.38:996/doc.html   

## 前端项目地址
https://github.com/YEYUY/ToDoList-wechatApp/tree/main

## 小程序测试 [扫码进入]
![E}CY_H)DNY TAGF5Z41WKKU_tmb](https://github.com/Luyabs/blog-and-todo-backend/assets/74538732/28b61812-87e8-458d-a32d-11329c01203b)


## 运行前须知
1. 需要在/src/main/resources下追新增一个文件: application-dev.yaml
2. 在这个文件中追加下面这段代码 并修改数据库信息

```yaml
server:
  port: 996

spring:
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource  # DRUID
    url: jdbc:postgresql://???:???/???
    username: ????
    password: ????

wechat:
  appid: ?????小程序id??????
  secret: ?????微信密钥?????

base-url:
  avatar: D:/tmp/blog-and-todo/avatar/
  note-file: D:/tmp/blog-and-todo/note-file/
```


## 项目结构
| 项目结构           |                           |                        | 描述              | 功能                                                              |
|----------------|---------------------------|------------------------|-----------------|-----------------------------------------------------------------|
| \common        | 通用层                       |                        |                 |                                                                 |
| …              | \exception                |                        |                 |                                                                 |
| …              | …                         | GlobalExceptionHandler | 全局异常处理器         | 处理service层及以下的exception，并以Result形式返回给前端                         |
| …              | …                         | ServiceException       | 自定义业务层异常类       | service层抛掷用异常                                                   |
| …              | \interceptor              |                        |                 |                                                                 |
| …              | …                         | JwtInterceptor         | JWT拦截器          | 预处理请求中的token，并通过UserInfo从token中解析出userId存到UserInfo.threadLocal中 |
| …              | …                         | Knife4jInterceptor         | K4J拦截器          | 在非dev环境下拦截所有对api文档的请求                                           |
| …              | AutoFillMetaObjectHandler |                        | 元数据处理器          | MybatisPlus 填充公共字段                                              |
| …              | JwtUtils                  |                        | Token生成器(JWT)   | 生成与解析token                                                      |
| …              | NeedToken                 |                        | 自定义注解           | 标记哪些方法需要在请求头中传入token                                       |
| …              | Result                    |                        | 数据一致性处理         | 返回统一格式                                                          |
| …              | UserInfo                  |                        | 封装线程副本工具类       | 在处理token时保存一份userId到此类中                                         |
|                |                           |                        |                 |                                                                 |
| \config        | 配置层(配置/提供Bean)            |                        |                 |                                                                 |
| …              | Knife4jConfig             |                        | API文档配置类        | 配置Knife4j api文档                                                 |
| …              | MybatisPlusConfig         |                        | Mybatis分页拦截器配置类 | 配置Mybatis分页查询功能                                                 |
| …              | RestTemplateConfig        |                        | RestTemplate类   | 负责Http请求                                                        |
| …              | WebMvcConfig              |                        | MVC配置类          | 配置静态资源映射 跨域映射 拦截器配置 序列化处理                                       |
|                |                           |                        |                 |                                                                 |
|                |                           |                        |                 |                                                                 |
| \controller    | 控制器层                      |                        | Controller      | 资源映射                                                            |
|                |                           |                        |                 |                                                                 |
| \service       | 业务层                       |                        | Model           | 逻辑处理 调用mapper 异常处理 安全保证 连中间件                                    |
|                |                           |                        |                 |                                                                 |
| \mapper        | 数据层                       |                        | Model           | 存放SQL                                                           |
|                |                           |                        |                 |                                                                 |
| \entity        | 实体类层                      |                        | Model           | 与单表对应的Bean                                                      |
|                |                           |                        |                 |                                                                 |
| \dto           | 实体对象转换层                   |                        | Model           | 在entity基础上封装或合并的Bean                                            |
|                |                           |                        |                 |                                                                 |
|                |                           |                        |                 |                                                                 |
| 非\java目录下的文件夹: |                           |                        |                 |                                                                 |
| \resources     | 配置文件                      |                        |                 |                                                                 |
|                |                           |                        |                 |                                                                 |
| \test          | SpringBoot 测试类            |                        |                 |                                                                 |
