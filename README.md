# demo
**token:** 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 

**maven_address:** https://mvnrepository.com/artifact/com.alibaba/druid  

**redis:** 
   1. 运行镜像：docker run -d --name redis -p 6379:6379 redis:latest redis-server --appendonly yes --requirepass "你的密码"
   2. 进入容器：docker exec -ti 95b40 redis-cli 
   
**zookeeper:** 
   1. 运行镜像：docker run --name zookeeper -p 2181:2181 -p 2888:2888 -p 3888:3888 -d zookeeper:latest
   2. 进入容器：docker exec -it 95b40 zookeeper bash 
   
**dubbo-admin:** 
   1. 运行镜像：docker run -d --name dubbo-admin -v /Users/lyffin/software/dubbo-admin:/dubbo-admin -p 8888:8080 -e admin.registry.address=zookeeper://192.168.1.246:2181 -e admin.config-center=zookeeper://192.168.1.246:2181 -e admin.metadata-report.address=zookeeper://192.168.1.246:2181 apache/dubbo-admin:latest
