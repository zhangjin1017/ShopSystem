package com.my.controller;

import com.google.gson.Gson;
import com.my.dao.CartMapper;
import com.my.pojo.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class CartController {

    CartMapper cartMapper;

    @Autowired
    public void setCartMapper(CartMapper cartMapper) {
        this.cartMapper = cartMapper;
    }

    Gson gson = new Gson();

    @ResponseBody
    @RequestMapping("/addToCart")
    public String addToCart(@RequestParam("goodsId") int goodsId,
                            @RequestParam("userId") int userId,
                            @RequestParam("num") int num) {

        Cart cart = new Cart(null,userId,goodsId,num,0);
        int line = cartMapper.insert(cart);
        Map<String,String> map = new HashMap();

        if(line > 0){
            map.put("code","SUCCESS");
        }else{
            map.put("code","ERROR");
        }
        System.out.println();
        return gson.toJson(map);


    }
}
