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

**zipkin:** 
   1. 运行镜像：docker run -d -p 9411:9411 openzipkin/zipkin 
   2. 页面：localhost:9411/zipkin

**nacos:** 
   1. 运行镜像：docker run --name nacos -d -p 8848:8848 -p 9848:9848 -p 9849:9849 --privileged=true -e JVM_XMS=256m -e JVM_XMX=256m -e MODE=standalone -e PREFER_HOST_MODE=hostname -e SPRING_DATASOURCE_PLATFORM=mysql -e MYSQL_SERVICE_HOST=192.168.1.246 -e MYSQL_SERVICE_PORT=3306 -e MYSQL_SERVICE_DB_NAME=nacos -e MYSQL_SERVICE_USER=root -e MYSQL_SERVICE_PASSWORD=123456 -v /Users/lyffin/lyffin/docker/nacos/logs:/home/nacos/logs -v /Users/lyffin/lyffin/docker/nacos/conf/custom.properties:/home/nacos/init.d/custom.properties -v /Users/lyffin/lyffin/docker/nacos/data:/home/nacos/data nacos/nacos-server:latest 
   2. 自启动：docker update --restart=always nacos 
   3. 页面：localhost:8848/nacos    nacos/nacos 
   4. 集群： 
         1. 删除子网络：docker network rm my-net 
         2. 创建子网络：docker network create mynet --subnet 172.19.0.0/16 
         3. docker run -d -e PREFER_HOST_MODE=ip -e MODE=cluster -e NACOS_SERVERS="172.19.0.42:8848 172.19.0.43:8848" -e SPRING_DATASOURCE_PLATFORM=mysql -e MYSQL_SERVICE_HOST=172.19.0.2 -e MYSQL_SERVICE_PORT=3306 -e MYSQL_SERVICE_DB_NAME=nacos -e MYSQL_SERVICE_USER=root -e MYSQL_SERVICE_PASSWORD=123456 -p 3333:8848 -p 4333:9848 -p 4334:9849 --name nacos01 --net mynet --ip 172.19.0.41 nacos/nacos-server:latest 
         4. docker run -d -e PREFER_HOST_MODE=ip -e MODE=cluster -e NACOS_SERVERS="172.19.0.41:8848 172.19.0.43:8848" -e SPRING_DATASOURCE_PLATFORM=mysql -e MYSQL_SERVICE_HOST=172.19.0.2 -e MYSQL_SERVICE_PORT=3306 -e MYSQL_SERVICE_DB_NAME=nacos -e MYSQL_SERVICE_USER=root -e MYSQL_SERVICE_PASSWORD=123456 -p 4444:8848 -p 5444:9848 -p 5445:9849 --name nacos02 --net mynet --ip 172.19.0.42 nacos/nacos-server:latest 
         5. docker run -d -e PREFER_HOST_MODE=ip -e MODE=cluster -e NACOS_SERVERS="172.19.0.41:8848 172.19.0.42:8848" -e SPRING_DATASOURCE_PLATFORM=mysql -e MYSQL_SERVICE_HOST=172.19.0.2 -e MYSQL_SERVICE_PORT=3306 -e MYSQL_SERVICE_DB_NAME=nacos -e MYSQL_SERVICE_USER=root -e MYSQL_SERVICE_PASSWORD=123456 -p 5555:8848 -p 6555:9848 -p 6556:9849 --name nacos03 --net mynet --ip 172.19.0.43 nacos/nacos-server:latest 
   
**mysql:** 
   1. 运行镜像：docker run -itd -p 3306:3306 --name mysql -v /Users/lyffin/lyffin/docker/mysql/conf/my.cnf:/etc/my.cnf -v /Users/lyffin/lyffin/docker/mysql/data:/var/lib/mysql --privileged=true -e MYSQL_ROOT_PASSWORD=123456 -d mysql:8.0.27 
   2. 配合nacos集群：docker run -it -p 3306:3306 --name mysql --net mynet --ip 172.19.0.2 -v /Users/lyffin/lyffin/docker/mysql/conf/my.cnf:/etc/my.cnf -v /Users/lyffin/lyffin/docker/mysql/data:/var/lib/mysql --privileged=true -e MYSQL_ROOT_PASSWORD=123456 -d mysql:8.0.27 

**nginx:** 
   1. 运行镜像：docker run --name nginx -p 80:80 -p 1080:1080 -p 1081:1081 -v /Users/lyffin/lyffin/docker/ngix/nginx.conf:/etc/nginx/nginx.conf -v /Users/lyffin/lyffin/docker/ngix/www/:/usr/share/nginx/html/ -v /Users/lyffin/lyffin/docker/ngix/logs/:/var/log/nginx/ -v /Users/lyffin/lyffin/docker/ngix/conf/:/etc/nginx/conf.d --privileged=true -d nginx:latest 

**sentinel:** 
   1. 运行镜像：docker run --name sentinel -p 8858:8858 -d bladex/sentinel-dashboard:latest 
   2. 页面：http://localhost:8858/   sentinel/sentinel 

**seata:** 
   1. 运行镜像：docker run -d --name seata-server -p 8091:8091 -e SEATA_IP=192.168.1.246 -v /Users/lyffin/lyffin/docker/seata/seata-server:/seata-server seataio/seata-server:1.5.0 

**minio单机无纠删码:** 
   1. docker run -d -p 9000:9000 -p50000:50000 --name minio -e "MINIO_ROOT_USER=ytp" -e "MINIO_ROOT_PASSWORD=yuntu@byd" -v /Users/lyffin/lyffin/docker/minio/data:/data -v /Users/lyffin/lyffin/docker/minio/config:/root/.minio minio/minio server --console-address ":50000" /data 
   2. 页面: localhost:50000 

**minio单机纠删码模式:** 
   1. docker run -d -p 9000:9000 -p50000:50000 --name minio_ec -e "MINIO_ROOT_USER=ytp" -e "MINIO_ROOT_PASSWORD=yuntu@byd" -v /Users/lyffin/lyffin/docker/minio/data1:/data1 -v /Users/lyffin/lyffin/docker/minio/data2:/data2 -v /Users/lyffin/lyffin/docker/minio/data3:/data3 -v /Users/lyffin/lyffin/docker/minio/data4:/data4 -v /Users/lyffin/lyffin/docker/minio/data5:/data5 -v /Users/lyffin/lyffin/docker/minio/data6:/data6 -v /Users/lyffin/lyffin/docker/minio/data7:/data7 -v /Users/lyffin/lyffin/docker/minio/data8:/data8 -v /Users/lyffin/lyffin/docker/minio/config:/root/.minio minio/minio server --console-address ":50000" /data{1...8} 
