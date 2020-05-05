package com.will.opencv.opencv_test.video;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;
import org.opencv.video.BackgroundSubtractorKNN;
import org.opencv.video.BackgroundSubtractorMOG2;
import org.opencv.video.Video;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.VideoWriter;
import org.opencv.videoio.Videoio;

import static org.opencv.imgproc.Imgproc.MORPH_RECT;

public class C3 {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }


    public static void main(String[] args) {
        System.out.println("start video");
        VideoCapture cap = new VideoCapture();
        //使用本地视频
        cap.open("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\video\\bike.avi");

        if (!cap.isOpened()){
            System.out.println("not open");
            return;
        };
       /* BackgroundSubtractorMOG2 bs = Video.createBackgroundSubtractorMOG2();
        Mat bsmaskMOG2= new Mat();*/

        BackgroundSubtractorKNN bs = Video.createBackgroundSubtractorKNN();
        Mat bsmaskKNN = new Mat();
        while(true){
            Mat frame = new Mat();
            boolean read = cap.read(frame);
            if(!read){
                System.out.println("not read mat");
                break;
            }
      /*      bs.apply(frame, bsmaskMOG2);
            Mat kernel = Imgproc.getStructuringElement(MORPH_RECT, new Size(3, 3));
            Imgproc.morphologyEx(bsmaskMOG2,bsmaskMOG2,Imgproc.MORPH_ERODE,kernel);*/

            bs.apply(frame,bsmaskKNN);
            Mat kernel = Imgproc.getStructuringElement(MORPH_RECT, new Size(3, 3));
            Imgproc.morphologyEx(bsmaskKNN,bsmaskKNN,Imgproc.MORPH_ERODE,kernel);
            HighGui.imshow("bsmaskKNN", bsmaskKNN);
            HighGui.imshow("frame", frame);
            HighGui.waitKey(100);
        }
        System.out.println("video end");
    }
}