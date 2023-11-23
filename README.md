# 分布式篇  
+ 基础介绍及springsecurity单例demo参考oauth2文件夹中的HELP.md  
# 1. 什么是分布式系统  
+ 将单体结构的系统拆分为若干服务，服务之间通过网络交互来完成用户的业务处理  
![分布式系统架构图](./picture/img.png)  
+ 分布性：每个部分可独立部署
+ 伸缩性：每个部分可集群部署，进行硬件或软件扩容  
+ 共享性：每个部分可作为共享资源对外提供服务  
+ 开放性：每个部分可根据需求对外发布第三方接口  
# 2. 分布式系统认证需求  
+ 需求考虑：
  + 分布式系统每个服务都有认证、授权需求，但不可能每个服务都做一套认证授权，因此需要独立都认证服务来统一处理  
  + 分布式系统不仅要对内提供认证，还需要对第三方系统提供认证  
+ 基于以上考虑，总结需求如下：
  + 统一认证授权
    + 提供独立认证服务，统一处理认证授权  
    + 无论何种用户、客户端(web、h5、app)，均采用一致的认证、权限、会话机制，实现统一的认证授权  
    + 认证方式需可扩展，支持各种认证需求，如：账号密码、短信、二维码、人脸识别，并可灵活切换  
  + 应用接入认证  
    + 提供安全的系统对接机制，可开放部分api给第三方使用，系统内部及第三方应用均采用统一机制接入  
# 3. 分布式系统认证方案  
## 3.1 技术选型  
1. 基于session方式
+ 分布式系统基于session认证会出现一个问题：认证服务完成认证后，客户端请求需要将session带到其他服务，否则需要重新授权  
+ 解决思路大致分为三个方向：  
![基于session的分布式认证方案](./picture/img_1.png)  
+ **session复制：** 多个服务间同步session，如tomcat的session共享机制  
+ **Session黏贴：** 用户访问集群时，强制指定后续的所有请求落到同一个服务上，如nginx的哈希一致性策略  
+ **Session集中存储：** 将Session存入分布式缓存中，所有服务实例统一从分布式缓存取session，如redis  
+ **优点：** 更好的在服务端对会话进行控制，安全性高  
+ **缺点：**  
  + session方式基于cookie，在移动客户端无法有效使用  
  + cookie无法跨域  
  + 随着系统的扩展，需要提高session的复制、黏贴及存储的容错性  
2. 基于token方式  
![基于token的分布式认证方案](./picture/img_2.png)  
+ **优点：** 
  + 服务端不要存储认证数据，易维护扩展性强  
  + 客户端可以把token放在任意地方，可实现web和App的统一认证  
+ **缺点：** 
  + token由于包含了用户信息，一般数据量较大，每次请求都要传递token，因此比较占带宽  
  + token的签名验签操作会给cpu带来额外负担  
  + 由于token中包含了用户信息，有一定的安全风险(可通过加密方式解决)  
## 3.2 技术方案  
+ 基于上述选型分析，结合分布式系统认证需求，token更适合分布式系统：  
  + 适合统一认证要求，客户端、系统内部应用、第三方应用遵循一致的认证机制  
  + 对于第三方应用接入更合适，可使用开放协议Oauth2、jwt等  
  + 服务端无需存储会话信息，减轻服务端压力
+ 本工程分布式系统认证技术方案如下：  
+ ![分布式系统认证技术方案](./picture/img_3.png)  
+ 第5步网关等权限校验和第8步微服务等权限校验并不相同  
  + 网关是针对客户端的权限校验，判断这个客户端是否有权限访问，一般会提前颁发给客户端key和密钥  
  + 微服务权限校验是针对该客户端是否有该服务的访问权限，甚至细粒度到方法  
