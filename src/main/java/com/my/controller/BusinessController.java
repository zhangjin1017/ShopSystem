package com.my.controller;

import com.google.gson.Gson;
import com.my.pojo.Business;
import com.my.pojo.BusinessExample;
import com.my.pojo.Goods;
import com.my.pojo.GoodsExample;
import com.my.service.BusinessService;
import com.my.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class BusinessController {

    BusinessService businessService;

    GoodsService goodsService;
    @Autowired
    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @Autowired
    public void setBusinessService(BusinessService businessService) {
        this.businessService = businessService;
    }
    Gson gson = new Gson();

    @RequestMapping("/businessRegister")
    @ResponseBody
    public String businessRegister(@RequestParam("username") String username,
                                   @RequestParam("password") String password,
                                   @RequestParam("shop_name") String shopName,
                                   @RequestParam("phone") String phone,
                                   @RequestParam("imgUrl") String imgUrl) {
        Map<String, String> map = new HashMap<>();
        BusinessExample businessExample = new BusinessExample();
        businessExample.createCriteria().andUsernameEqualTo(username);
        int usernameSize = businessService.selectByExample(businessExample).size();
        businessExample.createCriteria().andShopNameEqualTo(shopName);
        int shopNameSize = businessService.selectByExample(businessExample).size();
        businessExample.createCriteria().andPhoneEqualTo(phone);
        int phoneSize = businessService.selectByExample(businessExample).size();

        if (usernameSize > 0) {
            map.put("code", "用户名重复");
        } else if (shopNameSize > 0) {
            map.put("code", "店铺名称重复");
        }else if(phoneSize > 0){
            map.put("code", "手机号重复");
        }else{
            Business business = new Business(null, username, password, shopName, imgUrl, phone);
            int line = businessService.insert(business);
            if (line > 0) {
                map.put("code", "SUCCESS");
            } else {
                map.put("code", "插入失败");
            }
        }
        return new Gson().toJson(map);
    }

    @RequestMapping("/businessLogin")
    @ResponseBody
    public String businessRegister(@RequestParam("username") String username,
                                   @RequestParam("password") String password) {
        Map<String, String> map = new HashMap<>();
        BusinessExample businessExample = new BusinessExample();
        BusinessExample.Criteria criteria =businessExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        criteria.andPasswordEqualTo(password);
        Business business = businessService.selectByExample(businessExample).get(0);

        if(business != null){
            map.put("code","SUCCESS");
            map.put("business",gson.toJson(business));
        }else{
            map.put("code","用户名不存在或密码错误");
        }
        return new Gson().toJson(map);
    }
    @RequestMapping("/getAllGoods")
    @ResponseBody
    public String businessRegister(@RequestParam("businessId") int businessId) {
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
