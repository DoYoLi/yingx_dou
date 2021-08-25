package cn.baizhi.service;

import cn.baizhi.aliyun.ALiYun;
import cn.baizhi.annotation.DeleteCache;
import cn.baizhi.dao.VideoDao;
import cn.baizhi.entity.Video;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoDao videoDao;

    //查询
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> selectByPage(int start, int end) {
        //创建map集合
        HashMap<String, Object> map = new HashMap<>();
        //查看所有数据
        Integer all = videoDao.selectAll();
//        System.out.println(all / end);
//        System.out.println(all / end+1);
        //计算可以分为几页
        if (all % end == 0) {
            map.put("score", all / end);
        } else {
            map.put("score", all / end + 1);
        }
        //查询分页后的结果
        List<Video> video = videoDao.selectByPage((start - 1) * end, end);
        //存进map中
        map.put("video", video);
        return map;
    }

    //删除
    @Override
    @DeleteCache//自定义注解，用于切面
    public void delete(String id, String video) {
        ALiYun yun = new ALiYun();
        yun.delVideo(video);
        videoDao.delete(id);
    }

    //添加
    @DeleteCache
    @Override
    public void insert(Video video, MultipartFile videoPath) throws IOException {
        //上传时的文件名
        String s = videoPath.getOriginalFilename();
        //获取uuid
        String uuid = UUID.randomUUID().toString();
        //截取文件名后缀
        String s1 = FilenameUtils.getExtension(s);
        //文件后缀拼接上uuid
        String fileName = uuid + "." + s1;
        //拼接上地址
        String addFileName = "video/" + fileName;
        //调用文件上传云端方法
        ALiYun aLiYun = new ALiYun();
        String coverPath = aLiYun.upvideo(videoPath, addFileName);

        //将图片数据存入对象  需要拼上地址
        video.setVideoPath("http://2021t.oss-cn-beijing.aliyuncs.com/" + addFileName);
        //将uuid、状态、时间属性存入对象
        video.setId(uuid);
        video.setCreateDate(new Date());
        video.setCoverPath("http://2021t.oss-cn-beijing.aliyuncs.com/" + coverPath);
        //System.out.println(user);
//        System.out.println(video);
        //调用业务
        videoDao.insert(video);

    }
}
