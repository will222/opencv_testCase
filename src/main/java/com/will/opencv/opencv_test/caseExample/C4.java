package com.will.opencv.opencv_test.caseExample;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

import static org.opencv.imgproc.Imgproc.MORPH_RECT;
import static org.opencv.imgproc.Imgproc.RETR_TREE;


public class C4 {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     * Feature2D.detect(),及其所有实现类
     *
     * @param args
     */
    public static void main(String[] args) {
        Mat src = Imgcodecs.imread("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\caseExample\\coin.png");
        Mat clone = src.clone();

        Imgproc.cvtColor(src,src,Imgproc.COLOR_BGR2GRAY);

        Imgproc.threshold(src,src,0,255,Imgproc.THRESH_BINARY_INV|Imgproc.THRESH_TRIANGLE);

        Mat kernel = Imgproc.getStructuringElement(MORPH_RECT, new Size(3, 3));
        Imgproc.morphologyEx(src,src,Imgproc.MORPH_ERODE,kernel);

        Mat dst = new Mat();
        Imgproc.distanceTransform(src,dst,Imgproc.CV_DIST_L2, 3);
        Core.normalize(dst,dst,0, 255, Core.NORM_MINMAX);

        Mat dist_8u = new Mat();
        dst.convertTo(dist_8u, CvType.CV_8U);
        Imgproc.threshold(dist_8u,dist_8u,150,255,Imgproc.THRESH_BINARY);


        Mat kernel1 = Imgproc.getStructuringElement(MORPH_RECT, new Size(10, 10));
        Imgproc.morphologyEx(dist_8u,dist_8u,Imgproc.MORPH_ERODE,kernel1);

        List<MatOfPoint> matOfPoints = new ArrayList<>();
        Imgproc.findContours(dist_8u,matOfPoints,new Mat(),RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

        dist_8u.convertTo(dist_8u,CvType.CV_8SC1);
        HighGui.imshow("dst", dist_8u);
        HighGui.imshow("clone", clone);
        System.out.println("sum:"+matOfPoints.size());
        HighGui.waitKey();
    }
}