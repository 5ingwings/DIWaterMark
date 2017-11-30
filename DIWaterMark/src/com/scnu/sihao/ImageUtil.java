package com.scnu.sihao;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
// 工具类ImageUtil   获取图片 获取像素  像素转换成图像文件 
//一维数组转为二维数组   一维数组转换为三维数组   二维数组转为一维数组  三维数组转为一维数组 
public class ImageUtil {
	/** 
     * 获取图片 
     *  
     * @param filepath 
     * @return 
     */  
    public static BufferedImage getImage(String filepath) {  
        BufferedImage image = null;  
        File file = new File(filepath);  
        try {  
            image = ImageIO.read(file);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return image;  
    }  
  
    /** 
     * 获取图像文件的像素（图片转换为像素） 
     *  
     * @param filepath 
     * @param format 
     */  
    public int[] getImagePixels(String filepath) {  
        BufferedImage image = null;  
        File file = new File(filepath);  
        try {  
            image = ImageIO.read(file);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        WritableRaster raster = image.getRaster();  
        // 得到图像的宽度  
        int width = raster.getWidth();  
        // 得到图像的高度  
        int height = raster.getHeight();  
        // RGB格式图像文件每一个点的颜色由红、绿、蓝三种颜色构成，即实际图像可为3层，  
        // 分别为R，G，B层，因此分解后的文件象素是实际坐标高度和宽度的三倍。  
        int[] pixels = new int[3 * width * height];  
        // 读取坐标的范围是从(0,0)坐标开始宽width,高height  
        raster.getPixels(0, 0, width, height, pixels);  
        return pixels;  
    }  
  
    /** 
     * 像素转换成图像文件  double型的一维数组
     *  
     * @param result 
     * @param width 
     * @param height 
     * @param filepath 
     * @param format 
     */  
    public static void setImage(double[] result, int width, int height,  
            String filepath, String format, int type) {  
        BufferedImage outImage = new BufferedImage(width, height, type);  
        WritableRaster outRaster = outImage.getRaster();  
        outRaster.setPixels(0, 0, width, height, result);  
        // 图像文件的写入  
        File outFile = new File(filepath);  
        try {  
            ImageIO.write(outImage, format, outFile);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }
    
    // 像素转换成图像文件  int型的一维数组
    public static void setImage(int[] result, int width, int height,  
            String filepath, String format, int type) {  
        BufferedImage outImage = new BufferedImage(width, height, type);  
        WritableRaster outRaster = outImage.getRaster();  
        outRaster.setPixels(0, 0, width, height, result);  
        // 图像文件的写入  
        File outFile = new File(filepath);  
        try {  
            ImageIO.write(outImage, format, outFile);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }
   
    /** 
     * 一维数组转为二维数组 
     *  
     * @param m 
     * @param width 
     * @param height 
     * @return 
     */  
    public static int[][] arrayToMatrix(int[] m, int width, int height) {  
        int[][] result = new int[height][width];  
        for (int i = 0; i < height; i++) {  
            for (int j = 0; j < width; j++) {  
                int p = j * height + i;  
                result[i][j] = m[p];  
            }  
        }  
        return result;  
    }  
  
    /** 
     * 一维数组转换为三维数组 
     *  
     * @param pixels 
     * @param width 
     * @param height 
     * @return 
     */  
    public static int[][][] getRGBArrayToMatrix(int[] pixels, int width,  
            int height) {  
        // 已知有3个二维数组组成分别代表RGB  
        int[][][] result = new int[3][height][width];  
        int[][] temp = new int[3][width * height];  
        for (int i = 0; i < pixels.length; i++) {  
            int m = i / 3;  
            int n = i % 3;  
            temp[n][m] = pixels[i];  
        }  
        result[0] = arrayToMatrix(temp[0], width, height);  
        result[1] = arrayToMatrix(temp[1], width, height);  
        result[2] = arrayToMatrix(temp[2], width, height);  
        return result;  
    }  
  
    /** 
     * 二维数组转为一维数组   int 型
     *  
     * @param m 
     * @return 
     */  
    //把int型二维数组转为一维数组
    public static int[] matrixToArray(int[][] m){
       int p = m.length * m[0].length;
       int[] result = new int[p];
       for(int i=0;i<m.length;i++){
 	 for(int j=0;j<m[i].length;j++){
 	    int q = j * m.length + i;
 	    result[q] = m[i][j];
 	 }
       }
       return result;
    }
    /** 
     * 二维数组转为一维数组   double 型
     *  
     * @param m 
     * @return 
     */  
    public static double[] matrixToArray(double[][] m) {  
        int p = m.length * m[0].length;  
        double[] result = new double[p];  
        for (int i = 0; i < m.length; i++) {  
            for (int j = 0; j < m[i].length; j++) {  
                int q = j * m.length + i;  
                result[q] = m[i][j];  
            }  
        }  
        return result;  
    }  
  
    /** 
     * 三维数组转为一维数组   double 型
     *  
     * @param m 
     * @return 
     */  
    public static double[] getRGBMatrixToArray(double[][][] m) {  
        int width = m[0].length;  
        int height = m[0][0].length;  
        int len = width * height;  
        double[] result = new double[3 * len];  
        double[][] temp = new double[3][len];  
        temp[0] = matrixToArray(m[0]);  
        temp[1] = matrixToArray(m[1]);  
        temp[2] = matrixToArray(m[2]);  
        for (int i = 0; i < 3; i++) {  
            for (int j = 0; j < temp[i].length; j++)  
                result[3 * j + i] = temp[i][j];  
        }  
        return result;  
    }
    /** 
     * 三维数组转为一维数组  int 型
     *  
     * @param m 
     * @return 
     */  
    public static int[] getRGBMatrixToArray(int[][][] m) {
    	int width = m[0].length;
    		      int height = m[0][0].length;
    		      int len = width * height;
    		      int[] result = new int[3*len];
    		      int[][] temp = new int[3][len];
    		      temp[0] = matrixToArray(m[0]);
    		      temp[1] = matrixToArray(m[1]);
    		      temp[2] = matrixToArray(m[2]);
    			for(int i=0;i<3;i++){
    			for(int j=0;j<temp[i].length;j++){
    			result[3 * j + i] = temp[i][j];
    			}
    		      }
    	return result; 
    	
    }
}
