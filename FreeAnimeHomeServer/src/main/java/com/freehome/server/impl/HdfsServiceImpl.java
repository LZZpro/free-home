package com.freehome.server.impl;

import com.freehome.common.core.domain.R;
import com.freehome.common.core.exception.HdfsFileException;
import com.freehome.common.core.utils.ServletUtils;
import com.freehome.common.core.utils.StringUtils;
import com.freehome.security.utils.VideoIdGeneratorUtil;
import com.freehome.server.IHdfsService;
import com.freehome.server.ImageReaderMapper;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.SequenceFileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author：Linzz
 * @Describe:
 * @Date：2024/8/17 13:08
 */
@Service
public class HdfsServiceImpl implements IHdfsService {


    @Value("${spring.hadoop.fs-uri}")
    private String hdfsUrl;

    @Autowired
    private FileSystem fileSystem;

    private final int bufferSize = 1024 * 1024 * 64;

    /**
     * 检验路径是否有效
     * @param path
     */
    private void checkPathValidate(String path)  {
        try{
            if (StringUtils.isEmpty(path) || !existFile(path))
            {
                throw new HdfsFileException("输入路径不存在/不合法");
            }
        }catch (Exception e){
            throw new HdfsFileException("输入路径不存在/不合法",e);
        }
    }



    /**
     * 在HDFS创建文件夹
     *
     * @param path
     * @return
     * @throws Exception
     */
    @Override
    public boolean mkdir(String path) throws Exception {
        if (StringUtils.isEmpty(path))
            return false;
        if (existFile(path))
            return true;
        //FileSystem fs = fileSystem;
        //目标路径
        Path targetPath = new Path(path);
        return fileSystem.mkdirs(targetPath);
    }

    /**
     * 判断文件是否存在
     *
     * @param path
     * @return
     * @throws Exception
     */
    @Override
    public boolean existFile(String path) throws Exception {
        if (StringUtils.isEmpty(path))
            return false;
        Path srcPath = new Path(path);
        return fileSystem.exists(srcPath);
    }

    /**
     * 读取目录信息
     *
     * @param path
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, Object>> readPathInfo(String path) throws Exception {
        checkPathValidate(path);
        Path newPath = new Path(path);
        FileStatus[] statusList = fileSystem.listStatus(newPath);
        List<Map<String, Object>> list = new ArrayList<>();
        if (statusList != null && statusList.length > 0) {
            for (FileStatus fileStatus : statusList) {
                HashMap<String, Object> map = new HashMap<>(2);
                map.put("filePath", fileStatus.getPath());
                map.put("fileStatus", fileStatus.toString());
                list.add(map);
            }
            return list;
        }
        return null;
    }

    /**
     * 创建文件
     *
     * @param path
     * @param file
     * @throws Exception
     */
    @Override
    public void createFile(String path, MultipartFile file) throws Exception {

        if (StringUtils.isEmpty(path) || null == file.getBytes())
            return;
        String fileName = file.getOriginalFilename();
        //上传时默认当前目录，后面自动拼接文件的目录
        Path newPath = new Path(path + "/" + fileName);
        //打开一个输出流
        FSDataOutputStream outputStream = fileSystem.create(newPath);
        outputStream.write(file.getBytes());
        outputStream.close();
    }

    /**
     * 读取文件内容
     *
     * @param path
     * @return
     * @throws Exception
     */
    @Override
    public String readFile(String path) throws Exception {
        checkPathValidate(path);
        Path srcPath = new Path(path);
        FSDataInputStream inputStream = null;
        try {
            inputStream = fileSystem.open(srcPath);
            //防止中文乱码
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String lineTxt = "";
            StringBuffer sb = new StringBuffer();
            while ((lineTxt = reader.readLine()) != null) {
                sb.append(lineTxt);
            }
            return sb.toString();
        } finally {
            if (inputStream != null)
                inputStream.close();
        }
    }

