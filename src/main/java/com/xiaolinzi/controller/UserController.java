package com.xiaolinzi.controller;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * 传统的文件上传方式
     * @return
     */
    @RequestMapping("/fileupload1")
    public String fileupload1(HttpServletRequest request) throws Exception {
        System.out.println("文件上传……");
        //使用fileuoload组件完成上传
        //上传的位置
        String path = request.getSession().getServletContext().getRealPath("/uploads");
        File file=new File(path);
        //判断该文件是否存在
        if(!file.exists()){
            file.mkdirs();
        }
        //解析request对象，获取文件项
        //创建一个磁盘文件工厂
        DiskFileItemFactory factory=new DiskFileItemFactory();
        ServletFileUpload upload=new ServletFileUpload(factory);
        //解析request
        List<FileItem> items = upload.parseRequest(request);
        //遍历
        for(FileItem item:items){
            if(item.isFormField()){
                //普通表单项
            }else{
                //说明上传文件项
                //获取文件名称
                String fileName = item.getName();
                String uuid = UUID.randomUUID().toString().replace("-", "");
                fileName=uuid+"_"+fileName;
                //完成文件上传
                item.write(new File(path,fileName));
                //删除临时文件
                item.delete();
            }
        }
        return "success";
    }
    /**
     * springmvc的文件上传方式
     * @return
     */
    @RequestMapping("/fileupload2")
    public String fileupload2(HttpServletRequest request, MultipartFile upload) throws Exception {
        System.out.println("springmvc方式文件上传……");
        //使用fileuoload组件完成上传
        //上传的位置
        String path = request.getSession().getServletContext().getRealPath("/uploads");
        File file=new File(path);
        //判断该文件是否存在
        if(!file.exists()){
            file.mkdirs();
        }
        //说明上传文件项
        //获取文件名称
        String filename = upload.getOriginalFilename();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        filename=uuid+"_"+filename;
        //完成文件上传
        upload.transferTo(new File(path,filename));
        return "success";
    }
}
