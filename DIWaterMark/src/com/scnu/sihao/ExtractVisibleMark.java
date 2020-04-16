package com.scnu.sihao;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

// 根据选定的区域，将该区域的像素矩阵取出，保存为新的图片

public class ExtractVisibleMark {
	// View中获得waterMarkX，waterMarkY去设置起始的长度 mimgwidth，mimgheight设置宽度高度
	 public void start(String resultImage,String returnVisibleMarkImage,int waterMarkX,int waterMarkY,int mimgwidth,int mimgheight) { 
		// 要提取的水印像素
			int [][][] WaterMarkRGBPixels= new int [3][mimgwidth][mimgheight];
		 // mImage是嵌入水印后的图像  
	        BufferedImage mImage = ImageUtil.getImage(resultImage);  
	        // 获取嵌入水印后图像的图片类型
	        int type = mImage.getType(); 
	        WritableRaster mRaster = mImage.getRaster();  
	        int wWidth = mRaster.getWidth();  
	        int wHeight = mRaster.getHeight();  
	      //定义长为3*wWidth*wHeight一维数组存储图像像素 
	        int[] oPixels = new int[3 * wWidth * wHeight]; 
	        // 返回 int（这里是int类ing）数组（即第五个参数）的像素 在这个区间（前面4个参数）的样本（取样）  返回结果为int类型的数组
	        mRaster.getPixels(0, 0, wWidth, wHeight, oPixels);	//Returns the samples in an array of int for the specified pixel.
	        // 一维RGB数组转换为三维RGB数组 
	        int[][][] RGBPixels = ImageUtil.getRGBArrayToMatrix(oPixels, wWidth,  wHeight);
	        // 获取水印的像素
	        for(int k=0; k<=2; k++){
		  	     for( int i=waterMarkX,q=0; q<mimgwidth; i++,q++){
		  	    	 // 获取原图像对应坐标的像素
		  	    	 for(int j=waterMarkY,w=0; w<mimgheight; j++,w++){
		  	    		WaterMarkRGBPixels[k][q][w]=RGBPixels[k][i][j];
		  	    	 }
		  	     }
		        }
	        //三维矩阵变为一维向量                
	        int[] result =ImageUtil.getRGBMatrixToArray(WaterMarkRGBPixels);
	        // 将BufferedImage对象写入磁盘    将rgbResult像素set给图片   这里的type为原图的格式文件 
	        ImageUtil.setImage(result,mimgwidth, mimgheight,returnVisibleMarkImage,  
	                "bmp",type);  // 使用type 传入 不是自定义 因为不同图片不同   这里type为bmp 但是上面命名又将她定义为JPG 图像更清晰了	
	        System.out.println("原图像宽度为:"+wWidth);
	        System.out.println("原图像长度为:"+wHeight);
	        System.out.println("水印图像宽度为:"+mimgwidth);
	        System.out.println("水印图像长度为:"+mimgheight);
	        
	        System.out.println("ExtractVisibleMark IS OK!");
	       

	 }
}
