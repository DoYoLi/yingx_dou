package cn.baizhi.controller;

import cn.baizhi.entity.Admin;
import cn.baizhi.service.AdminService;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RequestMapping("admin")
@RestController
@CrossOrigin
public class AdminController {
    //注入service对象
    @Autowired
    private AdminService adminService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    //登录
    @RequestMapping("admin")
    public Map<String,Object> queryAll(@RequestBody Admin admin , HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();



        Admin admin1 = adminService.queryByUserame(admin.getUsername());
        map.put("msg", false);
        if(admin!=null){
            //账号正确
            if(admin1.getPassword().equals(admin.getPassword())){
                //登录成功
                map.put("msg", true);
                map.put("admin", admin1);

                ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
                String sessionId = request.getSession(true).getId();
                opsForValue.set(sessionId, JSONObject.toJSONString(admin1),30, TimeUnit.MINUTES);

                map.put("token", sessionId);
            }else{
                //密码错误
                map.put("flag", "密码错误");
            }
        }else{
            //账号错误
            map.put("flag", "账号错误");
        }
        return map;
    }
}
