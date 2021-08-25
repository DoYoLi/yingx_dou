package cn.baizhi.controller;

import cn.baizhi.entity.Category;
import cn.baizhi.entity.Video;
import cn.baizhi.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("video")
@CrossOrigin
public class VideoController {

    @Autowired
    private VideoService videoService;
//查询
    @RequestMapping("/select")
    public Map<String, Object> findByPage(int start){
        int end = 3;
//        System.out.println("1111111111");
        Map<String, Object> map = videoService.selectByPage(start, end);
        return map;
    }
    //删除
    @RequestMapping("/del")
    public void delete(String id,String video){
//        System.out.println(video);
        videoService.delete(id,video);
    }
    //添加
    //     formData.append("videoPath", this.$refs.video.files[0]);
//      formData.append("title", this.title);//标题
//      formData.append("brief", this.brief);//描述
//      formData.append("id", this.value2);//二级类别的id
    @RequestMapping("/add")
    public void add(MultipartFile videoPath,String title,String brief,String id) throws IOException {
        //创建标签实体类，存入标签id
        Category category = new Category();
        category.setId(id);
        //将数据存入vid中
        Video video = new Video(null, title, brief, null, null, null, category, null, null);
        //调用service
        videoService.insert(video, videoPath);
    }
}
