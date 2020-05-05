package com.will.opencv.opencv_test.classify;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.imgproc.Imgproc.COLOR_BGR2GRAY;

public class C3 {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    static String cascadeFilePath = "F:\\opencv\\build\\etc\\haarcascades\\haarcascade_frontalface_alt.xml";
    static String eyefile = "F:\\opencv\\build\\etc\\haarcascades\\haarcascade_eye.xml";
    static CascadeClassifier face_cascade = new CascadeClassifier();
    static CascadeClassifier eye_cascade = new CascadeClassifier();
    static {
        face_cascade.load(cascadeFilePath);
        eye_cascade.load(eyefile);
    }
    /**
     * Feature2D.detect(),及其所有实现类
     *
     * @param src
     */
    public static void f(Mat src) {

        Mat grey = new Mat();
        Imgproc.cvtColor(src, grey, COLOR_BGR2GRAY);
        Imgproc.equalizeHist(grey, grey);
        MatOfRect matOfRectFace = new MatOfRect();
        face_cascade.detectMultiScale(grey, matOfRectFace);

        for (int t = 0; t < matOfRectFace.toList().size(); t++) {
            Imgproc.rectangle(src, matOfRectFace.toArray()[t], new Scalar(0, 0, 255), 2, 8, 0);

            Rect rect = matOfRectFace.toList().get(t);
            Mat eye = new Mat(src, rect);
            Imgproc.cvtColor(eye,eye, COLOR_BGR2GRAY);
            MatOfRect matOfRectEye = new MatOfRect();
            eye_cascade.detectMultiScale(eye, matOfRectEye);

            for (int e = 0; e < matOfRectEye.toList().size(); e++) {
                Rect eyeRect = matOfRectEye.toArray()[e];
                eyeRect.x= eyeRect.x + rect.x;
                eyeRect.y= eyeRect.y + rect.y;
                Imgproc.rectangle(src, eyeRect, new Scalar(0, 255, 0), 2, 8, 0);
            }
        }
        imshow("frame", src);

    }

    public static void main(String[] args) {
        System.out.println("start video");
        VideoCapture cap = new VideoCapture();
        //使用本地视频
        cap.open(0);
        if (!cap.isOpened()) {
            System.out.println("not open");
            return;
        }
        int i =0;
        int d =0;
        while (true) {
            i++;
            Mat frame = new Mat();
            boolean read = cap.read(frame);
            if (!read) {
                System.out.println("not read mat");
                break;
            }
            if( i%(25*1) ==0){
                d ++;
                System.out.println("第n个三秒 : "+d);
                f(frame);
                HighGui.waitKey(100);
            }
        }
        System.out.println("video end");
    }
}