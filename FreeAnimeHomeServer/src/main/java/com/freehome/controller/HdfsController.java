package com.freehome.controller;

import com.freehome.common.core.domain.R;
import com.freehome.server.IHdfsService;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author：Linzz
 * @Describe: 视频/文件操作
 * @Date：2024/8/19 1:12
 */
@RestController
@RequestMapping
public class HdfsController {

    @Autowired
    private IHdfsService hdfsService;

    @Value("${spring.hadoop.fs-uri}")
    private String hdfsUrl;

    @Autowired
    private FileSystem fileSystem;

    @GetMapping("/video/{videoId}")
    public void videoPlay(HttpServletResponse response,
                          @PathVariable String videoId,
                          @RequestHeader String Range) throws IOException {
       // String rang = "bytes=20-40/20,60-80/20";
        hdfsService.videoPreview(videoId,Range,response);
    }

    /**
     * 在线预览照片
     * @param response
     * @param imagesName
     * @throws IOException
     */
    @GetMapping("/images/{imagesName}")
    public void previewImages(HttpServletResponse response,
                              @PathVariable String imagesName) throws IOException {
        Path imagePath = new Path(hdfsUrl+"/images/"+imagesName);
        FSDataInputStream inputStream = fileSystem.open(imagePath);
        byte[] buffer = new byte[1024];
        int bytesRead = inputStream.read(buffer);
        while (bytesRead>0){
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(buffer,0,bytesRead);
            bytesRead = inputStream.read(buffer);
        }
        inputStream.close();
    }

    @PostMapping("/uploadVideo")
    public R<?> uploadVideo(@RequestParam("video")MultipartFile video) throws IOException {
       hdfsService.uploadVideo(video);
       return R.ok("视频上传成功");
    }

}


