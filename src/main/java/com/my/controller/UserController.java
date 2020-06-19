package com.my.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
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
public class UserController {

    Map<String, String> codes = new HashMap<>();


    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "/phone", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String phone(@RequestParam("phone") String phone) {
        Map<String, String> map = new HashMap<>();
        String message = PhoneCode.getPhonemsg(phone);
        map.put("message", message);
        if ("true".equals(message)) {
            System.out.println(PhoneCode.getCode());
            codes.put(phone, PhoneCode.getCode());
            System.out.println(codes.get(phone));
        }
        return new Gson().toJson(map);
    }

    @RequestMapping(value = "/register", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String register(@RequestParam("phone") String phone,
                           @RequestParam("code") String code,
                           @RequestParam("password") String password,
                           @RequestParam("username") String username,
                           @RequestParam("imgData") String imgData) {

        Map<String, String> map = new HashMap();
        UserExample userExample = new UserExample();

        userExample.createCriteria().andPhoneEqualTo(phone);//对比手机号是否重复
        List<User> list1 = userService.selectByExample(userExample);

        userExample = new UserExample();
        userExample.createCriteria().andUsernameEqualTo(username);//判断用户名是否重复
        List<User> list2 = userService.selectByExample(userExample);

        //如果用户名与手机号都没有重复的，执行操作
        String origin_code = codes.get(phone);
        System.out.println(origin_code);

        if (list1.size() == 0 && list2.size() == 0 && origin_code.equals(code)) {
            if (imgData.startsWith("data:image/png;base64,")) {
                imgData = imgData.substring("data:image/png;base64,".length());
            }

            JSONObject jsonObject = JSONObject.parseObject(FaceUtil.add(imgData, username));
            String message = jsonObject.getString("error_msg");
//            System.out.println(message);
            if ("SUCCESS".equals(message)) {
                String faceToken = (String) ((JSONObject) jsonObject.get("result")).get("face_token");
//                System.out.println(faceToken);
                User user = new User(null, username, password, phone, faceToken);
                int line = userService.insertSelective(user);
                if (line > 0) {
                    map.put("message", "true");
                    codes.remove(phone);
                } else {
                    map.put("message", "注册失败！请重试！");
                }
            } else {
                map.put("message", "人脸已经被注册或信息有误！");
            }
        } else {
            if (list1.size() > 0) {
                map.put("message", "用户名已经被注册！");
            } else if (list2.size() > 0) {
                map.put("message", "手机号已经被注册！");
            } else {
                map.put("message", "验证码错误！");
            }
        }
        return new Gson().toJson(map);
    }

    @RequestMapping(value = "/loginByUsername", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String loginByUsername(@RequestParam("username") String username,
                                  @RequestParam("password") String password) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andUsernameEqualTo(username);
        criteria.andPasswordEqualTo(password);
        List<User> list = userService.selectByExample(userExample);
        Map<String, String> map = new HashMap<>();
        if (list.size() == 1) {
            User user = new User();
            User u = list.get(0);
            user.setUsername(u.getUsername());
            user.setPhone(u.getPhone());
            user.setPassword(u.getPassword());
            user.setUserId(u.getUserId());

            map.put("message", "success");
            map.put("user", new Gson().toJson(user));
            return new Gson().toJson(map);
        } else {

            map.put("message", "用户名或密码错误");

            return new Gson().toJson(map);
        }
    }

    @RequestMapping(value = "/loginByFace", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String loginByFace(@RequestParam("image") String image) {
        if (image.startsWith("data:image/png;base64,")) {
            image = image.substring("data:image/png;base64,".length());
        }
        String json = FaceUtil.faceSearch(image);
//        System.out.println(json);
        JSONObject result = (JSONObject) JSONObject.parseObject(json).get("result");
        JSONObject userList = ((JSONObject) ((JSONArray) result.get("user_list")).get(0));
        String score = userList.getString("score");
        String username = userList.getString("user_info");
//        System.out.println(username);
        Map<String, String> map = new HashMap<>();
        if (Double.parseDouble(score) > 60) {
            UserExample userExample = new UserExample();
            UserExample.Criteria criteria = userExample.createCriteria();
            criteria.andUsernameEqualTo(username);
            List<User> list = userService.selectByExample(userExample);
//            System.out.println(list);
            if (list.size() == 1) {
                User u = list.get(0);
                User user = new User();
                user.setUsername(u.getUsername());
                user.setPhone(u.getPhone());
                user.setPassword(u.getPassword());
                user.setUserId(u.getUserId());
                map.put("message", "success");
                map.put("user", new Gson().toJson(user));
                return new Gson().toJson(map);
            }
        }
        map.put("message", "该人脸未注册或人脸信息有误");
        return new Gson().toJson(map);

    }


    @RequestMapping(value = "/loginByPhone", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String loginByPhone(@RequestParam("phone") String phone,
                               @RequestParam("code") String code) {
        UserExample userExample = new UserExample();

        userExample.createCriteria().andPhoneEqualTo(phone);//对比手机号是否重复
        List<User> list1 = userService.selectByExample(userExample);

        String origin_code = codes.get(phone);
        System.out.println(origin_code);
        System.out.println(code);
        Map<String, String> map = new HashMap<>();
        if (origin_code.equals(code) && list1.size() == 1) {

            User user = new User();
            User u = list1.get(0);
            user.setUsername(u.getUsername());
            user.setPhone(u.getPhone());
            user.setPassword(u.getPassword());
            user.setUserId(u.getUserId());
            codes.remove(phone);
            map.put("message", "success");
            map.put("user", new Gson().toJson(user));
            return new Gson().toJson(map);
        }

        map.put("message", "验证码不正确或手机号不存在");
        return new Gson().toJson(map);
    }
}
