package com.will.opencv.opencv_test.app;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUI extends JComponent {
	private BufferedImage image;
	public ImageUI() {
		this.image = null;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D)g;
		if(image == null) {
			g2d.setPaint(Color.BLACK);
			g2d.fillRect(0, 0, this.getWidth(), this.getHeight());
		}else {
			g2d.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
		}
	}
	
	public void imshow(String title, Mat src) {
		this.image = convert2BufferedImage(src);
		JDialog ui = new JDialog();
		ui.getContentPane().setLayout(new BorderLayout());
		ui.getContentPane().add(this, BorderLayout.CENTER);
		ui.setTitle(title);
		ui.setSize(image.getWidth(), image.getHeight());
		ui.setVisible(true);
		this.repaint();		//重新加载
	}
 
	private BufferedImage convert2BufferedImage(Mat src) {
		// TODO Auto-generated method stub
		int width = src.cols();
		int height = src.rows();
		int dims = src.channels();
		byte[] data = new byte[width * height * dims];
		src.get(0, 0, data);
		int[] pixels = new int[width * height];
		
		BufferedImage bi = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		int r = 0, g = 0, b = 0;
		int index = 0;
		for(int row = 0; row < height; row++) {
			for(int col = 0; col < width; col++) {
				index = row * width * dims + col * dims;
				if(dims == 3) {
					b = data[index]&0xff;
					g = data[index+1]&0xff;
					r = data[index+2]&0xff;
					pixels[row * width + col] = ((255&0xff) << 24) | ((r&0xff) << 16) | ((g&0xff) << 8) | (b&0xff);
				}else if(dims == 1) {
					b = data[index]&0xff;
					pixels[row * width + col] = ((255&0xff) << 24) | ((b&0xff) << 16) | ((b&0xff) << 8) | (b&0xff);
				}
			}
		}
		bi.getRaster().setDataElements(0, 0, width, height, pixels);
		return bi;
	}

	public static Mat array2Mat(double[][][] data) {

		int height = data.length;
		int width = data[0].length;
		Mat mat = new Mat(height, width, CvType.CV_8UC3);
		double[] mat_data = new double[width * height * 3];

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				mat_data[i * width * 3 + j * 3] = data[i][j][0];
				mat_data[i * width * 3 + j * 3 + 1] = data[i][j][1];
				mat_data[i * width * 3 + j * 3 + 2] = data[i][j][2];
			}
		}
		mat.put(0, 0, mat_data);
		return mat;
        /*int height = data.length;
        int width = data[0].length;
        Mat mat = new Mat(height, width, CvType.CV_8UC3);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                mat.put(i, j, data[i][j]);
            }
        }
        return mat;*/
	}

	public static double[][][] matGray2Array(Mat mat) {

		int width = mat.cols();
		int height = mat.rows();
		double[][][] data = new double[height][width][1];
		byte[] grayData = new byte[height * width];
		mat.get(0, 0, grayData);
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				data[i][j][0] = grayData[(i * width + j)];
				data[i][j][0] = data[i][j][0] >= 0 ? data[i][j][0] : data[i][j][0] + 256;
//                data[i][j] = mat.get(i, j);
			}
		}
		return data;
	}


}