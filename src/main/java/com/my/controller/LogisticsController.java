package com.my.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

@Controller
public class LogisticsController {

    @ResponseBody
    @RequestMapping(value = "/getLogistics", produces = "text/plain;charset=UTF-8")
    public String getLogistics(@RequestParam("postId") String postId) throws IOException {
        JSONObject jsonObject1 = JSONObject.parseObject(getExpressInfo(postId));
        JSONObject dataJson = JSONObject.parseObject(jsonObject1.getString("data"));
        JSONArray jsonArray = JSONArray.parseArray(dataJson.getString("messages"));
        JSONArray dataResult = new JSONArray();

        System.out.println();
        if (jsonArray.size() == 0) {
            JSONObject temp = new JSONObject();
            Date date = new Date();//2020-06-16 02:46:42
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String s1 = df.format(date);
            temp.put("context", "查无结果");
            temp.put("time", s1);
            temp.put("ftime", s1);
            dataResult.add(temp);
        }

        for (Iterator iterator = jsonArray.iterator(); iterator.hasNext(); ) {
            JSONObject one = (JSONObject) iterator.next();
            JSONObject temp = new JSONObject();
            temp.put("context", one.getString("context"));
            temp.put("time", one.getString("time"));
            temp.put("ftime", one.getString("time"));
            dataResult.add(temp);
        }
        JSONObject result = new JSONObject();
        result.put("status", 200);
        result.put("message", "ok");
        result.put("data", dataResult);
        System.out.println(dataResult);
        return result + "";
    }

    public String getExpressInfo(String postId) throws IOException {
        String result = null;
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = "https://api.m.sm.cn/rest?method=kuaidi.getdata&sc=express_cainiao&q=%E5%BF%AB%E9%80%92" + postId + "&callback=jsonp2";
        //创建httpGet
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64; rv:50.0) Gecko/20100101 Firefox/50.0");
        //http响应
        CloseableHttpResponse response = null;
        try {
            //执行请求
            response = httpClient.execute(httpGet);
            //判断返回状态是否ok
            if (response.getStatusLine().getStatusCode() == 200) {
                //获取实体
                HttpEntity entity = response.getEntity();
                //获取内容
                String content = EntityUtils.toString(entity, "utf-8");
                result = content.replace("jsonp2(", "").replace(");", "");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            httpClient.close();
        }
        return result;
    }
}
