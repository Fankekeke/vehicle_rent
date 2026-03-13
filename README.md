### 基于SpringBoot + Vue的车辆租赁系统

汽车租赁、租车系统、租车预订SaaS

##### 核心资源与基础档案
###### 品牌/车辆管理： 统一维护汽车品牌信息与车辆电子档案，实时监控车辆运营状态，确保租赁资源的精准调配。

###### 车店管理/导航： 管理线下门店位置与营业详情，并为用户提供一键地图导航服务，实现线上订车与线下取车的无缝衔接。

###### 用户/员工管理： 建立完善的用户诚信档案与内部员工权限体系，通过精细化的人员管理保障系统安全有序运行。

##### 租赁业务与交易闭环
###### 车辆预订管理： 提供多维度选车与下单功能，支持租赁时段的灵活选择，实时锁定库存以避免车辆超预订风险。

###### 订单支付/记录： 集成主流支付接口并详细记录每一笔款项流水，通过数字账单实现资金流向的透明化与可追溯。

###### 订单评价体系： 收集用户对车辆状况及门店服务的真实反馈，通过评价数据驱动服务质量持续优化。

##### 车辆生命周期维护
###### 车辆维修管理： 记录车辆报修、送修及完修进度，确保存量资产始终处于安全行驶状态，降低运营过程中的安全隐患。

##### 信息发布与辅助支撑
###### 公告信息管理： 实时发布租车优惠、节假日调价及行业政策动态，建立平台与用户之间高效的信息触达通道。

#### 安装环境

JAVA 环境 

Node.js环境 [https://nodejs.org/en/] 选择14.17

Yarn 打开cmd， 输入npm install -g yarn !!!必须安装完毕nodejs

Mysql 数据库 [https://blog.csdn.net/qq_40303031/article/details/88935262] 一定要把账户和密码记住

redis

Idea 编译器 [https://blog.csdn.net/weixin_44505194/article/details/104452880]

WebStorm OR VScode 编译器 [https://www.jianshu.com/p/d63b5bae9dff]

#### 采用技术及功能

后端：SpringBoot、MybatisPlus、MySQL、Redis、支付宝沙盒支付
前端：Vue、Apex、Antd、Axios

平台前端：vue(框架) + vuex(全局缓存) + rue-router(路由) + axios(请求插件) + apex(图表)  + antd-ui(ui组件)

平台后台：springboot(框架) + redis(缓存中间件) + shiro(权限中间件) + mybatisplus(orm) + restful风格接口 + mysql(数据库)

开发环境：windows10 or windows7 ， vscode or webstorm ， idea + lambok

用户管理，车辆预订，订单支付，付款记录，订单评价，车店导航，公告管理，品牌管理，车辆维修，车店管理，车辆管理，员工管理



#### 前台启动方式
安装所需文件 yarn install 
运行 yarn run dev

#### 后端启动方式

1.首先启动redis，进入redis目录终端。输入redis-server回车
2.导入sql文件，修改数据库与redis连接配置
3.idea中启动后端项目

#### 默认后台账户密码
[管理员]
admin
1234qwer

[用户]
fank
1234qwer


#### 项目截图

|  |  |
|---------------------|---------------------|
|![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1696517265775.jpg) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1696517092518.jpg) |
|![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1696517251728.jpg) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1696517076004.jpg) |
|![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1696517198532.jpg) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1696517058026.jpg) |
|![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1696517174261.jpg) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1696517037615.jpg) |
|![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1696517152392.jpg) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1696517015150.jpg) |
|![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1696517133833.jpg) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1696517283969.jpg) |
|![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/1696517117031.jpg) | ![](https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/work/936e9baf53eb9a217af4f89c616dc19.png) |


#### 演示视频

暂无

#### 获取方式

Email: fan1ke2ke@gmail.com

WeChat: `Storm_Berserker`

`附带部署与讲解服务，因为要恰饭资源非免费，伸手党勿扰，谢谢理解😭`

> 1.项目纯原创，不做二手贩子 2.一次购买终身有效 3.项目讲解持续到答辩结束 4.非常负责的答辩指导 5.**黑奴价格**

> 项目部署调试不好包退！功能逻辑没讲明白包退！

#### 其它资源

[2025年-答辩顺利通过-客户评价🍜](https://berserker287.github.io/2025/06/18/2025%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2024年-答辩顺利通过-客户评价👻](https://berserker287.github.io/2024/06/06/2024%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2023年-答辩顺利通过-客户评价🐢](https://berserker287.github.io/2023/06/14/2023%E5%B9%B4%E7%AD%94%E8%BE%A9%E9%A1%BA%E5%88%A9%E9%80%9A%E8%BF%87/)

[2022年-答辩通过率100%-客户评价🐣](https://berserker287.github.io/2022/05/25/%E9%A1%B9%E7%9B%AE%E4%BA%A4%E6%98%93%E8%AE%B0%E5%BD%95/)

[毕业答辩导师提问的高频问题](https://berserker287.github.io/2023/06/13/%E6%AF%95%E4%B8%9A%E7%AD%94%E8%BE%A9%E5%AF%BC%E5%B8%88%E6%8F%90%E9%97%AE%E7%9A%84%E9%AB%98%E9%A2%91%E9%97%AE%E9%A2%98/)

[50个高频答辩问题-技术篇](https://berserker287.github.io/2023/06/13/50%E4%B8%AA%E9%AB%98%E9%A2%91%E7%AD%94%E8%BE%A9%E9%97%AE%E9%A2%98-%E6%8A%80%E6%9C%AF%E7%AF%87/)

[计算机毕设答辩时都会问到哪些问题？](https://www.zhihu.com/question/31020988)

[计算机专业毕业答辩小tips](https://zhuanlan.zhihu.com/p/145911029)


#### 接JAVAWEB毕设，纯原创，价格公道，诚信第一

`网站建设、小程序、H5、APP、各种系统 选题+开题报告+任务书+程序定制+安装调试+项目讲解+论文+答辩PPT`

More info: [悲伤的橘子树](https://berserker287.github.io/)

<p><img align="center" src="https://fank-bucket-oss.oss-cn-beijing.aliyuncs.com/img/%E5%90%88%E4%BD%9C%E7%89%A9%E6%96%99%E6%A0%B7%E5%BC%8F%20(3).png" alt="fankekeke" /></p>