# 4. Oauth2.0  
## 4.1 介绍  
+ Oauth(开放授权)是一个开放标准，允许用户授权第三方应用访问他们存储在另外的服务提供者上的信息，而不需要将用户名和密码提供给第三方应用或分享他们数据的所有内容  
+ OAuth2.0是OAuth协议的延续版本，但不兼容Oauth1.0  
+ 举例：  
例如在一些视频网站可以采用qq登录而不要注册成为该网站的用户。那么qq便对该网站提供了第三方接口，以供该网站获取qq用户信息去完成网站登录  
+ **注意：** 
  + 虽然在视频网站输入了qq账号密码，但是账号密码是发往qq服务器的，视频网站并不会拿到账号密码(正规网站不会拿，而且还会把你的账号密码加密防止网络传输过程中外泄，但是黑网站会存你的账号密码)  
  + qq服务器校验完账号密码后返回一个token给视频网站  
  + 视频网站可以通过这个token从qq服务器拿到部分qq对第三方开放的用户信息  
+ 大致授权流程如下：  
![第三方授权认证流程图](./picture/img_4.png)  
+ Oauth2.0官方流程图如下：  
![oauth2.0流程图](./picture/img_5.png)  
+ **注意：** 
  + 在实际项目中，并不是所有客户端都可以接入授权服务器
  + 需要服务提供方给准入的第三方颁发一个身份，作为接入凭据(即3.2技术方案流程图中提到的网关这一层的权限校验)  
  + `client_id`，客户端标识  
  + `client_secret`，客户端密钥  
# 5. Spring-Security OAuth2  
+ Spring-Security OAuth2是基于OAuth2标准协议实现的安全框架  
+ 它包含了两个服务：
  + **授权服务(Authrization Server)：** 应包含对接入端及登入用户对合法性进行验证并颁发token等功能  
    + 请求端点可通过springmvc控制器实现：  
    + **AuthorizationEndpoint**服务于认证请求，默认url: `/oauth/authrize`  
    + **TokenEndpoint**服务于访问令牌等请求，默认url: `/oauth/token`  
  + **资源服务(Resource Server)：** 应包含保护资源，拦截非法请求等功能，对请求中对token进行解析鉴权等  
    + 可通过下面过滤器实现资源服务：  
    + **OAuth2AuthenticationProcessingFilter**用来解析鉴权token  

# 6. 搭建工程  
1. 创建maven项目  
2. 配置pom.xml文件
3. 创建相关文件夹
4. 创建启动类
5. 创建配置文件applcation.yml  

## 6.1 授权服务配置  
+ 在任意配置类添加@EnableAuthorizationServer注解  
![授权配置类](./picture/img_6.png)  
+ 在AuthorizationServer类中进行相关配置，一共三个`configure()`方法
  + 客户端管理，参考代码中注释信息
  + 管理令牌
    + 通过`AuthorizationServerTokenServices`接口进行令牌管理
      + 可以自己实现`AuthorizationServerTokenServices`接口，需要继承`DefaultTokenServices`这个类
    + 但是持久化令牌是通过另一个接口`TokenStore`来实现的
      + `TokenStore`有一个默认实现`InMemoryTokenStore`，即存储在内存中
      + 除此之外还有一些预定义实现：
        + `JdbcTokenStore`：基于数据库存储令牌，可以在不同服务器间共享令牌信息，使用该方式时需要注意引入`spring-jdbc`相关依赖
        + `JwtTokenStore`：该方式服务端不存储令牌(可去了解什么是jwt)，它可以把令牌相关数据进行编码，但是它有两个缺点：
          + 想要撤销一个已经授权的令牌非常困难，所以通常用来处理生命周期较短的令牌或者撤销刷新令牌
          + 如果加入了较多用户凭证信息，令牌的占用空间会较大
    + 单独建立生成令牌的配置文件类`TokenConfig`
    + 在`AuthorizationServer`中配置令牌管理
  + 配置令牌访问端点及授权类型相关设置
    + `AuthorizationServerEndpointsConfigurer`的实例可以完成令牌服务及令牌访问端点的配置
      + 配置授权类型'Grant Types'
      + 在`AuthorizationServer`中配置令牌
  + 配置令牌约束
    + 在`AuthorizationServer`中配置令牌约束  

