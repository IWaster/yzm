package com.sinosig.yzm.slap.enums;

/**
 * @author ouka
 * CaptchaEnum
 */
public enum CaptchaEnum {
    
    /**
     * 拼图验证码注册TOKEN非法
     */
    JIGSAW_CAPTCHA_TOKEN_INVALID(9999999, "验证失败，请刷新验证码！"),
    
    /**
     * 拼图验证码滑动距离错误
     */
    JIGSAW_CAPTCHA_SLIDE_FAILED(8888888, "验证失败，请重试！"),
    
    /**
     * 生成验证码出错
     */
    JIGSAW_CAPTCHA_ERROR(7777777, "获取验证码错误，请刷新验证码！"),
    ;
    
    /**
     * 错误码
     */
    private int code;
    
    /**
     * 错误信息
     */
    private String msg;
    
    public String msg() {
        return msg;
    }
    
    public int code() {
        return code;
    }
    
    CaptchaEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
