package com.jt.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.Cart;
import com.jt.pojo.User;
import com.jt.service.DubboCartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Reference(check = false)
    private DubboCartService cartService;

    @RequestMapping("/show")
    public String show(Model model, HttpServletRequest request) {
        User user = (User) request.getAttribute("JT_USER");;
        List<Cart> cartList = cartService.findCartListByUserId(user.getId());
        model.addAttribute("cartList", cartList);
        return "cart";

    }

    @RequestMapping("/update/num/{itemId}/{num}")
    @ResponseBody
    public void updateCart(Cart cart, HttpServletRequest request) {
        User user = (User) request.getAttribute("JT_USER");;
        cart.setUserId(user.getId());
        cartService.updateCartNum(cart);

    }

    @RequestMapping("/add/{itemId}")
    public String saveCart(Cart cart, HttpServletRequest request) {
        User user = (User) request.getAttribute("JT_USER");;
        cart.setUserId(user.getId());
        cartService.saveCart(cart);
        return "redirect:/cart/show";
    }

    @RequestMapping("/delete/{itemId}")
    public String deleteCart(@PathVariable Long itemId, HttpServletRequest request) {
        User user = (User) request.getAttribute("JT_USER");
        Cart cart = new Cart();
        cart.setUserId(user.getId());
        cart.setItemId(itemId);
        cartService.deleteCart(cart);
        return "redirect:/cart/show";
    }
}
