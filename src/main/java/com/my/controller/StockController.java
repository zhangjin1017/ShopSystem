package com.my.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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

import java.text.SimpleDateFormat;
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

    @RequestMapping(value = "/getAllStock", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getAllStock(@RequestParam("perPageCount") int perPageCount,
                              @RequestParam("currentPage") int currentPage) {
        PageHelper.startPage(currentPage, perPageCount);
        List<Stock> list = stockService.selectByExample(null);
        Gson gson = new Gson();
        List<Goods> goodsList = new ArrayList<>();
        List<String> dateList = new ArrayList<>();
        for (Stock stock : list) {
            goodsList.add(goodsService.selectByPrimaryKey(stock.getGoodsId()));
            dateList.add(new SimpleDateFormat("yyyy-MM-dd").format(stock.getDate()));
        }
        Map<String,Object> map = new HashMap<>();
        PageInfo pageInfo = new PageInfo<Stock>(list);
        map.put("dateList",gson.toJson(dateList));
        map.put("pageCount", pageInfo.getPages());
        map.put("totalNum", pageInfo.getTotal());
        map.put("stockList",gson.toJson(list));
        map.put("goodsList",gson.toJson(goodsList));
        map.put("code","SUCCESS");

        return gson.toJson(map);
    }
}
