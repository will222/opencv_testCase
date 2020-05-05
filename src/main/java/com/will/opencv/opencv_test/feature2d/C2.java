package com.will.opencv.opencv_test.feature2d;

import org.opencv.core.*;
import org.opencv.features2d.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.HOGDescriptor;

import java.util.ArrayList;
import java.util.List;

import static org.opencv.features2d.DescriptorMatcher.BRUTEFORCE_HAMMINGLUT;

public class C2 {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     *
     * @param args
     */
    public static void f1(String[] args) {
        Mat src = Imgcodecs.imread("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\feature2d\\pes.jpg");

        HOGDescriptor hogDescriptor = new HOGDescriptor();


        hogDescriptor.setSVMDetector(HOGDescriptor.getDefaultPeopleDetector());
        MatOfRect matOfRect = new MatOfRect();
        MatOfDouble matOfDouble = new MatOfDouble(0);
        hogDescriptor.detectMultiScale(src,matOfRect,matOfDouble);
        for (int t = 0; t < matOfRect.toList().size(); t++) {
            Imgproc.rectangle(src, matOfRect.toArray()[t], new Scalar(0, 0, 255), 2, 8, 0);
        }

        HighGui.imshow("dst",src);
        HighGui.waitKey();
    }

    public static void f2(String[] args) {
        Mat src = Imgcodecs.imread("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\feature2d\\pes.jpg");

        Mat sumii = Mat.zeros(src.rows() + 1, src.cols() + 1, CvType.CV_32FC1);

        Imgproc.integral(src,sumii);

        Core.normalize(sumii,sumii,0,255,Core.NORM_MINMAX);

        sumii.convertTo(sumii,CvType.CV_8UC3);
        HighGui.imshow("dst",sumii);
        HighGui.waitKey();
    }

    public static void main(String[] args) {
        Mat src = Imgcodecs.imread("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\feature2d\\pes.jpg");
        Mat clone = Imgcodecs.imread("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\feature2d\\p1.png");

              /*
//        AKAZE fd = AKAZE.create();
//        BRISK fd = BRISK.create();
//        KAZE fd = KAZE.create();
         */
        KAZE fd = KAZE.create();

        MatOfKeyPoint matOfKeyPoint = new MatOfKeyPoint();
        Mat describe = new Mat();
        fd.detectAndCompute(src,new Mat(),matOfKeyPoint,describe);

        Mat describe2 = new Mat();
        MatOfKeyPoint matOfKeyPoint2 = new MatOfKeyPoint();
        fd.detectAndCompute(clone,new Mat(),matOfKeyPoint2,describe2);
        DescriptorMatcher flannBasedMatcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);
        MatOfDMatch matOfDMatch = new MatOfDMatch();

        flannBasedMatcher.match(describe,describe2,matOfDMatch);


        Mat matchesImg = new Mat();
        Features2d.drawMatches(src, matOfKeyPoint, clone, matOfKeyPoint2, matOfDMatch, matchesImg);
        HighGui.imshow("dst",matchesImg);
        HighGui.waitKey();
    }
}