package com.sinosig.yzm.slap.model.vo;

/**
 * @author ouka
 * CaptchaVO
 */
public class CaptchaVO {
    
    /**
     * 滑动拼图块
     */
    private String sliceImg;
    
    /**
     * 背景图
     */
    private String bgImg;
    
    /**
     * 注册token
     */
    private String token;
    
    /**
     * 拼图所在y坐标
     */
    private Integer y;
    
    public CaptchaVO() {
    }
    
    public CaptchaVO(String sliceImg, String bgImg, String token, Integer y) {
        this.sliceImg = sliceImg;
        this.bgImg = bgImg;
        this.token = token;
        this.y = y;
    }
    
    @Override
    public String toString() {
        return "JigsawCaptchaVO{" +
                "sliceImg='" + sliceImg + '\'' +
                ", bgImg='" + bgImg + '\'' +
                ", token='" + token + '\'' +
                ", y=" + y +
                '}';
    }
    
    public String getSliceImg() {
        return sliceImg;
    }
    
    public void setSliceImg(String sliceImg) {
        this.sliceImg = sliceImg;
    }
    
    public String getBgImg() {
        return bgImg;
    }
    
    public void setBgImg(String bgImg) {
        this.bgImg = bgImg;
    }
    
    public String getToken() {
        return token;
    }
    
    public void setToken(String token) {
        this.token = token;
    }
    
    public Integer getY() {
        return y;
    }
    
    public void setY(Integer y) {
        this.y = y;
    }
}
