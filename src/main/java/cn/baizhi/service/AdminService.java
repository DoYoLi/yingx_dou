package cn.baizhi.service;

import cn.baizhi.entity.Admin;

public interface AdminService {

    //根据姓名查寻
    public Admin queryByUserame(String username);

}
