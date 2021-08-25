package cn.baizhi.service;

import cn.baizhi.annotation.DeleteCache;
import cn.baizhi.dao.CategoryDao;
import cn.baizhi.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
@Transactional
public class CategoryServiceImpl implements CategoryService {
    //注入dao
    @Autowired
    private CategoryDao categoryDao;

    //查询一级标签
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> selectAll(int levels) {
        List<Category> categories = categoryDao.selectAll(levels);
        return categories;
    }

    //查询二级标签
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> selectParentId(String parentId) {
        List<Category> categories = categoryDao.selectByParentId(parentId);
        return categories;
    }

    //添加标签
    @Override
    @DeleteCache//自定义注解，用于切面
    public void add(Category category) {
        category.setId(UUID.randomUUID().toString());
        categoryDao.insert(category);
    }

    //删除二级标签
    @Override
    @DeleteCache
    public void delete(String id) {
        categoryDao.delete2(id);
    }

    //删除一级标签
    @Override
    @DeleteCache
    public Map<String, Object> del1(String id) {
        HashMap<String, Object> map = new HashMap<>();
        List<Category> categories = categoryDao.selectByParentId(id);
//        System.out.println(categories);
        //判断一级标签下是否有二级标签
        if(categories.isEmpty()){
            categoryDao.delete2(id);
            map.put("msg", "删除成功");
        }else{
            map.put("msg", "此标签有子项，不能删除！");
        }
        return map;
    }
//修改二级标签
    @Override
    @DeleteCache
    public void update(Category category) {
        categoryDao.update(category);
    }

}
