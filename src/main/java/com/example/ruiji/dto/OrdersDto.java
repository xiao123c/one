package com.example.ruiji.dto;


import com.example.ruiji.pojo.OrderDetail;
import com.example.ruiji.pojo.Orders;
import lombok.Data;
import java.util.List;

@Data
public class OrdersDto extends Orders {

    private String userName;

    private String phone;

    private String address;

    private String consignee;

    private List<OrderDetail> orderDetails;
	
}
