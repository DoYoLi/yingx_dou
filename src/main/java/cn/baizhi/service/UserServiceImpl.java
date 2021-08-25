package cn.baizhi.service;

import cn.baizhi.aliyun.ALiYun;
import cn.baizhi.annotation.DeleteCache;
import cn.baizhi.dao.UserDao;
import cn.baizhi.entity.User;
import cn.baizhi.entity.vo.Count;
import com.alibaba.fastjson.JSONObject;
import io.goeasy.GoEasy;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    //注入
    @Autowired
    private UserDao userDao;

    //分页查询业务
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> selectByPaging(int page, int size) {
        //创建map集合，存储各种数据
        HashMap<String, Object> map = new HashMap<>();
        //分页查询
        List<User> users = userDao.selectByPaging((page - 1) * size, size);
        //将分页查询的数据存到map集合中
        map.put("user", users);
        //查询总数据条数
        Integer score = userDao.selectAllScore();
        //计算可以分为几页
        if (score % size == 0) {
            map.put("score", score / size);
        } else {
            map.put("score", score / size + 1);
        }
        //返回map集合
        return map;
    }

    //修改状态
    @Override
    @DeleteCache//自定义注解，用于切面
    public void upStatus(String id, int status) {
        userDao.upStatus(id, status);
    }

    //添加至数据库及云空间
    @Override
    @DeleteCache
    public void insert(MultipartFile photo, User user) throws IOException {
        //上传时的文件名
        String s = photo.getOriginalFilename();
        //获取uuid
        String uuid = UUID.randomUUID().toString();
        //截取文件名后缀
        String s1 = FilenameUtils.getExtension(s);
        //文件后缀拼接上uuid
        String fileName = uuid + "." + s1;
        //拼接上地址
        String addFileName = "dou/" + fileName;
        //调用文件上传云端方法
        ALiYun aLiYun = new ALiYun();
        aLiYun.up(photo, addFileName);

        //将图片数据存入对象  需要拼上地址
        user.setHeadimg("http://2021t.oss-cn-beijing.aliyuncs.com/dou/" + fileName);
        //将uuid、状态、时间属性存入对象
        user.setId(uuid);
        user.setStatus(1);
        user.setCreatedate(new Date());
        //调用业务
        userDao.insert(user);
        //使用goeasy动态显示
        Map<String, Object> map = selectCount();
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-a16bbe0e3f254f3c931176b419b53515");
        goEasy.publish("2021class", JSONObject.toJSONString(map));
    }

    //删除数据库数据及删除云空间的图片
    @Override
    @DeleteCache
    public void delete(String id, String headimg) {
        ALiYun aLiYun = new ALiYun();
        aLiYun.del(headimg);
        //调用业务
        userDao.delete(id);
        //使用goeasy
        Map<String, Object> map = selectCount();
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io", "BC-a16bbe0e3f254f3c931176b419b53515");
        goEasy.publish("2021class", JSONObject.toJSONString(map));
    }

    //查询所有的用户信息
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<User> selectAll() {
        List<User> list = userDao.selectAll();
        for (User user : list) {
            String headimg = user.getHeadimg();
            String[] a = headimg.split("/");
            new ALiYun().downJPG(a[a.length - 1]);
            user.setHeadimg("E:\\yingxue\\" + a[a.length - 1]);
            //遍历集合将获取的地址存到user中
        }
        for (User user : list) {
            System.out.println(user);
        }
        return list;
    }
//每月注册人数查询
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> selectCount() {
        List<String> data = Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月");
        List<Integer> man = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        List<Integer> woMan = Arrays.asList(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
        HashMap<String, Object> map = new HashMap<>();
        List<Count> listMan = userDao.selectCount("男");
        for (Count count1 : listMan) {
            Integer count = count1.getCount();
            Integer month = count1.getMonth();
            man.set(month - 1, count);
        }
        List<Count> listWoMan = userDao.selectCount("女");
        for (Count count1 : listWoMan) {
            Integer count = count1.getCount();
            Integer month = count1.getMonth();
            woMan.set(month - 1, count);
        }
        map.put("data", data);
        map.put("man", man);
        map.put("woman", woMan);
        return map;
    }
}
