package com.example.ruiji.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.ruiji.comment.R;
import com.example.ruiji.pojo.User;
import com.example.ruiji.service.impl.userImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/user")
public class userController {

    @Autowired
    private userImpl userService;

    @PostMapping("/login")
    public R<User> login(@RequestBody User user, HttpServletRequest request){
        LambdaQueryWrapper<User> lqw = new LambdaQueryWrapper<>();
        lqw.eq(User::getPhone,user.getPhone());
        User one = userService.getOne(lqw);
        if(one==null){
            one = new User();
            one.setPhone(user.getPhone());
            userService.save(one);
        }
        request.getSession().setAttribute("user",one.getId());
        return R.success(one);
    }
}
