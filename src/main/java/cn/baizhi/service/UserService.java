package cn.baizhi.service;

import cn.baizhi.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UserService {
    //分页业务
    Map<String,Object> selectByPaging(int start,int end);
    //修改状态
    void upStatus(String id,int status);
    //添加
    void insert(MultipartFile photo, User user) throws IOException;
    //删除
    void delete(String id,String headimg);
    //下载/查询  所有的用户信息
    List<User> selectAll();
    //查询月份注册人数
    Map<String, Object> selectCount();
}
