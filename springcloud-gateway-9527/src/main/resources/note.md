#路由方式：
1. yml中配置
2. 代码中注入RouteLocator的Bean
#gateway核心:
1. predicate断言，满足断言条件则可进入路由uri
2. route路由
3. filter过滤器，可在请求被路由的前后对请求进行修改
    ## filter：
    1. 生命周期：分为pre和post（被路由前后）
    2. 种类：GatewayFilter（单一）和GlobalFilter（全局）