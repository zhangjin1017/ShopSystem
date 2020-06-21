package com.my.controller;

import com.google.gson.Gson;
import com.my.pojo.Address;
import com.my.pojo.AddressExample;
import com.my.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AddressController {

    AddressService addressService;

    @Autowired
    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }
    Gson gson=new Gson();

    @ResponseBody
    @RequestMapping("/getAddress")
    public String getAddress(@RequestParam("userId")int userId){
        Map<String,String> map = new HashMap<>();
        AddressExample addressExample = new AddressExample();
        addressExample.createCriteria().andUserIdEqualTo(userId);
        List<Address> addressList =addressService.selectByExample(addressExample);
        if(addressList.size() == 0){
            map.put("code","EMPTY");
        }else{
            map.put("addressList",gson.toJson(addressList));
            map.put("code","SUCCESS");
        }
        return gson.toJson(map);
    }
}
