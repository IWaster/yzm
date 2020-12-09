package com.sinosig.yzm.slap.utils;

import com.sinosig.yzm.slap.constant.ConfigConstant;
import com.sinosig.yzm.slap.model.dto.CaptchaDTO;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author ouka
 * CaptchaUtil
 */
public class CaptchaUtil {
    
    private static final Logger log = LoggerFactory.getLogger(CaptchaUtil.class);
    
    /**
     * 创建验证码
     *
     * @param captchaPath 验证码背景图保存路径
     * @return
     */
    public CaptchaDTO createCaptcha(String captchaPath) {
        File sourceFile = FileUtil.getSourceImage(captchaPath);
        // 原图图层
        BufferedImage sourceImg;
        try {
            sourceImg = ImageIO.read(sourceFile);
        } catch (IOException e) {
            log.error("读取验证码背景图出错", e);
            return null;
        }
        
        // 生成随机坐标
        Random random = new Random();
        // 滑动拼图x坐标范围为 [(0+40),(260-40)]，y坐标范围为 [0,(160-40))
        int x = random.nextInt(sourceImg.getWidth() - 2 * ConfigConstant.SMALL_IMG_W) + ConfigConstant.SMALL_IMG_W;
        int y = random.nextInt(sourceImg.getHeight() - ConfigConstant.SMALL_IMG_H);
        log.info("滑动拼图坐标为({},{})", x, y);
        
        // 小图图层
        BufferedImage smallImg;
        try {
            smallImg = ImageUtil.cutSmallImg(sourceFile, x, y);
        } catch (IOException e) {
            log.error("创建验证码出错", e);
            return null;
        }
        // 创建shape区域
        List<Shape> shapes = createSmallShape();
        // 创建用于小图阴影和大图凹槽的图层
        List<BufferedImage> effectImgs = createEffectImg(shapes, smallImg);
        // 处理图片的边缘高亮及其阴影效果
        BufferedImage sliceImg = dealLightAndShadow(effectImgs.get(0), shapes.get(0));
        // 将灰色图当做水印印到原图上
        BufferedImage bgImg = ImageUtil.createBgImg(effectImgs.get(1), sourceImg, x, y);
        
        CaptchaDTO captchaDTO = new CaptchaDTO();
        captchaDTO.setBgImg(Base64Util.getImageBase64(bgImg, true));
        captchaDTO.setSliceImg(Base64Util.getImageBase64(sliceImg, false));
        captchaDTO.setX(x);
        captchaDTO.setY(y);
        captchaDTO.setToken(TokenUtil.createToken());
        return captchaDTO;
    }
    
    /**
     * 处理小图,在4个方向上随机找到2个方向添加凸出
     *
     * @return
     */
    private static List<Shape> createSmallShape() {
        int face1 = RandomUtils.nextInt(4);
        int face2;
        //使凸出1 与 凸出2不在同一个方向
        do {
            face2 = RandomUtils.nextInt(4);
        } while (face1 == face2);
        Shape shape1 = createShape(face1, 0);
        Shape shape2 = createShape(face2, 0);
        // 因为后边图形需要生成阴影，所以生成的小图shape + 阴影宽度 = 灰度化的背景小图shape（即大图上的凹槽）
        Shape bigShape1 = createShape(face1, ConfigConstant.SHADOW);
        Shape bigShape2 = createShape(face2, ConfigConstant.SHADOW);


        // 生成中间正方体Shape,(具体边界 + 弧半径 = x坐标位)
        int xStart = ConfigConstant.CIRCLE_R + ConfigConstant.LIGHT;
        int yStart = ConfigConstant.CIRCLE_R + ConfigConstant.LIGHT;
        Shape center = new Rectangle2D.Float(xStart, yStart, ConfigConstant.SQUARE_W, ConfigConstant.SQUARE_H);
        Shape bigCenter = new Rectangle2D.Float(xStart - (float) ConfigConstant.SHADOW / 2,
                yStart - (float) ConfigConstant.SHADOW / 2, ConfigConstant.SQUARE_W + ConfigConstant.SHADOW,
                ConfigConstant.SQUARE_H + ConfigConstant.SHADOW);
        
        // 合并Shape
        Area area = new Area(center);
        area.add(new Area(shape1));
        area.add(new Area(shape2));
        // 合并大Shape
        Area bigArea = new Area(bigCenter);
        bigArea.add(new Area(bigShape1));
        bigArea.add(new Area(bigShape2));
        List<Shape> list = new ArrayList<>();
        list.add(area);
        list.add(bigArea);
        return list;
    }
    
    /**
     * 创建圆形区域,半径为5
     * 由于小图边缘阴影的存在,坐标需加上此宽度
     *
     * @param type 0=上，1=左，2=下，3=右
     * @param size 圆外接矩形边长
     * @return
     */
    private static Shape createShape(int type, int size) {
        if (type < 0 || type > 3) {
            type = 0;
        }
        int x;
        int y;
        if (type == 0) {
            x = ConfigConstant.SQUARE_W / 2 + ConfigConstant.SHADOW;
            y = ConfigConstant.SHADOW;
        } else if (type == 1) {
            x = ConfigConstant.SHADOW;
            y = ConfigConstant.SQUARE_H / 2 + ConfigConstant.SHADOW;
        } else if (type == 2) {
            x = ConfigConstant.SQUARE_W / 2 + ConfigConstant.SHADOW;
            y = ConfigConstant.SQUARE_H + ConfigConstant.SHADOW;
        } else {
            x = ConfigConstant.SQUARE_W + ConfigConstant.SHADOW;
            y = ConfigConstant.SQUARE_H / 2 + ConfigConstant.SHADOW;
        }
        int halfSize = size / 2;
        int wSide = ConfigConstant.CIRCLE_D + size;
        return new Arc2D.Float(x - halfSize, y - halfSize, wSide, wSide, 90 * type, 190, Arc2D.CHORD);
    }
    
