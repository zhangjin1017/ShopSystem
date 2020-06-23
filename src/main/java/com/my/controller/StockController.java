package com.my.controller;

import com.google.gson.Gson;
import com.my.pojo.Goods;
import com.my.pojo.Stock;
import com.my.pojo.StockExample;
import com.my.service.GoodsService;
import com.my.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class StockController {

    StockService stockService;

    @Autowired
    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

    GoodsService goodsService;

    @Autowired
    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @RequestMapping("/getAllStock")
    @ResponseBody
    public String getAllStock(@RequestParam("businessId") String businessId,
                              @RequestParam("perPageCount") String perPageCount,
                              @RequestParam("currentPage") StockService currentPage) {

        List<Stock> list = stockService.selectByExample(null);
        Gson gson = new Gson();
        List<Goods> goodsList = new ArrayList<>();
        for (Stock stock : list) {
            goodsList.add(goodsService.selectByPrimaryKey(stock.getGoodsId()));
        }
        Map<String,String> map = new HashMap<>();
        map.put("stockList",gson.toJson(list));
        map.put("goodsList",gson.toJson(goodsList));
        map.put("code","SUCCESS");

        return gson.toJson(map);
    }
}