    /**
     * 读取文件列表
     * @param path
     * @return
     * @throws Exception
     */
    @Override
    public List<Map<String, String>> listFile(String path) throws Exception {
        checkPathValidate(path);
        Path srcPath = new Path(path);
        //递归到所有文件
        RemoteIterator<LocatedFileStatus> filesList = fileSystem.listFiles(srcPath, true);
        List<Map<String,String>> returnList = new ArrayList<>();
        while (filesList.hasNext()){
            LocatedFileStatus next = filesList.next();
            String fileName = next.getPath().getName();
            Path filePath = next.getPath();
            Map<String,String> map = new HashMap<>(2);
            map.put("fileName",fileName);
            map.put("filePath",filePath.toString());
            returnList.add(map);

        }
        return returnList;
    }

    /**
     * 重命名文件
     * @param oldName
     * @param newName
     * @return
     * @throws Exception
     */
    @Override
    public boolean renameFile(String oldName, String newName) throws Exception {
        if (StringUtils.isEmpty(oldName) || StringUtils.isEmpty(newName))
           return false;
        Path oldPath = new Path(oldName);
        Path newPath = new Path(newName);
        return fileSystem.rename(oldPath,newPath);
    }

    /**
     * 删除文件
     * @param path
     * @return
     * @throws Exception
     */
    @Override
    public boolean deleteFile(String path) throws Exception {
        checkPathValidate(path);
        Path srcPath = new Path(path);
        return fileSystem.delete(srcPath,true);
    }

    /**
     * 上传文件
     * @param path
     * @param uploadPath
     * @throws Exception
     */
    @Override
    public void uploadFile(String path, String uploadPath) throws Exception {
         if (StringUtils.isEmpty(path) || StringUtils.isEmpty(uploadPath))
             return;
         //上传路径
         Path clientPath = new Path(path);
         //目标路径
        Path targetPath = new Path(uploadPath);
        // 调用文件系统的文件复制方法，第一个参数是否删除原文件true为删除，默认为false
        fileSystem.copyFromLocalFile(false,clientPath,targetPath);
    }

    /**
     * 下载文件
     * @param path
     * @param downloadPath
     * @throws Exception
     */
    @Override
    public void downloadFile(String path, String downloadPath) throws Exception {
        if (StringUtils.isEmpty(path) || StringUtils.isEmpty(downloadPath))
            return;
        //上传路径
        Path clientPath = new Path(path);
        //目标路径
        Path targetPath = new Path(downloadPath);
        // 调用文件系统的文件复制方法，第一个参数是否删除原文件true为删除，默认为false
        fileSystem.copyToLocalFile(false,clientPath,targetPath);
    }

    /**
     * 文件复制
     * @param sourcePath
     * @param targetPath
     * @throws Exception
     */
    @Override
    public void copyFile(String sourcePath, String targetPath) throws Exception {
        if (StringUtils.isEmpty(sourcePath) || StringUtils.isEmpty(targetPath))
            return;
        //原始文件路径
        Path oldPath = new Path(sourcePath);
        //目标路径
        Path newPath = new Path(targetPath);
        FSDataInputStream inputStream=null;
        FSDataOutputStream outputStream = null;
        try{
            inputStream = fileSystem.open(oldPath);
            outputStream = fileSystem.create(newPath);
            IOUtils.copyBytes(inputStream,outputStream,bufferSize,false);
        }finally {
            if (inputStream !=null)
                inputStream.close();
            if (outputStream != null)
                outputStream.close();
        }
    }

    /**
     * 打开HDFS上的文件并返回byte数组  /有问题
     * @param path
     * @return
     * @throws Exception
     */
    @Override
    public byte[] openFileToBytes(String path) throws Exception {
        checkPathValidate(path);
        //目标路径
        Path srcPath = new Path(path);
        FSDataInputStream inputStream = fileSystem.open(srcPath);
        return IOUtils.readFullyToByteArray(inputStream);

    }

    /**
     * 打开HDFS上的文件并返回java对象  /不可用
     * @param path
     * @param clazz
     * @return
     * @param <T>
     * @throws Exception
     */
    @Override
    public <T> T openFileToObject(String path, Class<T> clazz) throws Exception {
        checkPathValidate(path);
        String jsonStr = readFile(path);
//		return JsonUtil.fromObject(jsonStr, clazz);
        return null;
    }

