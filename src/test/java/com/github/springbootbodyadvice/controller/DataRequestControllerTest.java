package com.github.springbootbodyadvice.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springbootbodyadvice.pojo.DataDTO;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Base64;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.annotation.Resource;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <p>
 * 创建时间为 下午12:52 2019/11/5
 * 项目名称 spring-boot-body-advice
 * </p>
 *
 * @author 石少东
 * @version 0.0.1
 * @since 0.0.1
 */

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class DataRequestControllerTest {

    @Resource
    private MockMvc mockMvc;

    @Resource
    private ObjectMapper objectMapper;

    @Test
    @SneakyThrows(Exception.class)
    public void getData1() {
        mockMvc.perform(MockMvcRequestBuilders.post("/data1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(getDataDTO(true))))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    @SneakyThrows(Exception.class)
    public void getData2() {
        mockMvc.perform(MockMvcRequestBuilders.post("/data2")
                .contentType(MediaType.APPLICATION_JSON)
                .content(getDataDTO(false)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    /**
     * 对整个数据进行 Base64 加密
     *
     * @return String
     */
    @Contract("_ -> new")
    @NotNull
    @SneakyThrows(JsonProcessingException.class)
    private String getDataDTO(boolean flag) {
        DataDTO dataDTO = new DataDTO();
        dataDTO.setData("123456");
        String data = objectMapper.writeValueAsString(dataDTO);
        if (flag) {
            return new String(Base64.encodeBase64(data.getBytes(), true));
        }
        return data;
    }

}