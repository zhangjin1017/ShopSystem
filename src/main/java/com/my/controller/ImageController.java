package com.my.controller;

import com.google.gson.Gson;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
public class ImageController {

    @RequestMapping(value = "/uploadImage", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String uploadImage(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException {

        String result = saveImage(request, file, "business");
        if (result != null) {
            return result;
        }
        return "false";
    }

        @RequestMapping(value = "/uploadGoodsImage", produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String uploadGoodsImage(HttpServletRequest request, @RequestParam("file") MultipartFile file) throws IOException {

        String result = saveImage(request, file, "images");
        if (result != null) {
            return result;
        }
        return "false";
    }


    public String saveImage(HttpServletRequest request, MultipartFile file, String path) throws IOException {
        if (!file.isEmpty()) {
            Map<String, String> map = new HashMap<>();
            System.out.println("文件非空");
            //获取上传文件的保存位置
            String savepath = request.getSession().getServletContext().getRealPath("/" + path + "/");
            System.out.println(savepath);
            //判断该路径是否存在
            File filePath = new File(savepath);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            String oldName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            long size = file.getSize();
            System.out.println("文件名称：" + oldName);
            System.out.println("文件大小：" + size);
            //文件传输
            file.transferTo(new File(filePath, oldName));
            Gson gson = new Gson();
            map.put("code", "000000");
            map.put("imgUrl", "http://127.0.0.1:8080/ShopSystem/"+path+"/" + oldName);
            return gson.toJson(map);
        }
        return null;
    }

}
