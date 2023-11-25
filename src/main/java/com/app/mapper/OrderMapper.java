package com.app.mapper;

import com.app.dto.OrderDto;
import com.app.dto.OrderItemDto;
import com.app.entity.Order;
import com.app.entity.OrderDetail;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class OrderMapper {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    OrderDetailMapper orderDetailMapper;

    public Order toOrder(OrderDto orderDto) {
        if (orderDto == null) {
            return null;
        }
        List<OrderDetail> orderDetails = new ArrayList<>();
        if(orderDto.getOrderItemDtos() != null){
            for (OrderItemDto orderItemDto: orderDto.getOrderItemDtos()){
                orderDetails.add(orderDetailMapper.toOrderDetail(orderItemDto));
            }
        }
        Order order = modelMapper.map(orderDto, Order.class);
        order.setOrderDetailList(orderDetails);
        return order;
    }
}
