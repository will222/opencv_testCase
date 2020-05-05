package com.will.opencv.opencv_test.feature2d;

import org.opencv.core.*;
import org.opencv.features2d.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class C1 {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     * Feature2D.detect(),及其所有实现类
     * @param args
     */
    public static void main(String[] args) {
        Mat src = Imgcodecs.imread("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app2\\wom.jpg");
        Mat clone = new Mat(src.size(),src.type());
//        AgastFeatureDetector fd = AgastFeatureDetector.create();
//        AKAZE fd = AKAZE.create();
//        BRISK fd = BRISK.create();
//        FastFeatureDetector fd = FastFeatureDetector.create();
//        GFTTDetector fd = GFTTDetector.create();
//        KAZE fd = KAZE.create();
//        MSER fd = MSER.create();
        ORB fd = ORB.create();

        MatOfKeyPoint matOfKeyPoint = new MatOfKeyPoint();
        fd.detect(src,matOfKeyPoint);
        Features2d.drawKeypoints(src,matOfKeyPoint,clone);

        HighGui.imshow("dst",clone);
        HighGui.waitKey();
    }
}