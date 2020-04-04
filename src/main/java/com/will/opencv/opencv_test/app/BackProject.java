package com.will.opencv.opencv_test.app;


import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.opencv.core.Core.NORM_MINMAX;
import static org.opencv.imgproc.Imgproc.RETR_TREE;

/**
 * 反向投影
 *
 */
public class BackProject {

    static {
    	// Java 在使用 OpenCV 前必须加载 Core.NATIVE_LIBRARY_NAME 类,否则会报错
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }


    /**
     *
     *  1.经过上面的分析，下面给出反向投影的作用：反向投影用于在输入图像（通常较大）中查找特定图像（通常较小或者仅1个像素，以下将其称为模板图像）最匹配的点或者区域，也就是定位模板图像出现在输入图像的位置。
     * 2.反向投影查找原理：查找的方式就是不断的在输入图像中切割跟模板图像大小一致的图像块，并用直方图对比的方式与模板图像进行比较。
     * 假设我们有一张100x100的输入图像，有一张10x10的模板图像，查找的过程是这样的：
     * （1）从输入图像的左上角(0,0)开始，切割一块(0,0)至(10,10)的临时图像；
     * （2）生成临时图像的直方图；
     * （3）用临时图像的直方图和模板图像的直方图对比，对比结果记为c；
     * （4）直方图对比结果c，就是结果图像(0,0)处的像素值；
     * （5）切割输入图像从(0,1)至(10,11)的临时图像，对比直方图，并记录到结果图像；
     * （6）重复（1）～（5）步直到输入图像的右下角。
     * 3.反向投影的结果包含了：以每个输入图像像素点为起点的直方图对比结果。可以把它看成是一个二维的浮点型数组，二维矩阵，或者单通道的浮点型图像。
     * 可以这样理解：对于calcBackProjectPatch，也就是是基于块的反向投影形式，利用直方图做匹配，类似于模板匹配，只不过这些模板转换为直方图，而原图中以某点为基准，抠出来作对比的部分也转换为直方图，两个直方图作匹配，匹配的结果作为此点的值。 结果会是一张灰度图。
     * 4.如果输入图像和模板图像一样大，那么反向投影相当于直方图对比。如果输入图像比模板图像还小，无法进行反向投影。
     *
     * 所谓反向投影就是首先计算某一特征的直方图模型，然后使用模型去寻找图像中存在的该特征
     *
     * 计算模板的直方图，把模板当成一个掩膜，进行大图上每一块的直方图比对
     * @return: void
     * @date: 2019年5月7日12:04:04
     */
    public static void 反向投影(String[] args) throws Exception {
        Mat src = Imgcodecs.imread("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\5.png");
        Mat mu = Imgcodecs.imread("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\4.png");
        Imgproc.cvtColor(mu, mu, Imgproc.COLOR_BGR2HSV);

        //1 图片转HSV
        Imgproc.cvtColor(src, src, Imgproc.COLOR_BGR2HSV);
        //模板
        Mat histimage = new Mat();
        Imgproc.calcHist(Arrays.asList(mu), new MatOfInt(0), new Mat(), histimage, new MatOfInt(255), new MatOfFloat(0, 255));
        //3 归一化
        Core.normalize(histimage, histimage, 1, histimage.rows(), NORM_MINMAX, -1, new Mat());
        //4 直方图反向投影
        Mat backimage = new Mat();
        Imgproc.calcBackProject(Arrays.asList(src), new MatOfInt(0), histimage, backimage, new MatOfFloat(0, 255), 2.0);
        HighGui.imshow("直方图反向投影", backimage);
        HighGui.waitKey(0);
    }

    /**
     * 在带检测图像上，从左到右，从上向下计算模板图像与重叠子图像的匹配度，匹配程度越大，两者相同的可能性越大
     *
     * @param args
     */
    public static void 模板匹配(String[] args) {
        Mat src = Imgcodecs.imread("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\5.png");
        Mat mu = Imgcodecs.imread("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\4.png");

        //模板
        Mat histimage = new Mat();
        Imgproc.matchTemplate(src, mu, histimage ,Imgproc.TM_CCORR_NORMED);
        Core.normalize(histimage, histimage, 0, 1, NORM_MINMAX);
        Core.MinMaxLocResult mmlr = Core.minMaxLoc(histimage);

        Point matchLocation = mmlr.maxLoc; // 此处使用maxLoc还是minLoc取决于使用的匹配算法
        Imgproc.rectangle(src, matchLocation,
                new Point(matchLocation.x + mu.cols(), matchLocation.y + mu.rows()),
                new Scalar(0, 0, 0, 0));

        HighGui.imshow("直方图反向投影", src);
        HighGui.waitKey(0);
    }

