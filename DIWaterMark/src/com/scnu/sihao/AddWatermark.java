// Raster A class representing a rectangular array of pixels 
package com.scnu.sihao;

import java.awt.image.BufferedImage;  
import java.awt.image.WritableRaster;  
  
import com.scnu.sihao.ImageUtil;  
import com.scnu.sihao.MathTool;  
//FDCTˮӡǶ��
public class AddWatermark {  
    private static final int d = 5;  
    public void start(String sourceImage,String waterMarkImage,String resultImage) {  
    	// ��ȡԭͼ��ͼƬ ����Buffer��
        BufferedImage oImage = ImageUtil.getImage(sourceImage);  
        // ��ȡˮӡͼ��ͼƬ ����Buffer��
        BufferedImage wImage = ImageUtil.getImage(waterMarkImage);  
        // ��ȡԭͼ���ͼƬ����
        int type = oImage.getType();  
        //to provide pixel writing capabilities�ṩ����д�빦��    
        WritableRaster oRaster = oImage.getRaster();   // getRaster ���صľ���WritableRaster
        WritableRaster wRaster = wImage.getRaster();  
        int oWidth = oRaster.getWidth();  
        int oHeight = oRaster.getHeight();  
        int wWidth = wRaster.getWidth();  
        int wHeight = wRaster.getHeight();  
        // ����ԭͼ������Ϊ3����writable pixel area RGB 3��     ���峤Ϊ3*oWidth*oHeightһά����洢ͼ������ 
        int[] oPixels = new int[3 * oWidth * oHeight];  
        int[] wPixels = new int[wWidth * wHeight];  
        // ���� int��������int��ing�����飨������������������� ��������䣨ǰ��4����������������ȡ����  ���ؽ��Ϊint���͵�����
        oRaster.getPixels(0, 0, oWidth, oHeight, oPixels);	//Returns the samples in an array of int for the specified pixel.
        wRaster.getPixels(0, 0, wWidth, wHeight, wPixels);  
        // һάRGB����ת��Ϊ��άRGB���� 
        int[][][] RGBPixels = ImageUtil.getRGBArrayToMatrix(oPixels, oWidth,  
                oHeight);  
        // ��RGB��ά����ĵ���ά ��int��ת��Ϊdouble��
        double[][] rPixels = MathTool.intToDoubleMatrix(RGBPixels[2]);  
        // ��ˮӡ�����ش�һάת��Ϊ��ά
        int[][] wDMatrix = ImageUtil.arrayToMatrix(wPixels, wWidth, wHeight);  
        double[][] result = rPixels;  
        
        // Ƕ���㷨  
        // ˮӡ�Ŀ��
        for (int i = 0; i < wWidth; i++) {  
        // ˮӡ�ĸ߶�
        	for (int j = 0; j < wHeight; j++) {  
            	// ÿ��8X8����ɺ��ִ���һ��8X8�Ŀ�
                double[][] blk = new double[8][8];  
                // ��ԭʼͼ��ĵ���ά����8 * 8 �ֿ�  
                for (int m = 0; m < 8; m++) {  
                    for (int n = 0; n < 8; n++) {  
                    	// double�͵�RGB��ά����ĵ���ά ��8X8���blk[][]
                        blk[m][n] = rPixels[8 * i + m][8 * j + n];  
                    }  
                }  
                // ��ԭʼͼ��ĵ���ά��8X8����п�����ɢ���ұ任
                double[][] dBlk = FDct.fDctTransform(blk);  
                // ��ˮӡͼƬ�� i j ����Ϊ0 �� ��ԭͼ�Ľ��п�����ɢ���ұ任��Ĳ���������ж��-5��+5����
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
                // �ٶ�ԭʼͼ��ĵ���ά��8X8����п�����ɢ���ұ任�õ���dBlk���� FDct��任
                blk = IFDct.iFDctTransform(dBlk);  
                for (int m = 0; m < 8; m++) {  
                    for (int n = 0; n < 8; n++) { 
                    	// ����8X8�������������ԭͼ
                        result[8 * i + m][8 * j + n] = blk[m][n];  
                    }  
                }  
            }  
        }  
        // ԭͼ��ĵ�һ��ά����  ֻ�ǶԵ���ά���зֿ����ɢ���ұ任
        double[][][] temp = new double[3][oWidth][oHeight];  
        temp[0] = MathTool.intToDoubleMatrix(RGBPixels[0]);  
        temp[1] = MathTool.intToDoubleMatrix(RGBPixels[1]);  
        temp[2] = result;  
        // ������ͼƬ���ؽ���ά����תΪһά����  
        double[] rgbResult = ImageUtil.getRGBMatrixToArray(temp);  
        // ��BufferedImage����д�����    ��rgbResult����set��ͼƬ   �����typeΪ5 ΪRGB��ʽ�ļ�
        ImageUtil.setImage(rgbResult, oWidth, oHeight,resultImage,  
                "bmp", type);     
        System.out.println("AddWatermark IS OK!"); 
    }  
  
}  