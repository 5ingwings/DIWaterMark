package com.scnu.sihao;
import java.util.Date;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.imageio.ImageIO;
public class View extends JFrame implements ActionListener {
private static final long serialVersionUID = 1L;
private int x,y;
private JFrame frame;
private JFileChooser chooser1,chooser2;
public int length=500,height=815;
// 原图像大小 可见水印的大小
private int oimgwidth,oimgheight,mimgwidth,mimgheight;
private JLabel label_1,label_2,rightLabel;
private java.io.File file1,file2;
private JButton b1,b2,b3,b4,b5,b7;
private int visibleWatermark;
// 原图
private String sourceImage="G:\\DIWM\\img\\background\\p2_2.png";
// 水印图
private String waterMarkImage="";
// 添加了水印的图片
private String resultImage="";
private int waterMarkX,waterMarkY;
static File fileFlag = new File("");
private String name;
// 提取出来的可见水印图像
private String returnVisibleMarkImage="";
//提取出来的不可见水印图像
private String returnWaterMarkImage="";
		public static void main(String[] args) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						View window = new View();
						window.frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		}

		//构造函数
		public View() {
			initFrame();
		}

		private void initFrame() {
			frame = new JFrame("WaterMark");
			frame.setBounds(200, 3, 1400,1024);  //窗体的X,Y 以及宽度与高度
			frame.getContentPane().setLayout(null);
			//设置窗口大小不能修改
			frame.setResizable(false);
			//设置窗口可见（默认不可见）
			frame.setVisible(true);
			//设置窗口关闭时，程序关闭
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			// 左侧要添加的水印图的label层 默认无图  放上面 因为先加载的在上面 并且要设置为空先 这样添加水印图片时 就可以不会把水印图置于下方了 因为先加载了这个label_2 就可以先添加图片再添加水印图片
			label_2= new JLabel("");
			label_2.setBounds(0, 0, 1024, 1024);
			frame.getContentPane().add(label_2);
			
			// 左侧要被处理的图片的label层，先默认加载一个背景图
			label_1 = new JLabel("");
		    // 用这个
		    file1=new java.io.File(sourceImage);
		    try{
			    java.awt.Image image = javax.imageio.ImageIO.read(file1);
			    javax.swing.ImageIcon icon = new javax.swing.ImageIcon(image);
			    label_1.setIcon(icon);
		    		}catch(java.io.IOException e){}
			label_1.setBounds(0, 0, 1024, 1024);
			frame.getContentPane().add(label_1);
			
			
			//java的文件选择器 
			chooser1 = new JFileChooser();
			//设置为默认打开当前目录
			chooser1.setCurrentDirectory(new File("."));
		
			chooser2 = new JFileChooser();
			chooser2.setCurrentDirectory(new File("."));
					
			//加载按钮
	        b1 = new JButton("添 加 图 片");
	        b1.setBackground(new Color(51, 255, 204));
	        b1.setBounds(1100, 54, 220, 50);
	        b1.setFont(new Font("楷体", Font.PLAIN, 26));
			frame.getContentPane().add(b1);
		
			b2 = new JButton("添加可见水印");
			b2.setBackground(Color.PINK);
			b2.setBounds(1100, 148, 220, 50);
			b2.setFont(new Font("楷体", Font.PLAIN, 26));
			frame.getContentPane().add(b2);
			
			b3 = new JButton("提取可见水印");
			b3.setBackground(new Color(255,165,0));
			b3.setBounds(1100, 245, 220, 50);
			b3.setFont(new Font("楷体", Font.PLAIN, 26));
			frame.getContentPane().add(b3);
			
			b4 = new JButton("添加不可见水印");
			b4.setBackground(new Color(218,112,214));
			b4.setBounds(1100, 343, 220, 50);
			b4.setFont(new Font("楷体", Font.PLAIN, 26));
			frame.getContentPane().add(b4);
			
			b5 = new JButton("提取不可见水印");
			b5.setBackground(new Color(0, 204, 255));
			b5.setBounds(1100, 441, 220, 50);
			b5.setFont(new Font("楷体", Font.PLAIN, 26));
			frame.getContentPane().add(b5);
			
			b7 = new JButton("合 成 图 片");
			b7.setBackground(new Color(255,255,0));
			b7.setBounds(1100, 539, 220, 50);
			b7.setFont(new Font("楷体", Font.PLAIN, 26));
			frame.getContentPane().add(b7);
			
			// 将按钮添加事件监听器
		    b1.addActionListener(this);
		    b2.addActionListener(this);
		    b3.addActionListener(this);
		    b4.addActionListener(this);
		    b5.addActionListener(this);
		    b7.addActionListener(this);
		    // 右边按钮的后面的背景图片   要放在button下面 后绘制的在下面
 			rightLabel = new JLabel("");
 			rightLabel.setIcon(new ImageIcon("G:\\DIWM\\img\\background\\p4.jpg" ));
 			rightLabel.setBounds(1024,0,376,1024);
 			frame.getContentPane().add(rightLabel);
 			
			// 添加移动图片功能
			label_2.addMouseListener(new MouseAdapter(){
				   public void mousePressed(MouseEvent e){
				    x=e.getX();
				    y=e.getY();
				   }
				  });
			label_2.addMouseMotionListener(new MouseMotionListener(){
				   public void mouseDragged(MouseEvent e) {
				    JLabel l = (JLabel)e.getSource();
				    l.setLocation(l.getX()+e.getX()-x,l.getY()+e.getY()-y);
				    waterMarkX=l.getBounds().x;
				    waterMarkY=l.getBounds().y;
				    System.out.println(l.getBounds().x+"  "+l.getBounds().y);
				   }
				   public void mouseMoved(MouseEvent e) {}
				  });

		/*	//添加缩放功能 往后面滚放大 num为1  前面缩小  num为-1
			label_1.addMouseWheelListener(new MouseWheelListener() {
				@Override
				public void mouseWheelMoved(MouseWheelEvent e) {
						// TODO Auto-generated method stub
						int num = e.getWheelRotation();
						//int x = (frame.getWidth() - label_1.getWidth()) / 2;
					//	int y = (frame.getHeight() - label_1.getHeight()) / 2;
						int width=label_1.getWidth()+ 2 * num;
						int height=label_1.getHeight()+ 2 * num;
						ImageIcon imageIcon = new ImageIcon(name);    // Icon由图片文件形成
						Image image = imageIcon.getImage();           // 但这个图片太大不适合做Icon
						// 为把它缩小点，先要取出这个Icon的image ,然后缩放到合适的大小
						//getScaledInstance 只能调用Image类
						Image img1 = image.getScaledInstance(width,height,Image.SCALE_SMOOTH);
						ImageIcon img2 = new ImageIcon(img1);
						//label_1 只能调用 ImageIcon 类
						label_1.setIcon(img2);
						label_1.setBounds(0,0, width, height);
						label_1.repaint();  //刷新屏幕
						
				 }
			});*/
		}
		
		 // ***************************事件处理***************************
		@Override
		public void actionPerformed(ActionEvent ae) {
			// 当点击第一个按钮 （添加图片）
			if (ae.getSource() == b1) {
				 //JAVA自带的打开文件管理器
	              int result = chooser1.showOpenDialog(null);
	              //添加点击打开时的操作 已经默认执行打开文件操作了
	              if(result == JFileChooser.APPROVE_OPTION){         
	            //添加的文件名 存于变量中
	            sourceImage = chooser1.getSelectedFile().getPath();
	              //将打开文件的位置给静态变量以便保存文件时可以快速定位 
	              fileFlag = new File(sourceImage);
	              //获取添加的图片的宽高
	              BufferedImage bufferedImage;
				try {
					bufferedImage = ImageIO.read(fileFlag); 
					 oimgwidth  = bufferedImage.getWidth();   
					 oimgheight = bufferedImage.getHeight(); 
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   
	              //将图片添加到相应的LABEL中
					// 用这个
				    file1=new java.io.File(sourceImage);
				    try{
				    java.awt.Image image = javax.imageio.ImageIO.read(file1);
				    javax.swing.ImageIcon icon = new javax.swing.ImageIcon(image);
				    label_1.setBounds(0,0,oimgwidth,oimgheight);
				    label_1.setIcon(icon);
				    }catch(java.io.IOException e){
				    }		
				}		             
		}
			
			 	  // 当点击第二个按钮 （添加可见水印）
			      else if(ae.getSource() == b2) {				
				  //JAVA自带的打开文件管理器
	              int result = chooser1.showOpenDialog(null);
	              //添加点击打开时的操作 已经默认执行打开文件操作了
	              if(result == JFileChooser.APPROVE_OPTION){         
	              //添加的文件名 存于变量中
	              waterMarkImage = chooser1.getSelectedFile().getPath();
	              //将打开文件的位置给静态变量以便保存文件时可以快速定位 
	              fileFlag = new File(waterMarkImage);
	              //获取添加水印的图片的宽高
	              BufferedImage bufferedImage;
				try {
					bufferedImage = ImageIO.read(fileFlag); 
					 mimgwidth  = bufferedImage.getWidth();   
					 mimgheight = bufferedImage.getHeight(); 
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   
	              //将图片添加到相应的LABEL中
					// 用这个
					file2=new java.io.File(waterMarkImage);
				    try{
				    java.awt.Image image = javax.imageio.ImageIO.read(file2);
				    javax.swing.ImageIcon icon = new javax.swing.ImageIcon(image);
				    label_2.setBounds(0,0,mimgwidth,mimgheight);
				    label_2.setIcon(icon);
				    // 设置visibleWatermark值为0 以便合成按钮可以转换条件为合成可见水印
				    visibleWatermark=0;
				    }catch(java.io.IOException e){
				    }
				}		             
			}
			// 当点击第三个按钮 （提取可见水印）
						else if(ae.getSource() == b3) {
							// 选择保存路径
							 chooser2.setCurrentDirectory(fileFlag);// 设置打开对话框的默认路径
					    	 chooser2.setSelectedFile(fileFlag);// 设置选中原来的文件
					    	  //JAVA自带的保存文件管理器
					    	  chooser2.showSaveDialog(null);
					    	  // 获取点击的文件名 但是你 可以在下面的输入框修改 得到的是输入框的值
					    	  returnVisibleMarkImage = chooser2.getSelectedFile().toString();
					    	  // 调用抽取水印的类方法
							ExtractVisibleMark extractVisibleMark = new ExtractVisibleMark();
							extractVisibleMark.start(resultImage,returnVisibleMarkImage,waterMarkX,waterMarkY,mimgwidth,mimgheight);
							// 再显示
							file2=new java.io.File(returnVisibleMarkImage);
						    try{
						    java.awt.Image image = javax.imageio.ImageIO.read(file2);
						    javax.swing.ImageIcon icon = new javax.swing.ImageIcon(image);
						    label_2.setBounds(0,0,mimgwidth,mimgheight);
						    label_2.setIcon(icon);
						    label_1.setIcon(null);
						    }catch(java.io.IOException e){
						    }
						}
			// 当点击第四个按钮 （添加不可见水印）
						else if(ae.getSource() == b4) {
							 //JAVA自带的打开文件管理器
							 int result = chooser1.showOpenDialog(null);
				              //添加点击打开时的操作 已经默认执行打开文件操作了
				              if(result == JFileChooser.APPROVE_OPTION){         
				              //添加的文件名 存于变量中
				              waterMarkImage = chooser1.getSelectedFile().getPath();
				              //将打开文件的位置给静态变量以便保存文件时可以快速定位 
				              fileFlag = new File(waterMarkImage);
				             //获取添加的图片的宽高
				              BufferedImage bufferedImage;
							try {
								bufferedImage = ImageIO.read(fileFlag); 
								 mimgwidth  = bufferedImage.getWidth();   
								 mimgheight = bufferedImage.getHeight(); 
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}   
				              //将图片添加到相应的LABEL中
								// 用这个
								file2=new java.io.File(waterMarkImage);
							    try{
							    java.awt.Image image = javax.imageio.ImageIO.read(file2);
							    javax.swing.ImageIcon icon = new javax.swing.ImageIcon(image);
							    label_2.setBounds(0,0,mimgwidth,mimgheight);
							    label_2.setIcon(icon);
							    // 设置visibleWatermark值为1 以便合成按钮可以转换条件为合成不可见水印
							    visibleWatermark=1;
							    }catch(java.io.IOException e){
							    }
							}		             
							
						}
						// 当点击第五个按钮 （提取不可见水印）
						else if(ae.getSource() == b5) {
							// 选择保存路径
							 chooser2.setCurrentDirectory(fileFlag);// 设置打开对话框的默认路径
					    	 chooser2.setSelectedFile(fileFlag);// 设置选中原来的文件
					    	  //JAVA自带的保存文件管理器
					    	  chooser2.showSaveDialog(null);
					    	  // 获取点击的文件名 但是你 可以在下面的输入框修改 得到的是输入框的值
					    	  returnWaterMarkImage = chooser2.getSelectedFile().toString();
					    	  ExtractWatermark extractWatermark = new ExtractWatermark();
					    	  extractWatermark.start(sourceImage,resultImage,returnWaterMarkImage,mimgwidth,mimgheight);
						}
						// 当点击合成按钮
						else if(ae.getSource() == b7) {
							// 选择保存路径
							 chooser2.setCurrentDirectory(fileFlag);// 设置打开对话框的默认路径
					    	 chooser2.setSelectedFile(fileFlag);// 设置选中原来的文件
					    	  //JAVA自带的保存文件管理器
					    	 chooser2.showSaveDialog(null);
					    	  // 获取点击的文件名 但是你 可以在下面的输入框修改 得到的是输入框的值
					    	  resultImage = chooser2.getSelectedFile().toString();
					    	 
							// 若是合成可见水印
							if(visibleWatermark==0) {	    	   
								// 调用AddVisibleWateramrk
								AddVisibleMark addVisibleMark = new AddVisibleMark();
								addVisibleMark.start(sourceImage,waterMarkImage,resultImage,waterMarkX,waterMarkY);		
							}else if(visibleWatermark==1){
								// 调用AddWaterMark
								AddWatermark addWatermark = new AddWatermark();
								addWatermark.start(sourceImage, waterMarkImage, resultImage);
							}else {
								
							}
						}
	}
}
