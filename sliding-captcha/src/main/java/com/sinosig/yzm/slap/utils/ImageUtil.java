package com.sinosig.yzm.slap.utils;

import com.sinosig.yzm.slap.constant.ConfigConstant;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import javax.imageio.*;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Iterator;

/**
 * @author ouka
 * ImageUtil
 */
class ImageUtil {
    private static final String JPG_HEX = "ff";
    private static final String PNG_HEX = "89";
    private static final String JPG_EXT = "jpg";
    private static final String PNG_EXT = "png";
    
    /**
     * 创建小块拼图
     *
     * @param file 背景原图
     * @param x    小块拼图x坐标
     * @param y    小块拼图y坐标
     * @return
     */
    static BufferedImage cutSmallImg(File file, int x, int y) throws IOException {
        Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName(ImageUtil.getFileExt(file.getPath()));
        ImageReader render = iterator.next();
        ImageInputStream in = ImageIO.createImageInputStream(new FileInputStream(file));
        render.setInput(in, false);
        BufferedImage bufferedImage;
        try {
            ImageReadParam param = render.getDefaultReadParam();
            Rectangle rect = new Rectangle(x, y, ConfigConstant.SMALL_IMG_W, ConfigConstant.SMALL_IMG_H);
            param.setSourceRegion(rect);
            bufferedImage = render.read(0, param);
        } finally {
            if (in != null) {
                in.close();
            }
        }
        return bufferedImage;
    }
    
    /**
     * 创建一个灰度化图层， 将生成的小图，覆盖到该图层，使其灰度化，用于作为一个水印图
     *
     * @param smallImage 小图
     * @param originImg  原图
     * @param x          x坐标
     * @param y          y坐标
     * @return
     */
    static BufferedImage createBgImg(BufferedImage smallImage, BufferedImage originImg, int x, int y) {
        // 将灰度化之后的图片，整合到原有图片上
        Graphics2D graphics2d = originImg.createGraphics();
        graphics2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, 0.6F));
        graphics2d.drawImage(smallImage, x, y, null);
        // 释放
        graphics2d.dispose();
        return originImg;
    }
    
    /**
     * 压缩图片
     *
     * @param originImg
     * @return
     */
    static byte[] compressImg(BufferedImage originImg) {
        ImageWriter imageWriter = null;
        ByteArrayOutputStream outputStream = null;
        try {
            int width = originImg.getWidth();
            int height = originImg.getHeight();
            BufferedImage newBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_USHORT_555_RGB);
            Graphics2D graphics2d = newBufferedImage.createGraphics();
//            graphics2D.setBackground(new Color(255, 255, 255));
            graphics2d.clearRect(0, 0, width, height);
            graphics2d.drawImage(originImg.getScaledInstance(width, height, Image.SCALE_SMOOTH), 0, 0, null);
            
            imageWriter = ImageIO.getImageWritersByFormatName("png").next();
            outputStream = new ByteArrayOutputStream();
            imageWriter.setOutput(ImageIO.createImageOutputStream(outputStream));
            imageWriter.write(new IIOImage(newBufferedImage, null, null));
            outputStream.flush();
            return outputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (imageWriter != null) {
                imageWriter.abort();
            }
            IOUtils.closeQuietly(outputStream);
        }
    }


    public static String getFileExt(String filePath) {
        FileInputStream fis = null;
        String extension = FilenameUtils.getExtension(filePath);
        try {
            fis = new FileInputStream(new File(filePath));
            byte[] bs = new byte[1];
            fis.read(bs);
            String type = Integer.toHexString(bs[0]&0xFF);
            if(JPG_HEX.equals(type)) {
                extension = JPG_EXT;
            }
            if(PNG_HEX.equals(type)) {
                extension = PNG_EXT;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fis != null) {
                    fis.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return extension;
    }
}
