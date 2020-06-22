package com.my.controller;

import com.google.gson.Gson;
import com.my.pojo.Address;
import com.my.pojo.AddressExample;
import com.my.pojo.Person;
import com.my.pojo.PersonExample;
import com.my.service.AddressService;
import com.my.service.PersonService;
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

    Gson gson = new Gson();

    PersonService personService;

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    @ResponseBody
    @RequestMapping(value = "/getAddress", produces = "text/plain;charset=UTF-8")
    public String getAddress(@RequestParam("userId") int userId) {
        Map<String, String> map = new HashMap<>();

        PersonExample personExample = new PersonExample();
        personExample.createCriteria().andUserIdEqualTo(userId);
        List<Person> personList = personService.selectByExample(personExample);

        AddressExample addressExample = new AddressExample();
        addressExample.createCriteria().andUserIdEqualTo(userId);
        List<Address> addressList = addressService.selectByExample(addressExample);
        System.out.println(personList);
        String name = personList.get(0).getName();
        if (addressList.size() == 0) {
            map.put("code", "EMPTY");
        } else {
            System.out.println(gson.toJson(addressList));
            map.put("addressList", gson.toJson(addressList));

            map.put("code", "SUCCESS");
            map.put("name", name);

        }
        return gson.toJson(map);
    }

    @ResponseBody
    @RequestMapping(value = "/addAddress", produces = "text/plain;charset=UTF-8")
    public String addAddress(@RequestParam("userId") int userId,
                             @RequestParam("address1") String address1,
                             @RequestParam("address2") String address2) {
        Map<String, String> map = new HashMap<>();

        String[] addressArray = address1.split(" ");
        Address address = new Address();
        address.setProvince(addressArray[0]);
        address.setCity(addressArray[1]);
        address.setDistrict(addressArray[2]);
        address.setAddress(address2);
        address.setUserId(userId);

        int line = addressService.insertSelective(address);
        if (line > 0) {
            map.put("code", "SUCCESS");
        } else {
            map.put("code", "FALSE");
        }
        System.out.println("hello");
        System.out.println("hello");

        return gson.toJson(map);
    }

    @ResponseBody
    @RequestMapping(value = "/deleteAddress", produces = "text/plain;charset=UTF-8")
    public String deleteAddress(@RequestParam("addressId") int addressId) {
        Map<String, String> map = new HashMap<>();

        int line = addressService.deleteByPrimaryKey(addressId);
        if (line > 0) {
            map.put("code", "SUCCESS");
        } else {
            map.put("code", "FALSE");
        }
        return gson.toJson(map);
    }

    @ResponseBody
    @RequestMapping(value = "/changeAddress", produces = "text/plain;charset=UTF-8")
    public String changeAddress(@RequestParam("addressId") int addressId,
                                @RequestParam("address1") String address1,
                                @RequestParam("address2") String address2) {
        Map<String, String> map = new HashMap<>();
        Address address = new Address();
        if(address1 != null){
            String[] addressArray = address1.split(" ");

            address.setProvince(addressArray[0]);
            address.setCity(addressArray[1]);
            address.setDistrict(addressArray[2]);
        }
        if(address2 != null){
            address.setAddress(address2);
        }

        address.setAddressId(addressId);

        int line = addressService.updateByPrimaryKeySelective(address);
        if (line > 0) {
            map.put("code", "SUCCESS");
        } else {
            map.put("code", "FALSE");
        }
        System.out.println("hello");
        System.out.println("hello");

        return gson.toJson(map);
    }

}
