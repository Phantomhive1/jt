package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.pojo.Order;
import com.jt.pojo.OrderItem;
import com.jt.pojo.OrderShipping;
import com.jt.mapper.OrderItemMapper;
import com.jt.mapper.OrderMapper;
import com.jt.mapper.OrderShippingMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

public class DubboOrderServiceImpl implements DubboOrderService {
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderShippingMapper orderShippingMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;

    @Override
    public String saveOrder(Order order) {
        String orderId = ""+order.getUserId() + System.currentTimeMillis();
        Date date = new Date();

        //1.实现订单入库
        order.setOrderId(orderId)
                .setStatus(1)   //未付款
                .setCreated(date)
                .setUpdated(date);
        orderMapper.insert(order);

        //2.订单物流入库
        OrderShipping orderShipping = order.getOrderShipping();
        orderShipping.setOrderId(orderId)
                .setCreated(date)
                .setUpdated(date);
        orderShippingMapper.insert(orderShipping);

        //3.订单商品入库
        List<OrderItem> list = order.getOrderItems();
        for (OrderItem orderItem : list) {

            orderItem.setOrderId(orderId)
                    .setCreated(date)
                    .setUpdated(date);
            orderItemMapper.insert(orderItem);
        }

        return orderId;

    }

    @Override
    public Order findOrderById(String orderId) {
        Order order = orderMapper.selectById(orderId);
        OrderShipping orderShipping = orderShippingMapper.selectById(orderId);
        QueryWrapper<OrderItem> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("order_id", orderId);
        List<OrderItem> orderItems = orderItemMapper.selectList(queryWrapper);

        return order.setOrderShipping(orderShipping).setOrderItems(orderItems);
    }
}
