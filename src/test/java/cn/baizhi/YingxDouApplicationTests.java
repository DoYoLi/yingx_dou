package cn.baizhi;

import cn.baizhi.controller.AdminController;
import cn.baizhi.controller.VideoController;
import cn.baizhi.dao.AdminDao;
import cn.baizhi.dao.UserDao;
import cn.baizhi.entity.User;
import cn.baizhi.service.CategoryService;
import cn.baizhi.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class YingxDouApplicationTests {

    @Autowired
    private AdminDao adminDao;
    @Autowired
    private AdminController adminController;
    @Autowired
    private UserDao userDao;

    @Autowired
    private UserService userService;
    @Autowired
    private VideoController videoController;
    @Autowired
    private CategoryService categoryService;

    @Test
    void contextLoads() {



//        Map<String, Object> map = userService.selectCount();
//        System.out.println(map);
//        System.out.println(""111111);
//        List<User> users = userDao.selectAll();
//        for (User user : users) {
////            System.out.println(user.getHeadimg());
//            String headimg = user.getHeadimg();
//            String[] a = headimg.split("/");
//            System.out.println(a[a.length-1]);
//        }

//        System.out.println(users);
//        System.out.println(users.size());
//        Map<String, Object> byPage = videoController.findByPage(0);
//        Object video = byPage.get("video");
//        System.out.println(video);
//        System.out.println(byPage);
    }

    @Test
    public void test2() {
//        List<Category> categories = categoryService.selectAll(1);
//        System.out.println(categories);
//        class Cat {
//            private String name;
//            public String getName() {
//                return name;
//            }
//            public void setName(String name) {
//                this.name = name;
//            }
//            public Cat(String name) {
//                this.name = name;
//            }
//        }
//        Cat c1 = new Cat("王磊");
//        Cat c2 = new Cat("王磊");
//        System.out.println(c1.equals(c2));

    }


    @Test
    void test1() {
//        userDao.upStatus("1", 0);

//
//        Integer integer = userDao.selectAllScore();
//        System.out.println(integer);


//        Admin admin = new Admin("1", "admin", "admin", 1);
//        Map<String, Object> map = adminController.queryAll(admin);
//        System.out.println(map);
//        userService.upStatus("1", 0);
        // userController.upStatus("1", 1);
    }
}
