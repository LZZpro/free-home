package com.freehome.server;

import org.apache.hadoop.fs.BlockLocation;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @Author：Linzz
 * @Describe:
 * @Date：2024/8/17 13:04
 */
public interface IHdfsService {

    boolean mkdir(String path) throws Exception;

    boolean existFile(String path) throws Exception;

    List<Map<String, Object>> readPathInfo(String path) throws Exception;

    void createFile(String path, MultipartFile file) throws Exception;

    String readFile(String path) throws Exception;

    List<Map<String, String>> listFile(String path) throws Exception;

    boolean renameFile(String oldName, String newName) throws Exception;

    boolean deleteFile(String path) throws Exception;

    void uploadFile(String path, String uploadPath) throws Exception;

    void downloadFile(String path, String downloadPath) throws Exception;

    void copyFile(String sourcePath, String targetPath) throws Exception;

    byte[] openFileToBytes(String path) throws Exception;

    <T extends Object> T openFileToObject(String path, Class<T> clazz) throws Exception;

    BlockLocation[] getFileBlockLocations(String path) throws Exception;

    void videoPreview(String videoId, String range, HttpServletResponse response) throws IOException;

    void getImage(String imagePath) throws IOException, InterruptedException, ClassNotFoundException;

    void uploadVideo(MultipartFile video) throws IOException;
}
