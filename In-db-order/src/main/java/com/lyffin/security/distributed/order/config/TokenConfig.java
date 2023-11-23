package com.lyffin.security.distributed.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class TokenConfig {

    /**
     * jwt密钥
     */
    private String SIGNING_KEY = "uaa123";

    /**
     * jwt令牌存储token
     */
    @Bean
    public TokenStore tokenStore() {
        return new JwtTokenStore(accessTokenConverter());
    }

    /**
     * 生成jwt令牌需要经过一系列哈希算法
     * 因此在此设置转换器
     */
    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //设置对称密钥，资源服务使用该密钥来验证
        converter.setSigningKey(SIGNING_KEY);
        return converter;
    }
}
