package com.my.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.my.dao.GoodsMapper;
import com.my.dao.PersonMapper;
import com.my.pojo.Goods;
import com.my.pojo.User;
import com.my.pojo.UserExample;
import com.my.service.UserService;
import com.my.util.FaceUtil;
import com.my.util.PhoneCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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


    @RequestMapping(value = "/searchGoods", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String searchGoods(@RequestParam("goodsName") String goodsName) {
        Map<String, List> map = new HashMap<>();
//        如果搜索框为空，则返回所有商品
        if (goodsName == null) {
            List<Goods> list = goodsMapper.selectByExample(null);
            map.put("goodsList", list);
            System.out.println(1);
        }
        return null;
    }
}
