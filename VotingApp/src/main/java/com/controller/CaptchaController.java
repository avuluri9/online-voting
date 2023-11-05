package com.controller;

import cn.apiclub.captcha.Captcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.service.CaptchaService;

import java.io.IOException;

@RestController
@RequestMapping("/captcha")
public class CaptchaController {

    @Autowired
    private CaptchaService captchaService;

    @GetMapping("/generate")
    public @ResponseBody byte[] generateCaptcha() throws IOException {
        Captcha captcha = captchaService.generateCaptcha();
        return captchaService.getCaptchaImageBytes(captcha);
    }
}

