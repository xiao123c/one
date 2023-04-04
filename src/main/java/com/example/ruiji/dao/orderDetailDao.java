package com.example.ruiji.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ruiji.pojo.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface orderDetailDao extends BaseMapper<OrderDetail> {
}
