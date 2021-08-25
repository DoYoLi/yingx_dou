package cn.baizhi.controller;

import cn.baizhi.downloadinformation.DownloadInformation;
import cn.baizhi.entity.User;
import cn.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    //注入service
    @Autowired
    private UserService userService;

    //分页查询
    @RequestMapping("/queryByPage")
    public Map<String, Object> queryByPage(int page) {
        //每页的数据条数
        int size = 3;
        Map<String, Object> map = userService.selectByPaging(page, size);
        return map;
    }

    @RequestMapping("/upStatus")
    //修改状态
    public void upStatus(String id, int status) {
        userService.upStatus(id, status);
    }
    //添加
    @RequestMapping("/add")
    public void add(MultipartFile photo, String userName, String brief, String phone,String sex) throws IOException {
        //调用service
        User user = new User(null, phone, userName,sex, null, brief, null, null, null);
        userService.insert(photo, user);
    }

    //删除
    @RequestMapping("/del")
    public void del(String id, String headimg) {
        userService.delete(id, headimg);
    }

    //下载所有的用户信息
    @RequestMapping("/down")
    public Map<String, Object> down() {
        HashMap<String, Object> map = new HashMap<>();
        //查询所有信息
        List<User> list = userService.selectAll();
        //创建下载信息方法
        DownloadInformation download = new DownloadInformation();
        //将查询的信息传入下载信息方法
        try{
            download.down(list);
            map.put("oks", "数据导出成功");
        }catch(Exception e){
            map.put("oks", "数据导出失败");
            e.printStackTrace();
        }
        return map;

    }
    //查询所有的用户信息
    @RequestMapping("/look")
    public List<User> look() {
        //查询所有信息
        List<User> list = userService.selectAll();
        return list;
    }
    //查询月增加人数
    @RequestMapping("/lookCount")
    public Map<String, Object> lookCount(){


        Map<String, Object> map = userService.selectCount();

        return map;
    }

//    前端动态显示（goeasy）
//    public void test(){
//        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-a16bbe0e3f254f3c931176b419b53515");
//        goEasy.publish("2021class", JSONObject.toJSONString(map));
//    }
}
