package com.will.opencv.opencv_test.app;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.Objdetect;

import java.util.ArrayList;
import java.util.List;

public class UseCase {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }


    public static void t1(String[] args) {
        Mat img = Imgcodecs.imread(""); //读图像跟C++一样只是多了Highgui.。
        Imgcodecs.imwrite("", img);//写图像
        Mat img2 = new Mat();
        Imgproc.blur(img, img2, new Size(3, 3));//光滑处理 多了Imgproc.
        Imgproc.cvtColor(img, img2, Imgproc.COLOR_BGR2GRAY);//RGB==>Gray
        Imgproc.equalizeHist(img, img2);  //直方图均衡化
        List<Mat> images = new ArrayList<Mat>();
        Core.split(img, images);//RGB通道分离 多了Core.
        Mat mat = images.get(2);//获得第二通道分量
        Core.merge(images, img);//RGB通道合并

//Java一维数组给Mat赋值 和从Mat中提取像素值保存在一维数组中
        float data[] = {1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4, 5, 5, 5, 6, 6, 6, 7, 7, 7, 8, 8, 8};
        Mat mat_gray = new Mat(4, 6, CvType.CV_32FC1);
        Mat mat_color = new Mat(2, 4, CvType.CV_32FC3);
        mat_gray.put(0, 0, data);//通过一维数组给灰度图像赋值
        mat_color.put(0, 0, data);//通过一维数组给彩色图像赋值
        float data2[] = new float[mat_color.rows() * mat_color.cols() * mat_color.channels()];
        mat_color.get(0, 0, data2);//把mat_color中的像素值保存在一维数组中。且以为数组的类型必须跟图像数据类型CvType一致，且数组的大小得确定大于等于行数*列数*通道数。
//需要注意的是，java中Mat对数据类型很敏感。具体问题具体尝试
/****************************************************
 CV_32F  float
 CV_16S  short
 CV_8S   char   byte
 CV_8U - 8-bit unsigned integers ( 0..255 )    uchar
 CV_8S - 8-bit signed integers ( -128..127 )   byte(java)
 CV_16U - 16-bit unsigned integers ( 0..65535 )
 CV_16S - 16-bit signed integers ( -32768..32767 )
 CV_32S - 32-bit signed integers ( -2147483648..2147483647 )
 CV_32F - 32-bit ﬂoating-point numbers ( -FLT_MAX..FLT_MAX, INF, NAN )
 CV_64F - 64-bit ﬂoating-point numbers ( -DBL_MAX..DBL_MAX, INF, NAN )
 ***************************************************/


    }

    public static void main(String[] args) {
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        Mat imread = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\1.jpg"
                , Imgcodecs.IMREAD_COLOR);
        Mat mat = doBackgroundRemoval(imread);
        Imgcodecs.imwrite("2.jpg", mat);

        HighGui.namedWindow("win1", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win1", imread);
        HighGui.namedWindow("win2", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win2", mat);

        int i = HighGui.waitKey(0);
    }
    /**
     * 背景去除 简单案列，只适合背景单一的图像
     * @param frame
     * @return
     */
    private static Mat doBackgroundRemoval(Mat frame)
    {
        // init
        Mat hsvImg = new Mat();
        List<Mat> hsvPlanes = new ArrayList<>();
        Mat thresholdImg = new Mat();
        int thresh_type = Imgproc.THRESH_BINARY_INV;
// threshold the image with the average hue value
        hsvImg.create(frame.size(), CvType.CV_8U);
        Imgproc.cvtColor(frame, hsvImg,
                Imgproc.COLOR_BGR2HSV);
        Core.split(hsvImg, hsvPlanes);

        // get the average hue value of the image
        Scalar average=Core.mean(hsvPlanes.get(0));
        double threshValue =average.val[0];
        Imgproc.threshold(hsvPlanes.get(0), thresholdImg, threshValue, 179.0, thresh_type);
        Imgproc.blur(thresholdImg, thresholdImg, new Size(5, 5));
        // dilate to fill gaps, erode to smooth edges
        Imgproc.dilate(thresholdImg, thresholdImg, new Mat(), new Point(-1, -1), 1);
        Imgproc.erode(thresholdImg, thresholdImg, new Mat(), new Point(-1, -1), 3);
        Imgproc.threshold(thresholdImg, thresholdImg, threshValue, 179.0, Imgproc.THRESH_BINARY);
        // create the new image
        Mat foreground = new Mat(frame.size(), CvType.CV_8UC3, new Scalar(255, 255, 255));
        thresholdImg.convertTo(thresholdImg,CvType.CV_8U);
        frame.copyTo(foreground, thresholdImg);//掩膜图像复制
        return foreground;
    }






}
