package cn.baizhi.aliyun;

import cn.baizhi.config.AliYun;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import com.aliyun.oss.model.GetObjectRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

public class ALiYun {
//上传
    public void up(MultipartFile photo,String fileName) throws IOException {
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = AliYun.ENDPOINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = AliYun.ACCESS_KEY_ID;
        String accessKeySecret = AliYun.ACCESS_KEY_SECRET;
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传Byte数组。获取的是源文件的字节数组
        byte[] content = photo.getBytes();
        //分别传入               云端名称       文件夹及文件名的拼接             源文件的字节数组
        ossClient.putObject("2021t", fileName, new ByteArrayInputStream(content));
        // 关闭OSSClient。
        ossClient.shutdown();
    }
//删除
    public void del(String headimg){
        //对headimg进行分割
        String[] a = headimg.split("/");
//        System.out.println(a);
        //获取分割后的文件后缀名和文件夹名
        String name1 = a[3];
        String name2 = a[4];
        //将后缀名和二级文件名进行拼接
        String fileName = name1 + "/" + name2;
//        System.out.println(fileName);
        //填写Bucket所在地域对应的Endpoint
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        //阿里云账号AccessKey
//        LTAI5tL3CqcPvv1jYJqr2B5e
//        T8mjWburSqa0iA9nBJUkLvjW1FtSoB
        String accessKeyId = "LTAI5tL3CqcPvv1jYJqr2B5e";
        String accessKeySecret = "T8mjWburSqa0iA9nBJUkLvjW1FtSoB";
        //创建OSSClient实例。传入                   阿里云网络地址    keyid     keyservic
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 删除文件
        ossClient.deleteObject("2021t", fileName);
        // 关闭OSSClient。
        ossClient.shutdown();
    }
    //视频截帧
    public String upvideo(MultipartFile video,String fileName) throws IOException {
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = AliYun.ENDPOINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = AliYun.ACCESS_KEY_ID;
        String accessKeySecret = AliYun.ACCESS_KEY_SECRET;
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        //http://2021t.oss-cn-beijing.aliyuncs.com/video/b19e02b5-c9fb-48e5-9f8a-c8a108a5c8e6.jpg
        // 上传Byte数组。获取的是源文件的字节数组
        byte[] content = video.getBytes();
        //分别传入               云端名称       文件夹及文件名的拼接             源文件的字节数组
        ossClient.putObject("2021t", fileName, new ByteArrayInputStream(content));

// 创建OSSClient实例。
//        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
// 设置视频截帧操作。
        String style = "video/snapshot,t_50000,f_jpg,w_100,h_100";
// 指定过期时间为10分钟。
        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 10 );
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest("2021t", fileName, HttpMethod.GET);
        req.setExpiration(expiration);
        req.setProcess(style);
        URL signedUrl = ossClient.generatePresignedUrl(req);
        System.out.println(signedUrl);

        String[] split = fileName.split("\\.");
        String s =split[0]+ ".jpg";

//        System.out.println(fileName);
//        System.out.println(s);
        //因为上面的方法智能保存10分钟的封面，所以使用下面的两行代码延长存在时间
        InputStream inputStream = new URL(signedUrl.toString()).openStream();
        ossClient.putObject("2021t", s, inputStream);
// 关闭OSSClient。
        ossClient.shutdown();
        return s;
    }
    //视频删除
    public void delVideo(String video){
        //对headimg进行分割
        String[] a = video.split("/");
//        System.out.println(video);
//        System.out.println(a);
        //获取分割后的文件名和文件夹名
        String name1 = a[3];
        String name2 = a[4];
        //将文件名和二级文件名进行拼接
        String fileName = name1 + "/" + name2;
        String[] s = name2.split("\\.");
        String fileName1 = name1 + "/"+s[s.length-2]+".jpg";
//        System.out.println(fileName);
//        System.out.println(fileName1);
//        System.out.println(fileName);
        //填写Bucket所在地域对应的Endpoint
        String endpoint = "http://oss-cn-beijing.aliyuncs.com";
        //阿里云账号AccessKey
//        LTAI5tL3CqcPvv1jYJqr2B5e
//        T8mjWburSqa0iA9nBJUkLvjW1FtSoB
        String accessKeyId = "LTAI5tL3CqcPvv1jYJqr2B5e";
        String accessKeySecret = "T8mjWburSqa0iA9nBJUkLvjW1FtSoB";
        //创建OSSClient实例。传入                   阿里云网络地址    keyid     keyservic
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 删除文件
        ossClient.deleteObject("2021t", fileName);
        ossClient.deleteObject("2021t", fileName1);
        // 关闭OSSClient。
        ossClient.shutdown();
    }
    //用户头像下载
    public void downJPG(String headimg){
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = AliYun.ENDPOINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = AliYun.ACCESS_KEY_ID;
        String accessKeySecret = AliYun.ACCESS_KEY_SECRET;
        String bucketName = "2021t";  //存储空间名

        String objectName = headimg;  //文件名
        String localFile="E:\\yingxue\\"+objectName;  //下载本地地址  地址加保存名字
//        System.out.println("文件名"+headimg);

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucketName, "dou/" + objectName), new File(localFile));

        // 关闭OSSClient。
        ossClient.shutdown();

    }
}
