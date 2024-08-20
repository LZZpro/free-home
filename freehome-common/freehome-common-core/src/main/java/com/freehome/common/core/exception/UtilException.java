package com.freehome.common.core.exception;

import java.io.Serial;

/**
 * @Author：Linzz
 * @Describe: 工具类异常
 * @Date：2024/8/9 22:03
 */
public class UtilException extends RuntimeException
{


    @Serial
    private static final long serialVersionUID = -824666983706626805L;

    public UtilException(){}

    public UtilException(Throwable e)
    {
        super(e.getMessage(), e);
    }

    public UtilException(String message)
    {
        super(message);
    }

    public UtilException(String message, Throwable throwable)
    {
        super(message, throwable);
    }
}
