package com.jt.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.CartMapper;
import com.jt.pojo.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class DubboCartServiceImpl implements DubboCartService {
    @Autowired
    private CartMapper cartMapper;

    @Override
    public List<Cart> findCartListByUserId(Long userId) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);

        return cartMapper.selectList(queryWrapper);
    }

    @Override
    @Transactional
    public void updateCartNum(Cart cart) {
        Cart cartTemp = new Cart();
        cartTemp.setNum(cart.getNum()).setUpdated(new Date());
        UpdateWrapper<Cart> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("item_id", cart.getItemId());
        updateWrapper.eq("user_id", cart.getUserId());
        cartMapper.update(cartTemp, updateWrapper);
    }

    @Override
    @Transactional
    public void saveCart(Cart cart) {
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", cart.getUserId());
        queryWrapper.eq("item_id", cart.getItemId());
        Cart cartDB = cartMapper.selectOne(queryWrapper);
        if (cartDB == null) {
            cart.setCreated(new Date()).setUpdated(cart.getCreated());
            cartMapper.insert(cart);
        } else {
            int num = cart.getNum() + cartDB.getNum();
            Cart cartTemp = new Cart();
            cartTemp.setNum(num).setId(cartDB.getId()).setUpdated(new Date());
            cartMapper.updateById(cartTemp);
        }
    }

    @Override
    public void deleteCart(Cart cart) {
        cartMapper.delete(new QueryWrapper<Cart>(cart));
    }
}
