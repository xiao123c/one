package com.example.ruiji.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ruiji.comment.R;
import com.example.ruiji.dto.OrdersDto;
import com.example.ruiji.pojo.Orders;

import java.util.List;

public interface ordersService extends IService<Orders>{

    public R<List<OrdersDto>> pageWithOrder(int page,int pageSize);
}
