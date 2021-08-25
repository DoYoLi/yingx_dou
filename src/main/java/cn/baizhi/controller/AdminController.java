package cn.baizhi.controller;

import cn.baizhi.entity.Admin;
import cn.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("admin")
@RestController
@CrossOrigin
public class AdminController {
    //注入service对象
    @Autowired
    private AdminService adminService;
    //登录
    @RequestMapping("admin")
    public Map<String,Object> queryAll(@RequestBody Admin admin){
        Map<String,Object> map = new HashMap<>();
        Admin admin1 = adminService.queryByUserame(admin.getUsername());
        map.put("msg", false);
        if(admin!=null){
            //账号正确
            if(admin1.getPassword().equals(admin.getPassword())){
                //登录成功
                map.put("msg", true);
                map.put("admin", admin1);
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
