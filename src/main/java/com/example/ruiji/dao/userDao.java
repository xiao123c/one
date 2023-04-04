package com.example.ruiji.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ruiji.pojo.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface userDao extends BaseMapper<User> {
}
