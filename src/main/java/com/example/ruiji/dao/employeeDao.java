package com.example.ruiji.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.ruiji.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface employeeDao extends BaseMapper<Employee> {
}
