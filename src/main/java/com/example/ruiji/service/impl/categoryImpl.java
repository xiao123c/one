package com.example.ruiji.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ruiji.dao.categoryDao;
import com.example.ruiji.pojo.Category;
import com.example.ruiji.service.categoryService;
import org.springframework.stereotype.Service;

@Service
public class categoryImpl extends ServiceImpl<categoryDao,Category> implements categoryService {
}
