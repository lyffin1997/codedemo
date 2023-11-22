package com.lyffin.security.distributed.uaa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.lang.reflect.Array;
import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
//继承类为授权服务配置的父类
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;

    /**
     * 配置客户端详情，可选择配置在内存或数据库
     * 查找数据库的大致过程：ClientDetailService负责查找ClientDetails，而ClientDetails包含下列几个属性
     * clientId：客户端id
     * secret：密钥
     * scope：用来限制客户端访问范围，若为空(默认)，那么客户端拥有全部访问范围
     * authorizedGrantTypes：客户端可以使用的授权类型，默认为空
     * authorities：客户端可以使用的权限(基于spring security authorities，若忘了spring security可以看看单例篇文档)
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception{
            //基于数据库：clients.withClientDetails(clientDetailsService);
        clients.inMemory()//基于内存
                .withClient("c1")//client_id
                .secret(new BCryptPasswordEncoder().encode("secret"))//密钥
                .resourceIds("res1")//资源列表，需要结合资源服务
                .authorizedGrantTypes("authorization_code", "password", "client_credentials", "implicit", "refresh_token")//允许五种授权方式：授权码模式、密码模式、客户端模式、简要模式、刷新令牌模式
                .scopes("all")//允许授权范围
                .autoApprove(false)//授权码模式时有个授权页面，false:跳转到授权页面；  true: 不跳转
                .redirectUris("https://www.baidu.com");//客户端回调地址
//                配置第二个客户端
//                .and()
//                .withClient("c1")//client_id
//                .secret(new BCryptPasswordEncoder().encode("secret"))//密钥
//                .resourceIds("res1")//资源列表，需要结合资源服务
//                .authorizedGrantTypes("authorization_code", "password", "client_credentials", "implicit", "refresh_token")//允许五种授权方式：授权码模式、密码模式、客户端模式、简要模式、刷新令牌模式
//                .scopes("all")//允许授权范围
//                .autoApprove(false)//授权码模式时有个授权页面，false:跳转到授权页面；  true: 不跳转
//                .redirectUris("http://www.baidu.com")//客户端回调地址
    }

    /**
     * 令牌管理服务
     */
    @Bean
    public AuthorizationServerTokenServices tokenService() {
        DefaultTokenServices services = new DefaultTokenServices();
        //设置客户端详情服务
        services.setClientDetailsService(clientDetailsService);
        //支持刷新令牌
        services.setSupportRefreshToken(true);
        //设置令牌存储策略
        services.setTokenStore(tokenStore);
        //设置令牌增强
        TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
        tokenEnhancerChain.setTokenEnhancers(Arrays.asList(accessTokenConverter));
        services.setTokenEnhancer(tokenEnhancerChain);
        //令牌默认有效期，7200s
        services.setAccessTokenValiditySeconds(7200);
        //刷新令牌默认有效期，259200s
        services.setRefreshTokenValiditySeconds(259200);
        return services;
    }

    /**
     * 授权类型配置
     * authenticationManager：认证管理器，授权类型为'password'时需要配置认证管理器
     * userDetailsService：可以设置自己实现但UserDetailsService接口实例，或者进行全局设置(
     *                     在GlobalAuthenticationManagerConfigurer配置)，设置了这个之后，
     *                     'refresh_token'模式流程中会包含一个检查，用来确保这个账号是否仍然
     *                     有效，例如在你禁用该账号但情况下
     * authorizationCodeServices：用于授权码模式
     * implicitGrantService：用于设置和管理隐式授权模式
     * tokenGranter：设置该值后授权将完全由你掌控，并忽略以上属性的设置，一般作拓展用途时使用
     *
     * spring security默认令牌访问端点
     * /oauth/authorize：授权端点
     * /oauth/token：令牌端点
     * /oauth/confirm_access：用户确认授权提交端点
     * /oauth/error：授权服务错误信息端点
     * /oauth/check_token：用于资源服务访问的令牌解析端点
     * /oauth/token_key：提供公有密钥的端点（限jwt令牌）
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)//该认证管理器为spring security中的认证管理器
                .authorizationCodeServices(new InMemoryAuthorizationCodeServices())
                .tokenServices(tokenService())
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }

    /**
     * 设置授权码模式的授权码存取方式，这里为内存存取
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices() {
        return new InMemoryAuthorizationCodeServices();
    }

    /**
     * 令牌约束
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .tokenKeyAccess("permitAll()")//对应公有密钥端点，即/oauth/token_key为公开
                .checkTokenAccess("permitAll()")//对应资源服务调用解析令牌端点，即/oauth/check_token公开
                .allowFormAuthenticationForClients();//允许表单认证
    }
}
