package com.chero.bserver.apigateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.util.Base64;

/**
 * Created by hp on 2018/4/9.
 */
@Component
public class AuthorizedRequestFilter extends ZuulFilter {       // 进行授权访问处理
    @Override
    public String filterType() {    // 在进行Zuul过滤的时候可以设置其过滤执行的位置，那么此时有如下几种类型
        // 1、pre：在请求发出之前进行过滤，如果要进行访问，肯定在请求前设置头信息；
        // 2、route：在进行路由请求的时候被调用；
        // 3、post：在路由之后发送请求信息的时候被调用；
        // 4、error：出现错误之后进行调用；
        return "pre";
    }

    @Override
    public int filterOrder() {  // 设置优先级，数字越小优先级越高
        return 0;
    }

    @Override
    public boolean shouldFilter() { // 该Filter是否要执行，默认flase
        return true;
    }

    @Override
    public Object run() {   // 表示具体的过滤执行操作
        RequestContext requestContext = RequestContext.getCurrentContext(); // 获取当前请求的上下文
        String auth = "hxh:hxh";    // 认证的原始信息
        byte[] encodeAuth = Base64.getEncoder().encode(auth.getBytes(Charset.forName("US-ASCII"))); // 进行一个加密的处理

        // 在进行授权的头信息内容配置的时候加密的信息一定要与"Basic"之间有一个空格
        String authHeader = "Basic " + new String(encodeAuth);
        requestContext.addZuulRequestHeader("Authorization", authHeader);
        return null;
    }
}
