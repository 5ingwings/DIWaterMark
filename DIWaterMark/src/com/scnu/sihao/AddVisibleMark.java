package com.scnu.sihao;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

// 基本原理
// 0 取原图和水印的像素放到3*width*height的一维数组中
// 1 将一维数组像素转为三维数组 （RGB）
// 2 原图的三维像素 修改为水印的三维像素 一维维的改（这里只处理水印图大小的区域）
// 3 三维数组再转回一维数组像素 再生成output图

public class AddVisibleMark {
     
	 public void start(String sourceImage,String waterMarkImage,String resultImage,int x,int y) {
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
	      //定义长为3*oWidth*oHeight一维数组存储图像像素 
	        int[] oPixels = new int[3 * oWidth * oHeight]; 
	        int[] wPixels = new int[3 * wWidth * wHeight];  
	        // 返回 int（这里是int类ing）数组（即第五个参数）的像素 在这个区间（前面4个参数）的样本（取样）  返回结果为int类型的数组
	        oRaster.getPixels(0, 0, oWidth, oHeight, oPixels);	//Returns the samples in an array of int for the specified pixel.
	        wRaster.getPixels(0, 0, wWidth, wHeight, wPixels); 
	        // 原图的一维RGB数组转换为三维RGB数组 
	        int[][][] oRGBPixels = ImageUtil.getRGBArrayToMatrix(oPixels, oWidth,  oHeight);  
	        // 水印的一维RGB数组转换为三维RGB数组 
	        int[][][] wRGBPixels = ImageUtil.getRGBArrayToMatrix(wPixels, wWidth,  wHeight);  
	       
	        // 原图的三维像素 修改为水印的三维像素
	        // 一维维的改
	        for(int k=0; k<=2; k++){
	        	// 横坐标
	        	for( int i=x,q=0; q<wWidth; i++,q++){
	  	        // 纵坐标
	        		for(int j=y,w=0; w<wHeight; j++,w++){
	  	        	oRGBPixels[k][i][j]=wRGBPixels[k][q][w];
	              }
	  	     }
	        }
	        //三维矩阵变为一维向量                
	        int[] result =ImageUtil.getRGBMatrixToArray(oRGBPixels);
	        // 将BufferedImage对象写入磁盘    将rgbResult像素set给图片   这里的type为5 为RGB格式文件 设置为bmp 然而名字却可以设置为其他的 这样会先自行生成bmp的图 再自动生成其他类型的图
	        ImageUtil.setImage(result, oWidth, oHeight,resultImage,  
	                "bmp", type);  
	        System.out.println("AddVisibleMark IS OK!");
	}
	 
}
