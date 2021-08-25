package cn.baizhi.dao;

import cn.baizhi.entity.Admin;

public interface AdminDao {

    //查姓名
    public Admin selectByUsername(String username);

}
