package com.github.springbootbodyadvice.controller;

import com.github.springbootbodyadvice.annotation.Decrypt;
import com.github.springbootbodyadvice.pojo.DataDTO;
import com.github.springbootbodyadvice.pojo.DataVO;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

@Slf4j
@RestController
public class DataRequestController {

    @PostMapping("/data1")
    public DataVO getData1(@Decrypt @NotNull @RequestBody DataDTO dataDTO) {
        log.info("dataDTO.toString()");
        return DataVO.builder().data(dataDTO.toString()).build();
    }

    @PostMapping("/data2")
    public DataVO getData2(@NotNull @RequestBody DataDTO dataDTO) {
        int a = 1 / 0;
        return DataVO.builder().data(dataDTO.toString()).build();
    }

}
