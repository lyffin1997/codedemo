# demo
## 构建父工程步骤
    1. 字符编码：preferences->editor->file encoding,全选utf-8，Transparent打勾
    2. 注解生效激活：preferences->build->compiler->annotation processors->enable annotation process打勾
    3. java编译版本：preferences->build->compiler->java compiler选版本8
    4. 文件过滤：preferences->editor->file types，actionscript,*.as,在最下面一栏加上*.idea;*.iml

**redis:** 
   1. 运行镜像：docker run -d --name redis -p 6379:6379 redis:latest redis-server --appendonly yes --requirepass "你的密码"
   2. 进入容器：docker exec -ti 95b40 redis-cli 
   
**zookeeper:** 
   1. 运行镜像：docker run --name zookeeper -p 2181:2181 -p 2888:2888 -p 3888:3888 -d zookeeper:latest
   2. 进入容器：docker exec -it 95b40 zookeeper bash 
   
**dubbo-admin:** 
   1. 运行镜像：docker run -d --name dubbo-admin -v /Users/lyffin/software/dubbo-admin:/dubbo-admin -p 8888:8080 -e admin.registry.address=zookeeper://192.168.1.246:2181 -e admin.config-center=zookeeper://192.168.1.246:2181 -e admin.metadata-report.address=zookeeper://192.168.1.246:2181 apache/dubbo-admin:latest

**rabbitMQ:** 
   1. 运行镜像：docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 docker.io/rabbitmq:management
   2. 账户/密码：guest/guest
