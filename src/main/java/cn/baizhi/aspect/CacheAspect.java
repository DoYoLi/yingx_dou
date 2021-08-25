package cn.baizhi.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;


@Component//创建对象
@Aspect//这是一个切面类
public class CacheAspect {
    //创建redis对象
    @Autowired
    private RedisTemplate redisTemplate;

    //进入环绕通知的方法,所有的查询都会走这个环绕通知
    @Around("execution(* cn.baizhi.service.*Impl.select*(..))")
    public Object addCache(ProceedingJoinPoint joinPoint) {
        //取消大键的序列化
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        //取消小键的序列化
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        //创建StringBuilder存键值
        StringBuilder builder = new StringBuilder();
        //获取方法的全路径、方法名、实参
        String className = joinPoint.getTarget().getClass().getName();//全路径
        String methodName = joinPoint.getSignature().getName();//方法名
        builder.append(className).append(methodName);//存入builder中
        Object[] args = joinPoint.getArgs();//实参
        for (Object arg : args) {//遍历实参集合
            builder.append(arg);//将实参拼到builder中
        }
        HashOperations ops = redisTemplate.opsForHash();//创建redis对象
        Object obj = null;
        if (ops.hasKey(className, builder.toString())) {//判断redis中有没有这个键
            //如果有这个键
            //获取这个键值
            obj = ops.get(className, builder.toString());
        } else {
            //没有这个键
            //放行请求
            try {
                obj = joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            ops.put(className, builder.toString(), obj);
        }
        return obj;
    }


    //删除redis中的缓存
    @After("@annotation(cn.baizhi.annotation.DeleteCache)")
    public void delCache(JoinPoint joinPoint) {
        String name = joinPoint.getTarget().getClass().getName();
        redisTemplate.delete(name);
    }
}
