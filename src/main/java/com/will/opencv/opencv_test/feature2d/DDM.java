package com.will.opencv.opencv_test.feature2d;

import org.opencv.calib3d.Calib3d;
import org.opencv.core.*;
import org.opencv.features2d.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.HOGDescriptor;

import java.util.ArrayList;
import java.util.List;

import static org.opencv.calib3d.Calib3d.RANSAC;

public class DDM {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        Mat src = Imgcodecs.imread("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\feature2d\\pes.jpg");
//        AgastFeatureDetector fd = AgastFeatureDetector.create();
//        AKAZE fd = AKAZE.create();
//        BRISK fd = BRISK.create();
//        FastFeatureDetector fd = FastFeatureDetector.create();
//        GFTTDetector fd = GFTTDetector.create();
//        KAZE fd = KAZE.create();
//        MSER fd = MSER.create();

        AKAZE akaze = AKAZE.create();
        MatOfKeyPoint matOfKeyPoint = new MatOfKeyPoint();
        Mat descriptors = new Mat();
        akaze.detectAndCompute(src,new Mat(),matOfKeyPoint,descriptors);


        DescriptorMatcher bfMatcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE);
        List<Mat> mats = new ArrayList<>();
        mats.add(descriptors);
        bfMatcher.add(mats);
        bfMatcher.train();

        MatOfDMatch matOfDMatch = new MatOfDMatch() ;
        bfMatcher.match(descriptors,matOfDMatch);

        Mat mat = new Mat();
        Features2d.drawMatches(src,matOfKeyPoint,src,matOfKeyPoint,matOfDMatch,mat);

        List<Point> obj = new  ArrayList<Point>();
        List<Point> objInScene = new  ArrayList<Point>();
        for (int t = 0; t < matOfDMatch.toArray().length; t++) {
            obj.add(matOfKeyPoint.toArray()[matOfDMatch.toArray()[t].queryIdx].pt);
            objInScene.add(matOfKeyPoint.toArray()[matOfDMatch.toArray()[t].trainIdx].pt);
        }
        MatOfPoint2f matOfPoint2fobj = new MatOfPoint2f();
        matOfPoint2fobj.fromList(obj);
        MatOfPoint2f matOfPoint2fobjInScene = new MatOfPoint2f();
        matOfPoint2fobjInScene.fromList(objInScene);
        Mat H = Calib3d.findHomography(matOfPoint2fobj, matOfPoint2fobjInScene, RANSAC);







        HighGui.imshow("dst",mat);
        HighGui.waitKey();
    }


}