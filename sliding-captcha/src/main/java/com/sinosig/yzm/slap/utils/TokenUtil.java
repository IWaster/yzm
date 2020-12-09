package com.sinosig.yzm.slap.utils;

/**
 * @author ouka
 * TokenUtil
 */
public interface TokenUtil {
    
    /**
     * 创建token
     *
     * @return
     */
    static String createToken() {
        long rnd1 = Math.round(Math.random() * 100);
        long rnd2 = Math.round(Math.random() * 100);
        String md5Str1 = Md5Util.md5(rnd1 + "");
        String md5Str2 = Md5Util.md5(rnd2 + "");
        return md5Str1 + md5Str2.substring(0, 2);
    }
    
}
