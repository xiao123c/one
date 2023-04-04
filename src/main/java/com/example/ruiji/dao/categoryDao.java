package com.example.ruiji.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ruiji.pojo.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface categoryDao extends BaseMapper<Category> {
}
