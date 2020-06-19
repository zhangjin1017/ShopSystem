package com.my.controller;

import com.google.gson.Gson;
import com.my.dao.PersonMapper;
import com.my.dao.UserMapper;
import com.my.dao.VipCardMapper;
import com.my.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin
@RequestMapping("/person")
public class PersonController {

    PersonMapper personMapper;
    Gson gson = new Gson();

    @Autowired
    public void setPersonMapper(PersonMapper personMapper) {
        this.personMapper = personMapper;
    }

    UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    VipCardMapper vipCardMapper;

    @Autowired
    public void setVipCardMapper(VipCardMapper vipCardMapper) {
        this.vipCardMapper = vipCardMapper;
    }

    @RequestMapping(value = "/getInfo", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getInfo(@RequestParam("userId") String userId) {
        Map<String, String> map = new HashMap<>();
        if (userId != null) {
            int id = Integer.parseInt(userId);
            PersonExample personExample = new PersonExample();
            personExample.createCriteria().andUserIdEqualTo(id);
            List<Person> list = personMapper.selectByExample(personExample);
            if (list.size() == 1) {
                Person person = list.get(0);
                System.out.println(person);
                Date date = person.getBirth();
                map.put("code", "SUCCESS");
                map.put("person", gson.toJson(person));
                map.put("birth", new SimpleDateFormat("yyyy-MM-dd").format(date));
                return gson.toJson(map);
            }
        }
        map.put("code", "ERROR");
        return gson.toJson(map);
    }

    @RequestMapping(value = "/updateInfo", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String updateInfo(@RequestParam("userId") String userId,
                             @RequestParam("name") String name,
                             @RequestParam("sex") String sex,
                             @RequestParam("birth") String birth,
                             @RequestParam("phone") String phone) {
        Map<String, String> map = new HashMap<>();

        int id = Integer.parseInt(userId);

        Person person = new Person();
        person.setName(name);
        person.setSex(sex);

        try {
            person.setBirth(new SimpleDateFormat("yyyy-MM-dd").parse(birth));
        } catch (ParseException e) {
            return map.put("code", "ERROR");
        }
        PersonExample personExample = new PersonExample();
        personExample.createCriteria().andUserIdEqualTo(id);
        personMapper.updateByExampleSelective(person, personExample);
        User user = new User();
//        user.setUserId(id);
        user.setPhone(phone);

        UserExample userExample = new UserExample();

        map.put("user", gson.toJson(userMapper.selectByPrimaryKey(id)));
        return gson.toJson(map);
    }


    @RequestMapping(value = "/updatePassword", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String updatePassword(@RequestParam("userId") String userId,
                                 @RequestParam("password") String password) {
        Map<String, String> map = new HashMap<>();

        int id = Integer.parseInt(userId);

        User user = new User();

        UserExample userExample = new UserExample();
        user.setPassword(password);
        userExample.createCriteria().andUserIdEqualTo(id);
        userMapper.updateByExampleSelective(user, userExample);
        userExample.createCriteria().andUserIdEqualTo(id);
        userMapper.updateByExampleSelective(user, userExample);
        map.put("code", "SUCCESS");

        map.put("user", gson.toJson(userMapper.selectByPrimaryKey(id)));
        map.put("code","SUCCESS");
        return gson.toJson(map);
    }

    @ResponseBody
    @RequestMapping(value = "/getVipInfo", produces = "text/plain;charset=UTF-8")
    public String getVipInfo(@RequestParam("userId") String userId){
        Map<String, String> map = new HashMap<>();

        int id = Integer.parseInt(userId);
        VipCardExample vipCardExample = new VipCardExample();
        vipCardExample.createCriteria().andUserIdEqualTo(id);
        List<VipCard> list = vipCardMapper.selectByExample(vipCardExample);
        if(list.size() == 1){
            map.put("code","SUCCESS");
            map.put("vip",gson.toJson(list.get(0)));
        }else{
            map.put("code","ERROR");
        }
        return gson.toJson(map);
    }
}
