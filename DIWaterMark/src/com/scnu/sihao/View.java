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
// ԭͼ���С �ɼ�ˮӡ�Ĵ�С
private int oimgwidth,oimgheight,mimgwidth,mimgheight;
private JLabel label_1,label_2,rightLabel;
private java.io.File file1,file2;
private JButton b1,b2,b3,b4,b5,b7;
private int visibleWatermark;
// ԭͼ
private String sourceImage="G:\\DIWM\\img\\background\\p2_2.png";
// ˮӡͼ
private String waterMarkImage="";
// �����ˮӡ��ͼƬ
private String resultImage="";
private int waterMarkX,waterMarkY;
static File fileFlag = new File("");
private String name;
// ��ȡ�����Ŀɼ�ˮӡͼ��
private String returnVisibleMarkImage="";
//��ȡ�����Ĳ��ɼ�ˮӡͼ��
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

		//���캯��
		public View() {
			initFrame();
		}

		private void initFrame() {
			frame = new JFrame("WaterMark");
			frame.setBounds(200, 3, 1400,1024);  //�����X,Y �Լ������߶�
			frame.getContentPane().setLayout(null);
			//���ô��ڴ�С�����޸�
			frame.setResizable(false);
			//���ô��ڿɼ���Ĭ�ϲ��ɼ���
			frame.setVisible(true);
			//���ô��ڹر�ʱ������ر�
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			// ���Ҫ��ӵ�ˮӡͼ��label�� Ĭ����ͼ  ������ ��Ϊ�ȼ��ص������� ����Ҫ����Ϊ���� �������ˮӡͼƬʱ �Ϳ��Բ����ˮӡͼ�����·��� ��Ϊ�ȼ��������label_2 �Ϳ��������ͼƬ�����ˮӡͼƬ
			label_2= new JLabel("");
			label_2.setBounds(0, 0, 1024, 1024);
			frame.getContentPane().add(label_2);
			
			// ���Ҫ�������ͼƬ��label�㣬��Ĭ�ϼ���һ������ͼ
			label_1 = new JLabel("");
		    // �����
		    file1=new java.io.File(sourceImage);
		    try{
			    java.awt.Image image = javax.imageio.ImageIO.read(file1);
			    javax.swing.ImageIcon icon = new javax.swing.ImageIcon(image);
			    label_1.setIcon(icon);
		    		}catch(java.io.IOException e){}
			label_1.setBounds(0, 0, 1024, 1024);
			frame.getContentPane().add(label_1);
			
			
			//java���ļ�ѡ���� 
			chooser1 = new JFileChooser();
			//����ΪĬ�ϴ򿪵�ǰĿ¼
			chooser1.setCurrentDirectory(new File("."));
		
			chooser2 = new JFileChooser();
			chooser2.setCurrentDirectory(new File("."));
					
			//���ذ�ť
	        b1 = new JButton("�� �� ͼ Ƭ");
	        b1.setBackground(new Color(51, 255, 204));
	        b1.setBounds(1100, 54, 220, 50);
	        b1.setFont(new Font("����", Font.PLAIN, 26));
			frame.getContentPane().add(b1);
		
			b2 = new JButton("��ӿɼ�ˮӡ");
			b2.setBackground(Color.PINK);
			b2.setBounds(1100, 148, 220, 50);
			b2.setFont(new Font("����", Font.PLAIN, 26));
			frame.getContentPane().add(b2);
			
			b3 = new JButton("��ȡ�ɼ�ˮӡ");
			b3.setBackground(new Color(255,165,0));
			b3.setBounds(1100, 245, 220, 50);
			b3.setFont(new Font("����", Font.PLAIN, 26));
			frame.getContentPane().add(b3);
			
			b4 = new JButton("��Ӳ��ɼ�ˮӡ");
			b4.setBackground(new Color(218,112,214));
			b4.setBounds(1100, 343, 220, 50);
			b4.setFont(new Font("����", Font.PLAIN, 26));
			frame.getContentPane().add(b4);
			
			b5 = new JButton("��ȡ���ɼ�ˮӡ");
			b5.setBackground(new Color(0, 204, 255));
			b5.setBounds(1100, 441, 220, 50);
			b5.setFont(new Font("����", Font.PLAIN, 26));
			frame.getContentPane().add(b5);
			
			b7 = new JButton("�� �� ͼ Ƭ");
			b7.setBackground(new Color(255,255,0));
			b7.setBounds(1100, 539, 220, 50);
			b7.setFont(new Font("����", Font.PLAIN, 26));
			frame.getContentPane().add(b7);
			
			// ����ť����¼�������
		    b1.addActionListener(this);
		    b2.addActionListener(this);
		    b3.addActionListener(this);
		    b4.addActionListener(this);
		    b5.addActionListener(this);
		    b7.addActionListener(this);
		    // �ұ߰�ť�ĺ���ı���ͼƬ   Ҫ����button���� ����Ƶ�������
 			rightLabel = new JLabel("");
 			rightLabel.setIcon(new ImageIcon("G:\\DIWM\\img\\background\\p4.jpg" ));
 			rightLabel.setBounds(1024,0,376,1024);
 			frame.getContentPane().add(rightLabel);
 			
			// ����ƶ�ͼƬ����
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

		/*	//������Ź��� ��������Ŵ� numΪ1  ǰ����С  numΪ-1
			label_1.addMouseWheelListener(new MouseWheelListener() {
				@Override
				public void mouseWheelMoved(MouseWheelEvent e) {
						// TODO Auto-generated method stub
						int num = e.getWheelRotation();
						//int x = (frame.getWidth() - label_1.getWidth()) / 2;
					//	int y = (frame.getHeight() - label_1.getHeight()) / 2;
						int width=label_1.getWidth()+ 2 * num;
						int height=label_1.getHeight()+ 2 * num;
						ImageIcon imageIcon = new ImageIcon(name);    // Icon��ͼƬ�ļ��γ�
						Image image = imageIcon.getImage();           // �����ͼƬ̫���ʺ���Icon
						// Ϊ������С�㣬��Ҫȡ�����Icon��image ,Ȼ�����ŵ����ʵĴ�С
						//getScaledInstance ֻ�ܵ���Image��
						Image img1 = image.getScaledInstance(width,height,Image.SCALE_SMOOTH);
						ImageIcon img2 = new ImageIcon(img1);
						//label_1 ֻ�ܵ��� ImageIcon ��
						label_1.setIcon(img2);
						label_1.setBounds(0,0, width, height);
						label_1.repaint();  //ˢ����Ļ
						
				 }
			});*/
		}
		
		 // ***************************�¼�����***************************
		@Override
		public void actionPerformed(ActionEvent ae) {
			// �������һ����ť �����ͼƬ��
			if (ae.getSource() == b1) {
				 //JAVA�Դ��Ĵ��ļ�������
	              int result = chooser1.showOpenDialog(null);
	              //��ӵ����ʱ�Ĳ��� �Ѿ�Ĭ��ִ�д��ļ�������
	              if(result == JFileChooser.APPROVE_OPTION){         
	            //��ӵ��ļ��� ���ڱ�����
	            sourceImage = chooser1.getSelectedFile().getPath();
	              //�����ļ���λ�ø���̬�����Ա㱣���ļ�ʱ���Կ��ٶ�λ 
	              fileFlag = new File(sourceImage);
	              //��ȡ��ӵ�ͼƬ�Ŀ��
	              BufferedImage bufferedImage;
				try {
					bufferedImage = ImageIO.read(fileFlag); 
					 oimgwidth  = bufferedImage.getWidth();   
					 oimgheight = bufferedImage.getHeight(); 
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   
	              //��ͼƬ��ӵ���Ӧ��LABEL��
					// �����
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
			
			 	  // ������ڶ�����ť ����ӿɼ�ˮӡ��
			      else if(ae.getSource() == b2) {				
				  //JAVA�Դ��Ĵ��ļ�������
	              int result = chooser1.showOpenDialog(null);
	              //��ӵ����ʱ�Ĳ��� �Ѿ�Ĭ��ִ�д��ļ�������
	              if(result == JFileChooser.APPROVE_OPTION){         
	              //��ӵ��ļ��� ���ڱ�����
	              waterMarkImage = chooser1.getSelectedFile().getPath();
	              //�����ļ���λ�ø���̬�����Ա㱣���ļ�ʱ���Կ��ٶ�λ 
	              fileFlag = new File(waterMarkImage);
	              //��ȡ���ˮӡ��ͼƬ�Ŀ��
	              BufferedImage bufferedImage;
				try {
					bufferedImage = ImageIO.read(fileFlag); 
					 mimgwidth  = bufferedImage.getWidth();   
					 mimgheight = bufferedImage.getHeight(); 
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}   
	              //��ͼƬ��ӵ���Ӧ��LABEL��
					// �����
					file2=new java.io.File(waterMarkImage);
				    try{
				    java.awt.Image image = javax.imageio.ImageIO.read(file2);
				    javax.swing.ImageIcon icon = new javax.swing.ImageIcon(image);
				    label_2.setBounds(0,0,mimgwidth,mimgheight);
				    label_2.setIcon(icon);
				    // ����visibleWatermarkֵΪ0 �Ա�ϳɰ�ť����ת������Ϊ�ϳɿɼ�ˮӡ
				    visibleWatermark=0;
				    }catch(java.io.IOException e){
				    }
				}		             
			}
			// �������������ť ����ȡ�ɼ�ˮӡ��
						else if(ae.getSource() == b3) {
							// ѡ�񱣴�·��
							 chooser2.setCurrentDirectory(fileFlag);// ���ô򿪶Ի����Ĭ��·��
					    	 chooser2.setSelectedFile(fileFlag);// ����ѡ��ԭ�����ļ�
					    	  //JAVA�Դ��ı����ļ�������
					    	  chooser2.showSaveDialog(null);
					    	  // ��ȡ������ļ��� ������ �����������������޸� �õ�����������ֵ
					    	  returnVisibleMarkImage = chooser2.getSelectedFile().toString();
					    	  // ���ó�ȡˮӡ���෽��
							ExtractVisibleMark extractVisibleMark = new ExtractVisibleMark();
							extractVisibleMark.start(resultImage,returnVisibleMarkImage,waterMarkX,waterMarkY,mimgwidth,mimgheight);
							// ����ʾ
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
			// ��������ĸ���ť ����Ӳ��ɼ�ˮӡ��
						else if(ae.getSource() == b4) {
							 //JAVA�Դ��Ĵ��ļ�������
							 int result = chooser1.showOpenDialog(null);
				              //��ӵ����ʱ�Ĳ��� �Ѿ�Ĭ��ִ�д��ļ�������
				              if(result == JFileChooser.APPROVE_OPTION){         
				              //��ӵ��ļ��� ���ڱ�����
				              waterMarkImage = chooser1.getSelectedFile().getPath();
				              //�����ļ���λ�ø���̬�����Ա㱣���ļ�ʱ���Կ��ٶ�λ 
				              fileFlag = new File(waterMarkImage);
				             //��ȡ��ӵ�ͼƬ�Ŀ��
				              BufferedImage bufferedImage;
							try {
								bufferedImage = ImageIO.read(fileFlag); 
								 mimgwidth  = bufferedImage.getWidth();   
								 mimgheight = bufferedImage.getHeight(); 
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}   
				              //��ͼƬ��ӵ���Ӧ��LABEL��
								// �����
								file2=new java.io.File(waterMarkImage);
							    try{
							    java.awt.Image image = javax.imageio.ImageIO.read(file2);
							    javax.swing.ImageIcon icon = new javax.swing.ImageIcon(image);
							    label_2.setBounds(0,0,mimgwidth,mimgheight);
							    label_2.setIcon(icon);
							    // ����visibleWatermarkֵΪ1 �Ա�ϳɰ�ť����ת������Ϊ�ϳɲ��ɼ�ˮӡ
							    visibleWatermark=1;
							    }catch(java.io.IOException e){
							    }
							}		             
							
						}
						// ������������ť ����ȡ���ɼ�ˮӡ��
						else if(ae.getSource() == b5) {
							// ѡ�񱣴�·��
							 chooser2.setCurrentDirectory(fileFlag);// ���ô򿪶Ի����Ĭ��·��
					    	 chooser2.setSelectedFile(fileFlag);// ����ѡ��ԭ�����ļ�
					    	  //JAVA�Դ��ı����ļ�������
					    	  chooser2.showSaveDialog(null);
					    	  // ��ȡ������ļ��� ������ �����������������޸� �õ�����������ֵ
					    	  returnWaterMarkImage = chooser2.getSelectedFile().toString();
					    	  ExtractWatermark extractWatermark = new ExtractWatermark();
					    	  extractWatermark.start(sourceImage,resultImage,returnWaterMarkImage,mimgwidth,mimgheight);
						}
						// ������ϳɰ�ť
						else if(ae.getSource() == b7) {
							// ѡ�񱣴�·��
							 chooser2.setCurrentDirectory(fileFlag);// ���ô򿪶Ի����Ĭ��·��
					    	 chooser2.setSelectedFile(fileFlag);// ����ѡ��ԭ�����ļ�
					    	  //JAVA�Դ��ı����ļ�������
					    	 chooser2.showSaveDialog(null);
					    	  // ��ȡ������ļ��� ������ �����������������޸� �õ�����������ֵ
					    	  resultImage = chooser2.getSelectedFile().toString();
					    	 
							// ���Ǻϳɿɼ�ˮӡ
							if(visibleWatermark==0) {	    	   
								// ����AddVisibleWateramrk
								AddVisibleMark addVisibleMark = new AddVisibleMark();
								addVisibleMark.start(sourceImage,waterMarkImage,resultImage,waterMarkX,waterMarkY);		
							}else if(visibleWatermark==1){
								// ����AddWaterMark
								AddWatermark addWatermark = new AddWatermark();
								addWatermark.start(sourceImage, waterMarkImage, resultImage);
							}else {
								
							}
						}
	}
}
