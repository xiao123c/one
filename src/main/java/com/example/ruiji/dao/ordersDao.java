package com.example.ruiji.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ruiji.pojo.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ordersDao extends BaseMapper<Orders> {
}
