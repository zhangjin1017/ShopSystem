package com.my.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.my.pojo.Cart;
import com.my.pojo.Goods;
import com.my.pojo.Orders;
import com.my.pojo.OrdersExample;
import com.my.service.AddressService;
import com.my.service.CartService;
import com.my.service.GoodsService;
import com.my.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class OrderController {
    OrdersService ordersService;

    @Autowired
    public void setOrdersService(OrdersService ordersService) {
        this.ordersService = ordersService;
    }

    CartService cartService;

    @Autowired
    public void setCartService(CartService cartService) {
        this.cartService = cartService;
    }

    AddressService addressService;

    @Autowired
    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }

    GoodsService goodsService;

    @Autowired
    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    Gson gson = new Gson();

    @ResponseBody
    @RequestMapping("/pushOrders")
    public String pushOrders(@RequestParam("addressId") int addressId,
                             @RequestParam("cartIds") String cartIds) {
        List<Integer> list = JSONObject.parseArray(cartIds, Integer.class);

        for (int i : list) {
            int cartId = list.get(i);
            Cart cart = cartService.selectByPrimaryKey(cartId);
            int goodsId = cart.getGoodsId();
            Goods goods = goodsService.selectByPrimaryKey(goodsId);

            Orders orders = new Orders();
            int businessId = goods.getBusinessId();
            orders.setBusinessId(businessId);
            orders.setAddressId(addressId);
            int userId = cart.getUserId();
            orders.setUserId(userId);
            orders.setGoodsId(goodsId);
            orders.setDate(new Date());
            orders.setType(0);
            orders.setNum(cart.getNum());

            ordersService.insertSelective(orders);
        }
        return gson.toJson("SUCCESS");
    }


    @ResponseBody
    @RequestMapping("/showOrders")
    public String showOrders(@RequestParam("userId") int userId) {
        Map<String, String> map = new HashMap<>();
        OrdersExample ordersExample = new OrdersExample();
        ordersExample.createCriteria().andUserIdEqualTo(userId);
        List<Orders> orders = ordersService.selectByExample(ordersExample);
        if (orders.size() > 0) {
            map.put("ordersList",gson.toJson(orders));
            map.put("code","SUCCESS");
        }else{
            map.put("code","ERROR");
        }
        return gson.toJson(map);

    }

}
