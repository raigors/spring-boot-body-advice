package com.github.springbootbodyadvice.controller;

import com.github.springbootbodyadvice.annotation.Encrypt;
import com.github.springbootbodyadvice.pojo.DataVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * <p>
 * 创建时间为 下午10:09 2019/11/4
 * 项目名称 spring-boot-body-advice
 * </p>
 *
 * @author 石少东
 * @version 0.0.1
 * @since 0.0.1
 */
@RestController
public class DataResponseController {

    @Encrypt
    @GetMapping("/data1")
    public DataVO getData1() {
        return DataVO.builder().data("567890").build();
    }

    @GetMapping("/data2")
    public DataVO getData2() {
        return DataVO.builder().data(new Date().toString()).build();
    }

}
