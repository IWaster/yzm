package com.sinosig.yzm.slap.model.form;

/**
 * @author ouka
 * CaptchaCheckForm
 */
public class CaptchaCheckForm {

    
    /**
     * 注册token
     */
    private String token;
    
    /**
     * 滑动x坐标
     */
    private Integer sliceX;
    
    public CaptchaCheckForm() {
    }
    
    public CaptchaCheckForm(String token, Integer sliceX) {
        this.token = token;
        this.sliceX = sliceX;
    }
    
    @Override
    public String toString() {
        return "JigsawCaptchaCheckForm{" +
                "  token='" + token + '\'' +
                ", sliceX=" + sliceX +
                '}';
    }

    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public Integer getSliceX() {
        return sliceX;
    }
    
    public void setSliceX(Integer sliceX) {
        this.sliceX = sliceX;
    }
}
