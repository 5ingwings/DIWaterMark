// Raster A class representing a rectangular array of pixels 
package com.scnu.sihao;

import java.awt.image.BufferedImage;  
import java.awt.image.WritableRaster;  
  
import com.scnu.sihao.ImageUtil;  
import com.scnu.sihao.MathTool;  
//FDCT水印嵌入
public class AddWatermark {  
    private static final int d = 5;  
    public void start(String sourceImage,String waterMarkImage,String resultImage) {  
    	// 获取原图像图片 存于Buffer中
        BufferedImage oImage = ImageUtil.getImage(sourceImage);  
        // 获取水印图像图片 存于Buffer中
        BufferedImage wImage = ImageUtil.getImage(waterMarkImage);  
        // 获取原图像的图片类型
        int type = oImage.getType();  
        //to provide pixel writing capabilities提供像素写入功能    
        WritableRaster oRaster = oImage.getRaster();   // getRaster 返回的就是WritableRaster
        WritableRaster wRaster = wImage.getRaster();  
        int oWidth = oRaster.getWidth();  
        int oHeight = oRaster.getHeight();  
        int wWidth = wRaster.getWidth();  
        int wHeight = wRaster.getHeight();  
        // 设置原图像像素为3倍的writable pixel area RGB 3层     定义长为3*oWidth*oHeight一维数组存储图像像素 
        int[] oPixels = new int[3 * oWidth * oHeight];  
        int[] wPixels = new int[wWidth * wHeight];  
        // 返回 int（这里是int类ing）数组（即第五个参数）的像素 在这个区间（前面4个参数）的样本（取样）  返回结果为int类型的数组
        oRaster.getPixels(0, 0, oWidth, oHeight, oPixels);	//Returns the samples in an array of int for the specified pixel.
        wRaster.getPixels(0, 0, wWidth, wHeight, wPixels);  
        // 一维RGB数组转换为三维RGB数组 
        int[][][] RGBPixels = ImageUtil.getRGBArrayToMatrix(oPixels, oWidth,  
                oHeight);  
        // 将RGB三维数组的第三维 从int型转换为double型
        double[][] rPixels = MathTool.intToDoubleMatrix(RGBPixels[2]);  
        // 将水印的像素从一维转换为二维
        int[][] wDMatrix = ImageUtil.arrayToMatrix(wPixels, wWidth, wHeight);  
        double[][] result = rPixels;  
        
        // 嵌入算法  
        // 水印的宽度
        for (int i = 0; i < wWidth; i++) {  
        // 水印的高度
        	for (int j = 0; j < wHeight; j++) {  
            	// 每个8X8块完成后，又创建一个8X8的块
                double[][] blk = new double[8][8];  
                // 对原始图像的第三维进行8 * 8 分块  
                for (int m = 0; m < 8; m++) {  
                    for (int n = 0; n < 8; n++) {  
                    	// double型的RGB三维数组的第三维 分8X8块给blk[][]
                        blk[m][n] = rPixels[8 * i + m][8 * j + n];  
                    }  
                }  
                // 对原始图像的第三维的8X8块进行快速离散余弦变换
                double[][] dBlk = FDct.fDctTransform(blk);  
                // 若水印图片的 i j 区域为0 则 将原图的进行快速离散余弦变换后的部分区域进行多次-5或+5操作
                // 因为是二值图像 只存在0/255
                if (wDMatrix[i][j] == 0) {  
                    dBlk[3][3] = dBlk[3][3] - d;  
                    dBlk[3][4] = dBlk[3][4] - d;  
                    dBlk[3][5] = dBlk[3][5] - d;  
                    dBlk[4][3] = dBlk[4][3] - d;  
                    dBlk[5][3] = dBlk[5][3] - d;  
                } else {  
                    dBlk[3][3] = dBlk[3][3] + d;  
                    dBlk[3][4] = dBlk[3][4] + d;  
                    dBlk[3][5] = dBlk[3][5] + d;  
                    dBlk[4][3] = dBlk[4][3] + d;  
                    dBlk[5][3] = dBlk[5][3] + d;  
                }  
                // 再对原始图像的第三维的8X8块进行快速离散余弦变换得到的dBlk进行 FDct逆变换
                blk = IFDct.iFDctTransform(dBlk);  
                for (int m = 0; m < 8; m++) {  
                    for (int n = 0; n < 8; n++) { 
                    	// 返回8X8处理结果的区域给原图
                        result[8 * i + m][8 * j + n] = blk[m][n];  
                    }  
                }  
            }  
        }  
        // 原图像的第一二维不变  只是对第三维进行分块的离散余弦变换
        double[][][] temp = new double[3][oWidth][oHeight];  
        temp[0] = MathTool.intToDoubleMatrix(RGBPixels[0]);  
        temp[1] = MathTool.intToDoubleMatrix(RGBPixels[1]);  
        temp[2] = result;  
        // 处理后的图片像素将三维数组转为一维数组  
        double[] rgbResult = ImageUtil.getRGBMatrixToArray(temp);  
        // 将BufferedImage对象写入磁盘    将rgbResult像素set给图片   这里的type为5 为RGB格式文件
        ImageUtil.setImage(rgbResult, oWidth, oHeight,resultImage,  
                "bmp", type);     
        System.out.println("AddWatermark IS OK!"); 
    }  
  
}  
