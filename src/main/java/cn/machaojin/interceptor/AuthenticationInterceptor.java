package cn.machaojin.interceptor;

import cn.machaojin.annotation.JwtIgnore;
import cn.machaojin.tool.JwtTokenUtil;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author Ma Chaojin
 * @since 2024-04-30 15:58
 */
@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从http请求头中取出token
        final String token = request.getHeader(JwtTokenUtil.AUTH_HEADER_KEY);
        log.info(request.getRequestURI());
        log.info(token);
        //如果不是映射到方法，直接通过
        if(!(handler instanceof HandlerMethod handlerMethod)){
            return true;
        }
        //如果是方法探测，直接通过
        if (HttpMethod.OPTIONS.toString().equals(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        //如果方法有JwtIgnore注解，直接通过
        Method method=handlerMethod.getMethod();
        if (method.isAnnotationPresent(JwtIgnore.class)) {
            JwtIgnore jwtIgnore = method.getAnnotation(JwtIgnore.class);
            if(jwtIgnore.value()){
                return true;
            }
        }
        if (token == null || token.isEmpty()) {
            log.error("暂无权限，请求地址：{}，请求类型：{}，请求参数：{}",request.getRequestURI(),request.getMethod(), JSONObject.toJSON(request.getParameterMap()));
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }
        //验证，并获取token内部信息
        Claims userToken = JwtTokenUtil.verifyToken(token);
        //将token放入本地缓存
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //方法结束后，移除缓存的token

    }
}