package com.will.opencv.opencv_test.app;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.Random;

import static org.opencv.core.CvType.CV_8U;
import static org.opencv.highgui.HighGui.waitKey;
import static org.opencv.imgproc.Imgproc.*;

public class ImgUseCase {

    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    public static void getTickCount(String[] args) {
        //该函数从0开始计时，返回自设备启动后的毫秒数（不含系统暂停时间）。
        long tickCount = Core.getTickCount();
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        Mat imread = Imgcodecs.imread("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\1.jpg", Imgcodecs.IMREAD_GRAYSCALE);
        double v = (Core.getTickCount() - tickCount) / Core.getTickFrequency();
        System.out.println(v);
    }

    /**
     * 图像的线性混合操作
     * 加，权重加，乘
     *
     * @param args
     */
    public static void 线性混合(String[] args) {
        //该函数从0开始计时，返回自设备启动后的毫秒数（不含系统暂停时间）。
        long tickCount = Core.getTickCount();
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        Mat imread = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\500750.jpg",
                Imgcodecs.IMREAD_COLOR);
        Mat m2 = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\5007502.jpg",
                Imgcodecs.IMREAD_COLOR);
        Mat mat = new Mat();
        Core.addWeighted(imread, 0.5, m2, 0.5, 0.0, mat);
//        Core.add(imread,m2,mat);
//        Core.multiply(imread,m2,mat);
        HighGui.namedWindow("win1", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win1", mat);

        int i = waitKey(0);
    }

    /**
     * 亮度和对比度
     * <p>
     * 点操作：亮度
     * 区域操作：卷积，整体特征提取，梯度的计算，更深入的模式匹配，模糊，平滑
     *
     * @param args
     */
    public static void 亮度和对比度(String[] args) {
        //该函数从0开始计时，返回自设备启动后的毫秒数（不含系统暂停时间）。
        long tickCount = Core.getTickCount();
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        Mat imread = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\500750.jpg",
                Imgcodecs.IMREAD_COLOR);
        Mat m2 = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\5007502.jpg",
                Imgcodecs.IMREAD_COLOR);
        Mat mat = new Mat();
        Core.addWeighted(imread, 0.5, m2, 0.5, 0.0, mat);
//        Core.add(imread,m2,mat);
//        Core.multiply(imread,m2,mat);
        HighGui.namedWindow("win1", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win1", mat);

        int i = waitKey(0);
    }


    /**
     * 绘制形状与文字
     * point(x,y)
     * scalar 四个元素的一个向量
     *
     * @param args
     */
    public static void 绘制形状与文字(String[] args) {
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        Mat imread = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\1.jpg",
                Imgcodecs.IMREAD_COLOR);

        Imgproc.line(imread, new Point(350, 45), new Point(350, 588), new Scalar(200, 299, 68), Imgproc.LINE_4);
        Imgproc.rectangle(imread, new Rect(50, 200, 400, 588), new Scalar(200, 299, 68), Imgproc.LINE_AA);
        Imgproc.ellipse(imread, new Point(300, 400), new Size(100, 200), 90, 0, 360, new Scalar(200, 299, 68), Imgproc.LINE_4);
        Imgproc.circle(imread, new Point(300, 400), 300, new Scalar(200, 299, 68), Imgproc.LINE_4);
        //绘制多边形
