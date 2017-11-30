package com.scnu.sihao;

import java.awt.image.BufferedImage;  
import java.awt.image.WritableRaster;

import javax.swing.JOptionPane;

import com.scnu.sihao.ImageUtil;  
import com.scnu.sihao.MathTool;  
 
//FDCT水印提取实验
public class ExtractWatermark {  
    public void start(String sourceImage,String resultImage,String returnWaterMarkImage,int wWidth, int wHeight) {  
        // mImage是嵌入水印后的图像  
        BufferedImage mImage = ImageUtil.getImage(resultImage);  
        // 原始图像  
        BufferedImage oImage = ImageUtil.getImage(sourceImage);  
        // getRaster 返回的就是WritableRaster
        WritableRaster oRaster = oImage.getRaster();  
        WritableRaster mRaster = mImage.getRaster();  
        int oWidth = oRaster.getWidth();  
        int oHeight = oRaster.getHeight();  
        // 设置原图像像素为3倍的writable pixel area RGB 3层
        int[] oPixels = new int[3 * oWidth * oHeight];  
        int[] mPixels = new int[3 * oWidth * oHeight];  
     // 返回 int（这里是int类型）数组（即第五个参数）的像素 在这个区间（前面4个参数）的样本（取样）  返回结果为int类型的数组
        oRaster.getPixels(0, 0, oWidth, oHeight, oPixels);  
        mRaster.getPixels(0, 0, oWidth, oHeight, mPixels);  
        // 得rgb图像三层矩阵，mRgbPixels[0]表示b层分量？   mRgbPixels[2]吧
        int[][][] mRgbPixels = ImageUtil.getRGBArrayToMatrix(mPixels, oWidth,  
                oHeight);  
        int[][][] oRgbPixels = ImageUtil.getRGBArrayToMatrix(oPixels, oWidth,  
                oHeight);  
        // 将RGB三维数组的第三维 从int型转换为double型
        double[][] oDPixels = MathTool.intToDoubleMatrix(mRgbPixels[2]);  
        double[][] mDPixels = MathTool.intToDoubleMatrix(oRgbPixels[2]);  
        // 创建一个大小和水印图片大小一致的二维数组
        double[][] result = new double[wWidth][wHeight]; 
        // 水印图片的宽高
        for (int i = 0; i < wWidth; i++) {  
            for (int j = 0; j < wHeight; j++) {  
                double[][] oBlk = new double[8][8];  
                double[][] mBlk = new double[8][8];  
                int d = 0;  
                int f = 0;  
                for (int m = 0; m < 8; m++) {  
                    for (int n = 0; n < 8; n++) {  
                    	// 原图像double型的RGB三维数组的第三维 分8X8块给oBlk[][]
                        oBlk[m][n] = oDPixels[8 * i + m][8 * j + n];  
                        // 添加了水印的图像double型的RGB三维数组的第三维 分8X8块给mBlk[][]
                        mBlk[m][n] = mDPixels[8 * i + m][8 * j + n];  
                    }  
                }  
                // 对oBlk进行快速离散余弦变换
                double[][] dOBlk = FDct.fDctTransform(oBlk);  
                // 对mBlk进行快速离散余弦变换
                double[][] dMBlk = FDct.fDctTransform(mBlk);  
                // 若原图像经过快速离散余弦变换的3，3区域大于 添加了水印经过快速离散余弦变换的3，3区域 则d++
                // 取水印的第三维像素，因为是嵌入到第三维中，对这个像素矩阵进行8X8分块，对原图像也是第三维8X8分块，再通过几个特征点判断 若存在d之和大于等于f之和，则图像ij区域置为1 否则置为0
                if (dOBlk[3][3] > dMBlk[3][3]) {  
                    d++;  
                } else {  
                    f++;  
                }  
                if (dOBlk[3][4] > dMBlk[3][4]) {  
                    d++;  
                } else {  
                    f++;  
                }  
                if (dOBlk[3][5] > dMBlk[3][5]) {  
                    d++;  
                } else {  
                    f++;  
                }  
                if (dOBlk[4][3] > dMBlk[4][3]) {  
                    d++;  
                } else {  
                    f++;  
                }  
                if (dOBlk[5][3] > dMBlk[5][3]) {  
                    d++;  
                } else {  
                    f++;  
                }  
                 // 若d小于f 则生成水印的二维数组为0   
                if (d < f) {  
                    result[i][j] = 0;  
                } else {  
                    result[i][j] = 1;  
                }  
            }  
        }  
        double[] outResult = ImageUtil.matrixToArray(result);  
        // 把嵌入水印的结果写到BufferedImage对象    得到的是bmp/jpg/等等 可自定义 格式的图片 然后用户在合成图片时改后缀 可以改图片格式
        ImageUtil.setImage(outResult, wWidth, wHeight, returnWaterMarkImage, "jpg",  
                BufferedImage.TYPE_BYTE_BINARY); 
        
        System.out.println("原图像宽度为:"+oWidth);
        System.out.println("原图像高度为:"+oHeight);
        System.out.println("水印图像宽度为:"+wWidth);
        System.out.println("水印图像高度为:"+wHeight);
        System.out.println("ExtractWatermark IS OK!");
     
    }  
  
}  