package com.example.ruiji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ruiji.pojo.Employee;
import com.example.ruiji.comment.R;
import com.example.ruiji.service.impl.employeeImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
//员工表
@Slf4j
@RestController
@RequestMapping("/employee")
public class employeeController {

    @Autowired
    private employeeImpl employeeImpl;
//登录
    @PostMapping("/login")
    public R<Employee> login(@RequestBody Employee employee, HttpServletRequest request){
        //1.将页面传的密码进行md5加密
        String password = employee.getPassword();
        password=DigestUtils.md5DigestAsHex(password.getBytes());

        //2.根据页面提交的用户名进行查询
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Employee::getUsername,employee.getUsername());
        Employee one = employeeImpl.getOne(lqw);

        //3.如果没有查询到就返回登录失败结果
        if(one==null){
            return R.error("登陆失败");
        }

        //4.密码比对，如果不一致返回失败结果
        if(!one.getPassword().equals(password)){
            return R.error("登陆失败");
        }

        //5.查看员工状态，如果禁用，返回禁用结果
        if(one.getStatus()==0){
            return R.error("账号已禁用");
        }
        request.getSession().setAttribute("employee",one.getId());
        //登陆成功
        return R.success(one);
    }
//退出
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }

    //添加员工
    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody  Employee employee){
        log.info("新增员工信息：{}",employee.toString());
        //密文加密初始化密码
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setCreateUser((Long) request.getSession().getAttribute("employee"));
//        employee.setUpdateUser((Long) request.getSession().getAttribute("employee"));

        boolean save = employeeImpl.save(employee);
        return R.success("添加成功");
    }

    @GetMapping("/page")
    public R<Page> page(int page,int pageSize,String name){
        log.info("page={},pageSize={},name={}",page,pageSize,name);

        //构造分页构造器
        Page page1 = new Page(page,pageSize);
        //构造条件构造器
        LambdaQueryWrapper<Employee> lqw = new LambdaQueryWrapper();
        lqw.like(StringUtils.isNotEmpty(name),Employee::getName,name);
        //查询
        employeeImpl.page(page1,lqw);
        return R.success(page1);
    }

    @PutMapping
    public R<String> update(HttpServletRequest request,@RequestBody Employee employee){
        log.info(employee.toString());

        //更新时间和更新人
        Long empId = (Long) request.getSession().getAttribute("employee");

//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setUpdateUser(empId);

        employeeImpl.updateById(employee);
        return R.success("员工信息修改成功");
    }

    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable Long id){
        Employee employee = employeeImpl.getById(id);
        return R.success(employee);
    }
}
