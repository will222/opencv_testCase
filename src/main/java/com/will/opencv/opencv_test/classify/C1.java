package com.will.opencv.opencv_test.classify;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.imgcodecs.Imgcodecs.imread;
import static org.opencv.imgproc.Imgproc.COLOR_BGR2GRAY;

public class C1 {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
    static String cascadeFilePath = "F:\\opencv\\build\\etc\\haarcascades\\haarcascade_frontalface_alt.xml";
    static String lbpfile = "F:\\opencv\\build\\etc\\lbpcascades\\lbpcascade_frontalface.xml";


    /**
     * Feature2D.detect(),及其所有实现类
     * @param args
     */
    public static void main(String[] args) {

        CascadeClassifier face_cascade = new CascadeClassifier();
        face_cascade.load(lbpfile);

        Mat src  = imread("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\classify\\face.png");
        Mat grey = new Mat();
        Imgproc.cvtColor(src, grey, COLOR_BGR2GRAY);
        Imgproc.equalizeHist(grey, grey);
        MatOfRect matOfRect = new MatOfRect();
        face_cascade.detectMultiScale(grey,matOfRect, 1.1, 5, 0, new Size(30, 30));


        for (int t = 0; t < matOfRect.toList().size(); t++) {
            Imgproc.rectangle(src, matOfRect.toArray()[t], new Scalar(0, 0, 255), 2, 8, 0);
        }

        imshow("dst",src);
        HighGui.waitKey();
    }
}