## 6.2 授权服务测试  
### 6.2.1 授权码模式测试  
![交互图](./picture/img_7.png)  
+ 浏览器访问: http://localhost:53020/oauth/authorize?client_id=c1&response_type=code&scope=all&redirect_uri=https://www.baidu.com  
+ 跳转到授权页面  
![授权页面](./picture/img_8.png)  
+ 输入userDetailService中定义的账号密码  
![手动确认](./picture/img_9.png)  
+ 若认证成功，需手动选择approve授权
+ 之后路径中会返回code码  
![code码](./picture/img_10.png)  
+ 然后用postman发送post请求(附带刚刚获取的code)获取token：http://localhost:53020/uaa/oauth/token  
![获取token](./picture/img_11.png)  
+ 成功获取token如下图  
![token](./picture/img_12.png)  
### 6.2.2 授权码模式总结
+ 这种模式是四种模式中最安全的模式
+ 一般用于客户端是web服务器应用或者第三方的原生app应用调用资源服务时
+ 因为该方式token不会经过浏览器或者app，减少令牌泄漏风险
  + 申请code请求认证通过后，授权服务会直接往客户端的后端服务器返回code
  + 拿到code再次申请token也是在客户端的后端服务器进行
  + 因此整个过程token相关信息不会暴露在浏览器或app上  
### 6.2.3 简化模式测试  
+ 简化模式流程图  
![简化模式](./picture/img_13.png)  
+ 浏览器发送请求：http://localhost:53020/oauth/authorize?client_id=c1&response_type=token&scope=all&redirect_uri=https://www.baidu.com  
+ 与授权码模式相比，它发送code请求并认证成功后会直接返回token，不需要在发送token请求  
![token返回结果](./picture/img_14.png)  
+ 返回的`access_token`以hash的形式存在在重新定向的uri的fragment中
  + `fragment`主要用来标识URI所标识资源里的某个资源，在uri的末尾通过'#'作为fragment的开头
  + 其中'#'不属于`fragment`的值
  + js可通过某些方法获取fragment的值
+ 简化模式一般用于没有服务器端的第三方但页面应用  
### 6.2.4 密码模式测试  
+ 密码模式流程图  
![密码模式](./picture/img_15.png)  
+ postman发送请求：http://localhost:53020/oauth/token?client_id=c1&client_secret=secret&grant_type=password&username=D46033&password=1234  
![token请求](./picture/img_16.png)  
+ 缺点：该模式会把账号密码等用户信息泄漏给客户端(不是指浏览器，指后端)
  + 因此密码模式一般用于自己开发测试  
### 6.2.6 客户端模式测试
+ 客户端模式流程图  
![客户端模式](./picture/img_17.png)  
+ postman发送请求：http://localhost:53020/oauth/token  
![token请求](./picture/img_18.png)  
+ 这是最方便但最不安全但模式  
  + 要求授权服务对客户端完全信任
  + 且客户端本身足够安全
  + 例如：合作第三方对接，用于拉取一组用户信息  

## 6.3 资源服务配置
+ 创建`ResourceServerConfig`文件并配置
  + `@EnableResourceServer`声明为资源服务
  + 配置其他  
  + 配置tokenServices
+ 创建`SecurityConfig`并配置
  + 不配置的话`@PreAuthorize()`不生效
+ 使用postman测试token：http://localhost:53020/oauth/check_token  
![check_token](./picture/img_19.png)  
+ 访问资源测试  
  + 在header中添加参数`Authorization`
  + 值为`Bearer token`  
![访问资源](./picture/img_20.png)  

# 7. jwt令牌  
+ 分布式系统中资源服务需要使用`RemoteTokenServices`请求授权服务验证token，访问量大时可能会影响系统性能  
  + 解决这一问题可以用jwt令牌
  + 用户认证后得到一个jwt令牌，令牌中包含用户相关信息  
  + 客户端携带jwt令牌访问资源服务  
  + 资源服务根据事先约定大算法自行完成令牌校验  
  + 这样就不用每次请求认证服务了  
+ jwt(JSON Web Token)是一种开放的行业标准  
  + 通信双方传递json对象
  + 传递的信息经过数字签名可以被信任和验证
  + 可以使用HMAC算法或RSA算法来签名，防止被篡改  
+ jwt令牌优点
  + 基于json，方便解析  
  + 可在令牌中自定义丰富的内容，已扩展
  + 通过非对称加密算法及数字签名技术，jwt防止被篡改，安全性高
  + 资源服务使用jwt可不依赖认证服务完成授权
+ jwt令牌缺点
  + 令牌较长，占存储空间比较大