    /**
     * 获取文件在HDFS集群的位置
     * @param path
     * @return
     * @throws Exception
     */
    @Override
    public BlockLocation[] getFileBlockLocations(String path) throws Exception {
        checkPathValidate(path);
        // 目标路径
        Path srcPath = new Path(path);
        FileStatus fileStatus = fileSystem.getFileStatus(srcPath);
        return fileSystem.getFileBlockLocations(fileStatus, 0, fileStatus.getLen());
    }

    /**
     * 视频播放处理 https://www.cnblogs.com/yanch01/p/14585297.html
     * @param videoId
     * @param range @RequestHeader String Rang
     * @param response
     * @throws IOException
     */
    @Override
    public void videoPreview(String videoId, String range, HttpServletResponse response) throws IOException {
        response.reset();
        Path srcPath = new Path("/videos/" + videoId);
        FSDataInputStream inputStream = fileSystem.open(srcPath);

        // 每次返回的最大数据块大小
        long partLen = 1024 * 1024 * 2;
        byte[] buffer = new byte[(int) partLen];

        // 计算本次返回的数据范围 [start, end]
        long fileLen = fileSystem.getFileStatus(srcPath).getLen();
//        long start = Long.parseLong(range.substring(range.indexOf("=") + 1, range.indexOf("-")));
//        long end = Math.min(start + partLen - 1, fileLen-1);
        long start = 0;
        long end;
        if (range != null && range.startsWith("bytes=")) {
            String[] ranges = range.substring(6).split("-");
            try {
                start = Long.parseLong(ranges[0]);
                if (ranges.length > 1) {
                    end = Long.parseLong(ranges[1]);
                } else {
                    end = Math.min(start + partLen - 1, fileLen - 1);
                }
            } catch (NumberFormatException e) {
                // 默认的start和end值如果解析错误
                start = 0;
                end = Math.min(partLen - 1, fileLen - 1);
            }
        } else {
            end = Math.min(partLen - 1, fileLen - 1);
        }
        // 确保end值不超过文件大小
        if (end >= fileLen) {
            end = fileLen - 1;
        }
        //返回码需要为206，代表只处理了部分请求，响应了部分数据
        response.setStatus(HttpServletResponse.SC_PARTIAL_CONTENT);
        response.setContentType("video/mp4");
        //设置此次相应返回的数据长度
        response.setHeader("Content-Length", String.valueOf(fileLen));
        //设置此次相应返回的数据范围
        response.setHeader("Content-Range", "bytes " + start + "-" + end + "/" + fileLen);
        response.setHeader("Accept-Ranges", "bytes");
        // 随机寻址 定位数据块头
        inputStream.seek(start);
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        // 读取数据到缓冲区
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1){
            // 写入数据到OutputStream
             out.write(buffer, 0, bytesRead);
        }
        out.flush();
        out.close();
        inputStream.close();
    }

    @Override
    public void getImage(String imagePath) throws IOException, InterruptedException, ClassNotFoundException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf,"Image Reader");
        //设置MapReduce程序的输入和输出路径
        job.setInputFormatClass(SequenceFileInputFormat.class);
        SequenceFileInputFormat.addInputPath(job,new Path(imagePath));
        FileOutputFormat.setOutputPath(job,new Path("/images/out"));
        //设置MapReduce程序的Mapper和Reduce类
        job.setJarByClass(IHdfsService.class);
        job.setMapperClass(ImageReaderMapper.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(BytesWritable.class);
        //提交MapReduce程序并等待完成
        job.waitForCompletion(true);
    }

    /**
     * 视频上传
     * @param video
     * @throws IOException
     */
    @Override
    public void uploadVideo(MultipartFile video) throws IOException
    {
        if (video.isEmpty())
            throw new HdfsFileException("不能上传空文件！");

        String videoSuffix = video.getOriginalFilename()
                .substring(video.getOriginalFilename().lastIndexOf("."));
        String videoId = VideoIdGeneratorUtil.getVideoId();
        String hdfsPath = "/videos/" + videoId + videoSuffix;
        FSDataOutputStream os = fileSystem.create(new Path(hdfsPath));
        // 将上传的文件写入 HDFS
        os.write(video.getBytes());
        os.close();
    }

}
