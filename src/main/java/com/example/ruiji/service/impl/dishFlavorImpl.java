package com.example.ruiji.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ruiji.dao.dishFlavorDao;
import com.example.ruiji.pojo.DishFlavor;
import com.example.ruiji.service.dishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class dishFlavorImpl extends ServiceImpl<dishFlavorDao, DishFlavor> implements dishFlavorService {
}
