package com.fct.reggie.controller;

import com.fct.reggie.common.Code;
import com.fct.reggie.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/common")
public class CommonController {

    @Value("${reggie.path}")
    private String basePath;

    /**
     * 文件上传的方法
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        // file是一个临时文件，需要转存到指定位置，否则本次请求完成后临时文件会消失
        log.info(file.toString());

//        获取原始文件名
        String originalFilename = file.getOriginalFilename();

//        使用UUID重新生成文件名，防止文件名称重复造成文件覆盖
        String fileName = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));

//        检查目录是否存在，不存在则创建相应的目录
        File dir = new File(basePath);
        if (!dir.exists()) {
//            目录不存在，创建
            dir.mkdirs();
        }

//        将临时文件存到指定位置
        try {
            file.transferTo(new File(basePath + fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Result.success(Code.LOGIN_OK, fileName);
    }

    /**
     * 图片下载
     *
     * @param name     前端获取图片名
     * @param response
     */
    @GetMapping("/download")
    public void download(String name, HttpServletResponse response) {
        try {
//        输入流，通过流读取文件内容
            FileInputStream fileInputStream = new FileInputStream(new File(basePath + name));
//        输出流，通过输出流将文件写回到浏览器
            ServletOutputStream outputStream = response.getOutputStream();
//        设置返回到浏览器的类型
            response.setContentType("image/jpeg");
            int len = 0;
            byte[] bytes = new byte[1024];
//            将输入流读取到的数据给bytes，长度为len
            while ((len = fileInputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
                outputStream.flush();
            }
//            关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
