package com.lyffin.security.distributed.uaa.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import javax.sql.DataSource;
import java.util.Arrays;

@Configuration
@EnableAuthorizationServer
//继承类为授权服务配置的父类
public class AuthorizationServer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private TokenStore tokenStore;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AuthorizationCodeServices authorizationCodeServices;

    @Autowired
    private JwtAccessTokenConverter accessTokenConverter;

    @Bean
    public ClientDetailsService JdbcClientDetailsService(DataSource dataSource) {
        ClientDetailsService clientDetailsService = new JdbcClientDetailsService(dataSource);
        ((JdbcClientDetailsService) clientDetailsService).setPasswordEncoder(passwordEncoder);
        return clientDetailsService;
    }

    /**
     * 客户端详情服务
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception{
        clients.withClientDetails(JdbcClientDetailsService);
    }

    @Autowired
    @Qualifier("JdbcClientDetailsService")
    private ClientDetailsService JdbcClientDetailsService;

    /**
     * 令牌管理服务
     */
    @Bean
    public AuthorizationServerTokenServices tokenService() {
        DefaultTokenServices services = new DefaultTokenServices();
        //设置客户端详情服务
        services.setClientDetailsService(JdbcClientDetailsService);
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
                .authorizationCodeServices(authorizationCodeServices)
                .tokenServices(tokenService())
                .allowedTokenEndpointRequestMethods(HttpMethod.POST);
    }

    /**
     * 设置授权码模式的授权码存取方式，这里为内存存取
     */
    @Bean
    public AuthorizationCodeServices authorizationCodeServices(DataSource dataSource) {
        return new JdbcAuthorizationCodeServices(dataSource);
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
