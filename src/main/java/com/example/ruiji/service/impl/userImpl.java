package com.example.ruiji.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ruiji.dao.userDao;
import com.example.ruiji.pojo.User;
import com.example.ruiji.service.userService;
import org.springframework.stereotype.Service;

@Service
public class userImpl extends ServiceImpl<userDao, User> implements userService {
}
