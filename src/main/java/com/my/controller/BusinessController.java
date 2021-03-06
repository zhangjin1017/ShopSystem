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

    @RequestMapping(value = "/businessRegister", produces = "text/plain;charset=UTF-8")
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
            map.put("code", "???????????????");
        } else if (shopNameSize > 0) {
            map.put("code", "??????????????????");
        } else if (phoneSize > 0) {
            map.put("code", "???????????????");
        } else {
            Business business = new Business(null, username, password, shopName, imgUrl, phone);
            int line = businessService.insert(business);
            if (line > 0) {
                map.put("code", "SUCCESS");
            } else {
                map.put("code", "????????????");
            }
        }
        return new Gson().toJson(map);
    }

    @RequestMapping(value = "/businessLogin", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String businessRegister(@RequestParam("username") String username,
                                   @RequestParam("password") String password) {
        Map<String, String> map = new HashMap<>();
        BusinessExample businessExample = new BusinessExample();
        BusinessExample.Criteria criteria = businessExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        criteria.andPasswordEqualTo(password);
        Business business = businessService.selectByExample(businessExample).get(0);

        if (business != null) {
            map.put("code", "SUCCESS");
            map.put("business", gson.toJson(business));
        } else {
            map.put("code", "?????????????????????????????????");
        }
        return new Gson().toJson(map);
    }

    @RequestMapping("/changePassword")
    @ResponseBody
    public String changePassword(@RequestParam("businessId") int businessId,
                                 @RequestParam("password") String password) {
        Map<String, String> map = new HashMap<>();

        Business business = new Business();
        business.setBusinessId(businessId);
        business.setPassword(password);

        int line = businessService.updateByPrimaryKeySelective(business);

        if (line > 0) {
            map.put("code", "SUCCESS");
        } else {
            map.put("code", "ERROR");
        }
        return new Gson().toJson(map);
    }
}
