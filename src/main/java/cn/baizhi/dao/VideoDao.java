package cn.baizhi.dao;

import cn.baizhi.entity.Video;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoDao {

    //分页查询
    List<Video> selectByPage(@Param("start") int start, @Param("end") int end);

    //查全部
    Integer selectAll();

    //添加
    void insert(Video video);

    //删除
    void delete(String id);

}
