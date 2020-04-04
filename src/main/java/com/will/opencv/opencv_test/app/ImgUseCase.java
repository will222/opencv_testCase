package com.will.opencv.opencv_test.app;

import jdk.internal.dynalink.linker.LinkerServices;
import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.*;

import static org.opencv.core.Core.NORM_MINMAX;
import static org.opencv.core.CvType.*;
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
     * 进行腐蚀操作时，将内核 B 划过图像,将内核 B 覆盖区域的最小相素值提取，并代替锚点位置的相素
     *
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
        Mat mat1 = new Mat();
        Mat mat2 = new Mat();
        Imgproc.erode(imread, mat1, kernel);
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
     * <p>
     * 开操作- open
     * 先腐蚀后膨胀
     * 可以去掉小的对象，假设对象是前景色，背景是黑色
     * <p>
     * OPT – CV_MOP_OPEN/ CV_MOP_CLOSE/ CV_MOP_GRADIENT / CV_MOP_TOPHAT/ CV_MOP_BLACKHAT 形态学操作类型
     *
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
        Imgproc.morphologyEx(imread, mat1, MORPH_OPEN, kernel);

        HighGui.namedWindow("win1", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win1", imread);
        HighGui.namedWindow("win2", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win2", mat1);
        int i = waitKey(0);
    }

    /**
     * 形态学操作应用-提取水平与垂直线
     * <p>
     * OPT – CV_MOP_OPEN/ CV_MOP_CLOSE/ CV_MOP_GRADIENT / CV_MOP_TOPHAT/ CV_MOP_BLACKHAT 形态学操作类型
     *
     * @param args
     */
    public static void 提取水平与垂直线(String[] args) {
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        Mat imread = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\2.png",
                Imgcodecs.IMREAD_COLOR);
        Mat mat1 = new Mat();
        Imgproc.cvtColor(imread, imread, Imgproc.COLOR_BGR2GRAY);
        Core.bitwise_not(imread, mat1);
        Mat mat2 = new Mat();

        //二值图像（Binary Image）是指将图像上的每一个像素只有两种可能的取值或灰度等级状态
        Imgproc.adaptiveThreshold(mat1, mat2, 255, ADAPTIVE_THRESH_GAUSSIAN_C, THRESH_BINARY, 15, -2);

        //getStructuringElement函数会返回指定形状和尺寸的结构元素
        Mat hline = Imgproc.getStructuringElement(MORPH_RECT, new Size(imread.height() / 8, 1));
        Mat yline = Imgproc.getStructuringElement(MORPH_RECT, new Size(1, imread.width() / 8));

        Mat txt = Imgproc.getStructuringElement(MORPH_RECT, new Size(4, 4));
        Mat mat4 = new Mat();
        Mat mat5 = new Mat();
        /*Mat temp = new Mat();Imgproc.erode(mat2,temp,hline);
        Imgproc.dilate(temp,mat4,hline);*/
        Imgproc.morphologyEx(mat2, mat4, MORPH_OPEN, txt);
        Core.bitwise_not(mat4, mat5);
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

    /**
     * 高斯金子塔的生成过程分为两步：
     * - 对当前层进行高斯模糊
     * - 删除当前层的偶数行与列
     * 即可得到上一层的图像，这样上一层跟下一层相比，都只有它的1/4大小
     * <p>
     * 高斯不同：就是把同一张图像在不同的参数下做高斯模糊之后的结果相减，得到的输出图像
     * <p>
     * 上采样(cv::pyrUp) – zoom in 放大 2倍
     * 降采样 (cv::pyrDown) – zoom out 缩小 2倍
     * <p>
     * 高斯不同(Difference of Gaussian-DOG)  高斯差值，归一化显示
     *
     * @param args
     */
    public static void 上采样降采样高斯不同(String[] args) {
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        Mat imread = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\1.jpg",
                Imgcodecs.IMREAD_COLOR);

        Mat mat1 = new Mat();
        Mat up = new Mat();
        Mat down = new Mat();
        Mat f = new Mat();
        imread.copyTo(mat1);
//        Imgproc.pyrUp(imread,up);
//        Imgproc.pyrDown(imread,down);

        Imgproc.cvtColor(imread, imread, COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(imread, up, new Size(5, 5), 0);
        Imgproc.GaussianBlur(up, down, new Size(5, 5), 0);
        Core.subtract(up, down, f);
        Mat r = new Mat();
        Core.normalize(f, r, 255, 0, NORM_MINMAX);
        HighGui.namedWindow("win0", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win0", mat1);
        HighGui.namedWindow("win1", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win1", r);
        int i = waitKey(0);
    }

    /**
     * 阈值 是什么？简单点说是把图像分割的标尺
     * 阈值二值化
     * 阈值反二值化
     * 截断
     * 阈值取零
     * 阈值反取零
     *
     * @param args
     */
    public static void 阈值(String[] args) {
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        Mat imread = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\1.jpg",
                Imgcodecs.IMREAD_COLOR);
        HighGui.namedWindow("win0", HighGui.WINDOW_AUTOSIZE);
        Mat mat1 = new Mat();
        imread.copyTo(mat1);
        Imgproc.cvtColor(imread, imread, COLOR_BGR2GRAY);
        Imgproc.threshold(imread, imread, 127, 255, THRESH_TOZERO);

        HighGui.imshow("win0", imread);
        HighGui.namedWindow("win1", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win1", mat1);
        int i = waitKey(0);
    }

    /**
     * 模糊，提取边缘，进行图像的锐化
     * robert算子 2* 2  +1 0 0  -1   左右45度进行边缘凸显
     * sobel算子   x或者y方向进行边缘凸显
     * 拉普拉斯算子   进行全方位的边缘凸显
     *
     * @param args
     */
    public static void 自定义线性滤波(String[] args) {
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        Mat imread = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\1.jpg",
                Imgcodecs.IMREAD_COLOR);
        Mat mat1 = new Mat();
        imread.copyTo(mat1);
        // Sobel X 方向
        // Mat kernel_x = (Mat_<int>(3, 3) << -1, 0, 1, -2,0,2,-1,0,1);
        // filter2D(src, dst, -1, kernel_x, Point(-1, -1), 0.0);

        // Sobel Y 方向
        // Mat yimg;
        // Mat kernel_y = (Mat_<int>(3, 3) << -1, -2, -1, 0,0,0, 1,2,1);
        // filter2D(src, yimg, -1, kernel_y, Point(-1, -1), 0.0);

        // 拉普拉斯算子
        //Mat kernel_y = (Mat_<int>(3, 3) << 0, -1, 0, -1, 4, -1, 0, -1, 0);
        //filter2D(src, dst, -1, kernel_y, Point(-1, -1), 0.0);
        int index = 1;
        HighGui.namedWindow("win0", HighGui.WINDOW_AUTOSIZE);
        while (true) {
            int size = 2 + index / 15;
            Mat ones = Mat.ones(new Size(size, size), CV_32F);
            Core.divide(ones, ones, ones);
            Imgproc.filter2D(imread, imread, -1, ones);
            index++;
            HighGui.imshow("win0", imread);
            int i = waitKey(2000);
            System.out.println(size);
        }
    }

    /**
     * 图像卷积的时候边界像素，不能被卷积操作，原因在于边界像素没有完全跟kernel重叠，
     * 所以当3x3滤波时候有1个像素的边缘没有被处理，5x5滤波的时候有2个像素的边缘没有被处理。
     *
     * @param args
     */
    public static void 卷积边缘处理(String[] args) {
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        Mat imread = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\1.jpg",
                Imgcodecs.IMREAD_COLOR);
        Mat mat1 = new Mat();
        imread.copyTo(mat1);
        Core.copyMakeBorder(imread, imread, 5, 5, 5, 5, Core.BORDER_CONSTANT, new Scalar(100));
        Imgproc.GaussianBlur(imread, imread, new Size(15, 15), 0);
        HighGui.imshow("win0", imread);
        HighGui.namedWindow("win1", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win1", mat1);
        int i = waitKey(0);
    }

    /**
     * sobel算子（一阶微分算子）：对图像进行xy方向的边缘凸显
     * 边缘是图像的特征之一：一元微分求导
     *
     * @param args
     */
    public static void sobel(String[] args) {
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        Mat imread = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\1.jpg",
                Imgcodecs.IMREAD_COLOR);
        Mat mat1 = new Mat();
        imread.copyTo(mat1);
        Mat matx = new Mat();
        Mat maty = new Mat();
        Mat f = new Mat();
        Imgproc.GaussianBlur(imread, imread, new Size(3, 3), 0);
        Imgproc.cvtColor(imread, imread, COLOR_BGR2GRAY);
//        Imgproc.Sobel(imread,matx,-1,1,0,3);
//        Imgproc.Sobel(imread,maty,-1,0,1,3);
        Imgproc.Scharr(imread, matx, -1, 1, 0, 3);
        Imgproc.Scharr(imread, maty, -1, 0, 1, 3);
        Core.addWeighted(matx, 1, maty, 1, 0, f);
        // |a* px + b|
//        Core.convertScaleAbs(matx,matx);
        HighGui.namedWindow("matx", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("matx", matx);
        HighGui.namedWindow("maty", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("maty", maty);
        HighGui.namedWindow("f", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("f", f);
        HighGui.namedWindow("win1", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win1", mat1);
        waitKey(0);
    }

    /**
     * 拉普拉斯算子
     * <p>
     * 在二阶导数的时候，最大变化处的值为零即边缘是零值
     *
     * @param args
     */
    public static void 拉普拉斯算子(String[] args) {
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        Mat imread = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\1.jpg",
                Imgcodecs.IMREAD_COLOR);
        Mat mat1 = new Mat();
        imread.copyTo(mat1);
        Mat f = new Mat();
        Imgproc.GaussianBlur(imread, imread, new Size(3, 3), 0);
        Imgproc.cvtColor(imread, imread, COLOR_BGR2GRAY);
        Imgproc.Laplacian(imread, f, -1, 3);
        //阈值
        Imgproc.threshold(f, f, 20, 255, THRESH_BINARY_INV);
        System.out.println(f.type());
        HighGui.namedWindow("win1", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win1", mat1);
        HighGui.namedWindow("f", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("f", f);
        waitKey(0);
    }

    /**
     * Canny算法介绍
     * 高斯模糊 - GaussianBlur
     * 灰度转换 - cvtColor
     * 计算梯度 – Sobel/Scharr
     * 非最大信号抑制   dx/dy 寻找像素点局部最大值，将非极大值点所对应的灰度值置为0
     * 高低阈值输出二值图像
     * <p>
     * #### 非最大信号抑制
     *
     *
     * 第三个参数和第四个参数表示阈值，这二个阈值中当中的小阈值用来控制边缘连接，
     * 大的阈值用来控制强边缘的初始分割即如果一个像素的梯度大与上限值，则被认为是边缘像素，
     * 如果小于下限阈值，则被抛弃。如果该点的梯度在两者之间则当这个点与高于上限值的像素点连接时我们才保留，否则删除。
     *
     * @param args
     */
    public static void Canny算法(String[] args) {
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        Mat imread = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\1.jpg",
                Imgcodecs.IMREAD_COLOR);
        Mat mat1 = new Mat();
        imread.copyTo(mat1);
        Mat f = new Mat();
        Imgproc.GaussianBlur(imread, imread, new Size(3, 3), 0);
        Imgproc.cvtColor(imread, imread, COLOR_BGR2GRAY);
        Imgproc.Canny(imread, f, 50, 150);

        mat1.copyTo(new Mat(mat1.size(), mat1.type()), f);
        Core.subtract(new Mat(f.size(), f.type(), new Scalar(255)), f, f);
        System.out.println(f.type());
        HighGui.namedWindow("win1", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win1", mat1);
        HighGui.namedWindow("f", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("f", f);
        waitKey(0);
    }

    /**
     * 霍夫变换-直线
     * 前提条件 – 边缘检测已经完成
     * 平面空间到极坐标空间转换
     * <p>
     * 对于任意一条直线上的所有点来说
     * 变换到极坐标中，从[0~360]空间，可以得到r的大小
     * 属于同一条直线上点在极坐标空(r, theta)必然在一个点上有最强的信号出现，根据此反算到平面坐标中就可以得到直线上各点的像素坐标。从而得到直线
     * <p>
     * 每一个像素点，分别计算1，2，3，4，5.。180的方程。最终，角度和直线到原点距离，一致的。是一条线段。
     * https://blog.csdn.net/fengjiexyb/article/details/78075888
     *
     * @param args
     */
    public static void 霍夫变换直线(String[] args) {
        System.out.println("Welcome to OpenCV " + Core.VERSION);
        Mat imread = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\1.jpg",
                Imgcodecs.IMREAD_COLOR);
        Mat mat1 = new Mat();
        Mat f = new Mat(imread.size(), imread.type());
        Mat line = new Mat();
        imread.copyTo(mat1);
        Imgproc.cvtColor(imread, imread, COLOR_BGR2GRAY);
        Imgproc.Canny(imread, imread, 50, 150);
//        Imgproc.HoughLines(imread,line, 1, 1, 10, 0, 10);
        Imgproc.HoughLinesP(imread, line, 1, 1, 10, 0, 0.1);
        if (line.rows() > 0 && line.cols() > 0) {
            for (int i = 0; i < line.rows(); i++) {
                double[] l = line.get(i, 0);
                if (l.length == 4) {
                    Point p1 = new Point(l[0], l[1]);
                    Point p2 = new Point(l[2], l[3]);
                    Imgproc.line(f, p1, p2, new Scalar(255, 255, 255), 2);
                }
            }
        }
        HighGui.namedWindow("win1", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win1", mat1);
        HighGui.namedWindow("imread", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("imread", f);
        waitKey(0);
    }

    /**
     * 因为霍夫圆检测对噪声比较敏感，所以首先要对图像做中值滤波。
     * <p>
     * 转化到极坐标，圆上点的，半径和圆心是相同的。
     * 进行0-360度的检测。
     * <p>
     * <p>
     * 基于效率考虑，Opencv中实现的霍夫变换圆检测是基于图像梯度的实现，分为两步：
     * 1. 检测边缘，发现可能的圆心
     * 2. 基于第一步的基础上从候选圆心开始计算最佳半径大小
     *
     *
     * HoughCircles(
     * InputArray image, // 输入图像 ,必须是8位的单通道灰度图像
     * OutputArray circles, // 输出结果，发现的圆信息
     * Int method, // 方法 - HOUGH_GRADIENT
     * Double dp, // dp = 1;
     * Double mindist, // 10 最短距离-可以分辨是两个圆的，否则认为是同心圆- src_gray.rows/8
     * Double param1, // canny edge detection low threshold
     * Double param2, // 中心点累加器阈值 – 决定成圆的多寡 ，一个圆上的像素超过这个阈值，则成圆，否则丢弃
     * Int minradius, // 最小半径
     * Int maxradius//最大半径
     * )
     * @param args
     */
    public static void 霍夫圆检测(String[] args) {

        Mat imread = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\3.jpg",
                Imgcodecs.IMREAD_COLOR);
        Mat mat1 = new Mat();
        Mat circles = new Mat();
        imread.copyTo(mat1);
        Imgproc.cvtColor(imread, imread, COLOR_BGR2GRAY);
        Imgproc.GaussianBlur(imread,imread,new Size(3,3),0);
//        Imgproc.HoughCircles(imread, circles, Imgproc.CV_HOUGH_GRADIENT, 1, 100, 440, 50, 0, 345);
        Imgproc.HoughCircles(imread, circles, Imgproc.HOUGH_GRADIENT,   1, 100, 150, 60, 50, 200);
        for (int i = 0; i < circles.cols(); i++) {
            double[] vCircle = circles.get(0, i);

            Point center = new Point(vCircle[0], vCircle[1]);
            int radius = (int) Math.round(vCircle[2]);

            // circle center
            Imgproc.circle(mat1, center, 3, new Scalar(0, 255, 0), -1, 8, 0);
            // circle outline
            Imgproc.circle(mat1, center, radius, new Scalar(0, 0, 255), 3, 8, 0);
        }
        HighGui.namedWindow("win1", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win1", mat1);
        waitKey(0);
    }

    /**
     * 像素重映射
     * @param args
     */
    public static void 像素重映射(String[] args) {

        Mat imread = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\1.jpg",
                Imgcodecs.IMREAD_COLOR);
        Mat mat1 = new Mat();Mat f = new Mat();
        imread.copyTo(mat1);
        Mat x = new Mat(imread.size(), CV_32FC1);  Mat y = new Mat(imread.size(), CV_32FC1);
        for (int i = 0;i< imread.rows();i++){
            for(int j =0;j< imread.cols();j++){
//                x.put(i,j,imread.cols() - j - 1);
//                y.put(i,j,i);

                x.put(i,j,j);
                y.put(i,j,imread.rows()-i);
            }
        }

        Imgproc.remap(imread,f,x,y,INTER_LINEAR);

        HighGui.namedWindow("win1", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win1", mat1);
        HighGui.namedWindow("f", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("f", f);
        waitKey(0);
    }

    /**
     * 图像直方图，是指对整个图像像在灰度范围内的像素值(0~255)统计出现频率次数
     *
     * 得到每个灰度的直方图，对这个灰度的每个像素点，进行处理
     * @param args
     */
    public static void 图像直方图(String[] args) {

        Mat imread = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\1.jpg",
                Imgcodecs.IMREAD_COLOR);
        Mat mat1 = new Mat();Mat f = new Mat();
        imread.copyTo(mat1);
        Imgproc.cvtColor(imread,imread, COLOR_BGR2GRAY);

        Imgproc.equalizeHist(imread,f);

        HighGui.namedWindow("win1", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win1", imread);
        HighGui.namedWindow("f", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("f", f);
        waitKey(0);
    }

    /**
     *
     * 上述直方图概念是基于图像像素值，
     * 其实对图像梯度、每个像素的角度、等一切图像的属性值，我们都可以建立直方图。
     * 这个才是直方图的概念真正意义，不过是基于图像像素灰度直方图是最常见的。
     *
     * onst Mat* images：输入图像
     *
     *  int nimages：输入图像的个数
     *
     * const int* channels：需要统计直方图的第几通道
     *
     * InputArray mask：掩膜，，计算掩膜内的直方图  ...Mat()
     *
     * OutputArray hist:输出的直方图数组
     *
     * int dims：需要统计直方图通道的个数
     *
     * const int* histSize：指的是直方图分成多少个区间，就是 bin的个数
     *
     * const float** ranges： 统计像素值得区间
     *
     * bool uniform=true::是否对得到的直方图数组进行归一化处理
     *
     * bool accumulate=false：在多个图像时，是否累计计算像素值得个数
     * @param args
     */
    public static void 直方图概念(String[] args) {

        Mat imread = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\1.jpg",
                Imgcodecs.IMREAD_COLOR);
        Mat mat1 = new Mat();Mat f = new Mat();
        imread.copyTo(mat1);
        List<Mat> mats = new ArrayList<>();
        Core.split(imread,mats);

        Mat mat = new Mat(new Size(256, 500), CV_8UC3,new Scalar(255,255,255));

        mkLineForCalcHist(f, mats,  mat,0,new Scalar(255,0,0));
        mkLineForCalcHist(f, mats,  mat,1,new Scalar(0,255,0));
        mkLineForCalcHist(f, mats,  mat,2,new Scalar(0,0,255));
        HighGui.namedWindow("win1", HighGui.WINDOW_AUTOSIZE);
        HighGui.imshow("win1", mat);
        waitKey(0);
    }

    private static void mkLineForCalcHist(Mat f, List<Mat> mats, Mat mat,int index,Scalar scalar) {
        MatOfInt hitSize = new MatOfInt(256);
        MatOfFloat range = new MatOfFloat(0, 256);
        Imgproc.calcHist(mats,new MatOfInt(index),new Mat(),f,hitSize,range);
        Core.normalize(f,f,0,500,NORM_MINMAX);
        for (int i =0;i< f.rows();i++){
            try {
                double[] doubles = f.get(i, 0);
                int aDouble = (int)doubles[0];
                double[] b = f.get(i+1, 0);
                int bs = (int)b[0];

                Imgproc.line(mat,new Point(i,500-aDouble),new Point(i+1,500-bs),scalar, 2, LINE_AA);
            } catch (Exception e) {
                break;
            }
        }
    }

    /**
     * 直方图比较
     *
     * Correlation 相关性比较
     * Chi-Square 卡方比较
     * Intersection 十字交叉性
     * Bhattacharyya distance 巴氏距离
     * @param args
     */
    public static void main(String[] args) {

        Mat imread = Imgcodecs.imread(
                "F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\1.jpg",
                Imgcodecs.IMREAD_COLOR);
        Mat mat1 = new Mat();Mat x = new Mat();Mat y = new Mat();
        imread.copyTo(mat1);
        List<Mat> mats = new ArrayList<>();
        Core.split(imread,mats);

        Mat mat = new Mat(new Size(256, 500), CV_8UC3,new Scalar(255,255,255));

        MatOfInt hitSize = new MatOfInt(256);
        MatOfFloat range = new MatOfFloat(0, 256);
        Imgproc.calcHist(mats,new MatOfInt(0),new Mat(),x,hitSize,range);
        Imgproc.calcHist(mats,new MatOfInt(1),new Mat(),y,hitSize,range);

        double v = compareHist(x, y, CV_COMP_CORREL);
        System.out.println(v);
    }
}
