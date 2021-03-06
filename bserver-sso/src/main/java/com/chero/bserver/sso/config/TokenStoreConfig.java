package com.chero.bserver.sso.config;


import com.chero.bserver.sso.enhancer.TokenJwtEnhancer;
import com.chero.bserver.sso.model.service.UserService;
import com.chero.bserver.sso.properties.CustomSecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.approval.InMemoryApprovalStore;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultUserAuthenticationConverter;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * Created by hxh on 2018/4/9.
 */
@Configuration
public class TokenStoreConfig {

    /**
     * 使用redis存储token的配置，只有在chero.com.chero.server.sleep.security.oauth2.tokenStore配置为redis时生效
     * @author hxh
     *
     */
    @Configuration
    @ConditionalOnProperty(prefix = "chero.com.chero.security.oauth2", name = "tokenStore", havingValue = "redis")
    public static class RedisConfig {

        @Autowired
        private RedisConnectionFactory redisConnectionFactory;

        /**
         * @return
         */
        @Bean
        public TokenStore redisTokenStore() {
            return new RedisTokenStore(redisConnectionFactory);
        }

    }

    /**
     * 使用jwt时的配置，默认生效
     *
     * @author hxh
     *
     */
    @Configuration
    @ConditionalOnProperty(prefix = "chero.com.chero.security.oauth2", name = "tokenStore", havingValue = "jwt", matchIfMissing = true)
    public static class JwtConfig {

        @Autowired
        private CustomSecurityProperties customSecurityProperties;
        @Autowired
        private UserService userService;

        /**
         * @return
         */
        @Bean
        public TokenStore jwtTokenStore() {
            JwtTokenStore jwtTokenStore = new JwtTokenStore(jwtAccessTokenConverter());
//            jwtTokenStore.setApprovalStore(new InMemoryApprovalStore());  觉得这个可能是用来存储token用的e
            return jwtTokenStore;
        }

        /**
         * @return
         */
        @Bean
        public JwtAccessTokenConverter jwtAccessTokenConverter(){
            JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
            converter.setSigningKey(customSecurityProperties.getOauth2().getJwtSigningKey());
            DefaultAccessTokenConverter accessTokenConverter = new DefaultAccessTokenConverter();
            DefaultUserAuthenticationConverter userTokenConverter = new DefaultUserAuthenticationConverter();
            userTokenConverter.setUserDetailsService(userService); // 解析UserDetail
            accessTokenConverter.setUserTokenConverter(userTokenConverter);
            converter.setAccessTokenConverter(accessTokenConverter);
            return converter;
        }

        /**
         * @return
         */
        @Bean
        @ConditionalOnBean(TokenEnhancer.class)
        public TokenEnhancer jwtTokenEnhancer(){
            return new TokenJwtEnhancer();
        }

    }

}
