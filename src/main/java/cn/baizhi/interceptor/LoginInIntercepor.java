package cn.baizhi.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
public class LoginInIntercepor implements HandlerInterceptor {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String token = request.getParameter("token");
        Boolean aBoolean = redisTemplate.hasKey(token);
        if(aBoolean){
            //重置token的存活时间
            redisTemplate.expire(token, 30, TimeUnit.MINUTES);
            return true;
        }else {
            throw new RuntimeException();
        }

    }
}
