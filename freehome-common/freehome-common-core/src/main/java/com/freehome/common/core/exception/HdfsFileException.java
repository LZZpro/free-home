package com.freehome.common.core.exception;

import java.io.Serial;

/**
 * @Author：Linzz
 * @Describe:
 * @Date：2024/8/17 21:37
 */
public class HdfsFileException extends RuntimeException{

    @Serial
    private static final long serialVersionUID = 8260258851855607798L;

    /**
     * 空构造方法，避免反序列化问题
     */
    public HdfsFileException(){}

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    public HdfsFileException(String message)
    {
        super(message);
    }

    public HdfsFileException(String message,Integer code)
    {
        this.message = message;
        this.code = code;
    }

    public Integer getCode()
    {
        return code;
    }

    public HdfsFileException setMessage(String message)
    {
        this.message = message;
        return this;
    }

    public HdfsFileException(String message,Throwable throwable)
    {
        super(message,throwable);
    }

}
