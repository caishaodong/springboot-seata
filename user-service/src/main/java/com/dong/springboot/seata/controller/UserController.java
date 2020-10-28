package com.dong.springboot.seata.controller;

import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @Author caishaodong
 * @Date 2020-10-28 16:38
 * @Description
 **/
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("account/reduce")
    @GlobalTransactional(rollbackFor = Exception.class)
    public String reduceAccount() {
        jdbcTemplate.update("update t_user set account = account - 1 where id = 1");

        restTemplate.getForEntity("http://localhost:8081/good-service/good/amount/reduce", String.class);
        return "success";
    }
}
