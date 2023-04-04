package com.example.ruiji.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ruiji.dto.DishDto;
import com.example.ruiji.pojo.Dish;

public interface dishService extends IService<Dish> {

    public DishDto getWithFlavorId(Long id);

    public void updateWithFlavor(DishDto dto);
}
