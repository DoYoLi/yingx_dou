package cn.baizhi.service;

import cn.baizhi.entity.Category;

import java.util.List;
import java.util.Map;

public interface CategoryService {
    //查全部
    List<Category> selectAll(int levels);
    //根据上级id查询
    List<Category> selectParentId(String parentId);
    //添加标签
    void add(Category category);
    //根据id删除
    void delete(String id);
    //删除一级标签
    Map<String,Object> del1(String id);
    //修改二级标签
    void update(Category category);
}
