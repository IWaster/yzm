package io.ouka.yzm.api;


import com.sinosig.yzm.slap.model.dto.CaptchaDTO;
import com.sinosig.yzm.slap.utils.CaptchaUtil;
import io.ouka.yzm.modul.CheckModul;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ouka
 * TestController
 */
@Controller
@RequestMapping("util/img")
public class TestController {
    private static Map<String,Integer> map = new HashMap<>();

    @RequestMapping("get_img_verify")
    @ResponseBody
    public CaptchaDTO testImg()  {
        CaptchaUtil captchaUtil = new CaptchaUtil();
        String path = this.getClass().getResource("/image/61121eda174c3-3.jpg").getPath();
        CaptchaDTO captcha = null;
        try {
            captcha = captchaUtil.createCaptcha(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put(captcha.getToken(),captcha.getX());
        captcha.setX(null);
        return captcha;

    }


    @PostMapping("check")
    @ResponseBody
    public Boolean javaPost(@RequestBody CheckModul checkModul) {
        Integer integer = map.get(checkModul.getToken());
        System.out.println(integer);
        System.out.println(checkModul.getSliceX());
        if (integer-checkModul.getSliceX()>3||checkModul.getSliceX()-integer>3) {
            return false;
        }
        /*TODO 登录啥的*/
        return true;
    }

}