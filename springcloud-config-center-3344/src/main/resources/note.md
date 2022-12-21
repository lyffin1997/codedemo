# 分布式资源配置中心
1. github上修改完配置文件后，发送post请求：curl -X POST http://localhost:3344/actuator/busrefresh
2. 要想指定微服务实例生效：curl -X POST http://localhost:3344/actuator/busrefresh/{destination}
3. destination格式：微服务名+端口号，如springcloud-config-client:3355