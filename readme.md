# Java秒杀系统方案优化 掌握海量访问通用解决方案
## 第1章 课程介绍
### 1 课程目标
- 秒杀核心技术
- 不仅仅是秒杀
### 2 课程技术点
- 前端：Thymeleaf、Bootstrap、JQuery
- 后端：SpringBoot、JSR303、MyBatis
- 中间件：RabbitMQ、Redis、Hikari
### 3 课程介绍
#### 3.1 课程内容
- 分布式会话
- 商品列表页
- 商品详情页
- 订单详情页
- 系统压测
- 缓存优化
- 消息队列
- 接口安全
#### 3.2 能学到什么：应对大并发
- 如何利用缓存
- 如何使用异步
- 如何编写优雅的代码
#### 3.3 章节安排
##### 第一章 项目框架搭建
- SpringBoot环境搭建
- 集成Thymeleaf，Result结果封装
- 集成Mybatis+Hikari
- 集成Jedis+Redis安装+通用缓存Key封装
##### 第二章 实现登录功能
- 数据库设计
- 明文密码两次MD5处理
- JSR303参数校验+全局异常处理器
- 分布式Session
##### 第三章 实现秒杀功能
- 数据库设计
- 商品列表页
- 商品详情页
- 订单详情页
##### 第四章 JMeter压测
- JMeter入门
- 自定义变量模拟多用户
- Meter命令行使用
- SpringBoot打war包
##### 第五章 页面优化技术
- 页面缓存+URL缓存+对象缓存
- 页面静态化，前后端分离
- 静态资源优化
- CDN优化
##### 第六章 接口优化
- Redis预减库存减少数据库访问
- 内存标记减少Redis访问
- RabbitMQ队列缓冲，异步下单，增强用户体验
- RabbitMQ安装与Spring Boot集成
- 访问Nginx水平扩展
- 压测
##### 第七章 安全优化
- 秒杀接口地址隐藏
- 数学公式验证码
- 接口防刷