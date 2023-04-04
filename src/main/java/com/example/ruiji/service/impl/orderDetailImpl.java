package com.example.ruiji.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ruiji.dao.orderDetailDao;
import com.example.ruiji.pojo.OrderDetail;
import com.example.ruiji.service.orderDetailService;
import org.springframework.stereotype.Service;

@Service
public class orderDetailImpl extends ServiceImpl<orderDetailDao, OrderDetail> implements orderDetailService {
}
