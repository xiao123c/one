package com.example.ruiji.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ruiji.dao.employeeDao;
import com.example.ruiji.pojo.Employee;
import com.example.ruiji.service.employeeService;
import org.springframework.stereotype.Service;

@Service
public class employeeImpl extends ServiceImpl<employeeDao, Employee>implements employeeService {
}
