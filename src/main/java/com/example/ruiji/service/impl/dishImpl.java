package com.example.ruiji.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ruiji.comment.R;
import com.example.ruiji.dao.dishDao;
import com.example.ruiji.dto.DishDto;
import com.example.ruiji.pojo.Dish;
import com.example.ruiji.pojo.DishFlavor;
import com.example.ruiji.service.dishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class dishImpl extends ServiceImpl<dishDao, Dish>implements dishService {

    @Autowired
    private dishFlavorImpl dishFlavor;

    @Transactional//开启事务
    public void saveWithFlavor(DishDto dto){
        this.save(dto);
        Long dishId = dto.getCategoryId();
        List<DishFlavor> flavors = dto.getFlavors();
       flavors= flavors.stream().map((item)->{
            item.setDishId(dishId);
            return item;
        }).collect(Collectors.toList());
        dishFlavor.saveBatch(flavors);
    }

    public DishDto getWithFlavorId(Long id){
        Dish dish = this.getById(id);
        DishDto dto = new DishDto();
        BeanUtils.copyProperties(dish,dto);
        LambdaQueryWrapper<DishFlavor> lq = new LambdaQueryWrapper<>();
        lq.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> list = dishFlavor.list(lq);
        dto.setFlavors(list);
        return  dto;
    }

    @Override
    @Transactional
    public void updateWithFlavor(DishDto dto) {
        //更新菜品信息
        this.updateById(dto);

        //删除菜品口味
        LambdaQueryWrapper<DishFlavor> lq = new LambdaQueryWrapper<>();
        lq.eq(DishFlavor::getDishId,dto.getId());
        dishFlavor.remove(lq);


        List<DishFlavor> flavors = dto.getFlavors();
        //给每一个DishFlavor 附上id 遍历 赋值 收集
        flavors=flavors.stream().map((item)->{
            item.setDishId(dto.getId());
            return item;
        }).collect(Collectors.toList());

        //重新添加
        dishFlavor.saveBatch(flavors);
    }

    @Transactional
    public void deleteWithFlavor(List<Long> ids){
        LambdaQueryWrapper<Dish> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Dish::getId,ids);
        lambdaQueryWrapper.eq(Dish::getStatus,1);
        int count = this.count(lambdaQueryWrapper);
        if(count>0){
            throw new RuntimeException("套餐正在售卖，不能删除");
        }
        this.removeByIds(ids);

        LambdaQueryWrapper<DishFlavor> lqw = new LambdaQueryWrapper<>();
        lqw.in(DishFlavor::getDishId,ids);
        dishFlavor.remove(lqw);
    }
}
