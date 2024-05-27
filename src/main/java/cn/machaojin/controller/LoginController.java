package cn.machaojin.controller;

import cn.machaojin.annotation.CodeLog;
import cn.machaojin.annotation.JwtIgnore;
import cn.machaojin.config.SonarQubeConfig;
import cn.machaojin.domain.Developer;
import cn.machaojin.domain.user.*;
import cn.machaojin.dto.LoginTokenDto;
import cn.machaojin.dto.UserDto;
import cn.machaojin.service.DeveloperService;
import cn.machaojin.tool.ApiResult;
import cn.machaojin.tool.JwtTokenUtil;
import cn.machaojin.tool.RedisUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static cn.machaojin.constants.UserConstant.USER_LOGIN;

/**
 * @author: Ma, Chaojin(C | TT - 33)
 * @description:
 * @date: 2024/4/9 16:50
 * @version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final DeveloperService developerService;
    private final SonarQubeConfig sonarQubeConfig;
    private final RedisUtil redisUtil;

    @CodeLog
    @JwtIgnore
    @PostMapping("/account")
    public ApiResult login(@RequestBody UserDto user, HttpServletResponse response) {
        if (user.getUsername().equals(sonarQubeConfig.getUsername()) && user.getPassword().equals(sonarQubeConfig.getPassword())) {
            String token = JwtTokenUtil.createToken(JSON.toJSONString(user));
            response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY, token);
            log.info("{}", token);
            redisUtil.set(USER_LOGIN, user.getUsername());
            return ApiResult.success(LoginTokenDto.builder().token(token).build());
        }
        Developer serviceOne = developerService.getOne(new LambdaQueryWrapper<>(Developer.class).eq(Developer::getName, user.getUsername()).eq(Developer::getPassword, user.getPassword()));
        if (serviceOne != null) {
            String token = JwtTokenUtil.createToken(JSON.toJSONString(user));
            response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY, token);
            serviceOne.setLoginStatus("已登录");
            developerService.updateById(serviceOne);
            redisUtil.set(USER_LOGIN, user.getUsername());
            return ApiResult.success(LoginTokenDto.builder().token(token).build());
        }
        return ApiResult.error("用户名或者密码错误");
    }

    @CodeLog
    @GetMapping("/currentUser")
    public ApiResult getUserInfo() {
        //获取最新的用户
        List<Developer> developers = developerService.list(new LambdaQueryWrapper<>(Developer.class).eq(Developer::getLoginStatus, "已登录"));
        if (developers == null || developers.isEmpty()) {
            return ApiResult.success(
                    User.builder()
                            .name("Ma Chaojin")
                            .avatar("https://message-stack.oss-cn-beijing.aliyuncs.com/images/%E5%A4%B4%E5%83%8F.jpg")
                            .userid("")
                            .email("1917939763@qq.com")
                            .build());
        }
        developers.sort((a, b) -> b.getUpdatedAt().compareTo(a.getUpdatedAt()));
        Developer developer = developers.get(0);
        return ApiResult.success(
                User.builder()
                        .name(developer.getName())
                        .avatar(developer.getAvatar())
                        .userid(developer.getId().toString())
                        .email(developer.getEmail())
                        .build());
    }

    @CodeLog
    @JwtIgnore
    @GetMapping("/error")
    public ApiResult error() {
        return ApiResult.success();
    }

    @CodeLog
    @JwtIgnore
    @PostMapping("/outLogin")
    public ApiResult outLogin() {
        List<Developer> list = developerService.list();
        for (Developer developer : list) {
            developer.setLoginStatus("未登录");
            developerService.updateById(developer);
        }
        redisUtil.delete(USER_LOGIN);
        return ApiResult.success();
    }

    @CodeLog
    @JwtIgnore
    @GetMapping("/admin")
    public ApiResult getAdmin() {
        return ApiResult.success(
                User.builder()
                        .name("Ma Chaojin")
                        .avatar("https://message-stack.oss-cn-beijing.aliyuncs.com/images/%E5%A4%B4%E5%83%8F.jpg")
                        .userid("")
                        .email("1917939763@qq.com")
                        .build());
    }
}
