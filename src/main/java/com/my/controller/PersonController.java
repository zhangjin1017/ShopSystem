package com.my.controller;

import com.google.gson.Gson;
import com.my.pojo.*;
import com.my.service.PersonService;
import com.my.service.UserService;
import com.my.service.VipCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/person")
public class PersonController {

    PersonService personService;
    Gson gson = new Gson();

    @Autowired
    public void setPersonService(PersonService personService) {
        this.personService = personService;
    }

    UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    VipCardService vipCardService;

    @Autowired
    public void setVipCardService(VipCardService vipCardService) {
        this.vipCardService = vipCardService;
    }

    @RequestMapping(value = "/getInfo", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String getInfo(@RequestParam("userId") String userId) {
        Map<String, String> map = new HashMap<>();
        if (userId != null) {
            int id = Integer.parseInt(userId);
            PersonExample personExample = new PersonExample();
            personExample.createCriteria().andUserIdEqualTo(id);
            List<Person> list = personService.selectByExample(personExample);
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
                             @RequestParam("birth") String birth) {
        Map<String, String> map = new HashMap<>();
        System.out.println("userId"+userId);
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
        personService.updateByExampleSelective(person, personExample);

        map.put("code", "SUCCESS");
        map.put("user", gson.toJson(userService.selectByPrimaryKey(id)));
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
        userService.updateByExampleSelective(user, userExample);
//        map.put("code", "SUCCESS");

        map.put("user", gson.toJson(userService.selectByPrimaryKey(id)));
        map.put("code", "SUCCESS");
        return gson.toJson(map);
    }

    @ResponseBody
    @RequestMapping(value = "/getVipInfo", produces = "text/plain;charset=UTF-8")
    public String getVipInfo(@RequestParam("userId") String userId) {
        Map<String, String> map = new HashMap<>();

        int id = Integer.parseInt(userId);
        VipCardExample vipCardExample = new VipCardExample();
        vipCardExample.createCriteria().andUserIdEqualTo(id);
        List<VipCard> list = vipCardService.selectByExample(vipCardExample);
        if (list.size() == 1) {
            map.put("code", "SUCCESS");
            map.put("vip", gson.toJson(list.get(0)));
        } else {
            map.put("code", "ERROR");
        }
        return gson.toJson(map);
    }

    @ResponseBody
    @RequestMapping(value = "/toBeVip", produces = "text/plain;charset=UTF-8")
    public String toBeVip(@RequestParam("userId") String userId) {
        Map<String, String> map = new HashMap<>();
        int id = Integer.parseInt(userId);
        int cardId = (int) (Math.random() * 100000000 + 1);
//        System.out.println(cardId);
        int line = vipCardService.insert(new VipCard(cardId + "", id, 0.0));
        if (line == 1) {
            map.put("code", "SUCCESS");
        } else {
            map.put("code", "ERROR");
        }
        return gson.toJson(map);
    }
}
