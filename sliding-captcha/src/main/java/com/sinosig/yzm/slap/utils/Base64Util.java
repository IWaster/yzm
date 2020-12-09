package com.sinosig.yzm.slap.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author ouka
 * Base64Util
 */
class Base64Util {
    
    private static final Logger log = LoggerFactory.getLogger(Base64Util.class);
    
    /**
     * 把图片转换为base64
     *
     * @param image      图片
     * @param doCompress 是否压缩图片
     * @return
     */
    static String getImageBase64(BufferedImage image, boolean doCompress) {
        byte[] bytes;
        if (doCompress) {
            bytes = ImageUtil.compressImg(image);
            if (bytes == null) {
                return "";
            }
        } else {
            ByteArrayOutputStream bao = new ByteArrayOutputStream();
            try {
                ImageIO.write(image, "png", bao);
            } catch (IOException e) {
                log.error("压缩图片出错", e);
            }
            bytes = bao.toByteArray();
        }
        return new BASE64Encoder().encodeBuffer(bytes).trim().replaceAll("\r|\n", "");
    }
    
    
}
