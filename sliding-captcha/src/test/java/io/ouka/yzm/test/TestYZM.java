package io.ouka.yzm.test;

import com.sinosig.yzm.slap.utils.CaptchaUtil;
import org.junit.Test;

import java.net.URL;

/**
 * @author ouka
 * Test
 */
public class TestYZM {

    @Test
    public void test(){
        CaptchaUtil captchaUtil = new CaptchaUtil();
        
        URL resource = this.getClass().getResource("/image/3f59039d4f4c.png");
        String path = resource.getPath();
        System.out.println(captchaUtil.createCaptcha(path));
    }
}
