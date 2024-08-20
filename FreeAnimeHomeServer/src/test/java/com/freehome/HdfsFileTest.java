package com.freehome;

import com.freehome.server.IHdfsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

/**
 * @Author：Linzz
 * @Describe:
 * @Date：2024/8/17 23:24
 */
@SpringBootTest
public class HdfsFileTest {
    @Autowired
    private IHdfsService hdfsService;
    @Test
    public void uploadTest() throws Exception {
        String path = "/images";
        boolean isOk = hdfsService.mkdir(path);
        if (isOk)
        {
            String localPath = "C:\\Users\\m1785\\Pictures\\hutao.png";
            hdfsService.uploadFile(localPath,path);
            System.out.println("上传成功，请去HDFS上看文件是否存在。");
        }

    }

    @Test
    public void readPathInfo() throws Exception {
        //读取目录信息
        String path = "/video";
        List<Map<String, Object>> maps = hdfsService.readPathInfo(path);

        System.out.println(maps.toString());

    }

    @Test
    public void renameFile() throws Exception {
        boolean isOk = hdfsService.renameFile("/video", "/videos");
        if (isOk){
            System.out.println("改名成功！");
        }
    }
}
