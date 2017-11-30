package com.scnu.sihao;

import java.awt.image.BufferedImage;  
import java.awt.image.WritableRaster;

import javax.swing.JOptionPane;

import com.scnu.sihao.ImageUtil;  
import com.scnu.sihao.MathTool;  
 
//FDCTˮӡ��ȡʵ��
public class ExtractWatermark {  
    public void start(String sourceImage,String resultImage,String returnWaterMarkImage,int wWidth, int wHeight) {  
        // mImage��Ƕ��ˮӡ���ͼ��  
        BufferedImage mImage = ImageUtil.getImage(resultImage);  
        // ԭʼͼ��  
        BufferedImage oImage = ImageUtil.getImage(sourceImage);  
        // getRaster ���صľ���WritableRaster
        WritableRaster oRaster = oImage.getRaster();  
        WritableRaster mRaster = mImage.getRaster();  
        int oWidth = oRaster.getWidth();  
        int oHeight = oRaster.getHeight();  
        // ����ԭͼ������Ϊ3����writable pixel area RGB 3��
        int[] oPixels = new int[3 * oWidth * oHeight];  
        int[] mPixels = new int[3 * oWidth * oHeight];  
     // ���� int��������int���ͣ����飨������������������� ��������䣨ǰ��4����������������ȡ����  ���ؽ��Ϊint���͵�����
        oRaster.getPixels(0, 0, oWidth, oHeight, oPixels);  
        mRaster.getPixels(0, 0, oWidth, oHeight, mPixels);  
        // ��rgbͼ���������mRgbPixels[0]��ʾb�������   mRgbPixels[2]��
        int[][][] mRgbPixels = ImageUtil.getRGBArrayToMatrix(mPixels, oWidth,  
                oHeight);  
        int[][][] oRgbPixels = ImageUtil.getRGBArrayToMatrix(oPixels, oWidth,  
                oHeight);  
        // ��RGB��ά����ĵ���ά ��int��ת��Ϊdouble��
        double[][] oDPixels = MathTool.intToDoubleMatrix(mRgbPixels[2]);  
        double[][] mDPixels = MathTool.intToDoubleMatrix(oRgbPixels[2]);  
        // ����һ����С��ˮӡͼƬ��Сһ�µĶ�ά����
        double[][] result = new double[wWidth][wHeight]; 
        // ˮӡͼƬ�Ŀ��
        for (int i = 0; i < wWidth; i++) {  
            for (int j = 0; j < wHeight; j++) {  
                double[][] oBlk = new double[8][8];  
                double[][] mBlk = new double[8][8];  
                int d = 0;  
                int f = 0;  
                for (int m = 0; m < 8; m++) {  
                    for (int n = 0; n < 8; n++) {  
                    	// ԭͼ��double�͵�RGB��ά����ĵ���ά ��8X8���oBlk[][]
                        oBlk[m][n] = oDPixels[8 * i + m][8 * j + n];  
                        // �����ˮӡ��ͼ��double�͵�RGB��ά����ĵ���ά ��8X8���mBlk[][]
                        mBlk[m][n] = mDPixels[8 * i + m][8 * j + n];  
                    }  
                }  
                // ��oBlk���п�����ɢ���ұ任
                double[][] dOBlk = FDct.fDctTransform(oBlk);  
                // ��mBlk���п�����ɢ���ұ任
                double[][] dMBlk = FDct.fDctTransform(mBlk);  
                // ��ԭͼ�񾭹�������ɢ���ұ任��3��3������� �����ˮӡ����������ɢ���ұ任��3��3���� ��d++
                // ȡˮӡ�ĵ���ά���أ���Ϊ��Ƕ�뵽����ά�У���������ؾ������8X8�ֿ飬��ԭͼ��Ҳ�ǵ���ά8X8�ֿ飬��ͨ�������������ж� ������d֮�ʹ��ڵ���f֮�ͣ���ͼ��ij������Ϊ1 ������Ϊ0
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
                 // ��dС��f ������ˮӡ�Ķ�ά����Ϊ0   
                if (d < f) {  
                    result[i][j] = 0;  
                } else {  
                    result[i][j] = 1;  
                }  
            }  
        }  
        double[] outResult = ImageUtil.matrixToArray(result);  
        // ��Ƕ��ˮӡ�Ľ��д��BufferedImage����    �õ�����bmp/jpg/�ȵ� ���Զ��� ��ʽ��ͼƬ Ȼ���û��ںϳ�ͼƬʱ�ĺ�׺ ���Ը�ͼƬ��ʽ
        ImageUtil.setImage(outResult, wWidth, wHeight, returnWaterMarkImage, "jpg",  
                BufferedImage.TYPE_BYTE_BINARY); 
        
        System.out.println("ԭͼ����Ϊ:"+oWidth);
        System.out.println("ԭͼ��߶�Ϊ:"+oHeight);
        System.out.println("ˮӡͼ����Ϊ:"+wWidth);
        System.out.println("ˮӡͼ��߶�Ϊ:"+wHeight);
        System.out.println("ExtractWatermark IS OK!");
     
    }  
  
}  