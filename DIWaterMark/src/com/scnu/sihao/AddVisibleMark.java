package com.scnu.sihao;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class AddVisibleMark {
     
	 public void start(String sourceImage,String waterMarkImage,String resultImage,int x,int y) {
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
	      //���峤Ϊ3*oWidth*oHeightһά����洢ͼ������ 
	        int[] oPixels = new int[3 * oWidth * oHeight]; 
	        int[] wPixels = new int[3 * wWidth * wHeight];  
	        // ���� int��������int��ing�����飨������������������� ��������䣨ǰ��4����������������ȡ����  ���ؽ��Ϊint���͵�����
	        oRaster.getPixels(0, 0, oWidth, oHeight, oPixels);	//Returns the samples in an array of int for the specified pixel.
	        wRaster.getPixels(0, 0, wWidth, wHeight, wPixels); 
	        // ԭͼ��һάRGB����ת��Ϊ��άRGB���� 
	        int[][][] oRGBPixels = ImageUtil.getRGBArrayToMatrix(oPixels, oWidth,  oHeight);  
	        // ˮӡ��һάRGB����ת��Ϊ��άRGB���� 
	        int[][][] wRGBPixels = ImageUtil.getRGBArrayToMatrix(wPixels, wWidth,  wHeight);  
	       
	        // ԭͼ����ά���� �޸�Ϊˮӡ����ά����
	        // һάά�ĸ�
	        for(int k=0; k<=2; k++){
	        	// ������
	        	for( int i=x,q=0; q<wWidth; i++,q++){
	  	        // ������
	        		for(int j=y,w=0; w<wHeight; j++,w++){
	  	        	oRGBPixels[k][i][j]=wRGBPixels[k][q][w];
	              }
	  	     }
	        }
	        //��ά�����Ϊһά����                
	        int[] result =ImageUtil.getRGBMatrixToArray(oRGBPixels);
	        // ��BufferedImage����д�����    ��rgbResult����set��ͼƬ   �����typeΪ5 ΪRGB��ʽ�ļ� ����Ϊbmp Ȼ������ȴ��������Ϊ������ ����������������bmp��ͼ ���Զ������������͵�ͼ
	        ImageUtil.setImage(result, oWidth, oHeight,resultImage,  
	                "bmp", type);  
	        System.out.println("AddVisibleMark IS OK!");
	}
	 
}
