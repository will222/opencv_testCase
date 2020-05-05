package com.will.opencv.opencv_test.feature2d;

import org.opencv.core.*;
import org.opencv.features2d.Features2d;
import org.opencv.features2d.ORB;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgproc.Imgproc.COLOR_BGR2GRAY;

public class HaarFace {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    /**
     * Feature2D.detect(),及其所有实现类
     * @param args
     */
    public static void main(String[] args) {

        String cascadeFilePath = "F:\\opencv\\build\\etc\\haarcascades\\haarcascade_eye.xml";
        CascadeClassifier face_cascade = new CascadeClassifier();
        face_cascade.load(cascadeFilePath);
        Mat src = new Mat();Mat gray_src = new Mat();
        src = imread("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\app2\\wom.jpg");
        Imgproc.cvtColor(src, gray_src, COLOR_BGR2GRAY);
//        Imgproc.equalizeHist(gray_src, gray_src);
        MatOfRect matOfRect = new MatOfRect();
        face_cascade.detectMultiScale(gray_src,matOfRect);
        for (int t = 0; t < matOfRect.toList().size(); t++) {
            Imgproc.rectangle(src, matOfRect.toArray()[t], new Scalar(0, 0, 255), 2, 8, 0);
        }

        imshow("dst",src);
        HighGui.waitKey();
    }
}