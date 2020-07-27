package com.jt.controller;

import com.jt.pojo.Cart;
import com.jt.pojo.Order;
import com.jt.pojo.User;
import com.jt.service.DubboCartService;
import com.jt.service.DubboOrderService;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private DubboCartService cartService;

    @Autowired
    private DubboOrderService orderService;

    @RequestMapping("/create")
    public String create(HttpServletRequest request, Model model) {
        User user = (User) request.getAttribute("JT_USER");
        long userId = user.getId();
        List<Cart> cartList = cartService.findCartListByUserId(userId);
        model.addAttribute("carts", cartList);
        return "order-cart";
    }

    @RequestMapping("/submit")
    @ResponseBody
    public SysResult saveOrder(Order order, HttpServletRequest request) {
        User user = (User) request.getAttribute("JT_USER");
        Long userId = user.getId();
        order.setUserId(userId);	//将userId进行赋值操作.
        String orderId = orderService.saveOrder(order);
        if(StringUtils.isEmpty(orderId)) {
            //说明:后端服务器异常
            return SysResult.fail();
        }
        return SysResult.success(orderId);
    }

    @RequestMapping("/success")
    public String findOrderById(String id, Model model) {
        Order order = orderService.findOrderById(id);
        model.addAttribute("order", order);
        return "success";
    }

}
