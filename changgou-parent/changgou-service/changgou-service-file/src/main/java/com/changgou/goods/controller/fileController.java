package com.changgou.goods.controller;

import com.changgou.goods.file.FastDFSFile;
import com.changgou.goods.util.FastDFSUtil;
import entity.Result;
import entity.StatusCode;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
@CrossOrigin
public class fileController {
    @PostMapping
    public Result uploadFile(@RequestParam(value = "file")MultipartFile file) throws Exception {
        //调用String Utils工具类，将文件传入FastDF中
        FastDFSFile fastDFSFile = new FastDFSFile(
                file.getName(), //文件名字，1.jpg
                file.getBytes(), ///文件字节数组
                StringUtils.getFilenameExtension(file.getOriginalFilename()) //获取文件扩展名
        );
        String[] upload = FastDFSUtil.upload(fastDFSFile);
        //拼接路径
        String url=FastDFSUtil.getTrackerInfo()+"/"+upload[0]+"/"+upload[1];
        return new Result(true, StatusCode.OK,"文件上传成功",url);
    }
}