    /**
     * 轮廓发现是基于图像边缘提取的基础寻找对象轮廓的方法。
     * 所以边缘提取的阈值选定会影响最终轮廓发现结果
     * @param args
     */
    public static void 轮廓发现(String[] args) {
        Mat mu = Imgcodecs.imread("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\1.jpg");

        Imgproc.cvtColor(mu,mu,Imgproc.COLOR_BGR2GRAY);
        Imgproc.Canny(mu, mu, 50, 150);
        HighGui.imshow("直方图反向投影", mu);
        HighGui.waitKey(2000);
        List<MatOfPoint> matOfPoints = new ArrayList<>();
        Mat mat = new Mat();
        Imgproc.findContours(mu,matOfPoints,mat, RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
        Random random = new Random();
        for (int i = 0; i < matOfPoints.size(); i++) {
            Scalar color = new Scalar(random.nextInt( 255), random.nextInt( 255), random.nextInt( 255));
            Imgproc.drawContours(mu, matOfPoints, i, color, 2);

        }
        HighGui.imshow("直方图反向投影", mu);
        HighGui.waitKey(0);

    }

    /**
     * 凸包
     * @param args
     */
    public static void 凸包(String[] args) {
        Mat mu = Imgcodecs.imread("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\1.jpg");

        Imgproc.cvtColor(mu,mu,Imgproc.COLOR_BGR2GRAY);
        Imgproc.Canny(mu, mu, 50, 150);

        List<MatOfPoint> matOfPoints = new ArrayList<>();
        Mat mat = new Mat();
        Imgproc.findContours(mu,matOfPoints,mat, RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        MatOfInt matOfInt = new MatOfInt();
        Random random = new Random();
        for (int i = 0; i < matOfPoints.size(); i++ ){
            Imgproc.convexHull(matOfPoints.get(i),matOfInt);
            Scalar color = new Scalar(random.nextInt( 255), random.nextInt( 255), random.nextInt( 255));
            Imgproc.drawContours(mu, Arrays.asList(matOfIntToPoints(matOfPoints.get(i), matOfInt)), 0, color, 2);
        }

        HighGui.imshow("直方图反向投影", mu);
        HighGui.waitKey(0);

    }
    //convexHull 第二个参数：在第一种情况下，外壳元素是原始数组中凸包点的从0开始的索引 （因为凸包点集是原始点集的子集） 所以需要转换为真实的坐标地址
    public static MatOfPoint matOfIntToPoints(MatOfPoint contour, MatOfInt indexes) {
        int[] arrIndex = indexes.toArray();
        Point[] arrContour = contour.toArray();
        Point[] arrPoints = new Point[arrIndex.length];

        for (int i=0;i<arrIndex.length;i++) {
            arrPoints[i] = arrContour[arrIndex[i]];
        }

        MatOfPoint hull = new MatOfPoint();
        hull.fromArray(arrPoints);
        return hull;
    }

    /**
     * 提取图像边缘
     * 发现轮廓
     * 计算每个轮廓对象的矩
     * 计算每个对象的中心、弧长、面积
     *
     *   图像的矩通常描述了该图像形状的全局特征，并被广泛的应用在各种图像处理
     * @param args
     */
    public static void main(String[] args) {
        Mat mu = Imgcodecs.imread("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app\\1.jpg");

        Imgproc.cvtColor(mu,mu,Imgproc.COLOR_BGR2GRAY);
        Imgproc.Canny(mu, mu, 50, 150);


        HighGui.imshow("直方图反向投影", mu);
        HighGui.waitKey(0);
    }
/**
 * 1.将白色背景变成黑色-目的是为后面的变换做准备
 * 2. 使用filter2D与拉普拉斯算子实现图像对比度提高，sharp
 * 3. 转为二值图像通过threshold
 * 4. 距离变换
 * 5. 对距离变换结果进行归一化到[0~1]之间
 * 6. 使用阈值，再次二值化，得到标记
 * 7. 腐蚀得到每个Peak - erode
 * 8.发现轮廓 – findContours
 * 9. 绘制轮廓- drawContours
 * 10.分水岭变换 watershed
 * 11. 对每个分割区域着色输出结果
 */

}


