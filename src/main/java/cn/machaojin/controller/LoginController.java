package cn.machaojin.controller;

import cn.machaojin.annotation.CodeLog;
import cn.machaojin.annotation.JwtIgnore;
import cn.machaojin.domain.user.*;
import cn.machaojin.dto.LoginTokenDto;
import cn.machaojin.dto.UserDto;
import cn.machaojin.tool.ApiResult;
import cn.machaojin.tool.JwtTokenUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author: Ma, Chaojin(C | TT - 33)
 * @description:
 * @date: 2024/4/9 16:50
 * @version: 1.0
 */
@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {

    @CodeLog
    @JwtIgnore
    @PostMapping("/account")
    public ApiResult login(@RequestBody UserDto user, HttpServletResponse response) {
        String token = JwtTokenUtil.createToken(JSON.toJSONString(user));
        response.setHeader(JwtTokenUtil.AUTH_HEADER_KEY, token);
        log.info("{}", token);
        return ApiResult.success(LoginTokenDto.builder().token(token).build());
    }

    @CodeLog
    @GetMapping("/currentUser")
    public ApiResult getUserInfo() {
        return ApiResult.success(
                User.builder()
                        .name("Ma Chaojin")
                        .avatar("https://gw.alipayobjects.com/zos/antfincdn/XAosXuNZyF/BiazfanxmamNRoxxVxka.png")
                        .userid("00000001")
                        .email("antdesign@alipay.com")
                        .signature("海纳百川，有容乃大")
                        .title("交互专家")
                        .group("蚂蚁金服－某某某事业群－某某平台部－某某技术部－UED")
                        .tags(List.of(
                                Tag.builder().key("0").label("很有想法的").build(),
                                Tag.builder().key("1").label("专注设计").build(),
                                Tag.builder().key("2").label("辣~").build(),
                                Tag.builder().key("3").label("大长腿").build(),
                                Tag.builder().key("4").label("川妹子").build(),
                                Tag.builder().key("5").label("海纳百川").build()
                        ))
                        .notifyCount(12)
                        .unreadCount(11)
                        .country("China")
                        // 这里使用了一个示例值
                        .access("admin")
                        .geographic(Geographic.builder()
                                .province(Province.builder().label("浙江省").key("330000").build())
                                .city(City.builder().label("杭州市").key("330100").build())
                                .build())
                        .address("西湖区工专路 77 号")
                        .phone("0752-268888888")
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
        return ApiResult.success();
    }
}