//        Imgproc.polylines();
        Imgproc.putText(imread, "will", new Point(300, 400), Imgproc.FONT_HERSHEY_DUPLEX, 1.0, new Scalar(200, 299, 68));


        HighGui.namedWindow("win1", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win1", imread);

        int i = waitKey(0);
    }

    /**
     * 随机生成颜色和线条
     * point(x,y)
     * scalar 四个元素的一个向量
     *
     * @param args
     */
    public static void 随机生成颜色和线条(String[] args) {
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        Mat bgImage = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\1.jpg",
                Imgcodecs.IMREAD_COLOR);
        Random rng = new Random(100);
        HighGui.namedWindow("win1", HighGui.WINDOW_AUTOSIZE);
        for (int i = 0; i < 100000; i++) {
            int x = rng.nextInt(bgImage.cols());
            int x1 = rng.nextInt(bgImage.cols());
            int y = rng.nextInt(bgImage.rows());
            int y1 = rng.nextInt(bgImage.rows());
            Scalar color = new Scalar(rng.nextInt(255), rng.nextInt(255), rng.nextInt(255));

            Imgproc.line(bgImage, new Point(x, y), new Point(x1, y1), color, 1, 8);
            HighGui.imshow("win1", bgImage);
            int i1 = waitKey(10);
            System.out.println(i1);
            if (i1 > 0) {
                System.out.println();
                break;
            }
        }
    }

    /**
     * blue
     * 滤波算法有均值滤波、中值滤波以及高斯滤波
     * <p>
     * 均值滤波 	使用模板内所有像素的平均值代替模板中心像素灰度值
     * 易收到噪声的干扰，不能完全消除噪声，只能相对减弱噪声
     * 中值滤波 	计算模板内所有像素中的中值，并用所计算出来的中值体改模板中心像素的灰度值
     * 对噪声不是那么敏感，能够较好的消除椒盐噪声，但是容易导致图像的不连续性
     * 高斯滤波 	对图像邻域内像素进行平滑时，邻域内不同位置的像素被赋予不同的权值
     * 对图像进行平滑的同时，同时能够更多的保留图像的总体灰度分布特征
     */
    public static void blue滤波算法(String[] args) {
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        Mat imread = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\1.jpg",
                Imgcodecs.IMREAD_COLOR);
        Mat mat = new Mat();
        imread.copyTo(mat);

