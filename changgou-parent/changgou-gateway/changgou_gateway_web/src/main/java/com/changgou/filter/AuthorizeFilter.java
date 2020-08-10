package com.changgou.filter;

import com.changgou.util.JwtUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 全局过滤器
 */
@Component
public class AuthorizeFilter implements GlobalFilter, Ordered {
    private static final String AUTHORIZE_TOKEN="Authorization";
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //获取令牌信息
        // 1)头文件获取
        String token = request.getHeaders().getFirst(AUTHORIZE_TOKEN);
        // 2）参数中
        if(StringUtils.isEmpty(token)){
            token = request.getQueryParams().getFirst(AUTHORIZE_TOKEN);
        }
        // 3）cookie中
        if(StringUtils.isEmpty(token)){
            HttpCookie cookie = request.getCookies().getFirst(AUTHORIZE_TOKEN);
            if(cookie!=null){
                token = cookie.getValue();
            }
        }
        //如果没有令牌就拦截
        if(StringUtils.isEmpty(token)){
            //设置没有权限的状态码 401
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //响应空数据
            return response.setComplete();
        }
        //有令牌，校验令牌是否有效，
        try {
            JwtUtils.parseJWT(token);
        } catch (Exception e) {
            //无效拦截
            //设置没有权限的状态码 401
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            //响应空数据
            return response.setComplete();
        }
        //有令牌，令牌正确，放行
        return chain.filter(exchange);
    }

    /**
     * 排序，服务越小的月先执行
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
