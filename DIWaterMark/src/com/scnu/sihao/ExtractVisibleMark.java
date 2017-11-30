package com.scnu.sihao;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

public class ExtractVisibleMark {
	// View�л��waterMarkX��waterMarkYȥ������ʼ�ĳ��� mimgwidth��mimgheight���ÿ�ȸ߶�
	 public void start(String resultImage,String returnVisibleMarkImage,int waterMarkX,int waterMarkY,int mimgwidth,int mimgheight) { 
		// Ҫ��ȡ��ˮӡ����
			int [][][] WaterMarkRGBPixels= new int [3][mimgwidth][mimgheight];
		 // mImage��Ƕ��ˮӡ���ͼ��  
	        BufferedImage mImage = ImageUtil.getImage(resultImage);  
	        // ��ȡǶ��ˮӡ��ͼ���ͼƬ����
	        int type = mImage.getType(); 
	        WritableRaster mRaster = mImage.getRaster();  
	        int wWidth = mRaster.getWidth();  
	        int wHeight = mRaster.getHeight();  
	      //���峤Ϊ3*wWidth*wHeightһά����洢ͼ������ 
	        int[] oPixels = new int[3 * wWidth * wHeight]; 
	        // ���� int��������int��ing�����飨������������������� ��������䣨ǰ��4����������������ȡ����  ���ؽ��Ϊint���͵�����
	        mRaster.getPixels(0, 0, wWidth, wHeight, oPixels);	//Returns the samples in an array of int for the specified pixel.
	        // һάRGB����ת��Ϊ��άRGB���� 
	        int[][][] RGBPixels = ImageUtil.getRGBArrayToMatrix(oPixels, wWidth,  wHeight);
	        // ��ȡˮӡ������
	        for(int k=0; k<=2; k++){
		  	     for( int i=waterMarkX,q=0; q<mimgwidth; i++,q++){
		  	    	 // ��ȡԭͼ���Ӧ���������
		  	    	 for(int j=waterMarkY,w=0; w<mimgheight; j++,w++){
		  	    		WaterMarkRGBPixels[k][q][w]=RGBPixels[k][i][j];
		  	    	 }
		  	     }
		        }
	        //��ά�����Ϊһά����                
	        int[] result =ImageUtil.getRGBMatrixToArray(WaterMarkRGBPixels);
	        // ��BufferedImage����д�����    ��rgbResult����set��ͼƬ   �����typeΪԭͼ�ĸ�ʽ�ļ� 
	        ImageUtil.setImage(result,mimgwidth, mimgheight,returnVisibleMarkImage,  
	                "bmp",type);  // ʹ��type ���� �����Զ��� ��Ϊ��ͬͼƬ��ͬ   ����typeΪbmp �������������ֽ�������ΪJPG ͼ���������	
	        System.out.println("ԭͼ����Ϊ:"+wWidth);
	        System.out.println("ԭͼ�񳤶�Ϊ:"+wHeight);
	        System.out.println("ˮӡͼ����Ϊ:"+mimgwidth);
	        System.out.println("ˮӡͼ�񳤶�Ϊ:"+mimgheight);
	        
	        System.out.println("ExtractVisibleMark IS OK!");
	       

	 }
}
