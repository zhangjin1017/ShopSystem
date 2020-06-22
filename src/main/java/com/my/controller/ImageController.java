package com.my.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

@Controller
public class ImageController {

    @RequestMapping(value = "/uploadImage")
    @ResponseBody
    public String uploadImage(HttpServletRequest request, @RequestParam("file") MultipartFile file){
        if(!file.isEmpty()){
            System.out.println("文件非空");
            //获取上传文件的保存位置
            String savepath = request.getSession().getServletContext().getRealPath("/business/");
            System.out.println(savepath);
            //判断该路径是否存在
            File filePath = new File(savepath);
            if (!filePath.exists()) {
                filePath.mkdirs();
            }
            String oldName=file.getOriginalFilename();
            long size=file.getSize();
            System.out.println("文件名称："+oldName);
            System.out.println("文件大小："+size);
            //文件传输
            try {
                file.transferTo(new File(filePath,oldName));
                return "success";
            } catch (IOException e) {
                return "false";
            }
        }
        return "false";
    }
}