## 7.1 jwt结构  
+ jwt令牌由三部分组成：
+ **Header:**
  + 包含令牌类型及使用的哈希算法(HMAC、SHA256、RSA)
  + 将下面内容使用Base64Url编码，得到Header部分
  + 其中HS256表示HMAC-SHA256算法
```json
{
    "alg": "HS256",
    "typ": "JWT"
}
```
+ **Payload:**
  + 存放有效信息，可存放jwt提供的现成字段，如：
    + iss(签发者)、exp(过期时间戳)、sub(面向的用户)
  + 也可自定义字段(不建议存放敏感信息，因为该部分可解码还原原始内容)  
  + 将下面内容使用Base64Url编码，得到Payload部分  
```json
{
  "sub": "1234567",
  "name": "lf",
  "admin": true
}
```
+ **Signature:**
  + 签名，防止令牌被篡改
  + 使用Base64Url将前两部分的内容(header和payload为原始json)再编码
  + 编码后使用`.`连接组成字符串
  + 最后使用header中的算法进行签名
  + 内容如下：  
```java
HMACSHA256(
  base64UrlEncode(header) + "." +
  base64UrlEncode(payload),
  secret)
```
+ 最终组成：`base64UrlEncodedHeader + "." + base64UrlEncodedPayload + "." + base64UrlEncodedSignature`
+ **注意:**
  + Header 和 Payload 只是简单的利用 Base64 编码，是可逆的，因此不要在 Payload 中存储敏感信息
  + Signature 使用的是不可逆的加密算法，无法解码出原文，它的作用是校验 Token 有没有被篡改。该算法需要我们自己指定一个密钥，这个密钥存储在服务端，不能泄露
  + 尽量避免在 JWT 中存储大量信息，因为一些服务器接收的 HTTP 请求头最大不超过 8KB  

## 7.2 jwt令牌测试
+ 修改`TokenConfig`配置
+ 修改`AuthorizationServer`配置
+ 使用postman测试：http://localhost:53020/oauth/token  
![jwt_token](./picture/img_21.png)  
+ 可见，token已经变成jwt_token  
## 7.3 资源服务校验jwt令牌  
+ 新增`TokenConfig`
+ 修改`ResourceServerConfig`
+ postman测试：http://localhost:53021/r1  
![资源服务测试](./picture/img_22.png)  
## 7.3 完善配置  
+ `AuthorizationServer`中
  + 客户端配置现在还是在内存中，需要改为数据库
  + 授权码存取模式还在内存，需要改为数据库  
# 8. 搭建分布式demo
## 8.1 搭建gateway
+ 网关整合oauth2有两种思路
  + 认证服务生成jwt令牌，所有请求统一在网关层验证，判断权限  
  + 请求由各自资源服务处理，网关只做转发
  + 本demo以第一种为例
+ **整体思路：** 把网关作为`oauth2.0`资源服务，实现接入客户端权限拦截、令牌解析、并转发当前登录用户信息给资源服务
  + 这种情况，下游微服务就不需要关注令牌格式解析及`oauth2.0`相关机制了
+ **网关的主要工作：** 
  + 作为`oauth2.0`资源服务器，实现接入方权限拦截
  + 令牌解析并转发明文token给微服务
+ 微服务拿到token后干两件事：
  + 用户授权拦截(查看当前用户是否有权访问该资源)
  + 将用户信息存储进当前线程上下文(方便后续业务逻辑随时获取用户信息)  

# 9. 总结
## 9.1 问题总结  
+ 什么是认证、授权、会话
+ java servlet为支持http会话做了哪些事
+ 基于session机制的认证流程
+ 基于token机制的认证流程
+ spring security
  + 工作原理
  + 结构总览
  + 认证、授权流程
  + 认证、授权过程涉及组件及其作用，如何自定义这些组件
+ oauth2.0四种模式及其大体流程
+ spring cloud security oauth2包含哪些组件及其作用
+ 分布式系统认证需要解决哪些问题  
## 9.2 参考资料
+ 视频：https://www.bilibili.com/video/BV1VE411h7aL/?p=49&spm_id_from=pageDriver&vd_source=422073808a5a89732b3d9bfcc1703ccc
+ demo：https://github.com/baixiaoyiren/Coud-SpringSecurity-Oauth2/tree/master/10-securityoauth2




