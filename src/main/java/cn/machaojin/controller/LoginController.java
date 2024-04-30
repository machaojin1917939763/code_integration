package cn.machaojin.controller;

import cn.machaojin.annotation.CodeLog;
import cn.machaojin.annotation.JwtIgnore;
import cn.machaojin.domain.User;
import cn.machaojin.dto.LoginTokenDto;
import cn.machaojin.dto.UserDto;
import cn.machaojin.tool.ApiResult;
import cn.machaojin.tool.JwtTokenUtil;
import com.alibaba.fastjson.JSON;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * @author: Ma, Chaojin(C | TT - 33)
 * @description:
 * @date: 2024/4/9 16:50
 * @version: 1.0
 */
@Slf4j
@RestController
public class LoginController {

    @CodeLog
    @JwtIgnore
    @PostMapping("/auth/login")
    public ApiResult login(@RequestBody UserDto user, HttpServletResponse response) {
        String token = JwtTokenUtil.createToken(JSON.toJSONString(user));
        response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY, token);
        log.info("{}", token);
        return ApiResult.success(LoginTokenDto.builder().token(token).refreshToken("").build());
    }

    @CodeLog
    @JwtIgnore
    @GetMapping("/auth/getUserInfo")
    public ApiResult getUserInfo() {
        return ApiResult.success(
                User
                        .builder()
                        .userId("1")
                        .userName("ma")
                        .buttons(new ArrayList<>())
                        .roles(new ArrayList<>())
                .build());
    }

    @CodeLog
    @JwtIgnore
    @GetMapping("/error")
    public ApiResult error() {
        log.error("1111");
        return ApiResult.success();
    }
}
