package com.example.ruiji.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ruiji.dto.SetmealDto;
import com.example.ruiji.pojo.Setmeal;

import java.util.List;

public interface setmealService extends IService<Setmeal> {


    public void saveWithSetmeal(SetmealDto setmealDto);

    public SetmealDto getWithSetmeal(long id);

    void updateWithSetmeal(SetmealDto setmealDto);
}
