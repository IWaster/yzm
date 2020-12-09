package com.sinosig.yzm.slap.constant;

import java.awt.*;

/**
 * @author ouka
 * CaptchaEnum
 */
public class ConfigConstant {
    
    /**
     * 小图的宽 (SQUARE_W + CIRCLE_R * 2 + LIGHT * 2)
     */
    public static final int SMALL_IMG_W = 64;
    
    /**
     * 小图的高 (SQUARE_H + CIRCLE_R * 2 + LIGHT * 2)
     */
    public static final int SMALL_IMG_H = 64;
    
    /**
     * 正方形的宽
     */
    public static final int SQUARE_W = 40;
    
    /**
     * 正方形的高
     */
    public static final int SQUARE_H = 40;
    
    /**
     * 小图突出圆的直径 (CIRCLE_D * 2)
     */
    public static final int CIRCLE_D = 16;
    
    /**
     * 小图突出圆的半径
     */
    public static final int CIRCLE_R = 8;
    
    /**
     * 小图阴影宽度
     */
    public static final int SHADOW = 4;
    
    /**
     * 小图边缘高亮宽度
     */
    public static final int LIGHT = 4;
    
    /**
     * 小图边缘高亮颜色
     */
    public static final Color CIRCLE_GLOW_I_H = new Color(253, 239, 175, 148);
    public static final Color CIRCLE_GLOW_I_L = new Color(255, 209, 0);
    public static final Color CIRCLE_GLOW_O_H = new Color(253, 239, 175, 124);
    public static final Color CIRCLE_GLOW_O_L = new Color(255, 179, 0);
    
}