    /**
     * 创建用于小图阴影和大图凹槽的图层
     *
     * @param shapes
     * @param smallImg 小图原图
     * @return
     */
    private static List<BufferedImage> createEffectImg(java.util.List<Shape> shapes, BufferedImage smallImg) {
        Shape area = shapes.get(0);
        Shape bigArea = shapes.get(1);
        // 创建图层用于处理小图的阴影
        BufferedImage bfm1 = new BufferedImage(ConfigConstant.SMALL_IMG_W, ConfigConstant.SMALL_IMG_H,
                BufferedImage.TYPE_INT_ARGB);
        // 创建图层用于处理大图的凹槽
        BufferedImage bfm2 = new BufferedImage(ConfigConstant.SMALL_IMG_W, ConfigConstant.SMALL_IMG_H,
                BufferedImage.TYPE_INT_ARGB);
        for (int i = 0; i < ConfigConstant.SMALL_IMG_W; i++) {
            for (int j = 0; j < ConfigConstant.SMALL_IMG_W; j++) {
                if (area.contains(i, j)) {
                    bfm1.setRGB(i, j, smallImg.getRGB(i, j));
                }
                if (bigArea.contains(i, j)) {
                    bfm2.setRGB(i, j, Color.black.getRGB());
                }
            }
        }
        List<BufferedImage> list = new ArrayList<>();
        list.add(bfm1);
        list.add(bfm2);
        return list;
    }
    
    /**
     * 处理小图的边缘灯光及其阴影效果
     *
     * @param bfm
     * @param shape
     * @return
     */
    private static BufferedImage dealLightAndShadow(BufferedImage bfm, Shape shape) {
        //创建新的透明图层，该图层用于边缘化阴影， 将生成的小图合并到该图上
        BufferedImage buffimg = ((Graphics2D) bfm.getGraphics()).getDeviceConfiguration()
                .createCompatibleImage(ConfigConstant.SMALL_IMG_W, ConfigConstant.SMALL_IMG_H,
                        Transparency.TRANSLUCENT);
        Graphics2D graphics2d = buffimg.createGraphics();
        Graphics2D g2 = (Graphics2D) bfm.getGraphics();
        //原有小图，边缘亮色处理
        paintBorderGlow(g2, shape);
        //新图层添加阴影
        paintBorderShadow(graphics2d, shape);
        graphics2d.drawImage(bfm, 0, 0, null);
        return buffimg;
    }
    
    /**
     * 处理边缘亮色
     *
     * @param g2
     * @param clipShape
     */
    private static void paintBorderGlow(Graphics2D g2, Shape clipShape) {
        int gw = ConfigConstant.LIGHT * 2;
        for (int i = gw; i >= 2; i -= 2) {
            float pct = (float) (gw - i) / (gw - 1);
            Color mixHi = getMixedColor(ConfigConstant.CIRCLE_GLOW_I_H, pct, ConfigConstant.CIRCLE_GLOW_O_H,
                    1.0f - pct);
            Color mixLo = getMixedColor(ConfigConstant.CIRCLE_GLOW_I_L, pct, ConfigConstant.CIRCLE_GLOW_O_L,
                    1.0f - pct);
            g2.setPaint(new GradientPaint(0.0f, 35 * 0.25f, mixHi, 0.0f, 35, mixLo));
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, pct));
            g2.setStroke(new BasicStroke(i));
            g2.draw(clipShape);
        }
    }
    
    /**
     * 处理阴影
     *
     * @param g1
     * @param clipShape
     */
    private static void paintBorderShadow(Graphics2D g1, Shape clipShape) {
        g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int sw = ConfigConstant.SHADOW * 2;
        for (int i = sw; i >= 2; i -= 2) {
            float pct = (float) (sw - i) / (sw - 1);
            //pct<03. 用于去掉阴影边缘白边，  pct>0.8用于去掉过深的色彩， 如果使用Color.lightGray. 可去掉pct>0.8
            if (pct < 0.3 || pct > 0.8) {
                continue;
            }
            g1.setColor(getMixedColor(new Color(54, 54, 54), pct, Color.WHITE, 1.0f - pct));
            g1.setStroke(new BasicStroke(i));
            g1.draw(clipShape);
        }
    }
    
    private static Color getMixedColor(Color c1, float pct1, Color c2, float pct2) {
        float[] clr1 = c1.getComponents(null);
        float[] clr2 = c2.getComponents(null);
        for (int i = 0; i < clr1.length; i++) {
            clr1[i] = (clr1[i] * pct1) + (clr2[i] * pct2);
        }
        return new Color(clr1[0], clr1[1], clr1[2], clr1[3]);
    }
    
}
