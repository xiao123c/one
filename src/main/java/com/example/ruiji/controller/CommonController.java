package com.example.ruiji.controller;

import com.example.ruiji.comment.R;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.UUID;

@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${File.path}")
    private String pathName;

    @PostMapping("/upload")
    public R<String> upload(MultipartFile file) throws IOException {
        //file 产生的是临时文件需要转存到新文件中
        //获取文件名
        String s = file.getOriginalFilename().toString();
        //uuid 获取随机文件名
        String s1 = UUID.randomUUID().toString();
        String fileName=s1+s;
        File ff = new File(pathName);
        //判断文件夹是否存在，不存在就创建
        if(!ff.exists()){
            ff.mkdir();
        }
        //通过IO流的方式转存
        file.transferTo(new File(pathName+fileName));
        return R.success(fileName);
    }
    @GetMapping("/download")
    public void download(String name,HttpServletResponse response) throws IOException {
        FileInputStream fin = new FileInputStream(new File(pathName+name));
        ServletOutputStream slo = response.getOutputStream();
        byte[] by = new byte[1024];
        int len;
        while((len=fin.read(by))!=-1){
            slo.write(by,0,len);
            slo.flush();
        }
        slo.close();
        fin.close();
    }
}
