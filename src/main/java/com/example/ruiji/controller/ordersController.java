package com.example.ruiji.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ruiji.comment.R;
import com.example.ruiji.dto.OrdersDto;
import com.example.ruiji.pojo.AddressBook;
import com.example.ruiji.pojo.Orders;
import com.example.ruiji.pojo.User;
import com.example.ruiji.service.AddressBookService;
import com.example.ruiji.service.impl.ordersImpl;
import com.example.ruiji.service.impl.userImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
public class ordersController {
    @Autowired
    private ordersImpl ordersService;

    @Autowired
    private userImpl userService;

    @Autowired
    private AddressBookService addressBookService;


    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        ordersService.submit(orders);
        return R.success("");
    }

    @GetMapping("/page")
    public R<Page<OrdersDto>> page(int page, int pageSize){
        Page<Orders> pageInfo = new Page<>(page,pageSize);
        Page<OrdersDto> dtoPage = new Page<>();
        Page<Orders> page1 = ordersService.page(pageInfo);
        BeanUtils.copyProperties(page1,dtoPage,"records");
        List<Orders> records = page1.getRecords();

        List<OrdersDto> list =records.stream().map((item)->{
            OrdersDto dto = new OrdersDto();
            BeanUtils.copyProperties(item,dto);

            LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
            lqw.eq(User::getId,item.getUserId());
            User user = userService.getOne(lqw);
            dto.setUserName(user.getName());

            AddressBook addressBook = addressBookService.getById(item.getAddressBookId());
            dto.setPhone(addressBook.getPhone());
            dto.setAddress(addressBook.getDetail());
            return dto;
        }).collect(Collectors.toList());
        dtoPage.setRecords(list);
        return R.success(dtoPage);
    }

}
