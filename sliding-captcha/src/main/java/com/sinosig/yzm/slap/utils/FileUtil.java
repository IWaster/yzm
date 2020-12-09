package com.sinosig.yzm.slap.utils;

import org.apache.commons.lang.math.RandomUtils;

import java.io.File;

/**
 * @author ouka
 * FileUtil
 */
class FileUtil {
    
    /**
     * 随机获取背景图片
     *
     * @param captchaPath 验证码背景图保存路径
     * @return
     */
    static File getSourceImage(String captchaPath) {
        String[] list = new File(captchaPath).list();
        String filename = "";
        if (list != null && list.length > 0) {
            filename = list[RandomUtils.nextInt(list.length)];
        }
        return new File(captchaPath + File.separator + filename);
    }
    
}
