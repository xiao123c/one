package com.example.ruiji.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ruiji.comment.R;
import com.example.ruiji.dto.DishDto;
import com.example.ruiji.pojo.Category;
import com.example.ruiji.pojo.Dish;
import com.example.ruiji.pojo.DishFlavor;
import com.example.ruiji.service.impl.categoryImpl;
import com.example.ruiji.service.impl.dishFlavorImpl;
import com.example.ruiji.service.impl.dishImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;
//菜品
@RestController
@RequestMapping("/dish")
@Slf4j
public class dishController {
    @Autowired
    private dishImpl dish;
    @Autowired
    private dishFlavorImpl dishFlavor;

    @Autowired
    private categoryImpl category;

    /**
     * 菜品添加
     * @param dto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody DishDto dto){
        log.info(String.valueOf(dto));
        dish.saveWithFlavor(dto);
        return R.success("添加成功");
    }

    /**重点
     * 分页
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){
        Page<Dish> pageInfo = new Page<>(page,pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        //条件查询
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        lqw.like(name!=null,Dish::getName,name);
        lqw.orderByAsc(Dish::getCreateTime);
        dish.page(pageInfo,lqw);

        //数据拷贝 把pageInfo的数据拷贝到dishDtoPage,忽略records
        BeanUtils.copyProperties(pageInfo,dishDtoPage,"records");
        List<Dish> records = pageInfo.getRecords();

        //集合通过stream流的方式给DishDto的CategoryName赋值
        List<DishDto> list=records.stream().map((item)->{
            DishDto dto = new DishDto();
            BeanUtils.copyProperties(item,dto);

            Category byId = category.getById(item.getCategoryId());
            if(byId!=null) {
                String name1 = byId.getName();
                dto.setCategoryName(name1);
            }
            return dto;
        }).collect(Collectors.toList());

        //给新的对象赋值
        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }

    /**
     * 回显菜品信息菜品
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){
        DishDto dto = dish.getWithFlavorId(id);
        return R.success(dto);
    }

    /**
     * 更新菜品信息
     * @param dto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dto){
            dish.updateWithFlavor(dto);
        return R.success("更新成功");
    }

    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        dish.deleteWithFlavor(ids);
        return R.success("删除成功") ;
    }

    @PostMapping("/status/{n}")
    public R<String> status(@PathVariable int n,Long [] ids){
        log.info(String.valueOf(n),ids);
        for(long id:ids) {
            Dish byId = dish.getById(id);
            if(n==1){
                byId.setStatus(1);
            }
            if(n==0){
                byId.setStatus(0);
            }
            dish.updateById(byId);
        }
        return R.success("成功");
    }

    /**
     * 查菜品
     * @param dish1
     * @return
     */
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish1){
        LambdaQueryWrapper<Dish> lqw = new LambdaQueryWrapper<>();
        lqw.eq(dish1.getCategoryId()!=null,Dish::getCategoryId,dish1.getCategoryId());
        lqw.orderByAsc(Dish::getSort);
        lqw.eq(Dish::getStatus,1);
        List<Dish> list = dish.list(lqw);

        List<DishDto> dishDtos=list.stream().map((item)->{
            DishDto dto = new DishDto();
            BeanUtils.copyProperties(item,dto);
            LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(DishFlavor::getDishId,item.getId());
            List<DishFlavor> list1 = dishFlavor.list(lambdaQueryWrapper);
            dto.setFlavors(list1);
            return dto;
        }).collect(Collectors.toList());
        return R.success(dishDtos);
    }
}
