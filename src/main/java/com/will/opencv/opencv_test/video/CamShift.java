package com.will.opencv.opencv_test.video;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.videoio.VideoCapture;

public class CamShift {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }


    public static void main(String[] args) {
        System.out.println("start video");
        VideoCapture cap = new VideoCapture();
        //使用本地视频
        cap.open("F:\\workspace\\opencv_test\\src\\main\\java\\com\\will\\opencv\\opencv_test\\video\\video_006.mp4");

        if (!cap.isOpened()){
            System.out.println("not open");
            return;
        };

        while(true){
            Mat frame = new Mat();
            boolean read = cap.read(frame);
            if(!read){
                System.out.println("not read mat");
                break;
            }
            Mat msk =new Mat() ;
            Core.inRange(frame,new Scalar(0,127,0),new Scalar(122,255,122),msk);

            HighGui.imshow("msk", msk);
            HighGui.imshow("frame", frame);
            HighGui.waitKey(100);
        }
        System.out.println("video end");
    }


}