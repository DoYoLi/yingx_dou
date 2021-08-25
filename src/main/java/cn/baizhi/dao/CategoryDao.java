package cn.baizhi.dao;

import cn.baizhi.entity.Category;

import java.util.List;


public interface CategoryDao {
    //查询一级标签
    List<Category> selectAll(int levels);

    //根据id查询二级标签
    List<Category> selectByParentId(String parentId);

    //添加二级标签
    void insert(Category category);

    //根据id删除二级标签
    void delete2(String id);

    //修改二级标签
    void update(Category category);



}
