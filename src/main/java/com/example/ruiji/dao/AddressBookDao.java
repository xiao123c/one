package com.example.ruiji.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import com.example.ruiji.pojo.AddressBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface AddressBookDao extends BaseMapper<AddressBook> {

}
