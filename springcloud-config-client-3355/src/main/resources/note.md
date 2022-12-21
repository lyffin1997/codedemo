1. application.yml是用户级资源配置项
2. bootstrap.yml是系统级，优先级更高，即springcloud先加载bootstrap.yml再加载application.yml

### 动态刷新github上获取懂信息
1. 添加actuator依赖
2. 配置文件开启所有端点，management……
3.controller加上@RefreshScope
4.github修改完后发送post请求，curl -X POST http://localhost:3355/actuator/refresh