package com.will.opencv.opencv_test.caseExample;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import static org.opencv.imgproc.Imgproc.COLOR_BGR2GRAY;
import static org.opencv.imgproc.Imgproc.MORPH_RECT;

public class C2 {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     * Feature2D.detect(),及其所有实现类
     *
     * @param args
     */
    public static void main(String[] args) {
        Mat src = Imgcodecs.imread("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\caseExample\\line.png");
        Imgproc.cvtColor(src, src, COLOR_BGR2GRAY);

        Imgproc.GaussianBlur(src, src, new Size(3, 3), 0);

        Core.bitwise_not(src, src);
        Mat hline = Imgproc.getStructuringElement(MORPH_RECT, new Size(50, 1));
        Mat dilate = Imgproc.getStructuringElement(MORPH_RECT, new Size(3, 3));
        Imgproc.erode(src, src, hline);
        Imgproc.dilate(src, src, dilate);

        Imgproc.threshold(src,src,150, 255, Imgproc.THRESH_BINARY );
        Imgproc.dilate(src, src, hline);

        Mat line = new Mat();
        Mat zeros = Mat.zeros(src.size(), src.type());
//        Imgproc.GaussianBlur(src,src,new Size(3,3),0);
        Imgproc.HoughLinesP(src, line,  1, 3.14/180, 30, 50.0, 0);

        int rows = line.rows();
        int cols = line.cols();
        for (int i = 0; i < rows; i++) {
            double[] doubles = line.get(i, 0);
            Imgproc.line(zeros, new Point(doubles[0], doubles[1]), new Point(doubles[2], doubles[3]), new Scalar( 255));
        }
        HighGui.imshow("zeros", zeros);
        HighGui.imshow("src", src);
        HighGui.waitKey();
    }
}