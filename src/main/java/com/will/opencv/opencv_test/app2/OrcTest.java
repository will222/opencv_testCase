package com.will.opencv.opencv_test.app2;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class OrcTest {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /*
    Harris角点：算法的核心是利用局部窗口在图像上进行移动判断灰度发生较大的变化
     */
    public static void f1(String[] args) {
        Mat src = Imgcodecs.imread("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app2\\margin.png");
        Mat mat = new Mat();
        src.copyTo(mat);
        Imgproc.cvtColor(src, src, Imgproc.COLOR_BGR2GRAY);

        Imgproc.threshold(src, src, 200, 255, Imgproc.THRESH_BINARY_INV);

        Mat key1 = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT, new Size(3, 3));
        Imgproc.morphologyEx(src, src, Imgproc.MORPH_DILATE, key1);

        Mat key = Imgproc.getStructuringElement(Imgproc.CV_SHAPE_RECT, new Size(src.width() / 10, 1));
        Imgproc.morphologyEx(src, src, Imgproc.MORPH_OPEN, key);

        HighGui.imshow("src", src);
        HighGui.imshow("mat", mat);
        HighGui.waitKey();
    }

    public static void f2(String[] args) {
        Mat src = Imgcodecs.imread("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app2\\margin.png");
        Mat mat = new Mat();
        Mat up = new Mat();
        Mat down = new Mat();
        src.copyTo(mat);
        Imgproc.pyrUp(mat, up);
        Imgproc.pyrDown(mat, down);
        HighGui.imshow("src", src);
        HighGui.imshow("up", up);
        HighGui.imshow("down", down);
        HighGui.waitKey();
    }

    public static void f3(String[] args) {
        Mat src = Imgcodecs.imread("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app2\\margin.png");
        Mat mat = new Mat();
        Mat up = new Mat();
        Mat down = new Mat();
        src.copyTo(mat);

        Imgproc.GaussianBlur(src, up, new Size(15, 15), 0);
        Imgproc.GaussianBlur(src, down, new Size(5, 5), 0);
        Mat a = new Mat();
        Core.subtract(up, down, a);
        HighGui.imshow("a", a);
        Core.normalize(a, mat, 0, 255, Core.NORM_MINMAX);
        HighGui.imshow("src", src);
        HighGui.imshow("mat", mat);
        HighGui.waitKey();
    }

    public static void f4(String[] args) {
        Mat src = Imgcodecs.imread("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app2\\wom.jpg");
        Mat up = new Mat();
        Imgproc.GaussianBlur(src,src,new Size(3,3),0);
//        Imgproc.Sobel(src, up, 3, 1, 1);
         Imgproc.Laplacian(src, up, 3);
        Core.convertScaleAbs(up,up);
        Core.add(up,new Scalar(50,50,50),up);
        Core.normalize(up,up,0,255,Core.NORM_MINMAX);
        HighGui.imshow("up", up);
        HighGui.waitKey();
    }

    public static void f5(String[] args) {
        Mat src = Imgcodecs.imread("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app2\\margin.png");
        Mat up = new Mat(); Mat line = new Mat();
        Mat zeros = Mat.zeros(src.size(), src.type());
//        Imgproc.GaussianBlur(src,src,new Size(3,3),0);
        Imgproc.Canny(src,up,150,200);
        Core.convertScaleAbs(up,up);
        Imgproc.HoughLinesP(up,line,1,2,10);
        int rows = line.rows();

        for (int i = 0 ;i < rows;i++){
            double[] doubles = line.get(i, 0);
            Imgproc.line(zeros,new Point(doubles[0],doubles[1]),new Point(doubles[2],doubles[3]),new Scalar(0,0,255));
        }
        HighGui.imshow("src", src);
        HighGui.imshow("up", zeros);
        HighGui.waitKey();
    }

    public static void f6(String[] args) {
        Mat src = Imgcodecs.imread("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app2\\cicle.jpg");
        Mat line = new Mat();
        Imgproc.medianBlur(src,src,3);
        Imgproc.cvtColor(src,src,Imgproc.COLOR_BGR2GRAY);
        Imgproc.HoughCircles(src,line,Imgproc.CV_HOUGH_GRADIENT,1,100);

        for (int i = 0 ;i < line.cols();i++){
            double[] doubles = line.get(0, i);
            Imgproc.circle(src,new Point(doubles[0],doubles[1]),(int)doubles[2],new Scalar(255));
        }

        HighGui.imshow("src", src);
        HighGui.waitKey();
    }

    public static void f7(String[] args) {
        Mat src = Imgcodecs.imread("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app2\\wom.jpg");
        Mat map_x = new Mat(src.size(),CvType.CV_32FC1);
        Mat map_y = new Mat(src.size(),CvType.CV_32FC1);
        Mat dst = new Mat();
        for (int i = 0;i< src.rows();i++){
            for(int j =0;j< src.cols();j++){
                map_x.put(i,j,j);
                map_y.put(i,j,i);
            }
        }
        Imgproc.remap(src, dst, map_x, map_y, Imgproc.INTER_LINEAR);
        HighGui.imshow("src", src);
        HighGui.imshow("dst", dst);
        HighGui.waitKey();
    }
    public static void main(String[] args) {
        Mat src = Imgcodecs.imread("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app2\\wom.jpg");
        Mat dst = new Mat();
        Imgproc.cvtColor(src,src,Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(src,dst);

        HighGui.imshow("src", src);
        HighGui.imshow("dst", dst);
        HighGui.waitKey();
    }
}