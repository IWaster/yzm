package com.sinosig.yzm.slap.model.dto;

/**
 * @author ouka
 * CaptchaDTO
 */
public class CaptchaDTO {
    
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

    private Integer x;

    public CaptchaDTO() {
    }

    public CaptchaDTO(String sliceImg, String bgImg, String token, Integer y, Integer x) {
        this.sliceImg = sliceImg;
        this.bgImg = bgImg;
        this.token = token;
        this.y = y;
        this.x = x;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return "CaptchaDTO{" +
                "sliceImg='" + sliceImg + '\'' +
                ", bgImg='" + bgImg + '\'' +
                ", token='" + token + '\'' +
                ", y=" + y +
                ", x=" + x +
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
