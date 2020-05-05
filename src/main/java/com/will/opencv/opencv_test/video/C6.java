package com.will.opencv.opencv_test.video;

import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.Video;
import org.opencv.videoio.VideoCapture;

import static org.opencv.highgui.HighGui.imshow;
import static org.opencv.imgproc.Imgproc.COLOR_GRAY2BGR;

public class C6 {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }


    public static void main(String[] args) {
        System.out.println("start video1");
        VideoCapture cap = new VideoCapture();
        //使用本地视频
        cap.open("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\video\\bike.avi");

        if (!cap.isOpened()) {
            System.out.println("not open");
            return;
        }
        Mat frame = new Mat();
        Mat prev_gray = new Mat();

        while (true) {
            boolean read = cap.read(frame);
            if (!read) {
                System.out.println("not read mat");
                break;
            }
            Mat gray = new Mat();
            Mat flowResult =new Mat();
            Imgproc.cvtColor(frame, gray, Imgproc.COLOR_BGR2GRAY);
            if (!prev_gray.empty()) {
                Mat flowdata = new Mat();
                Video.calcOpticalFlowFarneback(prev_gray, gray, flowdata, 0.5, 3, 15, 3, 5, 1.2, 0);
                Imgproc.cvtColor(prev_gray, flowResult, COLOR_GRAY2BGR);
                drawOpticalFlowHF(flowdata, flowResult, 10);
                imshow("flow", flowResult);
                imshow("input", frame);
                HighGui.waitKey(100);
            }
            prev_gray = gray;

        }
        System.out.println("video end");
    }
    static void  drawOpticalFlowHF( Mat flowdata, Mat image, int step) {
        for (int row = 0; row < image.rows(); row++) {
            for (int col = 0; col < image.cols(); col++) {
			 double[] fxy = flowdata.get(row, col);
                if (fxy[0] > 1 || fxy[1] > 1) {
                    Imgproc.line(image, new Point(col, row), new Point(col + fxy[0], row + fxy[1]),new  Scalar(0, 255, 0), 2, 8, 0);
                    Imgproc.circle(image, new Point(col, row), 2, new Scalar(0, 0, 255), -1);
                }
            }
        }
    }

}