package com.my.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.my.pojo.*;
import com.my.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
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

    UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

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

                Stock stock = new Stock(null, goodsId, 2, new Date(), cart.getNum());
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
        List<String> dateList = new ArrayList<>();
        List<Goods> goods = new ArrayList<>();
        for (Orders order : orders) {
            dateList.add(new SimpleDateFormat("yyyy-MM-dd").format(order.getDate()));
            goods.add(goodsService.selectByPrimaryKey(order.getGoodsId()));
        }
        if (orders.size() > 0) {
            map.put("dateList", gson.toJson(dateList));
            map.put("goodsList", gson.toJson(goods));
            map.put("ordersList", gson.toJson(orders));
            map.put("code", "SUCCESS");
            System.out.println();
        } else {
            map.put("code", "ERROR");
        }
        return gson.toJson(map);
    }

    @ResponseBody
    @RequestMapping(value = "/confirmOrders", produces = "text/plain;charset=UTF-8")
    public String confirm(@RequestParam("orderId") int id) {
        Orders orders = ordersService.selectByPrimaryKey(id);
        orders.setType(Orders.Receipt);
        int line = ordersService.updateByPrimaryKey(orders);

        Map<String, String> map = new HashMap<>();
        if (line > 0) {
            map.put("code", "SUCCESS");
        } else {
            map.put("code", "FALSE");
        }
        return gson.toJson(map);
    }


    @ResponseBody
    @RequestMapping(value = "/getAllOrders", produces = "text/plain;charset=UTF-8")
    public String getAllOrders(@RequestParam("businessId") int businessId,
                               @RequestParam("perPageCount") int perPageCount,
                               @RequestParam("currentPage") int currentPage) {
        Map<String, Object> map = new HashMap<>();

        OrdersExample ordersExample = new OrdersExample();

        PageHelper.startPage(currentPage, perPageCount);
        ordersExample.createCriteria().andBusinessIdEqualTo(businessId);
        List<Orders> orders = ordersService.selectByExample(ordersExample);
        PageInfo<Orders> pageInfo = new PageInfo<Orders>(orders);
        List<Goods> goods = new ArrayList<>();
        List<String> dateList = new ArrayList<>();
        List<User> userList = new ArrayList<>();
        for (Orders order : orders) {
            dateList.add(new SimpleDateFormat("yyyy-MM-dd").format(order.getDate()));
            goods.add(goodsService.selectByPrimaryKey(order.getGoodsId()));
            userList.add(userService.selectByPrimaryKey(order.getUserId()));
        }
        if (orders.size() > 0) {

            map.put("pageCount", pageInfo.getPages());
            map.put("totalNum", pageInfo.getTotal());
            map.put("dateList", gson.toJson(dateList));
            map.put("goodsList", gson.toJson(goods));
            map.put("userList", gson.toJson(userList));
            map.put("ordersList", gson.toJson(orders));
            map.put("code", "SUCCESS");
            System.out.println();
        } else {
            map.put("code", "ERROR");
        }
        return gson.toJson(map);
    }

    @ResponseBody
    @RequestMapping(value = "/searchOrderByName", produces = "text/plain;charset=UTF-8")
    public String searchByName(@RequestParam("name") String name,
                               @RequestParam("perPageCount") int perPageCount,
                               @RequestParam("currentPage") int currentPage) {
        Map<String, Object> map = new HashMap<>();
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(name);

        User user = userService.selectByExample(userExample).get(0);

        OrdersExample ordersExample = new OrdersExample();

        PageHelper.startPage(currentPage, perPageCount);
        ordersExample.createCriteria().andUserIdEqualTo(user.getUserId());
        List<Orders> orders = ordersService.selectByExample(ordersExample);
        PageInfo<Orders> pageInfo = new PageInfo(orders);
        List<User> userList = new ArrayList<>();
        List<Goods> goods = new ArrayList<>();
        List<String> dateList = new ArrayList<>();
        for (Orders order : orders) {
            userList.add(userService.selectByPrimaryKey(order.getUserId()));
            dateList.add(new SimpleDateFormat("yyyy-MM-dd").format(order.getDate()));
            goods.add(goodsService.selectByPrimaryKey(order.getGoodsId()));
        }
        if (orders.size() > 0) {
            map.put("pageCount", pageInfo.getPages());
            map.put("totalNum", pageInfo.getTotal());
            map.put("dateList", gson.toJson(dateList));
            map.put("goodsList", gson.toJson(goods));
            map.put("userList", gson.toJson(userList));
            map.put("ordersList", gson.toJson(orders));
            map.put("code", "SUCCESS");
            System.out.println();
        } else {
            map.put("code", "ERROR");
        }

        return gson.toJson(map);
    }

    @ResponseBody
    @RequestMapping(value = "/updateLogisticsId", produces = "text/plain;charset=UTF-8")
    public String updateLogisticsId(@RequestParam("logisticsId") String logisticsId,
                                    @RequestParam("orderId") int orderId) {
        Map<String, Object> map = new HashMap<>();

        Orders orders = new Orders();
        orders.setOrderId(orderId);
        orders.setLogistics(logisticsId);
        orders.setType(Orders.Delivered);

        int line = ordersService.updateByPrimaryKeySelective(orders);
        if (line == 1) {
            map.put("code", "SUCCESS");
            System.out.println();
        } else {
            map.put("code", "ERROR");
        }

        return gson.toJson(map);
    }


}
