package com.my.controller;

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


            map.put("pageCount",pageInfo.getPages());
            map.put("totalNum", pageInfo.getTotal());
            System.out.println(list);
            System.out.println(businessList);
        }
        return gson.toJson(map);
    }


    @ResponseBody
    @RequestMapping("/addGoods")
    public String addGoods(@RequestParam("businessId")int businessId,
                           @RequestParam("name")String name,
                           @RequestParam("price")double price,
                           @RequestParam("stock")int stock,
                           @RequestParam("info")String info,
                           @RequestParam("imgUrl")String imgUrl){
        Map<String, Object> map = new HashMap<>();
        Goods goods = new Goods(null,businessId,name,price,1,imgUrl,stock,info);
        goodsService.insertSelective(goods);
        GoodsExample goodsExample = new GoodsExample();
        GoodsExample.Criteria criteria = goodsExample.createCriteria();
        criteria.andBusinessIdEqualTo(businessId);
        criteria.andNameEqualTo(name);
        criteria.andInfoEqualTo(info);
        criteria.andPriceEqualTo(price);

        Goods goods1 = goodsService.selectByExample(goodsExample).get(0);
        if(stock != 0){
            stockService.insertSelective(new Stock(null,goods1.getGoodsId(),Stock.IN,new Date(),stock));
            map.put("code","SUCCESS");
            return gson.toJson(map);
        }
        map.put("code","ERROR");
        return gson.toJson(map);
    }

    @RequestMapping(value = "/getAllGoods", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getAllGoods(@RequestParam("businessId") int businessId) {
        Map<String, String> map = new HashMap<>();
        GoodsExample goodsExample=new GoodsExample();
        GoodsExample.Criteria criteria=goodsExample.createCriteria();
        criteria.andBusinessIdEqualTo(businessId);
        List<Goods> goodsList=goodsService.selectByExample(goodsExample);
        System.out.println("goodsList"+goodsList);
        if(goodsList != null){
            map.put("code","SUCCESS");
            map.put("goodsList",gson.toJson(goodsList));
        }else{
            map.put("code","您还没有发布商品");
        }
        return new Gson().toJson(map);
    }


}
