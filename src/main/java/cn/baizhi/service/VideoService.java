package cn.baizhi.service;

import cn.baizhi.entity.Video;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

public interface VideoService {
    //分页查询
    Map<String,Object> selectByPage(@Param("start") int start, @Param("end") int end);
    //删除
    void delete(String id,String videoPath);
    //添加
    void insert(Video video, MultipartFile videoPath) throws IOException;

}
