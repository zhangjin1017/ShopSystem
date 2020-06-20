package com.my.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.gson.Gson;
import com.my.dao.BusinessMapper;
import com.my.dao.GoodsMapper;
import com.my.dao.PersonMapper;
import com.my.pojo.*;
import com.my.service.UserService;
import com.my.util.FaceUtil;
import com.my.util.PhoneCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("/goods")
public class GoodsController {

    Gson gson = new Gson();

    GoodsMapper goodsMapper;

    @Autowired
    public void setGoodsMapper(GoodsMapper goodsMapper) {
        this.goodsMapper = goodsMapper;
    }

    BusinessMapper businessMapper;

    @Autowired
    public void setBusinessMapper(BusinessMapper businessMapper) {
        this.businessMapper = businessMapper;
    }

    /**
     *
     * @param searchInfo 搜索内容
     * @param type 搜索类型，如果为0显示全部
     * @param page 当前页码
     * @param pageSize 每页显示行数
     * @return
     */
    @RequestMapping(value = "/searchGoods", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String searchGoods(@RequestParam("searchInfo") String searchInfo,
                              @RequestParam("searchInfoType") String type,
                              @RequestParam("page")String page,
                              @RequestParam("pageSize")String pageSize) {
        Map<String, String> map = new HashMap<>();
//        如果搜索框为空，则返回所有商品
        int myPage = Integer.parseInt(page);
        int myPageSize = Integer.parseInt(pageSize);
        PageHelper.startPage(myPage,myPageSize);
        List<Business> businessList = new ArrayList<>();
        List<Goods> list;
        if (type.equals("0")) {
            list = goodsMapper.selectByExample(null);
            map.put("goodsList", gson.toJson(list));
            System.out.println(1);
        } else {
            GoodsExample goodsExample = new GoodsExample();
            goodsExample.createCriteria().andNameLike("%" + searchInfo + "%");
            list = goodsMapper.selectByExample(goodsExample);

            for (Goods goods :list){
                int businessId = goods.getBusinessId();
                Business business = businessMapper.selectByPrimaryKey(businessId);
                businessList.add(business);
            }
        }

        if (list.size() == 0) {
            map.put("code", "EMPTY");
        } else {
            map.put("code", "SUCCESS");
            map.put("goodsList", gson.toJson(list));
            map.put("businessList",gson.toJson(businessList));
            System.out.println(gson.toJson(new PageInfo<Goods>(list)));
            System.out.println(list);
            System.out.println(businessList);
        }
        return gson.toJson(map);
    }


}
