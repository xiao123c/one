package com.example.ruiji.dto;

import com.example.ruiji.pojo.Dish;
import com.example.ruiji.pojo.DishFlavor;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
//数据传输对象 date transfer Object
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
