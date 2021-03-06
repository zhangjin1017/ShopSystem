package com.my.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.my.pojo.Cart;
import com.my.pojo.CartExample;
import com.my.pojo.Goods;
import com.my.service.CartService;
import com.my.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CartController {

    CartService cartService;

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    GoodsService goodsService;

    @Autowired
    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    Gson gson = new Gson();


    @RequestMapping(value = "/addToCart", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String addToCart(@RequestParam("goodsId") int goodsId,
                            @RequestParam("userId") int userId,
                            @RequestParam("num") int num) {

        Cart cart = new Cart(null, userId, goodsId, num, 0);
        int line = cartService.insert(cart);
        Map<String, String> map = new HashMap<>();

        if (line > 0) {
            map.put("code", "SUCCESS");
        } else {
            map.put("code", "ERROR");
        }
        return gson.toJson(map);
    }

    @ResponseBody
    @RequestMapping(value = "/getCarts", produces = "text/plain;charset=UTF-8")
    public String getCarts(@RequestParam("userId") int userId) {
        CartExample cartExample = new CartExample();
        cartExample.createCriteria().andUserIdEqualTo(userId);
        List<Cart> cartList = cartService.selectByExample(cartExample);
        Map<String, String> map = new HashMap<>();
        if (cartList.size() > 0) {
            List<Goods> goodsList = new ArrayList<>();
            for (Cart cart : cartList) {
                int goodId = cart.getGoodsId();
                goodsList.add(goodsService.selectByPrimaryKey(goodId));
            }
            map.put("code", "SUCCESS");
            map.put("cartList", gson.toJson(cartList));
            map.put("goodsList", gson.toJson(goodsList));
        } else {
            map.put("code", "EMPTY");
        }
        return gson.toJson(map);
    }

    @ResponseBody
    @RequestMapping(value = "/delCartGoods", produces = "text/plain;charset=UTF-8")
    public String delCartGoods(@RequestParam("cartId") int cartId) {
        int line = cartService.deleteByPrimaryKey(cartId);
        Map<String, String> map = new HashMap<>();
        if (line > 0) {
            map.put("code", "SUCCESS");
        } else {
            map.put("code", "ERROR");
        }
        return gson.toJson(map);
    }

    @ResponseBody
    @RequestMapping(value = "/getOrders", produces = "text/plain;charset=UTF-8")
    public String test(@RequestParam("selected") String selected) {
        List<Integer> list = JSONObject.parseArray(selected, Integer.class);
        Map<String, String> map = new HashMap<>();
        List<Cart> carts = new ArrayList<>();
        List<Goods> goods = new ArrayList<>();
        for (Integer i : list) {
            Cart cart = cartService.selectByPrimaryKey(i);
            carts.add(cart);

            int goodId = cart.getGoodsId();
            goods.add(goodsService.selectByPrimaryKey(goodId));
        }
        map.put("code", "SUCCESS");
        map.put("cartList", gson.toJson(carts));
        map.put("goodsList", gson.toJson(goods));

        return gson.toJson(map);
    }
}
