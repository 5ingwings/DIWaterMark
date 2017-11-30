package com.scnu.sihao;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
// ������ImageUtil   ��ȡͼƬ ��ȡ����  ����ת����ͼ���ļ� 
//һά����תΪ��ά����   һά����ת��Ϊ��ά����   ��ά����תΪһά����  ��ά����תΪһά���� 
public class ImageUtil {
	/** 
     * ��ȡͼƬ 
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
     * ��ȡͼ���ļ������أ�ͼƬת��Ϊ���أ� 
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
        // �õ�ͼ��Ŀ��  
        int width = raster.getWidth();  
        // �õ�ͼ��ĸ߶�  
        int height = raster.getHeight();  
        // RGB��ʽͼ���ļ�ÿһ�������ɫ�ɺ졢�̡���������ɫ���ɣ���ʵ��ͼ���Ϊ3�㣬  
        // �ֱ�ΪR��G��B�㣬��˷ֽ����ļ�������ʵ������߶ȺͿ�ȵ�������  
        int[] pixels = new int[3 * width * height];  
        // ��ȡ����ķ�Χ�Ǵ�(0,0)���꿪ʼ��width,��height  
        raster.getPixels(0, 0, width, height, pixels);  
        return pixels;  
    }  
  
    /** 
     * ����ת����ͼ���ļ�  double�͵�һά����
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
        // ͼ���ļ���д��  
        File outFile = new File(filepath);  
        try {  
            ImageIO.write(outImage, format, outFile);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }
    
    // ����ת����ͼ���ļ�  int�͵�һά����
    public static void setImage(int[] result, int width, int height,  
            String filepath, String format, int type) {  
        BufferedImage outImage = new BufferedImage(width, height, type);  
        WritableRaster outRaster = outImage.getRaster();  
        outRaster.setPixels(0, 0, width, height, result);  
        // ͼ���ļ���д��  
        File outFile = new File(filepath);  
        try {  
            ImageIO.write(outImage, format, outFile);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }
   
    /** 
     * һά����תΪ��ά���� 
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
     * һά����ת��Ϊ��ά���� 
     *  
     * @param pixels 
     * @param width 
     * @param height 
     * @return 
     */  
    public static int[][][] getRGBArrayToMatrix(int[] pixels, int width,  
            int height) {  
        // ��֪��3����ά������ɷֱ����RGB  
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
     * ��ά����תΪһά����   int ��
     *  
     * @param m 
     * @return 
     */  
    //��int�Ͷ�ά����תΪһά����
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
     * ��ά����תΪһά����   double ��
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
     * ��ά����תΪһά����   double ��
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
     * ��ά����תΪһά����  int ��
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
