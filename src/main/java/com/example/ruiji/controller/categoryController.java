package com.example.ruiji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ruiji.comment.R;
import com.example.ruiji.pojo.Category;
import com.example.ruiji.service.impl.categoryImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
//分类
@Slf4j
@RestController
@RequestMapping("/category")
public class categoryController {

    @Autowired
    private categoryImpl categoryImpl;

    //添加分类
    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("category:{}",category);
        categoryImpl.save(category);
        return R.success("添加分类成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page,int pageSize){
        //分页构造器
        Page<Category> page1 = new Page<>(page,pageSize);
        //条件构造器
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        //添加排序条件，根据sort进行排序
        lqw.orderByAsc(Category::getSort);
        //分页查询
        categoryImpl.page(page1,lqw);
        return R.success(page1);
    }

    @DeleteMapping
    public  R<String> delete(Long id){
        log.info("删除分类，id为：{}",id);
        categoryImpl.removeById(id);
        return R.success("删除分类成功");
    }

    @PutMapping
    public R<String> update(@RequestBody Category category){
        categoryImpl.updateById(category);
        return R.success("修改分类成功");
    }

    /**
     * 分类回显
     * @param category
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        //条件构造器
        LambdaQueryWrapper<Category> lqw = new LambdaQueryWrapper<>();
        //添加条件
        lqw.eq(category.getType()!=null,Category::getType,category.getType());
        //排序条件
        lqw.orderByAsc(Category::getSort).orderByAsc(Category::getUpdateTime);

        List<Category> list = categoryImpl.list(lqw);
        return R.success(list);
    }
}
