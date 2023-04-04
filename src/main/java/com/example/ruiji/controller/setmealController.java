package com.example.ruiji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ruiji.comment.R;
import com.example.ruiji.dto.SetmealDto;
import com.example.ruiji.pojo.Category;
import com.example.ruiji.pojo.Dish;
import com.example.ruiji.pojo.Setmeal;
import com.example.ruiji.service.impl.categoryImpl;
import com.example.ruiji.service.impl.setmealDishImpl;
import com.example.ruiji.service.impl.setmealImpl;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
//套餐
@RestController
@RequestMapping("/setmeal")
@Slf4j
public class setmealController {
    @Autowired
    private setmealImpl setmealService;
    @Autowired
    private setmealDishImpl setmealDishService;

    @Autowired
    private categoryImpl category;

    /**
     * 添加
     * @param setmealDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody SetmealDto setmealDto){
        log.info(String.valueOf(setmealDto));
        setmealService.saveWithSetmeal(setmealDto);
        return R.success("添加套餐成功");
    }

    /**
     * 分页
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        Page<Setmeal> pageInfo = new Page<>(page,pageSize);
        Page<SetmealDto> setmealDtoPage = new Page<>();

        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<Setmeal>();
        lqw.like(name!=null,Setmeal::getName,name);
        lqw.orderByAsc(Setmeal::getUpdateTime);
        setmealService.page(pageInfo, lqw);

        BeanUtils.copyProperties(pageInfo,setmealDtoPage,"records");
        List<Setmeal> records = pageInfo.getRecords();

        List<SetmealDto> collect = records.stream().map((item) -> {
            SetmealDto setmealDto = new SetmealDto();
            Long categoryId = item.getCategoryId();
            Category byId = category.getById(categoryId);
            BeanUtils.copyProperties(item, setmealDto);
            setmealDto.setCategoryName(byId.getName());
            return setmealDto;
        }).collect(Collectors.toList());

        setmealDtoPage.setRecords(collect);

        return R.success(setmealDtoPage);
    }

    @GetMapping("/{id}")
    public R<SetmealDto> getById(@PathVariable Long id){
        SetmealDto byId = setmealService.getWithSetmeal(id);
        return R.success(byId);
    }

    @PutMapping
    public R<String> update(@RequestBody SetmealDto setmealDto){
        setmealService.updateWithSetmeal(setmealDto);
        return R.success("更新成功");
    }

    @PostMapping("/status/{n}")
    public R<String> status(@PathVariable int n,Long @NotNull [] ids){
        for(long id:ids) {
            Setmeal byId = setmealService.getById(id);
            if(n==1){
                byId.setStatus(1);
            }
            if(n==0){
                byId.setStatus(0);
            }
            setmealService.updateById(byId);
        }
        return R.success("成功");
    }

    @DeleteMapping
    public R<String> delete(@RequestParam  List<Long> ids){
        setmealService.deleteWithSetmeal(ids);
        return R.success("删除成功");
    }

    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> lqw = new LambdaQueryWrapper<>();
        lqw.eq(setmeal.getCategoryId()!=null,Setmeal::getCategoryId,setmeal.getCategoryId());
        lqw.eq(Setmeal::getStatus,setmeal.getStatus());
        List<Setmeal> list = setmealService.list(lqw);
        return R.success(list);
    }
}
