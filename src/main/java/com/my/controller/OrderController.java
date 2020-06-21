package com.my.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.my.pojo.*;
import com.my.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

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

    StockService stockService;

    @Autowired
    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

    Gson gson = new Gson();

    @ResponseBody
    @RequestMapping(value = "/pushOrders", produces = "text/plain;charset=UTF-8")
    public String pushOrders(@RequestParam("addressId") int addressId,
                             @RequestParam("cartIds") String cartIds) {
        List<Integer> list = JSONObject.parseArray(cartIds, Integer.class);

        for (int cartId : list) {
            Cart cart = cartService.selectByPrimaryKey(cartId);
            int goodsId = cart.getGoodsId();
            Goods goods = goodsService.selectByPrimaryKey(goodsId);
            if (goods.getStock() >= cart.getNum()) {
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

                goods.setStock(goods.getStock() - cart.getNum());
                GoodsExample goodsExample = new GoodsExample();
                goodsExample.createCriteria().andGoodsIdEqualTo(goodsId);
                goodsService.updateByExample(goods, goodsExample);


                cartService.deleteByPrimaryKey(cartId);

                Stock stock = new Stock(null, goodsId, 1, new Date(), cart.getNum());
                stockService.insert(stock);
            } else {
                return gson.toJson("ERROR");
            }
        }
        return gson.toJson("SUCCESS");
    }


    @ResponseBody
    @RequestMapping(value = "/showOrders", produces = "text/plain;charset=UTF-8")
    public String showOrders(@RequestParam("userId") int userId) {
        Map<String, String> map = new HashMap<>();
        OrdersExample ordersExample = new OrdersExample();
        ordersExample.createCriteria().andUserIdEqualTo(userId);
        List<Orders> orders = ordersService.selectByExample(ordersExample);
        List<Goods> goods = new ArrayList<>();
        for (Orders order : orders) {
            goods.add(goodsService.selectByPrimaryKey(order.getGoodsId()));
        }
        if (orders.size() > 0) {
            map.put("goodsList", gson.toJson(goods));
            map.put("ordersList", gson.toJson(orders));
            map.put("code", "SUCCESS");
        } else {
            map.put("code", "ERROR");
        }
        return gson.toJson(map);
    }

    @ResponseBody
    @RequestMapping(value = "/confirm", produces = "text/plain;charset=UTF-8")
    public String confirm(@RequestParam("orderId") int id) {
        Orders orders = ordersService.selectByPrimaryKey(id);
        orders.setType(Orders.Delivered);
        int line = ordersService.updateByPrimaryKey(orders);

        Map<String, String> map = new HashMap<>();
        if (line > 0) {
            map.put("code", "SUCCESS");
        } else {
            map.put("code", "FALSE");
        }
        return gson.toJson(map);
    }
}
