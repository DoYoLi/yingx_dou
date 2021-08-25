package cn.baizhi.controller;


import cn.baizhi.entity.Category;
import cn.baizhi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RequestMapping("/cate")
@RestController
@CrossOrigin
public class CategoryController {
    //注入service
    @Autowired
    private CategoryService categoryService;

    @RequestMapping("/queryByLevels")
    public List<Category> queryByLevels(int levels){
        List<Category> categories = categoryService.selectAll(levels);
        return categories;
    }
    @RequestMapping("/queryParentId")
    public List<Category> queryParentId(String parentId){
        List<Category> categories = categoryService.selectParentId(parentId);
        return categories;
    }
    @RequestMapping("/add")
    public void add(@RequestBody Category category){
        categoryService.add(category);
    }
    @RequestMapping("/del")
    public void delete(String id){
        categoryService.delete(id);
    }
    //删除一级标签
    @RequestMapping("/del1")
    public Map<String,Object> del1(String id){
        Map<String, Object> map = categoryService.del1(id);
        return map;
    }
//修改二级标签
    @RequestMapping("/update")
    public void update(@RequestBody Category category){
        categoryService.update(category);
    }

}
