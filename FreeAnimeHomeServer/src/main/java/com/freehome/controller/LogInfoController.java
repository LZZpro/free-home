package com.freehome.controller;

import com.freehome.api.domain.SysLoginRecord;
import com.freehome.common.core.domain.R;
import com.freehome.server.ILogInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author：Linzz
 * @Describe: 日志记录
 * @Date：2024/8/15 16:35
 */
@RestController
@RequestMapping("/log-record")
public class LogInfoController {

    @Autowired
    private ILogInfoService logInfoService;


    @PostMapping
    public R<?> saveRecord(@RequestBody SysLoginRecord sysLoginRecord) {
        return R.ok(logInfoService.insertLoginInfo(sysLoginRecord),"记录成功！");
    }
}
