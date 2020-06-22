package com.my.controller;

import com.alibaba.fastjson.JSONArray;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.my.pojo.*;
import com.my.service.BusinessService;
import com.my.service.GoodsService;
import com.my.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    Gson gson = new Gson();

    GoodsService goodsService;

    @Autowired
    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    BusinessService businessService;

    @Autowired
    public void setBusinessService(BusinessService businessService) {
        this.businessService = businessService;
    }

    StockService stockService;

    @Autowired
    public void setStockService(StockService stockService) {
        this.stockService = stockService;
    }

    /**
     * @param searchInfo 搜索内容
     * @param type       搜索类型，如果为0显示全部
     * @param page       当前页码
     * @param pageSize   每页显示行数
     * @return
     */
    @RequestMapping(value = "/searchGoods", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String searchGoods(@RequestParam("searchInfo") String searchInfo,
                              @RequestParam("searchInfoType") String type,
                              @RequestParam("page") String page,
                              @RequestParam("pageSize") String pageSize) {
        Map<String, Object> map = new HashMap<>();
//        如果搜索框为空，则返回所有商品
        int myPage = Integer.parseInt(page);
        int myPageSize = Integer.parseInt(pageSize);
        PageHelper.startPage(myPage, myPageSize);

        List<Goods> list;
        if (type.equals("0")) {
            list = goodsService.selectByExample(null);
            map.put("goodsList", gson.toJson(list));
            System.out.println(1);
        } else {
            GoodsExample goodsExample = new GoodsExample();
            goodsExample.createCriteria().andNameLike("%" + searchInfo + "%");
            list = goodsService.selectByExample(goodsExample);
        }

        if (list.size() == 0) {
            map.put("code", "EMPTY");
        } else {
            List<Business> businessList = new ArrayList<>();
            for (Goods goods : list) {
                int businessId = goods.getBusinessId();
                Business business = businessService.selectByPrimaryKey(businessId);
                businessList.add(business);
            }
            map.put("code", "SUCCESS");
            map.put("goodsList", gson.toJson(list));
            map.put("businessList", gson.toJson(businessList));
            PageInfo pageInfo = new PageInfo<Goods>(list);


            map.put("pageCount", pageInfo.getPages());
            map.put("totalNum", pageInfo.getTotal());
            System.out.println(list);
            System.out.println(businessList);
        }
        return gson.toJson(map);
    }


    @ResponseBody
    @RequestMapping(value = "/addGoods", produces = "text/plain;charset=UTF-8")
    public String addGoods(@RequestParam("businessId") int businessId,
                           @RequestParam("name") String name,
                           @RequestParam("price") double price,
                           @RequestParam("stock") int stock,
                           @RequestParam("info") String info,
                           @RequestParam("imgUrl") String imgUrl) {
        Map<String, Object> map = new HashMap<>();
        Goods goods = new Goods(null, businessId, name, price, 1, imgUrl, stock, info);
        goodsService.insertSelective(goods);
        GoodsExample goodsExample = new GoodsExample();
        GoodsExample.Criteria criteria = goodsExample.createCriteria();
        criteria.andBusinessIdEqualTo(businessId);
        criteria.andNameEqualTo(name);
        criteria.andInfoEqualTo(info);
        criteria.andPriceEqualTo(price);

        Goods goods1 = goodsService.selectByExample(goodsExample).get(0);
        if (stock != 0) {
            stockService.insertSelective(new Stock(null, goods1.getGoodsId(), Stock.IN, new Date(), stock));
            map.put("code", "SUCCESS");
            return gson.toJson(map);
        }
        map.put("code", "ERROR");
        return gson.toJson(map);
    }

    @RequestMapping(value = "/getAllGoods", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getAllGoods(@RequestParam("businessId") int businessId,
                              @RequestParam("perPageCount") int perPageCount,
                              @RequestParam("currentPage") int currentPage) {
        Map<String, Object> map = new HashMap<>();
        GoodsExample goodsExample = new GoodsExample();
        GoodsExample.Criteria criteria = goodsExample.createCriteria();
        criteria.andBusinessIdEqualTo(businessId);
        PageHelper.startPage(currentPage, perPageCount);
        List<Goods> goodsList = goodsService.selectByExample(goodsExample);
        System.out.println("goodsList" + goodsList);
        if (goodsList != null) {
            map.put("code", "SUCCESS");
            PageInfo pageInfo = new PageInfo<Goods>(goodsList);

            map.put("pageCount", pageInfo.getPages());
            map.put("totalNum", pageInfo.getTotal());
            map.put("goodsList", gson.toJson(goodsList));
        } else {
            map.put("code", "您还没有发布商品");
        }
        return new Gson().toJson(map);
    }


    @RequestMapping(value = "/changeGoods", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String changeGoods(@RequestParam("goodsId") int goodsId,
                              @RequestParam("name") String name,
                              @RequestParam("price") double price,
                              @RequestParam("stock") int stock,
                              @RequestParam("info") String info,
                              @RequestParam("imgUrl") String imgUrl) {
        Map<String, String> map = new HashMap<>();
        Goods goods = new Goods(goodsId, null, name, price, 1, imgUrl, stock, info);

        int line = goodsService.updateByPrimaryKeySelective(goods);

        Goods goods1 = goodsService.selectByPrimaryKey(goodsId);
        if (goods1.getStock() < stock) {
            line += stockService.insertSelective(new Stock(null, goodsId, Stock.IN, new Date(), stock - goods1.getStock()));
        } else if (goods1.getStock() > stock) {
            line += stockService.insertSelective(new Stock(null, goodsId, Stock.IN, new Date(), goods1.getStock() - stock));
        }

        if (line == 0) {
            map.put("code", "ERROR");
        } else {
            map.put("code", "SUCCESS");
        }

        return new Gson().toJson(map);
    }

    @RequestMapping(value = "/searchByName", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String searchByName(@RequestParam("name") String name,
                               @RequestParam("perPageCount") int perPageCount,
                               @RequestParam("currentPage") int currentPage) {
        Map<String, Object> map = new HashMap<>();

        GoodsExample goodsExample = new GoodsExample();
        goodsExample.createCriteria().andNameLike("%" + name + "%");
        PageHelper.startPage(currentPage, perPageCount);
        List<Goods> goods = goodsService.selectByExample(goodsExample);

        if (goods.size() > 0) {
            PageInfo pageInfo = new PageInfo<Goods>(goods);

            map.put("pageCount", pageInfo.getPages());
            map.put("totalNum", pageInfo.getTotal());
            map.put("code", "SUCCESS");
            map.put("list", gson.toJson(goods));
        } else {
            map.put("code", "ERROR");
        }
        System.out.println();
        return new Gson().toJson(map);
    }

    @ResponseBody
    @RequestMapping("/deleteGoods")
    public String deleteGoods(@RequestParam("selected")String selected){
        List<Integer> list = JSONArray.parseArray(selected,Integer.class);

        for (int i:list){
            goodsService.deleteByPrimaryKey(i);
        }
        return gson.toJson("SUCCESS");
    }
}

