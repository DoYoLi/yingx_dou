package cn.baizhi.dao;

import cn.baizhi.entity.User;
import cn.baizhi.entity.vo.Count;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    //范围查
    List<User> selectByPaging(@Param("start") int start, @Param("end") int end );
    //查询数据条数
    Integer selectAllScore();
    //修改状态
    void upStatus(@Param("id") String id,@Param("status") int status);
    //添加
    void insert(User user);
    //删除
    void delete(String id);
    //查所有
    List<User> selectAll();
    //查询月份注册人数
    List<Count> selectCount(String sex);
}
