package com.freehome.server;

import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @Author：Linzz
 * @Describe:
 * @Date：2024/8/19 12:11
 */
public class ImageReaderMapper extends Mapper<Text, BytesWritable,Text,BytesWritable> {

    protected void map(Text key,BytesWritable value,Context context) throws IOException, InterruptedException {
        //读取图片文件
        Path imagePath = new Path(key.toString());
        FileSystem fs = FileSystem.get(context.getConfiguration());
        FSDataInputStream inputStream = fs.open(imagePath);

        //图片写入context
        byte[] buffer = new byte[(int) fs.getFileStatus(imagePath).getLen()];
        inputStream.readFully(buffer);
        inputStream.close();
        context.write(key,new BytesWritable(buffer));
    }
}