//        Imgproc.blur(imread,imread,new Size(3,3));
        Imgproc.GaussianBlur(imread, imread, new Size(3, 3), 5, 5);

        HighGui.namedWindow("win1", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win1", mat);
        HighGui.namedWindow("win2", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win2", imread);
        int i = waitKey(0);
    }

    /**
     * 中指滤波和双边滤波
     * 双边滤波相比于高斯模糊，会保留轮廓的信息
     * 均值模糊无法克服边缘像素信息丢失缺陷。原因是均值滤波是基于平均权重
     * <p>
     * 高斯模糊部分克服了该缺陷，但是无法完全避免，因为没有考虑像素值的不同
     * <p>
     * 高斯双边模糊 – 是边缘保留的滤波方法，避免了边缘信息丢失，保留了图像轮廓不变
     *
     * @param args
     */
    public static void 双边滤波相比于高斯模糊(String[] args) {
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        Mat imread = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\1.jpg",
                Imgcodecs.IMREAD_COLOR);
        Mat mat = new Mat();
        imread.copyTo(mat);

//        Imgproc.blur(imread,imread,new Size(3,3));
//        Imgproc.GaussianBlur(imread,imread,new Size(3,3),5,5);

//        Imgproc.medianBlur(imread,imread,3);
//高斯双边模糊  ：半径，像素差值，半径小于零sigmaspace计算d
        Imgproc.bilateralFilter(imread, mat, 15, 150, 3);

        HighGui.namedWindow("win1", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win1", mat);
        HighGui.namedWindow("win2", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win2", imread);
        int i = waitKey(0);
    }

    /**
     * 人脸ps
     * 先进行双边高斯模糊，再进行锐化
     *
     * @param args
     */
    public static void 人脸ps(String[] args) {
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        Mat imread = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\1.jpg",
                Imgcodecs.IMREAD_COLOR);
        Mat mat = new Mat();
        imread.copyTo(mat);

        Imgproc.bilateralFilter(imread, mat, 15, 150, 3);

        int[] b = {0, -1, 0, -1, 5, -1, 0, -1, 0};
        Mat kernel = new Mat(3, 3, CvType.CV_32S);
        kernel.put(0, 0, b);

        Mat mat1 = new Mat();
        Imgproc.filter2D(mat, mat1, -1, kernel);

        HighGui.namedWindow("win1", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win1", mat);
        HighGui.namedWindow("win2", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win2", imread);
        HighGui.namedWindow("win3", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win3", mat1);
        int i = waitKey(0);
    }

    /**
     * 图像的形态学操作（膨胀与腐蚀，开和闭）
     * 膨胀与腐蚀
     * 进行膨胀操作时，将内核 B 划过图像,将内核 B 覆盖区域的最大相素值提取，并代替锚点位置的相素
     *进行腐蚀操作时，将内核 B 划过图像,将内核 B 覆盖区域的最小相素值提取，并代替锚点位置的相素
     * @param args
     */
    public static void 膨胀与腐蚀(String[] args) {
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        Mat imread = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\1.jpg",
                Imgcodecs.IMREAD_COLOR);
//        createTrackbar  javaapi不支持这个方法

//getStructuringElement函数会返回指定形状和尺寸的结构元素
        Mat kernel = Imgproc.getStructuringElement(MORPH_RECT, new Size(3, 3));
        Mat mat1 = new Mat(); Mat mat2 = new Mat();
        Imgproc.erode(imread,mat1,kernel);
//        Imgproc.dilate(imread,mat2,kernel);

        HighGui.namedWindow("win1", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win1", imread);
        HighGui.namedWindow("win2", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win2", mat1);
        HighGui.namedWindow("win3", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win3", mat2);
        int i = waitKey(0);
    }

    /**
     * 图像的形态学操作（膨胀与腐蚀，开和闭）
     *
     * 开操作- open
     * 先腐蚀后膨胀
     * 可以去掉小的对象，假设对象是前景色，背景是黑色
     *
     *  OPT – CV_MOP_OPEN/ CV_MOP_CLOSE/ CV_MOP_GRADIENT / CV_MOP_TOPHAT/ CV_MOP_BLACKHAT 形态学操作类型
     * @param args
     */
    public static void 图像的形态学操作(String[] args) {
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        Mat imread = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\1.jpg",
                Imgcodecs.IMREAD_COLOR);
//        createTrackbar  javaapi不支持这个方法

        //getStructuringElement函数会返回指定形状和尺寸的结构元素
        Mat kernel = Imgproc.getStructuringElement(MORPH_RECT, new Size(3, 3));
        Mat mat1 = new Mat();
        Imgproc.morphologyEx(imread,mat1,MORPH_OPEN,kernel);

        HighGui.namedWindow("win1", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win1", imread);
        HighGui.namedWindow("win2", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win2", mat1);
        int i = waitKey(0);
    }

    /**
     * 形态学操作应用-提取水平与垂直线
     *
     *  OPT – CV_MOP_OPEN/ CV_MOP_CLOSE/ CV_MOP_GRADIENT / CV_MOP_TOPHAT/ CV_MOP_BLACKHAT 形态学操作类型
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        Mat imread = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\2.png",
                Imgcodecs.IMREAD_COLOR);
        Mat mat1 = new Mat();
        Imgproc.cvtColor(imread,imread,Imgproc.COLOR_BGR2GRAY);
        Core.bitwise_not(imread,mat1);
        Mat mat2 = new Mat();

        //二值图像（Binary Image）是指将图像上的每一个像素只有两种可能的取值或灰度等级状态
        Imgproc.adaptiveThreshold(mat1,mat2,255,ADAPTIVE_THRESH_GAUSSIAN_C,THRESH_BINARY,15, -2);

        //getStructuringElement函数会返回指定形状和尺寸的结构元素
        Mat hline = Imgproc.getStructuringElement(MORPH_RECT, new Size(imread.height()/8, 1));
        Mat yline = Imgproc.getStructuringElement(MORPH_RECT, new Size(1, imread.width()/8));

        Mat txt = Imgproc.getStructuringElement(MORPH_RECT, new Size(4, 4));
        Mat mat4 = new Mat();
        Mat mat5 = new Mat();
        /*Mat temp = new Mat();Imgproc.erode(mat2,temp,hline);
        Imgproc.dilate(temp,mat4,hline);*/
        Imgproc.morphologyEx(mat2,mat4,MORPH_OPEN,txt);
        Core.bitwise_not(mat4,mat5);
//        Imgproc.blur(mat5, mat5, new Size(2, 2));

        HighGui.namedWindow("win0", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win0", imread);
        HighGui.namedWindow("mat1", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("mat1", mat1);
        HighGui.namedWindow("mat2", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("mat2", mat2);
        HighGui.namedWindow("mat4", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("mat4", mat4);
        HighGui.namedWindow("mat5", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("mat5", mat5);
        int i = waitKey(0);
    }
}
