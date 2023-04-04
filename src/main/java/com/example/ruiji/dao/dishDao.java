package com.example.ruiji.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ruiji.pojo.Dish;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface dishDao extends BaseMapper<Dish> {

}
