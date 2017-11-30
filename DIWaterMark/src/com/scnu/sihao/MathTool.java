package com.scnu.sihao;

//������MathTool  
// ʵ��һά����άint������double��
public class MathTool {  
    public static double[][] intToDoubleMatrix(int[][] input) {  // ʵ�ֶ�άint������double��
        int height = input.length;    // ��һ���±곤��
        int width = input[0].length;  // �ڶ����±곤��
        double[][] output = new double[height][width];  
        for (int i = 0; i < height; i++) {  
            // ��  
            for (int j = 0; j < width; j++) {  
                // ��  
                output[i][j] = Double.valueOf(String.valueOf(input[i][j]));  
                /*System.out.print(output[i][j]);  */
            }  
           /* System.out.println();  */
        }  
        return output;  
    }  
  
    public static double[] intToDoubleArray(int[] input) {  // ʵ��һάint������double��
        int length = input.length;  
        double[] output = new double[length];  
        for (int i = 0; i < length; i++)  
            output[i] = Double.valueOf(String.valueOf(input[i]));  
        return output;  
    }  
  
   /* public static void main(String[] args) {  
        int[][] test = { { 4, 5, 6 }, { 1, 2, 3 } };  
        MathTool.intToDoubleMatrix(test);  
    }  */
}  
